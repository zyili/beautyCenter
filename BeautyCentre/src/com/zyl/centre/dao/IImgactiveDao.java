package com.zyl.centre.dao;

import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Imgactive;

public interface IImgactiveDao extends IOperations<Imgactive> {
	List<Imgactive> getImgactiveByShopid(int shopid);

	void deleteActiveByActiveId(int actid);

	Imgactive getActiveInfoByID(int id);

	List<Imgactive> getNowActives();
}
