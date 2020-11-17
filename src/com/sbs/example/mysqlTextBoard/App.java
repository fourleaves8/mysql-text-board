package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.controller.ArticleController;

public class App {
	Scanner sc = Container.sc;
	private ArticleController articleController = new ArticleController();

	public void run() {
		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();

			if (cmd.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			} else if (cmd.startsWith("article ")) {
				articleController.doCmd(cmd);
			}
		}
		sc.close();
	}

}
