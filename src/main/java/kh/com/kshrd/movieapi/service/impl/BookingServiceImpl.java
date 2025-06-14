package kh.com.kshrd.movieapi.service.impl;

import kh.com.kshrd.movieapi.dto.request.BookingRequest;
import kh.com.kshrd.movieapi.exception.NotFoundException;
import kh.com.kshrd.movieapi.model.Booking;
import kh.com.kshrd.movieapi.repository.BookingRepository;
import kh.com.kshrd.movieapi.service.AuthService;
import kh.com.kshrd.movieapi.service.BookingService;
import kh.com.kshrd.movieapi.service.SeatService;
import kh.com.kshrd.movieapi.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    public final SeatService seatService;
    private final ShowService showService;
    private final AuthService authService;

    @Override
    public Booking create(BookingRequest request) {
        showService.getById(request.getShowId());
        Long userId = authService.getCurrentUserId();
        Long bookingId = bookingRepository.insertBooking(request, userId);

        for (Long seatId : request.getSeatIds()) {
            seatService.getById(seatId);
            bookingRepository.insertBookingSeat(bookingId, seatId);
        }

        return getById(bookingId);
    }

    @Override
    public Booking getById(Long bookingId) {
        Long userId = authService.getCurrentUserId();
        Booking booking = bookingRepository.getById(bookingId, userId);
        if (booking == null) {
            throw new NotFoundException("Booking not found");
        }
        return booking;
    }

    @Override
    public List<Booking> getAll(Integer page, Integer size) {
        Long userId = authService.getCurrentUserId();
        page = (page - 1) * size;
        return bookingRepository.getAll(page, size, userId);
    }

    @Override
    public void delete(Long bookingId) {
        getById(bookingId);
        Long userId = authService.getCurrentUserId();
        bookingRepository.delete(bookingId, userId);
    }

    @Override
    public Booking update(Long bookingId, BookingRequest request) {
        getById(bookingId);
        showService.getById(request.getShowId());
        Long userId = authService.getCurrentUserId();
        bookingRepository.updateBooking(bookingId, request, userId);
        bookingRepository.deleteBookingSeat(bookingId);

        for (Long seatId : request.getSeatIds()) {
            seatService.getById(seatId);
            bookingRepository.insertBookingSeat(bookingId, seatId);
        }

        return getById(bookingId);
    }
}
