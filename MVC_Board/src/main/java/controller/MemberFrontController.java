package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.AdminMemberListAction;
import action.MemberJoinProAction;
import action.MemberJoinProAction_Backup;
import action.MemberLoginProAction;
import action.MemberLogoutAction;
import action.MemberUpdateProAction;
import vo.ActionForward;

// *.me 서블릿 패턴에 대한 요청을 모두 처리하는 MemberFrontController 클래스 정의 - 서블릿
@WebServlet("*.me")
public class MemberFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MemberFrontController");
		
		// POST 방식 요청에 대한 한글 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		
		// 공통으로 사용할 변수 선언
		Action action = null; // XXXAction 객체를 공통으로 관리할 Action 인터페이스 타입
		ActionForward forward = null; // 포워딩 정보를 저장할 ActionForward 타입
		
		// 서블릿 주소 추출
		String command = request.getServletPath();
		System.out.println("command : " + command);
		
		// ----------------------------------------------------------------------
		// 서블릿 주소 판별 후 컨트롤 작업 수행
		if(command.equals("/MemberJoinForm.me")) { // 회원 가입 폼
			// 포워딩 대상이 뷰페이지(*.jsp) 인 경우 Dispatch 방식 포워딩(별다른 비즈니스 로직 불필요)
			// (서블릿 -> 뷰페이지 포워딩 : Dispatch 방식)
			// 현재 서블릿 주소(= 요청 주소)가 루트(http://localhost:8080/MVC_Board/MemberJoinForm.me)이다.
			// => webapp 폴더(= 루트) 내의 member 폴더에 있는 member_join_form.jsp 페이지로 이동
			// 공통으로 포워딩 정보를 관리하는 ActionForward 객체 생성 후 
			// 포워딩 주소(member/member_join_form.jsp)와 포워딩 방식(Dispatch) 설정
			forward = new ActionForward();
			forward.setPath("member/member_join_form.jsp");
			forward.setRedirect(false); // boolean 타입 기본값 false 이므로 생략도 가능
			// => 포워딩 후에도 기존 요청 주소(서블릿 주소)가 그대로 유지됨(= Dispatch 방식 특징)
		} else if(command.equals("/MemberJoinPro.me")) { // 회원 가입 비즈니스 로직
			// 회원 가입 비즈니스 로직 작업 요청
			// 비즈니스 로직을 처리할 Action 클래스의 인스턴스 생성 후 execute() 메서드 호출
			// => 파라미터 : HttpServletRequest(request), HttpServletResponse(response) 객체
			// => 리턴타입 : ActionForward(forward)
//			MemberJoinProAction action = new MemberJoinProAction();
			
			// XXXAction 클래스들은 Action 인터페이스의 구현체(= 서브클래스)이므로
			// 각각의 Action 클래스 타입 변수를 선언하는 대신 
			// 부모 인터페이스 타입인 Action 타입 변수로 업캐스팅하여
			// 공통으로 관리도 가능하다! 
			// => 이 때, 호출되는 메서드도 상속받은 메서드이므로 문제가 되지 않는다!
			action = new MemberJoinProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/MemberJoinResult.me")) { // 회원 가입 결과 뷰페이지
			// "member/member_join_result.jsp" 페이지로 포워딩
			// 서블릿 -> 뷰페이지 포워딩이므로 Dispatch 방식 포워딩
			forward = new ActionForward();
			forward.setPath("member/member_join_result.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/MemberLoginForm.me")) { // 회원 로그인 폼
			// 서블릿 -> 뷰페이지 포워딩 : Dispatch 방식
			// ActionForward 객체 생성 및 포워딩 주소(member/member_login_form.jsp)와 방식 설정
			forward = new ActionForward();
			forward.setPath("member/member_login_form.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/MemberLoginPro.me")) { // 회원 로그인 비즈니스 로직
			// 회원 로그인 비즈니스 로직 작업 요청
			// 비즈니스 로직을 처리할 Action 클래스의 인스턴스 생성 후 execute() 메서드 호출
			// => 파라미터 : HttpServletRequest(request), HttpServletResponse(response) 객체
			// => 리턴타입 : ActionForward(forward)
			action = new MemberLoginProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/MemberLogout.me")) {
			// 회원 로그아웃 비즈니스 로직 작업 요청
			// 비즈니스 로직을 처리할 Action 클래스의 인스턴스 생성 후 execute() 메서드 호출
			action = new MemberLogoutAction();
			forward = action.execute(request, response);
		} else if(command.equals("/AdminMain.me")) { // 관리자 페이지
			// 관리자 메인페이지(member/admin_main.jsp)로 포워딩(디스패치)
			forward = new ActionForward();
			forward.setPath("member/admin_main.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/AdminMemberList.me")) { // 관리자 페이지
			action = new AdminMemberListAction();
			forward = action.execute(request, response);
		}
		
		
		// ----------------------------------------------------------------------
		// 서블릿 주소 판별 후 포워딩 작업 수행
		// 1. ActionForward 객체가 null 이 아닌지 판별
		if(forward != null) { // ActionForward 객체가 존재할 경우
			// 2. 포워딩 방식 판별(ActionForward 객체의 isRedirect() 메서드 활용)
			if(forward.isRedirect()) { // 리다이렉트 방식
				// 3-1. 리다이렉트 방식 포워딩 수행
				// => 포워딩 경로 : ActionForward 객체의 getPath() 메서드 활용
				response.sendRedirect(forward.getPath());
			} else { // 디스패치 방식
				// 3-2. 디스패치 방식 포워딩 수행
				// => 포워딩 경로 : ActionForward 객체의 getPath() 메서드 활용
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
		
	}
	
	// GET 방식 요청에 대해 자동 호출되는 doGet() 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공통으로 요청을 처리할 doProcess() 메서드 호출
		doProcess(request, response);
	}

	// POST 방식 요청에 대해 자동 호출되는 doPost() 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공통으로 요청을 처리할 doProcess() 메서드 호출
		doProcess(request, response);
	}

}












