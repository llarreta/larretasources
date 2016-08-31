/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.exceptions.BusinessDetailException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * EJB que Implementa las operaciones de negocio para la Entidad de
 * Dealers(Operaciones diferentes del CRUD)
 * @author Joan Lopez
 * @version 1.0
 * @see 
 */
@Local
public interface DealersBusinessBeanLocal {

   /**
     * Lista las compañias
     * @param countryId identificador del país sobre el que se realiza la consulta 
     * @return List<DealersDTO>
     * @throws BusinessException
     */
    public List<DealerVO> getDealerCodes(Long countryId) throws BusinessException;

   /**
    *
    * Metodo: <Descripcion>
    * @param dealerCode
    * @param depodCode
    * @param countryId identificador del país sobre el que se realiza la consulta
    * @return DealersDTO
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
    public DealerVO getDealer(Long dealerCode, String depotCode, Long countryId) throws BusinessException;

    /**
	 * Metodo: Si el edificio con codigo 'ibsBuildingCode' esta en la tabla BUILDINGS regresa los dealers que atienden ese edificio. 
	 *         Si no hay dealers activos que atiendan este edificio, regresa los dealers activos que atienden edificios cuya covertura esta asociada al 'postalCode'.  
	 *         Si el edificio con codigo 'ibsBuildingCode' NO ESTA en la tabla BUILDINGS, regresa los dealers activos que atienden edificios cuya covertura esta asociada al 'postalCode'.   
     * @param ibsBuildingCode Identificador del edificio.
     * @param postalCode Zona postal del edificio donde se va a prestar el servicio.
     * @return Regresa los dealers que atienden ese edificio. 
     * @throws BusinessException
     * @author rdelarosa
     */
    public List<DealerVO> getDealersWhoAttendsABuildingOrAttendsBuildingsIntoPostalCode(String ibsBuildingCode, String postalCode, String countryCode) 
       throws BusinessException; 
    
    

    /**
     * Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
	 * Metodo: Si el edificio con id de edificio esta en la tabla BUILDINGS regresa el dealer que atiende ese edificio. 
	 *         Si no hay dealer que atienda este edificio, retorna una lista vacia.  
	 *         Si el edificio con id 'buildingId' NO ESTA en la tabla BUILDINGS, retorna una lista vacia.   
     * @param buildingId Identificador del edificio.
     * @return Regresa el dealer que atienden ese edificio. 
     * @throws BusinessException
     * @throws BusinessDetailException <tipo> <descripcion>
     * @author ialessan
     */   
    public List<DealerVO> getDealersWhoAttendsABuilding(Long buildingId) throws BusinessException;    
    
    /**
     * 
     * Metodo: Obtiene el dealer que cumple la habilidad de dealer vende instala
     * @param SkillEvaluationDTO objeto que permite decidir que compañia cumple con la habilidad
     * @return Lista con dealer que cumple con la habilidad
     * @throws BusinessException <tipo> <descripcion>
     * @author jnova
     */
    public List<DealerVO> getDealersForSaleAndInstallSameDealerSkill(SkillEvaluationDTO parameters) throws BusinessException;
    
    /**
	 * 
	 * Metodo: A partir de los parametro obtiene los dealers que cumplen con la habilidad par o impar
	 * a partir de codigo de cliente
	 * @param parameters
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForEvenOrOddCustomerCodeSkill(SkillEvaluationDTO parameters) throws BusinessException;
	
	/**
	 * Metodo: Consulta los Dealers activos por el código postal
	 * @param postalCode - PostalCode
	 * @return List<Dealer>
	 * @throws DAOServiceException
	 * @throws BusinessException
	 */
	public List<DealerVO> getAllActiveByPostalCode(PostalCode postalCode)throws BusinessException;

}
