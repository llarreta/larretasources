package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerConfService;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad DealerConfService
 * 
 * Fecha de Creacion: Jue 12, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerConfServiceDAOLocal {
    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * Crea una DealerConfService en el sistema
     * @param obj - DealerConfCustomerType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
   
    public void createDealerConfService(DealerConfService obj) throws DAOServiceException, DAOSQLException ;
    public int deleteDealerConfServiceByDealerConfId(Long dealerConfId) throws DAOServiceException, DAOSQLException;
	public int deleteDealerConfServiceByDealerConfIdAndServiceCatId(Long dealerConfId, Long serviceCategoryId) throws DAOServiceException, DAOSQLException;
	
}
