package kh.com.kshrd.movieapi.model;

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
public class Show {
    private Long showId;
    private LocalDate showDate;
    private String showTime;
    private Integer numberOfTicket;
}
