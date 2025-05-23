package kh.com.kshrd.movieapi.repository;

import kh.com.kshrd.movieapi.dto.request.CategoryRequest;
import kh.com.kshrd.movieapi.model.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryRepository {

    @Results(id = "categoryResult", value = {
            @Result(property = "categoryId", column = "category_id")
    })
    @Select("""
                INSERT INTO categories (name) VALUES (#{name}) RETURNING *
            """)
    Category create(CategoryRequest request);

    @ResultMap("categoryResult")
    @Select("""
                SELECT *
                FROM categories
                OFFSET #{page} LIMIT #{size}
            """)
    List<Category> getAll(Integer page, Integer size);

    @ResultMap("categoryResult")
    @Select("""
                SELECT * FROM categories WHERE category_id = #{categoryId}
            """)
    Category getById(@Param("categoryId") Long categoryId);

    @ResultMap("categoryResult")
    @Select("""
                UPDATE categories SET name = #{request.name} WHERE category_id = #{categoryId} RETURNING *
            """)
    Category update(Long categoryId, @Param("request") CategoryRequest request);

    @Delete("""
                DELETE FROM categories WHERE category_id = #{categoryId}
            """)
    void delete(Long categoryId);

    @ResultMap("categoryResult")
    @Select("""
                SELECT * FROM categories
                WHERE name = #{categoryName}
            """)
    Category getByName(String categoryName);
}
