package kh.com.kshrd.movieapi.repository;

import kh.com.kshrd.movieapi.dto.request.SeatRequest;
import kh.com.kshrd.movieapi.model.Seat;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeatRepository {

    @Results(id = "seatResult", value = {
            @Result(property = "seatId", column = "seat_id"),
    })
    @Select("""
                INSERT INTO seats (row, number)
                VALUES (#{request.row}, #{request.number})
                RETURNING *
            """)
    Seat create(@Param("request") SeatRequest request);

    @ResultMap("seatResult")
    @Select("""
                SELECT * FROM seats
                OFFSET #{page} LIMIT #{size}
            """)
    List<Seat> getAll(Integer page, Integer size);

    @ResultMap("seatResult")
    @Select("""
                SELECT * FROM seats
                WHERE seat_id = #{seatId}
            """)
    Seat getById(Long seatId);

    @ResultMap("seatResult")
    @Select("""
                UPDATE seats
                SET row = #{request.row},
                    number = #{request.number}
                WHERE seat_id = #{seatId}
                RETURNING *
            """)
    Seat update(Long seatId, @Param("request") SeatRequest request);

    @Delete("""
                DELETE FROM seats
                WHERE seat_id = #{seatId}
            """)
    void delete(Long seatId);

    @ResultMap("seatResult")
    @Select("""
                SELECT * FROM booking_seat bs
                INNER JOIN seats s
                ON bs.seat_id = s.seat_id
                WHERE booking_id = #{bookingId}
            """)
    List<Seat> getAllByBookingId(@Param("bookingId") Long bookingId);
}
