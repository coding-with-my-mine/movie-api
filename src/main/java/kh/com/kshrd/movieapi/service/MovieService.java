package kh.com.kshrd.movieapi.service;

import jakarta.validation.constraints.Positive;
import kh.com.kshrd.movieapi.dto.request.MovieRequest;
import kh.com.kshrd.movieapi.model.Movie;

import java.util.List;

public interface MovieService {
    Movie create(MovieRequest request);

    List<Movie> getAll(Integer page, Integer size);

    Movie getById(Long movieId);

    Movie update(Long movieId, MovieRequest request);

    void delete(Long movieId);

    Movie updateFavoriteStatus(Long movieId, Boolean status);

    List<Movie> getAllByCategoryId(Long categoryId, Integer page, Integer size);

    List<Movie> getAllByCategoryName(String categoryName, Integer page, Integer size);

    void getByTitle(String title);
}
