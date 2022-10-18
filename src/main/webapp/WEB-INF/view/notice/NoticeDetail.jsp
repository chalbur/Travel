<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
<link rel="stylesheet" href="../css/home.css" type="text/css" />
<link rel="stylesheet" href="../css/noticedatail.css" type="text/css" />
</head>
<body>
	<div class="nav">
		<div class="logo">
			<a href="../main"><img src="../images/exlogo.png"></a>
		</div>
		<div class="nav_but">
			<a href="main">페이지1</a>
			<a href="">페이지2</a>
			<a href="">페이지3</a>
			<a href="">페이지4</a>
		</div>
	</div>
	
	<div class="wrap">
		<div class="box1">box1</div>
		
		<div class="line"></div>
		
		<div class="category">
			<ul>
				<li><a href="Notice" style="font-weight: bold;">공지사항</a></li>
				<li><a>여행후기게시판</a></li>
				<li><a>관리자인증게시물</a></li>
				<li><a>질문게시판</a></li>
				<li><a>투표게시판</a></li>
				<li><a>이벤트</a></li>
				
			</ul>
		</div>
		<div class="content">
			<div>
	            <h3>공지사항 내용</h3>
	            <table class="table">
	                <tbody>
	                    <tr>
	                        <th>제목</th>
	                        <td >${n.title }</td>
	                    </tr>
	                    <tr>
	                    	<th>작성자</th>
	                        <td>${n.writerId }</td>
	                        <th>작성일</th>
	                        <td ><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${n.regdate }"/></td>
	                    </tr>
	                    <tr>
	                        <th>첨부파일</th>
	                        <td colspan="3">
	                        	<c:forTokens var="fileName" items="${n.files }" delims="," varStatus="st">
			
								<c:set var="style" value="" />
								<c:if test="${fn:endsWith(fileName, '.zip') }">
									<!-- endsWith : 맨 뒷글자가 특정글자일경우 -->
									<c:set var="style" value="font-weight: bold; color:red;" />
								</c:if>
									
								<a download href="/upload/${fileName }" style="${style }">${fn:toUpperCase(fileName) }</a>
								<!-- toUpperCase : 실제파일은 원래이름, 출력되는곳에서만 대문자 -->
								<c:if test="${!st.last}"> <!-- st(현재상태)가 마지막인지 알려줌 -->
									/
								</c:if>
								</c:forTokens>
	                        </td>
	                        <th>조회수</th>
	                        <td>${n.hit }</td>
	                    </tr>
	                    <tr class="content">
	                        <td colspan="2">${n.content }</td>
	                    </tr>
	                </tbody>
	            </table>
        	</div>
        	<div>
				<button onclick="location.href='Notice'">목록</button>
			</div>
		</div>
		
	</div>
	
	<div class="bottom">
		<div class="bottom-1">분할1</div>
		<div class="bottom-2">분할2</div>
		<div class="bottom-3">분할3</div>
	</div>
	
	
</body>
</html>