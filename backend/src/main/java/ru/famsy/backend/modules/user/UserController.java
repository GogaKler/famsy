package ru.famsy.backend.modules.user;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.mapper.UserMapper;

@Tag(name = "users", description = "API для управления пользователями")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserController(
            UserService userService,
            UserMapper userMapper,
            UserRepository userRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Operation(
        summary = "Получение списка пользователей",
        description = "Возвращает список всех пользователей."
    )
    @GetMapping
    public Page<UserDTO> findAllUsers(
            @QuerydslPredicate(root = UserEntity.class) Predicate predicate,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<UserEntity> pageUserEntities = userRepository.findAll(predicate, pageable);

        return pageUserEntities.map((userEntity) -> userMapper.toDTO(userEntity));
    }

    @Operation(
        summary = "Получение пользователя по ID",
        description = "Возвращает данные пользователя по его идентификатору."
    )
    @GetMapping("/{id}")
    public UserDTO findUserById(@PathVariable("id") Long id) {
        UserEntity userEntity = userService.findUserById(id);
        return userMapper.toDTO(userEntity);
    }
}
