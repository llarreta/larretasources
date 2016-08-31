package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.ChannelTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ChannelTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.ChannelTypesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad ChannelTypes
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.ChannelTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.ChannelTypesCRUDBeanLocal
 * 
 */
@Stateless(name="ChannelTypesCRUDBeanLocal",mappedName="ejb/ChannelTypesCRUDBeanLocal")
public class ChannelTypesCRUDBean extends BusinessBase implements ChannelTypesCRUDBeanLocal {

	@EJB(name="ChannelTypesDAOLocal",beanInterface=ChannelTypesDAOLocal.class)
	private ChannelTypesDAOLocal dao;

    private final static Logger log = UtilsBusiness.getLog4J(ChannelTypesCRUDBean.class);
	
	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad ChannelTypes 
	 * 
	 * @return List<ChannelTypesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<ChannelTypeVO> getAllChannelTypes() throws BusinessException {
		log.debug("== Inicia getAllChannelTypes/ChannelTypesCRUDBean ==");
		try{
			List<ChannelTypeVO> listVO = UtilsBusiness.convertList(dao.getAllChannelTypes(), ChannelTypeVO.class);
			return listVO;
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAllChannelTypes/ChannelTypesCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad ChannelTypes.
	 * @param code
	 * @return ChannelTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ChannelTypeVO getChannelTypesByCode(String code) throws BusinessException {
		log.debug("== Inicia getChannelTypesByID/ChannelTypesCRUDBean ==");
        try {
        	ChannelTypeVO vo = UtilsBusiness.copyObject(ChannelTypeVO.class, dao.getChannelTypesByCode(code));
            return vo;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getChannelTypesByID/ChannelTypesCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad ChannelTypes.
	 * @param id
	 * @return ChannelTypesVo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ChannelTypeVO getChannelTypesByID(Long id) throws BusinessException {
		log.debug("== Inicia getChannelTypesByID/ChannelTypesCRUDBean ==");
        try {
        	ChannelTypeVO vo = UtilsBusiness.copyObject(ChannelTypeVO.class, dao.getChannelTypesByID(id));
            return vo;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getChannelTypesByID/ChannelTypesCRUDBean ==");
        }
	}
}
