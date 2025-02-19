package ru.famsy.backend.modules.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.mapper.UserMapper;

@Tag(name = "users", description = "API для управления пользователями")
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

    @Operation(
        summary = "Получение списка пользователей",
        description = "Возвращает список всех пользователей."
    )
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserEntity> userEntities = userService.findAllUsers();
        return ResponseEntity.ok(userMapper.toDTOs(userEntities));
    }

    @Operation(
        summary = "Получение пользователя по ID",
        description = "Возвращает данные пользователя по его идентификатору."
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable("id") Long id) {
        UserEntity userEntity = userService.findUserById(id);
        return ResponseEntity.ok(userMapper.toDTO(userEntity));
    }
}
