package io.bhimsur.movie;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;

@Path("/api/movie")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {
    @Inject
    MovieRepository repository;

    @GET
    public Response findAll() {
        return Response
                .ok(repository.listAll())
                .build();
    }

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id) {
        return repository.findByIdOptional(id)
                .map(movie -> Response.ok(movie).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/query")
    public Response findByTitleLike(@QueryParam("title") String title) {
        return repository.findByTitleLike(title)
                .map(movie -> Response.ok(movie).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response save(Movie movie) {
        repository.persist(movie);
        if (repository.isPersistent(movie)) {
            return Response
                    .ok(Map.ofEntries(Map.entry("path", URI.create("/api/movie/" + movie.getId()))))
                    .status(Response.Status.CREATED)
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        if (repository.deleteById(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
