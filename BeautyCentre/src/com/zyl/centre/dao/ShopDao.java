package com.zyl.centre.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.Shop;

@Repository("shopDao")
public class ShopDao extends HibernateDao<Shop> implements IShopDao {

	public ShopDao() {
		super();
		setClazz(Shop.class);
		setLog(LogFactory.getLog(ShopDao.class));
	}

	@Override
	public Shop getByUid(String userid) {
		Integer id = Integer.valueOf(userid);
		try {
			String sql = "from Shop where createuserid='" + id + "'";
			@SuppressWarnings("unchecked")
			List<Shop> shops = getCurrentSession().createQuery(sql).list();
			log.debug("get successful");
			if (!shops.isEmpty()) {
				return shops.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public Shop getByShopid(int id) {
		// TODO Auto-generated method stub
		log.debug("getting Shop instance with id:" + id);
		try {
			String sql = "from Shop where shopid='" + id + "' and state <>0 ";
			@SuppressWarnings("unchecked")
			List<Shop> shops = getCurrentSession().createQuery(sql).list();
			log.debug("get successful");
			if (!shops.isEmpty() && shops != null) {
				return shops.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Order> getOrdersByShopid(int id) {
		// TODO Auto-generated method stub
		log.debug("getting orders instance with shopid:" + id);
		try {
			String sql = "SELECT d.*  FROM shop AS a,service AS b, serviceordrel AS c,`order` AS d WHERE a.`shopid` = b.`shopid` AND  a.`state` NOT IN(0,5) AND b.`serviceid` = c.`serviceid` AND c.`orderid` = d.`orderid` AND a.`shopid` = '"
					+ id + "' ORDER BY d.`orderid` DESC";
			@SuppressWarnings("unchecked")
			List<Order> orders = (List<Order>) getCurrentSession()
					.createSQLQuery(sql).addEntity(Order.class).list();
			return orders;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	/*
	 * @Override public List<Shop> getHotShopsByCity(String city) { // TODO
	 * Auto-generated method stub log.debug("getting getHotShops  with city:" +
	 * city); try { String sql =
	 * "SELECT s.* FROM `shop` AS s,(SELECT c.`ordcount`,s.`shopid` FROM `shopcity` AS s,`shopordcunt` AS c WHERE s.`shopid` = c.`shopid` AND s.`city` = '"
	 * + city +
	 * "' ORDER BY c.`ordcount` DESC, c.`orddate` DESC LIMIT 5) AS t WHERE s.`shopid` = t.`shopid`  "
	 * ;
	 * 
	 * @SuppressWarnings("unchecked") List<Shop> shops = (List<Shop>)
	 * getCurrentSession() .createSQLQuery(sql).addEntity("s",
	 * Shop.class).list(); return shops; } catch (RuntimeException re) {
	 * log.error("get  failed", re); throw re; } }
	 */
	@Override
	public List<Shop> listShopInfo(int page, int pageSize) {
		// TODO Auto-generated method stub
		try {
			String sql = "from Shop WHERE  state<>0";
			Query query = getCurrentSession().createQuery(sql);
			query.setMaxResults(pageSize); // 每页最多显示几条
			query.setFirstResult((page - 1) * pageSize); // 每页从第几条记录开始
			@SuppressWarnings("unchecked")
			List<Shop> shops = query.list();
			return shops;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Shop> findShopByState(List<Integer> st, int page, int pageSize) {
		try {
			String sql = "from Shop where state ";
			int size = st.size();
			switch (size) {
			case 1:
				sql = sql + "= '" + st.get(0) + "'";
				break;
			case 2:
				sql = sql + "in( '" + st.get(0) + "','" + st.get(1) + "')";
				break;
			case 3:
				sql = sql + "in( '" + st.get(0) + "','" + st.get(1) + "','"
						+ st.get(2) + "')";
				break;
			}
			sql = sql + "  ORDER BY shopid  DESC";
			System.out.println("findShopByState----SQL" + sql);
			Query query = getCurrentSession().createQuery(sql);
			query.setMaxResults(pageSize); // 每页最多显示几条
			query.setFirstResult((page - 1) * pageSize); // 每页从第几条记录开始
			@SuppressWarnings("unchecked")
			List<Shop> shops = query.list();
			return shops;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public int findShopByState(List<Integer> st) {
		try {
			String sql = "select count(*) from Shop where state ";
			int size = st.size();
			switch (size) {
			case 1:
				sql = sql + "= '" + st.get(0) + "'";
				break;
			case 2:
				sql = sql + "in( '" + st.get(0) + "','" + st.get(1) + "')";
				break;
			case 3:
				sql = sql + "in( '" + st.get(0) + "','" + st.get(1) + "','"
						+ st.get(2) + "')";
				break;
			}

			Long count = (Long) getCurrentSession().createQuery(sql)
					.uniqueResult();
			return new Long(count).intValue();
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Shop> findShopByNameOrId(String nameorid) {
		// TODO Auto-generated method stub
		try {
			String sql = "from Shop where  shopname LIKE '%" + nameorid
					+ "%' OR shopid ='" + nameorid + "'  and state NOT IN(0,5)";
			@SuppressWarnings("unchecked")
			List<Shop> shops = getCurrentSession().createQuery(sql).list();
			return shops;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Shop> getHotShops(int cityid) {
		// TODO Auto-generated method stub
		log.debug("getting hotShops instance ");
		try {
			String sql = "SELECT shop.* FROM `shop` AS shop,`area` AS ar,`district` AS dist WHERE shop.`ishotshop`='1' AND shop.`state` NOT IN(0,5) AND shop.`areaid`=ar.`areaid` AND ar.`districtid`=dist.`districtid` AND dist.`districtid`='"
					+ cityid + "' ORDER BY shop.`shopid` DESC";
			@SuppressWarnings("unchecked")
			// List<Shop> shops =
			// getCurrentSession().createQuery(sql).list();
			List<Shop> shops = (List<Shop>) getCurrentSession()
					.createSQLQuery(sql).addEntity(Shop.class).list();
			log.debug("get successful");
			return shops;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public int getNormalShopsNum() {
		// TODO Auto-generated method stub
		log.debug("getting hotShops instance ");
		try {
			String sql = "select count(*) from Shop  WHERE  state NOT IN(0,5)";
			Long count = (Long) getCurrentSession().createQuery(sql)
					.uniqueResult();
			return new Long(count).intValue();
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Shop> getShopsByAreaType(String cityid, String areaid,
			String shopname, int productid, List<Integer> prodtypeid, int page,
			int pagesize, String orderby, int rank) {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT  shop.* FROM`shop` AS shop,`area` AS a,`district` AS d WHERE a.`areaid` = shop.`areaid` AND shop.`state` NOT IN(0,5) AND a.`districtid` = d.`districtid` AND d.`districtid` = '"
					+ cityid + "' ";
			if (areaid != null) {
				sql = sql + " AND a.`areaid` = '" + areaid + "' ";
			}
			if (shopname != null) {
				sql = sql + " AND shop.`shopname` LIKE '%" + shopname + "%'";
			}
			if (productid != 0 || prodtypeid.size() > 0) {
				if (prodtypeid.size() > 0) {
					if (productid == 0) {
						sql = sql
								+ "AND shop.`shopid` IN (SELECT ser.`shopid` FROM`service` AS ser WHERE ser.`serviceid` IN (SELECT pt.`serviceid` FROM`prodtyperel` AS pt  WHERE  pt.`prodtypeid` IN ";
						StringBuffer strsql = new StringBuffer(sql);
						strsql.append('(');
						int n = prodtypeid.size();
						for (int i = 0; i < n; i++) {
							if (i > 0) {
								strsql.append(',');
							}
							strsql.append(prodtypeid.get(i));
						}
						strsql.append(')');
						strsql.append(')');
						strsql.append(')');
						sql = strsql.toString();
					} else {
						sql = sql
								+ "AND shop.`shopid` IN (SELECT ser.`shopid` FROM`service` AS ser WHERE ser.`serviceid` IN (SELECT pt.`serviceid` FROM`prodtyperel` AS pt  WHERE pt.`productid` = '"
								+ productid + "'  AND pt.`prodtypeid` IN ";
						StringBuffer strsql = new StringBuffer(sql);
						strsql.append('(');
						int n = prodtypeid.size();
						for (int i = 0; i < n; i++) {
							if (i > 0) {
								strsql.append(',');
							}
							strsql.append(prodtypeid.get(i));
						}
						strsql.append(')');
						strsql.append(')');
						strsql.append(')');
						sql = strsql.toString();
					}
				} else {
					sql = sql
							+ "AND shop.`shopid` IN (SELECT ser.`shopid` FROM`service` AS ser WHERE ser.`serviceid` IN (SELECT pt.`serviceid` FROM`prodtyperel` AS pt  WHERE pt.`productid` = '"
							+ productid + "'";
					sql = sql + " ))";
				}
			}
			if (orderby.equals("composite")) {
				sql = sql + "ORDER BY shop.`shoplevel`";
			} else if (orderby.equals("perconsum")) {
				sql = sql + "ORDER BY shop.`perconsum`";
			} else {
				sql = sql + "ORDER BY shop.`score`";
			}
			if (rank == 1) {
				sql = sql + " DESC";
			} else {
				sql = sql + " ASC";
			}
			System.out.println("根据地区获取店铺信息SQL语句：" + sql);
			org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(
					sql);
			query.setMaxResults(pagesize); // 每页最多显示几条
			query.setFirstResult((page - 1) * pagesize); // 每页从第几条记录开始
			query.addEntity(Shop.class);
			@SuppressWarnings("unchecked")
			List<Shop> shops = query.list();
			return shops;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public int getShopsCountByAreaType(String cityid, String areaid,
			String shopname, int productid, List<Integer> prodtypeid) {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT  count(shop.shopid) FROM `shop` AS shop,`area` AS a,`district` AS d WHERE a.`areaid` = shop.`areaid` AND shop.`state` NOT IN(0,5) AND  a.`districtid` = d.`districtid` AND d.`districtid` = '"
					+ cityid + "' ";
			if (areaid != null) {
				sql = sql + " AND a.`areaid` = '" + areaid + "' ";
			}
			if (shopname != null) {
				sql = sql + " AND shop.`shopname` LIKE '%" + shopname + "%'";
			}
			if (productid != 0 || prodtypeid.size() > 0) {
				if (prodtypeid.size() > 0) {
					if (productid == 0) {
						sql = sql
								+ "AND shop.`shopid` IN (SELECT ser.`shopid` FROM`service` AS ser WHERE ser.`serviceid` IN (SELECT pt.`serviceid` FROM`prodtyperel` AS pt  WHERE  pt.`prodtypeid` IN ";
						StringBuffer strsql = new StringBuffer(sql);
						strsql.append('(');
						int n = prodtypeid.size();
						for (int i = 0; i < n; i++) {
							if (i > 0) {
								strsql.append(',');
							}
							strsql.append(prodtypeid.get(i));
						}
						strsql.append(')');
						strsql.append(')');
						strsql.append(')');
						sql = strsql.toString();
					} else {
						sql = sql
								+ "AND shop.`shopid` IN (SELECT ser.`shopid` FROM`service` AS ser WHERE ser.`serviceid` IN (SELECT pt.`serviceid` FROM`prodtyperel` AS pt  WHERE pt.`productid` = '"
								+ productid + "'  AND pt.`prodtypeid` IN ";
						StringBuffer strsql = new StringBuffer(sql);
						strsql.append('(');
						int n = prodtypeid.size();
						for (int i = 0; i < n; i++) {
							if (i > 0) {
								strsql.append(',');
							}
							strsql.append(prodtypeid.get(i));
						}
						strsql.append(')');
						strsql.append(')');
						strsql.append(')');
						sql = strsql.toString();
					}
				} else {
					sql = sql
							+ "AND shop.`shopid` IN (SELECT ser.`shopid` FROM`service` AS ser WHERE ser.`serviceid` IN (SELECT pt.`serviceid` FROM`prodtyperel` AS pt  WHERE pt.`productid` = '"
							+ productid + "'";
					sql = sql + " ))";
				}
			}
			org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(
					sql);
			Object count = query.uniqueResult();
			return Integer.parseInt(count.toString());
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Shop> getShopInfoByOrdId(int ordid) {
		// TODO Auto-generated method stub
		String sql = "SELECT shop.* FROM `shop` AS shop ,`serviceordrel` AS sorl WHERE sorl.`shopid`=shop.`shopid` AND sorl.`orderid`='"
				+ ordid + "'";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.addEntity(Shop.class);
		@SuppressWarnings("unchecked")
		List<Shop> shops = query.list();
		if (shops.isEmpty()) {
			return null;
		} else {
			return shops;
		}
	}

	@Override
	public List<Shop> getShopInfoByDistance(double lng, double lat) {
		// TODO Auto-generated method stub
		String sql = "SELECT  shop.* FROM `shop` AS shop  WHERE shop.`lat` > 0  AND shop.`lng` > 0 and shop.`state` NOT IN(0,5) ORDER BY (POWER(MOD(ABS(lng - '"
				+ lng
				+ "'),360),2) + POWER(ABS(lat - '"
				+ lat
				+ "'),2)) LIMIT 30";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.addEntity(Shop.class);
		@SuppressWarnings("unchecked")
		List<Shop> shops = query.list();
		if (shops.isEmpty()) {
			return null;
		} else {
			return shops;
		}
	}

	@Override
	public void updateShopName(String shopname, int shopid) {
		try {
			String sql = "UPDATE `activeshop` SET shopname='" + shopname
					+ "' WHERE shopid='" + shopid + "'";
			Session session = getCurrentSession();
			session.createSQLQuery(sql).executeUpdate();
			sql = "UPDATE `complaint` SET shopname='" + shopname
					+ "' WHERE shopid='" + shopid + "'";
			session.createSQLQuery(sql).executeUpdate();
			sql = "UPDATE `service`  SET shopname='" + shopname
					+ "' WHERE shopid='" + shopid + "'";
			session.createSQLQuery(sql).executeUpdate();
			session.flush();
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public Shop getByShopidCityid(int newshopid, int districtid) {
		// TODO Auto-generated method stub
		log.debug("getting eidthotShop instance ");
		try {
			String sql = "SELECT shop.* FROM `shop` AS shop,`area` AS ar,`district` AS dist WHERE shop.`shopid`='"
					+ newshopid
					+ "' AND shop.`state` NOT IN(0,5) AND shop.`areaid`=ar.`areaid` AND ar.`districtid`=dist.`districtid` AND dist.`districtid`='"
					+ districtid + "'";
			
			System.out.println("根据cityid和shopid查询店铺信息"+sql);
			@SuppressWarnings("unchecked")
			List<Shop> shops = (List<Shop>) getCurrentSession()
					.createSQLQuery(sql).addEntity(Shop.class).list();
			log.debug("get successful");
			if (shops != null && shops.size() > 0) {
				return shops.get(0);
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Shop> getShopsInfoByActiveID(int activeid) {
		// TODO Auto-generated method stub
		String sql = "SELECT  shop.* FROM  `shop` AS shop, `activeshop` AS actshop  WHERE shop.`shopid`=actshop.`shopid` AND actshop.`imgactiveid`='"
				+ activeid + "'";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.addEntity(Shop.class);
		@SuppressWarnings("unchecked")
		List<Shop> shops = query.list();
		return shops;
	}
}
