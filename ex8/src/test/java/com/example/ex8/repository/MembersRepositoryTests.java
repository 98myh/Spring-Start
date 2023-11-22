package com.example.ex8.repository;

import com.example.ex8.entity.Members;
import com.example.ex8.entity.MembersRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MembersRepositoryTests {
    @Autowired
    private MembersRepository membersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Members members=Members.builder()
                    .id("user"+i+"@example.com")
                    .email("user"+i+"@example.com")
                    .name("사용자"+i)
                    .gender((int)(Math.random()*2)+1==1?"남성":"여성")
                    .birthday(LocalDate.of((int)(Math.random()*20)+1990,(int)(Math.random()*12)+1,(int)(Math.random()*28)+1))
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1"))
                    .build();
            members.addMembersRole(MembersRole.USER);
            if (i>80){
                members.addMembersRole(MembersRole.MANAGER);
            }
            if (i>90){
                members.addMembersRole(MembersRole.ADMIN);
            }
            membersRepository.save(members);
        });
    }


    @Test
    public void testRead(){
        Optional<Members> result=membersRepository.findByEmail("user95@zerock.org",false);
        Members members=result.get();
        System.out.println(members);
    }
}