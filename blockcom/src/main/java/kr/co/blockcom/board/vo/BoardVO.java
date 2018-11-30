package kr.co.blockcom.board.vo;

import java.util.Date;

import lombok.Data;

@Data //자동으로 변수에 대한 Getter & Setter를 lombok이 생성
public class BoardVO {
	
	private int RNUM;
	private int bf_idx;
	private int bf_cate_idx;
	private String bf_title;
	private String bf_contents;
	private int mem_idx;
	private String mem_name;
	private Date reg_date;
	private Date mod_date;
	private int view_cnt;
	private boolean rec_flag;			//게시글 추천여부
	private String use_flag;
	private String use_sec;
	private int bfr_idx;
	private String bfr_contents;
	private int replyCnt;
	private String searchCondition;
	private String searchValue;
} 
