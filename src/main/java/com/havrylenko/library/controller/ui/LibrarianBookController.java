package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.BookDTO;
import com.havrylenko.library.model.dto.ReviewDTO;
import com.havrylenko.library.model.entity.Book;
import com.havrylenko.library.repository.ReviewRepository;
import com.havrylenko.library.service.BookService;
import com.havrylenko.library.service.ReaderService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Comparator;

@Controller
@RequestMapping("/library/books")
public class LibrarianBookController {
    private final BookService bookService;
    private final ReaderService readerService;
    private final ReviewRepository reviewRepository;

    public LibrarianBookController(BookService bookService, ReaderService readerService, ReviewRepository reviewRepository) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) boolean mostPopular,
                         @RequestParam(required = false) String readerId,
                         @RequestParam(required = false) boolean expired) {
        var booksList = bookService.getAll();
        if(Strings.isNotBlank(readerId)) {
            booksList = booksList.stream()
                    .filter(b -> {
                        var reader = b.getReader();
                        return reader != null && reader.getUserId().equals(readerId);
                    })
                    .toList();
        }
        if (mostPopular) {
            booksList = booksList.stream()
                    .sorted(Comparator.comparingInt(Book::getTimesBooked))
                    .limit(20)
                    .toList();
        }
        if (expired) {
            booksList = booksList.stream()
                    .filter(b -> b.getReservedTill() == null ? false : b.getReservedTill().isAfter(LocalDate.now()))
                    .toList();
        }
        var books = booksList.stream().map(b -> BookDTO.fromBook(b, true)).toList();
        model.addAttribute("books", books);
        model.addAttribute("isReader", false);
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
        model.addAttribute("isReader", false);
        return "single_book_view";
    }
}
