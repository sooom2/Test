<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/inc.css" rel="stylesheet" type="text/css">
<link href="css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header>
		<jsp:include page="/inc/top.jsp"/>
	</header>
	<article id="main">
		<h1>MVC 게시판 프로젝트</h1>
		<a href="BoardWriteForm.bo">1)글쓰기</a>
		<a href="BoardList.bo">2)글목록</a>
	</article>
</body>
</html>