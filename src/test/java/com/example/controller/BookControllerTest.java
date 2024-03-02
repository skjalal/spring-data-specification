package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

import com.example.model.BookDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookControllerTest {

  @Autowired TestRestTemplate restTemplate;

  @ParameterizedTest
  @ValueSource(strings = {"NO_FILTER", "TITLE", "AUTHOR", "DATE", "INVALID_DATE"})
  void testFetchAll(String filterName) {
    String url;
    String expect;
    if (filterName.equalsIgnoreCase("TITLE")) {
      url = "/book/all?offset=0&limit=10&title=NET";
      expect = ".NET";
    } else if (filterName.equalsIgnoreCase("AUTHOR")) {
      url = "/book/all?offset=0&limit=10&author=Alex";
      expect = "Python";
    } else if (filterName.contains("DATE")) {
      var date = filterName.equalsIgnoreCase("INVALID_DATE") ? "1992-01-Jan" : "1992-01-01";
      url = "/book/all?offset=0&limit=10&publishedDate=" + date;
      expect = "Java";
    } else {
      url = "/book/all?offset=0&limit=10";
      expect = "Java";
    }
    var result = restTemplate.getForObject(url, BookDTO[].class);
    assertNotNull(result);
    assertNotEquals(0, result.length);
    assertEquals(expect, result[0].getTitle());
  }
}
