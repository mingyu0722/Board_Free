package kr.co.blockcom.board.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.ReplyVO;

@Repository
public class ReplyDaoImpl implements ReplyDao {
	
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	@Override
	public List<ReplyVO> replyList(int bf_idx) throws Exception {
		return sessionTemplate.selectList("board.replyList", bf_idx);
	}
	
	@Override
	public boolean replyInsert(ReplyVO vo) throws Exception {
		if(sessionTemplate.insert("board.replyInsert", vo) > 0) {
			return true;
		}
		else
			return false;
	}
	
	@Override
	public boolean replyUpdate(ReplyVO vo) throws Exception {
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
	
}
