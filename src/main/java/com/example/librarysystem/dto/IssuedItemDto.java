package com.example.librarysystem.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssuedItemDto {
    private String issuedItemId;
    private BookDto bookDto;
    private MemberProfileDto memberProfileDto;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;

}
