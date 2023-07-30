package com.safronov.spring.repositories;

import com.safronov.spring.models.Person_1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person_1, Integer> {

}
