package com.epam.esm.hateoas;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsersLinksCreator implements LinksCreator<UserDto> {
    @Override
    public void createLinks(UserDto user) {
        int currentPage = 1;
        int elementsPerPageNumber = 2;
        user.add(linkTo(methodOn(UserController.class)
                .receiveSingleUser(user.getId()))
                .withSelfRel());
        user.add(linkTo(methodOn(UserController.class)
                .receiveUserOrders(user.getId(), currentPage, elementsPerPageNumber))
                .withRel("Orders"));
    }

    @Override
    public void createPaginationLinks(Page<UserDto> page) {
        if (page.getCurrentPage() > 1) {
            page.add(linkTo(methodOn(UserController.class)
                    .receivePageOfUsers(page.getCurrentPage() - 1, page.getElementsPerPageNumber()))
                    .withRel("Previous page"));
        }

        if (page.getCurrentPage() < page.getTotalPageNumber()) {
            page.add(linkTo(methodOn(UserController.class)
                    .receivePageOfUsers(page.getCurrentPage() + 1, page.getElementsPerPageNumber()))
                    .withRel("Next page"));
        }
    }
}
