package com.cooksys.ftd.file.storage.model.dao;

import java.sql.Connection;

public class AbstractDao {
	protected Connection conn;

	public Connection getConn() {
		return conn;
	}

	public static Object setConn(Connection conn) {
		return conn;
	}
}
