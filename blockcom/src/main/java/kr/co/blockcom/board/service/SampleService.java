package kr.co.blockcom.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.blockcom.board.dao.SampleDao;
import kr.co.blockcom.board.vo.SampleVO;

@Service
public class SampleService {

	
	@Autowired
	private SampleDao sampleDao;
	
	
	public SampleVO getOne() {
		
		
		return sampleDao.select();
	}
	
}
