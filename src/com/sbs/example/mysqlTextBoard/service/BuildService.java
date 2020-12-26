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
		String filePath = "site";
		Util.rmFilesInDir(new File(filePath));
		Util.mkdir(new File(filePath));
		System.out.println("site 폴더 내 파일 삭제 후 다시 생성");

		System.out.println("site/app.css 생성.");
		Util.copy(new File("site_template/app.css"), new File("site/app.css"));

		bildIndexPage();
		buildArticlelistPages();
		buildArticleDetailPages();
	}

	private void buildAnArticlelistPage(Board board, int itemsCountInAPage, int pageBoxSize, List<Article> articles,
			int articlesCount, int page, int totalPages) {

		StringBuilder sb = new StringBuilder();

		// 헤더 시작
		String head = getHeadHtml("article_list_" + board.code);
		sb.append(head);

		// 바디 시작
		String bodyTemplate = Util.getFileTemplate("site_template/article_list.html");

		StringBuilder mainContents = new StringBuilder();

		int firstArticleIndexOfPage = (page - 1) * itemsCountInAPage;
		int lastArticleIndexOfPage = firstArticleIndexOfPage + itemsCountInAPage - 1;

		if (lastArticleIndexOfPage >= articlesCount) {
			lastArticleIndexOfPage = articlesCount - 1;
		}

		for (int i = firstArticleIndexOfPage; i <= lastArticleIndexOfPage; i++) {
			Article article = articles.get(i);

			String link = getArticleDetailFileName(article.id);

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

		// 현재 페이지 계산
		if (page < 1) {
			page = 1;
		} else if (page > totalPages) {
			page = totalPages;
		}

		// 현재 페이지박스 시작, 끝 계산 (e.g. page = 17)
		int previousPageBoxesCount = (page - 1) / pageBoxSize; // 이전 페이지박스 대역 구하기 (e.g. ('17'-1)/10 = *1* (자동 소수점 버림))
		int pageBoxStartPage = pageBoxSize * previousPageBoxesCount + 1; // 현재 페이지 박스의 첫 페이지 (e.g. '1'*10+1 = *11*)
		int pageBoxEndPage = pageBoxStartPage + pageBoxSize - 1; // 현재 페이지 박스의 마지막 페이지 (e.g. '11' + 10 -1 = *20*)

		if (pageBoxEndPage > totalPages) {
			pageBoxEndPage = totalPages;
		}

		// 이전, 다음 버튼 페이지 계산
		int pageBforePageBoxStartPage = pageBoxStartPage - 1; // 이전페이지 (e.g. '11' - 1 = *10*)
		int pageAfterPageBoxEndPage = pageBoxEndPage + 1; // 다음페이지 (e.g. '20' + 1 = *21*)

		if (pageBforePageBoxStartPage < 1) { // e.g. 게시물 100개 미만, page 1~10이 끝일때, 1-1=0 이지만 1로 설정됨.
			pageBforePageBoxStartPage = 1;
		}

		if (pageAfterPageBoxEndPage > totalPages) { // e.g. totlaPages = 30 일때, 30+1=1 이지만 30으로 설정됨.
			pageAfterPageBoxEndPage = totalPages;
		}

		// 이전, 다음 버튼 노출여부 계산
		boolean needBtnBeforePageBox = pageBforePageBoxStartPage != pageBoxStartPage; // 이전버튼은 눌렀을때의 첫 페이지와 현재의 첫 페이지가
																						// 다를때 생성.
		boolean needBtnAfterPageBox = pageAfterPageBoxEndPage != pageBoxEndPage; // 다음버튼은 눌렀을때의 마지막 페이지와 현재의 마지막 페이지가
																					// 다를때 생성.

		if (needBtnBeforePageBox) {
			pageMenuContents.append("<li><a href=\"" + getArticleListFileName(board, pageBforePageBoxStartPage)
					+ "\" class=\"flex flex-ai-c\"> &lt; 이전</a></li>");
		}
		for (int i = pageBoxStartPage; i <= pageBoxEndPage; i++) {
			String selectedClass = "";
			if (i == page) {
				selectedClass = "article-page-menu__link--selected";
			}
			pageMenuContents.append("<li><a href=\"" + getArticleListFileName(board, i) + "\" class=\"flex flex-ai-c "
					+ selectedClass + "\">" + i + "</a></li>");
		}
		if (needBtnAfterPageBox) {
			pageMenuContents.append("<li><a href=\"" + getArticleListFileName(board, pageAfterPageBoxEndPage)
					+ "\" class=\"flex flex-ai-c\">다음 &gt;</a></li>");
		}

		String body = bodyTemplate.replace("${article-list__main-contents}", mainContents);
		body = body.replace("${article-page-menu__page-menu-contents}", pageMenuContents);

		sb.append(body);

		// 푸터 시작
		String foot = Util.getFileTemplate("site_template/foot.html");
		sb.append(foot);

		// 파일 생성 시작
		String fileName = getArticleListFileName(board, page);
		String filePath = "site/" + fileName;

		Util.fileWriter(filePath, sb.toString());
		System.out.println(filePath + " 생성.");
	}

	private String getArticleListFileName(Board board, int page) {
		return getArticleListFileName(board.code, page);
	}

	private String getArticleListFileName(String boardCode, int page) {
		return "article_list_" + boardCode + "_" + page + ".html";
	}

	private void buildArticlelistPages() {
		List<Board> boards = articleService.getBoards();
		for (Board board : boards) {
			List<Article> articles = articleService.getArticlesForPrintOut(board.id);

			int itemsCountInAPage = 10;
			int pageBoxSize = 10;
			int articlesCount = articles.size();
			int totalPages = (int) Math.ceil((double) articlesCount / itemsCountInAPage);

			for (int i = 1; i <= totalPages; i++) {
				buildAnArticlelistPage(board, itemsCountInAPage, pageBoxSize, articles, articlesCount, i, totalPages);
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
		List<Board> boards = articleService.getBoards();

		String head = getHeadHtml("article_detail");
		String bodyTemplate = Util.getFileTemplate("site_template/article_detail.html");
		String foot = Util.getFileTemplate("site_template/foot.html");

		for (Board board : boards) {
			List<Article> articles = articleService.getArticlesForPrintOut(board.id);

			for (int i = 0; i < articles.size(); i++) {
				Article article = articles.get(i);
				Article prevArticle = null;
				Article nextArticle = null;

				// 인덱스가 커질수록 예전글 (게시물을 DESC 정렬했으므로 가장 최신글부터 0에 저장)
				int prevArticleIndex = i + 1; // 이전글 = 예전글
				int preveArticleId = 0;
				if (prevArticleIndex < articles.size()) {
					prevArticle = articles.get(prevArticleIndex);
					preveArticleId = prevArticle.id;
				}

				// 인덱스가 작아질수록 최신글
				int nextArticleIndex = i - 1; // 다음글: 최신글
				int nextArticleId = 0;
				if (nextArticleIndex >= 0) {
					nextArticle = articles.get(nextArticleIndex);
					nextArticleId = nextArticle.id;
				}

				StringBuilder sb = new StringBuilder();
				String body = bodyTemplate;
				body = body.replace("${article-detail__title}", article.title);
				body = body.replace("${article-detail__board-name}", board.name);
				body = body.replace("${article-detail__reg-date}", article.regDate);
				body = body.replace("${article-detail__writer}", article.userName);
				body = body.replace("${article-detail__body}", article.body);
				body = body.replace("${article-detail__link-prev-article-url}",
						getArticleDetailFileName(preveArticleId));
				body = body.replace("${article-detail__link-prev-article-class-addi}",
						preveArticleId == 0 ? "none" : "");
				body = body.replace("${article-detail__link-prev-article-title-attr}",
						prevArticle != null ? prevArticle.title : "");
				body = body.replace("${article-detail__link-article-list-url}",
						getArticleListFileName(board.code, getPageNumber(i)));
				body = body.replace("${article-detail__link-article-list-class-addi}", "");
				body = body.replace("${article-detail__link-next-article-url}",
						getArticleDetailFileName(nextArticleId));
				body = body.replace("${article-detail__link-next-article-class-addi}",
						nextArticleId == 0 ? "none" : "");
				body = body.replace("${article-detail__link-next-article-title-attr}",
						nextArticle != null ? nextArticle.title : "");

				sb.append(head);
				sb.append(body);
				sb.append(foot);

				String fileName = getArticleDetailFileName(article.id);
				String filePath = "site/" + fileName;
				Util.fileWriter(filePath, sb.toString());
				System.out.println(filePath + " 생성.");
			}

		}

	}

	private int getPageNumber(int i) {
		return i / 10 + 1;

	}

	private String getArticleDetailFileName(int id) {
		return "article_detail_" + id + ".html";
	}

	private String getHeadHtml(String pageName) {
		String head = Util.getFileTemplate("site_template/head.html");
		StringBuilder boardMenuContentsHtml = new StringBuilder();

		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			boardMenuContentsHtml.append("<li>");

			String link = getArticleListFileName(board, 1);
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
