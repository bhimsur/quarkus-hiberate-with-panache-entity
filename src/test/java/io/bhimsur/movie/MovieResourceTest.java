package io.bhimsur.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import org.apache.http.HttpHeaders;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@QuarkusTest
@TestHTTPEndpoint(MovieResource.class)
class MovieResourceTest {

    @InjectMock
    MovieRepository repository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, String> httpHeaders = Map.ofEntries(
            Map.entry(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON),
            Map.entry(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
    );

    @Test
    void findAll() {
        Mockito.when(repository.listAll())
                .thenReturn(List.of(new Movie(1L, "Ant-Man", "United States of America", new Date())));
        RestAssured.given()
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.is(1));
    }

    @Test
    void findById() {
        Mockito.when(repository.findByIdOptional(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());
        RestAssured.given()
                .get("1")
                .then()
                .statusCode(404);
    }

    @Test
    void findByTitleLike() {
        Mockito.when(repository.findByTitleLike(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        RestAssured.given()
                .queryParam("title", "Hulk")
                .get("/query")
                .then()
                .statusCode(404);
    }

    @Test
    void save() throws JsonProcessingException {
        Mockito.doNothing().when(repository).persist(ArgumentMatchers.any(Movie.class));
        Mockito.when(repository.isPersistent(ArgumentMatchers.any(Movie.class)))
                .thenReturn(true);
        RestAssured.given()
                .headers(httpHeaders)
                .body(objectMapper.writeValueAsString(new Movie(1L, "Captain America", "USA", new Date())))
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("path", CoreMatchers.containsString("/api/movie"));
    }

    @Test
    void delete() {
        Mockito.when(repository.deleteById(ArgumentMatchers.anyLong()))
                .thenReturn(true);
        RestAssured.given()
                .delete("1")
                .then()
                .statusCode(204);
    }
}