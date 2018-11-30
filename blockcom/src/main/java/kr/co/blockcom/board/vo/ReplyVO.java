package kr.co.blockcom.board.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO extends PagingVO {
	
	private int bfr_idx;
	private String bfr_contents;
	private String mem_name;
	private Date reg_date;
	private Date mod_date;
	private String use_flag;
	
}
