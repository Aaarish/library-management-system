package com.example.librarysystem.services;

import com.example.librarysystem.dto.BookDto;
import com.example.librarysystem.dto.ChangePasswordRequest;

import java.util.List;

public interface BookService {
    String addBook(BookDto bookDto);
    String removeBook(String bookId);
    String updateBook(String bookId, BookDto bookDto);
    BookDto getBook(String bookId);
    List<BookDto> getBooks();

}
