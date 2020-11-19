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
		int maxFailCount = 3;
		int failCount = 0;
		String accountName;
		String accountPW;
		String name;

		while (true) {
			if (failCount >= maxFailCount) {
				System.out.println("회원가입을 취소합니다.");
				return;
			}

			System.out.printf("사용하실 아이디 : ");
			accountName = sc.nextLine().trim();

			boolean isValidAcctName = userService.isValidAcctName(accountName);
			if (isValidAcctName == false) {
				failCount++;
				System.out.printf("%s는 이미 사용중인 아이디입니다.\n", accountName);
				continue;

			} else if (accountName.length() == 0) {
				failCount++;
				System.out.println("이이디를 입력해주세요.");
				continue;
			}
			failCount = 0;
			break;
		}

		while (true) {
			if (failCount >= maxFailCount) {
				System.out.println("회원가입을 취소합니다.");
				return;
			}

			System.out.printf("사용하실 비밀번호 : ");
			accountPW = sc.nextLine().trim();

			if (accountPW.length() == 0) {
				failCount++;
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}
			failCount = 0;
			break;
		}

		while (true) {
			if (failCount >= maxFailCount) {
				System.out.println("회원가입을 취소합니다.");
				return;
			}

			System.out.printf("이름 : ");
			name = sc.nextLine().trim();

			if (name.length() == 0) {
				failCount++;
				System.out.println("이름을 입력해주세요.");
				continue;
			}
			failCount = 0;
			break;
		}

		userService.doJoin(accountName, accountPW, name);
		System.out.printf("환영합니다 %s님!\n회원가입에 성공하였습니다.\n", name);
	}

}
