package com.example.librarysystem.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberProfileDto {
    private Long memberProfileId;
    private MemberDto member;
    private List<IssuedItemDto> issuedBookDtoList;

}
