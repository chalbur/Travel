<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 목록</title>
<link rel="stylesheet" href="../css/home.css" type="text/css" />
<link rel="stylesheet" href="../css/notice.css" type="text/css" />
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
			<div class="top">
				<div>
					<h3 class="notice-main">공지사항</h3>
				</div>
				<div>
					<form class="notice-search">
						<fieldset>
							<label>검색분류</label>
							<select name="f">
								<option ${(param.f == "title")?"selected":"" }  value="title">제목</option>
								<option ${(param.f == "writer_Id")?"selected":"" } value="writer_Id">작성자</option>
							</select> 
							<label >검색어</label>
							<input type="text" name="q" value="${param.q }"/>
							<input  type="submit" value="검색" />
						</fieldset>
					</form>
				</div>
			</div>
			
			<div>
				<table class="notice-table">
					<thead>
						<tr>
							<th class="notice-id">번호</th>
							<th class="notice-title">제목</th>
							<th class="notice-writer_id">작성자</th>
							<th class="notice-regdate">작성일</th>
							<th class="notice-hit">조회수</th>
						</tr>
					</thead>
					<tbody class="tbody">
						<c:forEach var="n" items="${list }">
							<tr>
								<td>${n.id }</td>
								<td id="tbody-title"><a style="color: black;" href="NoticeDetail?id=${n.id }">${n.title }</a><span style="color:red;">[${n.cmtCount }]</span></td>
								<td>${n.writerId }</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${n.regdate }"/></td>
								<td>${n.hit }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div>
				<button onclick="location.href='reg'">글쓰기</button>
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