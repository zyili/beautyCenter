package com.zyl.centre.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.dao.IImgadverDao;
import com.zyl.centre.entity.Imgadver;

@Service("imgadverService")
public class ImgadverService extends AbstractService<Imgadver> implements IImgadverService{

	@Resource(name = "imgadverDao")
	private IImgadverDao dao;
	
	@Override
	protected IOperations<Imgadver> getDao() {
		// TODO Auto-generated method stub
		return this.dao;
	}

	@Override
	public List<Imgadver> getimgadverByCity(int cityid) {
		// TODO Auto-generated method stub
		return dao.getimgadverByCity(cityid);
	}

	@Override
	public Imgadver getimgadverById(int id) {
		// TODO Auto-generated method stub
		return dao.getimgadverById(id);
	}

}
