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
		buildArticleDetailPages();
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

			String link = board.code + "-list-1.html";
			boardMenuContentsHtml.append("<a href=\"" + link + "\" class=\"block\">");

			String iClass = "far fa-clipboard";

			if (board.code.contains("notice")) {
				iClass = "fas fa-exclamation-circle";
			} else if (board.code.contains("free")) {
				iClass = "fas fa-comment-dots";
			}

			boardMenuContentsHtml.append("<i class=\"" + iClass + "\"></i>");
			boardMenuContentsHtml.append(" ");
			boardMenuContentsHtml.append("<span>");
			boardMenuContentsHtml.append(board.name);
			boardMenuContentsHtml.append("</span>");

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
		}
		return "";
	}

}
