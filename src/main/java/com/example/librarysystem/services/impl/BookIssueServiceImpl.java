package com.example.librarysystem.services.impl;

import com.example.librarysystem.dao.BookDao;
import com.example.librarysystem.dao.IssuedItemDao;
import com.example.librarysystem.dao.MemberDao;
import com.example.librarysystem.entities.Book;
import com.example.librarysystem.entities.IssuedItem;
import com.example.librarysystem.entities.Member;
import com.example.librarysystem.entities.MemberProfile;
import com.example.librarysystem.services.BookIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookIssueServiceImpl implements BookIssueService {
    private final ModelMapper modelMapper;
    private final BookDao bookDao;
    private final MemberDao memberDao;
    private final IssuedItemDao issuedItemDao;

    @Override
    public String issueBookToMember(String bookId, String memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member is not registered"));

        Book book = bookDao.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book does not exist"));

        if(!book.isAvailable() || book.getCount() == 0) {
            log.info("Book is not available");
            return "Book is not available";
        }

        IssuedItem issuedItem = new IssuedItem();

        issuedItem.setIssuedItemId(UUID.randomUUID().toString());
        issuedItem.setBook(book);
        issuedItem.setMemberProfile(member.getMemberProfile());
        issuedItem.setIssuedAt(LocalDateTime.now());
        issuedItem.setExpiredAt(LocalDateTime.now().plusDays(7));

        IssuedItem savedIssuedItem = issuedItemDao.save(issuedItem);
        book.setCount(book.getCount() - 1);
        if(book.getCount() == 0) book.setAvailable(false);

        bookDao.save(book);
        return "book issued to member with issued item id : " + savedIssuedItem.getIssuedItemId();
    }

    @Override
    public String removeBookFromMemberIssuedItemsList(String bookId, String memberId) {
        Book book = bookDao.findById(bookId)
                .orElseThrow(() -> new RuntimeException("No such book exists in records"));

        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("No such member exists in records"));

        MemberProfile memberProfile = member.getMemberProfile();

        IssuedItem issuedItem = issuedItemDao.findByBookAndMemberProfile(book, memberProfile)
                .orElseThrow(() -> new RuntimeException("No such book issue item exists in records"));

        memberProfile.getIssuedBooksList().remove(issuedItem);
        memberDao.save(member);

        return "book removed from member's issued books list";
    }

    @Override
    public String clearMemberIssuedItemsList(String memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("No such member exists in records"));

        member.getMemberProfile().setIssuedBooksList(new ArrayList<>());
        memberDao.save(member);

        return "member's issued books list cleared";
    }
}
