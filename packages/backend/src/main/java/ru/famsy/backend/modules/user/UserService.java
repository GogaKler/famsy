package ru.famsy.backend.modules.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.famsy.backend.modules.user.dto.UserCreateDTO;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.dto.UserUpdateDTO;
import ru.famsy.backend.modules.user.exception.UserNotFoundException;
import ru.famsy.backend.modules.user.mapper.UserMapper;

import java.util.List;

@Service
public class UserService {

  private final ru.famsy.backend.modules.user.mapper.UserMapper userMapper;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;


  UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  public UserDTO saveUser(UserCreateDTO user) {
    UserEntity userEntity = userMapper.toCreateEntity(user);

    userEntity.setPassword(hashPassword(user.getPassword()));
    userRepository.save(userEntity);
    return userMapper.toDTO(userEntity);
  }

  public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
    UserEntity userEntity = findUserById(id);
    userMapper.updateUser(userUpdateDTO, userEntity);
    userRepository.save(userEntity);
    return userMapper.toDTO(userEntity);
  }

  public UserDTO patchUser(Long id, UserUpdateDTO userUpdateDTO) {
    UserEntity userEntity = findUserById(id);
    userMapper.patchUser(userUpdateDTO, userEntity);
    return userMapper.toDTO(userEntity);
  }

  public List<UserDTO> findAllUsers() {
    List<UserEntity> userEntities =  userRepository.findAll();
    return userMapper.toDTOs(userEntities);
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

  private String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }
}
