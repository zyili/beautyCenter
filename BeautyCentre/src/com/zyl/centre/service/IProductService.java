package com.zyl.centre.service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Product;

public interface IProductService extends IOperations<Product> {
	public Product findOneById(int prodid);

	Product findOneByName(String name);
}
