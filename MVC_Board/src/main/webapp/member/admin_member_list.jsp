<%@page import="vo.MemberBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/inc.css" rel="stylesheet" type="text/css">
<link href="css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="../inc/top.jsp" />
	<div align="center">
		<h1>회원 목록 조회</h1>
		<table border="1">
			<tr>
				<th>번호</th>
				<th>이름</th>
				<th>아이디</th>
				<th>E-Mail</th>
				<th>성별</th>
				<th>가입일</th>
				<th></th>
			</tr>	
			
			<c:choose>
				<c:when test="${empty memberList }">
					<tr>
						<td colspan="7">회원 목록이 존재하지 않습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="member" items="${memberList }">
					<tr>
					<td>${member.idx }</td>
					<td>${member.name }</td>
					<td>${member.id }</td>
					<td>${member.email }</td>
					<td>${member.gender }</td>
					<td>${member.date }</td>
					<td>
						<%-- 수정 버튼 클릭 시 회원 정보 조회 페이지로 이동(파라미터 : id) --%>
						<input type="button" value="수정" onclick="">
						<input type="button" value="삭제">
					</td>
				</tr>
					</c:forEach>
				
				</c:otherwise>
			</c:choose>
			
			
			
			
			

			
			
			
			
			
			
			
		</table>
	</div>
</body>
</html>


















