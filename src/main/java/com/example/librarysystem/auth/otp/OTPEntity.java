package com.example.librarysystem.auth.otp;

import com.example.librarysystem.entities.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "one_time_passwords")
public class OTPEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String otp;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "memberId", nullable = false)
    private Member member;


    public OTPEntity(Member member) {
        this.otp = UUID.randomUUID().toString();
        this.expirationTime = createExpirationTime();
        this.member = member;
    }

    private LocalDateTime createExpirationTime(){
        return LocalDateTime.now().plusMinutes(2);
    }
}
