package co.com.directv.sdii.facade.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceTypeVO;

@Local
public interface ServiceTypesFacadeBeanLocal {
	public List<ServiceTypeVO> getAllServiceType() throws BusinessException; 
}
