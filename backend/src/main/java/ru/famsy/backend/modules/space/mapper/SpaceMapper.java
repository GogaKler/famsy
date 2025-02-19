package ru.famsy.backend.modules.space.mapper;

import org.mapstruct.*;

import ru.famsy.backend.modules.space.SpaceEntity;
import ru.famsy.backend.modules.space.dto.SpaceDTO;
import ru.famsy.backend.modules.space.dto.SpaceCreateDTO;
import ru.famsy.backend.modules.space.dto.SpaceUpdateDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpaceMapper {
  SpaceDTO toDTO(SpaceEntity spaceEntity);

  SpaceEntity toEntity(SpaceDTO spaceDTO);
  SpaceEntity toEntity(SpaceCreateDTO spaceCreateDTO);

  List<SpaceDTO> toDTOs(List<SpaceEntity> spaceEntities);
  List<SpaceEntity> toEntities(List<SpaceDTO> spaceDTOs);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void patchEntity(SpaceUpdateDTO spaceUpdateDTO, @MappingTarget SpaceEntity spaceEntity);
}
