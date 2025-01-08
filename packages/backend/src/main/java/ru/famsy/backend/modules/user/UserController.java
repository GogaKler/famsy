package ru.famsy.backend.modules.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.famsy.backend.modules.user.dto.UserCreateDTO;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.dto.UserUpdateDTO;
import ru.famsy.backend.modules.user.mapper.UserMapper;

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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        UserEntity userEntity = userService.saveUser(userCreateDTO);
        return ResponseEntity.ok(userMapper.toDTO(userEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        UserEntity updatedUserEntity = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(userMapper.toDTO(updatedUserEntity));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patchUser(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        UserEntity updatedUserEntity = userService.patchUser(id, userUpdateDTO);
        return ResponseEntity.ok(userMapper.toDTO(updatedUserEntity));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserEntity> userEntities = userService.findAllUsers();
        return ResponseEntity.ok(userMapper.toDTOs(userEntities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable("id") Long id) {
        UserEntity userEntity = userService.findUserById(id);
        return ResponseEntity.ok(userMapper.toDTO(userEntity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
