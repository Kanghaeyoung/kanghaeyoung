<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>머릿글</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>

<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<style type="text/css">
	#header, #footer{
		height: 80px;
		background-color: orange;
		text-align: center;
		line-height: 80px;
	}
	#container{
		width:1000px;
		height:550px;
		margin: 0 auto;
/* 		border: 1px solidred; */
		overflow: auto;
	}
	a{text-decoration:none;}
	#replyForm{
		display: none;
	}
	
</style>
<script type="text/javascript">
	//글목록페이지로 돌아가기
	function boardList(){
		location.href="anscontroller.jsp?command=boardlist";
	}
</script>
</head>
<body>
<div id="header">
	<h1><a href="index.jsp">게시판만들기</a></h1>
</div>
</body>
</html>