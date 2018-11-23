package kr.co.blockcom.board.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.SampleVO;

@Repository
public class SampleDao {

	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	public SampleVO select() {
		
		SampleVO sampleVO = sessionTemplate.selectOne("sample.select");
		
		return sampleVO;
		
	}
}
