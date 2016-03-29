package com.zyl.centre.service;

import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Area;

public interface IAreaService extends IOperations<Area> {
	public Area GetByName(String areaname, String cityname);

	public String GetCityNameByid(Integer areaid);

	List<Area> getAreasByCityId(int cityid);
}
