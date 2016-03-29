package com.zyl.centre.dao;

import java.util.List;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.entity.Complaint;

public interface IComplaintDao extends IOperations<Complaint> {
	Complaint findComplaintByID(int compid);

	int findDealCompCount();

	int findnoDealCompCount();

	List<Complaint> findDealComp(int pagesize, int page);

	List<Complaint> findnoDealComp(int pagesize, int page);

}
