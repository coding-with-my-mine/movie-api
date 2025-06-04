package kh.com.kshrd.movieapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String profilePicture;

}
