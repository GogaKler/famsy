package ru.famsy.backendjava.modules.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.famsy.backendjava.modules.user.dto.UserMapper;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;
    UserRepository userRepository;

    UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserEntity saveUser(UserEntity user) {
        return this.userRepository.save(user);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользватель с таким id не найден:" + id));
    }

    public UserEntity findUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public UserEntity updateUser(Long id, UserEntity user) {
        UserEntity userEntity = this.findUserById(id);
        userMapper.updateUserEntity(user, userEntity);
        return this.userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }
}
