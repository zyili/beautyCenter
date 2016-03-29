package com.zyl.centre.entity;

// Generated 2015-11-17 16:17:44 by Hibernate Tools 3.2.2.GA

public class ServiceordrelId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int orderid;
	private int serviceid;
	private int shopid;

	public ServiceordrelId() {
	}

	public ServiceordrelId(int orderid, int serviceid, int shopid) {
		this.orderid = orderid;
		this.serviceid = serviceid;
		this.shopid = shopid;
	}

	public int getOrderid() {
		return this.orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getServiceid() {
		return this.serviceid;
	}

	public void setServiceid(int serviceid) {
		this.serviceid = serviceid;
	}

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
		if (!(other instanceof ServiceordrelId))
			return false;
		ServiceordrelId castOther = (ServiceordrelId) other;

		return (this.getOrderid() == castOther.getOrderid())
				&& (this.getServiceid() == castOther.getServiceid())
				&& (this.getShopid() == castOther.getShopid());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getOrderid();
		result = 37 * result + this.getServiceid();
		result = 37 * result + this.getShopid();
		return result;
	}

}
