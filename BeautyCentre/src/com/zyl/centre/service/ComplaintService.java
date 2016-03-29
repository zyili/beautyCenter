package com.zyl.centre.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.dao.IComplaintDao;
import com.zyl.centre.entity.Complaint;

@Service("complaintService")
public class ComplaintService extends AbstractService<Complaint> implements
		IComplaintService {

	@Resource(name = "complaintDao")
	private IComplaintDao dao;

	@Override
	protected IOperations<Complaint> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	public Complaint findComplaintByID(int compid) {
		// TODO Auto-generated method stub
		return dao.findComplaintByID(compid);
	}

	@Override
	public int findDealCompCount() {
		// TODO Auto-generated method stub
		return dao.findDealCompCount();
	}

	@Override
	public int findnoDealCompCount() {
		// TODO Auto-generated method stub
		return dao.findnoDealCompCount();
	}

	@Override
	public List<Complaint> findDealComp(int pagesize, int page) {
		// TODO Auto-generated method stub
		return dao.findDealComp(pagesize, page);
	}

	@Override
	public List<Complaint> findnoDealComp(int pagesize, int page) {
		// TODO Auto-generated method stub
		return dao.findnoDealComp(pagesize, page);
	}

}
