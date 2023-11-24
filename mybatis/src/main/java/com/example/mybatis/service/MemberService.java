package com.example.mybatis.service;

import com.example.mybatis.entity.Member;

public interface MemberService {
    Member getMember(String email);
}
