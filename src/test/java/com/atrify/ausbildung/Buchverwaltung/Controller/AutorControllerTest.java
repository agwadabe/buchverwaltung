package com.atrify.ausbildung.Buchverwaltung.Controller;

import static org.junit.jupiter.api.Assertions.*;

import com.atrify.ausbildung.books_management.models.Author;
import com.atrify.ausbildung.books_management.models.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AutorControllerTest {

  @Test
  void addAuthor() {
    AutorController ac = new AutorController();
    List<Author> authorList = new ArrayList<>();

    Author testAuthor = new Author();
    testAuthor.setFirstname("JK");
    testAuthor.setLastname("Rowling");
    testAuthor.setAuthor(1);

    Author testAuthorZwei = new Author();
    testAuthorZwei.setFirstname("R.R");
    testAuthorZwei.setLastname("Martin");
    testAuthorZwei.setAuthor(2);

    authorList.add(testAuthor);
    ac.addAuthor(testAuthor);

    assertEquals(ac.getAllAuthors().getBody(), authorList);
    ac.addAuthor(testAuthorZwei);
    assertNotEquals(ac.getAllAuthors().getBody(), authorList);

  }

  @Disabled
  void updateAuthor() {
  }

  @Test
  void deleteAuthor() {
    AutorController ac = new AutorController();
    List<Author> authorList = new ArrayList<>();

    Author testAuthor = new Author();
    testAuthor.setFirstname("JK");
    testAuthor.setLastname("Rowling");
    testAuthor.setAuthor(1);

    Author testAuthorZwei = new Author();
    testAuthorZwei.setFirstname("R.R");
    testAuthorZwei.setLastname("Martin");
    testAuthorZwei.setAuthor(2);

    authorList.add(testAuthor);
    ac.addAuthor(testAuthorZwei);

    Integer searchedAuthorId = testAuthorZwei.getAuthor();
    assertEquals(testAuthorZwei ,ac.getAuthorById(testAuthorZwei.getAuthor()).getBody());
    ac.deleteAuthor(searchedAuthorId);
    assertNull(ac.getAuthorById(searchedAuthorId).getBody());
  }

  @Test
  void getAuthorById() {
    AutorController ac = new AutorController();
    Integer authorId = 2;

    Author testAuthorZwei = new Author();
    testAuthorZwei.setFirstname("R.R");
    testAuthorZwei.setLastname("Martin");
    testAuthorZwei.setAuthor(2);

    assertNotNull(ac.getAuthorById(testAuthorZwei.getAuthor()));
    assertEquals(authorId, testAuthorZwei.getAuthor());
  }

  @Test
  void getAllAuthors() {
    AutorController ac = new AutorController();
    Author testAuthorZwei = new Author();
    testAuthorZwei.setFirstname("R.R");
    testAuthorZwei.setLastname("Martin");
    testAuthorZwei.setAuthor(2);

    Author testAuthor = new Author();
    testAuthor.setFirstname("JK");
    testAuthor.setLastname("Rowling");
    testAuthor.setAuthor(1);

    ac.addAuthor(testAuthorZwei);
    ac.addAuthor(testAuthor);

    ResponseEntity<List<Author>> response = ac.getAllAuthors();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<Author> authors = response.getBody();
    assertNotNull(authors);
    assertEquals(2, authors.size());


   // assertNotNull(authors);
  }
}