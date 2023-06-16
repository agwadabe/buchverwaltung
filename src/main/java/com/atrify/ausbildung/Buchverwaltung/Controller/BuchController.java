package com.atrify.ausbildung.Buchverwaltung.Controller;

import com.atrify.ausbildung.books_management.api.LibraryApi;
import com.atrify.ausbildung.books_management.models.Author;
import com.atrify.ausbildung.books_management.models.Book;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.web.servlet.ModelAndView;
import scala.xml.Null;

@Controller
public class BuchController implements LibraryApi {


  List<Book> buchList = new ArrayList<>();


  @Override
  public ResponseEntity<Book> addBook(Book book) {
    if (!buchList.contains(book)) {

      buchList.add(book);
      return new ResponseEntity(buchList, HttpStatus.OK);
    } else {
      return new ResponseEntity(buchList, HttpStatus.CONFLICT);
    }

  }

  @Override
  public ResponseEntity<Book> updateBook(Book book) {

    for (Book buchpdate : buchList) {
      if (buchpdate.getIsbn().equals(book.getIsbn())) {
        if (buchpdate.getIsbn() != null) {
          buchpdate.setIsbn(book.getIsbn());
        }
        if (buchpdate.getTitle() != null) {
          buchpdate.setTitle(book.getTitle());
        }
        if (buchpdate.getAuthor() != null) {
          buchpdate.setAuthor(book.getAuthor());
        }
        if (buchpdate.getPublishingYear() != null) {
          buchpdate.setPublishingYear(book.getPublishingYear());
        }
        return ResponseEntity.ok (buchpdate);
      }
    }
    ResponseEntity<Book> bookVar = LibraryApi.super.updateBook(book);
    return bookVar;
  }

  @Override
  public ResponseEntity<Book> getBookByISBN(String isbn) {
    for (Book buchupdate : buchList) {
      if (buchupdate.getIsbn().equals(isbn)) {
        return new ResponseEntity(buchupdate, HttpStatus.OK);
      }

      }
    return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }


  @Override
  public ResponseEntity<Void> deleteBook(String isbn) {
    boolean deleteSuccessful = buchList.removeIf(buchDelete -> buchDelete.getIsbn().equals(isbn));
    if (deleteSuccessful) {
      return new ResponseEntity(buchList, HttpStatus.OK);
    } else {
      return new ResponseEntity(buchList, HttpStatus.NOT_FOUND);
    }
  }


  @Override
  public ResponseEntity<List<Book>> getAllBooksInLibrary() {
    if (!buchList.isEmpty()) {
      return new ResponseEntity(buchList, HttpStatus.OK);
    }
    return new ResponseEntity(buchList, HttpStatus.CONFLICT);
  }

  public Book getTestBook() {
    Book testBuch = new Book();
    testBuch.setPublishingYear(2023);
    testBuch.setIsbn("001");
    testBuch.setTitle("Test Buch");
    testBuch.setAuthor(999);

    return testBuch;
  }

  public Book getTestBookZwei() {
    Book testBuch = new Book();
    testBuch.setPublishingYear(2023);
    testBuch.setIsbn("001");
    testBuch.setTitle("Test Buch");
    testBuch.setAuthor(9990);

    return testBuch;
  }


  @GetMapping("/listBook")
  public String listBook(Model model) {

    model.addAttribute("testBuecher", buchList);
    //  ResponseEntity<List<Book>> allBooksVar = getAllBooksInLibrary();

    model.addAttribute("books", buchList);

    return "listBook";

  }


  @GetMapping("/addBook")
  public String addBook(Model model) {
    Book book2 = new Book();
    model.addAttribute("book", book2);
    return "addBook";
  }


  @PostMapping("/add")
  public String saveBook(@ModelAttribute(name = "book") Book book) {

    ResponseEntity<Book> bL = addBook(book);
    System.out.println(bL);
    return "redirect:/listBook";
  }

  @RequestMapping(value = "/delete/{isbn}", method = RequestMethod.GET)
  public String deleteBookFromLibary(@PathVariable String isbn) {
    deleteBook(isbn);
    return "redirect:/listBook";
  }
  @RequestMapping(value = "/edit/{isbn}", method = RequestMethod.GET)
  public String editBookFromLibary(@PathVariable String isbn, Model model) {
    System.out.println("ISBN:" + isbn + "Hier"+isbn.getClass()+"Typ");
    ResponseEntity<Book> book = getBookByISBN(isbn);
    Book bookBody = book.getBody();
    model.addAttribute("book", bookBody);
    return "editBook";
  }
 @RequestMapping(value = "/editBook", method = RequestMethod.POST)
  public String editBook(@ModelAttribute(name= "book") Book bookVar) {
    //ResponseEntity<Book> bL = updateBook(getBookByISBN(isbn).getBody());
    //System.out.println(bL);
   ResponseEntity<Book> book = getBookByISBN(bookVar.getIsbn());
   Book bookBody = book.getBody();
   updateBook(bookBody);
    return "redirect:/listBook";
  }


  @Override
  public Optional<NativeWebRequest> getRequest() {
    return LibraryApi.super.getRequest();
  }


}
