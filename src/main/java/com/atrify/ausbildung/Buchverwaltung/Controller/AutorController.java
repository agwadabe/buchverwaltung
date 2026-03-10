package com.atrify.ausbildung.Buchverwaltung.Controller;

import com.atrify.ausbildung.books_management.api.LibraryApi;
import com.atrify.ausbildung.books_management.models.Author;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class AutorController {

  private final List<Author> autorList = new ArrayList<>();

  @PostMapping("/author")
  public ResponseEntity<Author> addAuthor(@org.springframework.web.bind.annotation.RequestBody Author author) {
    if (!autorList.contains(author)) {
      autorList.add(author);
      return ResponseEntity.ok(author);
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PutMapping("/author")
  public ResponseEntity<Author> updateAuthor(@org.springframework.web.bind.annotation.RequestBody Author author) {
    for (Author existing : autorList) {
      if (existing.getAuthor().equals(author.getAuthor())) {
        if (author.getFirstname() != null) {
          existing.setFirstname(author.getFirstname());
        }
        if (author.getLastname() != null) {
          existing.setLastname(author.getLastname());
        }
        return ResponseEntity.ok(existing);
      }
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/author/{authorId}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable Integer authorId) {
    boolean deleteSuccessful = autorList.removeIf(
        authorDelete -> authorDelete.getAuthor().equals(authorId));
    if (deleteSuccessful) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/author/{authorId}")
  public ResponseEntity<Author> getAuthorById(@PathVariable Integer authorId) {
    for (Author authorupdate : autorList) {
      if (authorupdate.getAuthor().equals(authorId)) {
        return ResponseEntity.ok(authorupdate);
      }
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/api/authors")
  public ResponseEntity<List<Author>> getAllAuthors() {
    return ResponseEntity.ok(autorList);
  }

  public Author getTestAuthor() {
    Author testAuthor = new Author();
    testAuthor.setFirstname("JK");
    testAuthor.setLastname("Rowling");
    testAuthor.setAuthor(1);

    return testAuthor;
  }

  @RequestMapping(value = "/listAuthor", method = RequestMethod.GET)
  public String listAuthor(Model model) {

    model.addAttribute("testAutoren", autorList);

    model.addAttribute("authors", autorList);

    return "listAuthor";

  }


  @RequestMapping(value = "/addAuthor", method = RequestMethod.GET)
  public String addAuthor(Model model) {
    Author author2 = new Author();
    model.addAttribute("author", author2);
    return "addAuthor";
  }

  @RequestMapping(value = "/addAuthor", method = RequestMethod.POST)
  public String saveAuthor(@ModelAttribute(name = "author") Author author) {

    ResponseEntity<Author> aL = addAuthor(author);
    System.out.println(aL);
    return "redirect:/listAuthor";
  }

  @RequestMapping(value = "/delete/{author}", method = RequestMethod.GET)
  public String deleteAuthorFromLibary(@PathVariable Integer author) {
    deleteAuthor(author);
    return "redirect:/listAuthor";
  }


}
