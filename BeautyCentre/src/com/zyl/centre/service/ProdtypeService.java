package com.zyl.centre.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.dao.IProdtypeDao;
import com.zyl.centre.entity.Prodtype;

@Service("prodtypeService")
public class ProdtypeService extends AbstractService<Prodtype> implements IProdtypeService{

	@Resource(name = "prodtypeDao")
	private IProdtypeDao dao;
	
	@Override
	protected IOperations<Prodtype> getDao() {
		// TODO Auto-generated method stub
		return this.dao;
	}

	@Override
	public List<Prodtype> findTypesByProdName(int page,int pagesize,int prdid) {
		// TODO Auto-generated method stub
		return dao.findTypesByProdName(page,pagesize,prdid);
	}

	@Override
	public int typeCount(int prdid) {
		// TODO Auto-generated method stub
		return dao.typeCount(prdid);
	}

	@Override
	public Prodtype findTypeByTypeid(int typeid) {
		// TODO Auto-generated method stub
		return dao.findTypeByTypeid(typeid);
	}

	@Override
	public boolean checkTypeName(String typename, int prodid) {
		// TODO Auto-generated method stub
		return dao.checkTypeName(typename, prodid);
	}

	@Override
	public List<Prodtype> findTypesByProdId(int prdid) {
		// TODO Auto-generated method stub
		return dao.findTypesByProdId(prdid);
	}

}
