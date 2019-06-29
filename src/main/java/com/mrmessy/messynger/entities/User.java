package com.mrmessy.messynger.entities;

import com.mrmessy.messynger.enums.UserRole;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="users")
@Entity
@Data
public class User extends BaseEntity {
    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private String email;

    private String password;

    private UserRole role;
}
