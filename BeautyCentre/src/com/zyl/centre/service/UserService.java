package com.zyl.centre.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.common.utils.TokenUtils;
import com.zyl.centre.dao.IAreaDao;
import com.zyl.centre.dao.IImgsrcDao;
import com.zyl.centre.dao.IOrderDao;
import com.zyl.centre.dao.IServiceDao;
import com.zyl.centre.dao.IServiceordrelDao;
import com.zyl.centre.dao.IShopDao;
import com.zyl.centre.dao.ITokenDao;
import com.zyl.centre.dao.IUserDao;
import com.zyl.centre.entity.Area;
import com.zyl.centre.entity.Imgsrc;
import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.Serviceordrel;
import com.zyl.centre.entity.ServiceordrelId;
import com.zyl.centre.entity.Shop;
import com.zyl.centre.entity.Token;
import com.zyl.centre.entity.User;

@Service("userservice")
public class UserService extends AbstractService<User> implements IUserService {
	@Resource(name = "usersDao")
	private IUserDao dao;

	@Resource(name = "tokenDao")
	private ITokenDao tokendao;

	@Resource(name = "serviceDao")
	private IServiceDao m_dao;

	@Resource(name = "orderDao")
	private IOrderDao ord_dao;

	@Resource(name = "serviceordrelDao")
	private IServiceordrelDao rel_dao;

	@Resource(name = "areaDao")
	private IAreaDao a_dao;

	@Resource(name = "shopDao")
	private IShopDao shop_dao;

	@Resource(name = "imgsrcDao")
	private IImgsrcDao imgdao;

	public UserService() {
		super();
	}

	@Override
	protected IOperations<User> getDao() {
		return this.dao;
	}

	protected void setDao(IUserDao dao) {
		this.dao = dao;
	}

	@Override
	public boolean checkByName(String username) {
		// TODO Auto-generated method stub
		return dao.checkByName(username);
	}

	@Override
	public User findOneByPass(String username, String pass) {
		// TODO Auto-generated method stub
		return dao.findOneByPass(username, pass);
	}

	@Override
	public int GetUserIDByName(String username, String password) {
		// TODO Auto-generated method stub
		return dao.GetUserIDByName(username, password);
	}

	@Override
	public void createUser(User user, String tokenCode) {
		dao.create(user);
		Token token = new Token();
		token.setUserid(user.getUserid());
		token.setTokencode(tokenCode);
		Calendar cal = Calendar.getInstance();
		Date currentDate = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, +30);
		token.setCreatedatetime(currentDate);
		token.setExpiredatetime(cal.getTime());
		tokendao.createTokenCode(token);
	}

	@Override
	public User findUserByUserid(int id) {
		// TODO Auto-generated method stub
		return dao.findUserByUserid(id);
	}

	@Override
	public Order bookService(int uerid, int serviceid, int number,
			String ordphone) {
		// TODO Auto-generated method stub
		com.zyl.centre.entity.Service serv = m_dao.getServiceByid(serviceid);
		User user = findUserByUserid(uerid);
		Order ord = new Order();
		ord.setUser(user);
		ord.setCreatetime(new Date());
		ord.setNumber(number);
		ord.setSumprice(number * serv.getPrice());
		ord.setSubmitusername(user.getUsername());
		ord.setState(2);// 订单状态预约中
		ord.setOrdphone(ordphone);
		ord_dao.create(ord);

		Serviceordrel ordrel = new Serviceordrel();
		ordrel.setDate(new Date());
		ordrel.setOrder(ord);
		ordrel.setId(new ServiceordrelId(ord.getOrderid(), serv.getServiceid(),
				0));// 订购服务设置shopid=0
		rel_dao.create(ordrel);
		return ord;
	}

	@Override
	public Order bookShop(int uerid, int shopid, String ordphone) {
		// TODO Auto-generated method stub
		User user = findUserByUserid(uerid);
		Order ord = new Order();
		ord.setUser(user);
		ord.setCreatetime(new Date());
		ord.setNumber(0);
		ord.setSumprice(0);
		ord.setSubmitusername(user.getUsername());
		ord.setState(2);// 订单状态预约中
		ord.setOrdphone(ordphone);
		ord_dao.create(ord);

		Serviceordrel ordrel = new Serviceordrel();
		ordrel.setDate(new Date());
		ordrel.setOrder(ord);
		ordrel.setId(new ServiceordrelId(ord.getOrderid(), 0, shopid));// 订购服务设置shopid=0
		rel_dao.create(ordrel);
		return ord;
	}

	@Override
	public Token createShopUser(User user) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();// 获取当前时间
		Date currentDate = cal.getTime();
		user.setCreatetime(currentDate);
		dao.create(user);
		Area a = a_dao.GetByName("鼓楼区", "南京");
		if (a == null) {
			a = a_dao.findAll().get(0);
		}
		Shop shop = new Shop();
		shop.setArea(a);
		shop.setState(0);
		shop.setIshotshop(0);
		shop.setShopname("shop of " + user.getUsername());
		shop.setUser(user);
		shop.setOwnusername(user.getUsername());
		shop_dao.create(shop);
		Token token = new Token();
		token.setUserid(user.getUserid());
		token.setTokencode(getTokenCode());
		cal.add(Calendar.DAY_OF_MONTH, +30);// 获取30天后时间
		token.setCreatedatetime(currentDate);
		token.setExpiredatetime(cal.getTime());
		tokendao.createTokenCode(token);
		return token;
	}

	@Override
	public List<User> findUserByPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		return dao.findUserByPage(page, pageSize);
	}

	@Override
	public List<User> findUserByNameOrId(String nameorid) {
		// TODO Auto-generated method stub
		return dao.findUserByNameOrId(nameorid);
	}

	public String getTokenCode() {
		String tokencode = TokenUtils.generateValue();
		if (tokendao.findOneByCode(tokencode) != null) {
			getTokenCode();
		}
		return tokencode;
	}

	@Override
	public int countBooksByUserId(int uid) {
		// TODO Auto-generated method stub
		return dao.countBooksByUserId(uid);
	}

	@Override
	public List<Order> getBookService(int useid, int ordstate, int pagesize,
			int page) {
		// TODO Auto-generated method stub
		return dao.getBookService(useid, ordstate, pagesize, page);
	}

	@Override
	public List<Order> getBookShop(int useid, int ordstate, int pagesize,
			int page) {
		// TODO Auto-generated method stub
		return dao.getBookShop(useid, ordstate, pagesize, page);
	}

	@Override
	public int getBookServiceCount(int useid, int ordstate) {
		// TODO Auto-generated method stub
		return dao.getBookServiceCount(useid, ordstate);
	}

	@Override
	public int getBookShopCount(int useid, int ordstate) {
		// TODO Auto-generated method stub
		return dao.getBookShopCount(useid, ordstate);
	}

	@Override
	public void createWebUser(User user, File uimg, String uimgFileName) {
		// TODO Auto-generated method stub
		dao.create(user);
		if (uimg != null) {
			Date currentTime = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = formatter.format(currentTime);
			int index = uimgFileName.lastIndexOf(".");
			String newString = uimgFileName.substring(index - 1);
			String filename = dateString + newString;
			String url = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/upload");
			
			File target = new File(url, filename);
			// 如果文件已经存在，则删除原有文件
			if (target.exists()) {
				target.delete();
			}
			try {
				FileUtils.copyFile(uimg, target, false);// 不删除上一次上传
				Imgsrc img_temp = new Imgsrc();
				img_temp.setUrl("upload/" + filename);
				img_temp.setUser(user);
				img_temp.setImgname(filename);
				imgdao.create(img_temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
