package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
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

    @GetMapping("/{id}")
    public UserDto receiveSingleUser(@PathVariable Long id) {
        return userService.retrieveSingleUser(id);
    }

    @GetMapping("/{id}/orders")
    public Page<OrderDto> receiveUserOrders(
            @PathVariable Long id,
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber) {
        return userService.retrieveUserOrders(id, currentPage, elementsPerPageNumber);
    }

    @GetMapping
    public Page<UserDto> receivePageOfUsers(
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber) {
        return userService.retrievePageOfUsers(currentPage, elementsPerPageNumber);
    }

    @PostMapping("/{id}")
    public ControllerResponse createOrder(@PathVariable Long id,
                                          @RequestBody OrderDto orderDto) {
        orderService.saveOrder(id, orderDto);
        return new ControllerResponse("Order was created successfully");
    }
}
