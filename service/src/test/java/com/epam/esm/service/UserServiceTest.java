package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.*;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {OrderDtoMapperImpl.class, CertificateDtoMapperImpl.class, TagDtoMapperImpl.class})
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final List<User> users = new ArrayList<>();
    private static final List<UserDto> userDtos = new ArrayList<>();
    private static final List<OrderDto> orderDtos = new ArrayList<>();
    private static final List<Order> orders = new ArrayList<>();
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Autowired
    private OrderDtoMapper orderDtoMapper;

    @BeforeAll
    static void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        User user1 = new User();
        user1.setId(1L);
        user1.setLogin("login1");
        user1.setPassword("password");
        user1.setRoleId(UserRole.ROLE_USER.getRoleId());
        User user2 = new User();
        user2.setId(2L);
        user2.setLogin("login2");
        user2.setPassword("password2");
        user2.setRoleId(UserRole.ROLE_ADMIN.getRoleId());
        users.add(user1);
        users.add(user2);

        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setLogin("login1");
        userDto1.setPassword("password");
        userDto1.setUserRole(UserRole.ROLE_USER.toString());
        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setLogin("login2");
        userDto2.setPassword("password2");
        userDto2.setUserRole(UserRole.ROLE_ADMIN.toString());
        userDtos.add(userDto1);
        userDtos.add(userDto2);

        Certificate certificate = new Certificate();
        certificate.setId(1L);
        certificate.setTitle("title1");
        certificate.setPrice(new BigDecimal(1));
        certificate.setDuration(100);
        certificate.setDescription("description1");
        certificate.setCreatedDate(LocalDateTime.parse("2022-04-19T11:00:00Z", formatter));
        certificate.setLastUpdateDate(LocalDateTime.parse("2022-04-19T11:00:00Z", formatter));
        certificate.setTags(Collections.emptyList());

        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(1L);
        certificateDto.setTitle("title1");
        certificateDto.setPrice(new BigDecimal(1));
        certificateDto.setDuration(100);
        certificateDto.setDescription("description1");
        certificateDto.setCreatedDate("2022-04-19T11:00:00Z");
        certificateDto.setLastUpdateDate("2022-04-19T11:00:00Z");
        certificateDto.setTags(Collections.emptyList());

        Order order1 = new Order();
        order1.setId(1L);
        order1.setCost(new BigDecimal(3));
        order1.setPurchaseDate(LocalDateTime.parse("2022-05-27T11:00:00Z", formatter));
        order1.setCertificates(List.of(certificate));
        order1.setUser(user1);
        Order order2 = new Order();
        order2.setId(2L);
        order2.setCost(new BigDecimal(1));
        order2.setPurchaseDate(LocalDateTime.parse("2022-05-27T12:00:00Z", formatter));
        order2.setCertificates(List.of(certificate));
        order2.setUser(user1);
        orders.add(order1);
        orders.add(order2);

        OrderDto orderDto1 = new OrderDto();
        orderDto1.setId(1L);
        orderDto1.setCost(new BigDecimal(3));
        orderDto1.setPurchaseDate("2022-05-27T11:00:00Z");
        orderDto1.setCertificates(List.of(certificateDto));
        OrderDto orderDto2 = new OrderDto();
        orderDto2.setId(2L);
        orderDto2.setCost(new BigDecimal(1));
        orderDto2.setPurchaseDate("2022-05-27T12:00:00Z");
        orderDto2.setCertificates(List.of(certificateDto));
        orderDtos.add(orderDto1);
        orderDtos.add(orderDto2);
    }

    @BeforeEach
    void before() {
        userService = new UserServiceImpl(userRepository, orderRepository, new UserDtoMapperImpl(), orderDtoMapper, new BCryptPasswordEncoder());
    }

    @Test
    void givenValidId_whenRetrieveSingleUser_thenReturnsCorrespondingUser() {
        UserDto expected = userDtos.get(1);
        User user = users.get(1);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserDto actual = userService.retrieveSingleUser(user.getId());
        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidId_whenRetrieveSingleUser_thenThrowsUserNotFoundException() {
        long id = 2L;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.retrieveSingleUser(id));
    }

    @Test
    void givenNotExistingPage_whenRetrievePageOfUsers_thenThrowsPageNotFoundException() {
        int currentPage = 2;
        int elementsPerPage = 2;
        Mockito.when(userRepository.count()).thenReturn(2L);
        assertThrows(PageNotFoundException.class, () -> userService.retrievePageOfUsers(currentPage, elementsPerPage));
    }

    @Test
    void givenExistingPage_whenRetrievePageOfUsers_thenThrowsPageNotFoundException() {
        int currentPage = 1;
        int elementsPerPage = 2;
        int totalPageNumber = 1;
        Page<UserDto> expected = new Page<>(currentPage, totalPageNumber, elementsPerPage, userDtos);
        Mockito.when(userRepository.count()).thenReturn(2L);
        Mockito.when(userRepository.findAll(PageRequest.of(currentPage - 1, elementsPerPage))).thenReturn(new PageImpl<>(users));
        Page<UserDto> actual = userService.retrievePageOfUsers(currentPage, elementsPerPage);
        assertEquals(expected, actual);
    }

    @Test
    void givenNotExistingPage_whenRetrieveUserOrders_thenThrowsPageNotFoundException() {
        int currentPage = 2;
        int elementsPerPage = 2;
        User user = users.get(0);
        Mockito.when(orderRepository.countOrdersByUserId(user.getId())).thenReturn(2L);
        assertThrows(PageNotFoundException.class,
                () -> userService.retrieveUserOrders(user.getId(), currentPage, elementsPerPage));
    }

    @Test
    void givenExistingPage_whenRetrieveUserOrders_thenReturnsCorrespondingPage() {
        int currentPage = 1;
        int elementsPerPage = 2;
        int totalPageNumber = 1;
        User user = users.get(0);
        Page<OrderDto> expected = new Page<>(currentPage, totalPageNumber, elementsPerPage, orderDtos);
        Mockito.when(orderRepository.countOrdersByUserId(user.getId())).thenReturn(2L);
        Mockito.when(orderRepository.findByUserId(user.getId(),
                PageRequest.of(currentPage - 1, elementsPerPage))).thenReturn(new PageImpl<>(orders));
        Page<OrderDto> actual = userService.retrieveUserOrders(user.getId(), currentPage, elementsPerPage);
        assertEquals(expected, actual);
    }

}
