package com.example.librarysystem.entities;

import lombok.*;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides(
        {
                @AttributeOverride(name = "email", column = @Column(name = "email", nullable = false, unique = true)),
                @AttributeOverride(name = "password", column = @Column(name = "password", nullable = false))
        }
)
@Getter
@Setter
public class Credentials {
    private String email;
    private String password;

}
