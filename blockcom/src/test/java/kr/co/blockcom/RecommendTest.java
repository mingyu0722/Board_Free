package kr.co.blockcom;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import kr.co.blockcom.board.mapper.BoardMapper;
import kr.co.blockcom.board.service.BoardService;
import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.PagingVO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})

public class RecommendTest {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardMapper mapper;
	
	@Test
	public void recommendTest() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setBf_idx(38);
		vo.setMem_idx(2);
		
		
		System.out.println(mapper.recommend_flag(vo));
	}
	
}
