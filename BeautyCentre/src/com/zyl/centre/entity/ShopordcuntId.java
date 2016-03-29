package com.zyl.centre.entity;

// Generated 2015-11-17 16:17:44 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ShopordcuntId generated by hbm2java
 */
@Embeddable
public class ShopordcuntId implements java.io.Serializable {

	private int shopid;
	private long ordcount;
	private Date orddate;

	public ShopordcuntId() {
	}

	public ShopordcuntId(int shopid, long ordcount) {
		this.shopid = shopid;
		this.ordcount = ordcount;
	}

	public ShopordcuntId(int shopid, long ordcount, Date orddate) {
		this.shopid = shopid;
		this.ordcount = ordcount;
		this.orddate = orddate;
	}

	@Column(name = "shopid", nullable = false)
	public int getShopid() {
		return this.shopid;
	}

	public void setShopid(int shopid) {
		this.shopid = shopid;
	}

	@Column(name = "ordcount", nullable = false)
	public long getOrdcount() {
		return this.ordcount;
	}

	public void setOrdcount(long ordcount) {
		this.ordcount = ordcount;
	}

	@Column(name = "orddate", length = 0)
	public Date getOrddate() {
		return this.orddate;
	}

	public void setOrddate(Date orddate) {
		this.orddate = orddate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ShopordcuntId))
			return false;
		ShopordcuntId castOther = (ShopordcuntId) other;

		return (this.getShopid() == castOther.getShopid())
				&& (this.getOrdcount() == castOther.getOrdcount())
				&& ((this.getOrddate() == castOther.getOrddate()) || (this
						.getOrddate() != null && castOther.getOrddate() != null && this
						.getOrddate().equals(castOther.getOrddate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getShopid();
		result = 37 * result + (int) this.getOrdcount();
		result = 37 * result
				+ (getOrddate() == null ? 0 : this.getOrddate().hashCode());
		return result;
	}

}
