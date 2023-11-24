package com.example.mybatis.mappers;

import com.example.mybatis.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
@MapperScan(basePackages = {"com.example.mybatis.mappers"})
public interface MemberMapper {

    public Member getMember(String email);
}
