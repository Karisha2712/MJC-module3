package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto receiveSingleUser(@PathVariable Long id) {
        return userService.retrieveSingleUser(id);
    }

    @GetMapping
    public Page<UserDto> receivePageOfUsers(
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber) {
        return userService.retrievePageOfUsers(currentPage, elementsPerPageNumber);
    }
}
