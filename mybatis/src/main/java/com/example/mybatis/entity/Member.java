package com.example.mybatis.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member {
    private String email;
    private String password;
    private String name;
}