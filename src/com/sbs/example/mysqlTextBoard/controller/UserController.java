package com.sbs.example.mysqlTextBoard.controller;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.User;
import com.sbs.example.mysqlTextBoard.service.UserService;
import com.sbs.example.mysqlTextBoard.session.Session;

public class UserController extends Controller {

	private Scanner sc;
	private UserService userService;

	public UserController() {
		sc = Container.sc;
		userService = Container.userService;
	}

	public void doCmd(String cmd) {
		if (cmd.equals("user join")) {
			doJoin();
		} else if (cmd.equals("user login")) {
			doLogin();
		} else if (cmd.equals("user logout")) {
			doLogout();
		} else if (cmd.equals("user whoami")) {
			showWhoami();
		} else {
			System.out.println("올바른 명령어를 입력하세요.");
		}
	}

	private void showWhoami() {
		System.out.println("== 회원 확인 ==");
		if (Container.session.islogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		int loginedUserId = Container.session.getLoginedUserId();
		User user = userService.getUserById(loginedUserId);
		System.out.printf("회원번호 : %d\n", user.id);
		System.out.printf("가입일 : %s\n", user.regDate);
		System.out.printf("아이디 : %s\n", user.accountName);
		System.out.printf("이름 : %s\n", user.name);
	}

	private void doLogout() {
		if (Container.session.islogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		Container.session.doLogout();
		System.out.println("로그아웃 되었습니다.");
	}

	private void doLogin() {
		System.out.println("== 회원 로그인 ==");
		if (Container.session.islogined()) {
			int LoginedUserId = Container.session.getLoginedUserId();
			User user = userService.getUserById(LoginedUserId);
			System.out.printf("현재 %s님께서 이미 로그인되어있습니다.\n", user.name);
			System.out.println("**다른 계정으로 로그인하시려면 로그아웃을 먼저 진행해주세요.**");
			return;
		}
		int failCount = 0;
		int maxFailCount = 3;
		String accountName = "";
		String accountPw;
		User user;

		while (true) {
			if (failCount >= maxFailCount) {
				System.out.println("로그인을 취소합니다.");
				return;
			}

			System.out.printf("회원아이디 : ");
			accountName = sc.nextLine().trim();
			user = userService.getUserByAcctName(accountName);

			if (accountName.length() == 0) {
				failCount++;
				System.out.println("아이디를 입력해주세요.");
				continue;
			} else if (user == null) {
				failCount++;
				System.out.println("존재하지 않는 아이디입니다.");
				continue;
			}
			failCount = 0;
			break;
		}

		while (true) {
			if (failCount >= maxFailCount) {
				System.out.println("로그인을 취소합니다.");
				return;
			}

			System.out.printf("비밀번호 : ");
			accountPw = sc.nextLine().trim();

			if (accountPw.length() == 0) {
				failCount++;
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			} else if (accountPw.equals(user.accountPw) == false) {
				failCount++;
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			break;
		}
		Container.session.login(user.id);
		System.out.printf("로그인 성공! %s님 환영합니다.\n", user.name);
	}

	private void doJoin() {
		System.out.println("== 회원 가입 ==");

		if (Container.session.islogined()) {
			int LoginedUserId = Container.session.getLoginedUserId();
			User user = userService.getUserById(LoginedUserId);
			System.out.printf("현재 %s님께서 이미 로그인되어있습니다.\n", user.name);
			System.out.println("**회원가입을 하시려면 로그아웃을 먼저 진행해주세요.**");
			return;
		}

		int maxFailCount = 3;
		int failCount = 0;
		String accountName;
		String accountPw;
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
			accountPw = sc.nextLine().trim();

			if (accountPw.length() == 0) {
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

		userService.doJoin(accountName, accountPw, name);
		System.out.printf("환영합니다 %s님!\n회원가입에 성공하였습니다.\n", name);
	}

}
