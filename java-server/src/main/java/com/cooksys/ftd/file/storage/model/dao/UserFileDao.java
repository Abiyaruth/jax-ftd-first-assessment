package com.cooksys.ftd.file.storage.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cooksys.ftd.file.storage.model.FileD;
import com.cooksys.ftd.file.storage.model.User;
//import com.cooksys.ftd.file.storage.model.UserFile;

public class UserFileDao extends AbstractDao {
	public List<String[]> getFilebyUserid(User user, FileD file) throws SQLException {
		List<String[]> files = new ArrayList<String[]>();
		int id = 0;
		String sql = "SELECT userid FROM user WHERE username = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, user.getUsername());

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			id = rs.getInt("userid");
		}

		sql = "SELECT f.user_file_number, f.absolute_path FROM files f WHERE f.userid = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		while (rs.next()) {
			String[] temp = new String[2];
			temp[0] = "" + rs.getInt("f.user_file_number");
			temp[1] = rs.getString("f.absolute_path");
			files.add(temp);
		}
		return files;

	}
}
