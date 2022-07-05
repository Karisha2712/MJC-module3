package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    String PREFIX = "ROLE_";
    String EMPTY_STRING = "";

    @Mapping(source = "roleId", target = "userRole", qualifiedByName = "roleIdToString")
    UserDto mapToDto(User user);

    User mapToEntity(UserDto userDto);

    @Named("roleIdToString")
    static String roleIdToString(int roleId) {
        return UserRole.getRoleById(roleId).toString().replace(PREFIX, EMPTY_STRING);
    }
}
