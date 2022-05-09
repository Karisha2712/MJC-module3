package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column
    private String login;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Order> orders = new ArrayList<>();

    @Column
    private String password;

}
