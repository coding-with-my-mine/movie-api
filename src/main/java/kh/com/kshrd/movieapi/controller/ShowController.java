package kh.com.kshrd.movieapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import kh.com.kshrd.movieapi.dto.request.ShowRequest;
import kh.com.kshrd.movieapi.dto.response.APIResponse;
import kh.com.kshrd.movieapi.model.Show;
import kh.com.kshrd.movieapi.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
@Tag(name = "Shows", description = "Manage movie show time")
public class ShowController {

    private final ShowService showService;

    @PostMapping
    @Operation(summary = "Create a new show")
    public ResponseEntity<APIResponse<Show>> create(@RequestBody @Valid ShowRequest request) {
        Show show = showService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                APIResponse.<Show>builder()
                        .message("Show created successfully")
                        .payload(show)
                        .status(HttpStatus.CREATED)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all shows with pagination")
    public ResponseEntity<APIResponse<List<Show>>> getAll(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<Show> shows = showService.getAll(page, size);
        return ResponseEntity.ok(
                APIResponse.<List<Show>>builder()
                        .message("Shows retrieved successfully")
                        .payload(shows)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping("/{show-id}")
    @Operation(summary = "Get show by ID")
    public ResponseEntity<APIResponse<Show>> getById(@PathVariable("show-id") Long showId) {
        Show show = showService.getById(showId);
        return ResponseEntity.ok(
                APIResponse.<Show>builder()
                        .message("Show retrieved successfully")
                        .payload(show)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @PutMapping("/{show-id}")
    @Operation(summary = "Update a show by ID")
    public ResponseEntity<APIResponse<Show>> update(
            @PathVariable("show-id") Long showId,
            @RequestBody @Valid ShowRequest request) {
        Show updated = showService.update(showId, request);
        return ResponseEntity.ok(
                APIResponse.<Show>builder()
                        .message("Show updated successfully")
                        .payload(updated)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @DeleteMapping("/{show-id}")
    @Operation(summary = "Delete a show by ID")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable("show-id") Long showId) {
        showService.delete(showId);
        return ResponseEntity.ok(
                APIResponse.<Void>builder()
                        .message("Show deleted successfully")
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping("/movies/{movie-id}")
    @Operation(summary = "Get all shows by movie ID")
    public ResponseEntity<APIResponse<List<Show>>> getByMovieId(@PathVariable("movie-id") Long movieId) {
        List<Show> shows = showService.getByMovieId(movieId);
        return ResponseEntity.ok(
                APIResponse.<List<Show>>builder()
                        .message("Shows retrieved successfully by movie")
                        .payload(shows)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

}
