package com.example.service.impl;

import com.example.domain.Book;
import com.example.helper.BookSpecifications;
import com.example.helper.OffsetBasedPageRequest;
import com.example.model.BookDTO;
import com.example.model.BookRequest;
import com.example.repository.BookRepository;
import com.example.service.BookService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final SimpleDateFormat dateFormat;

  @Autowired
  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  }

  @Override
  public List<BookDTO> findAllBooks(BookRequest bookRequest) {
    var pageRequest = new OffsetBasedPageRequest(bookRequest.getOffset(), bookRequest.getLimit());
    var dataFilter =
        new BookSpecifications(
            bookRequest.getTitle(),
            bookRequest.getAuthor(),
            toDate(bookRequest.getPublishedDate()));
    Page<Book> result;
    if (dataFilter.isAnyFilterPresent()) {
      log.info("No filter added in request");
      result = bookRepository.findAll(pageRequest);
    } else {
      result = bookRepository.findAll(dataFilter, pageRequest);
    }
    log.info("Total elements: {}", result.getTotalElements());
    return result.getContent().stream().map(this::toDTO).toList();
  }

  private BookDTO toDTO(Book book) {
    var bookDTO = new BookDTO();
    bookDTO.setTitle(book.getTitle());
    bookDTO.setAuthor(book.getAuthor());
    bookDTO.setPublishedDate(book.getPublishDate());
    return bookDTO;
  }

  private Date toDate(String date) {
    try {
      return (StringUtils.hasText(date)) ? dateFormat.parse(date) : null;
    } catch (Exception e) {
      log.info("Failed to parse date: {}", date, e);
    }
    return null;
  }
}
