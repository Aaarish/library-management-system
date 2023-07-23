package com.example.librarysystem.dao;

import com.example.librarysystem.entities.IssuedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedItemDao extends JpaRepository<IssuedItem, String> {
}
