package com.example.librarysystem.dao;

import com.example.librarysystem.entities.Book;
import com.example.librarysystem.entities.IssuedItem;
import com.example.librarysystem.entities.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssuedItemDao extends JpaRepository<IssuedItem, String> {

    Optional<IssuedItem> findByBookAndMemberProfile(Book book, MemberProfile memberProfile);
}
