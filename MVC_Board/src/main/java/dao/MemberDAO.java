package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;
import vo.MemberBean;

/*
 * 실제 비즈니스 로직을 수행하는 DAO 클래스 정의
 * => 각 Service 클래스의 객체에서 DAO 객체에 접근할 때 고유 데이터가 불필요하므로
 *    (즉, 각 객체별로 저장되는 데이터를 별도로 관리할 필요가 없으므로)
 *    DAO 객체를 단 하나만 생성하고, Service 클래스에서 공유하도록
 *    싱글톤 디자인 패턴(Singleton Design Pattern)을 적용하여 클래스를 정의하면
 *    메모리를 효율적으로 사용할 수 있다!
 */
public class MemberDAO {
	/*
	 * -------- 싱글톤 디자인 패턴을 활용한 DAO 인스턴스 생성 및 리턴 작업 ----------
	 * 1. 외부에서 인스턴스 생성이 불가능하도록 생성자를 private 접근제한자로 지정
	 * 2. 자신의 클래스 내에서 직접 인스턴스를 생성하여 멤버변수에 저장
	 *    => 유일한 인스턴스가 되어야 하며, 인스턴스 생성 없이도 변수에 접근 가능하도록
	 *       멤버변수를 static 변수로 선언
	 *    => 외부에서 접근하여 멤버변수 값을 변경할 수 없도록 private 접근제한자로 지정
	 * 3. 생성된 인스턴스를 외부로 리턴하는 Getter 메서드 정의
	 *    => 인스턴스 생성 없이 클래스가 메모리에 로딩될 때 함께 로딩되도록
	 *       static 메서드로 선언(static 변수인 instance 에 접근하기 위해 static 메서드로 정의)
	 *    => 누구나 접근할 수 있도록 public 접근제한자로 지정
	 */
	private MemberDAO() {}
	
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}

	// ------------------------------------------------------------------------------
	// Connection 타입 멤버변수 선언 및 Setter 정의
	// => 외부(Service 클래스)로부터 Connection 객체를 전달받아 저장해두기 위함
	private Connection con;
	
	public void setConnection(Connection con) {
		this.con = con;
	}
	// ------------------------------------------------------------------------------

	// 회원 가입 작업을 위한 insertMember() 메서드 정의
	// => 파라미터 : MemberBean 객체(member)   리턴타입 : int(insertCount)
	public int insertMember(MemberBean member) {
		System.out.println("MemberDAO - insertMember()");
		
		int insertCount = 0;
		
		// Connection 객체 가져오기
//		Connection con = JdbcUtil.getConnection();
		// => Service 클래스가 관리하므로 DAO 클래스에서는 Connection 객체 사용만 해야한다!
		//    Connection 객체를 전달받아 저장할 setConnection() 메서드를 통해 객체 저장 후
		//    각 작업을 수행하는 메서드에서는 해당 멤버변수에 접근해여 Connection 객체 사용
		
		// PreparedStatement 타입 변수 선언
		PreparedStatement pstmt = null;
		
		try {
			// mvc_board3.member 테이블에 회원 정보 INSERT
			// => 단, 회원번호(idx)는 null 값 전달 시 AUTO_INCREMENT 속성에 의해 번호 자동 증가
			//    가입일(date)은 데이터베이스의 now() 함수 활용
			String sql = "INSERT INTO member VALUES (null, ?, ?, ?, ?, ?, now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getId());
			pstmt.setString(3, member.getPasswd());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getGender());
			
			System.out.println(pstmt);
			
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - insertMember()");
			e.printStackTrace();
		} finally {
			// 자원 반환
			JdbcUtil.close(pstmt);
			
			// 주의! DAO 객체에서 Connection 객체 반환 금지! => Service 객체가 반환하기 때문
//			JdbcUtil.close(con);
		}
		
		return insertCount; // MemberJoinProService 로 리턴
		
	}

	// 로그인 판별 작업을 위한 selectCorrectUser() 메서드 정의
	public boolean selectCorrectUser(MemberBean member) {
		boolean isLoginSuccess = false;
		
		// PreparedStatement, ResultSet 타입 변수 선언
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// SELECT 구문을 사용하여 아이디와 패스워드가 일치하는 레코드 조회
			// => 조회 결과가 있을 경우 로그인 성공 표시로 isLoginSuccess 를 true 로 변경
			String sql = "SELECT * FROM member WHERE id = ? AND passwd = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPasswd());
			
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우 isLoginSuccess 를 true 로 변경
			if(rs.next()) {
				isLoginSuccess = true;
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectCorrectUser()");
			e.printStackTrace();
		} finally {
			// 자원 반환(단, Connection 객체 반환 금지)
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return isLoginSuccess; // MemberLoginProService 로 리턴
	}

	// 회원 목록 조회 작업을 수행하는 selectMemberList() 메서드 정의
	public List<MemberBean> selectMemberList() {
		List<MemberBean> memberList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			memberList = new ArrayList<MemberBean>();
			
			while(rs.next()) {
				MemberBean member = new MemberBean();
				member.setIdx(rs.getInt("idx"));
				member.setName(rs.getString("name"));
				member.setId(rs.getString("id"));
				member.setEmail(rs.getString("email"));
				member.setGender(rs.getString("gender"));
				member.setDate(rs.getDate("date"));
				
				memberList.add(member);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
		
		return memberList; // AdminMemberListService 로 리턴
	}
	
}





















