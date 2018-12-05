package kr.co.blockcom.board.vo;

import java.util.Date;

import lombok.Data;

@Data
public class FileVO {
	
	private int bf_idx;
	private int mem_idx;
	private String file_path;
	private String file_name;
	private int file_size;
	
}
