package com.zyl.centre.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Area;

@Repository("areaDao")
public class AreaDao extends HibernateDao<Area> implements IAreaDao {
	public AreaDao() {
		super();
		setClazz(Area.class);
		setLog(LogFactory.getLog(AreaDao.class));
	}

	public Area GetByName(String areaname, String cityname) {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT Area.* FROM area AS Area,`district` AS d WHERE Area.`districtid`=d.`districtid` AND Area.`areaname`= '"
					+ areaname + "' AND d.`districtname`='" + cityname + "'";
			@SuppressWarnings("unchecked")
			List<Area> as = (List<Area>) getCurrentSession()
					.createSQLQuery(sql).addEntity("Area",Area.class).list();
			if(!as.isEmpty())
			{
				return as.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	public String GetCityNameByid(Integer areaid) {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "SELECT b.districtname city,a.areaname area FROM AREA AS a, district AS b WHERE a.districtid=b.districtid AND a.areaid ='"
					+ areaid + "'";
			org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(
					sql);
			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> listmap = query.list();
			if (listmap != null && !listmap.isEmpty()) {
				map = listmap.get(0);
				return map.get("city").toString();
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public List<Area> getAreasByCityId(int cityid) {
		// TODO Auto-generated method stub
		String sql="FROM Area where districtid='"+cityid+"'";
		@SuppressWarnings("unchecked")
		List<Area> areas = getCurrentSession().createQuery(sql).list();
		return areas;
	}

}
