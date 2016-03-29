package com.zyl.centre.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.dao.IDistrictDao;
import com.zyl.centre.entity.District;


@Service("districtService")
public class DistrictService  extends AbstractService<District> implements IDistrictService{

	@Resource(name = "districtDao")
	private IDistrictDao dao;
	@Override
	protected IOperations<District> getDao() {
		// TODO Auto-generated method stub
		return this.dao;
	}
	@Override
	public District findById(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

}
