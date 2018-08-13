package com.hk.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hk.dbinfo.DataBase;
import com.hk.dtos.AnsDto;
//import com.sun.java.swing.plaf.windows.WindowsTreeUI.CollapsedIcon;

public class AnsDao extends DataBase{

	//싱글톤 패턴을 적용해보자
	private static AnsDao ansDao;
	private AnsDao() {}//AnsDao dao=new AnsDao()생성을 못함
	public static AnsDao getAnsDao() {//AnsDao dao=AnsDao.getAnsDao()->클래스명.메서드(객체얻어옴)
		if(ansDao==null) { 			// 
			ansDao=new AnsDao();
		}
		return ansDao;
	}
	
	//글목록 조회
	public List<AnsDto> getAllList(){
		List<AnsDto>list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		String sql=" SELECT SEQ, ID, TITLE, CONTENT, REGDATE, REFER, STEP, DEPTH, READCOUNT, DELFLAG "
				+ " FROM HKANSWERBOARD ORDER BY REFER, STEP ";
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			rs=psmt.executeQuery();
			while(rs.next()) {
				AnsDto dto=new AnsDto();
				int i=1;
				dto.setSeq(rs.getInt(i++));
				dto.setId(rs.getString(i++));
				dto.setTitle(rs.getString(i++));
				dto.setContent(rs.getString(i++));
				dto.setRegdate(rs.getDate(i++));
				dto.setRefer(rs.getInt(i++));
				dto.setStep(rs.getInt(i++));
				dto.setDepth(rs.getInt(i++));
				dto.setReadcount(rs.getInt(i++));
				dto.setDelflag(rs.getString(i++));
				list.add(dto);
				System.out.println(dto);
			}
			log("쿼리실행","getAllList()");
		} catch (SQLException e) {
			log("JDBC","getAllList()",e);
			e.printStackTrace();
		}finally {
			close(rs, psmt, conn);
		}
		return list;
	}
	//글 추가 (새글)
	public boolean insertBoard(AnsDto dto) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		String sql="  INSERT INTO HKANSWERBOARD "
				+ " (SEQ,ID,TITLE,CONTENT,REGDATE,REFER,STEP,DEPTH,READCOUNT,DELFLAG) "
				+ " VALUES (HKANSWERBOARD_SEQ.NEXTVAL,?,?,?,SYSDATE, "
				+ " (SELECT NVL(MAX(REFER)+1,0) FROM HKANSWERBOARD), 0,0,0,'N') ";
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2,  dto.getTitle());
			psmt.setString(3, dto.getContent());
			count=psmt.executeUpdate();
			log("쿼리실행","insertBoard()");
		} catch (SQLException e) {
			log("JDBC","insertBoard()",e);
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		return count>0?true:false;
	}
	//글 상세보기
	public AnsDto getBoard(int seq){
		AnsDto dto=new AnsDto();
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		String sql=" SELECT SEQ, ID, TITLE, CONTENT, REGDATE, REFER, STEP, DEPTH, READCOUNT, DELFLAG "
				+ " FROM HKANSWERBOARD WHERE SEQ=? ";
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1,  seq);
			rs=psmt.executeQuery();
			while(rs.next()) {
				
				int i=1;
				dto.setSeq(rs.getInt(i++));
				dto.setId(rs.getString(i++));
				dto.setTitle(rs.getString(i++));
				dto.setContent(rs.getString(i++));
				dto.setRegdate(rs.getDate(i++));
				dto.setRefer(rs.getInt(i++));
				dto.setStep(rs.getInt(i++));
				dto.setDepth(rs.getInt(i++));
				dto.setReadcount(rs.getInt(i++));
				dto.setDelflag(rs.getString(i++));
				System.out.println(dto);
			}
			log("쿼리실행","getBoard()");
		} catch (SQLException e) {
			log("JDBC","getBoard()",e);
			e.printStackTrace();
		}finally {
			close(rs, psmt, conn);
		}
		return dto;
	}
	//조회수 : update문 실행 -> readcount를 증가
	public boolean readCount(int seq) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql=" UPDATE HKANSWERBOARD SET READCOUNT=READCOUNT+1 WHERE SEQ=? ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			count=psmt.executeUpdate();
			log("쿼리실행","readCount()");
		} catch (SQLException e) {
			log("JDBC","readCount()",e);
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		return count>0?true:false;
	}
	
	//글 수정하기
	public boolean updateBoard(AnsDto dto) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql=" UPDATE HKANSWERBOARD SET TITLE=?, CONTENT=?,REGDATE=SYSDATE WHERE SEQ=? ";
		
		try {
			conn=getConnection();
			psmt=conn.prepareStatement(sql);
			
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setInt(3, dto.getSeq());

			count=psmt.executeUpdate();
			log("쿼리실행","updateBoard()");
		} catch (SQLException e) {
			log("JDBC","updateBoard()",e);
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		return count>0?true:false;
	}
	//글 삭제하기(여러글 삭제, 하나삭제)
	public boolean muldelBoard(String [] seq) {
		boolean isS=true;//결과를 저장한 배열을 확인해서 실패했을때 담을 변수 선언
		int [] count=null;//여러쿼리의 실행 결과를 저장할 배열 선언
		
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql=" UPDATE HKANSWERBOARD SET DELFLAG = 'Y' WHERE SEQ=? ";
		
		try {
			conn=getConnection();
			conn.setAutoCommit(false);//rollback을 실행할수도 있기 때문에 false
			psmt=conn.prepareStatement(sql);
			for (int i = 0; i < seq.length; i++) {
				psmt.setString(1,  seq[i]);//[seq,seq,seq.......]
				psmt.addBatch();
			}
			count=psmt.executeBatch();//만약 모두 성공하면 count[-2,-2,-2......]
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log("JDBC","muldelBoard()",e);//실패하면 모든 작업 취소
			e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			close(null, psmt, conn);
			for (int i = 0; i < count.length; i++) {
				if(count[i]!=-2) {
					isS=false;
					break;
				}
			}
		}
		return isS;
	}
	//답글달기:update문,insert문 실행 -> 트랜젝션 처리 진행
	public boolean replyBoard(AnsDto dto) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		//답글 추가전에 부모글의 step보다 큰 step을 가진 글들에 대해 step+1 수정
		String sql1=" UPDATE HKANSWERBOARD SET STEP=STEP+1 "
				+ " WHERE REFER=(SELECT REFER FROM HKANSWERBOARD WHERE SEQ=?) "
				+ " AND STEP > (SELECT REFER FROM HKANSWERBOARD WHERE SEQ=?) ";
		
		//답글을 추가하는데 부모의 step에 step+1을 한 값을 추가
		String sql2=" INSERT INTO HKANSWERBOARD VALUES( "
				+ " HKANSWERBOARD_SEQ.NEXTVAL,?,?,?,SYSDATE, "
				+ " (SELECT REFER FROM HKANSWERBOARD WHERE SEQ=?), "
				+ " (SELECT STEP FROM HKANSWERBOARD WHERE SEQ=?)+1, "
				+ " (SELECT DEPTH FROM HKANSWERBOARD WHERE SEQ=?)+1, "
				+ " 0,'N') ";
		
		try {
			conn=getConnection();
			conn.setAutoCommit(false);
			psmt=conn.prepareStatement(sql1);
			psmt.setInt(1, dto.getSeq());
			psmt.setInt(2, dto.getSeq());
			psmt.executeQuery();
			
			psmt=conn.prepareStatement(sql2);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3,dto.getContent());
			psmt.setInt(4, dto.getSeq());
			psmt.setInt(5, dto.getSeq());
			psmt.setInt(6, dto.getSeq());
			count=psmt.executeUpdate();
			conn.commit();
			log("쿼리실행","replyBoard");
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log("JDBC","replyBoard()",e);
			e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			close(null, psmt, conn);
		}
		return count>0?true:false;
	}
}
