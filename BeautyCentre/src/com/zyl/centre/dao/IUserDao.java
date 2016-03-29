package com.zyl.centre.dao;

import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.User;

public interface IUserDao extends IOperations<User> {

	boolean checkByName(final String username);

	User findOneByPass(final String username, final String pass);

	int GetUserIDByName(final String username, final String password);

	User findUserByUserid(int id);

	List<User> findUserByPage(int page, int pageSize);

	List<User> findUserByNameOrId(String nameorid);

	int countBooksByUserId(int uid);

	List<Order> getBookService(int useid,int ordstate,int pagesize,int page);

	List<Order> getBookShop(int useid,int ordstate,int pagesize,int page);
	
	int getBookServiceCount(int useid,int ordstate);

	int getBookShopCount(int useid,int ordstate);
}
