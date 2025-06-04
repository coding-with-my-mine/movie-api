package kh.com.kshrd.movieapi.service;

import kh.com.kshrd.movieapi.dto.request.LoginRequest;
import kh.com.kshrd.movieapi.dto.request.RegisterRequest;
import kh.com.kshrd.movieapi.dto.response.LoginResponse;
import kh.com.kshrd.movieapi.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    Long getCurrentUserId();
}
