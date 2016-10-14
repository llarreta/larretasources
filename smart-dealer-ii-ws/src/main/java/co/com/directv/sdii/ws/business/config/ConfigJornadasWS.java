/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.directv.sdii.ws.business.config;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigJornadasFacadeLocal;
import co.com.directv.sdii.facade.config.ConfigJornadasStatusFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ServiceHourDTO;
import co.com.directv.sdii.model.vo.ServiceHourStatusVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;


/**
 * Servicio que expone todos los metodos referentes a la administracion de Jornadas
 * 
 * Fecha de Creación: 25/03/2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0    
 */
@MTOM
@WebService()
@Stateless()
public class ConfigJornadasWS {

    @EJB
    private ConfigJornadasFacadeLocal ejbRef;
    @EJB
    private ConfigJornadasStatusFacadeBeanLocal statusFacadeBean;

    /**
     * Obtiene una jornada por el id
     * @param id Id de jornada a buscar
     * @return Jornada de servicio con el id especificado
     * @throws BusinessException
     */
    @WebMethod(operationName = "getServiceHoursByID", action="getServiceHoursByID")
    public ServiceHourVO getServiceHoursByID(@WebParam(name = "id") Long id) throws BusinessException {
        return ejbRef.getServiceHoursByID(id);
    }

    /**
     * Obtiene un listado de jornadas con el nombre especificado
     * @param name Nombre de la jornada a buscar
     * @return Listado de jornadas que contengan el nombre especificado
     * @throws BusinessException
     */
    @WebMethod(operationName = "getServiceHoursByName", action="getServiceHoursByName")
    public List<ServiceHourVO> getServiceHoursByName(@WebParam(name = "name") String name) throws BusinessException {
        return ejbRef.getServiceHoursByName(name);
    }
    
    /**
     * Obtiene un listado de las jornadas existentes en el sistema
     * @return Listado de las jornadas existentes en el sistema
     * @throws BusinessException
     */
    @WebMethod(operationName = "getAllServiceHours", action="getAllServiceHours")
    public List<ServiceHourVO> getAllServiceHours() throws BusinessException {
    	return ejbRef.getAll();
    }
    
    /**
     * Obtiene un listado de las jornadas existentes en el sistema correspondientes a un pais especifico
     * @param countryId Id del pais de las jornadas
     * @return Listado de las jornadas correspondientes al pais especificado
     * @throws BusinessException
     */
    @WebMethod(operationName = "getAllServiceHoursByCountryId", action="getAllServiceHoursByCountryId")
    public List<ServiceHourVO> getAllServiceHoursByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
        return ejbRef.getAllServiceHoursByCountryId(countryId);
    }
    
    /**
     * Obtiene un listado de las jornadas activas existentes en el sistema correspondientes a un pais especifico
     * @param countryId Id del pais de las jornadas
     * @return Listado de las jornadas correspondientes al pais especificado
     * @throws BusinessException
     */
    @WebMethod(operationName = "getAllActiveServiceHoursByCountryId", action="getAllActiveServiceHoursByCountryId")
    public List<ServiceHourVO> getAllActiveServiceHoursByCountryId(@WebParam(name = "countryId")Long countryId) throws BusinessException {
        return ejbRef.getAllActiveServiceHoursByCountryId(countryId);
    }
    
    /**
     * Obtiene un listado de jornadas dentro de un rango de fechas especifico
     * @param init Fecha de inicio
     * @param end Fecha fin
     * @return Listado de jornadas que estan dentro del rango de fechas especificado
     * @throws BusinessException
     */
    @WebMethod(operationName = "getServiceHoursByDate", action="getServiceHoursByDate")
    public List<ServiceHourVO> getServiceHoursByDate(@WebParam(name = "init") Date init, @WebParam(name = "end") Date end) throws BusinessException {
        return ejbRef.getServiceHoursByDate(init, end);
    }

    /**
     * Crea una jornada en el sistema
     * @param obj Objeto con los datos basicos de la jornada
     * @throws BusinessException
     */
    @WebMethod(operationName = "createServiceHours",action="createServiceHours")
    public void createServiceHours(@WebParam(name = "obj") ServiceHourVO obj, 
    		@WebParam(name = "userId") Long userId) throws BusinessException {
        try {
            ejbRef.createServiceHours(obj, userId);
        } catch (BusinessException ex) {
            throw ex;
        }
        catch(Throwable ex){
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
    }

    /**
     * Actualiza una jornada en el sistema
     * @param obj Objeto con los datos basicos de la jornada
     * @throws BusinessException
     */
    @WebMethod(operationName = "updateServiceHours",action="updateServiceHours")
    public void updateServiceHours(@WebParam(name = "obj") ServiceHourVO obj,
    		@WebParam(name = "userId") Long userId) throws BusinessException {
        try{
        	ejbRef.updateServiceHours(obj, userId);
        }catch(BusinessException ex){
        	throw ex;
        }catch(Throwable ex){
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
        
    }

    /**
     * Elimina una jornada del sistema
     * @param obj Objeto con los datos basicos de la jornada
     * @throws BusinessException
     */
    @WebMethod(operationName = "deleteServiceHours", action="deleteServiceHours")
    public void deleteServiceHours(@WebParam(name = "obj") ServiceHourVO obj) throws BusinessException {
        try{
        	ejbRef.deleteServiceHours(obj);
        }catch (BusinessException ex){
        	throw ex;
        }catch (Throwable ex){
        	if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
        }
    }
    
    /**
     * Obtiene todos los estados posibles de las jornadas
     * @return Listado de los estados de jornadas
     * @throws BusinessException
     */
    @WebMethod(action="getAllServiceHourStatus", operationName="getAllServiceHourStatus")
    public List<ServiceHourStatusVO> getAllServiceHourStatus() throws BusinessException{
    	try{
        	return statusFacadeBean.getAllServiceHourStatus();
        }catch (BusinessException ex){
        	throw ex;
        }catch (Throwable ex){
        	throw new BusinessException(ex.getMessage());
        }
    }
    
    /**
     * Este método retorna una instancia de SERVICE_HOURS por ID.
     * @param id identificador de la jornada
     * @return Objeto que encapsula la información de las jornadas
     * @throws BusinessException en caso de error
     * @author jjimenezh
     */
    @WebMethod(action="getServiceHourDtoByID", operationName="getServiceHourDtoByID")
    public ServiceHourDTO getServiceHourDtoByID(@WebParam(name = "id") Long id) throws BusinessException{
    	return ejbRef.getServiceHourDtoByID(id);
    }

    /**
     * Obtiene una lista de jornadas dado el identificador de un país
     * @param countryId identificador del país
     * @return lista de jornadas que aplican a un país específico
     * @throws BusinessException en caso de error al tratar de obtener la lista de jornadas regionalizadas
     * @author jjimenezh agregado por control de cambios 2010-04-26
     */
    @WebMethod(action="getServiceHoursDtoByCountryId", operationName="getServiceHoursDtoByCountryId")
    public List<ServiceHourDTO> getServiceHoursDtoByCountryId(@WebParam(name = "countryId") Long countryId) throws BusinessException{
    	return ejbRef.getServiceHoursDtoByCountryId(countryId);
    }

    
}
