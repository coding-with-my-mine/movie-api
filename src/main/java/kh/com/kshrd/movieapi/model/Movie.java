package kh.com.kshrd.movieapi.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    private Long movieId;
    private String title;
    private Integer year;
    private Integer duration;
    private Double rating;
    private String overview;
    private String directorName;
    private Boolean isFavorite;
    private String poster;
    private String thriller;
    private Category category;
    private List<CastMember> castMembers;
}
