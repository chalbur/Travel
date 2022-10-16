<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 목록</title>
</head>
<body>
	<h4>
		<a href="/index.html" style="font-weight: bold; color:blue;">공지사항</a>
	</h4>
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		<c:forEach var="n" items="${list }">
		<tr>
			<th>${n.id }</th>
			<th>${n.title }</th>
			<th>${n.regdate }</th>
			<th>${n.hit }</th>
		</tr>
		</c:forEach>
	</table>
	
</body>
</html>