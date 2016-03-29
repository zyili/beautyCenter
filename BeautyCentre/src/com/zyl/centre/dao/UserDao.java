package com.zyl.centre.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.User;

@Repository("usersDao")
public class UserDao extends HibernateDao<User> implements IUserDao {

	public UserDao() {
		super();
		setClazz(User.class);
		setLog(LogFactory.getLog(UserDao.class));
	}

	@Override
	public User findOneByPass(String username, String pass) {
		// TODO Auto-generated method stub
		log.debug("getting User instance with username:" + username + " pass:"
				+ pass);
		try {
			String sql = "from User where username='" + username
					+ "' and password='" + pass + "'";
			@SuppressWarnings("unchecked")
			List<User> users = getCurrentSession().createQuery(sql).list();
			log.debug("get successful");
			if (!users.isEmpty()) {
				return users.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	public int GetUserIDByName(String username, String password) {
		System.out.println("GetUserIDByName" + username);
		String sql = "from User where username='" + username
				+ "' and password='" + password + "'";
		try {
			@SuppressWarnings("unchecked")
			List<User> users = getCurrentSession().createQuery(sql).list();
			if (!users.isEmpty()) {
				return users.get(0).getUserid();
			}
			return -1;
		} catch (RuntimeException e) {
			log.error("GetUserIDByName failed", e);
			throw e;
		}
	};

	@Override
	public boolean checkByName(String username) {
		// TODO Auto-generated method stub
		log.debug("checking User instance with username:" + username);
		try {
			String sql = "from User where username='" + username + "' ";
			@SuppressWarnings("unchecked")
			List<User> users = getCurrentSession().createQuery(sql).list();
			log.debug("check successful");
			if (users.isEmpty()) {
				return false;
			} else {
				return true;
			}

		} catch (RuntimeException re) {
			log.error("check  failed", re);
			throw re;
		}
	}

	@Override
	public User findUserByUserid(int id) {
		// TODO Auto-generated method stub
		log.debug("getting User instance with id:" + id);
		try {
			String sql = "from User where userid='" + id + "' ";
			@SuppressWarnings("unchecked")
			List<User> users = getCurrentSession().createQuery(sql).list();
			log.debug("check successful");
			if (!users.isEmpty()) {
				return users.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("check  failed", re);
			throw re;
		}
	}

	/**
	 * pageSize为每页显示的记录数 page为当前显示的页
	 */
	@Override
	public List<User> findUserByPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(
				"from User order by userid DESC");
		query.setMaxResults(pageSize); // 每页最多显示几条
		query.setFirstResult((page - 1) * pageSize); // 每页从第几条记录开始
		@SuppressWarnings("unchecked")
		List<User> users = query.list();
		return users;
	}

	@Override
	public List<User> findUserByNameOrId(String nameorid) {
		// TODO Auto-generated method stub
		try {
			String sql = "from User where username LIKE '%" + nameorid
					+ "%' OR userid ='" + nameorid + "' ";
			@SuppressWarnings("unchecked")
			List<User> users = getCurrentSession().createQuery(sql).list();
			return users;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public int countBooksByUserId(int uid) {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT COUNT(orderid) FROM Order WHERE submituserid='"
					+ uid + "'";
			Long count = (Long) getCurrentSession().createQuery(sql)
					.uniqueResult();
			return new Long(count).intValue();
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Order> getBookService(int useid, int ordstate, int pagesize,
			int page) {
		// TODO Auto-generated method stub
		String sql = "";
		if (ordstate != -1) {
			sql = "SELECT orde.* FROM `order` AS orde,`serviceordrel` AS ordrel WHERE orde.`orderid` = ordrel.`orderid` AND ordrel.`shopid` = 0 AND orde.`submituserid` ='"
					+ useid + "' AND orde.`state`='" + ordstate + "'";
		} else {
			sql = "SELECT orde.* FROM `order` AS orde,`serviceordrel` AS ordrel WHERE orde.`orderid` = ordrel.`orderid` AND ordrel.`shopid` = 0 AND orde.`submituserid` ='"
					+ useid + "'";
		}
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.setMaxResults(pagesize); // 每页最多显示几条
		query.setFirstResult((page - 1) * pagesize); // 每页从第几条记录开始
		query.addEntity(Order.class);
		@SuppressWarnings("unchecked")
		List<Order> ords = query.list();
		return ords;
	}

	@Override
	public List<Order> getBookShop(int useid, int ordstate, int pagesize,
			int page) {
		// TODO Auto-generated method stub
		String sql = "";
		if (ordstate != -1) {
			sql = "SELECT orde.* FROM `order` AS orde,`serviceordrel` AS ordrel WHERE orde.`orderid` = ordrel.`orderid` AND ordrel.`serviceid` = 0 AND orde.`submituserid` = '"
					+ useid + "' AND orde.`state`='" + ordstate + "'";
		} else {
			sql = "SELECT orde.* FROM `order` AS orde,`serviceordrel` AS ordrel WHERE orde.`orderid` = ordrel.`orderid` AND ordrel.`serviceid` = 0 AND orde.`submituserid` = '"
					+ useid + "' ";
		}
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.setMaxResults(pagesize); // 每页最多显示几条
		query.setFirstResult((page - 1) * pagesize); // 每页从第几条记录开始
		query.addEntity(Order.class);
		@SuppressWarnings("unchecked")
		List<Order> ords = query.list();
		return ords;
	}

	@Override
	public int getBookServiceCount(int useid, int ordstate) {
		// TODO Auto-generated method stub
		String sql = "";
		if (ordstate != -1) {
			sql = "SELECT count(orde.orderid) FROM `order` AS orde,`serviceordrel` AS ordrel WHERE orde.`orderid` = ordrel.`orderid` AND ordrel.`shopid` = 0 AND orde.`submituserid` ='"
					+ useid + "' AND orde.`state`='" + ordstate + "'";
		} else {
			sql = "SELECT count(orde.orderid) FROM `order` AS orde,`serviceordrel` AS ordrel WHERE orde.`orderid` = ordrel.`orderid` AND ordrel.`shopid` = 0 AND orde.`submituserid` ='"
					+ useid + "'";
		}
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		Object count = query.uniqueResult();
		return Integer.parseInt(count.toString());
	}

	@Override
	public int getBookShopCount(int useid, int ordstate) {
		// TODO Auto-generated method stub
		String sql = "";
		if (ordstate != -1) {
			sql = "SELECT count(orde.orderid) FROM `order` AS orde,`serviceordrel` AS ordrel WHERE orde.`orderid` = ordrel.`orderid` AND ordrel.`serviceid` = 0 AND orde.`submituserid` = '"
					+ useid + "' AND orde.`state`='" + ordstate + "'";
		} else {
			sql = "SELECT count(orde.orderid) FROM `order` AS orde,`serviceordrel` AS ordrel WHERE orde.`orderid` = ordrel.`orderid` AND ordrel.`serviceid` = 0 AND orde.`submituserid` = '"
					+ useid + "' ";
		}
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		Object count = query.uniqueResult();
		return Integer.parseInt(count.toString());
	}
}
