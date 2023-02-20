package io.bhimsur.movie;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "country")
    private String country;
    @Column(name = "release")
    private LocalDate release;

    public Movie() {
    }

    public Movie(Long id, String title, String country, LocalDate release) {
        this.id = id;
        this.title = title;
        this.country = country;
        this.release = release;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getRelease() {
        return release;
    }

    public void setRelease(LocalDate release) {
        this.release = release;
    }
}
