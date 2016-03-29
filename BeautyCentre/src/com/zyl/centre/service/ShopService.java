package com.zyl.centre.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.common.utils.TimeString;
import com.zyl.centre.dao.IDistrictDao;
import com.zyl.centre.dao.IImgsrcDao;
import com.zyl.centre.dao.IServiceDao;
import com.zyl.centre.dao.IShopDao;
import com.zyl.centre.entity.District;
import com.zyl.centre.entity.Imgsrc;
import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.Shop;

@Service("shopService")
public class ShopService extends AbstractService<Shop> implements IShopService {

	@Resource(name = "shopDao")
	private IShopDao dao;

	@Resource(name = "serviceDao")
	private IServiceDao servdao;

	@Resource(name = "districtDao")
	private IDistrictDao distdao;

	@Resource(name = "imgsrcDao")
	private IImgsrcDao imgdao;

	public ShopService() {
		super();
	}

	@Override
	protected IOperations<Shop> getDao() {
		return this.dao;
	}

	public Shop getByUid(String userid) {
		return dao.getByUid(userid);
	}

	@Override
	public Shop getByShopid(int id) {
		// TODO Auto-generated method stub
		return dao.getByShopid(id);
	}

	@Override
	public List<Order> getOrdersByShopid(int id) {
		// TODO Auto-generated method stub
		return dao.getOrdersByShopid(id);
	}

	@Override
	public Shop getShopByServid(int servid) {
		// TODO Auto-generated method stub
		com.zyl.centre.entity.Service serv = servdao.getServiceByid(servid);
		return serv.getShop();
	}

	@Override
	public List<Shop> listShopInfo(int page, int pageSize) {
		// TODO Auto-generated method stub
		return dao.listShopInfo(page, pageSize);
	}

	@Override
	public List<Shop> findShopByState(List<Integer> st, int page, int pageSize) {
		// TODO Auto-generated method stub
		return dao.findShopByState(st, page, pageSize);
	}

	@Override
	public List<Shop> findShopByNameOrId(String nameorid) {
		// TODO Auto-generated method stub
		return dao.findShopByNameOrId(nameorid);
	}

	@Override
	public List<District> findDistricts() {
		// TODO Auto-generated method stub
		return distdao.findAll();
	}

	@Override
	public List<Shop> getHotShops(int cityid) {
		// TODO Auto-generated method stub
		return dao.getHotShops(cityid);
	}

	@Override
	public int findShopByState(List<Integer> st) {
		// TODO Auto-generated method stub
		return dao.findShopByState(st);
	}

	@Override
	public int getNormalShopsNum() {
		// TODO Auto-generated method stub
		return dao.getNormalShopsNum();
	}

	@Override
	public List<Shop> getShopsByAreaType(String city, String area,
			String shopname, int productid, List<Integer> prodtypeid, int page,
			int pagesize,String orderby,int rank) {
		// TODO Auto-generated method stub
		return dao.getShopsByAreaType(city, area, shopname, productid,
				prodtypeid, page, pagesize,orderby,rank);
	}

	@Override
	public int getShopsCountByAreaType(String cityid, String areaid,
			String shopname, int productid, List<Integer> prodtypeid) {
		// TODO Auto-generated method stub
		return dao.getShopsCountByAreaType(cityid, areaid, shopname, productid,
				prodtypeid);
	}

	@Override
	public void saveOrUpdateShop(Shop dbshop, List<File> files,
			List<String> filesFileName) {
		// TODO Auto-generated method stub
		TimeString time = new TimeString();
		String url = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/shop_upload");
		int state = dbshop.getState();
		List<Imgsrc> oldimgs = new ArrayList<Imgsrc>();
		if (state != 1) {
			oldimgs.addAll(dbshop.getImgsrcs());
		}
		dao.update(dbshop);
		if (files == null || 0 >= files.size() || filesFileName == null
				|| 0 >= filesFileName.size()) {
			return;
		} else {
			try {
				for (int i = 0; i < files.size(); i++) {
					System.out.println("进入保存图片：" + i);
					InputStream is = new FileInputStream(files.get(i));
					int index = filesFileName.get(i).lastIndexOf(".");
					String newString = filesFileName.get(i)
							.substring(index - 2);
					String filename = time.getTimeString() + newString;
					// String url = "shop_upload/" + filename;
					// File destFile = new File(root, url);
					File destFile = new File(url, filename);
					OutputStream os;
					os = new FileOutputStream(destFile);
					byte[] buffer = new byte[400];
					int length = 0;
					while ((length = is.read(buffer)) > 0) {
						os.write(buffer, 0, length);
					}
					is.close();
					os.close();
					Imgsrc img_temp = new Imgsrc();
					img_temp.setUrl("shop_upload/" + filename);
					img_temp.setShop(dbshop);
					img_temp.setImgname(filename);
					imgdao.create(img_temp);
				}
				if (state != 1)// 修改店铺
				{
					if (oldimgs.size() > 0) {
						for (int i = 0; i < oldimgs.size(); i++) {
							Imgsrc str = oldimgs.get(i);
							imgdao.delete(str);
							File target = new File(url, str.getImgname());
							// 如果文件已经存在，则删除原有文件
							if (target.exists()) {
								target.delete();
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public List<Shop> getShopInfoByOrdId(int ordid) {
		// TODO Auto-generated method stub
		return dao.getShopInfoByOrdId(ordid);
	}

	@Override
	public List<Shop> getShopInfoByDistance(double lng, double lat) {
		// TODO Auto-generated method stub
		return dao.getShopInfoByDistance(lng, lat);
	}

	@Override
	public void updateShopName(String shopname, int shopid) {
		// TODO Auto-generated method stub
		dao.updateShopName(shopname, shopid);
	}

	@Override
	public Shop getByShopidCityid(int newshopid, int districtid) {
		// TODO Auto-generated method stub
		return dao.getByShopidCityid(newshopid, districtid);
	}

	@Override
	public List<Shop> getShopsInfoByActiveID(int activeid) {
		// TODO Auto-generated method stub
		return dao.getShopsInfoByActiveID(activeid);
	}
}
