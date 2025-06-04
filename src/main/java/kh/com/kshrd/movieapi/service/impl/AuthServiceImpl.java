package kh.com.kshrd.movieapi.service.impl;

import kh.com.kshrd.movieapi.dto.request.LoginRequest;
import kh.com.kshrd.movieapi.dto.request.RegisterRequest;
import kh.com.kshrd.movieapi.dto.response.LoginResponse;
import kh.com.kshrd.movieapi.dto.response.RegisterResponse;
import kh.com.kshrd.movieapi.exception.BadRequestException;
import kh.com.kshrd.movieapi.exception.ConflictException;
import kh.com.kshrd.movieapi.jwt.JwtService;
import kh.com.kshrd.movieapi.model.AppUser;
import kh.com.kshrd.movieapi.repository.AuthRepository;
import kh.com.kshrd.movieapi.service.AppUserService;
import kh.com.kshrd.movieapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        AppUser existUser = (AppUser) appUserService.loadUserByUsername(request.getEmail());
        if (existUser != null){
            throw new ConflictException("Your email already register");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        AppUser appUser = authRepository.register(request);
        return modelMapper.map(appUser, RegisterResponse.class);
    }

    @SneakyThrows
    @Override
    public LoginResponse login(LoginRequest request) {
        authenticate(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(request.getEmail());
        return LoginResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public Long getCurrentUserId() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appUser.getUserId();
    }

    private void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid email, or password. Please check your credentials and try again.");
        }
    }


}
