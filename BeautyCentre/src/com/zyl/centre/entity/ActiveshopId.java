package com.zyl.centre.entity;

// Generated 2015-12-13 14:43:40 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ActiveshopId generated by hbm2java
 */
@Embeddable
public class ActiveshopId implements java.io.Serializable {

	private int imgactiveid;
	private int shopid;

	public ActiveshopId() {
	}

	public ActiveshopId(int imgactiveid, int shopid) {
		this.imgactiveid = imgactiveid;
		this.shopid = shopid;
	}

	@Column(name = "imgactiveid", nullable = false)
	public int getImgactiveid() {
		return this.imgactiveid;
	}

	public void setImgactiveid(int imgactiveid) {
		this.imgactiveid = imgactiveid;
	}

	@Column(name = "shopid", nullable = false)
	public int getShopid() {
		return this.shopid;
	}

	public void setShopid(int shopid) {
		this.shopid = shopid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ActiveshopId))
			return false;
		ActiveshopId castOther = (ActiveshopId) other;

		return (this.getImgactiveid() == castOther.getImgactiveid())
				&& (this.getShopid() == castOther.getShopid());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getImgactiveid();
		result = 37 * result + this.getShopid();
		return result;
	}

}
