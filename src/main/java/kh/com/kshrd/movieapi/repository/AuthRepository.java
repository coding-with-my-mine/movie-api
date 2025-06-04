package kh.com.kshrd.movieapi.repository;

import kh.com.kshrd.movieapi.dto.request.RegisterRequest;
import kh.com.kshrd.movieapi.model.AppUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AuthRepository {

    @Results(id = "appUserMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "profilePicture", column = "profile_picture")
    })
    @Select("""
        INSERT INTO users
        VALUES (default, #{request.fullName}, #{request.password},  #{request.email}, #{request.profilePicture})
        RETURNING *;
    """)
    AppUser register(@Param("request") RegisterRequest request);

}
