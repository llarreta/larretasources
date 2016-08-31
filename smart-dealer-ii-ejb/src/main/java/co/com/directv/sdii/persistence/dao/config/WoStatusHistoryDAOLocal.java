package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoStatusHistory;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WoStatusHistory
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WoStatusHistoryDAOLocal {
	
	/**
     * Crea una WoStatusHistory en el sistema
     * @param obj - WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWoStatusHistory(WoStatusHistory obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un wostatushistory con el id especificado
     * @param id - Long
     * @return - WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WoStatusHistory getWoStatusHistoryByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un wostatushistory especificado
     * @param obj - WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWoStatusHistory(WoStatusHistory obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un wostatushistory del sistema
     * @param obj - WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWoStatusHistory(WoStatusHistory obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los wostatushistorys del sistema
     * @return - List<WoStatusHistory>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WoStatusHistory> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Retorna un WoStatusHistory filtrando
     * por el id de la workorder y por el estado,
     * retornando la ultima workorder reason.
     * @param woId
     * @param woStatusId
     * @return WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public WoStatusHistory getWorkorderReasonByWoHistory(Long woId,Long woStatusId) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Control Cambios Creacion de Contacts
     * Retorna un listado de id de la  WoStatusHistory, filtrados
     * por el id de la work order y por el dealer.
     * @param woId Long - id de la WO
     * @param dealerId Long - id del Dealer
     * @return List<WoStatusHistory> - Listado con los id de la  WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jalopez
     */
    public List<WoStatusHistory> getWoStatusHistoryByDealer(Long woId,Long dealerId) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta los registros de cambios de estado de WO por codigo de workorder y key del cambio de estado de IBS
     * @param woCode codigo de WO
     * @param historyKey key retornado por ibs en el momento de realizar el historial
     * @return List<WoStatusHistory>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public List<WoStatusHistory> getWoStatusHistoryByWoCodeAndHistoryKey(String woCode , String historyKey) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta los registros de cambios de estado de WO por codigo de workorder y key del cambio de estado de IBS
     * @param woCode codigo de WO
     * @param historyKey key retornado por ibs en el momento de realizar el historial
     * @return List<WoStatusHistory>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author waguilera
     */
    public List<WoStatusHistory> getWoStatusHistoryByWoIDAttendOrFinish(Long woId) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo encargado de traer el ultimo woStatusHistory de una work order en un estado especifico
     * @param woId id de la work order que desea buscar
     * @param woStatusId id del estado que desea bucar
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author Aharker
     */
    public WoStatusHistory getLastWoStatusHistoryByWoIdAndWoStatus(Long woId, Long woStatusId) throws DAOServiceException, DAOSQLException;
    
    
    /**
     * 
     * Metodo: Retorna un WoStatusHistory filtrando
     * por el id de la workorder , por el estado, y por descripcion no nula.
     * @param woId
     * @param woStatusId
     * @return WoStatusHistory
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author ialessan
     */
    
    public WoStatusHistory getWoStatusHistoryByWoIdDescNotNull(Long woId,Long woStatusId) throws DAOServiceException, DAOSQLException;
    
}
