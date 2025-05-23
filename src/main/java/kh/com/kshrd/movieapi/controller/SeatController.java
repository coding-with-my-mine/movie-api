package kh.com.kshrd.movieapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import kh.com.kshrd.movieapi.dto.request.SeatRequest;
import kh.com.kshrd.movieapi.dto.response.APIResponse;
import kh.com.kshrd.movieapi.model.Seat;
import kh.com.kshrd.movieapi.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
@Tag(name = "Seats", description = "Manage seats for movies")
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    @Operation(summary = "Create a seat")
    public ResponseEntity<APIResponse<Seat>> create(@RequestBody @Valid SeatRequest request) {
        Seat seat = seatService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                APIResponse.<Seat>builder()
                        .message("Seat created successfully")
                        .payload(seat)
                        .status(HttpStatus.CREATED)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all seats with pagination")
    public ResponseEntity<APIResponse<List<Seat>>> getAll(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<Seat> seats = seatService.getAll(page, size);
        return ResponseEntity.ok(
                APIResponse.<List<Seat>>builder()
                        .message("Seats retrieved successfully")
                        .payload(seats)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping("/{seat-id}")
    @Operation(summary = "Get seat by ID")
    public ResponseEntity<APIResponse<Seat>> getById(@PathVariable("seat-id") Long seatId) {
        Seat seat = seatService.getById(seatId);
        return ResponseEntity.ok(
                APIResponse.<Seat>builder()
                        .message("Seat retrieved successfully")
                        .payload(seat)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @PutMapping("/{seat-id}")
    @Operation(summary = "Update seat by ID")
    public ResponseEntity<APIResponse<Seat>> update(@PathVariable("seat-id") Long seatId, @RequestBody @Valid SeatRequest request) {
        Seat updated = seatService.update(seatId, request);
        return ResponseEntity.ok(
                APIResponse.<Seat>builder()
                        .message("Seat updated successfully")
                        .payload(updated)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @DeleteMapping("/{seat-id}")
    @Operation(summary = "Delete seat by ID")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable("seat-id") Long seatId) {
        seatService.delete(seatId);
        return ResponseEntity.ok(
                APIResponse.<Void>builder()
                        .message("Seat deleted successfully")
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }
}
