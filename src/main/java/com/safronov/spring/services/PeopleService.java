package com.safronov.spring.services;

import com.safronov.spring.models.Book_1;
import com.safronov.spring.models.Person_1;
import com.safronov.spring.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public List<Person_1> findAll() {
        return peopleRepository.findAll();
    }

    @Transactional
    public Person_1 findOne(int id) {
        Optional<Person_1> optionalPerson = peopleRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    @Transactional
    public void save(Person_1 person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person_1 updatedPerson) {
        updatedPerson.setId_p(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book_1> getBooksByPersonId(int id) {
        Optional<Person_1> person = peopleRepository.findById(id);

        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBookList());

            person.get().getBookList().forEach(book -> {
                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
//                864000000 мс в 10 сутках
                        if(diffInMillies > 2)
                            book.setExpired(true);
            });

            return person.get().getBookList();
        } else {
            return Collections.emptyList();
        }
    }
}
