package kh.com.kshrd.movieapi.service;

import kh.com.kshrd.movieapi.dto.request.SeatRequest;
import kh.com.kshrd.movieapi.model.Seat;

import java.util.List;

public interface SeatService {
    Seat create(SeatRequest request);
    List<Seat> getAll(Integer page, Integer size);
    Seat getById(Long id);
    Seat update(Long id, SeatRequest request);
    void delete(Long id);
}
