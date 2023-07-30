package com.safronov.spring.services;

import com.safronov.spring.models.Book_1;
import com.safronov.spring.models.Person_1;
import com.safronov.spring.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book_1> findAll(boolean sortByYear) {
        if(sortByYear) {
            return booksRepository.findAll(Sort.by("year"));
        } else {
            return booksRepository.findAll();
        }
    }

    public List<Book_1> findWithPagination(Integer page, Integer bookPerPage, boolean sortByYear) {
        if(sortByYear) {
            return booksRepository.findAll(PageRequest.of(page, bookPerPage, Sort.by("year"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page, bookPerPage)).getContent();
        }
    }

    @Transactional
    public Book_1 findOne(int id) {
        Optional<Book_1> optionalPerson = booksRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    @Transactional
    public void save(Book_1 book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book_1 updatedBook) {
        Book_1 bookUpdated = booksRepository.findById(id).get();
        updatedBook.setId_b(id);
        updatedBook.setPerson_id(bookUpdated.getPerson_id());

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setPerson_id(null);
            book.setTakenAt(null);
        });
    }

    @Transactional
    public Optional<Person_1> getBooksOwner(int id) {
         Optional<Book_1> book = booksRepository.findById(id);
         Person_1 person = book.get().getPerson_id();

        return Optional.ofNullable(person);
    }

    @Transactional
    public void assign(int id, Person_1 selectedPerson) {
            booksRepository.findById(id).ifPresent(book -> {
                book.setPerson_id(selectedPerson);
                book.setTakenAt(new Date());
            });
    }

    @Transactional
    public List<Book_1> findBookByTitle(String searchWord) {
        return booksRepository.findByTitleStartingWith(searchWord);
    }
}
