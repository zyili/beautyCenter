package com.zyl.centre.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Complaint;

@Repository("complaintDao")
public class ComplaintDao extends HibernateDao<Complaint> implements
		IComplaintDao {
	public ComplaintDao() {
		super();
		setClazz(Complaint.class);
		setLog(LogFactory.getLog(DistrictDao.class));
	}

	@Override
	public Complaint findComplaintByID(int compid) {
		// TODO Auto-generated method stub
		try {
			String sql = "from Complaint where complaintid='" + compid + "'";
			@SuppressWarnings("unchecked")
			List<Complaint> comps = getCurrentSession().createQuery(sql).list();
			log.debug("get successful");
			if (!comps.isEmpty()) {
				return comps.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public int findDealCompCount() {
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) FROM  `complaint` AS comp WHERE comp.`state`=1";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		Object count = query.uniqueResult();
		return Integer.parseInt(count.toString());
	}

	@Override
	public int findnoDealCompCount() {
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) FROM  `complaint` AS comp WHERE comp.`state`=0";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		Object count = query.uniqueResult();
		return Integer.parseInt(count.toString());
	}

	@Override
	public List<Complaint> findDealComp(int pagesize, int page) {
		// TODO Auto-generated method stub
		String sql = "SELECT comp.* FROM  `complaint` AS comp WHERE comp.`state`=1 ORDER BY comp.`complaintid` DESC ";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.setMaxResults(pagesize); // 每页最多显示几条
		query.setFirstResult((page - 1) * pagesize); // 每页从第几条记录开始
		query.addEntity(Complaint.class);
		@SuppressWarnings("unchecked")
		List<Complaint> comps = query.list();
		return comps;
	}

	@Override
	public List<Complaint> findnoDealComp(int pagesize, int page) {
		// TODO Auto-generated method stub
		String sql = "SELECT comp.* FROM  `complaint` AS comp WHERE comp.`state`=0 ORDER BY comp.`complaintid` DESC ";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.setMaxResults(pagesize); // 每页最多显示几条
		query.setFirstResult((page - 1) * pagesize); // 每页从第几条记录开始
		query.addEntity(Complaint.class);
		@SuppressWarnings("unchecked")
		List<Complaint> comps = query.list();
		return comps;
	}
}
