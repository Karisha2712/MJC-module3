package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.pagination.Page;

public interface UserService {
    UserDto retrieveSingleUser(long id);

    Page<UserDto> retrievePageOfUsers(int currentPage, int elementsPerPageNumber);
}
