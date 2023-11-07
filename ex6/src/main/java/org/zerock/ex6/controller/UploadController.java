package org.zerock.ex6.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.ex6.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {
	@Value("${org.zerock.upload.path}")
	private String uploadPath; //application.properties에 있음


	@PostMapping("/uploadAjax")
	public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){
		List<UploadResultDTO> resultDTOList=new ArrayList<>();


		for (MultipartFile uploadFile:uploadFiles){
			if (uploadFile.getContentType().startsWith("image")==false){ //이미지 파일이 아니면
				log.warn("This is not image file");
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);//403 에러
			}
			String originalName=uploadFile.getOriginalFilename();
			String fileName=originalName.substring(originalName.lastIndexOf("\\")+1); // \\는 \를 의미
			log.info("fileName : "+fileName  );

			String folderPath=makeFolder(); //해당 날짜로 폴더 생성
			String uuid= UUID.randomUUID().toString(); //uuid 생성 - 파일명이 겹치면 안되기 때문

			String saveName=uploadPath+File.separator+folderPath+File.separator+uuid+"_"+fileName;

			Path savePath= Paths.get(saveName); // Path : 파일 올리는 경로를 가리킬 때 사용
			try {
				uploadFile.transferTo(savePath); //파일 전송

				String thumbnailSaveName=uploadPath+File.separator+folderPath+File.separator+"s_"+uuid+"_"+fileName; //썸네일
				File thumbnailFile=new File(thumbnailSaveName);
				Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,100,100);

				resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(resultDTOList,HttpStatus.OK); //200번
	}

	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String fileName){
		ResponseEntity<byte[]> result=null;

		try {
			String srcFileName= URLDecoder.decode(fileName,"UTF-8");
			log.info("fileName "+srcFileName);
			File file=new File(uploadPath+File.separator+srcFileName);
			log.info("file : "+file);
			HttpHeaders header=new HttpHeaders();
			//probeContentType - 파일의 확장자에 따라서 브라우저에 전송하는 MIME(다목적 메일 교환 방식)타입이 달라질수 있게 함
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result=new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //500번 에러
		}
		return result;
	}


	//삭제
	@DeleteMapping("/removeFile")
	public ResponseEntity<Boolean> removeFile(@RequestParam("fileName")String fileName){
		log.info("remove file : "+fileName);
		String srcFileName=null;

		try {
			srcFileName= URLDecoder.decode(fileName,"UTF-8");
			File file=new File(uploadPath+File.separator+srcFileName);
			boolean result=file.delete();
			File thumbnail=new File(file.getParent(),"s_"+file.getName());
			boolean result2=thumbnail.delete();
			return new ResponseEntity<>(result,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR); //500번 에러
		}
	}

	private String makeFolder(){
		String str= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String folderPath=str.replace("/", File.separator);

		File uploadPathFolder=new File(uploadPath,folderPath);
		if (uploadPathFolder.exists()==false){
			uploadPathFolder.mkdirs();
		}
		return folderPath;
	}

}
