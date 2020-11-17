package com.sbs.example.mysqlTextBoard.container;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class Container {

	public static Scanner sc;
	public static ArticleDao articleDao;
	public static ArticleService articleService;

	static {
		sc = new Scanner(System.in);
		articleDao = new ArticleDao();
		articleService = new ArticleService();
	}

}
