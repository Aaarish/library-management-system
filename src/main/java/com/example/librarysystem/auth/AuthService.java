package com.example.librarysystem.auth;

import com.example.librarysystem.auth.requests.AuthRequest;
import com.example.librarysystem.auth.requests.RegisterRequest;
import com.example.librarysystem.auth.requests.ResetPasswordRequest;
import com.example.librarysystem.auth.responses.AuthResponse;
import com.example.librarysystem.auth.responses.ResetPasswordResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse authenticate(AuthRequest authRequest);

    ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
}
