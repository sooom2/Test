package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardListAction");
		
		ActionForward forward = null;
		
		int listLimit = 10;
		int pageNum = 1;
		
		if(request.getParameter("pageNum") != null) {
			pageNum =  Integer.parseInt(request.getParameter("pageNum"));
		}
		
		int startRow = (pageNum - 1) * listLimit;
		
		
		// BoardListService 객체를 통해 게시물 목록 조회 후
		// 조회 결과를 request 객체에 저장 후 board_list.jsp 페이지로 포워딩
		// -----------------------------------------------------------------------
		// BoardListService 클래스의 인스턴스 생성 후 
		// getBoardList() 메서드 호출하여 게시물 목록 조회 요청
		// => 파라미터 : 없음    리턴타입 : List<BoardBean>(boardList)
		BoardListService service = new BoardListService();
		List<BoardBean> boardList = service.getBoardList(startRow, listLimit);
//		System.out.println(boardList);
		
		int listCount = service.getBoardListCount();
		System.out.println("총 게시물 수 : " + listCount);
		
		int pageListLimit = 3;
		
		int maxPage = listCount / listLimit + (listCount % listLimit > 0 ? 1 : 0);
	
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1 ;
		
		int endPage = startPage + pageListLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		System.out.println(pageInfo);
		// 글목록(List 객체)과 페이징정보(PageInfo 객체)를 request 객체에 저장
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pageNum", pageNum);
		
		// ActionForward 객체 생성 후 board/board_list.jsp 페이지 포워딩
		// => URL 및 request 객체 유지 = 디스패치
		forward = new ActionForward();
		forward.setPath("board/board_list.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}










