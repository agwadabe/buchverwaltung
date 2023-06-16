package com.atrify.ausbildung.Buchverwaltung.Controller;

import static org.junit.jupiter.api.Assertions.*;

import com.atrify.ausbildung.books_management.models.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class BuchControllerTest {

  @Test
  void addBook() {
    BuchController bc = new BuchController();
    List<Book> buchList = new ArrayList<>();


    Book testBuch = new Book();
    testBuch.setPublishingYear(2023);
    testBuch.setIsbn("001");
    testBuch.setTitle("Test Buch");
    testBuch.setAuthor(999);

    Book testBuchZwei = new Book();
    testBuchZwei.setPublishingYear(2023);
    testBuchZwei.setIsbn("002");
    testBuchZwei.setTitle("Test Buch");
    testBuchZwei.setAuthor(9990);

    buchList.add(testBuch);
    bc.addBook(testBuch);

    assertEquals(bc.getAllBooksInLibrary().getBody(), buchList);

    bc.addBook(testBuchZwei);
    assertNotEquals(bc.getAllBooksInLibrary().getBody(), buchList);


  }

  @Disabled
  void updateBook() {
  }

  @Test
  void getBookByISBN() {
    BuchController bc = new BuchController();
    String isbn = "12345678901234";
    Book book = new Book();
    book.setIsbn(isbn);
    book.setPublishingYear(2023);
    book.setTitle("Test Buch");
    book.setAuthor(9990);

    assertNotNull(bc.getBookByISBN(book.getIsbn()));
    assertEquals("12345678901234", book.getIsbn());

  }

  @Test
  void deleteBook() {
    BuchController bc = new BuchController();
    List<Book> buchList = new ArrayList<>();

    Book testBuch = new Book();
    testBuch.setPublishingYear(2023);
    testBuch.setIsbn("001");
    testBuch.setTitle("Test Buch");
    testBuch.setAuthor(999);

    Book testBuchZwei = new Book();
    testBuchZwei.setPublishingYear(2023);
    testBuchZwei.setIsbn("002");
    testBuchZwei.setTitle("Test Buch");
    testBuchZwei.setAuthor(9990);

    buchList.add(testBuch);
    buchList.add(testBuchZwei);

    String searchedIsbn = testBuch.getIsbn();
     bc.deleteBook(searchedIsbn);
     assertNull(bc.getBookByISBN(searchedIsbn).getBody());

  }

  @Test
  void getAllBooksInLibrary() {
    BuchController bc = new BuchController();
    Book testBuchZwei = new Book();
    testBuchZwei.setPublishingYear(2023);
    testBuchZwei.setIsbn("002");
    testBuchZwei.setTitle("Test Buch");
    testBuchZwei.setAuthor(9990);
    bc.addBook(testBuchZwei);

    Book testBuch = new Book();
    testBuch.setPublishingYear(2023);
    testBuch.setIsbn("001");
    testBuch.setTitle("Test Buch");
    testBuch.setAuthor(999);
    bc.addBook(testBuch);

    ResponseEntity<List<Book>> response = bc.getAllBooksInLibrary();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<Book> books = response.getBody();
    assertNotNull(books);
    assertEquals(2, books.size());

  }
}