package com.sbs.example.mysqlTextBoard.controller;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.service.UserService;

public class UserController {

	private Scanner sc;
	private UserService userService;

	public UserController() {
		sc = Container.sc;
		userService = Container.userService;
	}

	public void doCmd(String cmd) {
		if (cmd.equals("user join")) {
			doJoin();
		}

	}

	private void doJoin() {
		System.out.println("== 회원 가입 ==");
		System.out.printf("사용하실 아이디 : ");
		String accountName = sc.nextLine();
		System.out.printf("사용하실 비밀번호 : ");
		String accountPW = sc.nextLine();
		System.out.printf("이름 : ");
		String name = sc.nextLine();

		int id = userService.doJoin(accountName, accountPW, name);
		System.out.printf("%d번 회원이 생성되었습니다.\n", id);
	}

}
