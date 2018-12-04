package kr.co.blockcom.board.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.FileVO;
import kr.co.blockcom.board.vo.MemberVO;

@Repository
public interface FileMapper {
	
	/**
	 * 파일 업로드
	 * @param FileVO
	 * @return int
	 * @throws Exception
	 */
	public int fileUpload(FileVO fvo) throws Exception;
	
}
