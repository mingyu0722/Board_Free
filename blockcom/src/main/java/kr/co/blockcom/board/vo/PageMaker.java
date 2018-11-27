package kr.co.blockcom.board.vo;

import lombok.Data;

@Data
public class PageMaker {
	
	private int totalCnt;				//게시판 전체 데이터 개수
	private int displayPageNum = 10;	//게시판 화면에서 한번에 보여질 페이지 번호 개수
	
	private int startPage;				//현재 화면에 보이는 startPage 번호
	private int endPage;				//현재 화면에 보이는 endPage 번호
	private boolean prev;				//페이지 이전 버튼 활성화 여부 
	private boolean next;				//페이지 다음 버튼 활성화 여부
	
	private Criteria criteria;
	private String searchCondition;
	private String searchValue;
	private int bf_cate_idx;
	
	public int getTotalCount() {
		return totalCnt;
	}
	
	public void setTotalCount(int totalCnt) {
		this.totalCnt = totalCnt;
		calcData();
	}
	
	/*public String getSearchCondition() {
		return searchCondition;
	}
	
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	
	public String getSearchValue() {
		return searchValue;
	}
	
	public void setSearchValue(String searchCondition) {
		this.searchValue = searchValue;
	}*/
	
	private void calcData() {
		endPage = (int)(Math.ceil(criteria.getPage() / (double)displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;
		int tempEndPage = (int)(Math.ceil(totalCnt / (double)criteria.getPerPageNum()));
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		prev = startPage == 1 ? false : true;
		next = endPage * criteria.getPerPageNum() >= totalCnt ? false : true; 
	}
}
