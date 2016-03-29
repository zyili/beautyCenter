package com.zyl.centre.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.dao.IReviewDao;
import com.zyl.centre.entity.Review;

@Service("reviewService")
public class ReviewService extends AbstractService<Review> implements
		IReviewService {

	@Resource(name = "reviewDao")
	private IReviewDao dao;

	@Override
	protected IOperations<Review> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	public int findReviewsCountByShopId(int shopid) {
		// TODO Auto-generated method stub
		return dao.findReviewsCountByShopId(shopid);
	}

	@Override
	public List<Review> findReviewsByShopId(int shopid, int pagesize, int page) {
		// TODO Auto-generated method stub
		return dao.findReviewsByShopId(shopid, pagesize, page);
	}

	@Override
	public Review findReviewById(int reviewid) {
		// TODO Auto-generated method stub
		return dao.findReviewById(reviewid);
	}

	@Override
	public int findReviewsCount() {
		// TODO Auto-generated method stub
		return dao.findReviewsCount();
	}

	@Override
	public List<Review> findReviews(int pagesize, int page) {
		// TODO Auto-generated method stub
		return dao.findReviews(pagesize, page);
	}

}
