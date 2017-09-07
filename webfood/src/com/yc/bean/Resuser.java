package com.yc.bean;


/**
 *  有多少个表，建多少个类《bean》-》 实体类
 */
import java.io.Serializable;

public class Resuser implements Serializable {

	
	private static final long serialVersionUID = 1676684284807690359L;
	
	
	private Integer userid ;
	private String username ;
	private String pwd ;
	private String email;
	
	private String valcode;
	
	
	
	public String getValcode() {
		return valcode;
	}

	public void setValcode(String valcode) {
		this.valcode = valcode;
	}

	@Override
	public String toString() {
		return "Resuser [userid=" + userid + ", username=" + username + ", pwd=" + pwd + ", email=" + email + "]";
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
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
		Resuser other = (Resuser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	//构造方法   （）
	public Resuser(Integer userid, String username, String pwd, String email) {
		super();
		this.userid = userid;
		this.username = username;
		this.pwd = pwd;
		this.email = email;
	}
	//为后面的反射做准备
	public Resuser() {
		super();
	}

	
	
	
	
	
	

}
