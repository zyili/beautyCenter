package com.zyl.centre.entity;

// Generated 2015-11-17 16:17:44 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Token generated by hbm2java
 */
@Entity
@Table(name = "token", catalog = "beautycentre")
public class Token implements java.io.Serializable {

	private int userid;
	private User user;
	private String tokencode;
	private Date createdatetime;
	private Date expiredatetime;

	public Token() {
	}

	public Token(int userid, User user) {
		this.userid = userid;
		this.user = user;
	}

	public Token(int userid, User user, String tokencode, Date createdatetime,
			Date expiredatetime) {
		this.userid = userid;
		this.user = user;
		this.tokencode = tokencode;
		this.createdatetime = createdatetime;
		this.expiredatetime = expiredatetime;
	}

	@Id
	@Column(name = "userid", unique = true, nullable = false)
	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", unique = true, nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "tokencode", length = 35)
	public String getTokencode() {
		return this.tokencode;
	}

	public void setTokencode(String tokencode) {
		this.tokencode = tokencode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdatetime", length = 0)
	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiredatetime", length = 0)
	public Date getExpiredatetime() {
		return this.expiredatetime;
	}

	public void setExpiredatetime(Date expiredatetime) {
		this.expiredatetime = expiredatetime;
	}

}