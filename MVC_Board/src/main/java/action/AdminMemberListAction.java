package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.AdminMemberListService;
import vo.ActionForward;
import vo.MemberBean;

public class AdminMemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminMemberListAction");
		
		ActionForward forward = null;
		
		// 세션 아이디가 null 이거나 "admin" 이 아닐 경우
		// 자바스크립트를 사용하여 "잘못된 접근입니다!" 출력 후 메인페이지로 포워딩
		HttpSession session = request.getSession();
		
		if(session.getAttribute("sId") == null || !session.getAttribute("sId").equals("admin")) {
			try {
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter(); // IOException 예외 처리 필요
				out.println("<script>");
				out.println("alert('잘못된 접근입니다!');");
				out.println("location.href = './';");
				out.println("</script>");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // 관리자 아이디일 때
			// AdminMemberListService 객체의 getMemberList() 메서드 호출하여 회원목록 조회 요청
			// => 파라미터 : 없음    리턴타입 : List<MemberBean>(memberList)
			AdminMemberListService service = new AdminMemberListService();
			List<MemberBean> memberList = service.getMemberList();
			
			// List 객체를 request 객체에 저장(속성명 : memberList)
			request.setAttribute("memberList", memberList);
			
			// ActionForward 객체를 통해 member/admin_member_list.jsp 페이지로 포워딩(디스패치)
			forward = new ActionForward();
			forward.setPath("member/admin_member_list.jsp");
			forward.setRedirect(false);
		}
		
		return forward;
	}

}














