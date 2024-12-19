package ru.famsy.backendjava.modules.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.famsy.backendjava.modules.user.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(UserEntity user);

    UserEntity userDTOToUserEntity(UserDTO userDTO);

    List<UserDTO> usersToUserDTOs(List<UserEntity> users);

    List<UserEntity> userDTOsToUserEntities(List<UserDTO> userDTOs);

    void updateUserEntity(UserEntity user, @MappingTarget UserEntity userEntity);
}
