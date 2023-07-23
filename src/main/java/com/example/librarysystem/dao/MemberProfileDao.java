package com.example.librarysystem.dao;

import com.example.librarysystem.entities.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileDao extends JpaRepository<MemberProfile, Long> {
}
