package com.sbs.example.mysqlTextBoard.dao;

import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlTextBoard.mysqlutil.SecSql;

public class MemberDao {

	public int add(String accountName, String accountPw, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("accountName = ?,", accountName);
		sql.append("accountPw = ?,", accountPw);
		sql.append("name = ?", name);

		return MysqlUtil.insert(sql);
	}

	public Member getMemberByAcctName(String accountName) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE accountName = ?", accountName);

		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);
		if (memberMap.isEmpty()) {
			return null;
		}
		return new Member(memberMap);
	}

	public Member getMemberById(int id) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE id = ?", id);
		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

		if (memberMap.isEmpty()) {
			return null;
		}
		return new Member(memberMap);
	}

}
