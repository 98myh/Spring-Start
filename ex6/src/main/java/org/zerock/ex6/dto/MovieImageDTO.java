package org.zerock.ex6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieImageDTO {
	private String uuid;
	private String imgName;
	private String path;


	public String getImageURL(){ //@Data를 사용하였기 때문에 imageURL로 사용가능
		try {
			return URLEncoder.encode(path+"/"+uuid+"_"+imgName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	//썸네일 이미지 파일의 전체 경로가 필요한경우
	public String getThumbnailURL(){ //@Data를 사용하였기 때문에 imageURL로 사용가능
		try {
			return URLEncoder.encode(path+"/s_"+uuid+"_"+imgName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
