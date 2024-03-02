package com.example.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.model.BookDTO;
import com.example.model.BookRequest;
import com.example.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/book"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookController {

  private final BookService bookService;

  @GetMapping(
      value = {"/all"},
      produces = APPLICATION_JSON_VALUE)
  public List<BookDTO> fetchAll(BookRequest bookRequest) {
    return bookService.findAllBooks(bookRequest);
  }
}
