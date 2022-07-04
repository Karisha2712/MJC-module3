package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AbstractDto {
    @NotNull
    @Length(min = 4)
    private String login;

    @NotNull
    @Length(min = 4)
    private String password;

    @Pattern(regexp = "ADMIN|USER")
    private String userRole;
}
