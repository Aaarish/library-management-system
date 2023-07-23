package com.example.librarysystem.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "members")
public class Member {
    @Id
    private String memberId;
    private String memberName;
    private String memberContactNumber;

    @Embedded
    private Credentials memberCredentials;

}
