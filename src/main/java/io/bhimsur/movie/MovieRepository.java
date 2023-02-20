package io.bhimsur.movie;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {
    public Optional<Movie> findByTitleLike(String title) {
        return find("title like", '%' + title + '%')
                .firstResultOptional();
    }
}
