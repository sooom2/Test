package svc;

import java.sql.Connection;

import action.MemberLoginProAction;
import dao.MemberDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class MemberJoinProService {
	public boolean registMember(MemberBean member) {
		boolean isRegistSuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		
//		MemberDAO dao = new MemberDAO(); 		
		//-> single tone으로 구현되어 생성된 인스턴트를 리턴받아야함
		MemberDAO dao = MemberDAO.getInstance();
		
		dao.setConnection(con);
		
		int insertCount = dao.insertMember(member);
		
		if(insertCount > 0) {
			JdbcUtil.commit(con);
			
			isRegistSuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		
		return isRegistSuccess; 
	}

}
