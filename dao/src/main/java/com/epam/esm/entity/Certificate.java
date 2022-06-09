package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "certificates")
public class Certificate extends BaseEntity {

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Integer duration;

    @Column
    private BigDecimal price;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "user_roles_id")
    private UserRole role;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "certificates_has_tags",
            joinColumns = @JoinColumn(name = "certificates_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private List<Tag> tags;
}

