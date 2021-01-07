package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Member {
	public int id;
	public String accountName;
	public String accountPw;
	public String name;
	public String regDate;
	public String updateDate;

	public Member(Map<String, Object> memberMap) {
		this.id = (int) memberMap.get("id");
		this.accountName = (String) memberMap.get("accountName");
		this.accountPw = (String) memberMap.get("accountPw");
		this.name = (String) memberMap.get("name");
		this.regDate = (String) memberMap.get("regDate");
		this.updateDate = (String) memberMap.get("updateDate");
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", accountName=" + accountName + ", accountPw=" + accountPw + ", name=" + name
				+ ", regDate=" + regDate + ", updateDate=" + updateDate + "]";
	}

	public String getType() {
		return isAdmin() ? "관리자" : "일반";
	}

	public boolean isAdmin() {
		return accountName.equals("test1");
	}

}
