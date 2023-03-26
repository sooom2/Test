<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header>
		<jsp:include page="/inc/top.jsp"/>
	</header>
	<article>
		<h1>회원 가입 완료</h1>
		<h4>가입을 축하합니다 포인트 <font color="pink"> 1000점</font> 적립 완료</h4>
		<h3>
			<input type="button" value="HOME" onclick="location.href='./'">
			<input type="button" value="LOGIN" onclick="location.href='MemberLoginForm.me'">
		</h3>
	</article>
</body>
</html>