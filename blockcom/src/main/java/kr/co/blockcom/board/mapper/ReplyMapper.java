package kr.co.blockcom.board.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.PagingVO;
import kr.co.blockcom.board.vo.ReplyVO;

@Repository
public interface ReplyMapper {
	
	/**
	 * 댓글 수
	 * @param bf_idx
	 * @return int
	 * @throws Exception
	 */
	public int totalCount(int bf_idx) throws Exception;
	
	/**
	 * 댓글 리스트
	 * @param ReplyVO
	 * @return List<ReplyVO>
	 * @throws Exception
	 */
	public List<ReplyVO> replyList(ReplyVO rvo) throws Exception;
			
	/**
	 * 댓글 수정
	 * @param ReplyVO
	 * @return boolean
	 * @throws Exception
	 */
	public boolean replyUpdate(ReplyVO vo) throws Exception;
			
	/**
	 * 댓글 삭제
	 * @param bfr_idx
	 * @return boolean
	 * @throws Exception
	 */
	public boolean replyDelete(int bfr_idx) throws Exception;
			
	/**
	 * 댓글 입력
	 * @param ReplyVO
	 * @return boolean
	 * @throws Exception
	 */
	public boolean replyInsert(ReplyVO vo) throws Exception;
	
}
