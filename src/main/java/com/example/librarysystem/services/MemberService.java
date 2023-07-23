package com.example.librarysystem.services;

import com.example.librarysystem.dto.MemberDto;
import java.util.List;

public interface MemberService {
    String addMember(MemberDto memberDto);
    String removeMember(String memberId);
    String updateMember(String memberId, MemberDto memberDto);
    MemberDto getMember(String memberId);
    List<MemberDto> getMembers();

}
