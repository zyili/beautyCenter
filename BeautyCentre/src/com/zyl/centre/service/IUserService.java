package com.zyl.centre.service;

import java.io.File;
import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.Token;
import com.zyl.centre.entity.User;

public interface IUserService extends IOperations<User> {

	boolean checkByName(final String username);

	User findOneByPass(final String username, final String pass);

	int GetUserIDByName(String username, String password);

	void createUser(User user, String tokenCode);

	void createWebUser(User user, File uimg, String uimgFileName);

	User findUserByUserid(int id);

	Order bookService(int uerid, int serviceid, int number, String ordphone);

	Token createShopUser(User user);

	List<User> findUserByPage(int page, int pageSize);

	List<User> findUserByNameOrId(String nameorid);

	Order bookShop(int uerid, int shopid, String ordphone);

	int countBooksByUserId(int uid);

	List<Order> getBookService(int useid, int ordstate, int pagesize, int page);

	List<Order> getBookShop(int useid, int ordstate, int pagesize, int page);

	int getBookServiceCount(int useid, int ordstate);

	int getBookShopCount(int useid, int ordstate);
}
