package kh.com.kshrd.movieapi.dto.request;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRequest {

    @NotNull
    @NotBlank
    private String title;

    @Min(1800)
    private Integer year;

    @Min(1)
    private Integer duration;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;

    @NotNull
    @NotBlank
    private String overview;

    @NotNull
    @NotBlank
    private String directorName;

    @NotNull
    @NotBlank
    private String poster;

    @NotNull
    @NotBlank
    private String thriller;

    @NotNull
    @Positive
    private Long categoryId;

    @NotEmpty
    private List<@Positive Long> castMemberIds;
}

