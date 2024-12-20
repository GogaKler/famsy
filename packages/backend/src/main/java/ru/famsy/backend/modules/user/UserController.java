package ru.famsy.backend.modules.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.famsy.backend.modules.user.dto.UserCreateDTO;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.mapper.UserMapper;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ru.famsy.backend.modules.user.mapper.UserMapper userMapper;

    public UserController(
            UserService userService,
            UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        UserEntity userEntity = userMapper.userCreateDTOToUserEntity(userCreateDTO);
        UserEntity createdUserEntity = userService.saveUser(userEntity);
        return userMapper.userEntityToUserDTO(createdUserEntity);
    }

    @GetMapping
    public List<UserDTO> findAllUsers() {
        List<UserEntity> users = userService.findAllUsers();
        return userMapper.userEntitiesToUserDTOs(users);
    }

    @PostMapping("/{id}")
    public UserDTO findUserById(@PathVariable("id") Long id) {
        UserEntity userEntity = userService.findUserById(id);
        return userMapper.userEntityToUserDTO(userEntity);
    }

    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserDTO userDTO) {
        UserEntity userEntity = userMapper.userDTOToUserEntity(userDTO);
        UserEntity updatedUserEntity = userService.updateUser(id, userEntity);
        return userMapper.userEntityToUserDTO(updatedUserEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
