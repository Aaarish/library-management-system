package com.example.librarysystem.auth;

import com.example.librarysystem.auth.jwt.JwtUtil;
import com.example.librarysystem.auth.requests.AuthRequest;
import com.example.librarysystem.auth.requests.RegisterRequest;
import com.example.librarysystem.auth.responses.AuthResponse;
import com.example.librarysystem.dto.MemberDto;
import com.example.librarysystem.entities.Member;
import com.example.librarysystem.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final ModelMapper modelMapper;


    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        String memberId = memberService.addMember(registerRequest.getMemberDto());
        MemberDto memberDto = memberService.getMember(memberId);
        Member member = modelMapper.map(memberDto, Member.class);

        String token = jwtUtil.generateToken(member);

        return AuthResponse.builder()
                .jwt(token)
                .message("User registered successfully !!!")
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = memberService.loadUserByUsername(authRequest.getEmail());

        String token = null;

        if(authentication != null) {
            token = jwtUtil.generateToken(userDetails);
        }

        return AuthResponse.builder()
                .jwt(token)
                .message("User authenticated successfully !!!")
                .build();
    }
}
