package com.hk.dtos;

import java.io.Serializable;
import java.sql.Date;

public class AnsDto implements Serializable {
	
	//객체를 유니크하게 구별하기 위해 사용되는 ID를 생성
	private static final long serialVersionUID = -1535209101942794791L;
	
	private int seq;
	private String id;
	private String title;
	private String content;
	private Date regdate;
	private int refer;
	private int step;
	private int depth;
	private int readcount;
	private String delflag;
	public AnsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AnsDto(int seq, String id, String title, String content, Date regdate, int refer, int step, int depth,
			int readcount, String delflag) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.regdate = regdate;
		this.refer = refer;
		this.step = step;
		this.depth = depth;
		this.readcount = readcount;
		this.delflag = delflag;
	}
	
	public AnsDto(String id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	public AnsDto(int seq, String title, String content) {
		super();
		this.seq = seq;
		this.title = title;
		this.content = content;
	}
	
	public AnsDto(int seq, String id, String content, String title) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getRefer() {
		return refer;
	}
	public void setRefer(int refer) {
		this.refer = refer;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	@Override
	public String toString() {
		return "ansDto [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", regdate="
				+ regdate + ", refer=" + refer + ", step=" + step + ", depth=" + depth + ", readcount=" + readcount
				+ ", delflag=" + delflag + "]";
	}
	
	
}
