package com.example.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Entity
@Table(name = "book")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String title;
  String author;

  @Basic
  @Column(name = "publish_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date publishDate;
}
