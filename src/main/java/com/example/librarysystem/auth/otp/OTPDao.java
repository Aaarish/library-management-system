package com.example.librarysystem.auth.otp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPDao extends JpaRepository<OTPEntity, Long> {

    Optional<OTPEntity> findByOtp(String otp);
}
