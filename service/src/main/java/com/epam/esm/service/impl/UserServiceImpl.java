package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.InvalidAttributeValueException;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.OrderDtoMapper;
import com.epam.esm.mapper.UserDtoMapper;
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

    private final UserDtoMapper userDtoMapper;

    private final OrderDtoMapper orderDtoMapper;

    @Override
    public UserDto retrieveSingleUser(long id) {
        return userRepository.findById(id).map(userDtoMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Page<UserDto> retrievePageOfUsers(int currentPage, int elementsPerPageNumber) {
        if (currentPage <= 0 || elementsPerPageNumber <= 0) {
            throw new InvalidAttributeValueException();
        }
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
        if (currentPage <= 0 || elementsPerPageNumber <= 0) {
            throw new InvalidAttributeValueException();
        }
        int totalPageNumber = (int) (userRepository.countUserOrders(id) / elementsPerPageNumber)
                + (userRepository.countUserOrders(id) % elementsPerPageNumber > 0 ? 1 : 0);
        if (currentPage > totalPageNumber) {
            throw new PageNotFoundException(currentPage, totalPageNumber);
        }
        List<OrderDto> orderDtos = userRepository.findUserOrders(id, currentPage, elementsPerPageNumber)
                .stream()
                .map(orderDtoMapper::mapToDto)
                .collect(Collectors.toList());
        return new Page<>(currentPage, totalPageNumber, elementsPerPageNumber, orderDtos);
    }
}
