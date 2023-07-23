package com.example.librarysystem.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "issued_books")
public class IssuedItem {
    @Id
    private String issuedItemId;

    @OneToOne
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberProfile memberProfile;

    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;

}
