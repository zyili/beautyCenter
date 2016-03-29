package com.zyl.centre.service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.District;

public interface IDistrictService extends IOperations<District> {
	District findById(int id);
}
