package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.User;

public class UserDao {
	
	public int add(String accountName, String accountPW, String name) {
		int id = 0;
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

			String sql = "INSERT INTO user";
			sql += " SET regDate = NOW(),";
			sql += " updateDate = NOW(),";
			sql += " accountName = ?,";
			sql += " accountPW = ?,";
			sql += " name = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, accountName);
				pstmt.setString(2, accountPW);
				pstmt.setString(3, name);

				pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
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
		return id;
	}

}
