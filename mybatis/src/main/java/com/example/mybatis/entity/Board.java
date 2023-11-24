package com.example.mybatis.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Board {
    private Long bno;
    private String title;
    private String email;
    private String content;
}
