package com.sbs.example.mysqlTextBoard.service;

import java.io.File;
import java.util.List;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.util.Util;

public class BuildService {

	private ArticleService articleService;

	public BuildService() {
		articleService = Container.articleService;
	}

	public void buildsite() {
		String dirPath = "site";
		String headerTemplatePath = "site_template/head.html";
		String footerTemplatePath = "site_template/foot.html";

		Util.rmdir(new File(dirPath));
		Util.mkdirs(dirPath);

		String head = Util.getFileTemplate(new File(headerTemplatePath));
		String foot = Util.getFileTemplate(new File(footerTemplatePath));

		List<Article> articles = articleService.showList();

		for (Article article : articles) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(head);
		
			sb.append("번호 : " + article.id + "<br>");
			sb.append("생성날짜 : " + article.regDate + "<br>");
			sb.append("갱신날짜 : " + article.updateDate + "<br>");
			sb.append("제목 : " + article.title + "<br>");
			sb.append("내용 : " + article.body + "<br>");

			sb.append("</div>");

			sb.append(foot);
			
			String fileName = "article_detail_" + article.id + ".html";
			String filePath = "site/" + fileName;

			String body = sb.toString();

			Util.fileWriter(filePath, body);
			System.out.println(filePath + "가 생성되었습니다.");

		}
	}

}
