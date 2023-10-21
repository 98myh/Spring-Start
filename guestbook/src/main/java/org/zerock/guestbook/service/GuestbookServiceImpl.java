package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 종속성 자동 주입 , repository를 사용하고 싶으면 무조건 사용
public class GuestbookServiceImpl implements GuestbookService {

	private final GuestbookRepository repository; //final을 해야만 순환참조 끊을수 있음`

	@Override
	public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
		Pageable pageable=requestDTO.getPageable(Sort.by("gno").descending());
		Page<Guestbook> result=repository.findAll(pageable);
		Function<Guestbook,GuestbookDTO> fn=new Function<Guestbook, GuestbookDTO>() {
			@Override
			public GuestbookDTO apply(Guestbook entity) {
				return entityToDto(entity);
			}
		};
		return new PageResultDTO<>(result,fn); //dtoList 넘어옴
	}

	@Override
	public GuestbookDTO read(Long gno) {
		Optional<Guestbook> result=repository.findById(gno); //Optional로 null이어도 오류X
		return result.isPresent()?entityToDto(result.get()):null;
	}

	@Override
	public void modify(GuestbookDTO dto) {
		Optional<Guestbook> result=repository.findById(dto.getGno());
		if(result.isPresent()){
			Guestbook entity=result.get();
			entity.changeTitle(dto.getTitle());
			entity.changeContent(dto.getContent());
			repository.save(entity);
		}
	}

	@Override
	public void remove(Long gno) {
		System.out.println(gno+"test.............................");
		repository.deleteById(gno);
	}

	@Override
	public Long register(GuestbookDTO dto) {
		log.info("DTO....... :  "+dto);
		Guestbook entity=dtoToEntity(dto); // -> 아직 gno는 없음
		log.info(entity);
		repository.save(entity); // gno 생성, mariadb에 넘길때 자동으로 증가한 값을 넣기 때문
		return entity.getGno(); //필요한 값만 뽑아서 넘겨줌, 등록된 글 번호를 알려주기 위해서 gno만 넘겨줌
	}

}
