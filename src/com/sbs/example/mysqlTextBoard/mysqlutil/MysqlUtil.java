package com.sbs.example.mysqlTextBoard.mysqlutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlUtil {
	private static String dbHost;
	private static String dbLoginId;
	private static String dbLoginPw;
	private static String dbName;
	private static boolean isDevMode;

	private static Map<Long, Connection> connections;

	static {
		connections = new HashMap<>();
	}

	public static void setDevMode(boolean isDevMode) {
		MysqlUtil.isDevMode = isDevMode;
	}

	public static boolean isDevMode() {
		return isDevMode;
	}

	public static void setDBInfo(String dbHost, String dbLoginId, String dbLoginPw, String dbName) {
		MysqlUtil.dbHost = dbHost;
		MysqlUtil.dbLoginId = dbLoginId;
		MysqlUtil.dbLoginPw = dbLoginPw;
		MysqlUtil.dbName = dbName;
	}

	public static void closeConnection() {
		long currentThreadId = Thread.currentThread().getId();

		if (connections.containsKey(currentThreadId) == false) {
			return;
		}

		Connection connection = connections.get(currentThreadId);

		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		connections.remove(currentThreadId);
	}

	private static Connection getConnection() {
		long currentThreadId = Thread.currentThread().getId();

		if (connections.containsKey(currentThreadId) == false) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new MysqlUtilException(e);
			}

			Connection connection = null;

			String url = "jdbc:mysql://" + dbHost + "/" + dbName
					+ "?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60";
			try {
				connection = DriverManager.getConnection(url, dbLoginId, dbLoginPw);
				connections.put(currentThreadId, connection);

			} catch (SQLException e) {
				closeConnection();
				throw new MysqlUtilException(e);
			}
		}

		return connections.get(currentThreadId);
	}

	public static Map<String, Object> selectRow(SecSql sql) {
		List<Map<String, Object>> rows = selectRows(sql);

		if (rows.size() == 0) {
			return new HashMap<>();
		}

		return rows.get(0);
	}

	public static List<Map<String, Object>> selectRows(SecSql sql) throws MysqlUtilException {
		List<Map<String, Object>> rows = new ArrayList<>();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = sql.getPreparedStatement(getConnection());
			rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnSize = metaData.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();

				for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
					String columnName = metaData.getColumnName(columnIndex + 1);
					Object value = rs.getObject(columnName);

					if (value instanceof Long) {
						int numValue = (int) (long) value;
						row.put(columnName, numValue);
					} else if (value instanceof Timestamp) {
						String dateValue = value.toString();
						dateValue = dateValue.substring(0, dateValue.length() - 2);
						row.put(columnName, dateValue);
					} else {
						row.put(columnName, value);
					}
				}

				rows.add(row);
			}
		} catch (SQLException e) {
			closeConnection();
			throw new MysqlUtilException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					closeConnection();
					throw new MysqlUtilException(e);
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					closeConnection();
					throw new MysqlUtilException(e);
				}
			}
		}

		return rows;
	}

	public static int selectRowIntValue(SecSql sql) {
		Map<String, Object> row = selectRow(sql);

		for (String key : row.keySet()) {
			return (int) row.get(key);
		}

		return -1;
	}

	public static String selectRowStringValue(SecSql sql) {
		Map<String, Object> row = selectRow(sql);

		for (String key : row.keySet()) {
			return (String) row.get(key);
		}

		return "";
	}

	public static boolean selectRowBooleanValue(SecSql sql) {
		Map<String, Object> row = selectRow(sql);

		for (String key : row.keySet()) {
			return ((int) row.get(key)) == 1;
		}

		return false;
	}

	public static int insert(SecSql sql) {
		int id = -1;

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = sql.getPreparedStatement(getConnection());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			closeConnection();
			throw new MysqlUtilException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					closeConnection();
					throw new MysqlUtilException(e);
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					closeConnection();
					throw new MysqlUtilException(e);
				}
			}

		}

		return id;
	}

	public static int update(SecSql sql) {
		int affectedRows = 0;

		PreparedStatement stmt = null;

		try {
			stmt = sql.getPreparedStatement(getConnection());
			affectedRows = stmt.executeUpdate();
		} catch (SQLException e) {
			closeConnection();
			throw new MysqlUtilException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					closeConnection();
					throw new MysqlUtilException(e);
				}
			}
		}

		return affectedRows;
	}

	public static int delete(SecSql sql) {
		return update(sql);
	}
}
