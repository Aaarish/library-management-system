package com.example.librarysystem.auth;

import com.example.librarysystem.auth.requests.AuthRequest;
import com.example.librarysystem.auth.requests.RegisterRequest;
import com.example.librarysystem.auth.requests.ResetPasswordRequest;
import com.example.librarysystem.auth.responses.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.authenticate(authRequest));
    }

    @PutMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.resetPassword(resetPasswordRequest));
    }

}
