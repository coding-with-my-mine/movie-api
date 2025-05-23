package kh.com.kshrd.movieapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowRequest {

    @NotNull
    private LocalDate showDate;

    @NotBlank
    @NotNull
    private String showTime;

    @Min(1)
    private Integer numberOfTicket;

    @NotNull
    @Positive
    private Long movieId;
}
