package com.example.librarysystem.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_profiles")
public class MemberProfile {
    @Id
    private Long memberProfileId;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "memberId", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "memberProfile", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private List<IssuedItem> issuedBooksList;

}
