package kr.co.blockcom.board.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.MemberVO;
import kr.co.blockcom.board.vo.ReplyVO;

public interface BoardService {
	
	//Select Member
	public List<MemberVO> member() throws Exception;
	
	//List
//	public List<BoardVO> listAll(int bf_cate_idx) throws Exception;
	public List<BoardVO> listAll(BoardVO vo) throws Exception;
	
	//Write
	public boolean write(BoardVO vo) throws Exception;

	
	//Read
	public BoardVO read(int bf_idx) throws Exception;
	
	//Delete
	public boolean delete(int bf_idx) throws Exception;
	
	//Update
	//public void update(BoardVO vo) throws Exception;
	public boolean update(BoardVO vo) throws Exception;
	
	//View Count
	public void viewCnt(int bf_idx, HttpSession session) throws Exception;
	
	//Reply List
	public List<ReplyVO> replyList(int bf_idx) throws Exception;
	
	//Reply Update
	public boolean replyUpdate(ReplyVO vo) throws Exception;
	
	//Reply Delete
	public boolean replyDelete(int bfr_idx) throws Exception;
	
	//Reply Insert
	public boolean replyInsert(ReplyVO vo) throws Exception;
	
	//Search List
	public List<BoardVO> searchList(BoardVO vo) throws Exception;
}
