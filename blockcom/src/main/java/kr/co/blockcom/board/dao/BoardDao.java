package kr.co.blockcom.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.BoardVO;

@Repository
public interface BoardDao {
	
	//Select Member
	public List<BoardVO> member() throws Exception;
	
	//List
//	public List<BoardVO> listAll(int bf_cate_idx) throws Exception;
	public List<BoardVO> listAll(BoardVO vo) throws Exception;
	
	//Write	
	public boolean insert(BoardVO vo);
	
	//Read
	public BoardVO read(int bf_idx) throws Exception;
	
	//Previous Article
	public BoardVO preArticle(int bf_idx) throws Exception;
	
	//Next Article
	public BoardVO nextArticle(int bf_idx) throws Exception;
	
	//Delete
	public boolean delete(int bf_idx) throws Exception;
	
	//Update
	public boolean update(BoardVO vo) throws Exception;
	
	//View Count
	public void viewCnt(int bf_idx) throws Exception;
	
	//Search List
	public List<BoardVO> searchList(BoardVO vo) throws Exception;
	
	//Reply List
//	public List<BoardVO> replyList(int bf_idx) throws Exception;
	
	//Reply Update
//	public boolean replyUpdate(BoardVO vo) throws Exception;
	
	//Reply Delete
//	public boolean replyDelete(int bfr_idx) throws Exception;
	
	//Reply Insert
//	public boolean replyInsert(BoardVO vo);
}
