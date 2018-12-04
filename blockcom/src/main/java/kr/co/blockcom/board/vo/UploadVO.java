package kr.co.blockcom.board.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadVO {
	
	private int bf_cate_idx;
	private String bf_title;
	private String bf_contents;
	private int mem_idx;
	private String use_sec;
	private MultipartFile file;
	
}
