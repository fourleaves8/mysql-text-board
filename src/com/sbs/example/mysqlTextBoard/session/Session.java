package com.sbs.example.mysqlTextBoard.session;

public class Session {

	private int loginedUserId;

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

}
