package co.com.directv.sdii.ejb.business.config.impl;

import java.sql.Blob;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Alliance;
import co.com.directv.sdii.model.pojo.AllianceCompany;
import co.com.directv.sdii.model.pojo.AllianceFile;
import co.com.directv.sdii.model.pojo.ComercialProduct;
import co.com.directv.sdii.model.vo.AllianceCompanyVO;
import co.com.directv.sdii.model.vo.AllianceFileVO;
import co.com.directv.sdii.model.vo.AllianceVO;
import co.com.directv.sdii.model.vo.ComercialProductVO;
import co.com.directv.sdii.persistence.dao.config.AllianceCompanyDAOLocal;
import co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ComercialProductDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Alianzas.
 *
 * Caso de Uso CFG - 18 - Gestionar Alianzas
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal
 * @see co.com.directv.sdii.persistence.dao.config.AllianceFileDAOLocal
 * @see co.com.directv.sdii.persistence.dao.config.ComercialProductDAOLocal
 * @see co.com.directv.sdii.persistence.dao.config.AllianceCompanyDAOLocal
 */

@Stateless(name="ConfigAlianzasBusinessLocal",mappedName="ejb/ConfigAlianzasBusinessLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigAlianzasBusinessBean extends BusinessBase implements ConfigAlianzasBusinessLocal {

    @EJB(name="AllianceDAOLocal",beanInterface=AllianceDAOLocal.class)
    private AllianceDAOLocal dAOAllianceBean;
    
    @EJB(name="AllianceFileDAOLocal",beanInterface=AllianceFileDAOLocal.class)
    private AllianceFileDAOLocal dAOAllianceFileBean;

    @EJB(name="ComercialProductDAOLocal",beanInterface=ComercialProductDAOLocal.class)
    private ComercialProductDAOLocal comercialProductDAO;

    @EJB(name="AllianceCompanyDAOLocal",beanInterface=AllianceCompanyDAOLocal.class)
    private AllianceCompanyDAOLocal allianceCompanyDAO;

    private final static Logger log = UtilsBusiness.getLog4J(ConfigAlianzasBusinessBean.class);

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAllianceByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AllianceVO getAllianceByID(Long id) throws BusinessException {
        log.debug("== Inicia getAllianceByID/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            Alliance allianceId = dAOAllianceBean.getAllianceByID(id);
            if (allianceId != null) {
                AllianceVO dealerVo = UtilsBusiness.copyObject(AllianceVO.class, allianceId);
                return dealerVo;
            }
            return null;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllianceByID/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceByID/ConfigAlianzasBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAllianceByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AllianceVO getAllianceByCode(String code) throws BusinessException {
        log.debug("== Inicia getAllianceByCode/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            Alliance alliance = dAOAllianceBean.getAllianceByCode(code);
            if(alliance == null)
                return null;

            AllianceVO allianceVO = UtilsBusiness.copyObject(AllianceVO.class, alliance);

            return allianceVO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllianceByCode/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceByCode/ConfigAlianzasBusinessBean ==");
        }
    }

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAllianceByName(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<AllianceVO> getAllianceByName(String name) throws BusinessException {
        log.debug("== Inicia getAllianceByName/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(name, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            List<AllianceVO> listVo = UtilsBusiness.convertList(dAOAllianceBean.getAllianceByName(name), AllianceVO.class);
            
            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllianceByName/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceByName/ConfigAlianzasBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAll()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<AllianceVO> getAll() throws BusinessException {
       log.debug("== Inicia getAll/DealersCRUDBean ==");
        try {
            List<AllianceVO> listVo = UtilsBusiness.convertList(dAOAllianceBean.getAll(), AllianceVO.class);
            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAll/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DealersCRUDBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAllianceByDate(java.util.Date, java.util.Date)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<AllianceVO> getAllianceByDate(Date init, Date end) throws BusinessException {
         log.debug("== Inicia getAllianceByDate/ConfigAlianzasBusinessBean ==");
        try {
        	/*Se garantiza que las fechas de consulta esten entre las 0 horas de la fecha de incio
            y las 24 horas de la fecha final*/
           Calendar initTemp = new GregorianCalendar();
           Calendar endTemp = new GregorianCalendar();
           initTemp.setTime(init);
           endTemp.setTime(end);
           initTemp.set(Calendar.HOUR_OF_DAY, 0);
           initTemp.set(Calendar.MINUTE, 0);
           initTemp.set(Calendar.SECOND, 0);
           endTemp.set(Calendar.HOUR_OF_DAY, 23);
           endTemp.set(Calendar.MINUTE, 59);
           endTemp.set(Calendar.SECOND, 59);
           //Valida si la fecha inicial es anterior a la final
           if(endTemp.before(initTemp)){
               log.debug(ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getMessage());
               throw new BusinessException(ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getCode(),ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getMessage());
           }
            List<AllianceVO> listVo = UtilsBusiness.convertList(dAOAllianceBean.getAllianceByDate(UtilsBusiness.obtenerPrimeraHoraDia(init), UtilsBusiness.obtenerUltimaHoraDia(end)), AllianceVO.class);

            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllianceByDate/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceByDate/ConfigAlianzasBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#createAlliance(co.com.directv.sdii.model.vo.AllianceVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAlliance(AllianceVO obj) throws BusinessException {
        log.debug("== Inicio createAlliance/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

        	
            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
            	log.debug(ErrorBusinessMessages.ERROR_DATA.getMessage());
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }
            
            Alliance tmp = this.getAllianceByCode(obj.getAllianceCode());
            if(tmp != null){
            	log.debug(ErrorBusinessMessages.CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY.getMessage());
                throw new BusinessException(ErrorBusinessMessages.CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY.getCode(),ErrorBusinessMessages.CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY.getMessage());
            }

            
            if(obj.getEndDate().before(obj.getInitDate())){
            	log.debug(ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getMessage());
                throw new BusinessException(ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getCode(),ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getMessage());
            }

            Alliance alliancePojo = UtilsBusiness.copyObject(Alliance.class, obj);
            dAOAllianceBean.createAlliance(alliancePojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAlliance/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAlliance/ConfigAlianzasBusinessBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#createAllianceFile(co.com.directv.sdii.model.vo.AllianceFileVO)
     */
    @Override
	public void createAllianceFile(AllianceFileVO obj) throws BusinessException {
    	log.debug("== Inicio createAllianceFile/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());     
            AllianceFile allianceFilePojo = UtilsBusiness.copyObject(AllianceFile.class, obj);
            Blob blob = Hibernate.createBlob(obj.getDatos());
            allianceFilePojo.setData(blob);
            dAOAllianceFileBean.createAllianceFile(allianceFilePojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAllianceFile/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAllianceFile/ConfigAlianzasBusinessBean ==");
        }
    	
    	
		
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAllianceFileByAllianceID(co.com.directv.sdii.model.vo.AllianceVO)
     */
    @Override
	public AllianceFileVO getAllianceFileByAllianceID(AllianceVO obj)throws BusinessException {
    	log.debug("== Inicio getAllianceFileByAllianceID/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            AllianceFile allianceFile = dAOAllianceFileBean.getAllianceFileByAllianceID(obj.getId());
            AllianceFileVO allianceFileVO;
            if(allianceFile!=null){
            	allianceFileVO = UtilsBusiness.copyObject(AllianceFileVO.class, allianceFile);
            	allianceFileVO.setDatos(allianceFile.getData().getBytes(1,
            	Integer.parseInt(Long.valueOf(allianceFile.getData().length()).toString())));
            	return allianceFileVO;
            }else{
            	return null;
            }
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllianceFileByAllianceID/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceFileByAllianceID/ConfigAlianzasBusinessBean ==");
        }
    	
	}

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#updateAlliance(co.com.directv.sdii.model.vo.AllianceVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAlliance(AllianceVO obj) throws BusinessException {
        log.debug("== Inicia updateAlliance/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
            	log.debug(ErrorBusinessMessages.ERROR_DATA.getMessage());
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }

            Alliance alliancePojo = UtilsBusiness.copyObject(Alliance.class, obj);
            dAOAllianceBean.updateAlliance(alliancePojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateAlliance/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAlliance/ConfigAlianzasBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#deleteAlliance(co.com.directv.sdii.model.vo.AllianceVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAlliance(AllianceVO obj) throws BusinessException {
       log.debug("== Inicia deleteAlliance/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            //Consulta AllianceFile a eliminar            
            AllianceFile file =  dAOAllianceFileBean.getAllianceFileByAllianceID(obj.getId());
            if(file!=null){
            	dAOAllianceFileBean.deleteAllianceFile(file);
            }
            //Elimina Alliance
            Alliance alliancePojo = this.dAOAllianceBean.getAllianceByID(obj.getId());
            dAOAllianceBean.deleteAlliance(alliancePojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteAlliance/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAlliance/ConfigAlianzasBusinessBean ==");
        }
    }

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#populateEmpresas()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<AllianceCompanyVO> populateEmpresas() throws BusinessException {
         log.debug("== Inicia populateEmpresas/ConfigAlianzasBusinessBean ==");
        try {
            List<AllianceCompanyVO> listVo = UtilsBusiness.convertList(allianceCompanyDAO.getAll(), AllianceCompanyVO.class);

            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación populateEmpresas/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina populateEmpresas/ConfigAlianzasBusinessBean ==");
        }
    }

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#populateProductosComerciales()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ComercialProductVO> populateProductosComerciales() throws BusinessException {
         log.debug("== Inicia populateProductosComerciales/ConfigAlianzasBusinessBean ==");
        try {
            List<ComercialProductVO> listVo = UtilsBusiness.convertList(comercialProductDAO.getAll(), ComercialProductVO.class);

            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación populateProductosComerciales/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina populateProductosComerciales/ConfigAlianzasBusinessBean ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAllianceCompanyByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AllianceCompanyVO getAllianceCompanyByID(Long id) throws BusinessException {
        log.debug("== Inicia getAllianceCompanyByID/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            AllianceCompany allianceCompanyId = allianceCompanyDAO.getAllianceCompanyByID(id);
            if (allianceCompanyId != null) {
                AllianceCompanyVO allianceCompanyVo = UtilsBusiness.copyObject(AllianceCompanyVO.class, allianceCompanyId);
                return allianceCompanyVo;
            }
            return null;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllianceCompanyByID/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceCompanyByID/ConfigAlianzasBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAllianceCompanyByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AllianceCompanyVO getAllianceCompanyByCode(String code) throws BusinessException {
        log.debug("== Inicia getAllianceCompanyByCode/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            AllianceCompany allianceCompany = allianceCompanyDAO.getAllianceCompanyByCode(code);
            if(allianceCompany == null)
                return null;

            AllianceCompanyVO allianceCompanyVO = UtilsBusiness.copyObject(AllianceCompanyVO.class, allianceCompany);

            return allianceCompanyVO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllianceCompanyByCode/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceCompanyByCode/ConfigAlianzasBusinessBean ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getComercialProductByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ComercialProductVO getComercialProductByID(Long id) throws BusinessException {
        log.debug("== Inicia getComercialProductByID/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            ComercialProduct comercialProductId = comercialProductDAO.getComercialProductByID(id);
            if (comercialProductId != null) {
                ComercialProductVO comercialProductVo = UtilsBusiness.copyObject(ComercialProductVO.class, comercialProductId);
                return comercialProductVo;
            }
            return null;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getComercialProductByID/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getComercialProductByID/ConfigAlianzasBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getComercialProductByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ComercialProductVO getComercialProductByCode(String code) throws BusinessException {
        log.debug("== Inicia getComercialProductByCode/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            ComercialProduct comercialProduct = comercialProductDAO.getComercialProductByCode(code);
            if(comercialProduct == null)
                return null;

            ComercialProductVO comercialProductVO = UtilsBusiness.copyObject(ComercialProductVO.class, comercialProduct);

            return comercialProductVO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getComercialProductByCode/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getComercialProductByCode/ConfigAlianzasBusinessBean ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAllAllianceByCountryId(java.lang.Long)
     */
	public List<AllianceVO> getAllAllianceByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllAllianceByCountryId/DealersCRUDBean ==");
        try {
        	UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	List<Alliance> resultPojo = dAOAllianceBean.getAllAllianceByCountryId(countryId);
            List<AllianceVO> listVo = UtilsBusiness.convertList(resultPojo, AllianceVO.class);
            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllAllianceByCountryId/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllAllianceByCountryId/DealersCRUDBean ==");
        }
	}

	@Override
	public List<AllianceCompanyVO> getAllAllianceCompanyByCountryId(
			Long countryId) throws BusinessException {
		log.debug("== Inicia getAllAllianceCompanyByCountryId/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	List<AllianceCompany> resultPojo = allianceCompanyDAO.getAllAllianceCompanyByCountryId(countryId);
            List<AllianceCompanyVO> listVo = UtilsBusiness.convertList(resultPojo, AllianceCompanyVO.class);

            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllAllianceCompanyByCountryId/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllAllianceCompanyByCountryId/ConfigAlianzasBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal#getAllComercialProductsByCountryId(java.lang.Long)
	 */
	public List<ComercialProductVO> getAllComercialProductsByCountryId(
			Long countryId) throws BusinessException {
		log.debug("== Inicia getAllComercialProductsByCountryId/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	List<ComercialProduct> listPojo = comercialProductDAO.getAllComercialProductsByCountryId(countryId);
            List<ComercialProductVO> listVo = UtilsBusiness.convertList(listPojo, ComercialProductVO.class);

            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllComercialProductsByCountryId/ConfigAlianzasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllComercialProductsByCountryId/ConfigAlianzasBusinessBean ==");
        }
	}

	

	
 
}
