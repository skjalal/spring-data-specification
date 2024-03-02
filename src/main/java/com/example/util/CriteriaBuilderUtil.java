package com.example.util;

import com.example.domain.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Date;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class CriteriaBuilderUtil {

  public static Predicate predicateStringEq(
      Root<Book> root, CriteriaBuilder criteriaBuilder, String columnName, String value) {
    if (StringUtils.hasText(value)) {
      return criteriaBuilder.like(root.get(columnName), "%" + value + "%");
    }
    return null;
  }

  public static Predicate predicateDateGe(
      Root<Book> root, CriteriaBuilder criteriaBuilder, String columnName, Date value) {
    if (value == null) {
      return null;
    }
    return criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), value);
  }
}
