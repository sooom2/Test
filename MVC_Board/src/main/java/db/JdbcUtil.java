package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.cj.protocol.Resultset;

public class JdbcUtil {
	public static Connection getConnection() {
		Connection con = null;
		try {
			Context initCtx = new InitialContext();
//	 	Context envCtx = (Context)initCtx.lookup("java:comp/env");
//	 	DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL");
			DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/MySQL");
			con = ds.getConnection();
			
			con.setAutoCommit(false); // 트랜잭션 처리를 위해 Auto Commit 기능 끔
			// -> 때문에 이후로 DML 및 DDL 작업 수행 후 반드시 커밋 작업을 수동으로 실행해야함
			// -> 이전 상태로 되돌리는 롤백 작업도 수동으로 실행해야함.
			
			
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	// 디비 사용이 끝난 객체를 반환하기 위해 close()메서드 정의
	public static void close(Connection con) {
		try {
			if(con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 트랜잭션 처리에 필요한 커밋, 롤백 작업을 수행할 메서드 정의
	// connection 객체에 대해 Auto Commit 기능 해제가 선행되어야함
	// connection 객체의 commit(), rollback() 메서드 사용
	public static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
