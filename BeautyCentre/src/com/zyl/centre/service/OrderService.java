package com.zyl.centre.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.dao.IOrderDao;
import com.zyl.centre.entity.Order;

@Service("orderService")
public class OrderService extends AbstractService<Order> implements
		IOrderService {

	@Resource(name = "orderDao")
	private IOrderDao dao;

	@Override
	protected IOperations<Order> getDao() {
		// TODO Auto-generated method stub
		return this.dao;
	}

	@Override
	public Order getOrdByid(int id) {
		// TODO Auto-generated method stub
		return dao.getOrdByid(id);
	}

	@Override
	public List<Order> getOrdsByUserid(int userid) {
		// TODO Auto-generated method stub
		return dao.getOrdsByUserid(userid);
	}

	@Override
	public List<Order> getOrdsByServiceid(int serid) {
		// TODO Auto-generated method stub
		return dao.getOrdsByServiceid(serid);
	}

	@Override
	public List<Map<String, Object>> listOrderInfo(int page, int pageSize) {
		// TODO Auto-generated method stub
		return dao.listOrderInfo(page, pageSize);
	}

	@Override
	public int listOrderSize() {
		// TODO Auto-generated method stub
		return dao.listOrderSize();
	}

	@Override
	public int queryOrderSize(String nameorordid) {
		// TODO Auto-generated method stub
		return dao.queryOrderSize(nameorordid);
	}

	@Override
	public List<Map<String, Object>> queryOrderInfo(int page, int pageSize,
			String nameorordid) {
		// TODO Auto-generated method stub
		return dao.queryOrderInfo(page, pageSize, nameorordid);
	}

	@Override
	public List<Order> getOrdInfoByOrdShopid(int shopid, int state) {
		// TODO Auto-generated method stub
		return dao.getOrdInfoByOrdShopid(shopid, state);
	}
}
