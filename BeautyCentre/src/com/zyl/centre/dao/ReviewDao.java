package com.zyl.centre.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Review;

@Repository("reviewDao")
public class ReviewDao extends HibernateDao<Review> implements IReviewDao {
	public ReviewDao() {
		super();
		setClazz(Review.class);
		setLog(LogFactory.getLog(ProductDao.class));
	}

	@Override
	public int findReviewsCountByShopId(int shopid) {
		// TODO Auto-generated method stub

		String sql = "SELECT COUNT(*) FROM  `review` AS rev WHERE rev.`shopid`='"
				+ shopid + "'";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		Object count = query.uniqueResult();
		return Integer.parseInt(count.toString());
	}

	@Override
	public List<Review> findReviewsByShopId(int shopid, int pagesize, int page) {
		// TODO Auto-generated method stub
		String sql = "SELECT  rev.* FROM  `review` AS rev WHERE rev.`shopid`='"
				+ shopid + "' ORDER BY rev.`reviewid` DESC ";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.setMaxResults(pagesize); // 每页最多显示几条
		query.setFirstResult((page - 1) * pagesize); // 每页从第几条记录开始
		query.addEntity(Review.class);
		@SuppressWarnings("unchecked")
		List<Review> revs = query.list();
		return revs;
	}

	@Override
	public Review findReviewById(int reviewid) {
		// TODO Auto-generated method stub
		try {
			String sql = "from Review where reviewid='" + reviewid + "'";
			@SuppressWarnings("unchecked")
			List<Review> revws = getCurrentSession().createQuery(sql).list();
			log.debug("get successful");
			if (!revws.isEmpty()) {
				return revws.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			log.error("get  failed", re);
			throw re;
		}
	}

	@Override
	public int findReviewsCount() {
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) FROM  `review` AS rev";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		Object count = query.uniqueResult();
		return Integer.parseInt(count.toString());
	}

	@Override
	public List<Review> findReviews(int pagesize, int page) {
		// TODO Auto-generated method stub
		String sql = "SELECT  rev.* FROM  `review` AS rev ORDER BY rev.`reviewid` DESC";
		org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.setMaxResults(pagesize); // 每页最多显示几条
		query.setFirstResult((page - 1) * pagesize); // 每页从第几条记录开始
		query.addEntity(Review.class);
		@SuppressWarnings("unchecked")
		List<Review> revs = query.list();
		return revs;
	}
}
