package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Article {

	public int id;
	public String regDate;
	public String updateDate;
	public String title;
	public String body;
	public int userId;
	public int boardId;

	public Article(int id, String regDate, String updateDate, String title, String body, int userId, int boardId) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		this.userId = userId;
		this.boardId = boardId;

	}

	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (String) articleMap.get("regDate");
		this.updateDate = (String) articleMap.get("updateDate");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.userId = (int) articleMap.get("userId");
		this.boardId = (int) articleMap.get("boardId");

	}

}
