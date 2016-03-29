package com.zyl.centre.dao;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Activeshop;

@Repository("activeshopDao")
public class ActiveshopDao extends HibernateDao<Activeshop> implements
		IActiveshopDao {
	public ActiveshopDao() {
		super();
		setClazz(Activeshop.class);
		setLog(LogFactory.getLog(DistrictDao.class));
	}
}
