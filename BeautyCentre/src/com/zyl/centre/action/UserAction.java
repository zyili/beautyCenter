package com.zyl.centre.action;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.zyl.centre.common.utils.CommonUtils;
import com.zyl.centre.common.utils.TimeString;
import com.zyl.centre.common.utils.TokenUtils;
import com.zyl.centre.entity.Complaint;
import com.zyl.centre.entity.Imgadver;
import com.zyl.centre.entity.Imgsrc;
import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.Prodtype;
import com.zyl.centre.entity.Prodtyperel;
import com.zyl.centre.entity.Product;
import com.zyl.centre.entity.Review;
import com.zyl.centre.entity.Service;
import com.zyl.centre.entity.Serviceordrel;
import com.zyl.centre.entity.ServiceordrelId;
import com.zyl.centre.entity.Shop;
import com.zyl.centre.entity.User;
import com.zyl.centre.service.IComplaintService;
import com.zyl.centre.service.IImgService;
import com.zyl.centre.service.IImgadverService;
import com.zyl.centre.service.IOrderService;
import com.zyl.centre.service.IReviewService;
import com.zyl.centre.service.IServiceService;
import com.zyl.centre.service.IServiceordrelService;
import com.zyl.centre.service.IShopService;
import com.zyl.centre.service.ITokenService;
import com.zyl.centre.service.IUserService;

public class UserAction extends ActionSupport {

	private String token;
	private User user;
	private Service service;
	private String brithday;
	private int shopid;
	private int number;
	private int cityid;
	private int imgadid;
	private int pagesize = 5;
	private int page = 1;
	private String ordphone;
	private int ordstate = -1;
	private int ordid;
	private File uimg;// 用户头像
	private Review review;
	private Complaint comp;
	private String uimgFileName;

	public File getUimg() {
		return uimg;
	}

	public void setUimg(File uimg) {
		this.uimg = uimg;
	}

	public String getUimgFileName() {
		return uimgFileName;
	}

	public void setUimgFileName(String uimgFileName) {
		this.uimgFileName = uimgFileName;
	}

	@Resource(name = "userservice")
	private IUserService userservice;

	@Resource(name = "tokenService")
	private ITokenService tokenService;

	@Resource(name = "orderService")
	private IOrderService orderservice;

	@Resource(name = "serviceService")
	private IServiceService m_Service;

	@Resource(name = "serviceordrelService")
	private IServiceordrelService rel_Service;

	@Resource(name = "imgadverService")
	private IImgadverService imgadverService;

	@Resource(name = "shopService")
	public IShopService shopService;

	@Resource(name = "imgService")
	public IImgService imgService;

	@Resource(name = "reviewService")
	private IReviewService reviewService;

	@Resource(name = "complaintService")
	private IComplaintService complaintService;

	private Log log = LogFactory.getLog(LoginAction.class);

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getOrdstate() {
		return ordstate;
	}

	public void setOrdstate(int ordstate) {
		this.ordstate = ordstate;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public int getImgadid() {
		return imgadid;
	}

	public void setImgadid(int imgadid) {
		this.imgadid = imgadid;
	}

	public String getOrdphone() {
		return ordphone;
	}

	public void setOrdphone(String ordphone) {
		this.ordphone = ordphone;
	}

	public int getShopid() {
		return shopid;
	}

	public void setShopid(int shopid) {
		this.shopid = shopid;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getBrithday() {
		return brithday;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

	public int getOrdid() {
		return ordid;
	}

	public void setOrdid(int ordid) {
		this.ordid = ordid;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Complaint getComp() {
		return comp;
	}

	public void setComp(Complaint comp) {
		this.comp = comp;
	}

	public void getOrdersInfoByUserid() throws IOException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (token == null) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.ERRORTOKEN);
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			return;
		}
		Map<String, Object> tokenmap = TokenUtils.manageToken(token,
				tokenService);

		if (tokenmap.get("message").toString().equals("SUCCESS")) {
			List<Order> ords = orderservice.getOrdsByUserid(Integer
					.parseInt(tokenmap.get("userid").toString()));
			if (ords.size() > 0) {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				reMap.put("IfExist", "yes");
				JSONArray ordsJson = CommonUtils.ordsToJosn(ords, m_Service,
						imgService);
				reMap.put("orders", ordsJson);

			} else {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				reMap.put("IfExist", "no");
			}

		} else if (tokenmap.get("message").toString().equals("TOKENOUT")) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.TOKENOUT);
		} else {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.ERRORTOKEN);
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
	}

	public void userRegiste() throws IOException {
		JSONObject reJson = new JSONObject();
		if (user == null || user.getUsername() == null
				|| user.getPassword() == null || user.getType() == null) {
			reJson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
		} else {
			if (userservice.checkByName(user.getUsername().trim())) // 用户名重复
			{
				reJson.put(CommonUtils.RESULTMESS, CommonUtils.EXISTUSERNAME);
				CommonUtils.toJson(ServletActionContext.getResponse(), reJson);
				return;
			}
			if (user.getRealname() != null) {
				String name = URLDecoder.decode(user.getRealname(), "UTF-8");
				user.setRealname(name);
			}
			String tokenCode = getTokenCode();
			userservice.createUser(user, tokenCode);
			int u_id = userservice.GetUserIDByName(user.getUsername(),
					user.getPassword());
			if (u_id > 0) {
				reJson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				reJson.put("userid", u_id);
				reJson.put("username", user.getUsername());
				reJson.put("token", tokenCode);
			} else {
				reJson.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
			}
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), reJson);
	}

	public void getUserInfoById() throws IOException {
		JSONObject reJson = new JSONObject();
		if (user == null) {
			reJson.put(CommonUtils.RESULTMESS, CommonUtils.EXISTUSERNAME);
		} else {
			User guser = userservice.findUserByUserid(user.getUserid());
			if (guser == null) {
				reJson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				reJson.put("IfExist", "no");
			} else {
				List<User> users = new ArrayList<User>();
				users.add(guser);
				reJson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				reJson.put("IfExist", "yes");
				reJson.put("user", CommonUtils.usersToJson(users));
			}
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), reJson);

	}

	public void userUpdate() throws IOException {
		Map<String, Object> reMap = new HashMap<String, Object>();

		if (user.getRealname() != null) {
			String name = URLDecoder.decode(user.getRealname(), "UTF-8");
			user.setRealname(name);
		}
		if (user.getProfession() != null) {
			String profession = URLDecoder
					.decode(user.getProfession(), "UTF-8");
			user.setProfession(profession);
		}
		if (user.getAdress() != null) {
			String adress = URLDecoder.decode(user.getAdress(), "UTF-8");
			user.setAdress(adress);
		}
		if (user.getUserid() == null || user.getUserid() == 0) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (brithday != null) {
					user.setBrithday(df.parse(brithday));
				}
				userservice.update(user);
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
	}

	public String getTokenCode() {
		log.debug("getting token code");
		String tokencode = TokenUtils.generateValue();
		if (tokenService.findOneByCode(tokencode) != null) {
			getTokenCode();
		}
		log.info("get token success");
		return tokencode;
	}

	public void getimgadverByCity() throws IOException {
		JSONObject rejson = new JSONObject();
		if (cityid == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		List<Imgadver> imgadv = imgadverService.getimgadverByCity(cityid);
		if (imgadv == null || 0 >= imgadv.size()) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
		} else {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("IfExist", "yes");
			JSONArray jsonStrs = new JSONArray();
			int n = imgadv.size();
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("imgadverid", imgadv.get(i).getImgadverid());
				json.put("imgadvername", imgadv.get(i).getImgadvername());
				json.put("imgadverurl", imgadv.get(i).getImgadverurl());
				jsonStrs.add(json);
			}
			rejson.put("imgadvs", jsonStrs);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
		}
	}

	public void getimgadverInfoById() throws IOException {
		JSONObject rejson = new JSONObject();
		if (imgadid == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		Imgadver imgadv = imgadverService.getimgadverById(imgadid);
		if (imgadv == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
		} else {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("IfExist", "yes");
			rejson.put("imgadverid", imgadv.getImgadverid());
			rejson.put("advername", imgadv.getAdvername());
			rejson.put("tourl", imgadv.getTourl());
			rejson.put("imgadvername", imgadv.getImgadvername());
			rejson.put("imgadverurl", imgadv.getImgadverurl());
			rejson.put("expiredate",
					TimeString.dateToString(imgadv.getExpiredate()));
			rejson.put("refcityname", imgadv.getRefcityname());
			rejson.put("createdate",
					TimeString.dateToString(imgadv.getCreatedate()));
			rejson.put("modifydate",
					TimeString.dateToString(imgadv.getModifydate()));
			rejson.put("createop", imgadv.getCreateop());
			rejson.put("modifyop", imgadv.getModifyop());
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
		}
	}

	public void bookService() throws IOException {
		JSONObject rejson = new JSONObject();
		if (number == 0 || service == null || service.getServiceid() == 0
				|| user == null || user.getUserid() == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		Order ord = userservice.bookService(user.getUserid(),
				service.getServiceid(), number, ordphone);
		if (ord != null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("orderid", ord.getOrderid());
		} else {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void bookShop() throws IOException {
		JSONObject rejson = new JSONObject();
		if (user == null || user.getUserid() == 0 || shopid == 0
				|| ordphone == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		Order ord = userservice.bookShop(user.getUserid(), shopid, ordphone);
		if (ord != null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("orderid", ord.getOrderid());
		} else {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void getBookCount() throws IOException {
		JSONObject rejson = new JSONObject();
		if (user == null || user.getUserid() == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		int count = userservice.countBooksByUserId(user.getUserid());
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		rejson.put("bookcount", count);
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void getBookServsInfo() throws IOException {
		JSONObject rejson = new JSONObject();
		int sumcount = userservice.getBookServiceCount(user.getUserid(),
				ordstate);
		if (sumcount == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
			rejson.put("totalnumber", 0);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		int sumpage = CommonUtils.getTotalPages(pagesize, sumcount);
		List<Order> ords = userservice.getBookService(user.getUserid(),
				ordstate, pagesize, page);
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		rejson.put("IfExist", "yes");
		rejson.put("totalnumber", sumcount);
		rejson.put("totalpage", sumpage);
		JSONArray jsonStrs = new JSONArray();
		int n = ords.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("orderid", ords.get(i).getOrderid());
				List<Service> oserv = m_Service.GetServiceByOrdid(ords.get(i)
						.getOrderid());
				if (oserv != null && oserv.size() > 0) {
					Shop sshop = oserv.get(0).getShop();
					if (sshop != null) {
						json.put("shopid", sshop.getShopid());
						json.put("shopname", sshop.getShopname());
					}
				}
				json.put("createtime", TimeString.dateToDateString(ords.get(i)
						.getCreatetime()));
				json.put("number", ords.get(i).getNumber());
				json.put("sumprice", ords.get(i).getSumprice());
				json.put("ordphone", ords.get(i).getOrdphone());
				json.put("realprice", ords.get(i).getRealprice());
				json.put("ordstate", ords.get(i).getState());
				json.put("ordservs", serviceToSimpleJson(m_Service
						.GetServiceByOrdid(ords.get(i).getOrderid())));
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		rejson.put("ordsInfo", jsonStrs);
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void getBookShopsInfo() throws IOException {
		JSONObject rejson = new JSONObject();
		int sumcount = userservice.getBookShopCount(user.getUserid(), ordstate);
		if (sumcount == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
			rejson.put("totalnumber", 0);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		int sumpage = CommonUtils.getTotalPages(pagesize, sumcount);
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		rejson.put("IfExist", "yes");
		rejson.put("totalnumber", sumcount);
		rejson.put("totalpage", sumpage);
		List<Order> ords = userservice.getBookShop(user.getUserid(), ordstate,
				pagesize, page);
		JSONArray jsonStrs = new JSONArray();
		int n = ords.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("orderid", ords.get(i).getOrderid());
				json.put("createtime", TimeString.dateToDateString(ords.get(i)
						.getCreatetime()));
				json.put("number", ords.get(i).getNumber());
				json.put("sumprice", ords.get(i).getSumprice());
				json.put("ordphone", ords.get(i).getOrdphone());
				json.put("realprice", ords.get(i).getRealprice());
				json.put("ordstate", ords.get(i).getState());
				json.put("ordshop", shopsToSimpleJson(shopService
						.getShopInfoByOrdId(ords.get(i).getOrderid())));
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		rejson.put("ordsInfo", jsonStrs);
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void cancelOrd() throws IOException {
		JSONObject rejson = new JSONObject();
		if (ordid == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		Order ord = orderservice.getOrdByid(ordid);
		if (ord == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		} else {
			ord.setState(3);// 退订服务
			orderservice.update(ord);
		}
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void createComplaint() throws IOException {
		JSONObject rejson = new JSONObject();
		if (comp == null || comp.getSubmituserid() == 0
				|| comp.getUserphone() == null
				|| comp.getComplaintcontent() == null || comp.getShopid() == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		comp.setComplaintcontent(URLDecoder.decode(comp.getComplaintcontent(),
				"UTF-8"));
		User cuser = userservice.findUserByUserid(comp.getSubmituserid());
		comp.setSubmitusername(cuser.getUsername());
		Calendar cal = Calendar.getInstance();// 获取当前时间
		Date currentDate = cal.getTime();
		comp.setCreatedatetime(currentDate);
		comp.setState(0);
		complaintService.create(comp);
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		rejson.put("complaintid", comp.getComplaintid());
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void updateComplaint() throws IOException {
		JSONObject rejson = new JSONObject();
		if (comp == null || comp.getComplaintid() == 0
				|| comp.getComplaintcontent() == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		Complaint dbcomp = complaintService.findComplaintByID(comp
				.getComplaintid());
		if (dbcomp == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		dbcomp.setComplaintcontent(URLDecoder.decode(
				comp.getComplaintcontent(), "UTF-8"));
		Calendar cal = Calendar.getInstance();// 获取当前时间
		Date currentDate = cal.getTime();
		dbcomp.setModifydatetime(currentDate);
		complaintService.update(comp);
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		rejson.put("complaintid", comp.getComplaintid());
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void deleteComplaint() throws IOException {
		JSONObject rejson = new JSONObject();
		if (comp == null || comp.getComplaintid() == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		complaintService.delete(comp);
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void createReview() throws IOException {
		JSONObject rejson = new JSONObject();
		if (review == null || review.getOrderid() == 0
				|| review.getUserid() == 0 || review.getScore() == 0
				|| review.getContent() == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		User ruser = userservice.findUserByUserid(review.getUserid());
		review.setUsername(ruser.getUsername());
		Serviceordrel servrel = rel_Service.findServiceordrelByordId(review
				.getOrderid());
		if (servrel == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		if (servrel.getId().getServiceid() == 0) {
			int shopid = servrel.getId().getShopid();
			review.setShopid(shopid);
			review.setServiceid(0);
			review.setIshopord(1);
			Shop sshop = shopService.getByShopid(shopid);
			double sc = (review.getScore().doubleValue()) * 0.05
					+ sshop.getScore() * 0.95;
			sshop.setScore(sc);
			shopService.update(sshop);
		} else {
			review.setIshopord(0);
			review.setServiceid(servrel.getId().getServiceid());
			Shop gsshop = shopService.getShopByServid(servrel.getId()
					.getServiceid());
			if (gsshop != null) {
				review.setShopid(gsshop.getShopid());
			}
			double sc = (review.getScore().doubleValue()) * 0.05
					+ gsshop.getScore() * 0.95;
			gsshop.setScore(sc);
			shopService.update(gsshop);
		}
		Calendar cal = Calendar.getInstance();// 获取当前时间
		Date currentDate = cal.getTime();
		review.setCreatedatetime(currentDate);
		reviewService.create(review);
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		rejson.put("reviewid", review.getReviewid());
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void updateReview() throws IOException {
		JSONObject rejson = new JSONObject();
		if (review == null || review.getReviewid() == 0
				|| review.getScore() == 0 || review.getContent() == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		Review dbreview = reviewService.findReviewById(review.getReviewid());
		dbreview.setContent(review.getContent());
		dbreview.setScore(review.getScore());
		Calendar cal = Calendar.getInstance();// 获取当前时间
		Date currentDate = cal.getTime();
		dbreview.setModifydatetime(currentDate);
		reviewService.update(dbreview);
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void deleteReview() throws IOException {
		JSONObject rejson = new JSONObject();
		if (review == null || review.getReviewid() == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		reviewService.delete(review);
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public void findReviewsByShopId() throws IOException {
		JSONObject rejson = new JSONObject();
		if (shopid == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		int sumcount = reviewService.findReviewsCountByShopId(shopid);
		if (sumcount == 0) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
			rejson.put("totalnumber", 0);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		int sumpage = CommonUtils.getTotalPages(pagesize, sumcount);
		if (page > sumpage) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		rejson.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		rejson.put("IfExist", "yes");
		rejson.put("totalnumber", sumcount);
		rejson.put("totalpage", sumpage);
		List<Review> revs = reviewService.findReviewsByShopId(shopid, pagesize,
				page);
		rejson.put("reviews", CommonUtils.reviewsToJson(revs));
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	private JSONArray shopsToSimpleJson(List<Shop> shops) {
		JSONArray jsonStrs = new JSONArray();
		int n = shops.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("shopid", shops.get(i).getShopid());
				json.put("shopname", shops.get(i).getShopname());
				json.put("address", shops.get(i).getShopaddress());
				json.put("shoplevel", shops.get(i).getShoplevel());
				json.put("discountmessage", shops.get(i).getDiscountmessage());
				List<Imgsrc> shopimgs = imgService.getImagsByShopid(shops
						.get(i).getShopid());
				if (shopimgs != null) {
					json.put("shopimages", CommonUtils.imgsToJson(shopimgs));
				}
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	private static JSONArray serviceToSimpleJson(List<Service> ser) {
		JSONArray jsonStrs = new JSONArray();
		int n = ser.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("serviceid", ser.get(i).getServiceid());
				json.put("servicename", ser.get(i).getServicename());
				json.put("price", ser.get(i).getPrice().toString());
				Set<Prodtyperel> prel = ser.get(i).getProdtyperels();
				Iterator<Prodtyperel> it = prel.iterator();
				Product prodcut = null;
				while (it.hasNext()) {
					Prodtyperel str = it.next();
					prodcut = str.getProduct();
				}
				if (prodcut != null) {
					json.put("productid", prodcut.getProductid());
					json.put("productname", prodcut.getProductname());
				}
				List<Imgsrc> imgs = new ArrayList<Imgsrc>();
				imgs.addAll(ser.get(i).getImgsrcs());
				json.put("servimgs", CommonUtils.imgsToJson(imgs));
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}
}
