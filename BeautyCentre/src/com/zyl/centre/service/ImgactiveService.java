package com.zyl.centre.service;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.dao.IActiveshopDao;
import com.zyl.centre.dao.IImgactiveDao;
import com.zyl.centre.dao.IShopDao;
import com.zyl.centre.entity.Activeshop;
import com.zyl.centre.entity.ActiveshopId;
import com.zyl.centre.entity.Imgactive;
import com.zyl.centre.entity.Shop;
import com.zyl.centre.entity.User;

@Service("imgactiveService")
public class ImgactiveService extends AbstractService<Imgactive> implements
		IImgactiveService {
	@Resource(name = "imgactiveDao")
	private IImgactiveDao dao;

	@Resource(name = "activeshopDao")
	private IActiveshopDao actshopdao;

	@Resource(name = "shopDao")
	private IShopDao shopdao;

	@Override
	protected IOperations<Imgactive> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	public List<Imgactive> getImgactiveByShopid(int shopid) {
		// TODO Auto-generated method stub
		return dao.getImgactiveByShopid(shopid);
	}

	@Override
	public void saveActive(File file, String filenames, Imgactive active,
			List<Integer> shopids) {
		// TODO Auto-generated method stub
		int n = shopids.size();
		Date currentTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		int index = filenames.lastIndexOf(".");
		String newString = filenames.substring(index - 1);
		String filename = dateString + newString;
		String url = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/upload");
		File target = new File(url, filename);
		// 如果文件已经存在，则删除原有文件
		if (target.exists()) {
			target.delete();
		}
		// 复制文件
		try {
			FileUtils.copyFile(file, target, false);// 不删除上一次上传
			active.setActiveimgname(filename);
			active.setActiveimgurl("upload/" + filename);
			dao.create(active);
			for (int i = 0; i < n; i++) {
				Shop gshop = shopdao.getByShopid(shopids.get(i));
				if (gshop != null) {

					ActiveshopId activeid = new ActiveshopId(
							active.getImgactiveid(), gshop.getShopid());
					Activeshop actshop = new Activeshop(activeid, gshop, active);
					// actshop.setId(activeid);
					// actshop.setImgactive(active);
					actshop.setAvtivename(active.getActivename());
					// actshop.setShop(gshop);
					actshop.setShopname(gshop.getShopname());
					actshopdao.create(actshop);
				}
			}
			// imgadverService.create(imgadv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteActiveByActiveId(int actid) {
		// TODO Auto-generated method stub
		dao.deleteActiveByActiveId(actid);
	}

	@Override
	public Imgactive getActiveInfoByID(int id) {
		// TODO Auto-generated method stub
		return dao.getActiveInfoByID(id);
	}

	@Override
	public List<Imgactive> getNowActives() {
		// TODO Auto-generated method stub
		return dao.getNowActives();
	}

}
