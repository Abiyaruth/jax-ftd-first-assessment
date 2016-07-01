package com.cooksys.ftd.file.storage.model.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.cooksys.ftd.file.storage.model.FileD;
import com.cooksys.ftd.file.storage.model.User;
import com.cooksys.ftd.file.storage.model.dao.FileDDao;
import com.cooksys.ftd.file.storage.model.dao.UserDao;
import com.cooksys.ftd.file.storage.model.dao.UserFileDao;

public class Abstract {
	protected String filepath;
	protected String username;
	protected UserDao userdao;
	protected FileDDao filedao;
	protected UserFileDao userfiledao;
	protected User user;
	protected FileD file;
	protected List<FileD> userfile;

	public Abstract() {
		super();
	}

	public void executeCommand(String message, Map<String, Object> properties) throws JAXBException, SQLException {
		// implement in all commands
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}

	public FileDDao getFiledao() {
		return filedao;
	}

	public void setFiledao(FileDDao filedao) {
		this.filedao = filedao;
	}

	public UserFileDao getUserfiledao() {
		return userfiledao;
	}

	public void setUserfiledao(UserFileDao userfiledao) {
		this.userfiledao = userfiledao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FileD getFile() {
		return file;
	}

	public void setFile(FileD file) {
		this.file = file;
	}

	public List<FileD> getUserfile() {
		return userfile;
	}

	public void setUserfile(List<FileD> userfile) {
		this.userfile = userfile;
	}

}
