package com.zyl.centre.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zyl.centre.common.utils.CommonUtils;
import com.zyl.centre.common.utils.TimeString;
import com.zyl.centre.common.utils.TokenUtils;
import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.Prodtype;
import com.zyl.centre.entity.Product;
import com.zyl.centre.entity.Service;
import com.zyl.centre.entity.Shop;
import com.zyl.centre.service.IImgService;
import com.zyl.centre.service.IOrderService;
import com.zyl.centre.service.IProdtypeService;
import com.zyl.centre.service.IProductService;
import com.zyl.centre.service.IServiceService;
import com.zyl.centre.service.IShopService;
import com.zyl.centre.service.ITokenService;

public class ServiceAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Service service;
	// private Product product;
	private String types;
	private String proid;
	private String token;
	private String userid;
	private List<File> files;
	private List<String> filesFileName;
	private List<String> filesContentType;
	private int number;
	private String status;

	@Resource(name = "serviceService")
	private IServiceService m_Service;

	@Resource(name = "productService")
	private IProductService m_Product;

	@Resource(name = "prodtypeService")
	private IProdtypeService m_ProdtypeService;

	@Resource(name = "imgService")
	public IImgService imgService;

	@Resource(name = "tokenService")
	private ITokenService tokenService;

	@Resource(name = "shopService")
	public IShopService shopService;

	@Resource(name = "orderService")
	private IOrderService orderservice;

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getNumber() {
		return number;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setNumber(int number) {
		this.number = number;
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

	public String getProid() {
		return proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	@Override
	public String execute() throws Exception {

		return SUCCESS;
	}

	@SuppressWarnings("unused")
	public void produceService() throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (token == null || service == null
				|| service.getServicename() == null || service.getPrice() == 0
				|| types == null || proid == null || status == null) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			return;
		}

		Map<String, Object> maptoken = TokenUtils.manageToken(token,
				tokenService);
		if (maptoken.get("message").equals("SUCCESS")) {
			if (status.equals("post")) {
				if (files == null || 0 >= files.size() || filesFileName == null
						|| 0 >= filesFileName.size()) {
					reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
					CommonUtils.toJson(ServletActionContext.getResponse(),
							reMap);
					return;
				}
			}
			JSONArray ids = JSONArray.fromObject(types);
			System.out.println("ids" + ids);
			if (ids == null) {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
				CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
				return;
			}
			List<Integer> prodtypeid = new ArrayList<Integer>();
			for (int i = 0; i < ids.size(); i++) {
				JSONObject jsonObj = ids.getJSONObject(i);
				prodtypeid.add(jsonObj.getInt("prodtypeid"));
			}
			TimeString time = new TimeString();
			Product pro = m_Product.findOneById(Integer.valueOf(proid));
			service.setProduct(pro);
			Shop shop_temp = shopService.getByUid(userid);
			service.setShop(shop_temp);
			service.setShopname(shop_temp.getShopname());
			Calendar cal = Calendar.getInstance();// 获取当前时间
			Date currentDate = cal.getTime();
			service.setState(1);// 服务默认可用
			if (status.equals("post")) {
				service.setCreatetime(currentDate);
				Service serv = m_Service.CreateService(service, files,
						filesFileName, status, prodtypeid);
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				reMap.put("serviceid", serv.getServiceid());
				CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			}

			if (status.equals("edit")) {
				service.setModifydatetime(currentDate);
				Service serv = m_Service.UpdateService(service, files,
						filesFileName, status, prodtypeid);
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				reMap.put("serviceid", serv.getServiceid());
				CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			}
			

		} else {
			if (maptoken.get("message").equals("TOKENOUT")) {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.TOKENOUT);
			} else {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
			}
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
		}
	}

	public void deleteService() throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> token_reMap = new HashMap<String, Object>();
		if (token == null || service == null || service.getServiceid() == null) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			return;
		}
		token_reMap = TokenUtils.manageToken(token, tokenService);
		if (!token_reMap.get("message").equals("SUCCESS")) {
			CommonUtils.toJson(ServletActionContext.getResponse(), token_reMap);
			return;
		}
		if (service.getServiceid() == null) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.JSONERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			return;
		}
		List<Order> orders = orderservice.getOrdsByServiceid(service
				.getServiceid());
		if (orders == null || orders.size() == 0) {
			m_Service.deleteService(service);
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		} else {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.DELETESERVERROR);
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
	}

	public void getServsInfoByAreaType() throws IOException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		JSONObject json = CommonUtils.getJson();
		if (json == null || json.toString() == "{}") {
			List<Service> ser = m_Service.findAll();
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
			reMap.put("IfExist", "yes");
			reMap.put("servs", CommonUtils.serviceToJson(ser, imgService));
		} else {
			boolean falge = false;
			JSONArray ids = JSONArray.fromObject(types);
			String city = json.getString("cityname");
			String area = json.getString("areaname");
			if (city == null || area == null) {
				falge = true;
			}
			Integer prodid = Integer.valueOf(json.getString("prodctid"));
			if (prodid == null || ids == null) {
				falge = true;
			}
			if (falge) {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			} else {
				List<Integer> prodtypeid = new ArrayList<Integer>();
				for (int i = 0; i < ids.size(); i++) {
					JSONObject jsonObj = ids.getJSONObject(i);
					prodtypeid.add(jsonObj.getInt("prodtypeid"));
				}
				List<Service> ser = m_Service.getServByAreaType(city, area,
						prodid, prodtypeid);
				if (ser.size() > 0) {
					reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
					reMap.put("IfExist", "yes");
					reMap.put("servs",
							CommonUtils.serviceToJson(ser, imgService));
				} else {
					reMap.put("ResultMessage", CommonUtils.SUCCESS);
					reMap.put("IfExist", "no");
				}
			}
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
	}

	public void getServsInfo() throws IOException {
		Map<String, Object> reMap = new HashMap<String, Object>();

		if (userid == null || token == null) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			return;
		}
		Map<String, Object> maptoken = TokenUtils.manageToken(token,
				tokenService);
		if (maptoken.get("message").equals("SUCCESS")) {
			Shop shop = shopService.getByUid(userid);
			List<Service> services = m_Service.getServicesByShopid(shop
					.getShopid());
			if (services != null && !services.isEmpty()) {
				int sum = cmSum(services.size());
				List<Service> reServs = formatServices(number, services);
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				reMap.put("IfExist", "yes");
				reMap.put("sumPage", sum);
				reMap.put("services",
						CommonUtils.serviceToJson(reServs, imgService));
			} else {
				reMap.put("ResultMessage", CommonUtils.SUCCESS);
				reMap.put("IfExist", "no");
			}
		} else {
			if (maptoken.get("message").equals("TOKENOUT")) {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.TOKENOUT);
			} else {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.ERROR);
			}
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
	}

	public void getServtype() throws IOException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Product> prods = m_Product.findAll();
		JSONArray jsonStrs = new JSONArray();
		int n = prods.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("productid", prods.get(i).getProductid());
				json.put("productname", prods.get(i).getProductname());
				List<Prodtype> prodtypes = new ArrayList<Prodtype>(prods.get(i)
						.getProdtypes());
				json.put("prodtypes", CommonUtils.prodtypesToJosn(prodtypes));
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
		reMap.put("prods", jsonStrs);
		CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
	}

	public void getTypesByProdId() throws IOException {
		JSONObject rejson = new JSONObject();
		if (proid == null) {
			rejson.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
			return;
		}
		int prodid = Integer.valueOf(proid);
		List<Prodtype> types = m_ProdtypeService.findTypesByProdId(prodid);
		if (types.isEmpty()) {
			rejson.put("ResultMessage", CommonUtils.SUCCESS);
			rejson.put("IfExist", "no");
		} else {
			rejson.put("ResultMessage", CommonUtils.SUCCESS);
			rejson.put("IfExist", "yes");
			rejson.put("types", CommonUtils.prodtypesToJosn(types));
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), rejson);
	}

	public List<Service> formatServices(int number, List<Service> servs) {
		List<Service> hs = new ArrayList<Service>();
		int size = servs.size();
		int sum = cmSum(size);
		if (number == 0) {
			hs = servs;
		} else {
			if (sum > 1) {
				Service[] mser = (Service[]) servs.toArray();
				for (int i = 10 * (number - 1) + 1; i <= 10 * number; i++) {
					hs.add(mser[i]);
				}
			} else {
				hs = servs;
			}
		}
		return hs;
	}

	public int cmSum(int size) {
		int sum = 0;
		if (size > 10) {
			int m = size % 10;
			if (m == 0) {
				sum = size / 10;
			} else {
				sum = (size / 10) + 1;
			}
		} else {
			sum = 1;
		}
		return sum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
