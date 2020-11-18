package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleDao {
	private List<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();

	}

	public List<Article> getArticles() {

		Connection con = null;
		try {
			String dbmsJdbcUrl = "jdbc:mysql://localhost:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			String dbmsJdbcLoginId = "sbsst";
			String dbmsJdbcLoginPw = "sbs123414";

			// MySQL 드라이버 등록
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			// 연결 생성
			try {
				con = DriverManager.getConnection(dbmsJdbcUrl, dbmsJdbcLoginId, dbmsJdbcLoginPw);

			} catch (SQLException e) {
				e.printStackTrace();

			}

			String sql = "SELECT * FROM article";
			sql += " ORDER BY articleId DESC";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Article article = new Article();
					
					article.articleId = rs.getInt("articleId");
					article.regDate = rs.getString("regDate");
					article.updateDate = rs.getString("updateDate");
					article.title = rs.getString("title");
					article.body = rs.getString("body");
					article.userId = rs.getInt("userId");
					article.boardId = rs.getInt("boardId");

					articles.add(article);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return articles;
	}

}
