package com.bookstore.jpa.controller;

import com.bookstore.jpa.domain.dto.BookDto;
import com.bookstore.jpa.domain.model.BookModel;
import com.bookstore.jpa.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookstore/books")
@CrossOrigin(maxAge = 3600, origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookModel>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookModel> findOneBook(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(bookService.findOneBook(id));
    }

    @PostMapping
    public ResponseEntity<BookModel> saveBook(@RequestBody @Valid BookDto bookDto) {
        BookModel bookModel = bookService.saveBook(bookDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookModel.getId()).toUri();
        return ResponseEntity.created(uri).body(bookModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "id") UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted Successfully");
    }

}
