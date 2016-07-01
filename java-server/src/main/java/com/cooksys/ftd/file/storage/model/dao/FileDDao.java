package com.cooksys.ftd.file.storage.model.dao;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;


import com.cooksys.ftd.file.storage.model.FileD;

public class FileDDao extends AbstractDao {

	int id;

	public int createFile(FileD filep) throws SQLException, FileNotFoundException {
	
		String sql = "SELECT userid FROM user WHERE username = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, filep.getUsername());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			id = rs.getInt("userid");
		}
		// Insert the file into database
		
		String createFl = "INSERT INTO file (userid,absolute_path,file_data)" + "VALUES(?,?,?)";
		PreparedStatement stmt0 = conn.prepareStatement(createFl, Statement.RETURN_GENERATED_KEYS);
		byte[] buffer = Base64.getDecoder().decode(filep.getByteArray());
		stmt0.setInt(1, id);
		stmt0.setString(2, filep.getAbsolutePath());
		stmt0.setObject(3,buffer);
		stmt0.executeUpdate();
		sql = "SELECT @@IDENTITY";// returns the last value inserted
		stmt = this.getConn().prepareStatement(sql);
		rs = stmt.executeQuery();
		if (rs.next()) {
			id = rs.getInt("@@IDENTITY");
			return id;
		}
		return -1;
	}

	public FileD sendFile(int fileId) throws SQLException {
		FileD f = new FileD();
		String sql = "SELECT absolute_path, file_data FROM files WHERE fileid = ?";
		PreparedStatement stmt = this.getConn().prepareStatement(sql);
		stmt.setInt(1, fileId);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			f.setAbsolutePath(rs.getString("absolute_path"));
			byte[] buffer = rs.getBytes("file_data");
			String tob64 = Base64.getEncoder().encodeToString(buffer);
			f.setByteArray(tob64);
		}
		return f;
	}
	
public int checkUser(String userName, int fileId) throws SQLException {
		
		String sql = "SELECT userid FROM user WHERE username = ?";
		PreparedStatement stmt = this.getConn().prepareStatement(sql);
		stmt.setString(1, userName);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			id = rs.getInt("userid");
		}
		
		sql = "SELECT f.fileid FROM files f WHERE f.userid = ? AND f.fileid = ?";
		stmt = this.getConn().prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.setInt(2, fileId);
		rs = stmt.executeQuery();
		if(rs.next()) {
			return rs.getRow();
		}
		return 0;
	}

public ArrayList<String[]> listFiles(String userName) throws SQLException {
	ArrayList<String[]> files = new ArrayList<String[]>();
	String sql = "SELECT userid FROM user WHERE username = ?";
	PreparedStatement stmt = this.getConn().prepareStatement(sql);
	stmt.setString(1, userName);
	ResultSet rs = stmt.executeQuery();
	if (rs.next()) {
		id = rs.getInt("userid");
	}
	sql = "SELECT f.fileid, f.absolute_path FROM files f WHERE f.userid = ?";
	stmt = this.getConn().prepareStatement(sql);
	stmt.setInt(1, id);
	rs = stmt.executeQuery();
	while (rs.next()) {
		String[] temp = new String[2];
		temp[0] = "" + rs.getInt("f.fileid");
		temp[1] = rs.getString("f.absolute_path");
		files.add(temp);
	}
	return files;
}
}


