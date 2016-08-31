package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.SaleChanelBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.DealersCodesDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.SaleChanel;
import co.com.directv.sdii.model.pojo.SaleChannelInstaller;
import co.com.directv.sdii.model.pojo.SaleChannelSeller;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SaleChannelResponse;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.SaleChanelVO;
import co.com.directv.sdii.model.vo.SaleChannelInstallerVO;
import co.com.directv.sdii.model.vo.SaleChannelSellerVO;
import co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;
import co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO;

/**
 * EJB que implementa las operaciones Tipo CRUD SaleChanel
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChanelBusinessBeanLocal
 */
@Stateless(name="SaleChanelBusinessBeanLocal",mappedName="ejb/SaleChanelBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SaleChanelBusinessBean extends BusinessBase implements SaleChanelBusinessBeanLocal {

    @EJB(name="SaleChanelDAOLocal", beanInterface=SaleChanelDAOLocal.class)
    private SaleChanelDAOLocal daoSaleChanel;
    
    @EJB(name="SaleChannelInstallerDAOLocal", beanInterface=SaleChannelInstallerDAOLocal.class)
    private SaleChannelInstallerDAOLocal daoSaleChannelInstaller;
    
    @EJB(name="SaleChannelSellerDAOLocal", beanInterface=SaleChannelSellerDAOLocal.class)
    private SaleChannelSellerDAOLocal daoSaleChannelSeller;
    
    @EJB(name="DealersCRUDLocal", beanInterface=DealersCRUDLocal.class)
    private DealersCRUDLocal dealersCrudBusinessBean;
    
    @EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal daoDealers;
    
    
    
    private final static Logger log = UtilsBusiness.getLog4J(SaleChanelBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChanelBusinessBeanLocal#getAllSaleChanels()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SaleChanelVO> getAllSaleChanels() throws BusinessException {
        log.debug("== Inicia getAllSaleChanels/SaleChanelBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoSaleChanel.getAllSaleChanels(), SaleChanelVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllSaleChanels/SaleChanelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSaleChanels/SaleChanelBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChanelBusinessBeanLocal#getSaleChanelsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SaleChanelVO getSaleChanelByID(Long id) throws BusinessException {
        log.debug("== Inicia getSaleChanelByID/SaleChanelBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SaleChanel objPojo = daoSaleChanel.getSaleChanelByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            SaleChanelVO result = UtilsBusiness.copyObject(SaleChanelVO.class, objPojo);
            List<SaleChannelInstaller> installers = daoSaleChannelInstaller.getSaleChannelInstallersBySaleChannelId(id);
            List<SaleChannelInstallerVO> installersVo = UtilsBusiness.convertList(installers, SaleChannelInstallerVO.class);
            
            List<DealerVO> dealerInstallers = new ArrayList<DealerVO>();
            List<DealerVO> dealerSellers = new ArrayList<DealerVO>();
            
            for (SaleChannelInstallerVO saleChannelInstallerVO : installersVo) {
            	dealerInstallers.add(UtilsBusiness.copyObject(DealerVO.class, saleChannelInstallerVO.getDealer()));
			}
            
            List<SaleChannelSeller> sellers = daoSaleChannelSeller.getSaleChannelSellersBySaleChannelId(id);
            List<SaleChannelSellerVO> sellersVo = UtilsBusiness.convertList(sellers, SaleChannelSellerVO.class);
            
            for (SaleChannelSellerVO saleChannelSellerVO : sellersVo) {
            	dealerSellers.add(UtilsBusiness.copyObject(DealerVO.class, saleChannelSellerVO.getDealer()));
			}
            
            result.setInstallers(dealerInstallers);
            result.setSellers(dealerSellers);
            
            return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSaleChanelByID/SaleChanelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSaleChanelByID/SaleChanelBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChanelBusinessBeanLocal#createSaleChanel(co.com.directv.sdii.model.vo.SaleChanelVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSaleChanel(SaleChanelVO obj) throws BusinessException {
        log.debug("== Inicia createSaleChanel/SaleChanelBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	SaleChanel objPojo = daoSaleChanel.getSaleChanelByCode(obj.getCode());
        	if(objPojo != null){
        		throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL020.getCode(), ErrorBusinessMessages.ALLOCATOR_AL020.getMessage(), Arrays.asList(new String[]{obj.getCode()})); 
        	}
        	
            objPojo =  UtilsBusiness.copyObject(SaleChanel.class, obj);
            daoSaleChanel.createSaleChanel(objPojo);
            
            //Creando los vendedores
            List<DealerVO> saleChanelSellerVos = obj.getSellers();
            
            //Creando los instaladores
            List<DealerVO> saleChanelInstallerVos = obj.getInstallers();
            
            createSaleChannelSellersAndInstallers(saleChanelSellerVos, saleChanelInstallerVos, objPojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createSaleChanel/SaleChanelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSaleChanel/SaleChanelBusinessBean ==");
        }
		
	}
	
	private void createSaleChannelSellersAndInstallers(List<DealerVO> dealerSellersVos, List<DealerVO> dealerInstallersVos, SaleChanel objPojo) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		//Crear las compañías vendedoras que no existan en SDII, desde IBS
		List<SaleChannelSeller> sellers = createSellerDealersFromIbs(dealerSellersVos, objPojo);
		SaleChannelInstaller installer;
		for (SaleChannelSeller saleChannelSeller : sellers) {
        	daoSaleChannelSeller.createSaleChannelSeller(saleChannelSeller);
		}
        //Creando los instaladores
        for (DealerVO dealer : dealerInstallersVos) {
        	installer = createInstaller(dealer, objPojo);
        	daoSaleChannelInstaller.createSaleChannelInstaller(installer);
		}
        
	}
	
	private SaleChannelInstaller createInstaller(DealerVO dealer,
			SaleChanel objPojo) throws PropertiesException {
		SaleChannelInstaller installer = new SaleChannelInstaller();
		installer.setDealer(dealer);
		installer.setSaleChanel(objPojo);
		installer.setInclusionDate(new Date());
		installer.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
		return installer;
	}
	
	/**
	 * Metodo: <Descripcion>
	 * @param dealer
	 * @param saleChannel
	 * @return
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	private SaleChannelSeller createSaleChannelSeller(Dealer dealer, SaleChanel saleChannel) throws PropertiesException {
		SaleChannelSeller seller = new SaleChannelSeller();
		seller.setDealer(dealer);
		seller.setSaleChanel(saleChannel);
		seller.setInclusionDate(new Date());
		seller.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
		return seller;
	}


	private List<SaleChannelSeller> createSellerDealersFromIbs(List<DealerVO> dealerSellersVos, SaleChanel saleChannel) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		Long sellerCode;
		String sellerDepotCode;
		Dealer existingDealer = null;
		DealersCodesDTO dealerCodeDto;
		List<DealersCodesDTO> dealerCodesDto = new ArrayList<DealersCodesDTO>();
		SaleChannelSeller seller;
		List<SaleChannelSeller> result = new ArrayList<SaleChannelSeller>();
		
		for (DealerVO dealerSellerVO : dealerSellersVos) {
			sellerCode = dealerSellerVO.getDealerCode();
			sellerDepotCode = dealerSellerVO.getDepotCode();
			existingDealer = daoDealers.getDealerByDealerCodeAndCountryId(sellerCode, saleChannel.getCountry().getId());
			//Si el dealer vendedor no existe en SDII se agrega a la lista de dealers por crear
			if(existingDealer == null){
				dealerCodeDto = new DealersCodesDTO();
				dealerCodeDto.setCountryId(saleChannel.getCountry().getId());
				dealerCodeDto.setDealerCode(sellerCode);
				dealerCodeDto.setDepotCode(sellerDepotCode);
				dealerCodesDto.add(dealerCodeDto);
			}else{
				seller = createSaleChannelSeller(existingDealer, saleChannel);
				result.add(seller);
			}
		}
		
		List<DealerVO> dealersFromIbs = dealersCrudBusinessBean.createDealersFromIbsCodesAndReturn(dealerCodesDto);
		
		for (DealerVO dealerVO : dealersFromIbs) {
			seller = createSaleChannelSeller(UtilsBusiness.copyObject(Dealer.class, dealerVO), saleChannel);
			result.add(seller);
		}
		return result;
	}


	


	private void deleteSaleChannelSellersAndInstallers(Long saleChannelId) throws DAOServiceException, DAOSQLException{
		daoSaleChannelSeller.deleteSellersBySaleChannelId(saleChannelId);
    	daoSaleChannelInstaller.deleteInstallersBySaleChannelId(saleChannelId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChanelBusinessBeanLocal#updateSaleChanel(co.com.directv.sdii.model.vo.SaleChanelVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSaleChanel(SaleChanelVO obj) throws BusinessException {
        log.debug("== Inicia updateSaleChanel/SaleChanelBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	SaleChanel objPojo = daoSaleChanel.getSaleChanelByCode(obj.getCode());
        	if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL020.getCode(), ErrorBusinessMessages.ALLOCATOR_AL020.getMessage(), Arrays.asList(new String[]{obj.getCode()})); 
        	}
        	
            objPojo =  UtilsBusiness.copyObject(SaleChanel.class, obj);
            daoSaleChanel.updateSaleChanel(objPojo);
            deleteSaleChannelSellersAndInstallers(objPojo.getId());
            createSaleChannelSellersAndInstallers(obj.getSellers(), obj.getInstallers(), objPojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateSaleChanel/SaleChanelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSaleChanel/SaleChanelBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChanelBusinessBeanLocal#deleteSaleChanel(co.com.directv.sdii.model.vo.SaleChanelVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSaleChanel(SaleChanelVO obj) throws BusinessException {
        log.debug("== Inicia deleteSaleChanel/SaleChanelBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SaleChanel objPojo =  UtilsBusiness.copyObject(SaleChanel.class, obj);
            deleteSaleChannelSellersAndInstallers(objPojo.getId());
            daoSaleChanel.deleteSaleChanel(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSaleChanel/SaleChanelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSaleChanel/SaleChanelBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.SaleChanelBusinessBeanLocal#deleteSaleChanelById(java.lang.Long)
	 */
	@Override
	public void deleteSaleChanelById(Long saleChannelId)
			throws BusinessException {
		log.debug("== Inicia deleteSaleChanel/SaleChanelBusinessBean ==");
        UtilsBusiness.assertNotNull(saleChannelId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	SaleChanel saleChanel = daoSaleChanel.getSaleChanelByID(saleChannelId);
        	UtilsBusiness.assertNotNull(saleChanel, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + "No se encontró el canal de venta con el id: " + saleChannelId);
        	daoSaleChannelSeller.deleteSellersBySaleChannelId(saleChannelId);
        	daoSaleChannelInstaller.deleteInstallersBySaleChannelId(saleChannelId);
            daoSaleChanel.deleteSaleChanel(saleChanel);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSaleChanel/SaleChanelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSaleChanel/SaleChanelBusinessBean ==");
        }
		
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.SaleChanelBusinessBeanLocal#getSaleChannelsByFilters(co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	@Override
	public SaleChannelResponse getSaleChannelsByFilters(
			GetSaleChannelsByFiltersRequestDTO request,
			RequestCollectionInfoDTO requestCollectionInfo)
			throws BusinessException {
		log.debug("== Inicia deleteSaleChanel/SaleChanelBusinessBean ==");
        UtilsBusiness.assertNotNull(request.getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se especificó el identificador del país");
        try {
        	RequestCollectionInfo requestCollInfoPojo = UtilsBusiness.copyObject(RequestCollectionInfo.class, requestCollectionInfo);
        	SaleChannelResponse response = daoSaleChanel.getSaleChannelsByFilters(request, requestCollInfoPojo);
        	List<SaleChanel> listoPojo = response.getChannelPojos();
        	List<SaleChanelVO> listVos = UtilsBusiness.convertList(listoPojo, SaleChanelVO.class);
        	response.setChannelPojos(null);
        	response.setChannelVos(listVos);
        	return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSaleChanel/SaleChanelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSaleChanel/SaleChanelBusinessBean ==");
        }
	}
}
