package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerConfCustomerType;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad DealerConfCustomerType
 * 
 * Fecha de Creacion: Jue 12, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerConfCustomerTypeDAOLocal {
    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * Crea una DealerConfCustomerType en el sistema
     * @param obj - DealerConfCustomerType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
   
    public void createDealerConfCustomerType(DealerConfCustomerType obj) throws DAOServiceException, DAOSQLException ;
    public int deleteDealerConfCustomerTypeByDealerConfId(Long dealerConfigurationId) throws DAOServiceException, DAOSQLException ;
    public int deleteDealerConfCustomerTypeByDealerConfIdAndCustClassTypeId(Long dealerConfId, Long customerClassTypeId) throws DAOServiceException, DAOSQLException ;
}
