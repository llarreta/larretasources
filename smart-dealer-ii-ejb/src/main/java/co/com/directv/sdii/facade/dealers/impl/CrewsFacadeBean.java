package co.com.directv.sdii.facade.dealers.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.CrewsCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.facade.dealers.CrewsFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.CrewOff;
import co.com.directv.sdii.model.vo.CrewOffVO;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Crews 
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.CrewsCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.CrewsFacadeBeanLocal
 */
@Stateless(name="CrewsFacadeBeanLocal",mappedName="ejb/CrewsFacadeBeanLocal")
public class CrewsFacadeBean implements CrewsFacadeBeanLocal {

    @EJB(name="CrewsCRUDBeanLocal",beanInterface=CrewsCRUDBeanLocal.class)
    private CrewsCRUDBeanLocal businessCrewBean;

    /**
     *
     * Metodo: <Descripcion>
     * @param obj
     * @throws BusinessException 
     * @author jalopez
     */
    public void createCrews(CrewVO obj) throws BusinessException {
        businessCrewBean.createCrews(obj);
    }

    /**
     *
     * Metodo: <Descripcion>
     * @param obj
     * @throws BusinessException 
     * @author jalopez
     */
    public void deleteCrews(CrewVO obj) throws BusinessException {
        businessCrewBean.deleteCrews(obj);
    }

    /**
     *
     * Metodo: <Descripcion>
     * @return
     * @throws BusinessException 
     * @author jalopez
     */
    public List<CrewVO> getAllCrews() throws BusinessException {
        return businessCrewBean.getAllCrews();
    }

    /**
     *
     * Metodo: <Descripcion>
     * @param id
     * @return
     * @throws BusinessException 
     * @author jalopez
     */
    public CrewVO getCrewsByID(Long id) throws BusinessException {
        return businessCrewBean.getCrewsByID(id);
    }

   /*
    * (non-Javadoc)
    * @see co.com.directv.sdii.facade.dealers.CrewsFacadeBeanLocal#updateCrews(co.com.directv.sdii.model.vo.CrewVO, java.lang.String)
    */
    public void updateCrews(CrewVO obj,String description, UserVO user) throws BusinessException{
        businessCrewBean.updateCrews(obj, description, user);
    }

     /**
     * Metodo: <Descripcion>
     * @param plate - String
     * @param document - String
     * @throws BusinessException 
     * @author Jose Andres Casas
     */
    public List<CrewVO> getCrewsByPlateOrDocument(String plate, String document) throws BusinessException {
        return businessCrewBean.getCrewsByPlateOrDocument(plate, document);
    }
    
    /**
     * Metodo: <Descripcion>
     * @param plate - String
     * @param document - String
     * @throws BusinessException 
     * @author Jose Andres Casas
     */
    public List<CrewVO> getCrewsByPlateOrDocumentOrDealer(String plate, String document, Long dealerId) throws BusinessException {
        return businessCrewBean.getCrewsByPlateOrDocumentOrDealer(plate, document, dealerId);
    }

    /**
     * Desactiva una cuadrilla especifica
     * @param crewToDeactivate - CrewsBVO
     * @param desc - String
     * @throws BusinessException
     */
    public void deactivateCrew(CrewVO crewToDeactivate, String desc) throws BusinessException{
        this.businessCrewBean.deactivateCrew(crewToDeactivate, desc);
    }

	public List<CrewVO> getCrewsByDealerIdStartDateAndEndDate(Long dealerId,
			Date startDate, Date endDate) throws BusinessException {
		
		return businessCrewBean.getCrewsByDealerIdStartDateAndEndDate(dealerId, startDate, endDate);
	}

	public List<CrewVO> getAllCrewsOnlyBasicInfo() throws BusinessException {
		return businessCrewBean.getAllCrewsOnlyBasicInfo();
	}

	public List<CrewVO> getCrewsByVehicleId(long vehicleId)
			throws BusinessException {
		return businessCrewBean.getCrewsByVehicleId(vehicleId);
	}

	 /**
     * Method: Retorna un listado de Crews por el
     * id del Dealer
     * @param  Long dealerId
     * @return - List<CrewVO>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
	public List<CrewVO> getCrewsByDealerId(Long dealerId) throws BusinessException {		
		return businessCrewBean.getCrewsByDealerId(dealerId);
	}
	
	public List<CrewVO> getCrewsByResponsibleNameAndDealerId(String responsibleName, Long idDealer) throws BusinessException{
		return businessCrewBean.getCrewsByResponsibleNameAndDealerId(responsibleName, idDealer);
	}
	
	/**
	 * Metodo: permite consultar las cuadrillas por el id del dealer y el tipo de cuadrilla
	 * @param dealerId Long id del dealer
	 * @param crewType Long tipo de cuadrilla
	 * @return List<Crew> Lista de cuadrillas que cumple con el filtro
	 * @throws BusinessException Error en la busqueda de las cuadrillas por el filtro seleccionado
	 * @author jnova
	 */
	@Override
	public List<CrewVO> getCrewsByDealerIdAndType(Long dealerId, Long crewType)
			throws BusinessException {
		return businessCrewBean.getCrewsByDealerIdAndType(dealerId, crewType);
	}
		
	
	/**
     * Method: Retorna un listado de Crews por el
     * id del Dealer con la carga de la cuadrilla
     * @param  Long dealerId
     * @param  Long crewId
     * @return - CrewVO
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
	public CrewVO getCrewAmountByDealerId(Long crewId,Long dealerId) throws BusinessException{
		return businessCrewBean.getCrewAmountByDealerId(crewId,dealerId);
	}

	@Override
	public List<CrewVO> getActiveCrewsByDealerId(Long dealerId)
			throws BusinessException {
		return businessCrewBean.getActiveCrewsByDealerId(dealerId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.CrewsFacadeBeanLocal#getCrewsByDealerIdAndTypeAndResponsableName(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	public List<CrewVO> getCrewsByDealerIdAndTypeAndResponsableName(Long dealerId, Long crewType, String responsableName)throws BusinessException {
		return businessCrewBean.getCrewsByDealerIdAndTypeAndResponsableName(dealerId,crewType,responsableName);
	}
	
	/**
     * Metodo: Consultar CrewOff por el id de la cuadrilla
     * @param  crewOff CrewOff
     * @return List<CrewOffVO> 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
    public List<CrewOffVO> getCrewOffByCrew(CrewOff crewOff) throws BusinessException{
    	return businessCrewBean.getCrewOffByCrew(crewOff);
    }
}
