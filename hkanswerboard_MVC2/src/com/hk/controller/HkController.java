package com.hk.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.daos.AnsDao;
import com.hk.daos.util;
import com.hk.dtos.AnsDto;

/**
 * Servlet implementation class HkController
 */
@WebServlet("/HkController.do")
public class HkController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");;
		response.setContentType("text/html; charset=utf-8");
		
		//어떤 요청을 했는지?, 요청 내용을 알기위한 COMMAND 파라미터를 받는다
		String  command = request.getParameter("command");

		//요청을 처리하기 위한 dao객체 생성
		AnsDao dao=AnsDao.getAnsDao();
		
		//받은 요청을 확인한다.
		if(command.equalsIgnoreCase("boardlist")){//글목록을호 이동하기
			
			//글목록페이지로 이동하면 세션에 "readcount"를 삭제한다.
			request.removeAttribute("readcount");
		
			List<AnsDto>lists=dao.getAllList();
			request.setAttribute("lists", lists);
//			pageContext.forward("boardlist.jsp");
			dis("boardlist.jsp",request,response);
		}else if(command.equals("insertboard")){//글추가폼으로 이동하기
			response.sendRedirect("insertform.jsp");
		}else if(command.equals("insert")){//글추가하기
			String id=request.getParameter("id");
			String title=request.getParameter("title");
			String content=request.getParameter("content");
			
			boolean isS=dao.insertBoard(new AnsDto(id,title,content));
			
			if(isS){
				response.sendRedirect("HkController.do?command=boardlist");
			}else{
				util.jsForWard("글추가실패","insertform.jsp",response);
			}
		} else if(command.equals("detail")){//게시그 ㄹ상세보기(글을조회--> readcount증가)
				int seq=Integer.parseInt(request.getParameter("seq"));
//	 			String count=request.getParameter("count");

				//같은글 조회수 증가 방지하기--------------------------------------------------
				//세션에서 "readcount"값을 얻어온다
				String reseq=(String)request.getAttribute("readcount");
				//얻어온값이 null이거나 현재 조회환 글의 seq와 같이 않다면 조회수를 증가시킨다.
				if(reseq==null||!reseq.equals(seq+"")){
					dao.readCount(seq);//글조회수 증가
				}
				//현재 조회환 글의 seq를 세션에 저장해둔다.
				request.setAttribute("readcount", String.valueOf(seq));
				//증가방지 끝-------------------------------------------------------------
				
//	 			if(count!=null){
//	 			dao.readCount(seq);//조회수 증가 메서드
//	 			}
				
				//현재 조회한 글의 seq를 세션에 저장해 둔다.	문자열 형변환 2가지: String.valueOf(), 5+""
				request.setAttribute("readcount", String.valueOf(seq));
				AnsDto dto=dao.getBoard(seq);//글하나에 대한 정보 가져오기(row하나)
				request.setAttribute("dto", dto);
//				pageContext.forward("boarddetail.jsp");
				dis("boarddetail.jsp",request,response);
		}else if(command.equals("muldel")){//게시글 삭제하기
			String [] seqs=request.getParameterValues("chk");
			boolean isS = dao.muldelBoard(seqs);
			if(isS){
				response.sendRedirect("HkController.do?command=boardlist");
			}else{
				util.jsForWard("글삭제실패", "HkController.do?command=boardlist",response);
			}
		} else if(command.equals("updateform")){//글수정폼으로 이동
			int seq=Integer.parseInt(request.getParameter("seq"));
			AnsDto dto=dao.getBoard(seq);//글 상세내용 구하고, 수정폼에 표현하기
			request.setAttribute("dto", dto);//사용자 정의 이름 내마음대로
//			pageContext.forward("updateform.jsp");
			dis("updateform.jsp",request,response);
		}else if(command.equals("updateboard")){
			int seq=Integer.parseInt(request.getParameter("seq"));
			String title=request.getParameter("title");
			String content = request.getParameter("content");
			
			boolean isS=dao.updateBoard(new AnsDto(seq, title, content));
			if(isS){
				//out.print(text): 브라우저에 텍스트를 출력
				//out.print("<script>alert('출력');</script>")브라우저는 안에 내용을 실행
				util.jsForWard("수정합니다", "HkController.do?command=detail&seq="+seq,response);
			}else{
				util.jsForWard("수정실패", "HkController.do?command=updateform&seq="+seq,response);
			}
		}else if(command.equals("replyBoard")){
			int seq=Integer.parseInt(request.getParameter("seq"));//부모의 seq
			String id= request.getParameter("id");//답글의 id
			String title=request.getParameter("title");//답글의 title
			String content = request.getParameter("content");//답글의 content
			
			boolean isS=dao.replyBoard(new AnsDto(seq,id,title,content));
			if(isS){
				util.jsForWard("답글추가합니다", "HkController.do?command=boardlist",response);
			}else{
				util.jsForWard("답글추가실패", "HkController.do?command=detail&seq="+seq,response);
			}
		}
		
	}
	public void dis(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch=request.getRequestDispatcher(url);
		dispatch.forward(request, response);
	}

}
