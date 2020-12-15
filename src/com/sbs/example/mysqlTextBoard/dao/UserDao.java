package com.sbs.example.mysqlTextBoard.dao;

import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.User;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlTextBoard.mysqlutil.SecSql;

public class UserDao {

	public int add(String accountName, String accountPw, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `user`");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("accountName = ?,", accountName);
		sql.append("accountPw = ?,", accountPw);
		sql.append("name = ?", name);

		return MysqlUtil.insert(sql);
	}

	public User getUserByAcctName(String accountName) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `user`");
		sql.append("WHERE accountName = ?", accountName);

		Map<String, Object> userMap = MysqlUtil.selectRow(sql);
		if (userMap.isEmpty()) {
			return null;
		}
		return new User(userMap);
	}

	public User getUserById(int id) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `user`");
		sql.append("WHERE id = ?", id);
		Map<String, Object> userMap = MysqlUtil.selectRow(sql);

		if (userMap.isEmpty()) {
			return null;
		}
		return new User(userMap);
	}

}
