package kr.co.blockcom.board.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.MemberVO;

@Repository
public interface MemberMapper {
	
	/**
	 * 멤버 리스트
	 * @return List<MemberVO>
	 * @throws Exception
	 */
	public List<MemberVO> member() throws Exception;
	
}
