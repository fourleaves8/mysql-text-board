package com.sbs.example.mysqlTextBoard.service;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.UserDao;

public class UserService {

	private UserDao userDao;

	public UserService() {
		userDao = Container.userDao;
	}

	public int doJoin(String accountName, String accountPW, String name) {
		return userDao.add(accountName, accountPW, name);
	}

}
