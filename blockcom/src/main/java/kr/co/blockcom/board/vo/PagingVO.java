package kr.co.blockcom.board.vo;

import lombok.Data;

@Data
public class PagingVO extends BoardVO{
	
	private int page;
	private int perPageNumber;
	private int totalCount;
	private int tempEndPage;
	private int endPage;
	private int startPage;
	private boolean prev;
	private boolean next;	
}
