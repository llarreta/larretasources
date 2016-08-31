package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.MediaContactTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.MediaContactType;
import co.com.directv.sdii.model.vo.MediaContactTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.MediaContactTypesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad MediaContactTypes
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.MediaContactTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.MediaContactTypesCRUDBeanLocal
 * 
 */
@Stateless(name="MediaContactTypesCRUDBeanLocal",mappedName="ejb/MediaContactTypesCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MediaContactTypesCRUDBean extends BusinessBase implements MediaContactTypesCRUDBeanLocal {

	@EJB(name="MediaContactTypesDAOLocal",beanInterface=MediaContactTypesDAOLocal.class)
	private MediaContactTypesDAOLocal dao;

    private final static Logger log = UtilsBusiness.getLog4J(MediaContactTypesCRUDBean.class);

    /**
	 * Obtiene todos los medios de contacto
	 * @return List<MediaContactTypeVO> Listado con los medios de contacto
	 * @throws BusinessException 
	 * @author jcasas
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MediaContactTypeVO> getAllMediaContactTypes() throws BusinessException {
    	log.debug("== Termina getAllMediaContactTypes/MediaContactTypesCRUDBean ==");
    	try {
    		List<MediaContactType> mediaContactTypes = dao.getAllMediaContactTypes();
    		if(mediaContactTypes == null)
    			return null;
    		return UtilsBusiness.convertList(mediaContactTypes, MediaContactTypeVO.class);
    	} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllMediaContactTypes/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMediaContactTypes/MediaContactTypesCRUDBean ==");
        }
	}
	
	/**
	 * Obtiene un tipo de contacto por un id especificado
	 * @param id Id del tipo de medio de contacto a buscar
	 * @return MediaContactTypeVO Medio de contacto con el id especificado
	 * @throws BusinessException 
	 * @author jcasas
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MediaContactTypeVO getMediaContactTypesByID(Long id) throws BusinessException {
    	log.debug("== Termina getAllMediaContactTypes/MediaContactTypesCRUDBean ==");
    	try {
    		UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		MediaContactType mediaContactType = dao.getMediaContactTypesByID(id);
    		if(mediaContactType == null)
    			return null;
    		return UtilsBusiness.copyObject(MediaContactTypeVO.class, mediaContactType);
    		
    	} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getMediaContactTypesByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMediaContactTypes/MediaContactTypesCRUDBean ==");
        }
	}

}
