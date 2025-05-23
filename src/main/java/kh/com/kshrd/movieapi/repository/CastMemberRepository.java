package kh.com.kshrd.movieapi.repository;

import kh.com.kshrd.movieapi.dto.request.CastMemberRequest;
import kh.com.kshrd.movieapi.model.CastMember;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CastMemberRepository {

    @Results(id = "castMemberResult", value = {
            @Result(property = "castId", column = "cast_id"),
    })
    @Select("""
                INSERT INTO cast_members (name)
                VALUES (#{name})
                RETURNING *
            """)
    CastMember create(CastMemberRequest request);

    @ResultMap("castMemberResult")
    @Select("""
                SELECT * FROM cast_members
                OFFSET #{page} LIMIT #{size}
            """)
    List<CastMember> getAll(Integer page, Integer size);

    @ResultMap("castMemberResult")
    @Select("""
                SELECT * FROM cast_members
                WHERE cast_id = #{castMemberId}
            """)
    CastMember getById(Long castMemberId);

    @ResultMap("castMemberResult")
    @Select("""
                SELECT * FROM cast_members cm
                INNER JOIN casting c
                ON cm.cast_id = c.cast_id
                WHERE movie_id = #{movieId}
            """)
    List<CastMember> getByMovieId(Long movieId);

    @ResultMap("castMemberResult")
    @Select("""
                UPDATE cast_members
                SET name = #{request.name}
                WHERE cast_id = #{castMemberId}
                RETURNING *
            """)
    CastMember update(Long castMemberId, @Param("request") CastMemberRequest request);

    @Delete("""
                DELETE FROM cast_members
                WHERE cast_id = #{castMemberId}
            """)
    void delete(Long castMemberId);
}
