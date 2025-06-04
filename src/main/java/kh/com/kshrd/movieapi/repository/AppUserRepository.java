package kh.com.kshrd.movieapi.repository;

import kh.com.kshrd.movieapi.model.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AppUserRepository {


    @Results(id = "appUserMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "profilePicture", column = "profile_picture"),
            @Result(property = "isAdmin", column = "is_admin")
    })
    @Select("""
                SELECT * FROM users
                WHERE email = #{email}
            """)
    AppUser getUserByEmail(String email);

}
