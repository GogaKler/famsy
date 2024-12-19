package ru.famsy.backendjava.modules.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.famsy.backendjava.modules.user.dto.UserDTO;
import ru.famsy.backendjava.modules.user.dto.UserMapper;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(
            UserService userService,
            UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody @Valid UserDTO userDTO) {
        UserEntity userEntity = userMapper.userDTOToUserEntity(userDTO);
        UserEntity createdUserEntity = userService.saveUser(userEntity);
        return userMapper.userToUserDTO(createdUserEntity);
    }

    @GetMapping
    public List<UserDTO> findAllUsers() {
        List<UserEntity> users = userService.findAllUsers();
        return userMapper.usersToUserDTOs(users);
    }

    @PostMapping("/{id}")
    public UserDTO findUserById(@PathVariable("id") Long id) {
        UserEntity userEntity = userService.findUserById(id);
        return userMapper.userToUserDTO(userEntity);
    }

    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserDTO userDTO) {
        UserEntity userEntity = userMapper.userDTOToUserEntity(userDTO);
        UserEntity updatedUserEntity = userService.updateUser(id, userEntity);
        return userMapper.userToUserDTO(updatedUserEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
