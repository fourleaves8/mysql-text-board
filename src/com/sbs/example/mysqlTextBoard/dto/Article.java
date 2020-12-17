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
	public String userName;

	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (String) articleMap.get("regDate");
		this.updateDate = (String) articleMap.get("updateDate");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.userId = (int) articleMap.get("userId");
		this.boardId = (int) articleMap.get("boardId");
		if(articleMap.containsKey("userName")) {
			this.userName = (String) articleMap.get("userName");	
		}
		

	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", title=" + title
				+ ", body=" + body + ", userId=" + userId + ", boardId=" + boardId + "]";
	}

}
