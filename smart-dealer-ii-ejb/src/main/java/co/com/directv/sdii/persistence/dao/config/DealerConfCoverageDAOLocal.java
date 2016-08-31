package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerConfCoverage;
import co.com.directv.sdii.model.pojo.collection.DealerConfCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.DealerConfCoverageDTO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad DealerConfCoverage
 * 
 * Fecha de Creacion: Jue 12, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerConfCoverageDAOLocal {
    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * Crea una DealerConfCoverage en el sistema
     * @param obj - DealerConfCoverage
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
   
    public void createDealerConfCoverage(DealerConfCoverage obj) throws DAOServiceException, DAOSQLException;
    public int deleteDealerConfCoverageByDealerConfId(Long dealerConfigurationId) throws DAOServiceException, DAOSQLException;
    public DealerConfCoverageResponse getDealerConfCoverageByDealerConfId(Long dealerConfId , RequestCollectionInfo requestCollectionInfo)throws DAOServiceException, DAOSQLException;
	public int deleteDealerConfCoverageByDealerConfIdAndCoverageTypeId(Long dealerConfId, Long coverageTypeId) throws DAOServiceException, DAOSQLException;
	public int deleteDealerConfCoverageByDealerConfIdAndPostalCodeId(Long dealerConfId, Long postalCodeId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Trae los códigos postales de una ciudad en los que un dealer no tiene configuracion de covertura 
	 * @param dealerConfigurationId identificador del dealer conf
	 * @param cityId identificador de la ciudad
	 * @param requestCollectionInfo información de paginación
	 * @return PostalCodeResponse respuesta paginada
	 * @throws DAOServiceException en caso de un error en la ejecución de la operación
	 * @throws DAOSQLException en caso de un error en la ejecución de la operación
	 * @author ssanabri
	 */
	public PostalCodeResponse getPostalCodesWithoutConfCoverageByDealerConfIdAndCityId(Long dealerConfigurationId, Long cityId,
			RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Busca los DealerConfCoverage que correspondan a los parametros.
	 * @param dealerConfigurationId: id de configuracion de dealer
	 * @param dealerId: id de dealer
	 * @param postalCodeId: id de codigo postal.
	 * @return
	 * @throws DAOServiceException en caso de un error en la ejecución de la operación
	 * @throws DAOSQLException en caso de un error en la ejecución de la operación
	 */
	public DealerConfCoverage getDealerConfCoverageBy(Long dealerConfigurationId, Long dealerId, Long postalCodeId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DealerConfCoverage
	 * @param obj objeto que encapsula la información de un DealerConfCoverage
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerConfCoverage(DealerConfCoverage dealerConfCoverage) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerConfCoverage almacenados en la persistencia por país y estado
	 * @return Lista con los DealerConfCoverage existentes, una lista vacia en caso que no existan DealerConfCoverage en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author ssanabri
	 */
	public List<DealerConfCoverageDTO> getAllDealerConfCoveragesByCountryAndStatus(Long country,String status) throws DAOServiceException, DAOSQLException;
	
}
