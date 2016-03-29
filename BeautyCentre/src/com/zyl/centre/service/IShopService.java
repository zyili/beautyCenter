package com.zyl.centre.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.District;
import com.zyl.centre.entity.Order;
import com.zyl.centre.entity.Shop;

public interface IShopService extends IOperations<Shop> {

	public Shop getByUid(String userid);

	public Shop getByShopid(int id);

	public List<Order> getOrdersByShopid(int id);

	public Shop getShopByServid(int servid);

	public List<Shop> listShopInfo(int page, int pageSize);

	List<Shop> findShopByState(List<Integer> st, int page, int pageSize);

	List<Shop> findShopByNameOrId(String nameorid);

	List<District> findDistricts();

	List<Shop> getHotShops(int cityid);

	int findShopByState(List<Integer> st);

	int getNormalShopsNum();

	List<Shop> getShopsByAreaType(String city, String area, String shopname,
			int productid, List<Integer> prodtypeid, int page, int pagesize,
			String orderby, int rank);

	int getShopsCountByAreaType(String cityid, String areaid, String shopname,
			int productid, List<Integer> prodtypeid);

	void saveOrUpdateShop(Shop dbshop, List<File> files,
			List<String> filesFileName) throws IOException;

	List<Shop> getShopInfoByOrdId(int ordid);

	List<Shop> getShopInfoByDistance(double lng, double lat);

	public void updateShopName(String shopname, int shopid);

	Shop getByShopidCityid(int newshopid, int districtid);

	List<Shop> getShopsInfoByActiveID(int activeid);

}
