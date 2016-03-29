package com.zyl.centre.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zyl.centre.common.utils.TokenUtils;
import com.zyl.centre.common.utils.CommonUtils;
import com.zyl.centre.entity.Area;
import com.zyl.centre.entity.Shop;
import com.zyl.centre.entity.Token;
import com.zyl.centre.entity.User;
import com.zyl.centre.service.*;

public class LoginAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private String token;
	private Log log = LogFactory.getLog(LoginAction.class);
	@Resource(name = "userservice")
	private IUserService userservice;

	@Resource(name = "tokenService")
	private ITokenService tokenService;

	@Resource(name = "shopService")
	public IShopService shopService;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String execute() throws Exception {

		return SUCCESS;
	}

	public void login() throws IOException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			if (user == null || user.getUsername() == null
					|| user.getPassword() == null) {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			} else {
				User getuser = userservice.findOneByPass(user.getUsername(),
						user.getPassword());
				if (getuser != null) {
					Token token = tokenService.getTokenByUserId(getuser
							.getUserid());
					Calendar cal = Calendar.getInstance();// 获取当前日期
					Date currentDate = cal.getTime();
					if (token != null) {

						if (currentDate.before(token.getExpiredatetime())) {
							CommonUtils.setSessionMap(getuser.getUserid(),
									token.getTokencode());
							reMap.put(CommonUtils.RESULTMESS,
									CommonUtils.SUCCESS);
							reMap.put("token", token.getTokencode());
							reMap.put("userid", getuser.getUserid());

						} else {
							/*
							 * token过期
							 */
							String tokencode = getTokenCode();
							Token newtoken = new Token();
							newtoken.setUserid(getuser.getUserid());
							newtoken.setTokencode(tokencode);
							cal.add(Calendar.DAY_OF_MONTH, +30);// 获取30天后时间
							newtoken.setCreatedatetime(currentDate);
							newtoken.setExpiredatetime(cal.getTime());
							tokenService.createTokenCode(newtoken);
							CommonUtils.setSessionMap(getuser.getUserid(),
									newtoken.getTokencode());
							reMap.put(CommonUtils.RESULTMESS,
									CommonUtils.SUCCESS);
							reMap.put("token", newtoken.getTokencode());
							reMap.put("userid", getuser.getUserid());
							reMap.put("usertype", getuser.getType());
						}
					} else {
						/*
						 * 没有查询到token
						 */
						String tokencode = getTokenCode();
						Token newtoken = new Token();
						newtoken.setUserid(getuser.getUserid());
						newtoken.setTokencode(tokencode);
						cal.add(Calendar.DAY_OF_MONTH, +30);// 获取30天后时间
						newtoken.setCreatedatetime(currentDate);
						newtoken.setExpiredatetime(cal.getTime());
						tokenService.createTokenCode(newtoken);
						CommonUtils.setSessionMap(getuser.getUserid(),
								newtoken.getTokencode());
						reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
						reMap.put("token", newtoken.getTokencode());
						reMap.put("userid", getuser.getUserid());
					}
					if (!getuser.getShops().isEmpty()) {
						reMap.put("shopstate", getuser.getShops().iterator()
								.next().getState());
					} else {
						reMap.put("shopstate", 0);
					}

				} else {
					reMap.put(CommonUtils.RESULTMESS, CommonUtils.ERRORUSER);
				}
			}
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void weblogin() throws IOException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (user == null || user.getUsername() == null
				|| user.getPassword() == null) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
		} else {
			User getuser = userservice.findOneByPass(user.getUsername(),
					user.getPassword());
			if (getuser != null) {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.SUCCESS);
				reMap.put("userid", getuser.getUserid());
				reMap.put("phone", getuser.getPhone());
			} else {
				reMap.put(CommonUtils.RESULTMESS, CommonUtils.ERRORUSER);
			}
		}
		CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
	}

	/*
	 * 未登录/登录异常处理
	 */
	public void errorLogin() throws IOException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
		CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
	}

	public void unlogin() throws IOException {
		int reFlage = CommonUtils.ERROR;
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (token == null) {
			reMap.put(CommonUtils.RESULTMESS, CommonUtils.PARAMERROR);
			CommonUtils.toJson(ServletActionContext.getResponse(), reMap);
			return;
		}
		Map<String, Object> map = CommonUtils.getsetSessionMap();// 获取session的token信息
		String tokenCode = this.token;
		if (map != null) {

			if (tokenCode.equals(map.get("tokenCode").toString())) {
				/*
				 * 如果session中的token与传过来的token相同
				 * 则进行注销操作，删除session中的内容和删除用户在数据库中保存的token信息
				 */
				ActionContext.getContext().getSession().remove("sessionMap");
				Token token = new Token();
				token.setUserid(Integer.parseInt(map.get("userid").toString()));
				token.setTokencode(tokenCode);
				tokenService.delete(token);
				reFlage = CommonUtils.SUCCESS;
			}
		} else {
			try {
				Token gettoken = tokenService.findOneByCode(tokenCode);
				if (gettoken != null) {
					/*
					 * 如果数据库中的token与传过来的token相同
					 * 则进行注销操作，删除session中的内容和删除用户在数据库中保存的token信息
					 */
					ActionContext.getContext().getSession()
							.remove("sessionMap");
					tokenService.delete(gettoken);
					reFlage = CommonUtils.SUCCESS;
				} else {
					/*
					 * 如果数据库中的token与传过来的token不同 表示tokenCode错误
					 */
					reFlage = CommonUtils.ERRORTOKEN;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		reMap.put(CommonUtils.RESULTMESS, reFlage);
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
