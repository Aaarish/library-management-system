package com.example.librarysystem.controllers;

import com.example.librarysystem.dto.MemberDto;
import com.example.librarysystem.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<String> addMember(@RequestBody MemberDto memberDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.addMember(memberDto));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> removeMember(@PathVariable String memberId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberService.removeMember(memberId));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable String memberId, @RequestBody MemberDto memberDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.updateMember(memberId, memberDto));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> getMember(@PathVariable String memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(memberId));
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getMembers() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMembers());
    }

    @PutMapping("/member/{memberId}/changePassword")
    public ResponseEntity<String> changePassword(@PathVariable String memberId, @RequestParam String newPassword) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.changePassword(memberId, newPassword));
    }

}
