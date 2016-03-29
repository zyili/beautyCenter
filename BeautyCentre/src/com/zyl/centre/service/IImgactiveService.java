package com.zyl.centre.service;

import java.io.File;
import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Imgactive;

public interface IImgactiveService extends IOperations<Imgactive> {
	List<Imgactive> getImgactiveByShopid(int shopid);

	void saveActive(File file, String filename, Imgactive active,
			List<Integer> shopids);

	void deleteActiveByActiveId(int actid);

	Imgactive getActiveInfoByID(int id);

	List<Imgactive> getNowActives();
}
