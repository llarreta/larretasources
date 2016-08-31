package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;

/**
 * Esta interfaz define las operaciones de negocio
 * asociadas a la entidad de ServiceCategory
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 *
 */
@Local
public interface ServiceCategoryBusinessBeanLocal {
	
	public List<ServiceCategoryVO> getAllServiceCategoryByTypeId(Long id) throws BusinessException;
	
	/**
     * Obtiene un servicecategory con el código especificado
     * @param serviceCategoryCode
     * @return - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public ServiceCategoryVO getServiceCategoryByCode(String serviceCategoryCode)
			throws BusinessException;
	
	/**
     * Obtiene un servicecategoryvo con el service code
     * @param serviceCode - String
     * @return ServiceCategoryVO
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public ServiceCategoryVO getServiceCategoryByServiceCode(String serviceCode) throws BusinessException;
	
}
