package ru.famsy.backendjava.modules.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.famsy.backendjava.modules.user.UserEntity;
import ru.famsy.backendjava.modules.user.dto.UserCreateDTO;
import ru.famsy.backendjava.modules.user.dto.UserDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("userEntityToUserDTO")
    UserDTO userEntityToUserDTO(UserEntity user);

    UserEntity userDTOToUserEntity(UserDTO userDTO);

    @Named("userEntitiesToUserDTOs")
    List<UserDTO> userEntitiesToUserDTOs(List<UserEntity> users);

    List<UserEntity> userDTOsToUserEntities(List<UserDTO> userDTOs);

    @Named("userEntityToUserCreateDTO")
    UserCreateDTO userEntityToUserCreateDTO(UserEntity user);

    UserEntity userCreateDTOToUserEntity(UserCreateDTO userCreateDTO);

    void updateUserEntity(UserEntity user, @MappingTarget UserEntity userEntity);
}
