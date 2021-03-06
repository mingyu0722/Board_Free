package kr.co.blockcom.board.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.MemberVO;
import kr.co.blockcom.board.vo.PagingVO;
import kr.co.blockcom.board.vo.ReplyVO;

@Repository
public interface BoardMapper {

	/**
	 * 게시물 전체 개수
	 * 
	 * @param PagingVO
	 * @return int
	 * @throws Exception
	 */
	public int totalCount(PagingVO vo) throws Exception;

	/**
	 * 추천수
	 * 
	 * @param bf_cate_idx
	 * @return int
	 * @throws Exception
	 */
	public int recommendCount(int bf_cate_idx) throws Exception;
	
	/**
	 * 게시글 당 추천 수
	 * @param bf_idx
	 * @return int
	 * @throws Exception
	 */
	public int recCntPerList(int bf_idx) throws Exception;

	/**
	 * 추천글
	 * 
	 * @param bf_cate_idx
	 * @return List<BoardVO>
	 * @throws Exception
	 */
	public List<BoardVO> recommendedList(int bf_cate_idx) throws Exception;

	/**
	 * 전체 게시물
	 * 
	 * @param PagingVO
	 * @return List<PagingVO>
	 * @throws Exception
	 */
	public List<PagingVO> listAll(PagingVO vo) throws Exception;

	/**
	 * 글작성
	 * 
	 * @param BoardVO
	 * @return boolean
	 * @throws Exception
	 */
	public boolean insert(BoardVO vo) throws Exception;

	/**
	 * 게시글 상세
	 * 
	 * @param bf_idx
	 * @return BoardVO
	 * @throws Exception
	 */
	public BoardVO read(BoardVO vo) throws Exception;

	/**
	 * 추천
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public void recommend(BoardVO vo) throws Exception;

	/**
	 * 추천여부확인
	 * 
	 * @param vo
	 * @return int
	 * @throws Exception
	 */
	public int recommend_flag(BoardVO vo) throws Exception;

	/**
	 * 추천 취소
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public int del_recommend(BoardVO vo) throws Exception;

	/**
	 * 글 삭제 시 해당 추천 해제
	 * 
	 * @param bf_idx
	 * @throws Exception
	 */
	public void del_recommend_all(int bf_idx) throws Exception;

	/**
	 * 이전글
	 * 
	 * @param bf_idx
	 * @return BoardVO
	 * @throws Exception
	 */
	public BoardVO preArticle(BoardVO vo) throws Exception;

	/**
	 * 다음글
	 * 
	 * @param bf_idx
	 * @return BoardVO
	 * @throws Exception
	 */
	public BoardVO nextArticle(BoardVO vo) throws Exception;

	/**
	 * 글 삭제
	 * 
	 * @param bf_idx
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delete(int bf_idx) throws Exception;

	/**
	 * 글 수정
	 * 
	 * @param BoardVO
	 * @return boolean
	 * @throws Exception
	 */
	public boolean update(BoardVO vo) throws Exception;

	/**
	 * 검색결과 리스트
	 * 
	 * @param BoardVO
	 * @return List<BoardVO>
	 * @throws Exception
	 */
	public List<BoardVO> searchList(BoardVO vo) throws Exception;

	/**
	 * 조회수 증가
	 * 
	 * @param BoardVO
	 * @throws Exception
	 */
	public void viewCnt(BoardVO vo) throws Exception;

}
