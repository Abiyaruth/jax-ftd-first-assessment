package com.cooksys.ftd.file.storage.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileD {
	public static final String PRIMARY_KEY = null;
	public static final String FILE_COLUMN = null;
	private Integer fileid;
	private String filepath;
	private byte[] filedata;
	private Long pk;

	public FileD() {
		super();
	}

	public FileD(Integer fileid, String filepath, byte[] filedata) {
		super();
		this.fileid = fileid;
		this.filepath = filepath;
		this.filedata = filedata;
	}

	public Integer getFileid() {
		return fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid = fileid;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public static String getPrimaryKey() {
		return PRIMARY_KEY;
	}

	public static String getFileColumn() {
		return FILE_COLUMN;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filedata == null) ? 0 : filedata.hashCode());
		result = prime * result + ((fileid == null) ? 0 : fileid.hashCode());
		result = prime * result + ((filepath == null) ? 0 : filepath.hashCode());
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
		if (filedata == null) {
			if (other.filedata != null)
				return false;
		} else if (!filedata.equals(other.filedata))
			return false;
		if (fileid == null) {
			if (other.fileid != null)
				return false;
		} else if (!fileid.equals(other.fileid))
			return false;
		if (filepath == null) {
			if (other.filepath != null)
				return false;
		} else if (!filepath.equals(other.filepath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "File [fileid=" + fileid + ", filepath=" + filepath + ", filedata=" + filedata + "]";
	}

	public void setPrimaryKey(Long pk) {
		this.pk = pk;

	}
}
