package com.example.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
  String title;
  String author;
  String publishedDate;

  Integer offset;
  Integer limit;
}
