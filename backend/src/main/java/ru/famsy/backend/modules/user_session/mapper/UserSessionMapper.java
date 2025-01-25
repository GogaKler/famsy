package ru.famsy.backend.modules.user_session.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ru.famsy.backend.modules.user_session.UserSessionEntity;
import ru.famsy.backend.modules.user_session.dto.UserSessionDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserSessionMapper {
  UserSessionDTO toDTO(UserSessionEntity userSessionEntity);

  @Named("toDTOList")
  List<UserSessionDTO> toDTOList(List<UserSessionEntity> entities);
}
