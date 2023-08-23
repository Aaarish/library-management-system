package com.example.librarysystem.services;

import com.example.librarysystem.dto.MemberDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    String addMember(MemberDto memberDto);
    String removeMember(String memberId);
    String updateMember(String memberId, MemberDto memberDto);
    MemberDto getMember(String memberId);
    MemberDto getMemberByEmail(String email);
    List<MemberDto> getMembers();

}
