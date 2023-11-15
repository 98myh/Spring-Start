package org.zerock.ex7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubMemberDTO {
	private String username;
	private String email;
	private String name;
	private String password;
	private boolean fromSocial;
	private LocalDateTime regDate;
	private LocalDateTime modDate;

	@Builder.Default
	private Set<String> roleSet = new HashSet<>();
}