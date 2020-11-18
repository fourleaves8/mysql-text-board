package com.sbs.example.mysqlTextBoard.container;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dao.UserDao;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.UserService;

public class Container {

	public static Scanner sc;
	public static ArticleDao articleDao;
	public static ArticleService articleService;
	public static UserDao userDao;
	public static UserService userService;

	static {
		sc = new Scanner(System.in);

		articleDao = new ArticleDao();
		articleService = new ArticleService();

		userDao = new UserDao();
		userService = new UserService();
	}

}
