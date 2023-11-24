package com.example.mybatis.controller;

import com.example.mybatis.entity.Member;
import com.example.mybatis.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/getMember")
    public ResponseEntity<Member>getMember(@RequestParam("email") String email){
        Member member=memberService.getMember(email);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

}
