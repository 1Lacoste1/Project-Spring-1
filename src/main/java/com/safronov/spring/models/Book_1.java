package com.safronov.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book_1")
public class Book_1 {
    @Id
    @Column(name = "id_b")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_b;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id_p")
    private Person_1 person_id;
    @NotEmpty(message = "Название книги не должно быть пустым.")
    @Size(min = 2, max = 90, message = "Название книги должно быть от 2 до 90 символов")
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Автор не должен быть пустым")
    @Size(min = 2, max = 90, message = "Имя автора должно быть от 2 до 90 символов")
    @Column(name = "author")
    private String author;
    @Min(value = 1500, message = "Минимальный год 1500")
    @Column(name = "year")
    private int year;
    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;
    @Transient
    private boolean expired;

    public Book_1() {}

    public Book_1(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Person_1 getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Person_1 person_id) {
        this.person_id = person_id;
    }

    public int getId_b() {
        return id_b;
    }

    public void setId_b(int id_b) {
        this.id_b = id_b;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
