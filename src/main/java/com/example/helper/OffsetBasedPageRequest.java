package com.example.helper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

public class OffsetBasedPageRequest implements Pageable {

  private final int limit;
  private final int offset;
  private final Sort sort;

  public OffsetBasedPageRequest(int offset, int limit, Sort sort) {
    if (offset < 0) {
      throw new IllegalArgumentException("Offset index must not be less than zero!");
    }

    if (limit < 1) {
      throw new IllegalArgumentException("Limit must not be less than one!");
    }
    this.limit = limit;
    this.offset = offset;
    this.sort = sort;
  }

  public OffsetBasedPageRequest(int offset, int limit) {
    this(offset, limit, Sort.unsorted());
  }

  @Override
  public int getPageNumber() {
    return offset / limit;
  }

  @Override
  public int getPageSize() {
    return limit;
  }

  @Override
  @NonNull
  public long getOffset() {
    return offset;
  }

  @Override
  @NonNull
  public Sort getSort() {
    return sort;
  }

  @Override
  @NonNull
  public Pageable next() {
    return new OffsetBasedPageRequest((int) getOffset() + getPageSize(), getPageSize(), getSort());
  }

  public OffsetBasedPageRequest previous() {
    return hasPrevious()
        ? new OffsetBasedPageRequest((int) getOffset() - getPageSize(), getPageSize(), getSort())
        : this;
  }

  @Override
  @NonNull
  public Pageable previousOrFirst() {
    return hasPrevious() ? previous() : first();
  }

  @Override
  @NonNull
  public Pageable first() {
    return new OffsetBasedPageRequest(0, getPageSize(), getSort());
  }

  @Override
  @NonNull
  public Pageable withPage(int pageNumber) {
    return PageRequest.of(pageNumber, getPageSize());
  }

  @Override
  public boolean hasPrevious() {
    return offset > limit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (!(o instanceof OffsetBasedPageRequest that)) return false;

    return new EqualsBuilder()
        .append(limit, that.limit)
        .append(offset, that.offset)
        .append(sort, that.sort)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(limit).append(offset).append(sort).toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("limit", limit)
        .append("offset", offset)
        .append("sort", sort)
        .toString();
  }
}
