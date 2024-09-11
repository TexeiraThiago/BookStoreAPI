package com.bookstore.jpa.service;

import com.bookstore.jpa.domain.dto.BookDto;
import com.bookstore.jpa.domain.model.BookModel;
import com.bookstore.jpa.domain.model.ReviewModel;
import com.bookstore.jpa.domain.repository.AuthorRepository;
import com.bookstore.jpa.domain.repository.BookRepository;
import com.bookstore.jpa.domain.repository.PublisherRepository;
import com.bookstore.jpa.service.exception.DeletedException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public BookModel saveBook(BookDto dto) {
        BookModel bookModel = new BookModel();
        bookModel.setTitle(dto.title());
        bookModel.setPublisher(publisherRepository.findById(dto.publisherId()).orElseThrow(NoSuchElementException::new));
        bookModel.setAuthors(authorRepository.findAllById(dto.authorIds()).stream().collect(Collectors.toSet()));

        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setComment(dto.reviewComment());
        reviewModel.setBook(bookModel);
        bookModel.setReview(reviewModel);

        return bookRepository.save(bookModel);
    }

    public List<BookModel> findAll() {
        return bookRepository.findAll();
    }

    public BookModel findOneBook(UUID id) {
        return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public BookModel updateBook(UUID id , BookDto dto) {
        BookModel bookModel = bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
        bookModel.setTitle(dto.title());
        bookModel.setPublisher(publisherRepository.findById(dto.publisherId()).orElseThrow(NoSuchElementException::new));
        bookModel.setAuthors(authorRepository.findAllById(dto.authorIds()).stream().collect(Collectors.toSet()));
        bookModel.getReview().setComment(dto.reviewComment());
        return bookRepository.save(bookModel);
    }

    @Transactional
    public void deleteBook(UUID id) {
        BookModel bookModel = bookRepository.findById(id).orElseThrow(DeletedException::new);
        bookRepository.delete(bookModel);
    }
}
