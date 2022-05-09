package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserDtoMapper implements DtoMapper<User, UserDto> {
    private final DtoMapper<Order, OrderDto> orderDtoMapper;

    @Override
    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setOrders(user.getOrders().stream().map(orderDtoMapper::mapToDto)
                .collect(Collectors.toList()));
        return userDto;
    }

    @Override
    public User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLogin(userDto.getLogin());
        user.setOrders(userDto.getOrders().stream().map(orderDtoMapper::mapToEntity)
                .collect(Collectors.toList()));
        return user;
    }
}
