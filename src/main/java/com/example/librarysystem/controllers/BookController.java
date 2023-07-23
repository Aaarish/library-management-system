package com.example.librarysystem.controllers;

import com.example.librarysystem.dto.BookDto;
import com.example.librarysystem.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDto));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> removeBook(@PathVariable String bookId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookService.removeBook(bookId));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<String> updateBook(@PathVariable String bookId, @RequestBody BookDto bookDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.updateBook(bookId, bookDto));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable String bookId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBook(bookId));
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBooks());
    }
}
