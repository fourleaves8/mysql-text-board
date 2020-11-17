package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleDao {
	private List<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();

	}

	public List<Article> getArticles() {
		Article article;

		// 임시 게시물 1
		article = new Article();
		article.articleId = 1;
		article.regDate = "2020-11-18 12:12:12";
		article.updateDate = "2020-11-18 12:12:12";
		article.title = "제목1";
		article.body = "내용1";
		article.userId = 1;
		article.boardId = 1;

		articles.add(article);

		// 임시 게시물 2
		article = new Article();
		article.articleId = 2;
		article.regDate = "2020-11-18 12:12:15";
		article.updateDate = "2020-11-18 12:12:15";
		article.title = "제목2";
		article.body = "내용2";
		article.userId = 1;
		article.boardId = 1;

		articles.add(article);

		return articles;

	}

}
