package com.sbs.example.mysqlTextBoard.session;

public class Session {

	private int loginedUserId;
	private String selectedBoardCode;

	public Session() {
		// 공지사항을 기본 게시판으로 설정
		selectedBoardCode = "notice";
	}

	public void doLogout() {
		loginedUserId = 0;

	}

	public void login(int id) {
		loginedUserId = id;

	}

	public boolean islogined() {
		return loginedUserId > 0;
	}

	public int getLoginedUserId() {
		return loginedUserId;
	}

	public String getSelectedBoardCode() {
		return selectedBoardCode;
	}

}
