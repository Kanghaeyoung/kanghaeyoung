<%@page import="com.hk.dtos.AnsDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=utf-8"); %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글목록보기</title>
<jsp:include page="header.jsp"/>
<script type="text/javascript">
	
	//여러글 삭제시 체크박스 유효값 처리 (체크가 하나라도 되어 있어야 삭제 실행)\
	$(function(){
		$("#container > form").submit(function(){
			var bool=false;
			
			$("input[name=chk]").each(function(){
				if($(this).prop("checked")){
					bool=true;
					return;
				}
			});//each:chk인 체크박스가 모두 체크안되어 있는지 여부
			
			if(!bool){
				alert("최소하나이상 체크해주세요.");
			}
			return bool;
		});//submit
		
	//삭제된 글에 해당하는 체크박스 비활성화
	$(".delboard").each(function(){
		//핵심탐색기법: tree탐색 --> 부모의 자식에 형제에 자식의 엘리먼트를 구하는
		$(this).parent("tr").children().eq(0).find("input").attr("disabled","disabled");
		});	
		//답변형을 위한 속성 refer, step, depth, delflag를 감추는 기능
		$("#container > h1> small").click(function(){
			$("th").slice(6,10).toggle();
			$("tr").each(function(){
				$(this).children("td").slice(6,10).toggle();
			});
		});
	});
	
	
	//새글작성 폼으로 이동
	function insertform(){
		location.href="HkController.do?command=insertboard";
	}
	//전체선택 체크박스 기능
	function allsel(bool){
		$("input[name=chk]").prop("checked",bool);
	}
</script>
</head>
<body>
<!-- 객체생성과 마찬가지 -->
<jsp:useBean id="util" class="com.hk.daos.util"/>
<div id="container">
<h1>게시글 목록<small style="cursor: pointer;" title="게시판속성보기">답변형게시판</small></h1>
<form action="HkController.do" method="post">
<input type="hidden" name="command" value="muldel"/>
<%-- <% --%>
<!-- 	List<AnsDto>lists=(List<AnsDto>)request.getAttribute("lists"); -->
<!-- 	for(AnsDto dto:lists){//향상된 for문 : 길이를 구하지 않아도 lists의 길이를 자동으로 구함 -->
<!-- 		dto.getTitle(); -->
<!-- } -->
<!-- %> -->

	<table class="table table-striped">
		<col width="50px" />
		<col width="50px" />
		<col width="100px" />
		<col width="300px" />
		<col width="100px" />
		<col width="100px" />
		<col width="50px" />
		<col width="50px" />
		<col width="50px" />
		<col width="50px" />
		<tr>
			<th><input type="checkbox" name="all" onclick="allSel(this.checked)"/></th>
			<th>번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>작성일</th>
			<th>조회수</th>
			<th>refer</th>
			<th>step</th>
			<th>depth</th>
			<th>삭제</th>
		</tr>
<!-- 		ifelse기능을하는 choose -> choose, when, otherwise가 한 세트 -->
		<c:choose>
			<c:when test="${empty lists}">
				<tr><td colspan="10">---작성된 글이 없습니다.---</td></tr>
			</c:when>
			<c:otherwise>
<!-- 			위의 when을 만족하지 않으면 실행되는 otherwise(otherwise안에 when을 다시 사용할 수 있음) -->
<!-- 처음과 끝값 입력 없이 자동으로 구해서 반복 -->
				<c:forEach var = "dto" items="${lists}">
					<tr>
<!-- 					value안에 출력한 멤버필드의 이름입력 -->
						<td><input type="checkbox" name="chk" value="${dto.seq}"/></td>
<!-- 						글번호 출력 -->
						<td>${dto.seq}</td>
						<td>${dto.id}</td>
						<c:choose>
							<c:when test="${dto.delflag=='Y'}">
								<td class="delboard">---삭제된 글입니다.---</td>
							</c:when>
							<c:otherwise>
								<td>
									<jsp:setProperty value="${dto.depth}" property="arrowNbsp" name="util"/>
									<jsp:getProperty property="arrowNbsp" name="util"/>
									<a href="HkController.do?count=count&command=detail&seq=${dto.seq}">
										${dto.title}
									</a>
								</td>
							</c:otherwise>
						</c:choose>
						<td>${dto.regdate}</td>
						<td>${dto.readcount}</td>
						<td>${dto.refer}</td>
						<td>${dto.step}</td>
						<td>${dto.depth}</td>
						<td>${dto.delflag}</td>
					</tr>
				</c:forEach>				
			</c:otherwise>
		</c:choose>
		<tr>
			<td colspan="10">
				<input type="button" class="btn btn-primary" value="글추가" onclick="insertform()"/>
				<input type="submit" class="btn btn-danger" value="삭제"/>
			</td>
			
		</tr>
	</table>
</form>
<%-- <c:forEach var = "dto" begin="1" end="10" step="1">//1부터 10까지 1씩 증가 --%>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>