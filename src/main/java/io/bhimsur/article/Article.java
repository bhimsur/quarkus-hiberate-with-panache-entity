package io.bhimsur.article;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article")
public class Article extends PanacheEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "body")
    private String body;
    @Column(name = "author")
    private String author;
    @Column(name = "created_date")
    private Date createdDate;
    public Article(String title, String body, String author, Date createdDate) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.createdDate = createdDate;
    }
    public Article() {

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
