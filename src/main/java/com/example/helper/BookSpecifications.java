package com.example.helper;

import com.example.domain.Book;
import com.example.util.CriteriaBuilderUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class BookSpecifications implements Specification<Book> {

  private final String title;
  private final String author;
  private final Date publishDate;

  public boolean isAnyFilterPresent() {
    return !(StringUtils.hasText(title) || StringUtils.hasText(author) || publishDate != null);
  }

  @Override
  public Predicate toPredicate(
      @NonNull Root<Book> root,
      @NonNull CriteriaQuery<?> query,
      @NonNull CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();
    Optional.ofNullable(
            CriteriaBuilderUtil.predicateStringEq(root, criteriaBuilder, "title", title))
        .ifPresent(predicates::add);
    Optional.ofNullable(
            CriteriaBuilderUtil.predicateStringEq(root, criteriaBuilder, "author", author))
        .ifPresent(predicates::add);
    Optional.ofNullable(
            CriteriaBuilderUtil.predicateDateGe(root, criteriaBuilder, "publishDate", publishDate))
        .ifPresent(predicates::add);
    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
