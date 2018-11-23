package kr.co.blockcom.board.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.blockcom.board.vo.MemberVO;

@Repository
public class MemberDaoImpl implements MemberDao {
	
	@Autowired
	private SqlSessionTemplate sessionTemplate;    				//SqlSessionTemplate : 마이바티스 스프링 연동모듈의 핵심. 여러개의 Dao나 Mapper에서 공유할 수 있음
																//Mybatis : SQL, 저장프로시저, 그리고 몇가지 고급 Mapping을 지원하는 프레임워크. JDBC로 처리하는 상당부분의 코드와 파라미터 설정 및 결과 매핑을 대신해줌.
	public List<MemberVO> member() throws Exception {						
		return sessionTemplate.selectList("board.member");
	}

}
