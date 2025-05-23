package kh.com.kshrd.movieapi.service.impl;

import kh.com.kshrd.movieapi.dto.request.SeatRequest;
import kh.com.kshrd.movieapi.exception.NotFoundException;
import kh.com.kshrd.movieapi.model.Seat;
import kh.com.kshrd.movieapi.repository.SeatRepository;
import kh.com.kshrd.movieapi.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    public Seat create(SeatRequest request) {
        return seatRepository.create(request);
    }

    @Override
    public List<Seat> getAll(Integer page, Integer size) {
        page = (page - 1) * size;
        return seatRepository.getAll(page, size);
    }

    @Override
    public Seat getById(Long seatId) {
        Seat seat = seatRepository.getById(seatId);
        if (seat == null) {
            throw new NotFoundException("Seat not found");
        }
        return seat;
    }

    @Override
    public Seat update(Long seatId, SeatRequest request) {
        getById(seatId);
        return seatRepository.update(seatId, request);
    }

    @Override
    public void delete(Long seatId) {
        getById(seatId);
        seatRepository.delete(seatId);
    }
}
