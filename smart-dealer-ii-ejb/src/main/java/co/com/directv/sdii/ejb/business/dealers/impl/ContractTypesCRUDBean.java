package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.ContractTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.ContractTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.ContractTypesDAOLocal;

/**
 *
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad ContractTypes
 * Solo implementa operaciones de consulta
 *
 * Fecha de Creacion: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.dao.dealers.ContractTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.ContractTypesCRUDBeanLocal
 *
 */
@Stateless(name="ContractTypesCRUDBeanLocal",mappedName="ejb/ContractTypesCRUDBeanLocal")
public class ContractTypesCRUDBean extends BusinessBase implements ContractTypesCRUDBeanLocal {

	@EJB(name="ContractTypesDAOLocal",beanInterface=ContractTypesDAOLocal.class)
	private ContractTypesDAOLocal dao;

    private final static Logger log = UtilsBusiness.getLog4J(ContractTypesCRUDBean.class);

	/**
	 *
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad ContractTypes
	 *
	 * @return List<ContractTypesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<ContractTypeVO> getAllContractTypes() throws BusinessException {
        log.debug("== Inicia getAllContractTypes/ContractTypesCRUDBean ==");
        try{
                List<ContractTypeVO> listVO = UtilsBusiness.convertList(dao.getAllContractTypes(), ContractTypeVO.class);
                return listVO;
        } catch (DAOServiceException ex) {
        log.debug("== Error en la Capa de Negocio debido a una DAOServiceException ==");
        throw new BusinessException(ex.getMessage());
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAllContractTypes/ContractTypesCRUDBean ==");
        }
	}

	/**
	 *
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad ContractTypes.
	 * @param code
	 * @return ContractTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ContractTypeVO getContractTypesByCode(String code) throws BusinessException {
            log.debug("== Inicia getContractTypesByID/ContractTypesCRUDBean ==");
            try {
                    ContractTypeVO vo = UtilsBusiness.copyObject(ContractTypeVO.class, dao.getContractTypesByCode(code));
                    return vo;
            } catch(Throwable ex){
            	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
            	throw this.manageException(ex);
            } finally {
                log.debug("== Termina getContractTypesByID/ContractTypesCRUDBean ==");
            }
	}

	/**
	 *
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad ContractTypes.
	 * @param id
	 * @return ContractTypesVo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ContractTypeVO getContractTypesByID(Long id) throws BusinessException {
            log.debug("== Inicia getContractTypesByID/ContractTypesCRUDBean ==");
            try {
                    ContractTypeVO vo = UtilsBusiness.copyObject(ContractTypeVO.class, dao.getContractTypesByID(id));
                    return vo;
            }catch(Throwable ex){
            	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
            	throw this.manageException(ex);
            }  finally {
                log.debug("== Termina getContractTypesByID/ContractTypesCRUDBean ==");
            }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.ContractTypesCRUDBeanLocal#getAllContractTypesByCountryId(java.lang.Long)
	 */
	public List<ContractTypeVO> getAllContractTypesByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllContractTypesByCountryId/ContractTypesCRUDBean ==");
        try{
                List<ContractTypeVO> listVO = UtilsBusiness.convertList(dao.getAllContractTypesByCountryId(countryId), ContractTypeVO.class);
                return listVO;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllContractTypesByCountryId/ContractTypesCRUDBean ==");
        }
	}

}
