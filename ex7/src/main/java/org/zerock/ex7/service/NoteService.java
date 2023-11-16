package org.zerock.ex7.service;

import org.zerock.ex7.dto.NoteDTO;
import org.zerock.ex7.entity.ClubMember;
import org.zerock.ex7.entity.Note;

import java.util.List;

public interface NoteService {
	Long register(NoteDTO noteDTO);

	NoteDTO get(Long num);

	void modify(NoteDTO noteDTO);

	void remove(Long num);

	List<NoteDTO> getAllWithWriter(String writer);

	default Note dtoToEntity(NoteDTO noteDTO) {
		Note note = Note.builder()
				.num(noteDTO.getNum())
				.title(noteDTO.getTitle())
				.content(noteDTO.getContent())
				.writer(ClubMember.builder().email(
						noteDTO.getWriterEmail()
				).build())
				.build();
		return note;
	}

	default NoteDTO entityToDto(Note note) {
		NoteDTO dto = NoteDTO.builder()
				.num(note.getNum())
				.title(note.getTitle())
				.content(note.getContent())
				.writerEmail(note.getWriter().getEmail())
				.regDate(note.getRegDate())
				.modDate(note.getModDate())
				.build();
		return dto;
	}
}