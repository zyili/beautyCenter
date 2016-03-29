package com.zyl.centre.dao;

import java.util.List;
import java.util.Map;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Order;

public interface IOrderDao extends IOperations<Order> {
	public Order getOrdByid(int id);

	public List<Order> getOrdsByUserid(int userid);

	List<Order> getOrdsByServiceid(int serid);

	List<Map<String, Object>> listOrderInfo(int page, int pageSize);

	public int listOrderSize();

	public int queryOrderSize(String nameorordid);

	List<Map<String, Object>> queryOrderInfo(int page, int pageSize,
			String nameorordid);

	List<Order> getOrdInfoByOrdShopid(int shopid, int state);
}
