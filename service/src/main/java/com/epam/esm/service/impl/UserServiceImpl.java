package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final DtoMapper<User, UserDto> userDtoMapper;

    @Override
    public UserDto retrieveSingleUser(long id) {
        return userRepository.findById(id).map(userDtoMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Page<UserDto> retrievePageOfUsers(int currentPage, int elementsPerPageNumber) {
        int totalPageNumber = (int) (userRepository.countAllElements() / elementsPerPageNumber)
                + (userRepository.countAllElements() % elementsPerPageNumber > 0 ? 1 : 0);
        if (currentPage > totalPageNumber) {
            throw new PageNotFoundException(currentPage, totalPageNumber);
        }
        List<UserDto> userDtos = userRepository.findAll(currentPage, elementsPerPageNumber)
                .stream()
                .map(userDtoMapper::mapToDto)
                .collect(Collectors.toList());
        return new Page<>(currentPage, totalPageNumber, elementsPerPageNumber, userDtos);
    }

    @Override
    public Page<OrderDto> retrieveUserOrders(long id, int currentPage, int elementsPerPageNumber) {
        UserDto userDto = retrieveSingleUser(id);
        List<OrderDto> orderDtos = userDto.getOrders();
        int totalOrdersNumber = orderDtos.size();
        int totalPageNumber = (totalOrdersNumber / elementsPerPageNumber)
                + (totalOrdersNumber % elementsPerPageNumber > 0 ? 1 : 0);
        if (currentPage > totalPageNumber) {
            throw new PageNotFoundException(currentPage, totalPageNumber);
        }
        List<OrderDto> limitOrderDtos = orderDtos.stream()
                .skip((long) (currentPage - 1) * elementsPerPageNumber)
                .limit(elementsPerPageNumber)
                .collect(Collectors.toList());
        return new Page<>(currentPage, totalPageNumber, elementsPerPageNumber, limitOrderDtos);
    }
}
