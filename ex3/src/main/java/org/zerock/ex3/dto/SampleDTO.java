package org.zerock.ex3.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true) //(toBuilder = true)생략 가능
public class SampleDTO {
	private Long sno;
	private String first;
	private String last;
	private LocalDateTime regTime;
}
