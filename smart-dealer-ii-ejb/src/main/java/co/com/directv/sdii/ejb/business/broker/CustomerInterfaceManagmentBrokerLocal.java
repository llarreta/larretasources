package co.com.directv.sdii.ejb.business.broker;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.AddCustomerInquiryIBSDTO;
import co.com.directv.sdii.model.dto.CustomerInquiriesByCriteriaIBSDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.vo.IbsContactVO;

import com.directvla.schema.businessdomain.customer.customerinterfacemanagement.v1_0.CustomerInquiry;


/**
 * Define las operaciones del broker de servicios para el WS de contacts del cliente expuesto por DTV IBS
 * 
 * Fecha de Creación: 26/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.impl.CustomerInterfaceManagmentBrokerImpl
 */
@Local
public interface CustomerInterfaceManagmentBrokerLocal {

	/**
	 * Metodo: obtiene los contacts de un cliente en un rango de fechas.
	 * @param request
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<CustomerInquiry> getCustomerInquiriesByCriteria(CustomerInquiriesByCriteriaIBSDTO request) throws BusinessException;
	
	/**
	 * Metodo: pobla la informacion de una instacia del objeto IbsContactVO con la informacion de un objeto de ibs customerInquiry
	 * @param customerInquiry
	 * @param country
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public IbsContactVO populateIbsContactVO(CustomerInquiry customerInquiry,Country country) throws DAOServiceException, DAOSQLException, BusinessException;

	/**
	 * Metodo: genera un nuevo contacto en IBS.
	 * @param customerInquiry
	 * @param country
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void addCustomerInquiry(AddCustomerInquiryIBSDTO request) throws BusinessException;
	
}
