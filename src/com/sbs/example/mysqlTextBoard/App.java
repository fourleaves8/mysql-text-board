package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.controller.Controller;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;

public class App {
	public void run() {
		Scanner sc = Container.sc;
		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();

			MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "textBoard");

			boolean needToExit = false;

			if (cmd.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				needToExit = true;
			} else {
				Controller controller = getControllerByCmd(cmd);
				if (controller != null) {
					controller.doCmd(cmd);
				}
			}

			MysqlUtil.closeConnection();

			if (needToExit) {
				break;
			}
		}
		sc.close();
	}

	private Controller getControllerByCmd(String cmd) {
		if (cmd.startsWith("article ")) {
			return Container.articleController;
		} else if (cmd.startsWith("member ")) {
			return Container.memberController;
		} else if (cmd.startsWith("build ")) {
			return Container.buildController;
		}
		return null;
	}

}
