package ru.famsy.backend.modules.user.mapper;

import org.mapstruct.*;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.dto.UserCreateDTO;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.dto.UserUpdateDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("toDTO")
    UserDTO toDTO(UserEntity user);
    UserEntity toEntity(UserDTO userDTO);

    List<UserDTO> toDTOs(List<UserEntity> users);
    List<UserEntity> toEntities(List<UserDTO> userDTOs);

    @Named("toCreateDTO")
    UserCreateDTO toCreateDTO(UserEntity user);
    UserEntity toCreateEntity(UserCreateDTO userCreateDTO);

    @Named("toUpdateDTO")
    UserUpdateDTO toUpdateDTO(UserEntity user);
    UserEntity toUpdateEntity(UserUpdateDTO userUpdateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchUser(UserUpdateDTO userUpdateDTO, @MappingTarget UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void updateUser(UserUpdateDTO userUpdateDTO, @MappingTarget UserEntity userEntity);
}
