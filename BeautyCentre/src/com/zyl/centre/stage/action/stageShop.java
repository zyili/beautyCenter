package com.zyl.centre.stage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zyl.centre.entity.District;
import com.zyl.centre.entity.Imgsrc;
import com.zyl.centre.entity.Prodtype;
import com.zyl.centre.entity.Product;
import com.zyl.centre.entity.Service;
import com.zyl.centre.entity.Shop;
import com.zyl.centre.entity.User;
import com.zyl.centre.service.IDistrictService;
import com.zyl.centre.service.IImgService;
import com.zyl.centre.service.IProdtypeService;
import com.zyl.centre.service.IProductService;
import com.zyl.centre.service.IServiceService;
import com.zyl.centre.service.IShopService;

public class stageShop extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int status;
	public int shopid;
	public int checkfalge;
	public Shop webshop;
	public List<Shop> shops = new ArrayList<Shop>();

	private int pageSize = 10;// 每页显示几条

	private int spage = 1; // 默认当前页

	private int totalPage;// 总共多少页

	private int num;// 总过多少条

	private int ptpage = 1;// 当前页数

	private int snckpage = 1;

	private int svpage = 1;

	private String typename;

	private String typedec;

	private int cityid = 1;

	private int prdid = 1;

	private int oldshopid;

	private int newshopid;

	public String nameorid;

	public String svnamorid;

	private int servid;

	public int getServid() {
		return servid;
	}

	public void setServid(int servid) {
		this.servid = servid;
	}

	public String getSvnamorid() {
		return svnamorid;
	}

	public void setSvnamorid(String svnamorid) {
		this.svnamorid = svnamorid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getTypedec() {
		return typedec;
	}

	public void setTypedec(String typedec) {
		this.typedec = typedec;
	}

	public int getPtpage() {
		return ptpage;
	}

	public void setPtpage(int ptpage) {
		this.ptpage = ptpage;
	}

	private int prodtypeid;

	public int getProdtypeid() {
		return prodtypeid;
	}

	public void setProdtypeid(int prodtypeid) {
		this.prodtypeid = prodtypeid;
	}

	public String getNameorid() {
		return nameorid;
	}

	public void setNameorid(String nameorid) {
		this.nameorid = nameorid;
	}

	public int getSvpage() {
		return svpage;
	}

	public void setSvpage(int svpage) {
		this.svpage = svpage;
	}

	public Shop getWebshop() {
		return webshop;
	}

	public void setWebshop(Shop webshop) {
		this.webshop = webshop;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	@Resource(name = "shopService")
	public IShopService shopService;

	@Resource(name = "prodtypeService")
	private IProdtypeService m_ProdtypeService;

	@Resource(name = "productService")
	private IProductService m_Product;

	@Resource(name = "imgService")
	private IImgService imgService;

	@Resource(name = "serviceService")
	private IServiceService m_Service;

	@Resource(name = "districtService")
	private IDistrictService districtService;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSpage() {
		return spage;
	}

	public void setSpage(int spage) {
		this.spage = spage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getShopid() {
		return shopid;
	}

	public void setShopid(int shopid) {
		this.shopid = shopid;
	}

	public int getCheckfalge() {
		return checkfalge;
	}

	public void setCheckfalge(int checkfalge) {
		this.checkfalge = checkfalge;
	}

	public int getPrdid() {
		return prdid;
	}

	public void setPrdid(int prdid) {
		this.prdid = prdid;
	}

	public int getSnckpage() {
		return snckpage;
	}

	public void setSnckpage(int snckpage) {
		this.snckpage = snckpage;
	}

	public int getOldshopid() {
		return oldshopid;
	}

	public void setOldshopid(int oldshopid) {
		this.oldshopid = oldshopid;
	}

	public int getNewshopid() {
		return newshopid;
	}

	public void setNewshopid(int newshopid) {
		this.newshopid = newshopid;
	}

	/**
	 * 查询未审核的店铺信息
	 * 
	 * @return
	 */
	public String findNoCheckShop() {
		List<Integer> st = new ArrayList<Integer>();
		if (status == 1) {
			st.add(1);

		} else if (status == 2) {
			st.add(2);
		} else {
			st.add(1);
			st.add(2);
		}
		setNum(shopService.findShopByState(st));

		if (num % pageSize == 0) {
			totalPage = num / pageSize;// 总共多少页
		} else {
			totalPage = num / pageSize + 1;// 总共多少页
		}
		System.out.println("findNoCheckShop---totalPage:" + totalPage
				+ "snckpage:" + snckpage);
		setShops(shopService.findShopByState(st, snckpage, pageSize));
		ServletActionContext.getRequest().getSession()
				.setAttribute("checkshops", shops);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("snckpage", snckpage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("status", status);
		return SUCCESS;
	}

	/**
	 * 查询已审核的店铺信息
	 * 
	 * @return
	 */
	public String findCheckedShop() {
		List<Integer> st = new ArrayList<Integer>();
		/*
		 * System.out.println(ServletActionContext.getRequest().getSession()
		 * .getAttribute("status")); if
		 * (ServletActionContext.getRequest().getSession()
		 * .getAttribute("status") != null) { status =
		 * Integer.parseInt(ServletActionContext.getRequest()
		 * .getSession().getAttribute("status").toString()); }
		 */
		System.out.println("status--------------:" + status);
		if (status == 3) {
			st.add(3);
		} else if (status == 4) {
			st.add(4);
		} else if (status == 5) {
			st.add(5);
		} else {
			st.add(3);
			st.add(4);
			st.add(5);
		}
		System.out.println("状态选择-----------" + st.toString());
		setNum(shopService.findShopByState(st));
		setShops(shopService.findShopByState(st, spage, pageSize));
		// setNum(shops.size());
		if (num % pageSize == 0) {
			totalPage = num / pageSize;// 总共多少页
		} else {
			totalPage = num / pageSize + 1;// 总共多少页
		}
		System.out.println("totalPage:" + totalPage + "spage:" + spage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("checkshops", shops);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("spage", spage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("status", status);
		return SUCCESS;
	}

	public String findProdType() {
		List<Prodtype> prdts = m_ProdtypeService.findTypesByProdName(ptpage,
				pageSize, prdid);
		setNum(m_ProdtypeService.typeCount(prdid));
		List<Product> prds = m_Product.findAll();
		if (num % pageSize == 0) {
			totalPage = num / pageSize;// 总共多少页
		} else {
			totalPage = num / pageSize + 1;// 总共多少页
		}
		ServletActionContext.getRequest().getSession()
				.setAttribute("prdts", prdts);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("ptpage", ptpage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("prdid", prdid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("prds", prds);
		return SUCCESS;
	}

	public String findShopInfo() {
		Shop shop = shopService.getByShopid(shopid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("shopinfo", shop);
		List<Imgsrc> imgs = imgService.getImagsByShopid(shop.getShopid());
		ServletActionContext.getRequest().getSession()
				.setAttribute("imgs", imgs);
		return SUCCESS;
	}

	public String checkShopInfo() {
		Shop shop = shopService.getByShopid(shopid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("shopinfo", shop);
		List<Imgsrc> imgs = imgService.getImagsByShopid(shop.getShopid());
		ServletActionContext.getRequest().getSession()
				.setAttribute("imgs", imgs);
		return SUCCESS;
	}

	public String closeShop() {
		Shop shop = shopService.getByShopid(shopid);
		shop.setState(5);
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		shop.setCheckusername(user.getUsername());
		shopService.update(shop);
		int sumcount = shopService.getNormalShopsNum();
		int shoppage = (int) ServletActionContext.getRequest().getSession()
				.getAttribute("shoppage");
		System.out.println("商店当前页：" + shoppage);
		if (sumcount % pageSize == 0) {
			totalPage = sumcount / pageSize;// 总共多少页
		} else {
			totalPage = sumcount / pageSize + 1;// 总共多少页
		}
		List<Shop> shopinfo = shopService.listShopInfo(shoppage, pageSize);
		ServletActionContext.getRequest().getSession()
				.setAttribute("shopsinfo", shopinfo);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("shoppage", shoppage);
		return SUCCESS;
	}

	public String recoveryShop() {
		Shop rshop = shopService.getByShopid(shopid);
		rshop.setState(2);
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		rshop.setCheckusername(user.getUsername());
		shopService.update(rshop);
		int sumcount = shopService.getNormalShopsNum();
		int shoppage = (int) ServletActionContext.getRequest().getSession()
				.getAttribute("shoppage");
		if (sumcount % pageSize == 0) {
			totalPage = sumcount / pageSize;// 总共多少页
		} else {
			totalPage = sumcount / pageSize + 1;// 总共多少页
		}
		List<Shop> shopinfo = shopService.listShopInfo(shoppage, pageSize);
		ServletActionContext.getRequest().getSession()
				.setAttribute("shopsinfo", shopinfo);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("shoppage", shoppage);
		return SUCCESS;
	}

	public String queryShop() {
		List<Shop> shops = shopService.findShopByNameOrId(nameorid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("shopsinfo", shops);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", 1);
		ServletActionContext.getRequest().getSession()
				.setAttribute("shoppage", 1);
		return SUCCESS;
	}

	public String checkShop() {
		Shop shop = shopService.getByShopid(shopid);
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		if (checkfalge == 1) {
			shop.setState(3);
			shop.setCheckusername(user.getUsername());
		} else {
			shop.setState(4);
			shop.setCheckusername(user.getUsername());
		}
		shopService.update(shop);
		List<Integer> st = new ArrayList<Integer>();
		st.add(1);
		st.add(2);
		setShops(shopService.findShopByState(st, spage, pageSize));
		setNum(shops.size());
		if (num % pageSize == 0) {
			totalPage = num / pageSize;// 总共多少页
		} else {
			totalPage = num / pageSize + 1;// 总共多少页
		}
		ServletActionContext.getRequest().getSession()
				.setAttribute("checkshops", shops);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("page", spage);
		return SUCCESS;
	}

	public String modifyProdType() {
		Prodtype type = m_ProdtypeService.findTypeByTypeid(prodtypeid);
		return SUCCESS;
	}

	public String deleteProdType() {
		prdid = (int) ServletActionContext.getRequest().getSession()
				.getAttribute("prdid");
		System.out.println("----------------------------prdid:" + prdid);
		Prodtype type = new Prodtype();
		type.setProdtypeid(prodtypeid);
		m_ProdtypeService.delete(type);
		List<Prodtype> prdts = m_ProdtypeService.findTypesByProdName(1,
				pageSize, prdid);
		ptpage = 1;
		setNum(m_ProdtypeService.typeCount(prdid));
		if (num % pageSize == 0) {
			totalPage = num / pageSize;// 总共多少页
		} else {
			totalPage = num / pageSize + 1;// 总共多少页
		}
		ServletActionContext.getRequest().getSession()
				.setAttribute("prdts", prdts);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("ptpage", ptpage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("prdid", prdid);
		return SUCCESS;
	}

	public String addProdType() {
		if (typename == null) {
			return ERROR;
		}
		try {
			if (m_ProdtypeService.checkTypeName(typename, prdid)) {

				ServletActionContext.getResponse().setContentType(
						"text/html;charset=UTF-8");
				ServletActionContext.getResponse()
						.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
				PrintWriter out = ServletActionContext.getResponse()
						.getWriter();
				out.print("<script>alert('标签名重复！')</script>");
				out.flush();
				return ERROR;
			}
			Prodtype type = new Prodtype();
			Product prod = m_Product.findOneById(prdid);
			Calendar cal = Calendar.getInstance();// 获取当前时间
			Date currentDate = cal.getTime();
			type.setCreatetime(currentDate);
			type.setProdtypedec(typedec);
			type.setProdtypename(typename);
			type.setProduct(prod);
			User user = (User) ServletActionContext.getRequest().getSession()
					.getAttribute("user");
			type.setCreateusername(user.getUsername());
			m_ProdtypeService.create(type);
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=UTF-8");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print("<script>alert('添加成功！')</script>");
			out.flush();
			return ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR;
		}
	}

	public String editHotShops() {
		List<Shop> shops = shopService.getHotShops(cityid);
		int hotshopCount = 0;
		if (shops != null && shops.size() > 0) {
			hotshopCount = shops.size();
		}
		List<District> citys = districtService.findAll();
		ServletActionContext.getRequest().getSession()
				.setAttribute("hotshops", shops);
		ServletActionContext.getRequest().getSession()
				.setAttribute("citys", citys);
		ServletActionContext.getRequest().getSession()
				.setAttribute("cityid", cityid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("hotshopCount", hotshopCount);
		return SUCCESS;
	}

	public String changehotShop() {
		Shop shop = shopService.getByShopid(shopid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("changehot", shop);
		return SUCCESS;
	}

	public String serviceInfo() throws IOException {
		if (svnamorid == null || svnamorid.equals("-1")) {
			svnamorid = null;// 表示不是查询操作
			ServletActionContext.getRequest().getSession()
					.setAttribute("querykey", "  ");
		} else {
			ServletActionContext.getRequest().getSession()
					.setAttribute("querykey", svnamorid);
		}
		ServletActionContext.getRequest().getSession()
				.setAttribute("querykey", svnamorid);
		int sumcount = m_Service.getServsCountByShop(svnamorid);
		List<Service> servs = m_Service.getServsByShop(svnamorid, svpage,
				pageSize);
		if (sumcount % pageSize == 0) {
			totalPage = sumcount / pageSize;// 总共多少页
		} else {
			totalPage = sumcount / pageSize + 1;// 总共多少页
		}
		if (svnamorid == null || 0 >= svnamorid.length()) {
			svnamorid = "-1";// 不是查询操作设置查询字段为特殊字符方便前端显示
		}
		ServletActionContext.getRequest().getSession()
				.setAttribute("servs", servs);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("svpage", svpage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("svnamorid", svnamorid);
		return SUCCESS;
	}

	public String recoveryService() {
		Service serv = m_Service.getServiceByid(servid);
		serv.setState(1);
		m_Service.update(serv);
		svpage = (int) ServletActionContext.getRequest().getSession()
				.getAttribute("svpage");
		svnamorid = ServletActionContext.getRequest().getSession()
				.getAttribute("svnamorid").toString();
		if (svnamorid == null || svnamorid.equals("-1")) {
			svnamorid = null;// 表示不是查询操作
		}
		int sumcount = m_Service.getServsCountByShop(svnamorid);
		List<Service> servs = m_Service.getServsByShop(svnamorid, svpage,
				pageSize);
		if (sumcount % pageSize == 0) {
			totalPage = sumcount / pageSize;// 总共多少页
		} else {
			totalPage = sumcount / pageSize + 1;// 总共多少页
		}
		if (svnamorid == null || 0 >= svnamorid.length()) {
			svnamorid = "-1";// 不是查询操作设置查询字段为特殊字符方便前端显示
		}
		ServletActionContext.getRequest().getSession()
				.setAttribute("servs", servs);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("svpage", svpage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("svnamorid", svnamorid);
		return SUCCESS;
	}

	public String closeService() {
		Service serv = m_Service.getServiceByid(servid);
		serv.setState(0);
		m_Service.update(serv);
		svpage = (int) ServletActionContext.getRequest().getSession()
				.getAttribute("svpage");
		svnamorid = ServletActionContext.getRequest().getSession()
				.getAttribute("svnamorid").toString();
		if (svnamorid == null || svnamorid.equals("-1")) {
			svnamorid = null;// 表示不是查询操作
		}
		int sumcount = m_Service.getServsCountByShop(svnamorid);
		List<Service> servs = m_Service.getServsByShop(svnamorid, svpage,
				pageSize);
		if (sumcount % pageSize == 0) {
			totalPage = sumcount / pageSize;// 总共多少页
		} else {
			totalPage = sumcount / pageSize + 1;// 总共多少页
		}
		if (svnamorid == null || 0 >= svnamorid.length()) {
			svnamorid = "-1";// 不是查询操作设置查询字段为特殊字符方便前端显示
		}
		ServletActionContext.getRequest().getSession()
				.setAttribute("servs", servs);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("svpage", svpage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("svnamorid", svnamorid);
		return SUCCESS;
	}

	public String findServInfo() {
		Service serv = m_Service.getServiceByid(servid);
		Product prod = serv.getProduct();
		prod.getProductid();
		prod.getProductname();// 激活懒加载
		Shop sop = serv.getShop();
		sop.getShopid();
		sop.getShopname();
		List<Imgsrc> simg = imgService.getImagsByServiceid(serv.getServiceid());
		ServletActionContext.getRequest().getSession()
				.setAttribute("service", serv);
		ServletActionContext.getRequest().getSession()
				.setAttribute("prod", prod);
		ServletActionContext.getRequest().getSession().setAttribute("sop", sop);
		ServletActionContext.getRequest().getSession()
				.setAttribute("simgs", simg);
		return SUCCESS;
	}

	public String saveWebShopInfo() {
		Shop dbshop = shopService.getByShopid(webshop.getShopid());
		if (webshop.getShopname() != null
				&& webshop.getShopname().trim().length() > 0) {
			dbshop.setShopname(webshop.getShopname());
		}
		dbshop.setShopphone(webshop.getShopphone());
		dbshop.setShopaddress(webshop.getShopaddress());
		dbshop.setBusinesstime(webshop.getBusinesstime());
		dbshop.setShoplevel(webshop.getShoplevel());
		dbshop.setShopdec(webshop.getShopdec());
		shopService.update(dbshop);
		if (webshop.getShopname() != null
				&& webshop.getShopname().trim().length() > 0) {
			shopService.updateShopName(webshop.getShopname(),
					dbshop.getShopid());
		}
		return SUCCESS;
	}

	public String modifyHotShop() throws IOException {
		int districtid = Integer.parseInt(ServletActionContext.getRequest()
				.getSession().getAttribute("cityid").toString());
		Shop newshop = shopService.getByShopidCityid(newshopid, districtid);
		if (newshop == null) {
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=UTF-8");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print("<script>alert('不存该ID的店铺！')</script>");
			out.flush();
			return ERROR;
		} else {
			if (newshop.getIshotshop() == 1) {
				ServletActionContext.getResponse().setContentType(
						"text/html;charset=UTF-8");
				ServletActionContext.getResponse()
						.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
				PrintWriter out = ServletActionContext.getResponse()
						.getWriter();
				out.print("<script>alert('该ID的店铺已是热门店铺！')</script>");
				out.flush();
				return ERROR;
			} else {
				Shop oldshop = shopService.getByShopid(oldshopid);
				oldshop.setIshotshop(0);
				shopService.update(oldshop);
				newshop.setIshotshop(1);
				shopService.update(newshop);
				System.out.println("districtid::::::::::" + districtid);
				List<Shop> shops = shopService.getHotShops(districtid);
				List<District> citys = districtService.findAll();
				ServletActionContext.getRequest().getSession()
						.setAttribute("hotshops", shops);
				ServletActionContext.getRequest().getSession()
						.setAttribute("citys", citys);
				ServletActionContext.getRequest().getSession()
						.setAttribute("cityid", districtid);
				return SUCCESS;
			}
		}
	}

	public String deleteHotShop() {
		Shop dshop = shopService.getByShopid(shopid);
		if (dshop != null) {
			dshop.setIshotshop(0);
		}
		shopService.update(dshop);
		int dcityid = (int) ServletActionContext.getRequest().getSession()
				.getAttribute("cityid");
		System.out.println("城市ID:" + dcityid);
		int hotshopCount = 0;
		List<Shop> shops = shopService.getHotShops(dcityid);
		if (shops != null && shops.size() > 0) {
			hotshopCount = shops.size();
		}
		List<District> citys = districtService.findAll();
		ServletActionContext.getRequest().getSession()
				.setAttribute("hotshops", shops);
		ServletActionContext.getRequest().getSession()
				.setAttribute("citys", citys);
		ServletActionContext.getRequest().getSession()
				.setAttribute("cityid", dcityid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("hotshopCount", hotshopCount);
		return SUCCESS;
	}

	public String addHotShop() throws IOException {
		System.out.println("城市ID:" + cityid);
		System.out.println("店铺ID:" + shopid);
		Shop dshop = shopService.getByShopid(shopid);
		if (dshop == null) {
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=UTF-8");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print("<script>alert('不存在该ID的店铺！')</script>");
			out.flush();
			return ERROR;
		}
		if (dshop.getIshotshop() == 1) {
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=UTF-8");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print("<script>alert('该ID的店铺已是热门店铺！')</script>");
			out.flush();
			return ERROR;
		}
		Shop newhotshop = shopService.getByShopidCityid(shopid, cityid);
		if (newhotshop != null) {
			newhotshop.setIshotshop(1);
			shopService.update(newhotshop);
			int hotshopCount = 0;
			List<Shop> shops = shopService.getHotShops(cityid);
			if (shops != null && shops.size() > 0) {
				hotshopCount = shops.size();
			}
			List<District> citys = districtService.findAll();
			ServletActionContext.getRequest().getSession()
					.setAttribute("hotshops", shops);
			ServletActionContext.getRequest().getSession()
					.setAttribute("citys", citys);
			ServletActionContext.getRequest().getSession()
					.setAttribute("cityid", cityid);
			ServletActionContext.getRequest().getSession()
					.setAttribute("hotshopCount", hotshopCount);
			return SUCCESS;
		} else {
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=UTF-8");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print("<script>alert('不是本城市的店铺！')</script>");
			out.flush();
			return ERROR;
		}

	}
}
