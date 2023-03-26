<%@page import="vo.BoardBean"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
	#listForm {
		width: 1024px;
		max-height: 610px;
		margin: auto;
	}
	
	h2 {
		text-align: center;
	}
	
	table {
		margin: auto;
		width: 1024px;
	}
	
	#tr_top {
		background: orange;
		text-align: center;
	}
	
	table td {
		text-align: center;
	}
	
	#subject {
		text-align: left;
		padding-left: 20px;
	}
	
	#pageList {
		margin: auto;
		width: 1024px;
		text-align: center;
	}
	
	#emptyArea {
		margin: auto;
		width: 1024px;
		text-align: center;
	}
	
	#buttonArea {
		margin: auto;
		width: 1024px;
		text-align: right;
	}
	
	a {
		text-decoration: none; 	/* 하이퍼링크 밑줄 없애기 */
	}
	
</style>
<!-- css/main.css 파일 불러오기 -->
<link href="css/inc.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header>
		<%-- inc/top.jsp 페이지 삽입(jsp:include 액션태그 사용 시 / 경로는 webapp 가리킴) --%>
		<jsp:include page="/inc/top.jsp"></jsp:include>
	</header>

	<!-- 게시판 리스트 -->
	<h2>게시판 글 목록</h2>
	<section id="buttonArea">
		<input type="button" value="글쓰기" onclick="location.href=''" />
	</section>
	<section id="listForm">
		<table>
			<tr id="tr_top">
				<td width="100px">번호</td>
				<td>제목</td>
				<td width="150px">작성자</td>
				<td width="150px">날짜</td>
				<td width="100px">조회수</td>
			</tr>
			
			
			<%
// 				List<BoardBean> boardList = (List<BoardBean>)request.getAttribute("boardList");
// 				for(BoardBean board : boardList) {
					%>
<!-- 					<tr> -->
<%-- 					<td><%=board.getBoard_num() %></td> --%>
<%-- 					<td id="subject"><a href="BoardDetail.bo?board_num=<%=board.getBoard_num() %>"><%=board.getBoard_subject() %></a></td> --%>
<%-- 					<td><%=board.getBoard_name() %></td> --%>
<%-- 					<td><%=sdf.format(board.getBoard_date()) %></td> --%>
<%-- 					<td><%=board.getBoard_readcount() %></td> --%>
<!-- 				</tr> -->
					<%
// 				}
			%>
			
			
			
			
			<%-- JSTL 과 EL 활용하여 글목록 표시를 위한 반복문 작성 - <c:forEach> --%>
			<c:forEach var="board" items="${boardList }">
				<tr>
					<td>${board.board_num }</td>
					<td id="subject"><a href="BoardDetail.bo?board_num=${board.board_num}&pageNum=${pageNum}">${board.board_subject }</a></td>
					<td>${board.board_name }</td>
					<td><fmt:formatDate value="${board.board_date }" pattern="yy-MM-dd hh:mm"/></td>
					<td>${board.board_readcount }</td>
				</tr>
			</c:forEach>
		</table>
	</section>
	<section id="pageList">
	<c:choose>
		<c:when test="${pageNum > 1 }">
		<input type="button" value="이전" onclick="location.href='BoardList.bo?pageNum=${pageNum-1}'">
		</c:when>
		<c:otherwise>
		<input type="button" value="이전">
		</c:otherwise>
	</c:choose>
		<c:forEach var="num" begin="${pageInfo.startPage }" end="${pageInfo.endPage }">
			<c:choose>
				<c:when test="${pageNum eq num }">
				${num }
				</c:when>
				<c:otherwise>
			<a href="BoardList.bo?pageNum=${num }"> ${num } </a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:choose>
		<c:when test="${pageNum < pageInfo.maxPage }">
		<input type="button" value="다음" onclick="location.href='BoardList.bo?pageNum=${pageNum+1}'">
		</c:when>
		<c:otherwise>
		<input type="button" value="다음">
		</c:otherwise>
	</c:choose>
	</section>
</body>
</html>













