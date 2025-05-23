package kh.com.kshrd.movieapi.service;

import kh.com.kshrd.movieapi.dto.request.ShowRequest;
import kh.com.kshrd.movieapi.model.Show;

import java.util.List;

public interface ShowService {
    Show create(ShowRequest request);

    List<Show> getAll(Integer page, Integer size);

    Show getById(Long showId);

    Show update(Long showId, ShowRequest request);

    void delete(Long showId);

    List<Show> getByMovieId(Long movieId);
}
