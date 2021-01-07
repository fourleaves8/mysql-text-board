package com.sbs.example.mysqlTextBoard.controller;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class MemberController extends Controller {

	private Scanner sc;
	private MemberService memberService;

	public MemberController() {
		sc = Container.sc;
		memberService = Container.memberService;
	}

	public void doCmd(String cmd) {
		if (cmd.equals("member join")) {
			doJoin();
		} else if (cmd.equals("member login")) {
			doLogin();
		} else if (cmd.equals("member logout")) {
			doLogout();
		} else if (cmd.equals("member whoami")) {
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
		int loginedMemberId = Container.session.getLoginedMemberId();
		Member member = memberService.getMemberById(loginedMemberId);
		System.out.printf("회원번호 : %d\n", member.id);
		System.out.printf("가입일 : %s\n", member.regDate);
		System.out.printf("아이디 : %s\n", member.accountName);
		System.out.printf("이름 : %s\n", member.name);
		System.out.printf("회원종류 : %s\n", member.getType());
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
			int LoginedMemberId = Container.session.getLoginedMemberId();
			Member member = memberService.getMemberById(LoginedMemberId);
			System.out.printf("%s 님으로 로그인되어있습니다.\n", member.name);
			System.out.println("*** 로그아웃 후 재시도 해주세요. ***");
			return;
		}
		int failCount = 0;
		int maxFailCount = 3;
		String accountName = "";
		String accountPw;
		Member member;

		while (true) {
			if (failCount >= maxFailCount) {
				System.out.println("로그인을 취소합니다.");
				return;
			}

			System.out.printf("회원아이디 : ");
			accountName = sc.nextLine().trim();
			member = memberService.getMemberByAcctName(accountName);

			if (accountName.length() == 0) {
				failCount++;
				System.out.println("아이디를 입력해주세요.");
				continue;
			} else if (member == null) {
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
			} else if (accountPw.equals(member.accountPw) == false) {
				failCount++;
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			break;
		}
		Container.session.login(member.id);
		System.out.printf("로그인 성공! %s님 환영합니다.\n", member.name);
	}

	private void doJoin() {
		System.out.println("== 회원 가입 ==");

		if (Container.session.islogined()) {
			int LoginedMemberId = Container.session.getLoginedMemberId();
			Member member = memberService.getMemberById(LoginedMemberId);
			System.out.printf("%s 님으로 로그인되어있습니다.\n", member.name);
			System.out.println("*** 로그아웃 후 재시도 해주세요. ***");
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

			boolean isValidAcctName = memberService.isValidAcctName(accountName);
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

		memberService.doJoin(accountName, accountPw, name);
		System.out.printf("환영합니다 %s님!\n회원가입에 성공하였습니다.\n", name);
	}

}
