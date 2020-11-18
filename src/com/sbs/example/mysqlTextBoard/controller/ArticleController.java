package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class ArticleController {

	private ArticleService articleService;

	public ArticleController() {
		articleService = Container.articleService;
	}

	public void doCmd(String cmd) {
		if (cmd.equals("article list")) {
			showList();
		} else if (cmd.startsWith("article detail ")) {
			showDetail(cmd);
		} else if (cmd.startsWith("article delete ")) {
			doDelete(cmd);
		}

	}

	private void doDelete(String cmd) {
		System.out.println("== 게시물 삭제 ==");
		int inputId = Integer.parseInt(cmd.split(" ")[2]);
		articleService.doDelete(inputId);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", inputId);
	}

	private void showDetail(String cmd) {
		System.out.println("== 게시물 상세 ==");
		int inputId = Integer.parseInt(cmd.split(" ")[2]);
		Article article = articleService.getArticle(inputId);
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성일 : %s\n", article.regDate);
		System.out.printf("수정일 : %s\n", article.updateDate);
		System.out.printf("작성자 : %s\n", article.userId);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);

	}

	private void showList() {
		System.out.println("== 게시물 리스트 ==");
		List<Article> articles = articleService.showList();
		System.out.println("번호 / 작성 / 수정 / 작성자 / 제목");
		for (Article article : articles) {
			System.out.printf("%d / %s / %s / %s / %s\n", article.id, article.regDate, article.updateDate,
					article.userId, article.title);
		}

	}

}
