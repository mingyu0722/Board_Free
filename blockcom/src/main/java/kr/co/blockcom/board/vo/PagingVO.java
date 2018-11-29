package kr.co.blockcom.board.vo;

import lombok.Data;

@Data
public class PagingVO extends BoardVO{
	
	private int page;			//현재 화면
	private int perPageNumber;	//페이지 당 보일 게시글 개수
	private int blockSize;		//한 화면에 보여질 페이지 수
	private int totalCount;		//전체 게시글 개수
	private int totalPage;		//전체 페이지 수
	private int tempEndPage;	//화면에 보여질 끝페이지
	private int startPage;		//화면에 보이는 시작 페이지
	private boolean prev;
	private boolean next;
	private int listStart;

}
