package com.atrify.ausbildung.Buchverwaltung.Controller;

import com.atrify.ausbildung.books_management.api.LibraryApi;
import com.atrify.ausbildung.books_management.models.Author;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.NativeWebRequest;

//@Controller
public class AutorController implements LibraryApi {

  List<Author> autorList = new ArrayList<>();

  @Override
  public ResponseEntity<Author> addAuthor(Author author) {
    if (!autorList.contains(author)) {

      autorList.add(author);
      return new ResponseEntity(autorList, HttpStatus.OK);
    } else {
      return new ResponseEntity(autorList, HttpStatus.CONFLICT);
    }
  }

  @Override
  public ResponseEntity<Author> updateAuthor(Author author) {
    for (Author authorUpdate : autorList) {
      if (authorUpdate.getAuthor().equals(author.getAuthor())) {
        if (authorUpdate.getAuthor() != null) {
          authorUpdate.setAuthor(author.getAuthor());
        }
        if (authorUpdate.getFirstname() != null) {
          authorUpdate.setFirstname(author.getFirstname());
        }
        if (authorUpdate.getLastname() != null) {
          authorUpdate.setLastname(author.getLastname());
        }
        return ResponseEntity.ok((Author) Arrays.asList(authorUpdate));
      }
    }
    return LibraryApi.super.updateAuthor(author);
  }

  @Override
  public ResponseEntity<Void> deleteAuthor(Integer authorId) {
    boolean deleteSuccessful = autorList.removeIf(authorDelete -> authorDelete.getAuthor().equals(authorId));
    if (deleteSuccessful) {
      return new ResponseEntity(autorList, HttpStatus.OK);
    } else {
      return new ResponseEntity(autorList, HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Author> getAuthorById(Integer authorId) {
    return LibraryApi.super.getAuthorById(authorId);
  }

  @Override
  public ResponseEntity<List<Author>> getAllAuthors() {
    return LibraryApi.super.getAllAuthors();
  }

  public Author getTestAuthor() {
    Author testAuthor = new Author();
    testAuthor.setFirstname("JK");
    testAuthor.setLastname("Rowling");
    testAuthor.setAuthor(1);

    return testAuthor;
  }

  @GetMapping("/listAuthor")
  public String listAuthor(Model model){

    autorList.add(getTestAuthor());
    model.addAttribute("testAutoren", getTestAuthor());
    ResponseEntity<List<Author>> allAuthorVar = getAllAuthors();

    model.addAttribute("authors", allAuthorVar);

    return "listAuthor";

  }


  @GetMapping("/addAuthor")
  public String addAuthor(Model model) {
    Author author2 = new Author();
    model.addAttribute("author", author2);
    return "addAuthor";
  }



}