package kr.co.blockcom.board.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/*import kr.co.blockcom.board.dao.BoardDao;
import kr.co.blockcom.board.dao.MemberDao;
import kr.co.blockcom.board.dao.ReplyDao;*/
import kr.co.blockcom.board.mapper.BoardMapper;
import kr.co.blockcom.board.mapper.FileMapper;
import kr.co.blockcom.board.mapper.MemberMapper;
import kr.co.blockcom.board.mapper.ReplyMapper;
import kr.co.blockcom.board.vo.BoardVO;
import kr.co.blockcom.board.vo.FileVO;
import kr.co.blockcom.board.vo.MemberVO;
import kr.co.blockcom.board.vo.PagingVO;
import kr.co.blockcom.board.vo.ReplyVO;
import kr.co.blockcom.board.vo.UploadVO;
import kr.co.blockcom.common.util.BoardFileHandler;
import kr.co.blockcom.common.util.PageMaker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	private final BoardMapper boardMapper;
	private final MemberMapper memberMapper;
	private final ReplyMapper replyMapper;
	private final FileMapper fileMapper; 
	
	@Autowired
	ServletContext servletContext;
	
	@Override
	public List<MemberVO> member() throws Exception {
		return memberMapper.member();
	}
	
	@Override
	public int totalCount(PagingVO vo) throws Exception {
		return boardMapper.totalCount(vo);
	}
	
	public List<BoardVO> recommendedList(int bf_cate_idx) throws Exception {
		return boardMapper.recommendedList(bf_cate_idx);
	}
	
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
		int recTotalCount = boardMapper.recommendCount(pvo.getBf_cate_idx());
		int realTotalCount = totalCount - recTotalCount;
		pvo.setTotalCount(totalCount);
		pvo.setRealTotalCount(realTotalCount);
		
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
	public boolean fileUpload(List<MultipartFile> file, int mem_idx) throws Exception {
		FileVO fvo = new FileVO();
		fvo.setMem_idx(mem_idx);
		int count = 0;
		BoardFileHandler boardFileHandler = new BoardFileHandler(servletContext);
		for(int i=0; i<file.size(); i++) {
			if(file.get(i).getSize() > 0) {
				boardFileHandler.uploadFile(fvo, file.get(i));
				if(fileMapper.fileUpload(fvo) > 0)
					count++;
			}
			else
				count++;
		}		
		if(count == file.size())
			return true;
		else
			return false;
	}
	
	@Override
	public BoardVO read(BoardVO vo) throws Exception {
		boardMapper.viewCnt(vo);
		return boardMapper.read(vo);
	}
	
	@Override
	public List<FileVO> fileDown(int bf_idx) throws Exception {
		List<FileVO> fvo = fileMapper.fileDown(bf_idx);
		if(fvo.size() > 0) {
			for(int i=0; i<fvo.size(); i++) {
				fvo.get(i).setFile_size(fvo.get(i).getFile_size() / 1024);
			}
			return fvo;
		}
		else
			return null;
	}
	
	@Override
	public boolean fileDelete(FileVO fvo) throws Exception {
		
		if(fileMapper.fileDelete(fvo) > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean updateFileUpload(List<MultipartFile> file, int mem_idx, int bf_idx) throws Exception {
		FileVO fvo = new FileVO();
		fvo.setMem_idx(mem_idx);
		fvo.setBf_idx(bf_idx);
		int count = 0;
		BoardFileHandler boardFileHandler = new BoardFileHandler(servletContext);
  		for(int i=0; i<file.size(); i++) {
			if(file.get(i).getSize() > 0) {
				boardFileHandler.uploadFile(fvo, file.get(i));
				if(fileMapper.updateFileUpload(fvo) > 0)
					count++;
			}
			else
				count++;
		}		
		if(count == file.size())
			return true;
		else
			return false;
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
	public boolean delRecommend(BoardVO vo) throws Exception {
		if(boardMapper.del_recommend(vo) > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public BoardVO preArticle(int bf_idx, int bf_cate_idx) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setBf_idx(bf_idx);
		vo.setBf_cate_idx(bf_cate_idx);
		return boardMapper.preArticle(vo);
	}
	
	@Override
	public BoardVO nextArticle(int bf_idx, int bf_cate_idx) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setBf_idx(bf_idx);
		vo.setBf_cate_idx(bf_cate_idx);
		return boardMapper.nextArticle(vo);
	}
	
	@Override
	public boolean delete(int bf_idx) throws Exception {
		if(boardMapper.delete(bf_idx) == true) {
			boardMapper.del_recommend_all(bf_idx);
			return true;
		}
			
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
		rvo.setRealTotalCount(totalCount);
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
