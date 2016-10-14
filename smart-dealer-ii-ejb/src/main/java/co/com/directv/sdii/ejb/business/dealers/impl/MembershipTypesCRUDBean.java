package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.MembershipTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MembershipTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.MembershipTypesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad MembershipTypes
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.MembershipTypess.MembershipTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.MembershipTypess.MembershipTypesCRUDBeanLocal
 * 
 */
@Stateless(name="MembershipTypesCRUDBeanLocal",mappedName="ejb/MembershipTypesCRUDBeanLocal")
public class MembershipTypesCRUDBean extends BusinessBase implements MembershipTypesCRUDBeanLocal {

	@EJB(name="MembershipTypesDAOLocal",beanInterface=MembershipTypesDAOLocal.class)
	private MembershipTypesDAOLocal dao;

	private final static Logger log = UtilsBusiness.getLog4J(MembershipTypesCRUDBean.class);
	
	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad MembershipTypes 
	 * 
	 * @return List<MembershipTypesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<MembershipTypeVO> getAllMembershipTypes() throws BusinessException {
		log.debug("== Inicia getAllMembershipTypes/MembershipTypesCRUDBean ==");
		try{
			List<MembershipTypeVO> listVO = UtilsBusiness.convertList(dao.getAllMembershipTypes(), MembershipTypeVO.class);
			return listVO;
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllMembershipTypes/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMembershipTypes/MembershipTypesCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad MembershipTypes.
	 * @param code
	 * @return MembershipTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public MembershipTypeVO getMembershipTypesByCode(String code) throws BusinessException {
		log.debug("== Inicia getMembershipTypesByID/MembershipTypesCRUDBean ==");
        try {
        	MembershipTypeVO MembershipTypesVo = UtilsBusiness.copyObject(MembershipTypeVO.class, dao.getMembershipTypesByCode(code));
            return MembershipTypesVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getMembershipTypesByCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMembershipTypesByID/MembershipTypesCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad MembershipTypes.
	 * @param id
	 * @return MembershipTypesVo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public MembershipTypeVO getMembershipTypesByID(Long id) throws BusinessException {
		log.debug("== Inicia getMembershipTypesByID/MembershipTypesCRUDBean ==");
        try {
        	MembershipTypeVO MembershipTypesVo = UtilsBusiness.copyObject(MembershipTypeVO.class, dao.getMembershipTypesByID(id));
            return MembershipTypesVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getMembershipTypesByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMembershipTypesByID/MembershipTypesCRUDBean ==");
        }
	}
}
