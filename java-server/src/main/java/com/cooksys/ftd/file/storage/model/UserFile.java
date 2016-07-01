package com.cooksys.ftd.file.storage.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserFile {
	private Integer userid;
	private Integer fileid;

	public UserFile() {
		super();
	}

	public UserFile(Integer userid, Integer fileid) {
		super();
		this.userid = userid;
		this.fileid = fileid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getFileid() {
		return fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid = fileid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileid == null) ? 0 : fileid.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
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
		UserFile other = (UserFile) obj;
		if (fileid == null) {
			if (other.fileid != null)
				return false;
		} else if (!fileid.equals(other.fileid))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserFile [userid=" + userid + ", fileid=" + fileid + "]";
	}
}
