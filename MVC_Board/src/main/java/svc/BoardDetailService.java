//package svc;
//
//import java.sql.Connection;
//
//import dao.BoardDAO;
//import db.JdbcUtil;
//import vo.BoardBean;
//
//public class BoardDetailService {
//
//	public BoardBean getBoard(int board_num) {
//		BoardBean board = new BoardBean();
//		Connection con = JdbcUtil.getConnection();
//		BoardDAO dao = BoardDAO.getInstance();
//		dao.setConnection(con);
//		board = dao.selectBoard(board_num);
//		if(board != null) {
//			int updateCount = dao.updateReadcount(board_num);
//			if(updateCount > 0) {
//				JdbcUtil.commit(con);
//				board.setBoard_readcount(board.getBoard_readcount() + 1);
//			} else {
//				JdbcUtil.rollback(con);
//			}
//		}
//		JdbcUtil.close(con);
//		return board;
//	}
//
//	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
////	public List<BoardBean> getBoard() {
////		BoardBean board = new BoardBean();
////		Connection con = JdbcUtil.getConnection();
////		BoardDAO dao = BoardDAO.getInstance();
////		dao.setConnection(con);
////		board = dao.selectBoard(board);
////		JdbcUtil.close(con);
////		return board;
////	}
//
//
//}


package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardDetailService {
	
	// 글 상세정보 조회 요청 작업 수행할 getBoard() 메서드 정의
	public BoardBean getBoard(int board_num) {
		// 1. 리턴값을 저장할 변수 선언
		BoardBean board = null;
		
		// 2. Connection 객체 가져오기(공통)
		Connection con = JdbcUtil.getConnection();
		
		// 3. BoardDAO 객체 가져오기(공통)
		BoardDAO dao = BoardDAO.getInstance();
		
		// 4. BoardDAO 객체에 Connection 객체 전달하기(공통)
		dao.setConnection(con);
		
		// 5. BoardDAO 객체의 selectBoard() 메서드 호출
		// => 파라미터 : 글번호   리턴타입 : BoardBean(board)
		board = dao.selectBoard(board_num);
		
		// 6. 리턴받은 BoardBean 객체가 존재할 경우
		//    BoardDAO 객체의 updateReadcount() 메서드를 호출하여 조회수 증가 작업 수행
		// => 파라미터 : 글번호   리턴타입 : int(updateCount)
		if(board != null) {
			int updateCount = dao.updateReadcount(board_num);
			
			// 조회수 증가 작업이 성공했을 경우 commit, 아니면 rollback
			if(updateCount > 0) {
				JdbcUtil.commit(con);
				
				// 조회수 증가 전 게시물 상세 정보 조회 작업을 먼저 수행했으므로
				// 증가된 조회수가 반영되지 않는 상태이다!
				// 따라서, 조회수 증가 작업이 성공했을 경우 
				// BoardBean 객체 내의 조회수를 1만큼 증가시켜야 한다!
				board.setBoard_readcount(board.getBoard_readcount() + 1);
			} else {
				JdbcUtil.rollback(con);
			}
		}
		
		// 6. Connection 객체 반환하기(공통)
		JdbcUtil.close(con);
		
		// 7. 작업 요청 처리 결과 리턴
		return board;
	}

}

