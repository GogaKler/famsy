package ru.famsy.backend.modules.space;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.famsy.backend.modules.space.dto.SpaceCreateDTO;
import ru.famsy.backend.modules.space.dto.SpaceDTO;
import ru.famsy.backend.modules.space.dto.SpaceUpdateDTO;
import ru.famsy.backend.modules.space.mapper.SpaceMapper;
import ru.famsy.backend.modules.user.UserEntity;

import java.util.List;

@Tag(name = "spaces", description = "API для управления пространствами")
@RestController
@RequestMapping("/spaces")
public class SpaceController {

  private final SpaceService spaceService;
  private final SpaceMapper spaceMapper;

  public SpaceController(SpaceService spaceService, SpaceMapper spaceMapper) {
    this.spaceService = spaceService;
    this.spaceMapper = spaceMapper;
  }

  @Operation(
      summary = "Создание нового пространства",
      description = "Создает новое пространство для авторизованного пользователя и возвращает его данные."
  )
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public SpaceDTO createSpace(
          @Valid @RequestBody SpaceCreateDTO spaceCreateDTO,
          @AuthenticationPrincipal UserEntity userEntity
  ) {
    SpaceEntity spaceEntity = spaceService.createSpace(spaceCreateDTO, userEntity);
    return spaceMapper.toDTO(spaceEntity);
  }

  @Operation(
      summary = "Получение списка пространств",
      description = "Возвращает список пространств, к которым имеет доступ авторизованный пользователь."
  )
  @GetMapping
  public List<SpaceDTO> findSpacesByAuthUser(
          @AuthenticationPrincipal UserEntity userEntity
  ) {
    List<SpaceEntity> spaceEntities = spaceService.findSpacesByCurrentUser(userEntity);
    return spaceMapper.toDTOs(spaceEntities);
  }

  @Operation(
      summary = "Получение информации о пространстве",
      description = "Возвращает данные пространства по идентификатору для авторизованного пользователя."
  )
  @GetMapping("/{id}")
  public SpaceDTO findSpaceByIdAndAuthUser(
          @PathVariable("id") Long id,
          @AuthenticationPrincipal UserEntity userEntity
  ) {
    SpaceEntity spaceEntity = spaceService.findSpaceByIdAndCurrentUser(id, userEntity);
    return spaceMapper.toDTO(spaceEntity);
  }

  @Operation(
      summary = "Обновление пространства",
      description = "Вносит частичные изменения в данные пространства, если у пользователя есть соответствующие права."
  )
  @PatchMapping("/{id}")
  public SpaceDTO patchSpace(
          @PathVariable("id") Long id,
          @Valid @RequestBody SpaceUpdateDTO spaceUpdateDTO,
          @AuthenticationPrincipal UserEntity userEntity
  ) {
    SpaceEntity spaceEntity = spaceService.patchSpace(id, spaceUpdateDTO, userEntity);
    return spaceMapper.toDTO(spaceEntity);
  }

  @Operation(
      summary = "Удаление пространства",
      description = "Удаляет (делает неактивным) пространство, если авторизованный пользователь является владельцем."
  )
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteSpace(
          @PathVariable("id") Long id,
          @AuthenticationPrincipal UserEntity userEntity
  ) {
    spaceService.deleteSpace(id, userEntity);
  }
}
