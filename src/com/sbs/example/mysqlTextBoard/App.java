package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.UserController;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;

public class App {
	Scanner sc = Container.sc;
	private ArticleController articleController = new ArticleController();
	private UserController userController = new UserController();

	public void run() {
		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();

			MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "textBoard");
			
			boolean needToExit = false;
			
			if (cmd.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				needToExit = true;
			} else if (cmd.startsWith("article ")) {
				articleController.doCmd(cmd);
			} else if (cmd.startsWith("user ")) {
				userController.doCmd(cmd);
			}
			
			MysqlUtil.closeConnection();
			
			if (needToExit) {
				break;
			}
		}
		sc.close();
	}

}
