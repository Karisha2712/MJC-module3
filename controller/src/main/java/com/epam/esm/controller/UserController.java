package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.OrdersLinksCreator;
import com.epam.esm.hateoas.UsersLinksCreator;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ControllerResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final UsersLinksCreator usersLinksCreator;
    private final OrdersLinksCreator ordersLinksCreator;

    @GetMapping("/{id}")
    public UserDto receiveSingleUser(@PathVariable Long id) {
        return userService.retrieveSingleUser(id);
    }

    @GetMapping("/{id}/orders")
    public Page<OrderDto> receiveUserOrders(
            @PathVariable Long id,
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber) {
        Page<OrderDto> orderPage = userService.retrieveUserOrders(id, currentPage, elementsPerPageNumber);
        orderPage.getPageContent().forEach(ordersLinksCreator::createLinks);
        //todo add pagination links
        return orderPage;
    }

    @GetMapping
    public Page<UserDto> receivePageOfUsers(
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber) {
        Page<UserDto> userPage = userService.retrievePageOfUsers(currentPage, elementsPerPageNumber);
        userPage.getPageContent().forEach(usersLinksCreator::createLinks);
        usersLinksCreator.createPaginationLinks(userPage);
        return userPage;
    }

    @PostMapping("/{id}/orders")
    public ControllerResponse createOrder(@PathVariable Long id,
                                          @RequestBody OrderDto orderDto) {
        orderService.saveOrder(id, orderDto);
        return new ControllerResponse("Order was created successfully");
    }
}
