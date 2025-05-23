package kh.com.kshrd.movieapi.service.impl;

import kh.com.kshrd.movieapi.dto.request.ShowRequest;
import kh.com.kshrd.movieapi.exception.NotFoundException;
import kh.com.kshrd.movieapi.model.Show;
import kh.com.kshrd.movieapi.repository.ShowRepository;
import kh.com.kshrd.movieapi.service.MovieService;
import kh.com.kshrd.movieapi.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final MovieService  movieService;

    @Override
    public Show create(ShowRequest request) {
        movieService.getById(request.getMovieId());
        return showRepository.create(request);
    }

    @Override
    public List<Show> getAll(Integer page, Integer size) {
        page = (page - 1) * size;
        return showRepository.getAll(page, size);
    }

    @Override
    public Show getById(Long showId) {
        Show show = showRepository.getById(showId);
        if (show == null) {
            throw new NotFoundException("Show not found");
        }
        return show;
    }

    @Override
    public Show update(Long showId, ShowRequest request) {
        getById(showId);
        movieService.getById(request.getMovieId());
        return showRepository.update(showId, request);
    }

    @Override
    public void delete(Long showId) {
        getById(showId);
        showRepository.delete(showId);
    }

    @Override
    public List<Show> getByMovieId(Long movieId) {
        movieService.getById(movieId);
        return showRepository.getByMovieId(movieId);
    }

}
