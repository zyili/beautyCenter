package com.zyl.centre.dao;

import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Imgadver;

public interface IImgadverDao extends IOperations<Imgadver> {
	List<Imgadver> getimgadverByCity(int cityid);

	Imgadver getimgadverById(int id);
}
