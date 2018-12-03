package kr.co.blockcom.board.vo;

import java.util.Date;

import lombok.Data;

@Data
public class FileVO {
	
	private int bff_idx;
	private int bf_idx;
	private int mem_idx;
	private String file_path;
	private int file_size;
	private Date reg_date;
	private Date mod_date;
	private String use_flag; 
	
}
