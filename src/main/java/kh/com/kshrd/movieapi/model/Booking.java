package kh.com.kshrd.movieapi.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    private Long bookingId;
    private String fullName;
    private String email;
    private BigDecimal totalPrice;
    private Show show;
    private List<Seat> seats;
}

