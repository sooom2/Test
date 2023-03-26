package svc;

import java.sql.Connection;
import java.util.List;

import dao.MemberDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class AdminMemberListService {

	// 회원 목록 작업을 요청하는 getMemberList() 메서드 정의
	public List<MemberBean> getMemberList() {
		// 1. 회원 목록을 저장할 변수 선언(List<MemberBean> 타입 memberList)
		List<MemberBean> memberList = null;
		
		// 2. Connection 객체 가져오기(공통)
		Connection con = JdbcUtil.getConnection();
		
		// 3. MemberDAO 객체 가져오기(공통)
		MemberDAO dao = MemberDAO.getInstance();
		
		// 4. MemberDAO 객체에 Connection 객체 전달하기(공통)
		dao.setConnection(con);
		
		// 5. MemberDAO 객체의 selectMemberList() 메서드를 호출하여 회원 목록 조회 작업 요청
		// => 파라미터 : 없음   리턴타입 : List<MemberBean>
		memberList = dao.selectMemberList();
		
		// 6. Connection 객체 반환하기(공통)
		JdbcUtil.close(con);
		
		// 7. 회원 목록 조회 결과 리턴
		return memberList; // AdminMemberListAction 으로 리턴
	}
	
}
