package svc;

import java.sql.Connection;

import dao.MemberDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class MemberLoginProService {

	public boolean isCorrectUser(MemberBean member) {
		boolean isLoginSuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		dao.setConnection(con);
		
		isLoginSuccess = dao.selectCorrectUser(member);
		
		JdbcUtil.close(con);
		
		
		
		return isLoginSuccess;
	}

}
