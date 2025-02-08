package ru.famsy.backend.modules.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.famsy.backend.common.exception.base.MinioException;
import ru.famsy.backend.common.validator.FileValidationRules;
import ru.famsy.backend.common.validator.FileValidator;
import ru.famsy.backend.modules.files.MinioFileStorageService;
import ru.famsy.backend.modules.files.dto.FileUploadResponseDTO;
import ru.famsy.backend.modules.user.dto.UserCreateDTO;
import ru.famsy.backend.modules.user.dto.UserUpdateDTO;
import ru.famsy.backend.modules.user.exception.UserNotFoundException;
import ru.famsy.backend.modules.user.mapper.UserMapper;

import java.util.List;

@Service
public class UserService {
  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final MinioFileStorageService minioFileStorageService;
  private final FileValidator fileValidator;

  UserService(
          UserRepository userRepository,
          UserMapper userMapper,
          MinioFileStorageService minioFileStorageService,
          FileValidator fileValidator
  ) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.minioFileStorageService = minioFileStorageService;
    this.fileValidator = fileValidator;
  }

  public UserEntity saveUser(UserCreateDTO user) {
    UserEntity userEntity = userMapper.toCreateEntity(user);

    userEntity.setPassword(hashPassword(user.getPassword()));
    userEntity = userRepository.save(userEntity);
    return userEntity;
  }

  public UserEntity updateUser(Long id, UserUpdateDTO userUpdateDTO) {
    UserEntity userEntity = findUserById(id);
    userMapper.updateUser(userUpdateDTO, userEntity);
    userEntity = userRepository.save(userEntity);
    return userEntity;
  }

  public UserEntity patchUser(Long id, UserUpdateDTO userUpdateDTO) {
    UserEntity userEntity = findUserById(id);
    userMapper.patchUser(userUpdateDTO, userEntity);
    return userEntity;
  }

  public List<UserEntity> findAllUsers() {
    return userRepository.findAll();
  }

  public UserEntity findUserById(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> UserNotFoundException.byId(id));
  }

  public UserEntity findUserByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> UserNotFoundException.notFound());
  }

  public UserEntity findUserByUsername(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> UserNotFoundException.notFound());
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  public UserEntity uploadAvatar(Long id, MultipartFile file) {
    try {
      FileValidationRules rules = FileValidationRules
              .builder()
              .image()
              .maxSizeInBytes(2 * 1024 * 1024L)
              .allowedMimeTypes(List.of("image/jpeg", "image/png"))
              .minWidth(100)
              .minHeight(100)
              .build();

      fileValidator.validateFile(file, rules);

      UserEntity userEntity = findUserById(id);

      if (userEntity.getAvatarFileName() != null && !userEntity.getAvatarFileName().isEmpty()) {
        deleteAvatar(userEntity.getId());
      }

      FileUploadResponseDTO fileUploadResponseDTO = minioFileStorageService.uploadFile(file, "avatars");
      userEntity.setAvatarUrl(fileUploadResponseDTO.getFileUrl());
      userEntity.setAvatarFileName(fileUploadResponseDTO.getFileName());
      return userRepository.save(userEntity);
    } catch (Exception e) {
      throw new MinioException(e.getMessage());
    }
  }

  public UserEntity deleteAvatar(Long id) {
    try {
      UserEntity userEntity = findUserById(id);
      String avatarFileName = userEntity.getAvatarFileName();

      if (avatarFileName != null && !avatarFileName.isEmpty()) {
        minioFileStorageService.deleteFile(avatarFileName, "avatars");
      }

      userEntity.setAvatarUrl(null);
      userEntity.setAvatarFileName(null);
      return userRepository.save(userEntity);
    } catch (Exception e) {
      throw new MinioException(e.getMessage());
    }
  }

  private String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }
}
