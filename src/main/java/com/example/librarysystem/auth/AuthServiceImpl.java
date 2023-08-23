package com.example.librarysystem.auth;

import com.example.librarysystem.auth.jwt.JwtUtil;
import com.example.librarysystem.auth.otp.OTPDao;
import com.example.librarysystem.auth.otp.OTPEntity;
import com.example.librarysystem.auth.requests.AuthRequest;
import com.example.librarysystem.auth.requests.RegisterRequest;
import com.example.librarysystem.auth.responses.AuthResponse;
import com.example.librarysystem.dto.MemberDto;
import com.example.librarysystem.entities.Member;
import com.example.librarysystem.services.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final ModelMapper modelMapper;
    private final OTPDao otpDao;



    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        String memberId = memberService.addMember(registerRequest.getMemberDto());
        MemberDto memberDto = memberService.getMember(memberId);
        Member member = modelMapper.map(memberDto, Member.class);

        //verify member before completing the registration
        OTPEntity savedOtp = otpDao.save(new OTPEntity(member));

        if(!verifyOTP(savedOtp.getOtp())) {
            log.info("{} is not verified !", member.getEmail());
            throw new RuntimeException("Member cannot be verified!\tPlease try again!");
        }

        log.info("{} is verified successfully !!!", member.getEmail());
        otpDao.delete(savedOtp);

        String token = jwtUtil.generateToken(member);

        return AuthResponse.builder()
                .jwt(token)
                .message("User registered successfully !!!")
                .build();
    }

    private boolean verifyOTP(String otp) {
        log.info("Verifying the OTP !");
        Optional<OTPEntity> otpEntry = otpDao.findByOtp(otp);

        if(otpEntry.isEmpty() || otpEntry.get().getExpirationTime().isBefore(LocalDateTime.now())) {
            log.info("OTP is invalid !");
            return false;
        }

        return otp.equals(otpEntry.get().getOtp());
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
