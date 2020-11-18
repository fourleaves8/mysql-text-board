package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public List<Article> showList() {
		return articleDao.getArticles();
	}

	public Article getArticle(int inputId) {
		return articleDao.getArticle(inputId);
	}

	public void doDelete(int inputId) {
		articleDao.remove(inputId);

	}

}
