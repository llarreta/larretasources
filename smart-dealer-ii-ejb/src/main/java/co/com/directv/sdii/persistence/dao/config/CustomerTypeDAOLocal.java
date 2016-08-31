package co.com.directv.sdii.persistence.dao.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.pojo.CustomerType;
import co.com.directv.sdii.model.vo.CustomerTypeVO;

@Local
public interface CustomerTypeDAOLocal {

	public void createCustomerType(CustomerType ct)throws DAOServiceException, DAOSQLException ;
	public void updateCustomerType(CustomerType ct)throws DAOServiceException, DAOSQLException ;
	public List<CustomerTypeVO> getAllCustomerType(Long countryId)throws DAOServiceException, DAOSQLException ;
	public CustomerTypeVO getCustomerTypeById(Long id)throws DAOServiceException, DAOSQLException ;
	public CustomerTypeVO getCustomerTypeByCode(String code,Long countryId)throws DAOServiceException, DAOSQLException ;
	public void deleteCustomerType(CustomerType ct)throws DAOServiceException, DAOSQLException ;
	
	//Req-0096 - Requerimiento Consolidado Asignador
	public List<CustomerClassType> getCustomerTypeByCategory(Long categoryId,Long countryId)throws DAOServiceException, DAOSQLException ;
	
}
