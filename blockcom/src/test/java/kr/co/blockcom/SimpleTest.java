package kr.co.blockcom;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import kr.co.blockcom.board.service.BoardService;
import kr.co.blockcom.board.vo.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})

public class SimpleTest {
	
	@Autowired
	private BoardService boardService;
	
	/*@Test
	public void listAllTest() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setSearchCondition("mem_name");
		vo.setSearchValue("사나");
		vo.setBf_cate_idx(1);
		boolean result = false;
		List<BoardVO> list = boardService.listAll(vo);
		System.out.println(list.size());
		if(boardService.searchList(vo) != null)
			result = true;
		Assert.assertEquals(true, result);
	}*/
	
	@Test
	public void preArticleTest() throws Exception {
		BoardVO vo = boardService.preArticle(17);
		Assert.assertEquals(14, vo.getBf_idx());
	}
}
