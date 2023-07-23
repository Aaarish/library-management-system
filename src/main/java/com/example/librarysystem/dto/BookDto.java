package com.example.librarysystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private String bookId;
    private String bookName;
    private String authorName;
    private int edition;
    private int count;
    private boolean isAvailable;

}
