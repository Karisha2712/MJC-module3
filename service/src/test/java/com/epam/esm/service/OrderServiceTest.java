package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.CertificateDtoMapperImpl;
import com.epam.esm.mapper.OrderDtoMapper;
import com.epam.esm.mapper.OrderDtoMapperImpl;
import com.epam.esm.mapper.TagDtoMapperImpl;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.OrderServiceImpl;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {OrderDtoMapperImpl.class, CertificateDtoMapperImpl.class, TagDtoMapperImpl.class})
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    private static final List<OrderDto> orderDtos = new ArrayList<>();
    private static final List<Order> orders = new ArrayList<>();
    private static final List<User> users = new ArrayList<>();
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private UserRepository userRepository;
    @Autowired
    private OrderDtoMapper orderDtoMapper;

    @BeforeAll
    static void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        List<Certificate> certificates = new ArrayList<>();
        List<CertificateDto> certificateDtos = new ArrayList<>();

        TagDto tagDto1 = new TagDto();
        TagDto tagDto2 = new TagDto();
        tagDto1.setId(1L);
        tagDto2.setId(2L);
        tagDto1.setName("tag1");
        tagDto2.setName("tag2");

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setId(1L);
        tag2.setId(2L);
        tag1.setName("tag1");
        tag2.setName("tag2");

        CertificateDto certificateDto1 = new CertificateDto();
        CertificateDto certificateDto2 = new CertificateDto();
        certificateDto1.setId(1L);
        certificateDto1.setTitle("title1");
        certificateDto1.setPrice(new BigDecimal(1));
        certificateDto1.setDuration(100);
        certificateDto1.setDescription("description1");
        certificateDto1.setCreatedDate("2022-04-19T11:00:00Z");
        certificateDto1.setLastUpdateDate("2022-04-19T11:00:00Z");
        certificateDto2.setId(2L);
        certificateDto2.setTitle("title2");
        certificateDto2.setPrice(new BigDecimal(2));
        certificateDto2.setDuration(200);
        certificateDto2.setDescription("description2");
        certificateDto2.setCreatedDate("2022-04-19T12:00:00Z");
        certificateDto2.setLastUpdateDate("2022-04-19T12:00:00Z");
        certificateDto1.setTags(List.of(tagDto1, tagDto2));
        certificateDto2.setTags(List.of(tagDto2));
        certificateDtos.add(certificateDto1);
        certificateDtos.add(certificateDto2);

        Certificate certificate1 = new Certificate();
        Certificate certificate2 = new Certificate();
        certificate1.setId(1L);
        certificate1.setTitle("title1");
        certificate1.setPrice(new BigDecimal(1));
        certificate1.setDuration(100);
        certificate1.setDescription("description1");
        certificate1.setCreatedDate(LocalDateTime.parse("2022-04-19T11:00:00Z", formatter));
        certificate1.setLastUpdateDate(LocalDateTime.parse("2022-04-19T11:00:00Z", formatter));
        certificate2.setId(2L);
        certificate2.setTitle("title2");
        certificate2.setPrice(new BigDecimal(2));
        certificate2.setDuration(200);
        certificate2.setDescription("description2");
        certificate2.setCreatedDate(LocalDateTime.parse("2022-04-19T12:00:00Z", formatter));
        certificate2.setLastUpdateDate(LocalDateTime.parse("2022-04-19T12:00:00Z", formatter));
        certificate1.setTags(List.of(tag1, tag2));
        certificate2.setTags(List.of(tag2));
        certificates.add(certificate1);
        certificates.add(certificate2);

        User user1 = new User();
        user1.setId(1L);
        user1.setLogin("login1");
        user1.setPassword("password");
        User user2 = new User();
        user2.setId(2L);
        user2.setLogin("login2");
        user2.setPassword("password2");
        users.add(user1);
        users.add(user2);

        Order order1 = new Order();
        order1.setId(1L);
        order1.setCost(new BigDecimal(3));
        order1.setPurchaseDate(LocalDateTime.parse("2022-05-27T11:00:00Z", formatter));
        order1.setCertificates(certificates);
        order1.setUser(user1);
        Order order2 = new Order();
        order2.setId(2L);
        order2.setCost(new BigDecimal(1));
        order2.setPurchaseDate(LocalDateTime.parse("2022-05-27T12:00:00Z", formatter));
        order2.setCertificates(List.of(certificate1));
        order2.setUser(user2);
        orders.add(order1);
        orders.add(order2);

        OrderDto orderDto1 = new OrderDto();
        orderDto1.setId(1L);
        orderDto1.setCost(new BigDecimal(3));
        orderDto1.setPurchaseDate("2022-05-27T11:00:00Z");
        orderDto1.setCertificates(certificateDtos);
        OrderDto orderDto2 = new OrderDto();
        orderDto2.setId(2L);
        orderDto2.setCost(new BigDecimal(1));
        orderDto2.setPurchaseDate("2022-05-27T12:00:00Z");
        orderDto2.setCertificates(List.of(certificateDto1));
        orderDtos.add(orderDto1);
        orderDtos.add(orderDto2);
    }

    @BeforeEach
    void before() {
        orderService = new OrderServiceImpl(orderRepository, userRepository, certificateRepository, orderDtoMapper);
    }

    @Test
    void givenValidId_whenRetrieveSingleOrder_thenReturnsCorrespondingOrder() {
        OrderDto expected = orderDtos.get(1);
        Order order = orders.get(1);
        long id = 2L;
        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        OrderDto actual = orderService.retrieveSingleOrder(id);
        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidId_whenRetrieveSingleOrder_thenThrowsOrderNotFoundException() {
        long id = 2L;
        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.retrieveSingleOrder(id));
    }

    @Test
    void givenUserInvalidId_whenSaveOrder_thenThrowsUserNotFoundException() {
        long id = 3L;
        OrderDto orderDto = orderDtos.get(0);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> orderService.saveOrder(id, orderDto));
    }

    @Test
    void givenInvalidCertificateId_whenSaveOrder_thenThrowsCertificateNotFoundException() {
        long id = 3L;
        OrderDto orderDto = orderDtos.get(0);
        CertificateDto certificateDto = orderDto.getCertificates().get(0);
        User user = users.get(0);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(certificateRepository.findById(certificateDto.getId())).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> orderService.saveOrder(id, orderDto));
    }

    @Test
    void givenNotExistingPage_whenRetrievePageOfOrders_thenThrowsPageNotFoundException() {
        int currentPage = 2;
        int elementsPerPage = 2;
        Mockito.when(orderRepository.count()).thenReturn(2L);
        assertThrows(PageNotFoundException.class, () -> orderService.retrievePageOfOrders(currentPage, elementsPerPage));
    }

    @Test
    void givenExistingPage_whenRetrievePageOfOrders_thenReturnsCorrespondingPage() {
        int currentPage = 1;
        int elementsPerPage = 2;
        int totalPageNumber = 1;
        Page<OrderDto> expected = new Page<>(currentPage, totalPageNumber, elementsPerPage, orderDtos);
        Mockito.when(orderRepository.count()).thenReturn(2L);
        Mockito.when(orderRepository.findAll(PageRequest.of(currentPage - 1, elementsPerPage))).thenReturn(new PageImpl<>(orders));
        Page<OrderDto> actual = orderService.retrievePageOfOrders(currentPage, elementsPerPage);
        assertEquals(expected, actual);
    }
}
