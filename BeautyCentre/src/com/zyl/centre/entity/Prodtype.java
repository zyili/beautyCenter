package com.zyl.centre.entity;

// Generated 2015-11-17 16:17:44 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Prodtype generated by hbm2java
 */
@Entity
@Table(name = "prodtype", catalog = "beautycentre")
public class Prodtype implements java.io.Serializable {

	private Integer prodtypeid;
	private Product product;
	private Date createtime;
	private String prodtypename;
	private String prodtypedec;
	private String ext1;
	private String ext2;
	private String createusername;
	private Set<Prodtyperel> prodtyperels = new HashSet<Prodtyperel>(0);

	public Prodtype() {
	}

	public Prodtype(Product product, String prodtypename, String prodtypedec,
			String ext1, String ext2, String createusername,
			Set<Prodtyperel> prodtyperels) {
		this.product = product;
		this.prodtypename = prodtypename;
		this.prodtypedec = prodtypedec;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.createusername = createusername;
		this.prodtyperels = prodtyperels;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "prodtypeid", unique = true, nullable = false)
	public Integer getProdtypeid() {
		return this.prodtypeid;
	}

	public void setProdtypeid(Integer prodtypeid) {
		this.prodtypeid = prodtypeid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productid")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "prodtypename", length = 32)
	public String getProdtypename() {
		return this.prodtypename;
	}

	public void setProdtypename(String prodtypename) {
		this.prodtypename = prodtypename;
	}

	@Column(name = "prodtypedec", length = 65535)
	public String getProdtypedec() {
		return this.prodtypedec;
	}

	public void setProdtypedec(String prodtypedec) {
		this.prodtypedec = prodtypedec;
	}

	@Column(name = "ext1", length = 10)
	public String getExt1() {
		return this.ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	@Column(name = "ext2", length = 20)
	public String getExt2() {
		return this.ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	@Column(name = "createusername", length = 30)
	public String getCreateusername() {
		return this.createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prodtype")
	public Set<Prodtyperel> getProdtyperels() {
		return this.prodtyperels;
	}

	public void setProdtyperels(Set<Prodtyperel> prodtyperels) {
		this.prodtyperels = prodtyperels;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", length = 0)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
