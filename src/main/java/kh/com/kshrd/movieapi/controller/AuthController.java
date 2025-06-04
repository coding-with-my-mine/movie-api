package kh.com.kshrd.movieapi.controller;

import jakarta.validation.Valid;
import kh.com.kshrd.movieapi.dto.request.LoginRequest;
import kh.com.kshrd.movieapi.dto.request.RegisterRequest;
import kh.com.kshrd.movieapi.dto.response.APIResponse;
import kh.com.kshrd.movieapi.dto.response.LoginResponse;
import kh.com.kshrd.movieapi.dto.response.RegisterResponse;
import kh.com.kshrd.movieapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auths")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest request) {
        APIResponse<RegisterResponse> apiResponse = APIResponse.<RegisterResponse>builder()
                .message("User registered successfully! Please verify your email to complete the registration.")
                .status(HttpStatus.CREATED)
                .payload(authService.register(request))
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        APIResponse<LoginResponse> apiResponse = APIResponse.<LoginResponse>builder()
                .message("Login successful! Authentication token generated.")
                .status(HttpStatus.CREATED)
                .payload(authService.login(request))
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}
