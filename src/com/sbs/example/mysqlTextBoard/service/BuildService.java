package com.sbs.example.mysqlTextBoard.service;

import java.io.File;
import java.util.List;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.util.Util;

public class BuildService {

	private ArticleService articleService;

	public BuildService() {
		articleService = Container.articleService;
	}

	public void buildsite() {
		System.out.println("site 폴더 생성");
		Util.rmdir(new File("site"));
		Util.mkdir(new File("site"));

		System.out.println("site/app.css 생성.");
		Util.copy(new File("site_template/app.css"), new File("site/app.css"));

		bildIndexPage();
		buildArticlelistPages();
		buildArticleDetailPages();
	}

	private void buildAnArticlelistPage(Board board, int pageBoxSize, List<Article> articles, int page) {

		StringBuilder sb = new StringBuilder();

		// 헤더 시작
		String head = getHeadHtml("article_list_" + board.code);
		sb.append(head);

		// 바디 시작
		String bodyTemplate = Util.getFileTemplate("site_template/article_list.html");

		StringBuilder mainContents = new StringBuilder();

		for (Article article : articles) {

			String link = "article_detail_" + article.id + ".html";

			mainContents.append("<div>");
			mainContents.append("<div class=\"article-list__cell-id\">" + article.id + "</div>");
			mainContents.append("<div class=\"article-list__cell-reg-date\">" + article.regDate + "</div>");
			mainContents.append("<div class=\"article-list__cell-writer\">" + article.userName + "</div>");
			mainContents.append("<div class=\"article-list__cell-title\">");
			mainContents.append("<a href=\"" + link + "\" class=\"hover-underline\">" + article.title + "</a>");
			mainContents.append("</div>");
			mainContents.append("</div>");
		}
		StringBuilder pageMenuContents = new StringBuilder();

		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\"> &lt; 이전</a></li>");
		pageMenuContents
				.append("<li><a href=\"#\"	class=\"flex flex-ai-c article-page-menu__link--selected\">1</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">2</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">3</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">4</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">5</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">6</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">7</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">8</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">9</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">10</a></li>");
		pageMenuContents.append("<li><a href=\"#\" class=\"flex flex-ai-c\">다음 &gt;</a></li>");

		String body = bodyTemplate.replace("${article-list__main-contents}", mainContents);
		body = body.replace("${article-page-menu__page-menu-contents}", pageMenuContents);

		sb.append(body);

		// 푸터 시작
		String foot = Util.getFileTemplate("site_template/foot.html");
		sb.append(foot);

		// 파일 생성 시작
		String fileName = "article_list_" + board.code + "_" + page + ".html";
		String filePath = "site/" + fileName;

		Util.fileWriter(filePath, sb.toString());
		System.out.println(filePath + " 생성.");
	}

	private void buildArticlelistPages() {
		List<Board> boards = articleService.getBoards();
		for (Board board : boards) {
			List<Article> articles = articleService.getArticlesForPrintOut(board.id);

			int pageBoxSize = 10;
			int articlesCount = articles.size();
			int totalPages = (int) Math.ceil((double) articlesCount / pageBoxSize);

			for (int i = 1; i <= totalPages; i++) {
				buildAnArticlelistPage(board, pageBoxSize, articles, i);
			}

		}
	}

	private void bildIndexPage() {
		String head = getHeadHtml("index");
		String foot = Util.getFileTemplate("site_template/foot.html");
		String index = Util.getFileTemplate("site_template/index.html");

		StringBuilder sb = new StringBuilder();
		sb.append(head);
		sb.append(index);
		sb.append(foot);

		String filePath = "site/index.html";
		Util.fileWriter(filePath, sb.toString());
		System.out.println(filePath + " 생성.");
	}

	private void buildArticleDetailPages() {
		List<Article> articles = articleService.showList();

		String head = getHeadHtml("article_detail");
		String foot = Util.getFileTemplate("site_template/foot.html");
		// 게시물 상세 페이지 생성
		for (Article article : articles) {
			StringBuilder sb = new StringBuilder();

			sb.append(head);

			sb.append("<div>");

			sb.append("번호 : " + article.id + "<br>");
			sb.append("생성날짜 : " + article.regDate + "<br>");
			sb.append("갱신날짜 : " + article.updateDate + "<br>");
			sb.append("제목 : " + article.title + "<br>");
			sb.append("내용 : " + article.body + "<br>");
			sb.append("<a href=\"article_detail_" + (article.id - 1) + ".html\">이전글</a><br>");
			sb.append("<a href=\"article_detail_" + (article.id + 1) + ".html\">다음글</a><br>");

			sb.append("</div>");

			sb.append(foot);

			String fileName = "article_detail_" + article.id + ".html";
			String filePath = "site/" + fileName;
			Util.fileWriter(filePath, sb.toString());
			System.out.println(filePath + " 생성.");
		}

	}

	private String getHeadHtml(String pageName) {
		String head = Util.getFileTemplate("site_template/head.html");
		StringBuilder boardMenuContentsHtml = new StringBuilder();

		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			boardMenuContentsHtml.append("<li>");

			String link = "article_list_" + board.code + "_1.html";
			boardMenuContentsHtml.append("<a href=\"" + link + "\" class=\"block\">");

			boardMenuContentsHtml.append(getTitleBarContentsByPageName("article_list_" + board.code));

			boardMenuContentsHtml.append("</a>");
			boardMenuContentsHtml.append("</li>");

		}

		head = head.replace("${menu-bar__menu-1__board-menu-contents}", boardMenuContentsHtml.toString());

		String titleBarContentsHtml = getTitleBarContentsByPageName(pageName);

		head = head.replace("${title-bar__contents}", titleBarContentsHtml);

		return head;
	}

	private String getTitleBarContentsByPageName(String pageName) {
		if (pageName.equals("index")) {
			return "<i class=\"fas fa-home\"></i> <span>HOME</span>";
		} else if (pageName.startsWith("article_detail")) {
			return "<i class=\"fas fa-file-invoice\"></i> <span>ARTICLE DETAIL</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-exclamation-circle\"></i> <span>NOTICE</span>";
		} else if (pageName.startsWith("article_list_free")) {
			return "<i class=\"fas fa-comment-dots\"></i> <span>FREE</span>";
		} else if (pageName.startsWith("article_list_")) {
			return "<i class=\"far fa-clipboard\"></i> <span>GENERAL</span>";
		}
		return "";
	}

}
