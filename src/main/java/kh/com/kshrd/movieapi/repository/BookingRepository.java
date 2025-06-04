package kh.com.kshrd.movieapi.repository;

import kh.com.kshrd.movieapi.dto.request.BookingRequest;
import kh.com.kshrd.movieapi.model.Booking;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookingRepository {

    @Select("""
                INSERT INTO bookings (total_price, show_id, user_id)
                VALUES (#{request.totalPrice}, #{request.showId}, #{userId})
                RETURNING booking_id
            """)
    Long insertBooking(@Param("request") BookingRequest request, Long userId);

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
                SELECT
                    b.booking_id,
                    u.full_name,
                    u.email,
                    b.total_price,
                    b.show_id
                FROM bookings b
                JOIN users u ON b.user_id = u.user_id
                WHERE b.user_id = #{userId}
                OFFSET #{page} LIMIT #{size};
            """)
    List<Booking> getAll(Integer page, Integer size, Long userId);

    @ResultMap("bookingResult")
    @Select("""
                SELECT
                    b.booking_id,
                    u.full_name,
                    u.email,
                    b.total_price,
                    b.show_id
                FROM bookings b
                JOIN users u ON b.user_id = u.user_id
                WHERE b.user_id = #{userId} AND b.booking_id = #{bookingId}
            """)
    Booking getById(Long bookingId, Long userId);

    @Delete("""
                DELETE FROM bookings
                WHERE booking_id = #{bookingId} AND user_id = #{userId}
            """)
    void delete(Long bookingId, Long userId);

    @Update("""
                UPDATE bookings
                SET total_price = #{request.totalPrice},
                    show_id = #{request.showId}
                WHERE booking_id = #{bookingId} AND user_id = #{userId}
            """)
    void updateBooking(Long bookingId, @Param("request") BookingRequest request, Long userId);

    @Delete("""
                DELETE FROM booking_seat
                WHERE booking_id = #{bookingId}
            """)
    void deleteBookingSeat(Long bookingId);
}
