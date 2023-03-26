package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

import db.JdbcUtil;
import vo.BoardBean;

public class BoardDAO {
	/*single tone*/
	private BoardDAO() {}
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private Connection con;
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	public int insertBoard(BoardBean board) {
		System.out.println("BoardDAO - insertBoard()");
		int insertCount = 0;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs  = null;
		
		try {
			int board_num = 1;
			String sql = "SELECT MAX(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board_num = rs.getInt(1) + 1;
			}
			
				String sql2 = "INSERT INTO board VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";
				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setInt(1, board_num);
				pstmt2.setString(2, board.getBoard_name());
				pstmt2.setString(3, board.getBoard_pass());
				pstmt2.setString(4, board.getBoard_subject());
				pstmt2.setString(5, board.getBoard_content());
				pstmt2.setString(6, board.getBoard_file());
				pstmt2.setInt(7, board_num);
				pstmt2.setInt(8, 0);
				pstmt2.setInt(9, 0);
				pstmt2.setInt(10, 0);
				
				System.out.println(pstmt);
				System.out.println(pstmt2);
				
				insertCount = pstmt2.executeUpdate();
				// TODO Auto-generated catch block
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(pstmt);
			
			
		}
		
		
		return insertCount;
	}

	public List<BoardBean> selectBoardList(int startRow, int listLimit) {
		List<BoardBean> boardList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM board order by board_re_ref desc,"
					+ "board_re_seq asc LIMIT ?, ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			rs = pstmt.executeQuery();
			
			boardList = new ArrayList<BoardBean>();
			
			
			while(rs.next()) {
				BoardBean board = new BoardBean();
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_name(rs.getString("board_name"));
				board.setBoard_pass(rs.getString("board_pass"));
				board.setBoard_subject(rs.getString("board_subject"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_file(rs.getString("board_file"));
				board.setBoard_re_ref(rs.getInt("board_re_ref"));
				board.setBoard_re_lev(rs.getInt("board_re_lev"));
				board.setBoard_re_seq(rs.getInt("board_re_seq"));
				board.setBoard_readcount(rs.getInt("board_readcount"));
				board.setBoard_date(rs.getTimestamp("board_date"));
				
				System.out.println(board);
				
				boardList.add(board);
				
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectBoardList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
		
		
		
		return boardList;
	}

	public int selectBoardListCount() {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT COUNT(*) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectBoardListCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
		return listCount;
	}

	public BoardBean selectBoard(int board_num) {
		BoardBean board = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		String sql = "SELECT * FROM board WHERE board_num = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, board_num);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			board = new BoardBean();
			board.setBoard_num(rs.getInt("board_num"));
			board.setBoard_name(rs.getString("board_name"));
//			board.setBoard_pass(rs.getString("board_pass"));
			board.setBoard_subject(rs.getString("board_subject"));
			board.setBoard_content(rs.getString("board_content"));
			board.setBoard_file(rs.getString("board_file"));
			board.setBoard_re_ref(rs.getInt("board_re_ref"));
			board.setBoard_re_lev(rs.getInt("board_re_lev"));
			board.setBoard_re_seq(rs.getInt("board_re_seq"));
			board.setBoard_readcount(rs.getInt("board_readcount"));
			board.setBoard_date(rs.getTimestamp("board_date"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		JdbcUtil.close(rs);
		JdbcUtil.close(pstmt);
	}
		
		return board;
	}

	// 조회수 증가
		public int updateReadcount(int board_num) {
			int updateCount = 0;
			
			PreparedStatement pstmt = null;
			
			try {
				// 글번호에 해당하는 레코드의 readcount 값을 1만큼 증가 - UPDATE
				String sql = "UPDATE board "
								+ "SET board_readcount = board_readcount + 1 "
								+ "WHERE board_num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, board_num);
				updateCount = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("SQL 구문 오류 발생! - updateReadcount()");
				e.printStackTrace();
			} finally {
				JdbcUtil.close(pstmt);
			}
			
			return updateCount;
		}

	
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
//	public List<BoardBean> selectBoard(BoardBean board) {
//		List<BoardBean> boardDetail = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//			try {
//				String sql = "SELECT * FROM board WHERE board_num = ?";
//				pstmt = con.prepareStatement(sql);
//				rs = pstmt.executeQuery();
//				pstmt.setInt(1, board.getBoard_num());
//				boardDetail = new ArrayList<BoardBean>();
//				while(rs.next()) {
//					BoardBean board1 = new BoardBean();
//					board1.setBoard_num(rs.getInt("board_num"));
//					board1.setBoard_name(rs.getString("board_name"));
//					board1.setBoard_pass(rs.getString("board_pass"));
//					board1.setBoard_subject(rs.getString("board_subject"));
//					board1.setBoard_content(rs.getString("board_content"));
//					board1.setBoard_file(rs.getString("board_file"));
//					board1.setBoard_re_ref(rs.getInt("board_re_ref"));
//					board1.setBoard_re_lev(rs.getInt("board_re_lev"));
//					board1.setBoard_re_seq(rs.getInt("board_re_seq"));
//					board1.setBoard_readcount(rs.getInt("board_readcount"));
//					board1.setBoard_date(rs.getTimestamp("board_date"));
//					System.out.println(board1);
//					boardDetail.add(board1);
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}finally {
//				JdbcUtil.close(rs);
//				JdbcUtil.close(pstmt);
//			}
//			
//		
//		
//		return boardDetail;
//	}

	
	
}
