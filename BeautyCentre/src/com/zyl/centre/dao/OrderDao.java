package com.zyl.centre.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Order;

@Repository("orderDao")
public class OrderDao extends HibernateDao<Order> implements IOrderDao {
	public OrderDao() {
		super();
		setClazz(Order.class);
		setLog(LogFactory.getLog(OrderDao.class));
	}

	@Override
	public Order getOrdByid(int id) {
		// TODO Auto-generated method stub
		try {
			String sql = "from Order where orderid='" + id + "'";
			@SuppressWarnings("unchecked")
			List<Order> ords = getCurrentSession().createQuery(sql).list();
			log.debug("get successful");
			if (!ords.isEmpty()) {
				return ords.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Order> getOrdsByUserid(int userid) {
		// TODO Auto-generated method stub
		try {
			String sql = "from Order where submituserid='" + userid + "'";
			@SuppressWarnings("unchecked")
			List<Order> ords = getCurrentSession().createQuery(sql).list();
			log.debug("get successful");
			if (!ords.isEmpty()) {
				return ords;
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Order> getOrdsByServiceid(int serid) {
		try {
			String sql = "SELECT orde.* FROM `order` AS orde WHERE orde.`orderid` IN(SELECT sero.`orderid` FROM `serviceordrel` AS sero WHERE sero.`serviceid`='"
					+ serid + "')";
			@SuppressWarnings("unchecked")
			List<Order> orders = (List<Order>) getCurrentSession()
					.createSQLQuery(sql).addEntity("orde", Order.class).list();
			return orders;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Map<String, Object>> listOrderInfo(int page, int pageSize) {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT * FROM `ordinfo`";
			org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(
					sql);
			query.setMaxResults(pageSize); // 每页最多显示几条
			query.setFirstResult((page - 1) * pageSize); // 每页从第几条记录开始
			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> orderinfo = query.list();
			return orderinfo;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public int listOrderSize() {
		try {
			String sql = "SELECT * FROM `ordinfo`";
			org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(
					sql);
			return query.list().size();
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Map<String, Object>> queryOrderInfo(int page, int pageSize,
			String nameorordid) {
		try {
			String sql = "SELECT * FROM `ordinfo`  AS of WHERE of.`orderid` = '"
					+ nameorordid
					+ "' OR of.`username` LIKE '%"
					+ nameorordid
					+ "%'";
			org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(
					sql);
			query.setMaxResults(pageSize); // 每页最多显示几条
			query.setFirstResult((page - 1) * pageSize); // 每页从第几条记录开始
			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> orderinfo = query.list();
			return orderinfo;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public int queryOrderSize(String nameorordid) {
		try {
			String sql = "SELECT * FROM `ordinfo` WHERE `orderid` = '"
					+ nameorordid + "' OR `username` LIKE '%" + nameorordid
					+ "%'";
			org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(
					sql);
			return query.list().size();
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Order> getOrdInfoByOrdShopid(int shopid, int state) {
		// TODO Auto-generated method stub
		try {
			String sql = "";
			if (state == -1) {
				sql = "SELECT orde.* FROM `order` AS orde,`serviceordrel` AS rel WHERE rel.`orderid`=orde.`orderid` AND  rel.`shopid`='"
						+ shopid + "' ORDER BY orde.`orderid` DESC ";
			} else {
				sql = "SELECT orde.* FROM `order` AS orde,`serviceordrel` AS rel WHERE rel.`orderid`=orde.`orderid` AND  rel.`shopid`='"
						+ shopid
						+ "' AND orde.`state`='"
						+ state
						+ "' ORDER BY orde.`orderid` DESC";
			}
			@SuppressWarnings("unchecked")
			List<Order> orders = (List<Order>) getCurrentSession()
					.createSQLQuery(sql).addEntity("orde", Order.class).list();
			return orders;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}
}
