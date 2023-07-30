package com.safronov.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person_1")
public class Person_1 {
    @Id
    @Column(name = "id_p")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_p;
    @OneToMany(mappedBy = "person_id")
    private List<Book_1> bookList;
    @Column(name = "full_name")
    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 100, message = "Размер поля ФИО от 2 до 100 символов")
    private String full_name;
    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "Год не должен быть меньше 1900!")
    private int year_of_birth;

    public Person_1() {}

    public Person_1( String full_name, int year_of_birth) {
        this.full_name = full_name;
        this.year_of_birth = year_of_birth;
    }

    public List<Book_1> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book_1> bookList) {
        this.bookList = bookList;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

}
