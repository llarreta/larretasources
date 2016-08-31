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
import co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.City;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.persistence.dao.dealers.CitiesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.PostalCodesDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad PostalCodes
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creacion: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.PostalCodess.PostalCodesDAOLocal
 * @see co.com.directv.sdii.ejb.business.PostalCodess.PostalCodesCRUDBeanLocal
 * 
 */
@Stateless(name="PostalCodesCRUDBeanLocal",mappedName="ejb/PostalCodesCRUDBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PostalCodesCRUDBean extends BusinessBase implements PostalCodesCRUDBeanLocal {

	@EJB(name="CitiesDAOLocal",beanInterface=CitiesDAOLocal.class)
	private CitiesDAOLocal daoCity;
	
	@EJB(name="PostalCodesDAOLocal",beanInterface=PostalCodesDAOLocal.class)
	private PostalCodesDAOLocal dao;

	private final static Logger log = UtilsBusiness.getLog4J(PostalCodesCRUDBean.class);
	
	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad PostalCodes 
	 * 
	 * @return List<PostalCodesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
         @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<PostalCodeVO> getAllPostalCodes() throws BusinessException {
        log.debug("== Inicia getAllPostalCodes/PostalCodesCRUDBean ==");
        try{
            return UtilsBusiness.convertList(dao.getAllPostalCodes(), PostalCodeVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllPostalCodes/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllPostalCodes/PostalCodesCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad PostalCodes.
	 * @param code
	 * @return PostalCodesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
         @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostalCodeVO getPostalCodesByCode(String code) throws BusinessException {
		log.debug("== Inicia getPostalCodesByID/PostalCodesCRUDBean ==");
        try {
            PostalCode postalCode = dao.getPostalCodesByCode(code);
            if(postalCode == null){
                return null;
            }
            return UtilsBusiness.copyObject(PostalCodeVO.class, postalCode);
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPostalCodesByCode/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPostalCodesByID/PostalCodesCRUDBean ==");
        }
	}

	
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal#getPostalCodesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostalCodeVO getPostalCodesByID(Long id) throws BusinessException {
        log.debug("== Inicia getPostalCodesByID/PostalCodesCRUDBean ==");
        try {
            PostalCode pc = dao.getPostalCodesByID(id);
            if(pc == null){
                return null;
            }
            return UtilsBusiness.copyObject(PostalCodeVO.class, pc);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPostalCodesByID/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPostalCodesByID/PostalCodesCRUDBean ==");
        }
	}

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal#getPostalCodesByCityId(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PostalCodeVO> getPostalCodesByCityId(Long cityId) throws BusinessException {
        log.debug("== Inicia getAllPostalCodes/PostalCodesCRUDBean ==");
        try{
            return UtilsBusiness.convertList(dao.getPostalCodesByCityId(cityId), PostalCodeVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPostalCodesByCityId/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllPostalCodes/PostalCodesCRUDBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal#getPostalCodesByName(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PostalCodeVO> getPostalCodesByName(String name) throws BusinessException {
        log.debug("== Inicia getPostalCodesByName/PostalCodesCRUDBean ==");
        try {
            return UtilsBusiness.convertList(dao.getPostalCodesByName(name), PostalCodeVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPostalCodesByName/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPostalCodesByName/PostalCodesCRUDBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal#createPostalCode(co.com.directv.sdii.model.vo.PostalCodeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createPostalCode(PostalCodeVO obj) throws BusinessException {
        log.debug("== Inicia createPostalCode/PostalCodesCRUDBean ==");
        try {
            

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode() , "No se puede crear el PostalCode, porque no se ha asignado uno o mas parámetros obligatorios");
            }
            Long cityId = obj.getCity().getId();
            City city = daoCity.getCitiesByID(cityId);
            UtilsBusiness.assertNotNull(city, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            String countryCode = city.getState().getCountry().getCountryCode();
            PostalCode postalCodePojo = dao.getPostalCodesByCodeByCountryCode(obj.getPostalCodeCode(), countryCode);
            
            if(postalCodePojo != null){
            	throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode() , ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
            }
            
            postalCodePojo = UtilsBusiness.copyObject(PostalCode.class, obj);
            dao.createPostalCode(postalCodePojo);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion createPostalCode/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createPostalCode/PostalCodesCRUDBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal#updatePostalCode(co.com.directv.sdii.model.vo.PostalCodeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updatePostalCode(PostalCodeVO obj) throws BusinessException {
        log.debug("== Inicia updatePostalCode/PostalCodesCRUDBean ==");
        try {
            PostalCode postalCodePojo = UtilsBusiness.copyObject(PostalCode.class, obj);

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode() , "No se puede crear el PostalCode, porque no se ha asignado uno o mas parámetros obligatorios");
            }

            dao.updatePostalCode(postalCodePojo);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion updatePostalCode/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updatePostalCode/PostalCodesCRUDBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal#deletePostalCode(co.com.directv.sdii.model.vo.PostalCodeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePostalCode(PostalCodeVO obj) throws BusinessException {
        log.debug("== Inicia updatePostalCode/PostalCodesCRUDBean ==");
        try {
            PostalCode postalCodePojo = UtilsBusiness.copyObject(PostalCode.class, obj);
            dao.deletePostalCode(postalCodePojo);

        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion deletePostalCode/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updatePostalCode/PostalCodesCRUDBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal#getAllPostalCodesByCountryId(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<PostalCodeVO> getAllPostalCodesByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllPostalCodesByCountryId/PostalCodesCRUDBean ==");
        try{
        	List<PostalCode> resultPojo = dao.getAllPostalCodesByCountryId(countryId);
            return UtilsBusiness.convertList(resultPojo, PostalCodeVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllPostalCodesByCountryId/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllPostalCodesByCountryId/PostalCodesCRUDBean ==");
        }
	}

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal#getPostalCodesByCodeByCountryCode(java.lang.String, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public PostalCodeVO getPostalCodesByCodeByCountryCode(String postalCode, String countryCode) throws BusinessException {
    	log.debug("== Inicia getPostalCodesByCodeByCountryCode/PostalCodesCRUDBean ==");
        try{
        	PostalCode resultPojo = dao.getPostalCodesByCodeByCountryCode(postalCode, countryCode);
            return UtilsBusiness.copyObject(PostalCodeVO.class, resultPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPostalCodesByCodeByCountryCode/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPostalCodesByCodeByCountryCode/PostalCodesCRUDBean ==");
        }
	}

	/* 
	 * modificacion ialessan
	 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
	 * marzo 2014
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal#getPostalCodesByCodeByCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostalCodeVO getPostalCodesByCodeByCountryId(String postalCode, Long countryId) throws BusinessException {
		log.debug("== Inicia getPostalCodesByCodeByCountryId/PostalCodesCRUDBean ==");

    	String paramsRequired = new String("");
    	
        if (  postalCode == null  
        	||postalCode.isEmpty()
        	||countryId == null 
        	||countryId <=0L) 
        {
            
            if (postalCode == null)
            	paramsRequired = paramsRequired.concat( " Postal Code ");
            else if (postalCode.isEmpty())
            	paramsRequired = paramsRequired.concat( " Postal Code ");
            else if (countryId == null)
            	paramsRequired = paramsRequired.concat( " Country "); //validar antes
            else if (countryId <=0L)
            	paramsRequired = paramsRequired.concat( " Country "); //validar antes
            
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage().concat(paramsRequired));
        }
        

        try{
        	PostalCode resultPojo = dao.getPostalCodesByCodeByCountryId(postalCode, countryId);
            return UtilsBusiness.copyObject(PostalCodeVO.class, resultPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPostalCodesByCodeByCountryCode/PostalCodesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPostalCodesByCodeByCountryId/PostalCodesCRUDBean ==");
        }
	}	
}
