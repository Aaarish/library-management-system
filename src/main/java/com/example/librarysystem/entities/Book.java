package com.example.librarysystem.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {
    @Id
    private String bookId;
    private String bookName;
    private String authorName;
    private int edition;
    private int count;
    private boolean isAvailable;

}
