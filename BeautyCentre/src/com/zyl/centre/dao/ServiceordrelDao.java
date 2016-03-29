package com.zyl.centre.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Serviceordrel;

@Repository("serviceordrelDao")
public class ServiceordrelDao extends HibernateDao<Serviceordrel> implements
		IServiceordrelDao {
	public ServiceordrelDao() {
		super();
		setClazz(Serviceordrel.class);
		setLog(LogFactory.getLog(ServiceordrelDao.class));
	}

	@Override
	public Serviceordrel findServiceordrelByordId(int ordid) {
		// TODO Auto-generated method stub
		try {
			String sql = "from Serviceordrel where orderid='" + ordid + "'";
			@SuppressWarnings("unchecked")
			List<Serviceordrel> serelvs = getCurrentSession().createQuery(sql)
					.list();
			log.debug("get successful");
			if (!serelvs.isEmpty()) {
				return serelvs.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}
}
