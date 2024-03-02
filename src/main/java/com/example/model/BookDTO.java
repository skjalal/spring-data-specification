package com.example.model;

import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTO {
  String title;
  String author;
  Date publishedDate;
}
