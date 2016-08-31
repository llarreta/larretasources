package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.SaleChannelSellerBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerCoverage;
import co.com.directv.sdii.model.pojo.SaleChannelSeller;
import co.com.directv.sdii.model.pojo.ServiceSuperCategory;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.SaleChannelSellerVO;
import co.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD SaleChannelSeller
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelSellerBusinessBeanLocal
 */
@Stateless(name="SaleChannelSellerBusinessBeanLocal",mappedName="ejb/SaleChannelSellerBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SaleChannelSellerBusinessBean extends BusinessBase implements SaleChannelSellerBusinessBeanLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SaleChannelSellerBusinessBean.class);
    
    @EJB(name="SaleChannelSellerDAOLocal", beanInterface=SaleChannelSellerDAOLocal.class)
    private SaleChannelSellerDAOLocal daoSaleChannelSeller;
    
    @EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal dealersDAO;
    
    @EJB(name="DealerCoverageDAOLocal", beanInterface=DealerCoverageDAOLocal.class)
    private DealerCoverageDAOLocal dealerCoverageDAO;
    
    @EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
    private WorkOrderDAOLocal workOrderDAO;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelSellerBusinessBeanLocal#getAllSaleChannelSellers()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SaleChannelSellerVO> getAllSaleChannelSellers() throws BusinessException {
        log.debug("== Inicia getAllSaleChannelSellers/SaleChannelSellerBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoSaleChannelSeller.getAllSaleChannelSellers(), SaleChannelSellerVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllSaleChannelSellers/SaleChannelSellerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSaleChannelSellers/SaleChannelSellerBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelSellerBusinessBeanLocal#getSaleChannelSellersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SaleChannelSellerVO getSaleChannelSellerByID(Long id) throws BusinessException {
        log.debug("== Inicia getSaleChannelSellerByID/SaleChannelSellerBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SaleChannelSeller objPojo = daoSaleChannelSeller.getSaleChannelSellerByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(SaleChannelSellerVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSaleChannelSellerByID/SaleChannelSellerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSaleChannelSellerByID/SaleChannelSellerBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelSellerBusinessBeanLocal#createSaleChannelSeller(co.com.directv.sdii.model.vo.SaleChannelSellerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException {
        log.debug("== Inicia createSaleChannelSeller/SaleChannelSellerBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SaleChannelSeller objPojo =  UtilsBusiness.copyObject(SaleChannelSeller.class, obj);
            daoSaleChannelSeller.createSaleChannelSeller(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createSaleChannelSeller/SaleChannelSellerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSaleChannelSeller/SaleChannelSellerBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelSellerBusinessBeanLocal#updateSaleChannelSeller(co.com.directv.sdii.model.vo.SaleChannelSellerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException {
        log.debug("== Inicia updateSaleChannelSeller/SaleChannelSellerBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SaleChannelSeller objPojo =  UtilsBusiness.copyObject(SaleChannelSeller.class, obj);
            daoSaleChannelSeller.updateSaleChannelSeller(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateSaleChannelSeller/SaleChannelSellerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSaleChannelSeller/SaleChannelSellerBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelSellerBusinessBeanLocal#deleteSaleChannelSeller(co.com.directv.sdii.model.vo.SaleChannelSellerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException {
        log.debug("== Inicia deleteSaleChannelSeller/SaleChannelSellerBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SaleChannelSeller objPojo =  UtilsBusiness.copyObject(SaleChannelSeller.class, obj);
            daoSaleChannelSeller.deleteSaleChannelSeller(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSaleChannelSeller/SaleChannelSellerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSaleChannelSeller/SaleChannelSellerBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.SaleChannelSellerBusinessBeanLocal#getDealersForSaleAndInstallDealerAssociatedSkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersForSaleAndInstallDealerAssociatedSkill(SkillEvaluationDTO parameters) throws BusinessException {
		log.debug("== Inicia getDealersForSaleAndInstallDealerAssociatedSkill/SaleChannelSellerBusinessBean ==");
		final String REGEX=",";
		try {
			if( parameters == null ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL040.getCode(),ErrorBusinessMessages.ALLOCATOR_AL040.getMessage());
			} else if( parameters.getPostalCodeId() == null || parameters.getPostalCodeId().longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL042.getCode(),ErrorBusinessMessages.ALLOCATOR_AL042.getMessage());
			} else if( parameters.getCountryId() == null || parameters.getCountryId().longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL032.getCode(),ErrorBusinessMessages.ALLOCATOR_AL032.getMessage());
			}
			List<DealerVO> response = new ArrayList<DealerVO>();
			//Se valida que la supercategoria del servicio sea de tipo instalacion
			Long installationSuperCategoryId = CodesBusinessEntityEnum.SUPER_CATEGORY_INSTALLATION.getIdEntity( ServiceSuperCategory.class.getName() );
			if( installationSuperCategoryId != null
					&& installationSuperCategoryId.longValue() > 0
					&& parameters.getServiceSupercategoyId().equals( installationSuperCategoryId ) ){

				//Se consulta el parametro CODE_SYS_PARAM_ENABLED_SALE_INSTALL_UPGRADE_DOWNGRADE para determinar 
				// si esta activa la condicion de servicios de upgrade o downgrade
				String systemParameter=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.CODE_SYS_PARAM_ENABLED_SALE_INSTALL_UPGRADE_DOWNGRADE.getCodeEntity(), parameters.getCountryId(), systemParameterDAO);
				boolean enableUpgradeDownGrade = new Boolean(systemParameter == "" ? "false" : systemParameter);
				//si esta activa la condicion de los servicios de upgrade y downgrade
				if(enableUpgradeDownGrade){
					//Se consultan los servicio de upgrade y downgrade
					String codeServiceInstallUpgradeDowngrade = CodesBusinessEntityEnum.CODE_SERVICE_INSTALL_UPGRADE_DOWNGRADE.getCodeEntity();
					//Si los servicios son de upgrade o downgrade
					if(UtilsBusiness.isListInArray(parameters.getServices(),codeServiceInstallUpgradeDowngrade.split(REGEX))){
						//Se obtienen el dealer del ultimo servicio de la categoria instalacion 
						List<Dealer> resultpojo =  workOrderDAO.getDealerFromLastServiceInstallWoFromCustomer(parameters.getIbsCustomerCode(),parameters.getCountryId());
			            List<DealerVO> result = UtilsBusiness.convertList(resultpojo, DealerVO.class);
			            if(result.size()!=0 && !result.isEmpty()){
				            response.addAll(result);
							return response;
			            }
					}
				}
				
				if( parameters.getIbsSaleDealerCode() == null || parameters.getIbsSaleDealerCode().equals("") ){
					throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL047.getCode(),ErrorBusinessMessages.ALLOCATOR_AL047.getMessage());
				}
				Long saleDealerCode = new Long(parameters.getIbsSaleDealerCode());

				//1. Evaluar si el dealer vendedor tiene es instalador
				Dealer saleDealer = dealersDAO.getDealerByDealerCodeAndTypeCodeAndCountryIdAndStatus(saleDealerCode,CodesBusinessEntityEnum.CODE_DEALER_TYPE_INSTALLER.getCodeEntity(),parameters.getCountryId(),CodesBusinessEntityEnum.DEALER_STATUS_NORMAL.getCodeEntity());
				if( saleDealer != null ){
					// Evaluar si tiene cobertura
					if( this.evaluateDealerCoverage(saleDealerCode, parameters.getPostalCodeId(),parameters.getCountryId()) ){
						response.add( UtilsBusiness.copyObject(DealerVO.class, saleDealer) );
						return response;
					}
				} 
				
				//si no tiene cobertura revisa la cobertura de sus sucursales
				List<DealerVO> branchesWithCoverage = evaluateDealersCoverage(dealersDAO.getDealerBranchesByDealerCodeAndBranchesTypesAndCountryAndStatus(saleDealerCode,CodesBusinessEntityEnum.CODE_DEALER_TYPE_INSTALLER.getCodeEntity(),parameters.getCountryId(),CodesBusinessEntityEnum.DEALER_STATUS_NORMAL.getCodeEntity()),parameters.getPostalCodeId(),parameters.getCountryId());
				if(branchesWithCoverage != null && ! branchesWithCoverage.isEmpty()){	
					response.addAll( branchesWithCoverage );
					return response;
				}
				
				List<Dealer> installersOfSaleChannelCoverage = this.daoSaleChannelSeller.getInstallerDealersBySellerCodeAndCountry(saleDealerCode, parameters.getCountryId()); 
				//Si el dealer vendedor no es instalador se consultan los canales que sean instaladores y que tengan cobertura
				response.addAll( evaluateDealersCoverage(installersOfSaleChannelCoverage , parameters.getPostalCodeId(),parameters.getCountryId()) );
			}
			return response;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación getDealersForSaleAndInstallDealerAssociatedSkill/SaleChannelSellerBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealersForSaleAndInstallDealerAssociatedSkill/SaleChannelSellerBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Evalua el cubrimiento de una coleccion de compañias y retorna las que tienen cobertura en el codigo postal
	 * enviado por parametro
	 * @param dealers Lista de compañias a evaluar
	 * @param postalCodeId codigo postal en donde se busca saber si la compañia tiene cobertura
	 * @return Lista de compañias que tienen cobertura en el codigo postal enviado por parametro
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private List<DealerVO> evaluateDealersCoverage(List<Dealer> dealers,Long postalCodeId,Long countryId) throws BusinessException {
		log.debug("== Inicia evaluateDealersCoverage/SaleChannelSellerBusinessBean ==");
        try {
        	List<DealerVO> response = new ArrayList<DealerVO>();
        	if( dealers != null && !dealers.isEmpty() ){
        		for( Dealer dealer : dealers ){
    				if( this.evaluateDealerCoverage(dealer.getDealerCode(), postalCodeId,countryId) ){
            			response.add( UtilsBusiness.copyObject(DealerVO.class, dealer) );
            		}
    			}
        	}
        	return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación evaluateDealersCoverage/SaleChannelSellerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina evaluateDealersCoverage/SaleChannelSellerBusinessBean ==");
        }
	}
	
	/**
	 * 
	 * Metodo: A partir del codigo del dealer y del id del codigo postal 
	 * @param dealerCode
	 * @param postalCodeId
	 * @return true si tiene cobertura en el codigo postal, false si no
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private boolean evaluateDealerCoverage(Long dealerCode , Long postalCodeId,Long countryId) throws BusinessException {
		log.debug("== Inicia evaluateDealerCoverage/SaleChannelSellerBusinessBean ==");
        try {
        	boolean response = false;
        	DealerCoverage dealerCoverage = dealerCoverageDAO.getActiveDealerCoverageByDealerCodePostalCodeAndCountry(dealerCode, postalCodeId, countryId);
        	if( dealerCoverage != null && dealerCoverage.getId() != null && dealerCoverage.getId().longValue() > 0 )
        		response = true;
        	return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación evaluateDealerCoverage/SaleChannelSellerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina evaluateDealerCoverage/SaleChannelSellerBusinessBean ==");
        }
	}
	
	
}
