package co.com.directv.sdii.facade.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;

@Local
public interface ServiceCategoryFacadeBeanLocal {
	public List<ServiceCategoryVO> getAllServiceCategoryByTypeId(Long id) throws BusinessException;
	public ServiceCategoryVO getServiceCategoryByServiceCode(String serviceCode) throws BusinessException;
}
