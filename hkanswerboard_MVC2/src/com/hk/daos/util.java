package com.hk.daos;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class util {
	
	//static : class명.메서드, 클래스명.멤버필드
	//non-staitc : 객체생성후 객체명.메서드, 객체명.멤버필드
	//static/ nonstatic, 반환/void, 아규먼트 O/아규먼트X
	
	//&nbsp;&nbsp;<img src="img,jsp"/>
	private String arrowNbsp;
	
	
	public String getArrowNbsp() {
		return arrowNbsp;
	}

	//글목록에서 ${dto.depth}값을 setArrowNbsp(depth) 아규먼트로 전달
	public void setArrowNbsp(String depth) {
		String nbsp="";
		int depthInt=Integer.parseInt(depth);
		for (int i = 0; i < depthInt; i++) {//depth의 크기만큼 &nbsp;를 만들어준다
			nbsp+="&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		this.arrowNbsp=(depthInt>0?nbsp+"<img src='image/arrow_right.png' alt='답글'/>":"");
	}


	public static void jsForWard(String msg,String url, HttpServletResponse response) throws IOException{
		String s="<script type='text/javascript'>"
				+"alert('"+msg+"');"
				+"location.href='"+url+"';"
				+"</script>";
				
		PrintWriter pw= response.getWriter();
		pw.print(s);
	}
	
	
}
