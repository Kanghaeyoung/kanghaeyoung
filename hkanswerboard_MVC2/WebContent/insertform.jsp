<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="header.jsp"/>
<script type="text/javascript">
	$(function(){
		$("#container > form").submit(function(){
			var bool=true;
			$("tr>td").each(function(){//[td,td,td,td,td...] 길이만큼 each가 function를 실행
				if($(this).children().val()==""){
					alert($(this).children().attr("name")+"을 입력하세요");
					$(this).children().focus();//바로 입력할 수 있게 포커스(커서)를 준다.
					bool=false;//submit이벤트 취소를 위한 값 저장
					return false;//each에 구현된 함수 종료					
				}
			});//each종료
			return bool;//bool의 값이 false면 submit이벤트 취소됨
		});//submit종료
	});
</script>
</head>
<body>
<div id="container">
<h1>새글 추가하기</h1>
	<form action="HkController.do" method="post">
		<input type="hidden" name="command" value="insert"/>
		<table class="table table-striped">
			<tr>
				<th>아이디</th>
				<td><input type="text" class="form-control" name="id"/></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" class="form-control" name="title"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="10" cols="60" class="form-control" name="content"></textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<input class="btn btn-success btn-sm" type="submit" value="글추가"/>
					<input class="btn btn-info btn-sm" type="button" value="목록" onclick="boardList()"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>