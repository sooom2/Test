package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public class BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardDeleteProAction()");
		
		ActionForward forward = null;
		String board_pass = request.getParameter("board_pass");
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		
		
		
		return forward;
	}

}
