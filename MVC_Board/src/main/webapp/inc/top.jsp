<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
function confirm_logout() {
	let result = confirm("로그아웃하시겠습니까?");
	return result;
}
</script>
<div id="member_area">
<a href="./">HOME</a> |
	<c:choose>
		<c:when test="${empty sessionScope.sId }">
		
		<a href="MemberLoginForm.me">로그인</a> | <a href="MemberJoinForm.me">회원가입</a>
		</c:when>
		<c:otherwise>
			<a href="MemberInfo.me">${sessionScope.sId }님</a> | <a href="MemberLogout.me" onclick="return confirm_logout()">로그아웃</a>
			<c:if test="${sessionScope.sId == 'admin' }">
				| <a href="AdminMain.me">관리자페이지</a>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>