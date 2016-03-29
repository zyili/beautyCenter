package com.zyl.centre.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Service;

public interface IServiceService extends IOperations<Service> {
	List<Service> GetServiceByOrdid(int ordid);

	List<Service> getServByAreaType(String city, String area, int productid,
			List<Integer> prodtypeid);

	void deleteService(Service service);

	void DeleteTypeRelByid(int id);

	void UpdateTypeRel(int service_id, int product_id, List<Integer> prodtypeid);

	List<Service> getServicesByShopid(int shop);

	Service getServiceByid(int id);

	List<Service> getServiceInfoByOrdId(int ordid);

	com.zyl.centre.entity.Service saveOrUpdateService(
			com.zyl.centre.entity.Service serv, List<File> files,
			List<String> filesFileName, String status, List<Integer> prodtypeid)
			throws IOException;

	com.zyl.centre.entity.Service UpdateService(
			com.zyl.centre.entity.Service serv, List<File> files,
			List<String> filesFileName, String status, List<Integer> prodtypeid)
			throws IOException;

	com.zyl.centre.entity.Service CreateService(
			com.zyl.centre.entity.Service serv, List<File> files,
			List<String> filesFileName, String status, List<Integer> prodtypeid)
			throws IOException;

	int getServsCountByShop(String nameorid);

	List<Service> getServsByShop(String nameorid, int page, int pageSize);

	List<Service> getServsByWebShopId(int shopid);
}
