package kr.co.blockcom.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.MemberVO;

@Repository
public interface MemberDao {
	//Select Member
	public List<MemberVO> member() throws Exception;
}
