package kr.co.blockcom.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetSupportingSqlParameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.blockcom.board.service.BoardService;
import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.MemberVO;
import kr.co.blockcom.board.vo.PagingVO;
import kr.co.blockcom.board.vo.ReplyVO;
import kr.co.blockcom.board.vo.UploadVO;

@Controller
@RequestMapping("/")
public class BoardController {
	
	@Autowired		//특정기능을 수행하기 위한 해당 빈을 사용하기 위해 특정 프로퍼티를 자동으로 연결해준다.
	private BoardService boardService;
	
	//@RequestMapping(value="/member") //@RequestMappin method default = GET
	//DB의 member table로부터 회원리스트를 받아와 view 전달
	@GetMapping(value="/member")  
	public ModelAndView member() throws Exception {
		List<MemberVO> memList = boardService.member();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member");		//(jsp filename)
		mav.addObject("member", memList);	//(jsp var, return var)
		return mav;
	}
	
	@PostMapping(value="/member")
	//선택된 회원의 mem_idx를 받고 세션에 등록함
	public @ResponseBody String memSession(@RequestBody String mem_idx, HttpSession session) {
		mem_idx = mem_idx.substring(8);		//간략하게 처리할 방법 찾아내기
		session.setAttribute("mem_idx", Integer.parseInt(mem_idx));
		return "Success";
	}
	
	@GetMapping(value="/boardlist")
	//DB의 board_free table 데이터를 받아 view에 전달.
	public ModelAndView list(@RequestParam int bf_cate_idx, @RequestParam(defaultValue="") String searchCondition, @RequestParam(defaultValue="") String searchValue,
			@RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10") int perPageNumber, @RequestParam(defaultValue="5") int blockSize, HttpSession session) 
					throws Exception {
		
		PagingVO pvo = new PagingVO();
		BoardVO vo = new BoardVO();
		
		pvo.setBf_cate_idx(bf_cate_idx);
		pvo.setSearchCondition(searchCondition);
		pvo.setSearchValue(searchValue);
		pvo.setPage(page);
		pvo.setPerPageNumber(perPageNumber);
		pvo.setBlockSize(blockSize);
		
		List<BoardVO> recList = boardService.recommendedList(bf_cate_idx);
		List<PagingVO> list = boardService.listAll(pvo);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("list");		//(jsp filename)
		mav.addObject("list", list);	//(jsp var, return var)
		mav.addObject("recList", recList);
		mav.addObject("bf_cate_idx", bf_cate_idx);					//bf_cate_idx 1,default = 자유게시판, 2 = 공지사항  
		mav.addObject("memIdx", session.getAttribute("mem_idx"));	//member 선택 시 세션에 등록된 mem_idx
		mav.addObject("totalCount", pvo.getTotalCount());
		mav.addObject("page", pvo.getPage());
		mav.addObject("startPage", pvo.getStartPage());
		mav.addObject("endPage", pvo.getTempEndPage());
		mav.addObject("prev", pvo.isPrev());
		mav.addObject("next", pvo.isNext());
		mav.addObject("perPageNumber", pvo.getPerPageNumber());
		mav.addObject("blockSize", pvo.getBlockSize());
		return mav;
	}
	
	@PostMapping(value="/boardlist")
	//use_sec이 'Y'인 경우 게시글의 mem_idx와 현재 세션에 등록된 mem_idx를 비교하여 읽기 권한 판별.(admin mem_idx = 1)
	public @ResponseBody String readAuth(@RequestBody String mem_idx, HttpSession session) throws Exception {
		int memIdx = Integer.parseInt(mem_idx.substring(8)); 
		int currentMemeberIdx = (Integer)session.getAttribute("mem_idx");
		if(memIdx == (Integer)session.getAttribute("mem_idx") || currentMemeberIdx == 1) {
			return "true";
		}	
		else
			return "false"; //Controller에서 return 값이 String인 경우 @ResponseBody가 없으면 "return값.jsp" 파일을 찾는다.
	}
	
	@GetMapping(value="/boardwrite")
	//ModelAndView 객체에 bf_cate_idx를 담아 view로 return. 글 저장 시 자유게시판/공지사항 구분하기 위함.
	public ModelAndView write(@RequestParam int bf_cate_idx) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("write");
		mav.addObject("bf_cate_idx", bf_cate_idx);
		return mav;
	}
	
	@PostMapping(value="/boardwrite")
	//view에서 입력된 데이터를 vo객체에 담아 DB로 전달.
	public @ResponseBody String insert(@ModelAttribute UploadVO uvo) throws Exception {
		System.out.println(uvo.getFile().getOriginalFilename());
		BoardVO vo = new BoardVO();
		vo.setBf_cate_idx(uvo.getBf_cate_idx());
		vo.setBf_title(uvo.getBf_title());
		vo.setBf_contents(uvo.getBf_contents());
		vo.setMem_idx(uvo.getMem_idx());
		vo.setUse_sec(uvo.getUse_sec());
		
		if(boardService.write(vo) == true) 
			return "true";
		else
			return "false";
	}

	@GetMapping(value="/boardread")
	//bf_idx에 해당하는 글과 댓글을 DB 요청 후 view로 전달. replyList는 해당 idx에 있는 모든 것을 출력하므로 List로 받은 후 List객체를 전달.
	public ModelAndView view(@RequestParam int bf_idx, @RequestParam int rpage, HttpSession session) throws Exception {
		
		int currentMemeberIdx = (Integer) session.getAttribute("mem_idx");
		BoardVO vo = new BoardVO();
		vo.setBf_idx(bf_idx);
		vo.setMem_idx(currentMemeberIdx);
		BoardVO readVo = boardService.read(vo);
		
		ReplyVO rvo = new ReplyVO();
		rvo.setBf_idx(bf_idx);
		rvo.setPage(rpage);
		rvo.setPerPageNumber(5);
		rvo.setBlockSize(5);
		
		List<ReplyVO> replyList = boardService.replyList(rvo);
		String use_sec = readVo.getUse_sec();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("read");
		
		if(use_sec.equals("Y")) {
			if(currentMemeberIdx != readVo.getMem_idx() && currentMemeberIdx != 1) {
				vo.setBf_contents("비밀글입니다.");
				mav.addObject("read", vo);
				return mav;
			}	
		}
		
		mav.addObject("read", readVo);
		mav.addObject("preArticle", boardService.preArticle(bf_idx));
		mav.addObject("nextArticle", boardService.nextArticle(bf_idx));
		mav.addObject("replyList", replyList);
		mav.addObject("memIdx", currentMemeberIdx);
		mav.addObject("rec_flag", boardService.recommend_flag(vo));
		mav.addObject("rpage", rvo.getPage());
		mav.addObject("startPage", rvo.getStartPage());
		mav.addObject("endPage", rvo.getTempEndPage());
		mav.addObject("prev", rvo.isPrev());
		mav.addObject("next", rvo.isNext());
		return mav;
	}
	
	@PostMapping(value="/boardread")
	//bf_idx에 해당하는 데이터를 DB에서 삭제 후 성공 시 true 반환 및 게시글 목록으로 이동.
	public @ResponseBody String delete(@ModelAttribute BoardVO vo, HttpSession session) throws Exception {
		int currentMemeberIdx = (Integer)session.getAttribute("mem_idx");
		if(currentMemeberIdx == vo.getMem_idx() || currentMemeberIdx == 1) {
			if(boardService.delete(vo.getBf_idx()) == true)
				return "true";
			else
				return "false";
		}
		else
			return "Auth";
	}
	
	@PostMapping(value="/recommend")
	public @ResponseBody void recommend(@ModelAttribute BoardVO vo, HttpSession session) throws Exception {
		int currentMemeberIdx = (Integer)session.getAttribute("mem_idx");
		vo.setMem_idx(currentMemeberIdx);
		boardService.recommend(vo);
	}
	
	@PostMapping(value="/delRecommend")
	public @ResponseBody boolean delRecommend(@ModelAttribute BoardVO vo) throws Exception {
		if(boardService.delRecommend(vo))
			return true;
		else
			return false;

	}
	
	@GetMapping(value="/boardupdate")
	//게시글 수정화면에 기본적인 값(작성일자, 작성자, 제목)을 전달.
	public ModelAndView upView(@RequestParam int bf_idx, HttpSession session) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setBf_idx(bf_idx);
		vo = boardService.read(vo);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("update");
		int currentMemberIdx = (Integer)session.getAttribute("mem_idx");
		if(currentMemberIdx == vo.getMem_idx() || currentMemberIdx == 1) {
			mav.addObject("read", vo);
			return mav;
		}
		else {
			vo.setBf_contents("수정할 수 없습니다.");
			mav.addObject("read", vo);
			return mav;
		}
		/*ModelAndView mav = new ModelAndView();
		mav.setViewName("update");
		mav.addObject("read", boardService.read(bf_idx));
		return mav;*/
	}
	
	@PostMapping(value="/boardupdate")
	//bf_idx를 전달받은 update page이동 후 수정된 내용을 vo 객체에 담아 DB로 전달. update 성공 시 true 반환 및 bf_idx해당 read page로 이동.
	public @ResponseBody String update(@ModelAttribute BoardVO vo, HttpSession session) throws Exception {
		if(boardService.update(vo) == true)
			return "true";
		else
			return "false";
	}
	
	@PostMapping(value="/replyInsert")
	//vo객체에 bf_idx, bfr_contents, mem_idx(세션에 등록된 mem_idx)를 담아 DB에 insert. 성공 시 true return.
	public @ResponseBody String replyInsert(@ModelAttribute ReplyVO vo) throws Exception {
		if(boardService.replyInsert(vo) == true) 
			return "true";
		else
			return "false";
	}
	
	@PostMapping(value="/replyUpdate")
	public @ResponseBody String replyUpdate(@ModelAttribute ReplyVO vo) throws Exception {
		if(boardService.replyUpdate(vo) == true)
			return "true";
		else
			return "false";
	}
	
	@PostMapping(value="/replyDelete")
	//해당 댓글의 bfr_idx, bfr_contents를 DB에 update. 성공 시 true return.
	public @ResponseBody String replyDelete(@ModelAttribute ReplyVO vo,  HttpSession session) throws Exception {
		int currentMemeberIdx = (Integer)session.getAttribute("mem_idx");
		int bfr_idx = vo.getBfr_idx();
		if(currentMemeberIdx == vo.getMem_idx() || currentMemeberIdx == 1) {
			if(boardService.replyDelete(bfr_idx) == true)
				return "true";
			else
				return "false";
		}
		else
			return "Auth";
	}
}
