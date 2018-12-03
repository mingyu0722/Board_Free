package kr.co.blockcom.board.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.FileVO;
import kr.co.blockcom.board.vo.MemberVO;
import kr.co.blockcom.board.vo.PagingVO;
import kr.co.blockcom.board.vo.ReplyVO;

public interface BoardService {
	
	//Select Member
	public List<MemberVO> member() throws Exception;
	
	//Total Count
	public int totalCount(PagingVO vo) throws Exception;
	
	/*//Recommended List
	public List<BoardVO> recList(BoardVO vo) throws Exception;*/
	
	//List
	public List<PagingVO> listAll(PagingVO vo) throws Exception;
	
	//Write
	public boolean write(BoardVO vo) throws Exception;
	
	//Read
	public BoardVO read(BoardVO vo) throws Exception;
	
	//Recommend
	public void recommend(BoardVO vo) throws Exception;
	
	//Recommend Flag Confirm
	public boolean recommend_flag(BoardVO vo) throws Exception;
	
	//Recommend Delete
	public boolean delRecommend(BoardVO vo) throws Exception;
	
	//Previous Article
	public BoardVO preArticle(int bf_idx) throws Exception;
	
	//Next Article
	public BoardVO nextArticle(int bf_idx) throws Exception;
	
	//Delete
	public boolean delete(int bf_idx) throws Exception;
	
	//Update
	public boolean update(BoardVO vo) throws Exception;
	
	//Reply List
	public List<ReplyVO> replyList(ReplyVO rvo) throws Exception;
	
	//Reply Update
	public boolean replyUpdate(ReplyVO vo) throws Exception;
	
	//Reply Delete
	public boolean replyDelete(int bfr_idx) throws Exception;
	
	//Reply Insert
	public boolean replyInsert(ReplyVO vo) throws Exception;
	
	//Search List
	public List<BoardVO> searchList(BoardVO vo) throws Exception;
}
