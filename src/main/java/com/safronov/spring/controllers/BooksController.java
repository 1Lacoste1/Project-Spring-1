package com.safronov.spring.controllers;

import com.safronov.spring.models.Book_1;
import com.safronov.spring.models.Person_1;
import com.safronov.spring.services.BooksService;
import com.safronov.spring.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer books_per_page,
                        @RequestParam(value = "sort_by_year", required = false) boolean sort_by_year,
                        Model model) {
        if(page == null || books_per_page == null) {
            model.addAttribute("books", booksService.findAll(sort_by_year));
        } else {
            model.addAttribute("books", booksService.findWithPagination(page, books_per_page, sort_by_year));
        }

        return "books/index_book";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person_1") Person_1 person_1 ) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("person_1");
        Optional<Person_1> ownerBook = booksService.getBooksOwner(id);

        if(ownerBook.isPresent()) {
            model.addAttribute("owner", ownerBook.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }

        return "books/show_book";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book_1 book) {
        return "books/new_book";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book_1 book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/books/new_book";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit_book";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("books") @Valid Book_1 book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "/books/edit_book";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person_1 selectedPerson,@PathVariable("id") int id) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String formSearch() {
        return "/books/search_book";
    }

    @PostMapping("/makeSearch")
    public String makeSearch(Model model, @RequestParam("searchWord") String searchWord) {
        model.addAttribute("books", booksService.findBookByTitle(searchWord));
        return "/books/search_book";
    }

}
