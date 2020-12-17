package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;

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

	public int doWrite(String title, String body, int userId, int boardId) {
		return articleDao.add(title, body, userId, boardId);
	}

	public List<Article> getArticlesForPrintOut(int boardId) {
		return articleDao.getArticlesForPrintOut(boardId);
	}

	public void doModify(int id, String title, String body) {
		articleDao.modify(id, title, body);
	}

	public Board getBoardByCode(String boardCode) {
		return articleDao.getBoardByCode(boardCode);
	}

}
