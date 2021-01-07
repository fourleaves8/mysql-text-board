package com.sbs.example.mysqlTextBoard.service;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.dto.Member;

public class MemberService {

	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public int doJoin(String accountName, String accountPw, String name) {
		return memberDao.add(accountName, accountPw, name);
	}

	public boolean isValidAcctName(String accountName) {
		Member member = memberDao.getMemberByAcctName(accountName);
		if (member != null) {
			return false;
		}
		return true;
	}

	public Member getMemberByAcctName(String accountName) {
		return memberDao.getMemberByAcctName(accountName);
	}

	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}

}
