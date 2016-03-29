package com.zyl.centre.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.record.formula.functions.Request;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionSupport;
import com.zyl.centre.common.utils.CommonUtils;
import com.zyl.centre.common.utils.TimeString;
import com.zyl.centre.common.utils.TokenUtils;
import com.zyl.centre.entity.*;
import com.zyl.centre.service.*;

public class ShopAction extends ActionSupport {

	private Shop shop;
	private Imgsrc shop_img;
	private String types;
	private int prodctid;
	private int activeid;
	private int pagesize;
	private int number;
	private int rank = 1;
	private int ordstate = -1;
	private double lng;// 经度
	private double lat;// 维度
	private Area area;
	private String areaname;
	private String cityname;
	private String areaid;
	private String shopname;
	private String cityid;
	private String token;
	private String userid;
	private String serviceid;
	private String status;
	private String address;
	private String orderby;
	private List<File> files;
	private List<String> filesFileName;
	private List<String> filesContentType;
	private Log log = LogFactory.getLog(LoginAction.class);
	@Resource(name = "shopService")
	public IShopService shopService;

	@Resource(name = "userservice")
	private IUserService userservice;

	@Resource(name = "imgService")
	public IImgService imgService;

	@Resource(name = "areaService")
	public IAreaService areaService;

	@Resource(name = "serviceService")
	private IServiceService m_Service;

	@Resource(name = "tokenService")
	private ITokenService tokenService;

	@Resource(name = "orderService")
	private IOrderService orderservice;

	@Resource(name = "districtService")
	private IDistrictService districtService;

	@Resource(name = "imgactiveService")
	private IImgactiveService imgactiveService;

	@Resource(name = "activeshopService")
	private IActiveshopService activeshopService;

	@Resource(name = "serviceordrelService")
	private IServiceordrelService rel_Service;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<String> getFilesFileName() {
		return filesFileName;
	}

	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}

	public List<String> getFilesContentType() {
		return filesContentType;
	}

	public void setFilesContentType(List<String> filesContentType) {
		this.filesContentType = filesContentType;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Imgsrc getShop_img() {
		return shop_img;
	}

	public void setShop_img(Imgsrc shop_img) {
		this.shop_img = shop_img;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public int getOrdstate() {
		return ordstate;
	}

	public void setOrdstate(int ordstate) {
		this.ordstate = ordstate;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public int getProdctid() {
		return prodctid;
	}

	public void setProdctid(int prodctid) {
		this.prodctid = prodctid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getActiveid() {
		return activeid;
	}

	public void setActiveid(int activeid) {
		this.activeid = activeid;
	}

	@Override
	public String execute() throws Exception {

		return SUCCESS;
	}

	public void updateShopInfo() throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		// String root = "/usr/apache-tomcat-8.0.28/webapps/BeautyCentre";
		/*
		 * String rooturl = ServletActionContext.getRequest().getSession()
		 * .getServletContext().getRealPath("/");
		 */

		Shop dbshop = shopService.getByUid(userid);
		System.out.println("店铺信息：" + dbshop.getShopname());
		if (dbshop == null || status == null || token == null) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			return;
		}
		Map<String, Object> maptoken = TokenUtils.manageToken(token,
				tokenService);
		if (maptoken.get("message").equals("SUCCESS")) {
			if (status.equals("post")) {
				if (dbshop.getState() != 0) {
					reMap.put(CommonUtils.RESULTMESS, CommonUtils.EXISTSHOP);
					CommonUtils.toJson(ServletActionContext.getResponse(),
							reMap);
					return;
				}
				if (files == null || 0 >= files.size() || filesFileName == null
						|| 0 >= filesFileName.size()) {
					reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
					CommonUtils.toJson(ServletActionContext.getResponse(),
							reMap);
					return;
				}
				dbshop.setState(1);// 创建店铺
				dbshop.setScore(3.0);
				Calendar cal = Calendar.getInstance();// 获取当前时间
				Date currentDate = cal.getTime();
				dbshop.setCreatetime(currentDate);
			} else {
				if (dbshop.getState() == 1 || dbshop.getState() == 4) {
					dbshop.setState(2);// 修改shop信息
					Calendar cal = Calendar.getInstance();// 获取当前时间
					Date currentDate = cal.getTime();
					dbshop.setModifytime(currentDate);
				}
			}
			Area area_temp = areaService.GetByName(areaname, cityname);
			if (area_temp == null) {
				System.out.println("area_temp is NULLNULLNULL");
			}
			dbshop.setArea(area_temp);
			if (shop.getShopaddress() != null) {
				dbshop.setShopaddress(shop.getShopaddress());
				Map<String, Double> map = CommonUtils.getLngAndLat(cityname
						+ areaname + shop.getShopaddress());
				if (map != null) {
					dbshop.setLat(map.get("lat"));
					dbshop.setLng(map.get("lng"));
				}
			}
			if (shop.getShopname() != null) {
				dbshop.setShopname(shop.getShopname());
			}
			if (shop.getShopphone() != null) {
				dbshop.setShopphone(shop.getShopphone());
			}
			if (shop.getBusinesstime() != null) {
				dbshop.setBusinesstime(shop.getBusinesstime());
			}
			if (shop.getShopdec() != null) {
				dbshop.setShopdec(shop.getShopdec());
			}
			if (dbshop.getShoplevel() == null) {
				dbshop.setShoplevel(3);
			}
			if (dbshop.getPerconsum() == null) {
				dbshop.setPerconsum(200);
			}
			shopService.saveOrUpdateShop(dbshop, files, filesFileName);
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			return;
		} else {
			if (maptoken.get("message").equals("TOKENOUT")) {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.TOKENOUT);
			} else {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
			}
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
		}
	}

	public void getShopInfo() throws Exception {
		JSONObject rejson = new JSONObject();
		if (userid == null || token == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		Map<String, Object> maptoken = TokenUtils.manageToken(token,
				tokenService);
		if (maptoken.get("message").equals("SUCCESS")) {
			Shop shop_temp = shopService.getByUid(userid);
			if (shop_temp == null) {
				rejson.put("shopsize", 0);
			} else if (shop_temp.getState() == 0) {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				rejson.put("IfExist", "no");
			} else {
				List<Shop> shops = new ArrayList<Shop>();
				shops.add(shop_temp);
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				rejson.put("shop", CommonUtils.shopsToJson(shops, imgService,
						imgactiveService));
			}
		} else {
			if (maptoken.get("message").equals("TOKENOUT")) {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.TOKENOUT);
			} else {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
			}
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void test() throws IOException {
		/*
		 * HashSet<Imgsrc> img_set = new HashSet<Imgsrc>(); Imgsrc a=new
		 * Imgsrc(); a.setImgname("daa"); a.setUrl("ddd"); a.setImgdec("ada");
		 * Imgsrc b=new Imgsrc(); b.setImgname("daa"); b.setUrl("ddd");
		 * b.setImgdec("ada"); img_set.add(a); img_set.add(b); JSONArray jsArr =
		 * JSONArray.fromObject(img_set); Map<String, Object> reMap = new
		 * HashMap<String, Object>(); reMap.put("Shopimages", jsArr);
		 * CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
		 * Map<String, Object> reMap = new HashMap<String, Object>();
		 * reMap.put("cityname", areaService.GetCityNameByid(7));
		 * CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
		 */
		/*
		 * List<Order> orders = shopService.getOrdersByShopid(1); List<Service>
		 * ser = m_Service.GetServiceByOrdid(1); String json =
		 * JSONArray.fromObject(ser).toString();
		 * 
		 * Map<String, Object> reMap = new HashMap<String, Object>();
		 * System.out.println(serviceToJson(ser));
		 * reMap.put("shopname",serviceToJson(ser));
		 * CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
		 * List<Integer> prodtypeid =new ArrayList<Integer>();
		 * prodtypeid.add(1); prodtypeid.add(2); List<Service>
		 * ser=m_Service.getServByAreaType("�Ͼ�","��������", 1, prodtypeid);
		 * Map<String, Object> reMap = new HashMap<String, Object>();
		 * reMap.put("shopname",CommonUtils.serviceToJson(ser));
		 * CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
		 */
		/*
		 * Area a = areaService.GetByName("�����", "����"); Shop shop = new
		 * Shop(); User user=userservice.findAll().get(0); shop.setUser(user);
		 * shop.setShopname("wwww"); shop.setArea(a); shopService.create(shop);
		 * Map<String, Object> reMap = new HashMap<String, Object>();
		 * reMap.put("hotshops",
		 * CommonUtils.shopsToJson(shopService.findAll()));
		 * CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
		 * 
		 * User user = new User(); user.setUsername("sun");
		 * user.setPassword("111"); userservice.createShopUser(user);
		 * Map<String, Object> reMap = new HashMap<String, Object>();
		 * reMap.put("hotshops", user.getUsername() +
		 * user.getUserid().toString());
		 * CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
		 */
		/*
		 * m_Service.deleteService(2); Shop shop = shopService.getByShopid(3);
		 * List<District> dists = shopService.findDistricts(); List<Shop> shops
		 * = new ArrayList<Shop>(); shops.add(shop); JSONObject json = new
		 * JSONObject(); json.put(CommonUtils.RESULTMESS,
		 * CommonUtils.shopsToJson(shops)); json.put("cityinfo",
		 * CommonUtils.citysToJson(dists));
		 * CommonUtils.toJson(ServletActionContext.getResponse(), json);
		 */
		/*
		 * List<Integer> prodtypeid = new ArrayList<Integer>();
		 * prodtypeid.add(1); prodtypeid.add(2); int sumNumber =
		 * shopService.getShopsCountByAreaType("1", "2", "2", 1, prodtypeid);
		 * JSONObject json = new JSONObject(); json.put("总页数", sumNumber);
		 * CommonUtils.toJson(ServletActionContext.getResponse(), json);
		 */
		/*
		 * ServletActionContext.getResponse().setContentType(
		 * "text/json; charset=utf-8");
		 * ServletActionContext.getResponse().setHeader("Cache-Control",
		 * "no-cache"); // 取消浏览器缓存 PrintWriter out =
		 * ServletActionContext.getResponse().getWriter();
		 * out.println(ServletActionContext.getRequest().getSession()
		 * .getServletContext().getRealPath("/shop_upload")); out.flush();
		 * out.close();
		 */
		/*
		 * List<com.zyl.centre.entity.Service> servs = m_Service.findAll(); for
		 * (int i = 0; i < servs.size(); i++) { com.zyl.centre.entity.Service
		 * sercv = servs.get(i); Shop servshop = sercv.getShop();
		 * sercv.setShopname(servshop.getShopname()); m_Service.update(sercv); }
		 */
		/*
		 * List<Integer> prodtypeid = new ArrayList<Integer>();
		 * prodtypeid.add(11); prodtypeid.add(12); prodtypeid.add(13);
		 * prodtypeid.add(16); List<Shop> shops =
		 * shopService.getShopsByAreaType("2", null, null, 2, prodtypeid, 1, 5,
		 * "perconsum", 1); JSONObject json = new JSONObject();
		 * json.put("shopsinfo", CommonUtils.shopsToJson(shops, imgService,
		 * imgactiveService));
		 * CommonUtils.toJson(ServletActionContext.getResponse(), json);
		 */
		List<Shop> gshops = shopService.findAll();
		int n = gshops.size();
		for (int i = 0; i < n; i++) {
			List<com.zyl.centre.entity.Service> servs = m_Service
					.getServicesByShopid(gshops.get(i).getShopid());
			int perconsum = 0;
			if (servs != null && servs.size() > 0) {
				int m = servs.size();
				if (m > 0) {
					for (int j = 0; j < m; j++) {
						perconsum = perconsum + servs.get(j).getPrice();
					}
					perconsum = perconsum / m;
				}
			}
			gshops.get(i).setPerconsum(perconsum);
			shopService.update(gshops.get(i));
		}
	}

	public void getHotShopsInfo() throws IOException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		// String city = URLDecoder.decode(cityname, "UTF-8");
		// List<Shop> shops = shopService.getHotShopsByCity(city);
		List<Shop> shops = shopService.getHotShops(Integer.parseInt(cityid));
		if (shops.size() > 0) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			reMap.put("IfExist", "yes");
			reMap.put("hotshops", CommonUtils.webshopsToJson(shops, imgService,
					imgactiveService, m_Service));
		} else {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			reMap.put("IfExist", "no");
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
	}

	public void getShopInfoById() throws IOException {
		JSONObject rejson = new JSONObject();
		if (shop != null) {
			Shop gshop = shopService.getByShopid(shop.getShopid());
			List<Shop> shopsi = new ArrayList<Shop>();
			shopsi.add(gshop);
			rejson.put("ResultMessage", CommonUtils.SUCCESS);
			rejson.put("shop", CommonUtils.webshopsToJson(shopsi, imgService,
					imgactiveService, m_Service));
		} else {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);

	}

	public void getShopsInfoByAreaType() throws IOException {
		try {
			JSONObject rejson = new JSONObject();
			if (cityid == null) {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
				CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
				return;
			}
			if (pagesize == 0 || number == 0) {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
				CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
				return;
			}
			List<Integer> prodtypeid = new ArrayList<Integer>();
			if (types != null) {
				JSONArray ids = JSONArray.fromObject(types);
				for (int i = 0; i < ids.size(); i++) {
					JSONObject jsonObj = ids.getJSONObject(i);
					prodtypeid.add(jsonObj.getInt("typeid"));
				}
			}
			if (orderby == null || 0 >= orderby.length()) {
				orderby = "composite";
			}
			int sumNumber = shopService.getShopsCountByAreaType(cityid, areaid,
					shopname, prodctid, prodtypeid);
			if (0 >= sumNumber) {
				rejson.put("ResultMessage", CommonUtils.SUCCESS);
				rejson.put("IfExist", "no");
				CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
				return;
			}
			int totalpages = CommonUtils.getTotalPages(pagesize, sumNumber);
			if (number > totalpages) {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
				CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
				return;
			}
			List<Shop> shops = shopService.getShopsByAreaType(cityid, areaid,
					shopname, prodctid, prodtypeid, number, pagesize, orderby,
					rank);
			if (shops.size() > 0) {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				rejson.put("IfExist", "yes");
				rejson.put("totalpages", totalpages);
				rejson.put("shops", CommonUtils.webshopsToJson(shops,
						imgService, imgactiveService, m_Service));
			}
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getCityInfo() throws IOException {
		List<District> dists = districtService.findAll();
		JSONObject json = new JSONObject();
		json.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		json.put("cityinfo", CommonUtils.citysToJson(dists));
		CommonUtils.toJson(ServletActionContext.getResponse(), json);
	}

	public void getAreasByCity() throws IOException {
		JSONObject rejson = new JSONObject();
		if (cityid == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		List<Area> areas = areaService
				.getAreasByCityId(Integer.valueOf(cityid));
		if (areas.isEmpty()) {
			rejson.put("ResultMessage", CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
		} else {
			rejson.put("ResultMessage", CommonUtils.SUCCESS);
			rejson.put("IfExist", "yes");
			rejson.put("types", CommonUtils.areasToJson(areas));
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void webfindShopinfo() throws IOException {
		Shop gshop = shopService.getByShopid(shop.getShopid());
		JSONObject json = new JSONObject();
		if (gshop != null) {

			json.put("shopid", gshop.getShopid());
			json.put("shopname", gshop.getShopname());
			json.put("shopexist", "yes");

		} else {
			json.put("shopexist", "no");
		}
		ServletActionContext.getResponse().setContentType(
				"text/json; charset=utf-8");
		ServletActionContext.getResponse().setHeader("Cache-Control",
				"no-cache"); // 取消浏览器缓存
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();

	}

	public void getShopOrdInfo() throws IOException {
		JSONObject rejson = new JSONObject();
		if (token == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		Map<String, Object> maptoken = TokenUtils.manageToken(token,
				tokenService);
		if (maptoken.get("message").equals("SUCCESS")) {
			String userid = maptoken.get("userid").toString();
			Shop gshop = shopService.getByUid(userid);
			if (gshop == null) {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
				CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
				return;
			}
			List<Order> ordes = orderservice.getOrdInfoByOrdShopid(
					gshop.getShopid(), ordstate);
			int n = ordes.size();
			if (n > 0) {
				rejson.put("ResultMessage", CommonUtils.SUCCESS);
				rejson.put("IfExist", "yes");
				JSONArray jsonStrs = new JSONArray();
				for (int i = 0; i < n; i++) {
					JSONObject json = new JSONObject();
					json.put("shopname", gshop.getShopname());
					json.put("orderid", ordes.get(i).getOrderid());
					json.put("username", ordes.get(i).getSubmitusername());
					json.put("ordphone", ordes.get(i).getOrdphone());
					json.put("state", ordes.get(i).getState());
					jsonStrs.add(json);
				}
				rejson.put("ordinfo", jsonStrs);

			} else {
				// jsonStrs.add("{}");
				rejson.put("ResultMessage", CommonUtils.SUCCESS);
				rejson.put("IfExist", "no");
			}
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
		} else {
			if (maptoken.get("message").equals("TOKENOUT")) {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.TOKENOUT);
			} else {
				rejson.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
			}
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
		}
	}

	public void getShopsInfoByDistance() throws IOException {
		JSONObject rejson = new JSONObject();
		if (0 >= lng || 0 >= lat) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		List<Shop> dshops = shopService.getShopInfoByDistance(lng, lat);
		int n = dshops.size();
		if (n > 0) {
			rejson.put("ResultMessage", CommonUtils.SUCCESS);
			rejson.put("IfExist", "yes");
			JSONArray jsonStrs = new JSONArray();
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("shopid", dshops.get(i).getShopid());
				json.put("shopname", dshops.get(i).getShopname());
				json.put("createuserid", dshops.get(i).getUser().getUserid());
				json.put("shopphone", dshops.get(i).getShopphone());
				json.put("address", dshops.get(i).getShopaddress());
				json.put("perconsum", dshops.get(i).getPerconsum());
				json.put("businesstime", dshops.get(i).getBusinesstime());
				json.put("shoplevel", dshops.get(i).getShoplevel());
				json.put("discountmessage", dshops.get(i).getDiscountmessage());
				json.put("state", dshops.get(i).getState());
				json.put("createtime",
						TimeString.dateToString(dshops.get(i).getCreatetime()));
				json.put("shopdec", dshops.get(i).getShopdec());
				json.put("shopimages", CommonUtils.imgsToJson(imgService
						.getImagsByShopid(dshops.get(i).getShopid())));
				List<Imgactive> imgactives = imgactiveService
						.getImgactiveByShopid(dshops.get(i).getShopid());
				if (imgactives != null && imgactives.size() > 0) {
					json.put("actives",
							CommonUtils.activeimgsToJson(imgactives));
				}
				json.put("distance", CommonUtils.GetDistance(lng, lat, dshops
						.get(i).getLng(), dshops.get(i).getLat()));
				jsonStrs.add(json);
			}
			rejson.put("shopinfo", jsonStrs);
		} else {
			rejson.put("ResultMessage", CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void getActivesInfo() throws IOException {
		JSONObject rejson = new JSONObject();
		List<Imgactive> imgactives = imgactiveService.getNowActives();
		if (imgactives != null && imgactives.size() > 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("IfExist", "yes");
			JSONArray jsonStrs = new JSONArray();
			int n = imgactives.size();
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("imgactiveid", imgactives.get(i).getImgactiveid());
				json.put("activename", imgactives.get(i).getActivename());
				json.put("activeurl", imgactives.get(i).getActiveurl());
				json.put("createusername", imgactives.get(i)
						.getCreateusername());
				json.put("activedec", imgactives.get(i).getActivedec());
				json.put("activeimgname", imgactives.get(i).getActiveimgname());
				json.put("activeimgurl", imgactives.get(i).getActiveimgurl());
				/*
				 * List<Shop> shops = shopService
				 * .getShopsInfoByActiveID(imgactives.get(i) .getImgactiveid());
				 * json.put("activeshops", CommonUtils.shopsToJson(shops,
				 * imgService, imgactiveService));
				 */
				jsonStrs.add(json);
			}
			rejson.put("Activesinfo", jsonStrs);
		} else {
			rejson.put("ResultMessage", CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void getActiveInfoByID() throws IOException {
		Imgactive active = imgactiveService.getActiveInfoByID(activeid);
		JSONObject rejson = new JSONObject();
		if (active != null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("IfExist", "yes");
			JSONObject json = new JSONObject();
			json.put("imgactiveid", active.getImgactiveid());
			json.put("activename", active.getActivename());
			json.put("activeurl", active.getActiveurl());
			json.put("createusername", active.getCreateusername());
			json.put("activedec", active.getActivedec());
			json.put("activeimgname", active.getActiveimgname());
			json.put("activeimgurl", active.getActiveimgurl());
			List<Shop> shops = shopService.getShopsInfoByActiveID(active
					.getImgactiveid());
			json.put("activeshops", CommonUtils.shopsToJson(shops, imgService,
					imgactiveService));
			rejson.put("Activeinfo", json);
		} else {
			rejson.put("ResultMessage", CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}
}
