package org.zerock.ex6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
	private String fileName;
	private String uuid;

	private String folderPath;

	//실제 파일의 전체 경로가 필요할 경우
	public String getImageURL(){ //@Data를 사용하였기 때문에 imageURL로 사용가능
		try {
			return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	//썸네일 이미지 파일의 전체 경로가 필요한경우
	public String getThumbnailURL(){ //@Data를 사용하였기 때문에 imageURL로 사용가능
		try {
			return URLEncoder.encode(folderPath+"/s_"+uuid+"_"+fileName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
