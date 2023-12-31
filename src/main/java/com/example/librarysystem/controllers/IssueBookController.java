package com.example.librarysystem.controllers;

import com.example.librarysystem.dto.BookDto;
import com.example.librarysystem.services.BookIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issue-book")
@RequiredArgsConstructor
public class IssueBookController {
    private final BookIssueService bookIssueService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BookDto>> listIssuedBooks(@PathVariable String memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookIssueService.listIssuedBooks(memberId));
    }

    @PutMapping("/{bookId}/member/{memberId}")
    public ResponseEntity<String> issueBook(@PathVariable String bookId, @PathVariable String memberId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookIssueService.issueBookToMember(bookId, memberId));
    }

    @DeleteMapping("/{bookId}/member/{memberId}")
    public ResponseEntity<String> removeIssuedBook(@PathVariable String bookId, @PathVariable String memberId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookIssueService.removeBookFromMemberIssuedItemsList(bookId, memberId));
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<String> clearIssuedBooksList(@PathVariable String memberId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookIssueService.clearMemberIssuedItemsList(memberId));
    }
}
