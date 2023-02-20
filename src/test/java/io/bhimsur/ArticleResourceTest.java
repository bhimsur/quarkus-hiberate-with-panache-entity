package io.bhimsur;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bhimsur.article.Article;
import io.bhimsur.article.ArticleResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(ArticleResource.class)
class ArticleResourceTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findAll() {
        given()
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.is(2));
    }

    @Test
    void findById() {
        given()
                .get("1")
                .then()
                .statusCode(200)
                .body("id", CoreMatchers.is(1));
    }

    @Test
    void findByTitleLike() {
        given()
                .queryParam("title", "Quarkus")
                .when()
                .get("/query")
                .then()
                .statusCode(200)
                .body("body", CoreMatchers.is("Get Started"));

    }

    @Test
    void save() throws JsonProcessingException {
        given()
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Accept", "application/json"))
                .body(objectMapper.writeValueAsString(new Article("Getting Started", "Example Tutorial", "admin", new Date())))
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("path", CoreMatchers.containsString("/api/article/"));
    }

    @Test
    void delete() {
    }
}