package com.demo.spring.thymeleaf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.spring.thymeleaf.entity.Book;
import com.demo.spring.thymeleaf.repository.BookRepository;

@Controller
public class BookController {

  @Autowired
  private BookRepository bookRepository;

  @GetMapping("/books")
  public String getAll(Model model, @Param("keyword") String keyword) {
    try {
      List<Book> books = new ArrayList<Book>();

      if (keyword == null) {
        bookRepository.findAll().forEach(books::add);
      } else {
        bookRepository.findByTitleContainingIgnoreCase(keyword).forEach(books::add);
        model.addAttribute("keyword", keyword);
      }

      model.addAttribute("books", books);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }

    return "books";
  }

  @GetMapping("/books/new")
  public String addBook(Model model) {
    Book book = new Book();
    book.setPublished(true);

    model.addAttribute("book", book);
    model.addAttribute("pageTitle", "Add Book to Inventory");

    return "book_form";
  }

  @PostMapping("/books/save")
  public String saveBook(Book book, RedirectAttributes redirectAttributes) {
    try {
      bookRepository.save(book);

      redirectAttributes.addFlashAttribute("message", "The Book has been added to the inventory!");
    } catch (Exception e) {
      redirectAttributes.addAttribute("message", e.getMessage());
    }

    return "redirect:/books";
  }

  @GetMapping("/books/{id}")
  public String editBook(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      Book book = bookRepository.findById(id).get();

      model.addAttribute("book", book);
      model.addAttribute("pageTitle", "Editing Book");

      return "book_form";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());

      return "redirect:/books";
    }
  }

  @GetMapping("/books/delete/{id}")
  public String deleteBook(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      bookRepository.deleteById(id);

      redirectAttributes.addFlashAttribute("message", "The Book has been removed from inventory!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/books";
  }

  @GetMapping("/books/{id}/published/{status}")
  public String updateBookPublishedStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean published,
      Model model, RedirectAttributes redirectAttributes) {
    try {
      bookRepository.updatePublishedStatus(id, published);

      String status = published ? "published" : "disabled";
      String message = "The Book has been " + status;

      redirectAttributes.addFlashAttribute("message", message);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/books";
  }
}
