package kh.com.kshrd.movieapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import kh.com.kshrd.movieapi.dto.request.MovieRequest;
import kh.com.kshrd.movieapi.dto.response.APIResponse;
import kh.com.kshrd.movieapi.model.Movie;
import kh.com.kshrd.movieapi.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
@Tag(name = "Movies", description = "Endpoints for managing movies")
@SecurityRequirement(name = "bearerAuth")
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "Create a new movie")
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<APIResponse<Movie>> create(@RequestBody @Valid MovieRequest request) {
        Movie movie = movieService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                APIResponse.<Movie>builder()
                        .message("Movie created successfully")
                        .payload(movie)
                        .status(HttpStatus.CREATED)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @Operation(summary = "Get all movies with pagination")
    @GetMapping
    public ResponseEntity<APIResponse<List<Movie>>> getAll(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<Movie> movies = movieService.getAll(page, size);
        return ResponseEntity.ok(
                APIResponse.<List<Movie>>builder()
                        .message("Movies retrieved successfully")
                        .payload(movies)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @Operation(summary = "Get movie by ID")
    @GetMapping("/{movie-id}")
    public ResponseEntity<APIResponse<Movie>> getById(@PathVariable("movie-id") Long movieId) {
        Movie movie = movieService.getById(movieId);
        return ResponseEntity.ok(
                APIResponse.<Movie>builder()
                        .message("Movie retrieved successfully")
                        .payload(movie)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @Operation(summary = "Update a movie by ID")
    @PutMapping("/{movie-id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<APIResponse<Movie>> update(
            @PathVariable("movie-id") Long movieId,
            @RequestBody @Valid MovieRequest request) {
        Movie updated = movieService.update(movieId, request);
        return ResponseEntity.ok(
                APIResponse.<Movie>builder()
                        .message("Movie updated successfully")
                        .payload(updated)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @Operation(summary = "Delete a movie by ID")
    @DeleteMapping("/{movie-id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable("movie-id") Long movieId) {
        movieService.delete(movieId);
        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.<Void>builder()
                        .message("Movie deleted successfully")
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @Operation(summary = "Mark a movie as favorite")
    @PostMapping("/{movie-id}/favorite")
    public ResponseEntity<APIResponse<Movie>> markToFavorite(
            @PathVariable("movie-id") Long movieId) {
        Movie updated = movieService.markToFavorite(movieId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.<Movie>builder()
                        .message("Favorite marked successfully")
                        .payload(updated)
                        .status(HttpStatus.CREATED)
                        .timestamp(Instant.now())
                        .build());
    }

    @Operation(summary = "Unmark a movie as favorite")
    @DeleteMapping("/{movie-id}/favorite")
    public ResponseEntity<APIResponse<Movie>> unmarkToFavorite(
            @PathVariable("movie-id") Long movieId) {
        Movie updated = movieService.unmarkToFavorite(movieId);
        return ResponseEntity.ok(
                APIResponse.<Movie>builder()
                        .message("Favorite unmarked successfully")
                        .payload(updated)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @Operation(summary = "Get movies by category ID with pagination")
    @GetMapping("/categories/{category-id}")
    public ResponseEntity<APIResponse<List<Movie>>> getAllByCategoryId(
            @PathVariable("category-id") Long categoryId,
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<Movie> movies = movieService.getAllByCategoryId(categoryId, page, size);
        return ResponseEntity.ok(
                APIResponse.<List<Movie>>builder()
                        .message("Movies retrieved by category id successfully")
                        .payload(movies)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @Operation(summary = "Get movies by category name with pagination")
    @GetMapping("/categories")
    public ResponseEntity<APIResponse<List<Movie>>> getAllByCategoryName(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<Movie> movies = movieService.getAllByCategoryName(categoryName, page, size);
        return ResponseEntity.ok(
                APIResponse.<List<Movie>>builder()
                        .message("Movies retrieved by category name successfully")
                        .payload(movies)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

}
