package com.example.mybatis.service;

import com.example.mybatis.entity.Member;
import com.example.mybatis.mappers.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;
    @Override
    public Member getMember(String email) {
        Member member= memberMapper.getMember(email);
        return member;
    }
}
