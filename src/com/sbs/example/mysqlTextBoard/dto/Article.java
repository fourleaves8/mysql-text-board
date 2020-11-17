package com.sbs.example.mysqlTextBoard.dto;

public class Article {

	public int articleId;
	public String regDate;
	public String updateDate;
	public String title;
	public String body;
	public int userId;
	public int boardId;

	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", regDate=" + regDate + ", updateDate=" + updateDate + ", title="
				+ title + ", body=" + body + ", userId=" + userId + ", boardId=" + boardId + "]";
	}

}
