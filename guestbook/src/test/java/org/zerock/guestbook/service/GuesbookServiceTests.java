package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest
class GuesbookServiceTests {

	@Autowired
	GuestbookService service;

	@Test
	void getList() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
				.page(1)
				.size(10)
				.build();
		PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);


		System.out.println("PREV : " + resultDTO.isPrev());
		System.out.println("NEXT : " + resultDTO.isNext());
		System.out.println("TOTAL : " + resultDTO.getTotalPage());
		System.out.println("==================================================");

		for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
			System.out.println(guestbookDTO);
		}
		System.out.println("==================================================");
		resultDTO.getPageList().forEach(i -> {
					if (i != 1) System.out.print(", ");
					System.out.print(i);
				}
		);
		System.out.println();

	}
}