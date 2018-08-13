package com.hk.dbinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class DataBase {  // 1단계, 2단계 6단계만 사용

	
	public DataBase() {
		//생성자: 초기값 설정 기능, 객체생성시 가장 먼저 실행된다.
		//생성자 오버로딩: default생성자 - 생략가능, 오버로딩시 - 생략 불가능
		//1단계 작성
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	//DB연걸
	public Connection getConnection() throws SQLException { //connection 반환
		//예외처리 1가지 방법: try~~~~catch(직접처리), throw~~~(나중에 다른 곳에서 처리)
		Connection conn=null;
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="USER03";
		String password="user03";
		conn=DriverManager.getConnection(url, user, password);
		return conn;
		
	}
	
	
	
	//6단계: DB닫기
	public void close(ResultSet rs, PreparedStatement psmt,Connection conn) {		//닫고 끝나기때문에 반환타입 없다.
		
        try {
            if(rs!=null) {
               rs.close();
            }
            if(psmt!=null) {
            psmt.close();
            }
            if(conn!=null) {
            conn.close();
            }
            System.out.println("6단계:DB닫기 성공!");
         } catch (SQLException e) {
            System.out.println("6단게:DB닫기 실패!");
            e.printStackTrace();

         }
	}
	
	
	
	//jdbc성공했을 때
	public void log(String msg,String methodName) {
		Calendar cal=Calendar.getInstance();
		System.out.println(msg+"성공!!@@:"+getClass()+":"+methodName+":"+cal.getTime());
	}
	
	
	//jdbc실패했을 때 (메소드 오버로딩)이름똑같고 아규먼트 갯수 다른거
	public void log(String msg,String methodName,Exception e) {
		Calendar cal=Calendar.getInstance();
		System.out.println(msg+"실팽!!@@:"+getClass()+":"+methodName+":"+e+":"+cal.getTime());
	}
	
}
