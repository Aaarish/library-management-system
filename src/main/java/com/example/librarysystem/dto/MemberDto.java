package com.example.librarysystem.dto;

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
    private MemberProfileDto memberProfileDto;

}
