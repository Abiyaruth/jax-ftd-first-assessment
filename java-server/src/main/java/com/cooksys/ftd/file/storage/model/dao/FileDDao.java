package com.cooksys.ftd.file.storage.model.dao;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.file.storage.model.FileD;

public class FileDDao extends AbstractDao {

	private Logger log = LoggerFactory.getLogger(FileDDao.class);

	public FileD createFile(FileD filep) throws SQLException, FileNotFoundException {
		String createFl = "INSERT INTO file (filepath,filedata)" + "VALUES(?,?)";
		PreparedStatement stmt = conn.prepareStatement(createFl, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, filep.getFilepath());
		stmt.setObject(2, filep.getFiledata());
		int result = stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		if (result == 0 || !rs.next())
			filep.setFileid(rs.getInt(1));
		stmt.close();
		return filep;
	}

	public String getFileFromPath(String filepath) {
		String getfile = "SELECT * FROM file WHERE filename=" + filepath + "";
		FileD file = null;
		try {
			PreparedStatement stmt1 = conn.prepareStatement(getfile);
			ResultSet rs = stmt1.executeQuery();
			rs.next();
			Long pk = rs.getLong(FileD.PRIMARY_KEY);
			byte[] fileData = rs.getBytes(FileD.FILE_COLUMN);

			file = new FileD(null, filepath, fileData);
			file.setPrimaryKey(pk);

			rs.close();
			stmt1.close();
		} catch (SQLException e) {
			log.warn("Error preparing or executing sql: ");
			log.warn(e.getMessage());
			log.error("Got an error", e);
		}
		return filepath;
	}
}
