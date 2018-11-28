package kr.co.blockcom.board.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.PagingVO;

@Repository
public class BoardDaoImpl implements BoardDao {
	
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	@Override
	public List<BoardVO> member() throws Exception {
		return sessionTemplate.selectList("board.member");
	}
	
	@Override
	public int totalCount(PagingVO vo) throws Exception {
		return sessionTemplate.selectOne("board.totalCount", vo);
	}
	
	/*@Override
	public List<BoardVO> recList(BoardVO vo) throws Exception {
		return sessionTemplate.selectList("board.recList", vo);
	}*/
	
	@Override
	public List<PagingVO> listAll(PagingVO vo) throws Exception {
		
		return sessionTemplate.selectList("board.listAll", vo);
	}

	@Override
	public boolean insert(BoardVO vo) {		
		if(sessionTemplate.insert("board.insert", vo) > 0) {
			return true;
		}
		else
			return false;
	}
	
	@Override
	public BoardVO read(int bf_idx) throws Exception {
		return sessionTemplate.selectOne("board.read", bf_idx);
	}
	
	@Override
	public BoardVO preArticle(int bf_idx) throws Exception {
//		BoardVO vo = new BoardVO();
//		vo = sessionTemplate.selectOne("board.preArticle", bf_idx);
//		System.out.println(vo);
		
		return sessionTemplate.selectOne("board.preArticle", bf_idx);		
	}
	
	@Override
	public BoardVO nextArticle(int bf_idx) throws Exception {
		return sessionTemplate.selectOne("board.nextArticle", bf_idx);
	}
	
	@Override
	public boolean delete(int bf_idx) throws Exception {
		if(sessionTemplate.delete("board.delete", bf_idx) > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean update(BoardVO vo) throws Exception {
		if(sessionTemplate.update("board.update", vo) > 0)
			return true;
		else
			return false;
		
	}
	
	@Override
	public void viewCnt(int bf_idx) throws Exception {
		sessionTemplate.update("board.viewCnt", bf_idx);
	}
	
	@Override
	public List<BoardVO> searchList(BoardVO vo) throws Exception {
		return sessionTemplate.selectList("board.searchList", vo);
	}
	
	/*@Override
	public List<BoardVO> replyList(int bf_idx) throws Exception {
		return sessionTemplate.selectList("board.replyList", bf_idx);		
	}
	
	@Override
	public boolean replyUpdate(BoardVO vo) throws Exception {
		if(sessionTemplate.update("board.replyUpdate", vo) > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean replyDelete(int bfr_idx) throws Exception {
		if(sessionTemplate.delete("board.replyDelete", bfr_idx) > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean replyInsert(BoardVO vo) {		
		if(sessionTemplate.insert("board.replyInsert", vo) > 0) {
			return true;
		}
		else
			return false;
	}*/
}
