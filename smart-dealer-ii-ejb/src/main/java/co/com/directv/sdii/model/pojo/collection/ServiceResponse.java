package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.vo.ServiceVO;

public class ServiceResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3080761005685805675L;

	private List<Service> services;
	private List<ServiceVO> servicesVO;
	
	public List<Service> getServices() {
		return services;
	}
	public void setServices(List<Service> services) {
		this.services = services;
	}
	public List<ServiceVO> getServicesVO() {
		return servicesVO;
	}
	public void setServicesVO(List<ServiceVO> servicesVO) {
		this.servicesVO = servicesVO;
	}
}
