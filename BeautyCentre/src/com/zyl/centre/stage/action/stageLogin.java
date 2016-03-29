package com.zyl.centre.stage.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zyl.centre.entity.District;
import com.zyl.centre.entity.Imgactive;
import com.zyl.centre.entity.Imgadver;
import com.zyl.centre.entity.Menu;
import com.zyl.centre.entity.User;
import com.zyl.centre.service.IActiveshopService;
import com.zyl.centre.service.IDistrictService;
import com.zyl.centre.service.IImgactiveService;
import com.zyl.centre.service.IImgadverService;
import com.zyl.centre.service.IMenuService;
import com.zyl.centre.service.IUserService;

public class stageLogin extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource(name = "userservice")
	private IUserService userservice;

	@Resource(name = "menuService")
	private IMenuService menuservice;

	@Resource(name = "imgadverService")
	private IImgadverService imgadverService;

	@Resource(name = "districtService")
	private IDistrictService districtService;

	@Resource(name = "imgactiveService")
	private IImgactiveService imgactiveService;

	@Resource(name = "activeshopService")
	private IActiveshopService activeshopService;

	private User user;
	// 封装上传文件域的属性
	private File upload;
	private String uploadFileName;
	private File activeimg;
	private String activeimgFileName;

	private String savePath;
	private int imgactiveid;

	private int adverid;
	private Imgadver imgadv;
	private Imgactive active;

	private int city;
	private int pageSize = 10;// 每页显示几条

	private int page = 1; // 默认当前页

	private int expday;

	private String shopkey;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	private int totalPage;// 总共多少页

	private int userNum;// 总过多少条

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Imgadver getImgadv() {
		return imgadv;
	}

	public void setImgadv(Imgadver imgadv) {
		this.imgadv = imgadv;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getExpday() {
		return expday;
	}

	public void setExpday(int expday) {
		this.expday = expday;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public int getAdverid() {
		return adverid;
	}

	public void setAdverid(int adverid) {
		this.adverid = adverid;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public File getActiveimg() {
		return activeimg;
	}

	public void setActiveimg(File activeimg) {
		this.activeimg = activeimg;
	}

	public String getActiveimgFileName() {
		return activeimgFileName;
	}

	public void setActiveimgFileName(String activeimgFileName) {
		this.activeimgFileName = activeimgFileName;
	}

	public Imgactive getActive() {
		return active;
	}

	public void setActive(Imgactive active) {
		this.active = active;
	}

	public int getImgactiveid() {
		return imgactiveid;
	}

	public void setImgactiveid(int imgactiveid) {
		this.imgactiveid = imgactiveid;
	}

	@Override
	public String execute() throws Exception {
		System.out.println(user.getUsername() + "   " + user.getPassword());
		User getuser = userservice.findOneByPass(user.getUsername(),
				user.getPassword());
		if (getuser != null) {
			if (getuser.getType() == 100) {
				List<Menu> menus = menuservice.findAll();
				System.out.println("menus size" + menus.size());
				ServletActionContext.getRequest().getSession()
						.setAttribute("user", getuser);
				// String nodes = GetMenus(menus);
				System.out.println(menusToJson(menus));
				// System.out.println(nodes);
				ServletActionContext.getRequest().getSession()
						.setAttribute("menus", menusToJson(menus));
				ServletActionContext.getRequest().getSession()
						.setAttribute("loginmess", "");
				return SUCCESS;
			} else {
				ServletActionContext.getRequest().getSession()
						.setAttribute("loginmess", "权限不足！");
				return ERROR;
			}
		}
		ServletActionContext.getRequest().getSession()
				.setAttribute("loginmess", "密码或者用户名错误！");
		return ERROR;
	}

	public String listUser() {
		userNum = userservice.findAll().size();// 总过多少条
		if (userNum % pageSize == 0) {
			totalPage = userNum / pageSize;// 总共多少页
		} else {
			totalPage = userNum / pageSize + 1;// 总共多少页
		}
		System.out.println("总页数：" + totalPage + "当前页：" + page);
		List<User> users = userservice.findUserByPage(page, pageSize);
		ServletActionContext.getRequest().getSession()
				.setAttribute("allUsers", users);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("page", page);
		return SUCCESS;
	}

	public JSONArray menusToJson(List<Menu> menus) {
		JSONArray jsonStrs = new JSONArray();
		int n = menus.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				JSONObject json = new JSONObject();
				json.put("id", menus.get(i).getMenuId());
				if (menus.get(i).getLevels() == 1) {
					json.put("pId", "0");
					json.put("font", "{'font-weight':'bold'}");
					json.put("isParent", "true");
					json.put("open", "true");
					json.put("iconOpen", "css/diy/open.png");
					json.put("iconClose", "css/diy/close.png");
				} else {
					json.put("pId", menus.get(i).getParentId());
					json.put("isParent", false);
					json.put("toUrl", menus.get(i).getUrl());
					json.put("icon", "css/diy/3.png");
				}
				json.put("name", menus.get(i).getMenuName());
				jsonStrs.add(json);
			}
		}
		return jsonStrs;
	}

	public String stageunlogin() {
		ActionContext.getContext().getSession().remove("user");
		Enumeration<String> em = ServletActionContext.getRequest().getSession()
				.getAttributeNames();
		while (em.hasMoreElements()) {
			ServletActionContext.getRequest().getSession()
					.removeAttribute(em.nextElement().toString());
		}
		return "login";
	}

	public String getImgadvsInfo() {
		List<Imgadver> imgadvers = imgadverService.findAll();
		List<District> citys = districtService.findAll();
		ServletActionContext.getRequest().getSession()
				.setAttribute("imgadvers", imgadvers);
		ServletActionContext.getRequest().getSession()
				.setAttribute("citys", citys);
		return SUCCESS;
	}

	public String saveImgadve() throws IOException {
		Calendar cal = Calendar.getInstance();// 获取当前时间
		Date currentDate = cal.getTime();
		imgadv.setCreatedate(currentDate);
		cal.add(Calendar.DAY_OF_MONTH, +expday);// 到期时间
		imgadv.setExpiredate(cal.getTime());
		imgadv.setRefcityid(city);
		District dty = districtService.findById(city);
		if (dty != null && dty.getDistrictname() != null) {
			imgadv.setRefcityname(dty.getDistrictname());
		}
		User guser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		imgadv.setCreateop(guser.getUsername());
		Date currentTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		int index = uploadFileName.lastIndexOf(".");
		String newString = uploadFileName.substring(index - 1);
		String filename = dateString + newString;
		String url = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/upload");
		System.out.println("url-------------:" + url);
		File target = new File(url, filename);
		// 如果文件已经存在，则删除原有文件
		if (target.exists()) {
			target.delete();
		}
		// 复制文件
		try {
			FileUtils.copyFile(getUpload(), target, false);// 不删除上一次上传
			imgadv.setImgadvername(filename);
			imgadv.setImgadverurl("upload/" + filename);
			imgadverService.create(imgadv);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=UTF-8");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print("<script>alert('添加失败！')</script>");
			out.flush();
			return ERROR;
		}
	}

	public String deleteImgadv() throws IOException {
		try {
			/*
			 * Imgadver adv = new Imgadver(); adv.setImgadverid(adverid);
			 */
			Imgadver adv = imgadverService.getimgadverById(adverid);
			String url = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/upload");
			String filename = adv.getImgadvername();
			File target = new File(url, filename);
			imgadverService.delete(adv);
			if (target.exists()) {
				target.delete();
				System.out.println("文件已存在，将被覆盖！");
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=UTF-8");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print("<script>alert('删除失败！')</script>");
			out.flush();
			return ERROR;
		}
	}

	public String showActive() {
		List<Imgactive> actives = imgactiveService.findAll();
		ServletActionContext.getRequest().getSession()
				.setAttribute("actives", actives);
		return SUCCESS;
	}

	public String saveImgactive() throws IOException {
		if (shopkey != null || activeimg != null || activeimgFileName != null
				|| active != null) {
			String[] skey = shopkey.split(",");
			List<Integer> shopids = new ArrayList<Integer>();
			if (skey.length > 0) {
				for (int i = 0; i < skey.length; i++) {
					shopids.add(Integer.valueOf(skey[i].trim()));
				}
			}
			Calendar cal = Calendar.getInstance();// 获取当前时间
			Date currentDate = cal.getTime();
			active.setActivecreatedate(currentDate);
			cal.add(Calendar.DAY_OF_MONTH, +expday);// 到期时间
			active.setActiveexpiredate(cal.getTime());
			User guser = (User) ServletActionContext.getRequest().getSession()
					.getAttribute("user");
			active.setCreateusername(guser.getUsername());
			imgactiveService.saveActive(activeimg, activeimgFileName, active,
					shopids);
			return SUCCESS;
		} else {
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=UTF-8");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print("<script>alert('添加失败！')</script>");
			out.flush();
			return ERROR;
		}
	}

	public String deleteActive() {
		if (imgactiveid == 0) {
			return ERROR;
		} else {
			imgactiveService.deleteActiveByActiveId(imgactiveid);
			return SUCCESS;
		}
	}

	public String getShopkey() {
		return shopkey;
	}

	public void setShopkey(String shopkey) {
		this.shopkey = shopkey;
	}
}
