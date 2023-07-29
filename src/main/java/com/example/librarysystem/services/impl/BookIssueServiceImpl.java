package com.example.librarysystem.services.impl;

import com.example.librarysystem.dao.BookDao;
import com.example.librarysystem.dao.IssuedItemDao;
import com.example.librarysystem.dao.MemberDao;
import com.example.librarysystem.dao.MemberProfileDao;
import com.example.librarysystem.dto.BookDto;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookIssueServiceImpl implements BookIssueService {
    private final ModelMapper modelMapper;
    private final BookDao bookDao;
    private final MemberDao memberDao;
    private final MemberProfileDao memberProfileDao;
    private final IssuedItemDao issuedItemDao;

    @Override
    public String issueBookToMember(String bookId, String memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member is not registered"));

        Book book = bookDao.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book does not exist"));

        MemberProfile memberProfile = null;

        try {
            memberProfile = memberProfileDao.findByMember(member).get();
        } catch (NoSuchElementException e) {
            MemberProfile newMemberProfile = MemberProfile.builder()
                    .memberProfileId(UUID.randomUUID().toString())
                    .member(member)
                    .issuedBooksList(new ArrayList<>())
                    .build();

            memberProfileDao.save(newMemberProfile);
        }

        if(!book.isAvailable() || book.getCount() == 0) {
            log.info("Book is not available");
            return "Book is not available";
        }

        IssuedItem issuedItem = new IssuedItem();

        issuedItem.setIssuedItemId(UUID.randomUUID().toString());
        issuedItem.setBook(book);
        issuedItem.setMemberProfile(memberProfile);
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

        MemberProfile memberProfile = memberProfileDao.findByMember(member)
                .orElseThrow(() -> new RuntimeException("No such member profile exists in records"));

        IssuedItem issuedItem = issuedItemDao.findByBookAndMemberProfile(book, memberProfile)
                .orElseThrow(() -> new RuntimeException("No such book issue item exists in records"));

        memberProfile.getIssuedBooksList().remove(issuedItem);
        book.setCount(book.getCount() + 1);

        memberProfileDao.save(memberProfile);
        bookDao.save(book);

        issuedItemDao.delete(issuedItem);

        return "book removed from member's issued books list";
    }

    @Override
    public String clearMemberIssuedItemsList(String memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("No such member exists in records"));

        MemberProfile memberProfile = memberProfileDao.findByMember(member)
                .orElseThrow(() -> new RuntimeException("No such member profile exists in records"));

        memberProfile.setIssuedBooksList(new ArrayList<>());
        memberProfileDao.save(memberProfile);

        // TODO: 7/30/2023 changes in logic to be done as similar to above method

        return "member's issued books list cleared";
    }

    @Override
    public List<BookDto> listIssuedBooks(String memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("No member exists with this member_id"));

        MemberProfile memberProfile = memberProfileDao.findByMember(member)
                .orElseThrow(() -> new RuntimeException("No such member_profile exists"));

        List<IssuedItem> issuedBooksList = memberProfile.getIssuedBooksList();

        return issuedBooksList.stream()
                .map(issuedItem -> modelMapper.map(issuedItem.getBook(), BookDto.class))
                .collect(Collectors.toList());
    }

}
