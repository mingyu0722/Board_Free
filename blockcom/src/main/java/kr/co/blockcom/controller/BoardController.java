package kr.co.blockcom.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.blockcom.board.service.BoardService;
import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.MemberVO;
import kr.co.blockcom.board.vo.PagingVO;
import kr.co.blockcom.board.vo.ReplyVO;

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
	
	@RequestMapping(value="/member", method=RequestMethod.POST) // = @PostMapping(value="/member")
	//선택된 회원의 mem_idx를 받고 세션에 등록함
	public @ResponseBody String memSession(@RequestBody String mem_idx, HttpSession session) {
		mem_idx = mem_idx.substring(8);		//간략하게 처리할 방법 찾아내기
		session.setAttribute("mem_idx", Integer.parseInt(mem_idx));
		return "Success";
	}
		
	@RequestMapping(value="/boardlist", method=RequestMethod.GET)
	//DB의 board_free table 데이터를 받아 view에 전달.
	public ModelAndView list(@RequestParam int bf_cate_idx, @RequestParam(defaultValue="") String searchCondition, @RequestParam(defaultValue="") String searchValue,
			@RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="5") int perPageNumber, @RequestParam(defaultValue="5") int pageNumber, HttpSession session) 
					throws Exception {
		
		PagingVO pvo = new PagingVO();
		
		pvo.setBf_cate_idx(bf_cate_idx);
		pvo.setSearchCondition(searchCondition);
		pvo.setSearchValue(searchValue);
		pvo.setPage(page);
		pvo.setPerPageNumber(perPageNumber);
		pvo.setPageNumber(pageNumber);
		
		int totalCount = boardService.totalCount(pvo);
		pvo.setTotalCount(totalCount);
		
		List<PagingVO> list = boardService.listAll(pvo);
		
		/*recVo.setBf_cate_idx(bf_cate_idx);
		List<BoardVO> recList = boardService.recList(recVo);*/

		ModelAndView mav = new ModelAndView();
		mav.setViewName("list");		//(jsp filename)
		mav.addObject("list", list);	//(jsp var, return var)
		/*mav.addObject("recList", recList);*/
		mav.addObject("bf_cate_idx", bf_cate_idx);					//bf_cate_idx 1,default = 자유게시판, 2 = 공지사항  
		mav.addObject("memIdx", session.getAttribute("mem_idx"));	//member 선택 시 세션에 등록된 mem_idx
		mav.addObject("totalCount", totalCount);
		mav.addObject("startPage", pvo.getStartPage());
		mav.addObject("endPage", pvo.getEndPage());
		return mav;
	}
	
	@RequestMapping(value="/boardlist", method=RequestMethod.POST)
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
	
	@RequestMapping(value="/boardwrite", method=RequestMethod.GET)
	//ModelAndView 객체에 bf_cate_idx를 담아 view로 return. 글 저장 시 자유게시판/공지사항 구분하기 위함.
	public ModelAndView write(@RequestParam int bf_cate_idx) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("write");
		mav.addObject("bf_cate_idx", bf_cate_idx);
		return mav;
	}
	
	@RequestMapping(value="/boardwrite", method=RequestMethod.POST)
	//view에서 입력된 데이터를 vo객체에 담아 DB로 전달.
	public @ResponseBody String insert(@ModelAttribute BoardVO vo) throws Exception {		//@ModelAttribute 파라미터 값을 확인하여 데이터를 getter/setter에 의해 데이터를 바인딩 해줌
		if(boardService.write(vo) == true) 
			return "true";
		else
			return "false";
	}
	
	@RequestMapping(value="/boardread", method=RequestMethod.GET)
	//bf_idx에 해당하는 글과 댓글을 DB 요청 후 view로 전달. replyList는 해당 idx에 있는 모든 것을 출력하므로 List로 받은 후 List객체를 전달.
	public ModelAndView view(@RequestParam int bf_idx, HttpSession session) throws Exception {
		
		int currentMemeberIdx = (Integer) session.getAttribute("mem_idx");
		BoardVO vo = boardService.read(bf_idx);
		ModelAndView mav = new ModelAndView();
		List<ReplyVO> replyList = boardService.replyList(bf_idx);
		String use_sec = vo.getUse_sec();
		mav.setViewName("read");
		System.out.println(use_sec);
		if(use_sec.equals("Y")) {
			if(currentMemeberIdx == vo.getMem_idx() || currentMemeberIdx == 1) {
				mav.addObject("read", vo);
				mav.addObject("preArticle", boardService.preArticle(bf_idx));
				mav.addObject("nextArticle", boardService.nextArticle(bf_idx));
				mav.addObject("replyList", replyList);
				mav.addObject("memIdx", currentMemeberIdx);
				boardService.viewCnt(bf_idx, session);
				return mav;
			}
			else {
				vo.setBf_contents("비밀글입니다.");
				mav.addObject("read", vo);
				return mav;
			}	
		}
		
		else{
			mav.setViewName("read");
			mav.addObject("read", vo);
			mav.addObject("preArticle", boardService.preArticle(bf_idx));
			mav.addObject("nextArticle", boardService.nextArticle(bf_idx));
			mav.addObject("replyList", replyList);
			mav.addObject("memIdx", currentMemeberIdx);
			boardService.viewCnt(bf_idx, session);
			return mav;
		}
	}
	
	@RequestMapping(value="/boardread", method=RequestMethod.POST)
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
	
	@RequestMapping(value="/boardupdate", method=RequestMethod.GET)
	//게시글 수정화면에 기본적인 값(작성일자, 작성자, 제목)을 전달.
	public ModelAndView upView(@RequestParam int bf_idx, HttpSession session) throws Exception {
		BoardVO vo = boardService.read(bf_idx);
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
	
	@RequestMapping(value="/boardupdate", method=RequestMethod.POST)
	//bf_idx를 전달받은 update page이동 후 수정된 내용을 vo 객체에 담아 DB로 전달. update 성공 시 true 반환 및 bf_idx해당 read page로 이동.
	public @ResponseBody String update(@ModelAttribute BoardVO vo, HttpSession session) throws Exception {
		if(boardService.update(vo) == true)
			return "true";
		else
			return "false";
	}
	
	@RequestMapping(value="/replyInsert", method=RequestMethod.POST)
	//vo객체에 bf_idx, bfr_contents, mem_idx(세션에 등록된 mem_idx)를 담아 DB에 insert. 성공 시 true return.
	public @ResponseBody String replyInsert(@ModelAttribute ReplyVO vo) throws Exception {
		if(boardService.replyInsert(vo) == true) 
			return "true";
		else
			return "false";
	}
	
	@RequestMapping(value="/replyUpdate", method=RequestMethod.POST)
	public @ResponseBody String replyUpdate(@ModelAttribute ReplyVO vo) throws Exception {
		if(boardService.replyUpdate(vo) == true)
			return "true";
		else
			return "false";
	}
	
	@RequestMapping(value="/replyDelete", method=RequestMethod.POST)
	//해당 댓글의 bfr_idx, bfr_contents를 DB에 update. 성공 시 true return.
	public @ResponseBody String replyDelete(@ModelAttribute ReplyVO vo,  HttpSession session) throws Exception {
		int currentMemeberIdx = (Integer)session.getAttribute("mem_idx");
		int bfr_idx = vo.getBfr_idx();
		System.out.println(currentMemeberIdx+" "+vo.getMem_idx()+" "+vo.getBfr_idx());
		if(currentMemeberIdx == vo.getMem_idx() || currentMemeberIdx == 1) {
			if(boardService.replyDelete(bfr_idx) == true)
				return "true";
			else
				return "false";
		}
		else
			return "Auth";
	}
	
	/*@PostMapping(value="/board/search")
	public ModelAndView searchList(@ModelAttribute BoardVO vo) throws Exception {
		String searchCondition = vo.getSearchCondition();
		if(searchCondition == "1")
			vo.setSearchCondition("bf_title");
		else if(searchCondition == "2")
			vo.setSearchCondition("reg_date");
		else if(searchCondition == "3")
			vo.setSearchCondition("mem_name");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("list");
		mav.addObject("searchList", boardService.searchList(vo));
		return mav;
	}*/
	
//	/*@RequestMapping(value="/replyList", method=RequestMethod.POST)
//	public ModelAndView replyList(@RequestBody int bf_idx) throws Exception {
//		List<BoardVO> replyList = boardService.replyList(bf_idx);
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("read");		//(jsp filename)
//		mav.addObject("replyList", replyList);	//(jsp var, return var)
//		return mav;
//	}*/ 
	//댓글 리스트 불러오는 컨트롤러를 새로 만드는 것이 아니라 READ를 불러올 때 같이 불러오므로 READ 컨트롤러에 함께 작성
}
