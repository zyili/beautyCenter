package com.zyl.centre.dao;

import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Prodtype;

public interface IProdtypeDao extends IOperations<Prodtype> {
	public List<Prodtype> findTypesByProdName(int page, int pagesize,
			int prdid);

	public int typeCount(int prdid);

	public Prodtype findTypeByTypeid(int typeid);
	
	public boolean checkTypeName(String typename,int prodid);
	
	List<Prodtype> findTypesByProdId(int prodid);
}
