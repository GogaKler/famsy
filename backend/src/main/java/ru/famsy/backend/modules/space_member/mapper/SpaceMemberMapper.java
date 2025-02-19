package ru.famsy.backend.modules.space_member.mapper;

import org.mapstruct.Mapper;
import ru.famsy.backend.modules.space_member.SpaceMemberEntity;
import ru.famsy.backend.modules.space_member.dto.SpaceMemberDTO;
import ru.famsy.backend.modules.space_member.dto.SpaceMemberNestedDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpaceMemberMapper {
  SpaceMemberDTO toDTO(SpaceMemberEntity spaceMemberEntity);
  SpaceMemberNestedDTO toNestedDTO(SpaceMemberEntity spaceMemberEntity);
  List<SpaceMemberDTO> toDTOs(List<SpaceMemberEntity> spaceMemberEntities);
  List<SpaceMemberNestedDTO> toNestedDTOs(List<SpaceMemberEntity> spaceMemberEntities);


  SpaceMemberEntity toEntity(SpaceMemberDTO spaceMemberDTO);
  SpaceMemberEntity toEntity(SpaceMemberNestedDTO spaceMemberDTO);
  List<SpaceMemberEntity> toEntities(List<SpaceMemberDTO> spaceMemberDTOs);
}
