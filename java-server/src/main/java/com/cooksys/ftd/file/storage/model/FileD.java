package com.cooksys.ftd.file.storage.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Files")
public class FileD {
	@XmlElement(name = "filePath")
	private String filePath;
	@XmlElement(name = "username")
	private String username;
	@XmlElement(name = "buffer")
	private String buffer;

	public FileD() {
		super();
	}

	public FileD(String filePath, String username, String buffer) {
		super();
		this.filePath = filePath;
		this.username = username;
		this.buffer = buffer;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buffer == null) ? 0 : buffer.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileD other = (FileD) obj;
		if (buffer == null) {
			if (other.buffer != null)
				return false;
		} else if (!buffer.equals(other.buffer))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FileD [absolutePath=" + filePath + ", username=" + username + ", buffer=" + buffer + "]";
	}

	public String getAbsolutePath() {
		return filePath;
	}

	public String getByteArray() {
		return buffer;
	}

	public void setAbsolutePath(String absolutePath) {
		this.filePath=absolutePath;
		
	}

	public void setByteArray(String byteArray) {
		this.buffer=byteArray;
		
	}

}
