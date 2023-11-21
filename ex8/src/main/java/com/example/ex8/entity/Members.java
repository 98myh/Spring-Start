package com.example.ex8.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Members extends BaseEntity{
	//number of column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mno;

	@Column(unique = true)
	private String id;

	private String password;
	private String name;
	private String gender;
	private String email;
	private String mobile;
	private String myPhoto;
	private LocalDate birthday;

	private boolean fromSocial;

	@ElementCollection(fetch= FetchType.LAZY)
	@Builder.Default
	private Set<MembersRole> roleSet=new HashSet<>();

	public void addMembersRole(MembersRole membersRole){
		roleSet.add(membersRole);
	}
}
