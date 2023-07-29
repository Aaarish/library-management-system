package com.example.librarysystem.services.impl;

import com.example.librarysystem.dao.MemberDao;
import com.example.librarysystem.dto.MemberDto;
import com.example.librarysystem.entities.Member;
import com.example.librarysystem.enums.Role;
import com.example.librarysystem.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final ModelMapper modelMapper;
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user exists with this username"));
    }


    @Override
    public String addMember(MemberDto memberDto) {
        Member member = new Member();

        member.setMemberId(UUID.randomUUID().toString());
        member.setMemberName(memberDto.getMemberName());
        member.setMemberContactNumber(memberDto.getMemberContactNumber());
        member.setEmail(memberDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setRole(Role.REGULAR);

        Member savedMember = memberDao.save(member);
        return savedMember.getMemberId();
    }

    @Override
    public String removeMember(String memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("No member with such id exists"));

        memberDao.delete(member);
        return String.format("Member with id : {} is deleted successfully !!!", member.getMemberId());
    }

    @Override
    public String updateMember(String memberId, MemberDto memberDto) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("No member with such id exists"));

        if(memberDto.getMemberName() != null) member.setMemberName(memberDto.getMemberName());
        if(memberDto.getMemberContactNumber() != null) member.setMemberContactNumber(memberDto.getMemberContactNumber());

        Member updatedMember = memberDao.save(member);
        return updatedMember.getMemberId();
    }

    @Override
    public MemberDto getMember(String memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new RuntimeException("No member with such id exists"));

        return modelMapper.map(member, MemberDto.class);
    }

    @Override
    public List<MemberDto> getMembers() {
        List<Member> memberList = memberDao.findAll();

        return memberList.stream()
                .map(member -> modelMapper.map(member, MemberDto.class))
                .collect(Collectors.toList());
    }

}
