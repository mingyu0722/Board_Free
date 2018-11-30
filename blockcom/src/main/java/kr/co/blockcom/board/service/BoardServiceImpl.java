package kr.co.blockcom.board.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*import kr.co.blockcom.board.dao.BoardDao;
import kr.co.blockcom.board.dao.MemberDao;
import kr.co.blockcom.board.dao.ReplyDao;*/
import kr.co.blockcom.board.mapper.BoardMapper;
import kr.co.blockcom.board.mapper.MemberMapper;
import kr.co.blockcom.board.mapper.ReplyMapper;
import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.MemberVO;
import kr.co.blockcom.board.vo.PagingVO;
import kr.co.blockcom.board.vo.ReplyVO;
import kr.co.blockcom.common.util.PageMaker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;
	private final MemberMapper memberMapper;
	private final ReplyMapper replyMapper;
	
	@Override
	public List<MemberVO> member() throws Exception {
		return memberMapper.member();
	}
	
	@Override
	public int totalCount(PagingVO vo) throws Exception {
		return boardMapper.totalCount(vo);
	}
	
	/*@Override
	public List<BoardVO> recList(BoardVO vo) throws Exception {
		return boardDao.recList(vo);
	}*/
	
	@Override
	public List<PagingVO> listAll(PagingVO pvo) throws Exception {
		
		String searchCondition = pvo.getSearchCondition();
		if(searchCondition.equals("1")) {		//String은 class이므로 비교연산자 == 으로 비교 불가. String.equals() 사용.
			searchCondition = "bf_title";
			pvo.setSearchCondition(searchCondition);
		}
		else if(searchCondition.equals("2")) {
			searchCondition = "reg_date";
			pvo.setSearchCondition(searchCondition);
		}
		else if(searchCondition.equals("3")) {
			searchCondition = "mem_name";
			pvo.setSearchCondition(searchCondition);
		}
		
		int totalCount = totalCount(pvo);
		pvo.setTotalCount(totalCount);
		
		PageMaker pageMaker = new PageMaker(pvo);
		pvo = pageMaker.setPaging(pvo);
		
		return boardMapper.listAll(pvo);
	}
	
	@Override
	public boolean write(BoardVO vo) throws Exception {
		String title = vo.getBf_title();
		String contents = vo.getBf_contents();
		
		title = title.replace("<", "&lt");
		title = title.replace("<", "&gt");
		
		vo.setBf_title(title);
		vo.setBf_contents(contents);
		if(boardMapper.insert(vo) == true) {
			return true;
		}
			
		else 
			return false;
	}
	
	@Override
	public BoardVO read(BoardVO vo) throws Exception {
		boardMapper.viewCnt(vo);
		return boardMapper.read(vo);
	}
	
	@Override
	public void recommend(BoardVO vo) throws Exception {
		boardMapper.recommend(vo);
	}
	
	@Override
	public boolean recommend_flag(BoardVO vo) throws Exception {
		if(boardMapper.recommend_flag(vo) > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public BoardVO preArticle(int bf_idx) throws Exception {
		return boardMapper.preArticle(bf_idx);
	}
	
	@Override
	public BoardVO nextArticle(int bf_idx) throws Exception {
		return boardMapper.nextArticle(bf_idx);
	}
	
	@Override
	public boolean delete(int bf_idx) throws Exception {
		if(boardMapper.delete(bf_idx) == true)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean update(BoardVO vo) throws Exception {
		if(boardMapper.update(vo) == true)
			return true;
		else
			return false;
	}
	
	@Override
	public List<ReplyVO> replyList(ReplyVO rvo) throws Exception {
		int totalCount = replyMapper.totalCount(rvo.getBf_idx());
		rvo.setTotalCount(totalCount);
		PageMaker pageMaker = new PageMaker(rvo);
		rvo = (ReplyVO)pageMaker.setPaging(rvo);
		return replyMapper.replyList(rvo);
	}
	
	@Override
	public boolean replyInsert(ReplyVO vo) throws Exception {
		String contents = vo.getBfr_contents();
		
		contents = contents.replace("<", "&lt");
		contents = contents.replace("<", "&gt");
		
		vo.setBfr_contents(contents);
		if(replyMapper.replyInsert(vo) == true) {
			return true;
		}
			
		else 
			return false;
	}
	
	@Override
	public boolean replyUpdate(ReplyVO vo) throws Exception {
		if(replyMapper.replyUpdate(vo) == true)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean replyDelete(int bfr_idx) throws Exception {
		if(replyMapper.replyDelete(bfr_idx) == true)
			return true;
		else
			return false;
	}
	
	@Override
	public List<BoardVO> searchList(BoardVO vo) throws Exception {
		return boardMapper.searchList(vo);
	}
}
