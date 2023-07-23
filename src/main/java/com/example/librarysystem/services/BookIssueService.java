package com.example.librarysystem.services;

public interface BookIssueService {
    String issueBookToMember(String bookId, String memberId);
    String removeBookFromMemberIssuedItemsList(String bookId, String memberId);
    String clearMemberIssuedItemsList(String memberId);

}
