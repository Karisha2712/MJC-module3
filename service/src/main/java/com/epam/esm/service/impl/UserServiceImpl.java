package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.exception.InvalidAttributeValueException;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.UserAlreadyExistsException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.OrderDtoMapper;
import com.epam.esm.mapper.UserDtoMapper;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    private final UserDtoMapper userDtoMapper;

    private final OrderDtoMapper orderDtoMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserDto userDto) {
        if (loginExists(userDto.getLogin())) {
            throw new UserAlreadyExistsException();
        }
        User user = new User();
        user.setLogin(userDto.getLogin());
        UserRole userRole = (UserRole.ROLE_ADMIN).toString().equals(userDto.getUserRole()) ?
                UserRole.ROLE_ADMIN : UserRole.ROLE_USER;
        user.setRoleId(userRole.getRoleId());
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.saveEntity(user);
    }

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

    private boolean loginExists(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserRole.getRoleById(user.getRoleId()).toString());
        return new CustomUserDetails(user.getId(), user.getLogin(), user.getPassword(), authority);
    }
}
