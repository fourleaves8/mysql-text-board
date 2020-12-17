package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.container.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.User;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.UserService;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private UserService userService;
	private Scanner sc;

	public ArticleController() {
		articleService = Container.articleService;
		userService = Container.userService;
		sc = Container.sc;
	}

	public void doCmd(String cmd) {
		if (cmd.equals("article list")) {
			showList(cmd);
		} else if (cmd.startsWith("article makeBoard")) {
			makeBoard(cmd);
		} else if (cmd.startsWith("article detail ")) {
			showDetail(cmd);
		} else if (cmd.startsWith("article delete ")) {
			doDelete(cmd);
		} else if (cmd.equals("article write")) {
			doWrite(cmd);
		} else if (cmd.startsWith("article modify ")) {
			doModify(cmd);
		} else {
			System.out.println("올바른 명령어를 입력하세요.");
		}
	}

	private void makeBoard(String cmd) {
		System.out.println("== 게시판 생성 ==");

		if (Container.session.islogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		int loginedUserId = Container.session.getLoginedUserId();
		User user = userService.getUserById(loginedUserId);

		if (user.isAdmin() == false) {
			System.out.println("***관리자만 게시판을 생성할 수 있습니다.***");
			return;
		}

		System.out.printf("게시판명 : ");
		String name = sc.nextLine();

		if (articleService.isValidBoardName(name) == false) {
			System.out.println("해당 게시판은 이미 존재합니다.");
			return;
		}

		System.out.printf("코드  : ");
		String code = sc.nextLine();

		if (articleService.isValidBoardCode(code) == false) {
			System.out.println("해당 코드는 이미 존재합니다.");
			return;
		}

		int boardId = articleService.makeBoard(name, code);

		System.out.printf("%s 게시판(%d번)이 생성되었습니다.\n", name, boardId);
	}

	private void doModify(String cmd) {
		System.out.println("== 게시물 수정 ==");

		if (Container.session.islogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		int inputId = Integer.parseInt(cmd.split(" ")[2]);
		Article article = articleService.getArticle(inputId);

		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}

		User user = userService.getUserById(article.userId);
		String writer = user.name;

		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성일 : %s\n", article.regDate);
		System.out.printf("작성자 : %s\n", writer);

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("코드  : ");
		String body = sc.nextLine();

		articleService.doModify(inputId, title, body);
		System.out.printf("%s번 게시물이 수정되었습니다.\n", inputId);

	}

	private void doWrite(String cmd) {
		System.out.println("== 게시물 작성 ==");

		if (Container.session.islogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용  : ");
		String body = sc.nextLine();

		int userId = Container.session.getLoginedUserId();
		int boardId = 1; // 임시

		int id = articleService.doWrite(title, body, userId, boardId);
		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
	}

	private void doDelete(String cmd) {
		System.out.println("== 게시물 삭제 ==");

		if (Container.session.islogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		int inputId = Integer.parseInt(cmd.split(" ")[2]);
		Article article = articleService.getArticle(inputId);

		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}

		articleService.doDelete(inputId);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", inputId);
	}

	private void showDetail(String cmd) {
		System.out.println("== 게시물 상세 ==");

		if (Container.session.islogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		int inputId = Integer.parseInt(cmd.split(" ")[2]);
		Article article = articleService.getArticle(inputId);

		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}

		User user = userService.getUserById(article.userId);
		String writer = user.name;

		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성일 : %s\n", article.regDate);
		System.out.printf("수정일 : %s\n", article.updateDate);
		System.out.printf("작성자 : %s\n", writer);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);

	}

	private void showList(String cmd) {
		String boardCode = Container.session.getSelectedBoardCode();
		Board board = articleService.getBoardByCode(boardCode);
		String boardName = board.name;
		System.out.printf("== %s 게시물 리스트 ==\n", boardName);

		List<Article> articles = articleService.getArticlesForPrintOut(board.id);

		System.out.println("번호 / 작성 / 수정 / 작성자 / 제목");
		for (Article article : articles) {
			String writer = article.userName;
			System.out.printf("%d / %s / %s / %s / %s\n", article.id, article.regDate, article.updateDate, writer,
					article.title);
		}

	}

}
