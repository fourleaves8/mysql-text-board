package com.sbs.example.mysqlTextBoard.session;

public class Session {

	private int loginedMemberId;
	private String selectedBoardCode;

	public Session() {
		// 공지사항을 기본 게시판으로 설정
		selectedBoardCode = "notice";
	}

	public void doLogout() {
		loginedMemberId = 0;

	}

	public void login(int id) {
		loginedMemberId = id;

	}

	public boolean islogined() {
		return loginedMemberId > 0;
	}

	public int getLoginedMemberId() {
		return loginedMemberId;
	}

	public String getSelectedBoardCode() {
		return selectedBoardCode;
	}

}
