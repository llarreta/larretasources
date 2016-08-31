package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.ZoneTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ZoneTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.ZoneTypesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad ZoneTypes
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creaci�n: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.ZoneTypess.ZoneTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.ZoneTypess.ZoneTypesCRUDBeanLocal
 * 
 */
@Stateless(name="ZoneTypesCRUDBeanLocal",mappedName="ejb/ZoneTypesCRUDBeanLocal")
public class ZoneTypesCRUDBean extends BusinessBase implements ZoneTypesCRUDBeanLocal {

	@EJB(name="ZoneTypesDAOLocal",beanInterface=ZoneTypesDAOLocal.class)
	private ZoneTypesDAOLocal dao;

	private final static Logger log = UtilsBusiness.getLog4J(ZoneTypesCRUDBean.class);
	
	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad ZoneTypes 
	 * 
	 * @return List<ZoneTypesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<ZoneTypeVO> getAllZoneTypes() throws BusinessException {
		log.debug("== Inicia getAllZoneTypes/ZoneTypesCRUDBean ==");
		try{
			List<ZoneTypeVO> listVO = UtilsBusiness.convertList(dao.getAllZoneTypes(), ZoneTypeVO.class);
			return listVO;
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllVehicleTypes/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllZoneTypes/ZoneTypesCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad ZoneTypes.
	 * @param code
	 * @return ZoneTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ZoneTypeVO getZoneTypesByCode(String code) throws BusinessException {
		log.debug("== Inicia getZoneTypesByID/ZoneTypesCRUDBean ==");
        try {
        	ZoneTypeVO vo = UtilsBusiness.copyObject(ZoneTypeVO.class, dao.getZoneTypesByCode(code));
            return vo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getZoneTypesByCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getZoneTypesByID/ZoneTypesCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad ZoneTypes.
	 * @param id
	 * @return ZoneTypesVo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ZoneTypeVO getZoneTypesByID(Long id) throws BusinessException {
		log.debug("== Inicia getZoneTypesByID/ZoneTypesCRUDBean ==");
        try {
        	ZoneTypeVO vo = UtilsBusiness.copyObject(ZoneTypeVO.class, dao.getZoneTypesByID(id));
            return vo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getZoneTypesByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getZoneTypesByID/ZoneTypesCRUDBean ==");
        }
	}
}
