package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AbstractDto {
    private String login;
    private String password;
    private List<OrderDto> orders;
}
