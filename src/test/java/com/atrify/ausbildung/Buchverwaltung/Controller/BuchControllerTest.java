package com.atrify.ausbildung.Buchverwaltung.Controller;

import static org.junit.jupiter.api.Assertions.*;

import com.atrify.ausbildung.books_management.models.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
    Book book = new Book();
    List<Book> books = (List<Book>) bc.getAllBooksInLibrary();
    assertNotNull(books);
  }
}