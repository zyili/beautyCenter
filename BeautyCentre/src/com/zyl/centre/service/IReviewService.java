package com.zyl.centre.service;

import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Review;

public interface IReviewService extends IOperations<Review> {
	int findReviewsCountByShopId(int shopid);

	List<Review> findReviewsByShopId(int shopid, int pagesize, int page);

	Review findReviewById(int reviewid);

	int findReviewsCount();

	List<Review> findReviews(int pagesize, int page);
}
