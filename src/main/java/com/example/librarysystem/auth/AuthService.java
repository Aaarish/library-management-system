package com.example.librarysystem.auth;

import com.example.librarysystem.auth.requests.AuthRequest;
import com.example.librarysystem.auth.requests.RegisterRequest;
import com.example.librarysystem.auth.responses.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse authenticate(AuthRequest authRequest);

}
