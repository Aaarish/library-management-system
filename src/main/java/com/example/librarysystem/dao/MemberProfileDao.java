package com.example.librarysystem.dao;

import com.example.librarysystem.entities.Member;
import com.example.librarysystem.entities.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberProfileDao extends JpaRepository<MemberProfile, String> {
    Optional<MemberProfile> findByMember(Member member);
}
