package com.zyl.centre.stage.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zyl.centre.entity.Complaint;
import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.Review;
import com.zyl.centre.entity.Shop;
import com.zyl.centre.entity.User;
import com.zyl.centre.service.IComplaintService;
import com.zyl.centre.service.IOrderService;
import com.zyl.centre.service.IReviewService;
import com.zyl.centre.service.IShopService;
import com.zyl.centre.service.IUserService;

public class statgeUser extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userid;
	public User userMessage;
	public List<Map<String, Object>> ordinfo = new ArrayList<Map<String, Object>>();
	public String nameorid;
	public String nameororid;
	public int orderid;
	public int reviewid;
	public int compid;
	public List<Shop> shopinfo = new ArrayList<Shop>();
	@Resource(name = "userservice")
	private IUserService userservice;

	@Resource(name = "orderService")
	private IOrderService orderservice;

	@Resource(name = "shopService")
	public IShopService shopService;

	@Resource(name = "reviewService")
	private IReviewService reviewService;

	@Resource(name = "complaintService")
	private IComplaintService complaintService;

	public int pageSize = 10;// 每页显示几条

	public int page = 1; // 默认当前页

	public int totalPage;// 总共多少页

	public int ordNum;// 总过多少条

	public int shoppage = 1;// listshop的默认当前页

	public int reviewpage = 1;// review的默认当前页
	public int ndcompage = 1;// 没有处理的投诉当前页
	public int dcompage = 1;// 已经处理的投诉当前页
	public String feedback;// 投诉处理结果

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public int getReviewpage() {
		return reviewpage;
	}

	public void setReviewpage(int reviewpage) {
		this.reviewpage = reviewpage;
	}

	public int getShoppage() {
		return shoppage;
	}

	public void setShoppage(int shoppage) {
		this.shoppage = shoppage;
	}

	public List<Map<String, Object>> getOrdinfo() {
		return ordinfo;
	}

	public void setOrdinfo(List<Map<String, Object>> ordinfo) {
		this.ordinfo = ordinfo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getOrdNum() {
		return ordNum;
	}

	public void setOrdNum(int ordNum) {
		this.ordNum = ordNum;
	}

	public int getCompid() {
		return compid;
	}

	public void setCompid(int compid) {
		this.compid = compid;
	}

	public int getReviewid() {
		return reviewid;
	}

	public void setReviewid(int reviewid) {
		this.reviewid = reviewid;
	}

	public User getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(User userMessage) {
		this.userMessage = userMessage;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getNdcompage() {
		return ndcompage;
	}

	public void setNdcompage(int ndcompage) {
		this.ndcompage = ndcompage;
	}

	public int getDcompage() {
		return dcompage;
	}

	public void setDcompage(int dcompage) {
		this.dcompage = dcompage;
	}

	public String getNameororid() {
		return nameororid;
	}

	public void setNameororid(String nameororid) {
		this.nameororid = nameororid;
	}

	public String getNameorid() {
		return nameorid;
	}

	public void setNameorid(String nameorid) {
		this.nameorid = nameorid;
	}

	public List<Shop> getShopinfo() {
		return shopinfo;
	}

	public void setShopinfo(List<Shop> shopinfo) {
		this.shopinfo = shopinfo;
	}

	public String findUserMessage() {
		System.out.println(userid);
		User user = userservice.findUserByUserid(userid);
		setUserMessage(user);
		ServletActionContext.getRequest().getSession()
				.setAttribute("userMessage", user);
		return SUCCESS;
	}

	public String modifyUserPass() {
		User user = userservice.findUserByUserid(userid);
		user.setPassword("888888");
		userservice.update(user);
		return SUCCESS;
	}

	public String listOrdInfo() {
		setOrdNum(orderservice.listOrderSize());
		if (ordNum % pageSize == 0) {
			totalPage = ordNum / pageSize;// 总共多少页
		} else {
			totalPage = ordNum / pageSize + 1;// 总共多少页
		}
		setOrdinfo(orderservice.listOrderInfo(page, pageSize));
		ServletActionContext.getRequest().getSession()
				.setAttribute("ordinfo", ordinfo);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("page", page);
		System.out.println("总页数：" + totalPage + "当前页：" + page);
		return SUCCESS;
	}

	public String listShopInfo() {
		setOrdNum(shopService.getNormalShopsNum());
		if (ordNum % pageSize == 0) {
			totalPage = ordNum / pageSize;// 总共多少页
		} else {
			totalPage = ordNum / pageSize + 1;// 总共多少页
		}
		setShopinfo(shopService.listShopInfo(shoppage, pageSize));
		ServletActionContext.getRequest().getSession()
				.setAttribute("shopsinfo", shopinfo);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("shoppage", shoppage);
		return SUCCESS;
	}

	public String queryUser() {
		List<User> users = userservice.findUserByNameOrId(nameorid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("allUsers", users);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", 1);
		ServletActionContext.getRequest().getSession().setAttribute("page", 1);
		return SUCCESS;
	}

	public String queryOderInfo() {
		setOrdNum(orderservice.queryOrderSize(nameororid));
		if (ordNum % pageSize == 0) {
			totalPage = ordNum / pageSize;// 总共多少页
		} else {
			totalPage = ordNum / pageSize + 1;// 总共多少页
		}
		setOrdinfo(orderservice.queryOrderInfo(page, pageSize, nameororid));
		ServletActionContext.getRequest().getSession()
				.setAttribute("ordinfo", ordinfo);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("page", page);
		return SUCCESS;
	}

	public String findOrdInfo() {
		Order order = orderservice.getOrdByid(orderid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("order", order);
		return SUCCESS;
	}

	public String listReviews() {
		int totalnum = reviewService.findReviewsCount();
		if (totalnum % pageSize == 0) {
			totalPage = totalnum / pageSize;// 总共多少页
		} else {
			totalPage = totalnum / pageSize + 1;// 总共多少页
		}
		List<Review> reviews = reviewService.findReviews(pageSize, reviewpage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("reviews", reviews);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("reviewpage", reviewpage);
		return SUCCESS;
	}

	public String deleteReviewByAdmin() throws IOException {
		Review drev = new Review();
		drev.setReviewid(reviewid);
		reviewService.delete(drev);
		reviewpage = (int) ServletActionContext.getRequest().getSession()
				.getAttribute("reviewpage");
		int totalnum = reviewService.findReviewsCount();
		if (totalnum % pageSize == 0) {
			totalPage = totalnum / pageSize;// 总共多少页
		} else {
			totalPage = totalnum / pageSize + 1;// 总共多少页
		}
		if (reviewpage > totalPage) {
			reviewpage = totalPage;
		}
		List<Review> reviews = reviewService.findReviews(pageSize, reviewpage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("reviews", reviews);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("reviewpage", reviewpage);
		return SUCCESS;
	}

	public String findDealComp() {
		int totalnum = complaintService.findDealCompCount();
		if (totalnum % pageSize == 0) {
			totalPage = totalnum / pageSize;// 总共多少页
		} else {
			totalPage = totalnum / pageSize + 1;// 总共多少页
		}
		;
		List<Complaint> comps = complaintService.findDealComp(pageSize,
				dcompage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("dcomps", comps);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("dcompage", dcompage);
		return SUCCESS;
	}

	public String findnoDealComp() {
		int totalnum = complaintService.findnoDealCompCount();
		if (totalnum % pageSize == 0) {
			totalPage = totalnum / pageSize;// 总共多少页
		} else {
			totalPage = totalnum / pageSize + 1;// 总共多少页
		}
		List<Complaint> comps = complaintService.findnoDealComp(pageSize,
				ndcompage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("ndcomps", comps);
		ServletActionContext.getRequest().getSession()
				.setAttribute("totalPage", totalPage);
		ServletActionContext.getRequest().getSession()
				.setAttribute("ndcompage", ndcompage);
		return SUCCESS;
	}

	public String dealComp() {
		Complaint comp = complaintService.findComplaintByID(compid);
		comp.setState(1);
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		Calendar cal = Calendar.getInstance();// 获取当前时间
		Date currentDate = cal.getTime();
		comp.setDealdatetime(currentDate);
		comp.setDealuserid(user.getUserid());
		comp.setDealusername(user.getUsername());
		comp.setFeedback(feedback);
		complaintService.update(comp);
		return SUCCESS;
	}

	public String getcompinfo() {
		Complaint comp = complaintService.findComplaintByID(compid);
		ServletActionContext.getRequest().getSession()
				.setAttribute("compinfo", comp);
		return SUCCESS;
	}

}
