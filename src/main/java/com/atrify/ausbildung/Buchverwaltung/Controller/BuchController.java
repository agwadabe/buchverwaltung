package com.atrify.ausbildung.Buchverwaltung.Controller;

import com.atrify.ausbildung.books_management.models.Book;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest; 


@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class BuchController {



  @GetMapping("/listBook")
  public String listBook(Model model) {
    model.addAttribute("testBuecher", buchList);
    model.addAttribute("books", buchList);
    return "listBook";
  }

  @RequestMapping(value = "/addBook", method = RequestMethod.GET)
  public String addBook(Model model) {
    Book book2 = new Book();
    model.addAttribute("book", book2);
    return "addBook";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String saveBook(@ModelAttribute(name = "book") Book book) {
    addBook(book); 
    return "redirect:/listBook";
  }

  @RequestMapping(value = "/delete/{isbn}", method = RequestMethod.GET)
  public String deleteBookFromLibary(@PathVariable String isbn) {
    deleteBook(isbn);
    return "redirect:/listBook";
  }

  @RequestMapping(value = "/edit/{isbn}", method = RequestMethod.GET)
  public String editBookFromLibary(@PathVariable String isbn, Model model) {
    ResponseEntity<Book> book = getBookByISBN(isbn);
    Book bookBody = book.getBody();
    model.addAttribute("book", bookBody);
    return "editBook";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public String editBook(@ModelAttribute(name = "book") Book bookVar) {
    updateBook(bookVar);
    return "redirect:/listBook";
  }



  private final List<Book> buchList = new ArrayList<>();

  public BuchController() {
    buchList.add(getTestBook());
    buchList.add(getTestBookZwei());
  }


  public void clearLibrary() {
    buchList.clear();
  }

  @PostMapping("/book")
  public ResponseEntity<Book> addBook(@org.springframework.web.bind.annotation.RequestBody Book book) {
    if (!buchList.contains(book)) {
      buchList.add(book);
      return ResponseEntity.ok(book);
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
  @PutMapping("/book")
  public ResponseEntity<Book> updateBook(@org.springframework.web.bind.annotation.RequestBody Book book) {
    for (Book existing : buchList) {
      if (existing.getIsbn().equals(book.getIsbn())) {
        
        if (book.getIsbn() != null) {
          existing.setIsbn(book.getIsbn());
        }
        if (book.getTitle() != null) {
          existing.setTitle(book.getTitle());
        }
        if (book.getAuthor() != null) {
          existing.setAuthor(book.getAuthor());
        }
        if (book.getPublishingYear() != null) {
          existing.setPublishingYear(book.getPublishingYear());
        }
        return ResponseEntity.ok(existing);
      }
    }
    return ResponseEntity.notFound().build();
  }
  @GetMapping("/book/{isbn}")
  public ResponseEntity<Book> getBookByISBN(@PathVariable String isbn) {
    for (Book buchupdate : buchList) {
      if (buchupdate.getIsbn().equals(isbn)) {
        return ResponseEntity.ok(buchupdate);
      }
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/book/{isbn}")
  public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
    boolean deleteSuccessful = buchList.removeIf(buchDelete -> buchDelete.getIsbn().equals(isbn));
    if (deleteSuccessful) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/api/books")
  public ResponseEntity<List<Book>> getAllBooksInLibrary() {
    return ResponseEntity.ok(buchList);
  }

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("books", buchList);
    return "listBook";
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
    testBuch.setIsbn("002");              
    testBuch.setTitle("Test Buch");
    testBuch.setAuthor(9990);

    return testBuch;
  }





}
