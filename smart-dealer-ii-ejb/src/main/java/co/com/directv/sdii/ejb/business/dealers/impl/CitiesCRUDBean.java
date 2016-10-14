package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.CitiesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CityVO;
import co.com.directv.sdii.persistence.dao.dealers.CitiesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad Cities
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.CitiesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.CitiesCRUDBeanLocal
 * 
 */
@Stateless(name="CitiesCRUDBeanLocal",mappedName="ejb/CitiesCRUDBeanLocal")
public class CitiesCRUDBean extends BusinessBase implements CitiesCRUDBeanLocal {

    @EJB(name="CitiesDAOLocal",beanInterface=CitiesDAOLocal.class)
    private CitiesDAOLocal dao;

    private final static Logger log = UtilsBusiness.getLog4J(CitiesCRUDBean.class);

    /**
     *
     * Metodo: Retorna un listado de todos los registros
     * de la Entidad Cities
     *
     * @return List<CitiesVO>
     * @throws BusinessException <tipo> <descripcion>
     * @author jalopez
     */
    public List<CityVO> getAllCities() throws BusinessException {
        log.debug("== Inicia getAllCities/CitiesCRUDBean ==");
        try {
            return UtilsBusiness.convertList(dao.getAllCities(), CityVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAllCities/CitiesCRUDBean ==");
        }
    }

    /**
     *
     * Metodo: Reorna el resultado de la consulta por codigo
     * de la Entidad Cities.
     * @param code
     * @return CitiesVO
     * @throws BusinessException <tipo> <descripcion>
     * @author jalopez
     */
    public CityVO getCitiesByCode(String code) throws BusinessException {
        log.debug("== Inicia getCitiesByID/CitiesCRUDBean ==");
        try {
            return UtilsBusiness.copyObject(CityVO.class, dao.getCitiesByCode(code));
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCitiesByID/CitiesCRUDBean ==");
        }
    }

    /**
     *
     * Metodo: Reorna el resultado de la consulta por ID
     * de la Entidad Cities.
     * @param id
     * @return CitiesVo
     * @throws BusinessException <tipo> <descripcion>
     * @author jalopez
     */
    public CityVO getCitiesByID(Long id) throws BusinessException {
        log.debug("== Inicia getCitiesByID/CitiesCRUDBean ==");
        try {
            return UtilsBusiness.copyObject(CityVO.class, dao.getCitiesByID(id));
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCitiesByID/CitiesCRUDBean ==");
        }
    }

    public List<CityVO> getCitiesByStateId(Long stateId) throws BusinessException {
        log.debug("== Inicia getAllCities/CitiesCRUDBean ==");
        try {
            return UtilsBusiness.convertList(dao.getCitiesByStateId(stateId), CityVO.class);
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAllCities/CitiesCRUDBean ==");
        }
    }
}
