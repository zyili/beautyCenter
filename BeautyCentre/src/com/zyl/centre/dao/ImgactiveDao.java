package com.zyl.centre.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;

import com.zyl.centre.entity.Imgactive;
import com.zyl.centre.entity.Shop;

@Repository("imgactiveDao")
public class ImgactiveDao extends HibernateDao<Imgactive> implements
		IImgactiveDao {
	public ImgactiveDao() {
		super();
		setClazz(Imgactive.class);
		setLog(LogFactory.getLog(ImgadverDao.class));
	}

	@Override
	public List<Imgactive> getImgactiveByShopid(int shopid) {
		// TODO Auto-generated method stub
		String sql = "SELECT imga.* FROM `activeshop` AS active ,`imgactive` AS imga WHERE active.`imgactiveid`=imga.`imgactiveid` AND active.`shopid`='"
				+ shopid + "' AND imga.`activeexpiredate` >=CURRENT_DATE()";
		try {
			@SuppressWarnings("unchecked")
			List<Imgactive> imgs = getCurrentSession().createSQLQuery(sql)
					.addEntity(Imgactive.class).list();
			return imgs;
		} catch (RuntimeException e) {
			log.error("GetUserIDByName failed", e);
			throw e;
		}
	}

	@Override
	public void deleteActiveByActiveId(int actid) {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from `activeshop` as p where p.imgactiveid='"
					+ actid + "'";
			if (!getCurrentSession().createSQLQuery(sql).list().isEmpty()) {
				sql = "DELETE FROM Activeshop WHERE imgactiveid='" + actid
						+ "'";
				Query query = getCurrentSession().createQuery(sql);
				query.executeUpdate();
			}
			sql = "select * from `imgactive` as p where p.imgactiveid='"
					+ actid + "'";
			if (!getCurrentSession().createSQLQuery(sql).list().isEmpty()) {
				sql = "DELETE FROM Imgactive WHERE imgactiveid='" + actid + "'";
				Query aquery = getCurrentSession().createQuery(sql);
				aquery.executeUpdate();
			}
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public Imgactive getActiveInfoByID(int id) {
		// TODO Auto-generated method stub
		try {
			String sql = "from Imgactive where imgactiveid='" + id + "'";
			@SuppressWarnings("unchecked")
			List<Imgactive> actives = getCurrentSession().createQuery(sql)
					.list();
			log.debug("get successful");
			if (!actives.isEmpty()) {
				return actives.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Imgactive> getNowActives() {
		// TODO Auto-generated method stub
		try {
			String sql = "from Imgactive where activeexpiredate >CURDATE()";
			@SuppressWarnings("unchecked")
			List<Imgactive> actives = getCurrentSession().createQuery(sql)
					.list();
			log.debug("get successful");
			return actives;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}
}
