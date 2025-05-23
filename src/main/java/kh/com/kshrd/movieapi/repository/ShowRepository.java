package kh.com.kshrd.movieapi.repository;

import kh.com.kshrd.movieapi.dto.request.ShowRequest;
import kh.com.kshrd.movieapi.model.Show;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShowRepository {

    @Results(id = "showResult", value = {
            @Result(property = "showId", column = "show_id"),
            @Result(property = "showDate", column = "show_date"),
            @Result(property = "showTime", column = "show_time"),
            @Result(property = "numberOfTicket", column = "number_of_ticket"),
    })
    @Select("""
                INSERT INTO shows (show_date, show_time, number_of_ticket, movie_id)
                VALUES (#{request.showDate}, #{request.showTime}, #{request.numberOfTicket}, #{request.movieId})
                RETURNING *
            """)
    Show create(@Param("request") ShowRequest request);

    @ResultMap("showResult")
    @Select("""
            SELECT * FROM shows
            OFFSET #{page} LIMIT #{size}
            """)
    List<Show> getAll(Integer page, Integer size);

    @ResultMap("showResult")
    @Select("""
            SELECT * FROM shows
            WHERE show_id = #{showId}
            """)
    Show getById(Long showId);

    @ResultMap("showResult")
    @Select("""
                UPDATE shows
                SET show_date = #{request.showDate},
                    show_time = #{request.showTime},
                    number_of_ticket = #{request.numberOfTicket},
                    movie_id = #{request.movieId}
                WHERE show_id = #{showId}
                RETURNING *
            """)
    Show update(Long showId, @Param("request") ShowRequest request);

    @Delete("""
                DELETE FROM shows
                WHERE show_id = #{showId}
            """)
    void delete(Long showId);

    @ResultMap("showResult")
    @Select("""
            SELECT * FROM shows
            WHERE movie_id = #{movieId}
        """)
    List<Show> getByMovieId(@Param("movieId") Long movieId);

}
