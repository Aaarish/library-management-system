package com.example.librarysystem.dao;

import com.example.librarysystem.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberDao extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);
}
