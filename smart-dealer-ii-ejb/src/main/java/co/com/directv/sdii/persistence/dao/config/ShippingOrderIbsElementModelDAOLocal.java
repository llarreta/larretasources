package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ShippingOrderIbsElementModel;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 19/06/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface ShippingOrderIbsElementModelDAOLocal {
	
	 /**
     * Crea una ShippingOrderIbsElementModel en el sistema
     * @param obj - ShippingOrderIbsElementModel
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createShippingOrderIbsElementModel(ShippingOrderIbsElementModel obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un ShippingOrderIbsElementModel con el id especificado
     * @param id - Long
     * @return - ShippingOrderIbsElementModel
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ShippingOrderIbsElementModel getShippingOrderIbsElementModelByID(Long id) throws DAOServiceException, DAOSQLException;
    
}
