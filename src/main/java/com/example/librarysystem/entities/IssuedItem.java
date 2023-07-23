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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "bookId", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_profile_id", referencedColumnName = "memberProfileId", nullable = false)
    private MemberProfile memberProfile;

    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;

}
