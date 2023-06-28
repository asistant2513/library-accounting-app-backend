package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.BookDTO;
import com.havrylenko.library.model.dto.ReviewDTO;
import com.havrylenko.library.model.entity.Review;
import com.havrylenko.library.repository.ReviewRepository;
import com.havrylenko.library.service.BookService;
import com.havrylenko.library.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/books")
public class ReaderBooksController {

    private final BookService bookService;
    private final ReaderService readerService;

    private final ReviewRepository reviewRepository;

    public ReaderBooksController(BookService bookService,
                                 ReaderService readerService,
                                 ReviewRepository reviewRepository) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public String getAll(Model model) {
        var books = bookService.getAll().stream().map(b -> BookDTO.fromBook(b, true)).toList();
        model.addAttribute("books", books);
        model.addAttribute("isReader", true);
        return "books_view";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable String id, Model model) {
        var book = bookService.getOneById(id).get();
        var reviews = book.getReviews();
        var bookDto = BookDTO.fromBook(book, true);
        model.addAttribute("book", bookDto);
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewDto", new ReviewDTO("", (short) 0));
        model.addAttribute("isReader", true);
        return "single_book_view";
    }

    @PostMapping("/{id}/addReview")
    public String addReview(@PathVariable String id,
                            @ModelAttribute("reviewDto") ReviewDTO dto,
                            Principal principal) {
        var book = bookService.getOneById(id).get();
        var reader = readerService.getOneByUsername(principal.getName()).get();
        Review r = new Review();
        r.setReader(reader);
        r.setBook(book);
        r.setRating(dto.rating());
        r.setText(dto.text());
        r = reviewRepository.save(r);
        book.getReviews().add(r);
        bookService.save(book);
        return "redirect:/books/{id}";
    }

    @PostMapping("/{id}/reserve")
    public String reserve(@PathVariable String id,
                          Principal principal) {
        var book = bookService.getOneById(id).get();
        var reader = readerService.getOneByUsername(principal.getName()).get();
        book.setReader(reader);
        book.setTimesBooked(book.getTimesBooked() + 1);
        book.setReservedTill(LocalDate.now().plus(30, ChronoUnit.DAYS));
        book.setReserveApproved(false);
        reader.getBooks().add(book);

        bookService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/return")
    public String returnBack(@PathVariable String id,
                          Principal principal) {
        var book = bookService.getOneById(id).get();
        var reader = readerService.getOneByUsername(principal.getName()).get();
        book.setReturnConfirmed(false);
        book.setUnderReturnApproval(true);
        bookService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/extendReservation")
    public String extendReservation(@PathVariable String id) {
        var book = bookService.getOneById(id).get();
        book.setReservedTill(book.getReservedTill().plus(30, ChronoUnit.DAYS));
        bookService.save(book);
        return "redirect:/books";
    }
}
