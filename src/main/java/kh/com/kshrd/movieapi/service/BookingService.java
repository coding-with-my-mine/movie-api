package kh.com.kshrd.movieapi.service;

import jakarta.validation.Valid;
import kh.com.kshrd.movieapi.dto.request.BookingRequest;
import kh.com.kshrd.movieapi.model.Booking;

import java.util.List;

public interface BookingService {
    Booking create(BookingRequest request);

    Booking getById(Long id);

    List<Booking> getAll(Integer page, Integer size);

    void delete(Long id);

    Booking update(Long bookingId, BookingRequest request);
}
