package com.safronov.spring.repositories;

import com.safronov.spring.models.Book_1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book_1, Integer> {
    List<Book_1> findByTitleStartingWith(String searchWord);
}
