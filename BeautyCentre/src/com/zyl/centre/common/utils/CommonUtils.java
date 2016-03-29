package com.zyl.centre.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.zyl.centre.entity.Area;
import com.zyl.centre.entity.District;
import com.zyl.centre.entity.Imgactive;
import com.zyl.centre.entity.Imgsrc;
import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.Prodtype;
import com.zyl.centre.entity.Prodtyperel;
import com.zyl.centre.entity.Product;
import com.zyl.centre.entity.Review;
import com.zyl.centre.entity.Service;
import com.zyl.centre.entity.Shop;
import com.zyl.centre.entity.User;
import com.zyl.centre.service.IImgService;
import com.zyl.centre.service.IImgactiveService;
import com.zyl.centre.service.IServiceService;

public class CommonUtils {

	public static final String RESULTMESS = "ResultMessage";// 传入json数据错误
	public static final int JSONERROR = -900;// 传入json数据错误
	public static final int ERROR = -999;// 系统错误
	public static final int PARAMERROR = -901;// 参数不能为空
	public static final int SUCCESS = 0;
	public static final int TOKENOUT = -902;// Token 超过期限
	public static final int ERRORTOKEN = -903;// Token 字符串错误
	public static final int DELETESERVERROR = -904;// 删除服务
	public static final int ERRORUSER = -905;// 用户信息异常（登录）
	public static final int EXISTSHOP = -906;// 已经存在店铺
	public static final int EXISTUSERNAME = -907;// 用户名重复
	private static final double EARTH_RADIUS = 6371000;// 赤道半径(单位m)

	/**
	 * 转化为弧度(rad)
	 * */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 
	 * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下
	 * 
	 * @param lon1
	 *            第一点的经度
	 * @param lat1第一点的纬度
	 * @param lon2
	 *            第二点的经度
	 * @param lat3
	 *            第二点的纬度
	 * @return 返回的距离，单位m
	 * */
	public static double GetDistance(double lon1, double lat1, double lon2,
			double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lon1) - rad(lon2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static void toJson(HttpServletResponse response, Object data)
			throws IOException {
		Gson gson = new Gson();
		String result = gson.toJson(data);// 将Map转换为json格式的数据
		System.out.println("返回客户端的json:" + result);
		response.setContentType("text/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}

	public static void setSessionMap(Integer integer, String tokenCode) {
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("userid", integer);
		sessionMap.put("tokenCode", tokenCode);
		ServletActionContext.getRequest().getSession()
				.setAttribute("sessionMap", sessionMap);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getsetSessionMap() {
		return (Map<String, Object>) ServletActionContext.getRequest()
				.getSession().getAttribute("sessionMap");
	}

	public static int getTotalPages(int pageSize, int sumNumber) {
		if (sumNumber > pageSize) {
			int sum = 0;
			int m = sumNumber % pageSize;
			if (m == 0) {
				sum = sumNumber / pageSize;
			} else {
				sum = (sumNumber / pageSize) + 1;
			}
			return sum;
		} else if (sumNumber == 0) {
			return 0;
		} else {
			return 1;
		}

	}

	public static JSONArray imgsToJson(List<Imgsrc> imgs) {
		JSONArray jsonStrs = new JSONArray();
		if (imgs == null) {
			return null;
		}
		int n = imgs.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("imgid", imgs.get(i).getImgid());
				json.put("imgname", imgs.get(i).getImgname());
				json.put("url", imgs.get(i).getUrl());
				json.put("imgdec", imgs.get(i).getImgdec());
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray serviceToJson(List<Service> ser,
			IImgService imgService) {
		JSONArray jsonStrs = new JSONArray();
		if (ser == null) {
			return null;
		}
		int n = ser.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("serviceid", ser.get(i).getServiceid());
				json.put("servicename", ser.get(i).getServicename());
				json.put("state", ser.get(i).getState());
				json.put("createtime",
						TimeString.dateToString(ser.get(i).getCreatetime()));
				json.put("price", ser.get(i).getPrice().toString());
				Set<Prodtyperel> prel = ser.get(i).getProdtyperels();
				Iterator<Prodtyperel> it = prel.iterator();
				List<Prodtype> types = new ArrayList<Prodtype>();
				Product prodcut = null;
				while (it.hasNext()) {
					Prodtyperel str = it.next();
					types.add(str.getProdtype());
					prodcut = str.getProduct();
				}
				if (prodcut != null) {
					json.put("productid", prodcut.getProductid());
					json.put("productname", prodcut.getProductname());
				}
				json.put("servicedec", ser.get(i).getServicedec());
				if (types.size() > 0) {
					json.put("types", prodtypesToJosn(types));
				}
				// List<Imgsrc> imgs = new ArrayList<Imgsrc>();
				// imgs.addAll(ser.get(i).getImgsrcs());
				List<Imgsrc> imgs = imgService.getImagsByServiceid(ser.get(i)
						.getServiceid());
				json.put("servimgs", imgsToJson(imgs));
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray webshopsToJson(List<Shop> shops,
			IImgService imgService, IImgactiveService imgactiveService,
			IServiceService m_Service) {
		JSONArray jsonStrs = new JSONArray();
		if (shops == null) {
			return null;
		}
		int n = shops.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("shopid", shops.get(i).getShopid());
				json.put("shopname", shops.get(i).getShopname());
				json.put("shoptypeid", shops.get(i).getShoptypeid());
				json.put("createuserid", shops.get(i).getUser().getUserid());
				json.put("shopphone", shops.get(i).getShopphone());
				json.put("address", shops.get(i).getShopaddress());
				json.put("perconsum", shops.get(i).getPerconsum());
				json.put("businesstime", shops.get(i).getBusinesstime());
				json.put("shoplevel", shops.get(i).getShoplevel());
				json.put("discountmessage", shops.get(i).getDiscountmessage());
				json.put("state", shops.get(i).getState());
				json.put("createtime",
						TimeString.dateToString(shops.get(i).getCreatetime()));
				json.put("city", shops.get(i).getArea().getDistrict()
						.getDistrictname());
				json.put("area", shops.get(i).getArea().getAreaname());
				json.put("shopdec", shops.get(i).getShopdec());
				json.put("shopimages", imgsToJson(imgService
						.getImagsByShopid(shops.get(i).getShopid())));
				List<Imgactive> imgactives = imgactiveService
						.getImgactiveByShopid(shops.get(i).getShopid());
				if (imgactives != null && imgactives.size() > 0) {
					json.put("actives", activeimgsToJson(imgactives));
				}
				List<Service> shopservs = m_Service.getServsByWebShopId(shops
						.get(i).getShopid());
				json.put("shopservs", serviceToJson(shopservs, imgService));
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray shopsToJson(List<Shop> shops,
			IImgService imgService, IImgactiveService imgactiveService) {
		JSONArray jsonStrs = new JSONArray();
		if (shops == null) {
			return null;
		}
		int n = shops.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("shopid", shops.get(i).getShopid());
				json.put("shopname", shops.get(i).getShopname());
				json.put("shoptypeid", shops.get(i).getShoptypeid());
				json.put("createuserid", shops.get(i).getUser().getUserid());
				json.put("shopphone", shops.get(i).getShopphone());
				json.put("address", shops.get(i).getShopaddress());
				json.put("perconsum", shops.get(i).getPerconsum());
				json.put("businesstime", shops.get(i).getBusinesstime());
				json.put("shoplevel", shops.get(i).getShoplevel());
				json.put("discountmessage", shops.get(i).getDiscountmessage());
				json.put("state", shops.get(i).getState());
				json.put("createtime",
						TimeString.dateToString(shops.get(i).getCreatetime()));
				json.put("city", shops.get(i).getArea().getDistrict()
						.getDistrictname());
				json.put("area", shops.get(i).getArea().getAreaname());
				json.put("shopdec", shops.get(i).getShopdec());
				json.put("shopimages", imgsToJson(imgService
						.getImagsByShopid(shops.get(i).getShopid())));
				List<Imgactive> imgactives = imgactiveService
						.getImgactiveByShopid(shops.get(i).getShopid());
				if (imgactives != null && imgactives.size() > 0) {
					json.put("actives", activeimgsToJson(imgactives));
				}
				List<Service> shopservs = new ArrayList<Service>();
				Set<Service> servs = shops.get(i).getServices();
				if (servs != null) {
					shopservs.addAll(servs);
				}
				json.put("shopservs", serviceToJson(shopservs, imgService));
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray ordsToJosn(List<Order> ords,
			IServiceService m_Service, IImgService imgService) {
		JSONArray jsonStrs = new JSONArray();
		if (ords == null) {
			return null;
		}
		int n = ords.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("orderid", ords.get(i).getOrderid());
				json.put("number", ords.get(i).getNumber());
				json.put("sumprice", ords.get(i).getSumprice());
				json.put("realprice", ords.get(i).getRealprice());
				json.put("createtime",
						TimeString.dateToString(ords.get(i).getCreatetime()));
				json.put("modifytime",
						TimeString.dateToString(ords.get(i).getModifytime()));
				json.put("userid", ords.get(i).getUser().getUserid());
				json.put("submitusername", ords.get(i).getSubmitusername());
				json.put("ordphone", ords.get(i).getOrdphone());
				json.put("state", ords.get(i).getState());
				json.put("orderdec", ords.get(i).getOrderdec());
				List<Service> sers = m_Service.GetServiceByOrdid(ords.get(i)
						.getOrderid());
				json.put("services",
						CommonUtils.serviceToJson(sers, imgService));
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONObject getJson() {
		StringBuffer json = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = ServletActionContext.getRequest()
					.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (json.length() > 0) {
			JSONObject jsonObj = JSONObject.fromObject(json.toString());
			return jsonObj;
		}
		return null;
	}

	public static JSONArray prodtypesToJosn(List<Prodtype> types) {
		JSONArray jsonStrs = new JSONArray();
		if (types == null) {
			return null;
		}
		int n = types.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("prodtypeid", types.get(i).getProdtypeid());
				json.put("prodtypename", types.get(i).getProdtypename());
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray prodsToJosn(List<Product> prods) {
		JSONArray jsonStrs = new JSONArray();
		if (prods == null) {
			return null;
		}
		int n = prods.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("productid", prods.get(i).getProductid());
				json.put("productname", prods.get(i).getProductname());
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray citysToJson(List<District> citys) {
		if (citys == null) {
			return null;
		}
		JSONArray jsonStrs = new JSONArray();
		int n = citys.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("districtid", citys.get(i).getDistrictid());
				json.put("districtname", citys.get(i).getDistrictname());
				List<Area> areas = new ArrayList<Area>(citys.get(i).getAreas());
				json.put("prodtypes", areasToJson(areas));
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray areasToJson(List<Area> areas) {
		if (areas == null) {
			return null;
		}
		JSONArray jsonStrs = new JSONArray();
		int n = areas.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("areaid", areas.get(i).getAreaid());
				json.put("areaname", areas.get(i).getAreaname());
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray usersToJson(List<User> users) {
		if (users == null) {
			return null;
		}
		JSONArray jsonStrs = new JSONArray();
		int n = users.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("userid", users.get(i).getUserid());
				json.put("username", users.get(i).getUsername());
				json.put("realname", users.get(i).getRealname());
				json.put("password", users.get(i).getPassword());
				json.put("sex", users.get(i).getSex());
				json.put("brithday",
						TimeString.dateToDateString(users.get(i).getBrithday()));
				json.put("profession", users.get(i).getProfession());
				json.put("type", users.get(i).getType());
				json.put("createtime",
						TimeString.dateToString(users.get(i).getCreatetime()));
				json.put("phone", users.get(i).getPhone());
				json.put("adress", users.get(i).getAdress());
				json.put("email", users.get(i).getEmail());
				json.put("userdec", users.get(i).getUserdec());
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray activeimgsToJson(List<Imgactive> imgactives) {
		if (imgactives == null) {
			return null;
		}
		JSONArray jsonStrs = new JSONArray();
		int n = imgactives.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("imgactiveid", imgactives.get(i).getImgactiveid());
				json.put("activename", imgactives.get(i).getActivename());
				json.put("activeurl", imgactives.get(i).getActiveurl());
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static JSONArray reviewsToJson(List<Review> reviews) {
		if (reviews == null) {
			return null;
		}
		JSONArray jsonStrs = new JSONArray();
		int n = reviews.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("reviewid", reviews.get(i).getReviewid());
				json.put("orderid", reviews.get(i).getOrderid());
				json.put("userid", reviews.get(i).getUserid());
				json.put("username", reviews.get(i).getUsername());
				json.put("ishopord", reviews.get(i).getIshopord());
				json.put("createdatetime", TimeString.dateToDateString(reviews
						.get(i).getCreatedatetime()));
				json.put("score", reviews.get(i).getScore());
				json.put("content", reviews.get(i).getContent());
				json.put("modifydatetime", TimeString.dateToDateString(reviews
						.get(i).getModifydatetime()));
				json.put("shopid", reviews.get(i).getShopid());
				json.put("serviceid", reviews.get(i).getServiceid());
				jsonStrs.add(json);
			}
		} else {
			jsonStrs.add("{}");
		}
		return jsonStrs;
	}

	public static Map<String, Double> getLngAndLat(String address) {
		Map<String, Double> map = new HashMap<String, Double>();
		String realAddress = address;
		String url = "http://api.map.baidu.com/geocoder/v2/?address="
				+ realAddress + "&output=json&ak=yRL9n3gObCfwPTw5WgnGX9oe";
		String json = loadJSON(url);
		System.out.println(json);
		JSONObject obj = JSONObject.fromObject(json);
		if (obj.get("status").toString().equals("0")) {
			double lng = obj.getJSONObject("result").getJSONObject("location")
					.getDouble("lng");
			double lat = obj.getJSONObject("result").getJSONObject("location")
					.getDouble("lat");
			System.out.println(String.valueOf(lat));
			map.put("lng", lng);
			map.put("lat", lat);
			System.out.println("经度：" + lng + "---纬度：" + lat);
		} else {
			System.out.println("未找到相匹配的经纬度！");
			return null;
		}
		return map;
	}

	private static String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return json.toString();
	}
}