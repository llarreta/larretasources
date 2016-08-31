/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.ws.business.config;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigBusinessAreasFacadeLocal;
import co.com.directv.sdii.model.vo.BusinessAreaVO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Esta interfaz define los contratos a utilizar para los servicios web
 * de la Configuración de Áreas de Negocio.
 *
 * @author ialessan
 * 
 * @see co.com.directv.sdii.facade.config.ConfigBusinessAreasFacadeLocal
 */
@MTOM
@WebService()
@Stateless()
public class ConfigBusinessAreasWS {
	/**
	 * Referencia a la fachada que ofrece el acceso a las operaciones de las áreas de negocio
	 */
    @EJB
    private ConfigBusinessAreasFacadeLocal configBusinessAreasFacadeLocal;

     /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * 
     * Metodo: Obtiene todas las Áreas de Negocios presentes en la tabla BUSINESS_AREAS
     * @return lista todas las Áreas de Negocios presentes en la tabla BUSINESS_AREAS
     * , una lista vacia en caso que no exista ningun área de negocio
     * @throws BusinessException en caso de error al ejecutar la operación,
     * @author ialessan
     */
    @WebMethod(operationName = "getAllBusinessAreas", action="getAllBusinessAreas")
    public List<BusinessAreaVO> getAllBusinessAreas(@WebParam(name = "countryId") Long countryId) throws BusinessException {
        return configBusinessAreasFacadeLocal.getAllBusinessAreas(countryId); 
    }
     
}
