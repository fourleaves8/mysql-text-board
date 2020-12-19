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
		String dirPath = "site";

		Util.rmdir(new File(dirPath));
		Util.mkdir(new File(dirPath));

		String sourceFilePath = "site_template/app.css";
		String targetFilePath = "site/app.css";
		File sourceFile = new File(sourceFilePath);
		File targetFile = new File(targetFilePath);

		Util.copy(sourceFile, targetFile);

		String headerTemplatePath = "site_template/head.html";
		String footerTemplatePath = "site_template/foot.html";

		String head = getHeadHtml(headerTemplatePath);

		String foot = Util.getFileTemplate(footerTemplatePath);

		List<Article> articles = articleService.showList();

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

			String body = sb.toString();

			Util.fileWriter(filePath, body);
			System.out.println(filePath + "가 생성되었습니다.");

		}
	}

	private String getHeadHtml(String headerTemplatePath) {
		String head = Util.getFileTemplate(headerTemplatePath);
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

			boardMenuContentsHtml.append("<span>");
			boardMenuContentsHtml.append(board.name);
			boardMenuContentsHtml.append("</span>");

			boardMenuContentsHtml.append("</a>");
			boardMenuContentsHtml.append("</li>");

		}

		head = head.replace("${menu-bar__menu-1__board-menu-contents}", boardMenuContentsHtml.toString());
		return head;
	}

}
