package com.example.librarysystem.services;

import com.example.librarysystem.dto.BookDto;
import com.example.librarysystem.dto.ChangePasswordRequest;

import java.util.List;

public interface BookIssueService {
    String issueBookToMember(String bookId, String memberId);
    String removeBookFromMemberIssuedItemsList(String bookId, String memberId);
    String clearMemberIssuedItemsList(String memberId);
    List<BookDto> listIssuedBooks(String memberId);
}
