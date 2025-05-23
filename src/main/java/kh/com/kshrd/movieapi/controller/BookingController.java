package kh.com.kshrd.movieapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import kh.com.kshrd.movieapi.dto.request.BookingRequest;
import kh.com.kshrd.movieapi.dto.response.APIResponse;
import kh.com.kshrd.movieapi.model.Booking;
import kh.com.kshrd.movieapi.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Manage bookings and reserved seats")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new booking with selected seats")
    public ResponseEntity<APIResponse<Booking>> create(@RequestBody @Valid BookingRequest request) {
        Booking booking = bookingService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                APIResponse.<Booking>builder()
                        .message("Booking created successfully")
                        .payload(booking)
                        .status(HttpStatus.CREATED)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all bookings with pagination")
    public ResponseEntity<APIResponse<List<Booking>>> getAll(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        List<Booking> bookings = bookingService.getAll(page, size);
        return ResponseEntity.ok(
                APIResponse.<List<Booking>>builder()
                        .message("Bookings retrieved successfully")
                        .payload(bookings)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @PutMapping("/{booking-id}")
    @Operation(summary = "Update booking and selected seats")
    public ResponseEntity<APIResponse<Booking>> update(
            @PathVariable("booking-id") Long bookingId,
            @RequestBody @Valid BookingRequest request) {
        Booking updated = bookingService.update(bookingId, request);
        return ResponseEntity.ok(
                APIResponse.<Booking>builder()
                        .message("Booking updated successfully")
                        .payload(updated)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }


    @GetMapping("/{booking-id}")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<APIResponse<Booking>> getById(@PathVariable("booking-id") Long bookingId) {
        Booking booking = bookingService.getById(bookingId);
        return ResponseEntity.ok(
                APIResponse.<Booking>builder()
                        .message("Booking retrieved successfully")
                        .payload(booking)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @DeleteMapping("/{booking-id}")
    @Operation(summary = "Delete booking by ID")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable("booking-id") Long bookingId) {
        bookingService.delete(bookingId);
        return ResponseEntity.ok(
                APIResponse.<Void>builder()
                        .message("Booking deleted successfully")
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }
}
