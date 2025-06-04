package kh.com.kshrd.movieapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalPrice;

    @NotNull
    @Positive
    private Long showId;

    @NotEmpty
    private List<@Positive Long> seatIds;
}
