package ru.famsy.backend.modules.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

  public UserEntity saveUser(UserEntity user) {
    user.setPassword(hashPassword(user.getPassword()));
    return this.userRepository.save(user);
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

  public UserEntity updateUser(Long id, UserEntity user) {
    UserEntity userEntity = findUserById(id);
    userMapper.updateUserEntity(user, userEntity);
    return userRepository.save(userEntity);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  private String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }
}
