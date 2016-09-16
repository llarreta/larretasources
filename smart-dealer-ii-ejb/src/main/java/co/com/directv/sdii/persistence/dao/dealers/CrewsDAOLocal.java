/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.directv.sdii.persistence.dao.dealers;


import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ReportCrewMovementsDTO;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 *
 * Interface Local para la gestion del CRUD de la
 * Entidad Crews
 *
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 */
@Local
public interface CrewsDAOLocal {

    public void createCrew(Crew newCrew) throws DAOServiceException, DAOSQLException;

    public List<Crew> getAll() throws DAOServiceException, DAOSQLException;

    public List<Crew> getCrewsByPlateOrDocument(String plate, String document) throws DAOServiceException, DAOSQLException;
    
    public List<Crew> getCrewsByPlateOrDocumentOrDealer(String plate, String document, Long dealerId) throws DAOServiceException, DAOSQLException;

    public Crew getCrewById(Long id) throws DAOServiceException, DAOSQLException;

    public void updateCrew(co.com.directv.sdii.model.pojo.Crew updateCrew) throws co.com.directv.sdii.exceptions.DAOServiceException, co.com.directv.sdii.exceptions.DAOSQLException;

    public void deleteCrew(Crew copyObject)throws DAOServiceException, DAOSQLException ;

    public List<Crew> getCrewsBetweenDates(final Date initDate, Date endDate) throws DAOServiceException, DAOSQLException;
    
    public List<Crew> getCrewsBetweenDatesAndEmployee(final Date initDate, final Date endDate, final Long empId) throws DAOServiceException, DAOSQLException;
    
    public List<Crew> getCrewsBetweenDatesAndEmployeeAndCrew(final Date initDate, final Date endDate, final Long empId , final Long crewId) throws DAOServiceException, DAOSQLException;

	public List<Crew> getCrewsByDealerIdStartDateAndEndDate(Long dealerId, Date startDate, Date endDate)throws DAOServiceException, DAOSQLException;

	public List<Crew> getAllCrewsOnlyBasicInfo()throws DAOServiceException, DAOSQLException;

	public List<Crew> getCrewsByVehicleId(long vehicleId)throws DAOServiceException, DAOSQLException;
	
	public List<Crew> getCrewsByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException;
	
	public List<Object[]> getCrewsByResponsibleNameAndDealerId(String responsibleName, Long idDealer) throws DAOSQLException, DAOServiceException ;
	
	/**
	 * Obtiene todos las cuadrillas de un responsable especificado
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param responsibleId
	 * @return List<Crew>
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public List<Crew> getCrewsByResponsible(Long responsibleId) throws DAOSQLException, DAOServiceException;
	
	/**
	 * Metodo: permite consultar las cuadrillas por el id del dealer y el tipo de cuadrilla
	 * @param dealerId Long id del dealer
	 * @param crewType Long tipo de cuadrilla
	 * @return List<Crew> Lista de cuadrillas que cumple con el filtro
	 * @throws DAOServiceException Error en la busqueda de las cuadrillas en la base de datos
	 * @throws DAOSQLException Error en la busqueda de las cuadrillas en la base de datos
	 * @author jnova
	 */
	public List<Crew> getCrewsByDealerIdAndType(Long dealerId , Long crewType) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: permite consultar las cuadrillas por el id del dealer y el tipo de cuadrilla y el vehiculo en estado activo
	 * @param dealerId Long id del dealer
	 * @param crewType Long tipo de cuadrilla
	 * @return List<Crew> Lista de cuadrillas que cumple con el filtro
	 * @throws DAOServiceException Error en la busqueda de las cuadrillas en la base de datos
	 * @throws DAOSQLException Error en la busqueda de las cuadrillas en la base de datos
	 * @author jnova
	 */
	public List<Crew> getActiveCrewsByDealerIdAndType(Long dealerId , Long crewType) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las cuadrillas activas por dealer
	 * @param dealerId identificador del dealer
	 * @return lista con las cuadrillas activas
	 * @throws DAOServiceException en caso de error al consultar la información
	 * @throws DAOSQLException en caso de error al consultar la información
	 * @author jjimenezh
	 */
	public List<Crew> getActiveCrewsByDealerId(Long dealerId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la cantidad de cuadrillas activas por dealer
	 * @param dealerId identificador del dealer
	 * @return cantidad de cuadrillas activas
	 * @throws DAOServiceException en caso de error al consultar la informaciÃ³n
	 * @throws DAOSQLException en caso de error al consultar la informaciÃ³n
	 * @author cduarte
	 */
	public Long getActiveCrewsQtyByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene la lista de cuadrillas asociadas a un vehiculo filtrando por un estado
	 * de cuadrilla
	 * @param vehicleId Identificador de vehiculo
	 * @param crewStatus Estado de cuadrilla
	 * @return Lista de cuadrillas que cumplen con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Crew> getCrewsByVehicleIdAndCrewStatusCode(Long vehicleId,String crewStatus)throws DAOServiceException, DAOSQLException;
	
	//REQ Inactivación de Técnico
	/**
	 * Metodo encargado de traer todos los movimentos de la cuadrilla con respecto a cambio de tecnico principal, nuevo/s integrante/s.<br/>
	 *
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jgonzmol
	 */
	public List<ReportCrewMovementsDTO> getCrewMovements(Long countryId, Date nowDate, RequestCollectionInfo requestInfo, ReportCrewMovementsDTO reportCrewMovementsDTO) throws DAOSQLException, DAOServiceException;

}

