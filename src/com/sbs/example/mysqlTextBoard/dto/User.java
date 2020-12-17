package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class User {
	public int id;
	public String accountName;
	public String accountPw;
	public String name;
	public String regDate;
	public String updateDate;

	public User(Map<String, Object> userMap) {
		this.id = (int) userMap.get("id");
		this.accountName = (String) userMap.get("accountName");
		this.accountPw = (String) userMap.get("accountPw");
		this.name = (String) userMap.get("name");
		this.regDate = (String) userMap.get("regDate");
		this.updateDate = (String) userMap.get("updateDate");
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", accountName=" + accountName + ", accountPw=" + accountPw + ", name=" + name
				+ ", regDate=" + regDate + ", updateDate=" + updateDate + "]";
	}

	public String getType() {
		return isAdmin() ? "관리자" : "일반";
	}

	public boolean isAdmin() {
		return accountName.equals("test1");
	}

}
