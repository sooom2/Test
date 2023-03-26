package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.MemberLoginProService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberLoginProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("MemberLoginProAction");
		
		ActionForward forward = null;
		
		// 폼으로 전달받은 아이디, 패스워드 가져와서 MemberBean 객체에 저장
		MemberBean member = new MemberBean();
		member.setId(request.getParameter("id"));
		member.setPasswd(request.getParameter("passwd"));
//		System.out.println(member);
		
		// MemberLoginProService - isCorrectUser() 메서드 호출하여 로그인 판별 작업 요청
		// => 파라미터 : MemberBean 객체    리턴타입 : boolean(isLoginSuccess)
		MemberLoginProService service = new MemberLoginProService();
		boolean isLoginSuccess = service.isCorrectUser(member);
		
		if(isLoginSuccess) {
			HttpSession session = request.getSession();
			session.setAttribute("sId", member.getId());
			
			forward = new ActionForward();
			forward.setPath("./");
			forward.setRedirect(true);
		} else {
			try {
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter(); // IOException 예외 처리 필요
				out.println("<script>");
				out.println("alert('로그인 실패!');");
				out.println("history.back();");
				out.println("</script>");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return forward;
	}

}














