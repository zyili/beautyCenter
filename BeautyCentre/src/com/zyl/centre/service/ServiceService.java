package com.zyl.centre.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.zyl.centre.common.utils.IOperations;
import com.zyl.centre.common.utils.TimeString;
import com.zyl.centre.dao.IImgsrcDao;
import com.zyl.centre.dao.IServiceDao;
import com.zyl.centre.dao.IShopDao;
import com.zyl.centre.entity.Imgsrc;

@Service("serviceService")
public class ServiceService extends
		AbstractService<com.zyl.centre.entity.Service> implements
		IServiceService {

	@Resource(name = "serviceDao")
	private IServiceDao dao;

	@Resource(name = "imgsrcDao")
	private IImgsrcDao imgdao;

	@Resource(name = "shopDao")
	private IShopDao shopdao;

	public ServiceService() {
		super();
	}

	@Override
	protected IOperations<com.zyl.centre.entity.Service> getDao() {
		return this.dao;
	}

	@Override
	public List<com.zyl.centre.entity.Service> GetServiceByOrdid(int ordid) {
		// TODO Auto-generated method stub
		return dao.GetServiceByOrdid(ordid);
	}

	@Override
	public List<com.zyl.centre.entity.Service> getServByAreaType(String city,
			String area, int productid, List<Integer> prodtypeid) {
		// TODO Auto-generated method stub
		return dao.getServByAreaType(city, area, productid, prodtypeid);
	}

	public void DeleteTypeRelByid(int id) {
		dao.DeleteTypeRelByid(id);
	}

	public void UpdateTypeRel(int service_id, int product_id,
			List<Integer> prodtypeid) {
		dao.UpdateTypeRel(service_id, product_id, prodtypeid);
	}

	@Override
	public List<com.zyl.centre.entity.Service> getServicesByShopid(int shop) {
		// TODO Auto-generated method stub
		return dao.getServicesByShopid(shop);
	}

	@Override
	public com.zyl.centre.entity.Service getServiceByid(int id) {
		// TODO Auto-generated method stub
		return dao.getServiceByid(id);
	}

	@Override
	public void deleteService(com.zyl.centre.entity.Service service) {
		// TODO Auto-generated method stub
		List<Imgsrc> oldimgs = new ArrayList<Imgsrc>();
		String url = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/service_upload");
		oldimgs.addAll(service.getImgsrcs());
		imgdao.deleteImgByServiceid(service.getServiceid());
		dao.DeleteTypeRelByid(service.getServiceid());
		dao.deleteServiceById(service.getServiceid());
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

	@Override
	public List<com.zyl.centre.entity.Service> getServiceInfoByOrdId(int ordid) {
		// TODO Auto-generated method stub
		return dao.getServiceInfoByOrdId(ordid);
	}

	@Override
	public com.zyl.centre.entity.Service saveOrUpdateService(
			com.zyl.centre.entity.Service serv, List<File> files,
			List<String> filesFileName, String status, List<Integer> prodtypeid)
			throws IOException {
		TimeString time = new TimeString();
		String url = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/service_upload");
		dao.create(serv);
		List<Imgsrc> oldimgs = new ArrayList<Imgsrc>();
		if (status.equals("edit")) {
			oldimgs.addAll(serv.getImgsrcs());
		}
		if (!(files.size() <= 0 || files == null)) {
			for (int i = 0; i < files.size(); i++) {
				InputStream is = new FileInputStream(files.get(i));
				int index = filesFileName.get(i).lastIndexOf(".");
				String newString = filesFileName.get(i).substring(index - 2);
				String filename = time.getTimeString() + newString;
				// String url = "service_upload/" + filename;
				File destFile = new File(url, filename);
				OutputStream os = new FileOutputStream(destFile);
				byte[] buffer = new byte[400];
				int length = 0;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				is.close();
				os.close();
				Imgsrc img_temp = new Imgsrc();
				img_temp.setUrl("service_upload/" + filename);
				img_temp.setService(serv);
				img_temp.setImgname(filename);
				imgdao.create(img_temp);
			}
		}
		dao.DeleteTypeRelByid(serv.getServiceid());
		dao.UpdateTypeRel(serv.getServiceid(),
				serv.getProduct().getProductid(), prodtypeid);
		if (status.equals("edit"))// 修改服务
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
		return serv;
	}

	@Override
	public com.zyl.centre.entity.Service UpdateService(
			com.zyl.centre.entity.Service serv, List<File> files,
			List<String> filesFileName, String status, List<Integer> prodtypeid)
			throws IOException {
		System.out.println("进入更新服务service");
		TimeString time = new TimeString();
		String url = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/service_upload");
		dao.create(serv);
		List<Imgsrc> oldimgs = imgdao.getImagsByServiceid(serv.getServiceid());
		System.out.println("改服务管理的图片数量：" + serv.getImgsrcs().size());
		if (!(0 >= files.size() || files == null || filesFileName == null || 0 >= filesFileName
				.size())) {
			System.out.println("进入更新服务service中的图片处理");
			for (int i = 0; i < files.size(); i++) {
				InputStream is = new FileInputStream(files.get(i));
				int index = filesFileName.get(i).lastIndexOf(".");
				String newString = filesFileName.get(i).substring(index - 2);
				String filename = time.getTimeString() + newString;
				// String url = "service_upload/" + filename;
				File destFile = new File(url, filename);
				OutputStream os = new FileOutputStream(destFile);
				byte[] buffer = new byte[400];
				int length = 0;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				is.close();
				os.close();
				Imgsrc img_temp = new Imgsrc();
				img_temp.setUrl("service_upload/" + filename);
				System.out.println("图片名称：" + filename);
				img_temp.setService(serv);
				img_temp.setImgname(filename);
				imgdao.create(img_temp);
				System.out.println("图片ID：" + img_temp.getImgid());
			}
			if (oldimgs.size() > 0) {
				for (int i = 0; i < oldimgs.size(); i++) {
					Imgsrc str = oldimgs.get(i);
					System.out.println("进入更新服务service中的图片删除"
							+ oldimgs.get(i).getImgname());
					imgdao.delete(str);
					File target = new File(url, str.getImgname());
					// 如果文件已经存在，则删除原有文件
					if (target.exists()) {
						target.delete();
					}
				}
			}
		}
		System.out.println("进入更新服务service中的图片处理之后");
		dao.DeleteTypeRelByid(serv.getServiceid());
		System.out.println("####after delete=" + serv.getServiceid());
		dao.UpdateTypeRel(serv.getServiceid(),
				serv.getProduct().getProductid(), prodtypeid);
		List<com.zyl.centre.entity.Service> servs = dao
				.getServicesByShopid(serv.getShop().getShopid());
		int perconsum = 0;
		int n = servs.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				perconsum = perconsum + servs.get(i).getPrice();
			}
			perconsum = perconsum / n;
		}
		serv.getShop().setPerconsum(perconsum);
		shopdao.update(serv.getShop());
		return serv;
	}

	@Override
	public com.zyl.centre.entity.Service CreateService(
			com.zyl.centre.entity.Service serv, List<File> files,
			List<String> filesFileName, String status, List<Integer> prodtypeid)
			throws IOException {
		// TODO Auto-generated method stub
		TimeString time = new TimeString();
		String url = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/service_upload");
		dao.create(serv);
		if (!(files.size() <= 0 || files == null)) {
			for (int i = 0; i < files.size(); i++) {
				InputStream is = new FileInputStream(files.get(i));
				int index = filesFileName.get(i).lastIndexOf(".");
				String newString = filesFileName.get(i).substring(index - 2);
				String filename = time.getTimeString() + newString;
				// String url = "service_upload/" + filename;
				File destFile = new File(url, filename);
				OutputStream os = new FileOutputStream(destFile);
				byte[] buffer = new byte[400];
				int length = 0;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				is.close();
				os.close();
				Imgsrc img_temp = new Imgsrc();
				img_temp.setUrl("service_upload/" + filename);
				img_temp.setService(serv);
				img_temp.setImgname(filename);
				imgdao.create(img_temp);
			}
		}
		dao.DeleteTypeRelByid(serv.getServiceid());
		dao.UpdateTypeRel(serv.getServiceid(),
				serv.getProduct().getProductid(), prodtypeid);
		List<com.zyl.centre.entity.Service> servs = dao
				.getServicesByShopid(serv.getShop().getShopid());
		int perconsum = 0;
		int n = servs.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				perconsum = perconsum + servs.get(i).getPrice();

			}
			perconsum = perconsum / n;
		}
		serv.getShop().setPerconsum(perconsum);
		shopdao.update(serv.getShop());
		return serv;
	}

	@Override
	public int getServsCountByShop(String nameorid) {
		// TODO Auto-generated method stub
		return dao.getServsCountByShop(nameorid);
	}

	@Override
	public List<com.zyl.centre.entity.Service> getServsByShop(String nameorid,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		return dao.getServsByShop(nameorid, page, pageSize);
	}

	@Override
	public List<com.zyl.centre.entity.Service> getServsByWebShopId(int shopid) {
		// TODO Auto-generated method stub
		return dao.getServsByWebShopId(shopid);
	}
}
