package com.example.librarysystem.services.impl;

import com.example.librarysystem.dao.BookDao;
import com.example.librarysystem.dto.BookDto;
import com.example.librarysystem.entities.Book;
import com.example.librarysystem.services.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final ModelMapper modelMapper;
    private final BookDao bookDao;

    @Override
    public String addBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        book.setBookId(UUID.randomUUID().toString());
        book.setAvailable(true);

        Book savedBook = bookDao.save(book);
        return savedBook.getBookId();
    }

    @Override
    public String removeBook(String bookId) {
        Book book = bookDao.findById(bookId)
                .orElseThrow(() -> new RuntimeException("No book with such id exists"));

        bookDao.delete(book);

        return String.format("Book with id : {} is deleted successfully !!!", book.getBookId());
    }

    @Override
    public String updateBook(String bookId, BookDto bookDto) {
        Book book = bookDao.findById(bookId)
                .orElseThrow(() -> new RuntimeException("No book with such id exists"));

        if(bookDto.getBookName() != null) book.setBookName(bookDto.getBookName());
        if(bookDto.getEdition() >= 0) book.setEdition(bookDto.getEdition());
        if(bookDto.getAuthorName() != null) book.setAuthorName(bookDto.getAuthorName());
        if(bookDto.getCount() >= 0) book.setCount(bookDto.getCount());
        book.setAvailable(bookDto.isAvailable());

        return null;
    }

    @Override
    public BookDto getBook(String bookId) {
        Book book = bookDao.findById(bookId)
                .orElseThrow(() -> new RuntimeException("No book with such id exists"));

        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public List<BookDto> getBooks() {
        List<Book> bookList = bookDao.findAll();

        return bookList.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }
}
