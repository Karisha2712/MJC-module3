package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
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
        List<UserDto> certificateDtos = userRepository.findAll(currentPage, elementsPerPageNumber)
                .stream()
                .map(userDtoMapper::mapToDto)
                .collect(Collectors.toList());
        return new Page<>(currentPage, totalPageNumber, elementsPerPageNumber, certificateDtos);
    }
}
