package com.example.service;

import com.example.model.BookDTO;
import com.example.model.BookRequest;
import java.util.List;

public interface BookService {

  List<BookDTO> findAllBooks(BookRequest bookRequest);
}
