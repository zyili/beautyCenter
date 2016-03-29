package com.zyl.centre.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.District;

@Repository("districtDao")
public class DistrictDao extends HibernateDao<District> implements IDistrictDao {
	public DistrictDao() {
		super();
		setClazz(District.class);
		setLog(LogFactory.getLog(DistrictDao.class));
	}

	@Override
	public District findById(int id) {
		// TODO Auto-generated method stub
		String sql = "FROM District where districtid='" + id + "'";
		@SuppressWarnings("unchecked")
		List<District> citys = getCurrentSession().createQuery(sql).list();
		if (citys.isEmpty()) {
			return null;
		} else {
			return citys.get(0);
		}
	}

}
