package kh.com.kshrd.movieapi.repository;

import kh.com.kshrd.movieapi.dto.request.BookingRequest;
import kh.com.kshrd.movieapi.model.Booking;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookingRepository {

    @Select("""
                INSERT INTO bookings (full_name, email, total_price, show_id)
                VALUES (#{request.fullName}, #{request.email}, #{request.totalPrice}, #{request.showId})
                RETURNING booking_id
            """)
    Long insertBooking(@Param("request") BookingRequest request);

    @Insert("""
                INSERT INTO booking_seat (booking_id, seat_id)
                VALUES (#{bookingId}, #{seatId})
            """)
    void insertBookingSeat(Long bookingId, Long seatId);

    @Results(id = "bookingResult", value = {
            @Result(property = "bookingId", column = "booking_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "totalPrice", column = "total_price"),
            @Result(property = "show", column = "show_id", one = @One(select = "kh.com.kshrd.movieapi.repository.ShowRepository.getById")),
            @Result(property = "seats", column = "booking_id", many = @Many(select = "kh.com.kshrd.movieapi.repository.SeatRepository.getAllByBookingId"))
    })
    @Select("""
                SELECT * FROM bookings
                OFFSET #{page} LIMIT #{size};
            """)
    List<Booking> getAll(Integer page, Integer size);

    @ResultMap("bookingResult")
    @Select("""
                SELECT * FROM bookings
                WHERE booking_id = #{bookingId}
            """)
    Booking getById(Long bookingId);

    @Delete("""
                DELETE FROM bookings
                WHERE booking_id = #{bookingId}
            """)
    void delete(Long bookingId);

    @Update("""
                UPDATE bookings
                SET full_name = #{fullName},
                    email = #{email},
                    total_price = #{totalPrice},
                    show_id = #{showId}
                WHERE booking_id = #{bookingId}
            """)
    void updateBooking(Long bookingId, @Param("request") BookingRequest request);

    @Delete("""
                DELETE FROM booking_seat
                WHERE booking_id = #{bookingId}
            """)
    void deleteBookingSeat(Long bookingId);
}
