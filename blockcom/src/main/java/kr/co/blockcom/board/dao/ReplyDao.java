package kr.co.blockcom.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.ReplyVO;

@Repository
public interface ReplyDao {
		//Reply List
		public List<ReplyVO> replyList(int bf_idx) throws Exception;
		
		//Reply Update
		public boolean replyUpdate(ReplyVO vo) throws Exception;
		
		//Reply Delete
		public boolean replyDelete(int bfr_idx) throws Exception;
		
		//Reply Insert
		public boolean replyInsert(ReplyVO vo) throws Exception;
}
