package kr.co.blockcom;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import kr.co.blockcom.board.service.BoardService;
import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.MemberVO;
import kr.co.blockcom.board.vo.ReplyVO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})

//Unit Test의 목적은 Error 유무 판별
public class UnitTest {
	
	@Autowired
	private BoardService boardService;
	
	@Test
	public void memberTest() throws Exception {
		List<MemberVO> memList = boardService.member();
		Assert.assertEquals(4, memList.size());
	}
	
	/*@Test
	public void listAllTest() throws Exception {
		List<BoardVO> list = boardService.listAll(1);		//자유게시판 리스트
		Assert.assertEquals(21, list.size());				//실행마다 값 +1
	}
	
	@Test
	public void listAllTest2() throws Exception {
		List<BoardVO> list = boardService.listAll(2);		//공지사항 리스트
		Assert.assertEquals(1, list.size());
	}*/
	
	@Test
	public void writeTest() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setBf_title("제목");
		vo.setBfr_contents("내용");
		vo.setMem_idx(2);
		vo.setBf_cate_idx(1);
		vo.setUse_sec("N");
		boolean result = boardService.write(vo);
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void readTest() throws Exception {
		BoardVO vo = boardService.read(1);
		Assert.assertEquals(1, vo.getBf_idx());
	}
	
	@Test
	public void deleteTest() throws Exception {
		boolean result = boardService.delete(1);
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void updateTest() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setBf_title("제목수정");
		vo.setBf_contents("내용수정");
		vo.setUse_sec("N");
		vo.setBf_idx(1);
		boolean result = boardService.update(vo);
		Assert.assertEquals(true, result);
	}
	
	/*@Test
	public void viewCntTest() throws Exception {
		
	}*/
	
	/*@Test
	public void replyListTest() throws Exception {
		List<ReplyVO> replyList = boardService.replyList(3);
		Assert.assertEquals(3, replyList.size());
	}*/
	
	@Test
	public void replyUpdateTest() throws Exception {
		ReplyVO vo = new ReplyVO();
		vo.setBfr_contents("댓글수정");
		vo.setBfr_idx(1);
		boolean result = boardService.replyUpdate(vo);
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void replyDelete() throws Exception {
		boolean result = boardService.replyDelete(1);
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void replyInsertTest() throws Exception {
		ReplyVO vo = new ReplyVO();
		vo.setBf_idx(1);
		vo.setBfr_contents("댓글");
		vo.setMem_idx(2);
		boolean result = boardService.replyInsert(vo);
		Assert.assertEquals(true, result);
	}
	
}
