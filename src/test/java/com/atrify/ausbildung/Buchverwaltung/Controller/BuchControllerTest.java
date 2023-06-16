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

  @Test
  void updateBook() {
    BuchController bc = new BuchController();

    Book existingBook = new Book();
    existingBook.setPublishingYear(2023);
    existingBook.setIsbn("001");
    existingBook.setTitle("Test Buch");
    existingBook.setAuthor(999);
    bc.addBook(existingBook);

    Book updatedBook = new Book();

    updatedBook.setPublishingYear(2000);
    updatedBook.setIsbn("001");
    updatedBook.setTitle("Test Buch Neu");
    updatedBook.setAuthor(999);

    ResponseEntity<Book> response = bc.updateBook(updatedBook);

    if (response.getStatusCode() == HttpStatus.OK) {
      Book updatedBookResponse = response.getBody();
      assertNotNull(updatedBookResponse);
      assertEquals(updatedBook.getIsbn(), updatedBookResponse.getIsbn());
      assertEquals(updatedBook.getTitle(), updatedBookResponse.getTitle());
      assertEquals(updatedBook.getAuthor(), updatedBookResponse.getAuthor());
      assertEquals(updatedBook.getPublishingYear(), updatedBookResponse.getPublishingYear());
    } else {
      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
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