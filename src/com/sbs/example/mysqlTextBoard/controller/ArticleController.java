package com.sbs.example.mysqlTextBoard.controller;

public class ArticleController {

	public void doCmd(String cmd) {
		if (cmd.equals("article list")) {
			showList();
		}

	}

	private void showList() {
		System.out.println("== 게시물 리스트 ==");

	}

}
