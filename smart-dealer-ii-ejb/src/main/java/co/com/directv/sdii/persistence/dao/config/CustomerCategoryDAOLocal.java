package co.com.directv.sdii.persistence.dao.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerCategory;
import co.com.directv.sdii.model.vo.CustomerCategoryVO;

@Local
public interface CustomerCategoryDAOLocal {

	public void createCustomerCategory(CustomerCategory ct)throws DAOServiceException, DAOSQLException ;
	public void updateCustomerCategory(CustomerCategory ct)throws DAOServiceException, DAOSQLException ;
	public List<CustomerCategoryVO> getAllCustomerCategory()throws DAOServiceException, DAOSQLException ;
	public List<CustomerCategoryVO> getCustomerCategoryForDealerConf(List<String> customerCategoryDealerConfList)throws DAOServiceException, DAOSQLException ;
	
	
	public CustomerCategoryVO getCustomerCategoryById(Long id)throws DAOServiceException, DAOSQLException ;
	public CustomerCategoryVO getCustomerCategoryByCode(String code)throws DAOServiceException, DAOSQLException ;
	public void deleteCustomerCategory(CustomerCategory ct)throws DAOServiceException, DAOSQLException ;
	public CustomerCategory getCustomerCategoryByCustomerClassCode(String customerClassCode) throws DAOServiceException, DAOSQLException;
	
}
