package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlTextBoard.mysqlutil.SecSql;

public class ArticleDao {

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `article`");
		sql.append("ORDER BY id DESC");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}

	public Article getArticle(int inputId) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", inputId);
		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);
		if (articleMap.isEmpty()) {
			return null;
		}
		return new Article(articleMap);
	}

	public void remove(int inputId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM `article`");
		sql.append("WHERE id = ?", inputId);

		MysqlUtil.delete(sql);
	}

	public int add(String title, String body, int memberId, int boardId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("body = ?,", body);
		sql.append("memberId = ?,", memberId);
		sql.append("boardId = ?", boardId);

		return MysqlUtil.insert(sql);
	}

	public List<Article> getArticlesForPrintOut(int boardId) {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT A.*,");
		sql.append("U.name AS memberName");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS U");
		sql.append("ON A.memberId = U.id");

		if (boardId != 0) {
			sql.append("WHERE A.boardId = ?", boardId);
		}

		sql.append("ORDER BY A.id DESC");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}

	public void modify(int id, String title, String body) {
		SecSql sql = new SecSql();
		sql.append("UPDATE `article`");
		sql.append("SET updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("`body` = ?", body);
		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);
	}

	public Board getBoardByCode(String boardCode) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `board`");
		sql.append("WHERE `code` = ?", boardCode);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}
		return new Board(boardMap);
	}

	public Board getBoardByName(String name) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `board`");
		sql.append("WHERE `name` = ?", name);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}
		return new Board(boardMap);
	}

	public int makeBoard(String name, String code) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `board`");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("name = ?,", name);
		sql.append("code = ?", code);

		return MysqlUtil.insert(sql);

	}

	public List<Board> getBoards() {
		List<Board> boards = new ArrayList<>();
		SecSql sql = new SecSql();
		sql.append("SELECT B.*");
		sql.append("FROM `board` AS B");
		sql.append("ORDER BY B.id");

		List<Map<String, Object>> boardMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> boardMap : boardMapList) {
			boards.add(new Board(boardMap));
		}
		return boards;
	}

}
