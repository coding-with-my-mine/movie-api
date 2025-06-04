package kh.com.kshrd.movieapi.service.impl;

import kh.com.kshrd.movieapi.dto.request.MovieRequest;
import kh.com.kshrd.movieapi.exception.NotFoundException;
import kh.com.kshrd.movieapi.model.Movie;
import kh.com.kshrd.movieapi.repository.MovieRepository;
import kh.com.kshrd.movieapi.service.AuthService;
import kh.com.kshrd.movieapi.service.CastMemberService;
import kh.com.kshrd.movieapi.service.CategoryService;
import kh.com.kshrd.movieapi.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CategoryService categoryService;
    private final CastMemberService castMemberService;
    private final AuthService authService;

    @Override
    public Movie create(MovieRequest request) {
        getByTitle(request.getTitle());
        categoryService.getById(request.getCategoryId());
        Long movieId = movieRepository.create(request);
        request.getCastMemberIds().forEach(castMemberId -> {
            castMemberService.getById(castMemberId);
            movieRepository.insertMovieCastMember(movieId, castMemberId);
        });
        Long userId = authService.getCurrentUserId();
        return movieRepository.getById(movieId, userId);
    }

    @Override
    public List<Movie> getAll(Integer page, Integer size) {
        Long userId = authService.getCurrentUserId();
        page = (page - 1) * size;
        return movieRepository.getAll(page, size, userId);
    }

    @Override
    public Movie getById(Long movieId) {
        Long userId = authService.getCurrentUserId();
        Movie movie = movieRepository.getById(movieId, userId);
        if (movie == null) {
            throw new NotFoundException("Movie not found");
        }
        return movie;
    }

    @Override
    public Movie update(Long movieId, MovieRequest request) {
        getById(movieId);
        getByTitle(request.getTitle());
        categoryService.getById(request.getCategoryId());
        movieRepository.update(movieId, request);
        movieRepository.deleteMovieCastMember(movieId);
        request.getCastMemberIds().forEach(castMemberId -> {
            castMemberService.getById(castMemberId);
            movieRepository.insertMovieCastMember(movieId, castMemberId);
        });
        Long userId = authService.getCurrentUserId();
        return movieRepository.getById(movieId, userId);
    }

    @Override
    public void delete(Long movieId) {
        getById(movieId);
        movieRepository.delete(movieId);
    }

    @Override
    public Movie markToFavorite(Long movieId) {
        getById(movieId);
        Long userId = authService.getCurrentUserId();
        movieRepository.markToFavorite(movieId, userId);
        return getById(movieId);
    }

    @Override
    public List<Movie> getAllByCategoryId(Long categoryId, Integer page, Integer size) {
        categoryService.getById(categoryId);
        page = (page - 1) * size;
        return movieRepository.getAllByCategoryId(categoryId, page, size);
    }

    @Override
    public List<Movie> getAllByCategoryName(String categoryName, Integer page, Integer size) {
        page = (page - 1) * size;
        return movieRepository.getAllByCategoryName(categoryName, page, size);
    }

    @Override
    public void getByTitle(String title) {
        Movie movie = movieRepository.getByTitle(title);
        if (movie != null) {
            throw new NotFoundException("Movie already exists");
        }
    }

    @Override
    public Movie unmarkToFavorite(Long movieId) {
        getById(movieId);
        Long userId = authService.getCurrentUserId();
        movieRepository.unmarkToFavorite(movieId, userId);
        return getById(movieId);
    }
}
