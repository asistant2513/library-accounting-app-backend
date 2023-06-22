package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.BookDTO;
import com.havrylenko.library.model.entity.Book;
import com.havrylenko.library.service.AuthorService;
import com.havrylenko.library.service.BookService;
import com.havrylenko.library.service.GenreService;
import com.havrylenko.library.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Controller
@RequestMapping("/admin/books")
public class AdminBooksController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public AdminBooksController(BookService bookService,
                                GenreService genreService,
                                AuthorService authorService,
                                PublisherService publisherService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getAll(Model model, @RequestParam(required = false) String readerId) {
        List<BookDTO> books;
        if(readerId != null) {
            books = bookService.getBooksByReaderId(readerId).stream().map(BookDTO::fromBook).toList();
        } else {
            books = bookService.getAll().stream().map(BookDTO::fromBook).toList();
        }

        model.addAttribute("books", books);
        return "/admin/book/admin_books";
    }

    @GetMapping("/{id}")
    public String getOne(Model model, @PathVariable String id) {
        var book = bookService.getOneById(id).get();
        model.addAttribute("book", BookDTO.fromBook(book, true));
        return "/admin/book/admin_single_book";
    }

    @GetMapping("/add")
    public String add(Model model) {
        var genres = genreService.getAll();
        var authors = authorService.getAll();
        var publishers = publisherService.getAll();
        model.addAttribute("book", BookDTO.getInstance());
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);
        return "/admin/book/admin_add_book";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("book") BookDTO dto) {
        var genre = genreService.getOneById(dto.genreId()).get();
        var author = authorService.getOneById(dto.authorId()).get();
        var publisher = publisherService.getOneById(dto.publisherId()).get();

        Book book = new Book();
        book.setName(dto.name());
        book.setAuthor(author);
        book.setPublishingYear(Year.of(dto.year()));
        book.setPages(dto.pages());
        book.setPublisher(publisher);
        book.setGenre(genre);
        book.setDescription(dto.description());
        book.setDateRegistered(LocalDateTime.now());

        book = bookService.save(book);

        return String.format("redirect:/admin/books/%s", book.getId());
    }

    @GetMapping("/{id}/edit")
    public String getEdit(Model model, @PathVariable String id) {
        var genres = genreService.getAll();
        var authors = authorService.getAll();
        var publishers = publisherService.getAll();
        var book = bookService.getOneById(id).get();

        model.addAttribute("book", BookDTO.fromBook(book, true));
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);
        return "/admin/book/admin_edit_book";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable String id, @ModelAttribute("book") BookDTO dto) {
        var genre = genreService.getOneById(dto.genreId()).get();
        var author = authorService.getOneById(dto.authorId()).get();
        var publisher = publisherService.getOneById(dto.publisherId()).get();
        var book = bookService.getOneById(id).get();

        book.setName(dto.name());
        book.setAuthor(author);
        book.setPublishingYear(Year.of(dto.year()));
        book.setPages(dto.pages());
        book.setPublisher(publisher);
        book.setGenre(genre);
        book.setDescription(dto.description());

        bookService.save(book);

        return "redirect:/admin/books/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        bookService.deleteById(id);
        return "redirect:/admin/books";
    }

    @PostMapping("/addToArchive")
    public String addToArchive(@RequestParam String bookId) {
        var book = bookService.getOneById(bookId).get();
        book.setInArchive(!book.isInArchive());
        bookService.save(book);
        return "redirect:/admin/books";
    }
}
