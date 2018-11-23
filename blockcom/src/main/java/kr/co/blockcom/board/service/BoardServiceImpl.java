package kr.co.blockcom.board.service;

import java.util.List;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.blockcom.board.dao.BoardDao;
import kr.co.blockcom.board.dao.MemberDao;
import kr.co.blockcom.board.dao.ReplyDao;
import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.MemberVO;
import kr.co.blockcom.board.vo.ReplyVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private ReplyDao replyDao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public List<MemberVO> member() throws Exception {
		return memberDao.member();
	}
	
	@Override
	public List<BoardVO> listAll(BoardVO vo) throws Exception {
		return boardDao.listAll(vo);
	}
	
	@Override
	public boolean write(BoardVO vo) throws Exception {
		String title = vo.getBf_title();
		String contents = vo.getBf_contents();
		
		title = title.replace("<", "&lt");
		title = title.replace("<", "&gt");
		
		vo.setBf_title(title);
		vo.setBf_contents(contents);
		if(boardDao.insert(vo) == true) {
			return true;
		}
			
		else 
			return false;
	}
	
	@Override
	public BoardVO read(int bf_idx) throws Exception {
		return boardDao.read(bf_idx);
	}
	
	@Override
	public boolean delete(int bf_idx) throws Exception {
		if(boardDao.delete(bf_idx) == true)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean update(BoardVO vo) throws Exception {
		if(boardDao.update(vo) == true)
			return true;
		else
			return false;
	}
	
	@Override
	public void viewCnt(int bf_idx, HttpSession session) throws Exception {
		long update_time = 0;
		long current_time = System.currentTimeMillis();
		if(current_time - update_time > 5*1000) {
			boardDao.viewCnt(bf_idx);
			session.setAttribute("update_time_"+bf_idx, current_time);
		}
	}
	
	@Override
	public List<ReplyVO> replyList(int bf_idx) throws Exception {
		return replyDao.replyList(bf_idx);
	}
	
	@Override
	public boolean replyInsert(ReplyVO vo) throws Exception {
		String contents = vo.getBfr_contents();
		
		contents = contents.replace("<", "&lt");
		contents = contents.replace("<", "&gt");
		
		vo.setBfr_contents(contents);
		if(replyDao.replyInsert(vo) == true) {
			return true;
		}
			
		else 
			return false;
	}
	
	@Override
	public boolean replyUpdate(ReplyVO vo) throws Exception {
		if(replyDao.replyUpdate(vo) == true)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean replyDelete(int bfr_idx) throws Exception {
		if(replyDao.replyDelete(bfr_idx) == true)
			return true;
		else
			return false;
	}
	
	@Override
	public List<BoardVO> searchList(BoardVO vo) throws Exception {
		return boardDao.searchList(vo);
	}
}
