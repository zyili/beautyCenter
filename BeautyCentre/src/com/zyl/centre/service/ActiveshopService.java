package com.zyl.centre.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.dao.IActiveshopDao;
import com.zyl.centre.entity.Activeshop;

@Service("activeshopService")
public class ActiveshopService extends AbstractService<Activeshop> implements
		IActiveshopService {

	@Resource(name = "activeshopDao")
	private IActiveshopDao dao;

	@Override
	protected IOperations<Activeshop> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
