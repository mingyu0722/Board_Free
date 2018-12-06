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
	
	/**
	 * 파일 다운로드
	 * @param int
	 * @return List<FileVO>
	 * @throws Exception
	 */
	public List<FileVO> fileDown(int bf_idx) throws Exception;
	
	/**
	 * 파일 삭제(수정화면에서 가능)
	 * @param bf_idx
	 * @param bff_idx
	 * @return int (삭제된 row 수)
	 * @throws Exception
	 */
	public int fileDelete(FileVO fvo) throws Exception;
	
	/**
	 * 파일 수정 시 파일 업로드
	 * @param fvo
	 * @return int
	 * @throws Exception
	 */
	public int updatdFileUpload(FileVO fvo) throws Exception;
}
