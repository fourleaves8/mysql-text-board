package com.sbs.example.mysqlTextBoard.dto;

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

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", title=" + title
				+ ", body=" + body + ", userId=" + userId + ", boardId=" + boardId + "]";
	}

}
