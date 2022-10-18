<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link rel="stylesheet" href="../css/home.css" type="text/css" />
<link rel="stylesheet" href="../css/reg.css" type="text/css" />
<script src="../ckeditor/ckeditor.js"></script>
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
			<h3><%=request.getRealPath("/") %></h3>
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
			<form method="post" enctype="multipart/form-data">
				<textarea rows="5" cols="50" id="contents" name="contents"></textarea>
						<script>
						 var ckeditor_config = {
						   resize_enaleb : false,
						   enterMode : CKEDITOR.ENTER_BR,
						   shiftEnterMode : CKEDITOR.ENTER_P,
						   filebrowserUploadUrl : '/upload'
						 };
						 
						 CKEDITOR.replace("contents", ckeditor_config);
						</script>
						
			        <button class="btn btn-primary">저장1</button>
	        </form>
		</div>
	</div>
	
	<div class="bottom">
		<div class="bottom-1">분할1</div>
		<div class="bottom-2">분할2</div>
		<div class="bottom-3">분할3</div>
	</div>
	
	
</body>
</html>