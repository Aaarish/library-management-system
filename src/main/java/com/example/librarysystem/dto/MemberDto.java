package com.example.librarysystem.dto;

import com.example.librarysystem.enums.Role;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String memberId;
    private String memberName;
    private String memberContactNumber;
    private String email;
    private String password;
    private Role role;

}
