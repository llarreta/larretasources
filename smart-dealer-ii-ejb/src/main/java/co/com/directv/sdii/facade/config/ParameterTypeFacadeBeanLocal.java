package co.com.directv.sdii.facade.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ParameterTypeVO;

@Local
public interface ParameterTypeFacadeBeanLocal {
	public List<ParameterTypeVO> getAllParametersTypes() throws BusinessException ; 
}
