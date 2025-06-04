package kh.com.kshrd.movieapi.repository;

import jakarta.validation.constraints.Positive;
import kh.com.kshrd.movieapi.dto.request.MovieRequest;
import kh.com.kshrd.movieapi.model.Movie;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MovieRepository {

    @Select("""
                INSERT INTO movies (
                    title, year, duration, rating, overview,
                    director_name, poster, thriller, category_id
                ) VALUES (
                    #{title}, #{year}, #{duration}, #{rating}, #{overview},
                    #{directorName}, #{poster}, #{thriller}, #{categoryId}
                )
                RETURNING movie_id
            """)
    Long create(MovieRequest request);

    @Results(id = "movieResult", value = {
            @Result(property = "movieId", column = "movie_id"),
            @Result(property = "directorName", column = "director_name"),
            @Result(property = "isFavorite", column = "is_favorite_for_user"),
            @Result(property = "category", column = "category_id", one = @One(select = "kh.com.kshrd.movieapi.repository.CategoryRepository.getById")),
            @Result(property = "castMembers", column = "movie_id", many = @Many(select = "kh.com.kshrd.movieapi.repository.CastMemberRepository.getByMovieId")),
    })
    @Select("""
                SELECT
                    m.*,
                    CASE WHEN ufm.user_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_favorite_for_user
                FROM movies m
                LEFT JOIN user_favorite_movies ufm
                    ON m.movie_id = ufm.movie_id AND ufm.user_id = #{userId}
                OFFSET #{page} LIMIT #{size}
            """)
    List<Movie> getAll(Integer page, Integer size, Long userId);

    @ResultMap("movieResult")
    @Select("""
                SELECT
                    m.*,
                    CASE WHEN ufm.user_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_favorite_for_user
                FROM movies m
                LEFT JOIN user_favorite_movies ufm
                    ON m.movie_id = ufm.movie_id AND ufm.user_id = #{userId}
                WHERE m.movie_id = #{movieId}
            """)
    Movie getById(Long movieId, Long userId);

    @Update("""
                UPDATE movies SET
                    title = #{request.title},
                    year = #{request.year},
                    duration = #{request.duration},
                    rating = #{request.rating},
                    overview = #{request.overview},
                    director_name = #{request.directorName},
                    poster = #{request.poster},
                    thriller = #{request.thriller},
                    category_id = #{request.categoryId}
                WHERE movie_id = #{movieId}
            """)
    void update(Long movieId, @Param("request") MovieRequest request);

    @Delete("""
                DELETE FROM movies WHERE movie_id = #{movieId}
            """)
    void delete(Long movieId);

    @Insert("""
                INSERT INTO user_favorite_movies
                VALUES (#{userId}, #{movieId})
            """)
    void markToFavorite(Long movieId, Long userId);

    @ResultMap("movieResult")
    @Select("""
                SELECT * FROM movies
                WHERE category_id = #{categoryId}
                OFFSET #{page} LIMIT #{size}
            """)
    List<Movie> getAllByCategoryId(Long categoryId, Integer page, Integer size);

    @ResultMap("movieResult")
    @Select("""
                SELECT * FROM movies m
                INNER JOIN categories c
                ON m.category_id = c.category_id
                WHERE c.name = #{categoryName}
                OFFSET #{page} LIMIT #{size}
            """)
    List<Movie> getAllByCategoryName(String categoryName, Integer page, Integer size);

    @ResultMap("movieResult")
    @Select("""
                SELECT * FROM movies WHERE title = #{title}
            """)
    Movie getByTitle(String title);

    @Insert("""
                    INSERT INTO casting(movie_id, cast_id)
                    VALUES (#{movieId}, #{castMemberId})
            """)
    void insertMovieCastMember(Long movieId, Long castMemberId);

    @Delete("""
                DELETE FROM casting
                WHERE movie_id = #{movie};
            """)
    void deleteMovieCastMember(Long movieId);

    @Delete("""
        DELETE FROM user_favorite_movies
        WHERE user_id = #{userId} AND movie_id = #{movieId}
    """)
    void unmarkToFavorite(Long movieId, Long userId);
}
