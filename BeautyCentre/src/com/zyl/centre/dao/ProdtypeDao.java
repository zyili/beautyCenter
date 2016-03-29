package com.zyl.centre.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Prodtype;

@Repository("prodtypeDao")
public class ProdtypeDao extends HibernateDao<Prodtype> implements IProdtypeDao {

	public ProdtypeDao() {
		super();
		setClazz(Prodtype.class);
		setLog(LogFactory.getLog(ProdtypeDao.class));
	}

	@Override
	public List<Prodtype> findTypesByProdName(int page, int pagesize, int prdid) {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT pt.*  FROM `prodtype` AS pt WHERE pt.productid = '"
					+ prdid + "'";
			SQLQuery squery = getCurrentSession().createSQLQuery(sql);
			squery.setMaxResults(pagesize); // 每页最多显示几条
			squery.setFirstResult((page - 1) * pagesize);// 每页从第几条记录开始
			squery.addEntity("pt", Prodtype.class);
			@SuppressWarnings("unchecked")
			List<Prodtype> pts = squery.list();
			return pts;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public int typeCount(int prdid) {
		// TODO Auto-generated method stub
		Long count = (Long) getCurrentSession()
				.createQuery(
						"select count(*) from Prodtype where productid='"
								+ prdid + "'").uniqueResult();
		return new Long(count).intValue();
	}

	@Override
	public Prodtype findTypeByTypeid(int typeid) {
		// TODO Auto-generated method stub
		try {
			String sql = "from Prodtype where prodtypeid='" + typeid + "' ";
			@SuppressWarnings("unchecked")
			List<Prodtype> types = getCurrentSession().createQuery(sql).list();
			log.debug("get successful");
			if (!types.isEmpty()) {
				return types.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public boolean checkTypeName(String typename, int prodid) {
		// TODO Auto-generated method stub
		String sql = "FROM Prodtype  WHERE productid = '" + prodid
				+ "' AND prodtypename = '" + typename + "' ";
		@SuppressWarnings("unchecked")
		List<Prodtype> types = getCurrentSession().createQuery(sql).list();
		if (types.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public List<Prodtype> findTypesByProdId(int prdid) {
		// TODO Auto-generated method stub
		String sql = "FROM Prodtype  WHERE productid = '" + prdid + "'";
		@SuppressWarnings("unchecked")
		List<Prodtype> types = getCurrentSession().createQuery(sql).list();
		return types;
	}
}
