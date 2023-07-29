package com.example.librarysystem.auth.requests;

import com.example.librarysystem.dto.MemberDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private MemberDto memberDto;

}
