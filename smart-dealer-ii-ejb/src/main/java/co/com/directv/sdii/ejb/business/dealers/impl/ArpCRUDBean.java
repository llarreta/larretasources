package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.ArpCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Arp;
import co.com.directv.sdii.model.vo.ArpVO;
import co.com.directv.sdii.persistence.dao.dealers.ArpDAOLocal;

/**
 *
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad Arp
 * Solo implementa operaciones de consulta
 *
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.dao.dealers.ArpDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.ArpCRUDBeanLocal
 *
 */
@Stateless(name="ArpCRUDBeanLocal",mappedName="ejb/ArpCRUDBeanLocal")
public class ArpCRUDBean extends BusinessBase implements ArpCRUDBeanLocal {

    @EJB(name="ArpDAOLocal",beanInterface=ArpDAOLocal.class)
    private ArpDAOLocal dao;
    
    private final static Logger log = UtilsBusiness.getLog4J(ArpCRUDBean.class);

    /**
     * Retorna un listado de todos los registros
     * de la Entidad Arp
     *
     * @return List<ArpVO>
     * @throws BusinessException
     */
    public List<ArpVO> getAllArp() throws BusinessException {
        log.debug("== Inicia getAllArp/ArpCRUDBean ==");
        try {
            List<ArpVO> listVO = UtilsBusiness.convertList(dao.getAllArp(), ArpVO.class);
            return listVO;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllArp/ArpCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllArp/ArpCRUDBean ==");
        }
    }

    /**
     *
     * Reorna el resultado de la consulta por codigo
     * de la Entidad Arp.
     * @param code
     * @return ArpVO
     * @throws BusinessException
     */
    public ArpVO getArpByCode(String code) throws BusinessException {
        log.debug("== Inicia getArpByCode/ArpCRUDBean ==");
        try {
            ArpVO vo = UtilsBusiness.copyObject(ArpVO.class, dao.getArpByCode(code));
            return vo;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getArpByCode/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getArpByCode/ArpCRUDBean ==");
        }
    }

    /**
     *
     * Reorna el resultado de la consulta por ID
     * de la Entidad Arp.
     * @param id - Long
     * @return ArpVo
     * @throws BusinessException
     */
    public ArpVO getArpByID(Long id) throws BusinessException {
        log.debug("== Inicia getArpByID/ArpCRUDBean ==");
        try {
            ArpVO vo = UtilsBusiness.copyObject(ArpVO.class, dao.getArpByID(id));
            return vo;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getArpByID/ArpCRUDBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getArpByID/ArpCRUDBean ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.ArpCRUDBeanLocal#getAllArpByCountryId(java.lang.Long)
     */
    public List<ArpVO> getAllArpByCountryId(Long countryId) throws BusinessException{
    	log.debug("== Inicia getAllArpByCountryId/ArpCRUDBean ==");
        try {
        	List<Arp> resultPojo = dao.getAllArpByCountryId(countryId);
            List<ArpVO> listVO = UtilsBusiness.convertList(resultPojo, ArpVO.class);
            return listVO;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAllArpByCountryId/ArpCRUDBean ==");
        }
    }
}
