package com.zyl.centre.entity;

// Generated 2015-11-17 16:17:44 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Area generated by hbm2java
 */
@Entity
@Table(name = "area", catalog = "beautycentre")
public class Area implements java.io.Serializable {

	private Integer areaid;
	private District district;
	private String areaname;
	private Integer postcode;
	private String areadec;
	private Set<Shop> shops = new HashSet<Shop>(0);

	public Area() {
	}

	public Area(District district) {
		this.district = district;
	}

	public Area(District district, String areaname, Integer postcode,
			String areadec, Set<Shop> shops) {
		this.district = district;
		this.areaname = areaname;
		this.postcode = postcode;
		this.areadec = areadec;
		this.shops = shops;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "areaid", unique = true, nullable = false)
	public Integer getAreaid() {
		return this.areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "districtid", nullable = false)
	public District getDistrict() {
		return this.district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@Column(name = "areaname", length = 20)
	public String getAreaname() {
		return this.areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	@Column(name = "postcode")
	public Integer getPostcode() {
		return this.postcode;
	}

	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}

	@Column(name = "areadec", length = 65535)
	public String getAreadec() {
		return this.areadec;
	}

	public void setAreadec(String areadec) {
		this.areadec = areadec;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<Shop> getShops() {
		return this.shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

}
