package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AbstractDto {
    @NotNull
    @Size(min = 4)
    private String login;

    @NotNull
    @Size(min = 4)
    private String password;
}
