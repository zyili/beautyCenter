package com.zyl.centre.dao;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.zyl.centre.entity.Imgadver;

@Repository("imgadverDao")
public class ImgadverDao extends HibernateDao<Imgadver> implements IImgadverDao {

	public ImgadverDao() {
		super();
		setClazz(Imgadver.class);
		setLog(LogFactory.getLog(ImgadverDao.class));
	}

	@Override
	public List<Imgadver> getimgadverByCity(int cityid) {
		// TODO Auto-generated method stub
		String  sql = "from Imgadver where expiredate>=CURRENT_DATE() and refcityid='"+cityid+"'";
		try {
			@SuppressWarnings("unchecked")
			List<Imgadver> imgs = getCurrentSession().createQuery(sql).list();
			return imgs;
		} catch (RuntimeException e) {
			log.error("GetUserIDByName failed", e);
			throw e;
		}
	}

	@Override
	public Imgadver getimgadverById(int id) {
		// TODO Auto-generated method stub
		String  sql = "from Imgadver where imgadverid='"+id+"'";
		try {
			@SuppressWarnings("unchecked")
			List<Imgadver> imgs = getCurrentSession().createQuery(sql).list();
			if (!imgs.isEmpty()) {
				return imgs.get(0);
			}else
			{
				return null;
			}
		} catch (RuntimeException e) {
			log.error("GetUserIDByName failed", e);
			throw e;
		}
	}

}
