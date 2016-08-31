package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.WarehouseElementStockDTO;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElementStock;
import co.com.directv.sdii.model.pojo.collection.WarehouseElemetStockResponse;
import co.com.directv.sdii.model.vo.WarehouseElementStockVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementStockDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;
import co.com.directv.sdii.ws.model.dto.ResponseWareHouseIndicatorAlarmDTO;

/**
 * EJB que implementa las operaciones Tipo CRUD WarehouseElementStock
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementStockDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal
 */
@Stateless(name="WarehouseElementStockBusinessBeanLocal",mappedName="ejb/WarehouseElementStockBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WarehouseElementStockBusinessBean extends BusinessBase implements WarehouseElementStockBusinessBeanLocal {

    @EJB(name="WarehouseElementStockDAOLocal", beanInterface=WarehouseElementStockDAOLocal.class)
    private WarehouseElementStockDAOLocal daoWarehouseElementStock;
    
    @EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
    private WarehouseDAOLocal daoWarehouse;
    
    @EJB( name="WarehouseElementDAOLocal",beanInterface=WarehouseElementDAOLocal.class )
    private WarehouseElementDAOLocal daoWareHouseElement;
    
    @EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
    private EmailTypeBusinessBeanLocal businessEmailType;
    
    @EJB(name="UserDAOLocal",beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    @EJB(name="WarehouseBusinessBeanLocal",beanInterface=WarehouseBusinessBeanLocal.class)
    private WarehouseBusinessBeanLocal warehouseBusiness;
    
    private final static Logger log = UtilsBusiness.getLog4J(WarehouseElementStockBusinessBean.class);

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal#getAllWarehouseElementStocks()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WarehouseElementStockVO> getAllWarehouseElementStocks() throws BusinessException {
        log.debug("== Inicia getAllWarehouseElementStocks/WarehouseElementStockBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoWarehouseElementStock.getAllWarehouseElementStocks(), WarehouseElementStockVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllWarehouseElementStocks/WarehouseElementStockBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllWarehouseElementStocks/WarehouseElementStockBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal#getWarehouseElementStocksByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WarehouseElementStockVO getWarehouseElementStockByID(Long id) throws BusinessException {
        log.debug("== Inicia getWarehouseElementStockByID/WarehouseElementStockBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            WarehouseElementStock objPojo = daoWarehouseElementStock.getWarehouseElementStockByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            WarehouseElementStockVO warehouseElementStockVO=UtilsBusiness.copyObject(WarehouseElementStockVO.class, objPojo);
            
            Warehouse warehouse=warehouseElementStockVO.getWarehouse();
            WarehouseVO warehouseVO = null;
            if(warehouse != null){
            	warehouseVO=UtilsBusiness.copyObject(WarehouseVO.class, warehouse);
            	warehouseBusiness.genWareHouseName(warehouseVO);
            	warehouseElementStockVO.setWareHouseName(warehouseVO.getWarehouseName());
            	warehouseElementStockVO.setDealerDepotPlusName(warehouseVO.getDealerDepotPlusName());
            	warehouseElementStockVO.setDealerBranchDepotPlusName(warehouseVO.getDealerBranchDepotPlusName());
            	
            }
            
            return warehouseElementStockVO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseElementStockByID/WarehouseElementStockBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseElementStockByID/WarehouseElementStockBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal#createWarehouseElementStock(co.com.directv.sdii.model.vo.WarehouseElementStockVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException {
        log.debug("== Inicia createWarehouseElementStock/WarehouseElementStockBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	Warehouse warehouse = daoWarehouse.getWarehouseByID(obj.getWarehouse().getId());
        	
        	List<WarehouseElementStock> currentStock = daoWarehouseElementStock.getWarehEleStockByEleTypeIdAndDealerIdAndWarehId(obj.getElementType().getId(), warehouse.getDealerId().getId(), warehouse.getId());
        	
        	if(! currentStock.isEmpty()){
        		log.error("Ya existe un registro de existencias del elemento de inventario en esa bodega de esa compañía");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
            WarehouseElementStock objPojo =  UtilsBusiness.copyObject(WarehouseElementStock.class, obj);
            daoWarehouseElementStock.createWarehouseElementStock(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createWarehouseElementStock/WarehouseElementStockBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWarehouseElementStock/WarehouseElementStockBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal#updateWarehouseElementStock(co.com.directv.sdii.model.vo.WarehouseElementStockVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException {
        log.debug("== Inicia updateWarehouseElementStock/WarehouseElementStockBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	Warehouse warehouse = daoWarehouse.getWarehouseByID(obj.getWarehouse().getId());
        	List<WarehouseElementStock> currentStock = daoWarehouseElementStock.getWarehEleStockByEleTypeIdAndDealerIdAndWarehId(obj.getElementType().getId(), warehouse.getDealerId().getId(), warehouse.getId());
        	
        	if(! currentStock.isEmpty()){
        		for (WarehouseElementStock oldWES : currentStock) {
					if(oldWES.getId().longValue() != obj.getId().longValue()){
						log.error("Ya existe un registro de existencias del elemento de inventario en esa bodega de esa compañía");
		        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
					}
        		}
        	}
        	
            WarehouseElementStock objPojo =  UtilsBusiness.copyObject(WarehouseElementStock.class, obj);
            daoWarehouseElementStock.updateWarehouseElementStock(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateWarehouseElementStock/WarehouseElementStockBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWarehouseElementStock/WarehouseElementStockBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal#deleteWarehouseElementStock(co.com.directv.sdii.model.vo.WarehouseElementStockVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException {
        log.debug("== Inicia deleteWarehouseElementStock/WarehouseElementStockBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            WarehouseElementStock objPojo =  UtilsBusiness.copyObject(WarehouseElementStock.class, obj);
            daoWarehouseElementStock.deleteWarehouseElementStock(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteWarehouseElementStock/WarehouseElementStockBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWarehouseElementStock/WarehouseElementStockBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal#getWarehouseElementStockByElementCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElementStockVO> getWarehouseElementStockByElementCode(
			String code) throws BusinessException {
		log.debug("== Inicia getWarehouseElementStockByElementCode/WarehouseElementStockBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<WarehouseElementStock> objPojos = daoWarehouseElementStock.getWarehouseElementStockByElementCode(code);
            return UtilsBusiness.convertList(objPojos, WarehouseElementStockVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseElementStockByElementCode/WarehouseElementStockBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseElementStockByElementCode/WarehouseElementStockBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal#getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElementStockDTO getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(
			String elementTypeCode, Long dealerId, String warehouseCode, Long countryId, RequestCollectionInfoDTO requestCollInfo, Long dealerBranchId)
			throws BusinessException {
		log.debug("== Inicia getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode/WarehouseElementStockBusinessBean ==");
        
		if((elementTypeCode == null 
			|| elementTypeCode.trim().length() == 0) 
		   && (dealerId == null) 
		   && (warehouseCode == null 
			   || warehouseCode.trim().length() == 0)
		){
			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
        try {        	
        	WarehouseElemetStockResponse response = daoWarehouseElementStock.getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(elementTypeCode, dealerId, warehouseCode, countryId, requestCollInfo, dealerBranchId);
        	List<WarehouseElementStock> objPojos = response.getWhElementsStock();
        	WarehouseElementStockDTO whElementsStockDTO = UtilsBusiness.copyObject(WarehouseElementStockDTO.class, response);        	
        	whElementsStockDTO.setWhElementsStock( UtilsBusiness.convertList(objPojos, WarehouseElementStockVO.class) );
        	whElementsStockDTO = warehouseBusiness.setNameWarehouseElementStockList(whElementsStockDTO); //Se colocan nombres de las bodegas
            return whElementsStockDTO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode/WarehouseElementStockBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode/WarehouseElementStockBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal#getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean checkShortcomingInWarehouse( Long warehouse,Long user )throws BusinessException
	{
		log.debug("== Inicia checkShortcomingInWarehouse/WarehouseElementStockBusinessBean ==");
		String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
		String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessageCode();
		
		try {
			
			UtilsBusiness.assertNotNull(warehouse, errorCode,errorMessage);
			UtilsBusiness.assertNotNull(user, errorCode,errorMessage);
			
			List<WarehouseElementStock> list = getWarehouseElementStockByWareHouseId( warehouse );
			List<ElementType>serializedElements    = daoWareHouseElement.getElementsTypesInWareHouseByType( warehouse ,true);
			List<ElementType>notSerializedElements = daoWareHouseElement.getElementsTypesInWareHouseByType( warehouse ,false);
			Warehouse wareHousePojo                = daoWarehouse.getWarehouseByID( warehouse );
			User userPojo                          = daoUser.getUserById(user);
			
			UtilsBusiness.assertNotNull(wareHousePojo, errorCode,errorMessage);
			UtilsBusiness.assertNotNull(userPojo, errorCode,errorMessage);
			
			StringBuffer mailContent           = new StringBuffer("\n" ); 
			boolean alarmIsActive              = false;
			
		    mailContent.append(ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue()+"\t"+ApplicationTextEnum.QUANTITY_MIN_STOCK.getApplicationTextValue()+"\t"+ApplicationTextEnum.QUANTITY_WAREHOUSE.getApplicationTextValue()+"\n");
		    
			  for(ElementType element:serializedElements){
				  Double totalElementsInWareHouse = daoWareHouseElement.getCurrentQuantityInWarehouseByElementType(warehouse, element.getId(),null);
				  String line = comparisonElementStockQuantityVsWareHouseElementQuantity(list, element, totalElementsInWareHouse);
				   if(line!=null){
					   alarmIsActive = true;
					   if(mailContent.toString().indexOf(line)==-1){
						   mailContent.append(line);
					   }
				  }
			  }

			  
			  for(ElementType element:notSerializedElements){
				  Double totalElementsInWareHouse = daoWareHouseElement.getCurrentQuantityInWarehouseByElementType(warehouse, element.getId(),null);
				  String line = comparisonElementStockQuantityVsWareHouseElementQuantity(list, element, totalElementsInWareHouse);
				   if(line!=null){
					   alarmIsActive = true;
					   if(mailContent.toString().indexOf(line)==-1){
						   mailContent.append(line);
					   }
				  }
			  }
			
			 
			  if( alarmIsActive && wareHousePojo.getResponsibleEmail()!=null )
				 sendMailTocheckShortcomingInWarehouse( wareHousePojo.getResponsibleEmail() ,mailContent.toString(),userPojo);
			  
			  return alarmIsActive;
			  
	        } catch(Throwable ex){
	        	log.error("== Error checkShortcomingInWarehouse/WarehouseElementStockBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina checkShortcomingInWarehouse/WarehouseElementStockBusinessBean ==");
	        }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal#checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ResponseWareHouseIndicatorAlarmDTO> checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser( Long dealerId,String warehouseType,Long user )throws BusinessException
	{
		
		log.debug("== Inicia checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser/WarehouseElementStockBusinessBean ==");
		String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
		String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessageCode();
		boolean alarmIsActive = false;
		String warehouseName="";
		List<ResponseWareHouseIndicatorAlarmDTO> response = new ArrayList<ResponseWareHouseIndicatorAlarmDTO>();
		boolean withbranch=true;
		try {
			
			UtilsBusiness.assertNotNull(dealerId, errorCode,errorMessage);
			UtilsBusiness.assertNotNull(warehouseType, errorCode,errorMessage);
			UtilsBusiness.assertNotNull(user, errorCode,errorMessage);
			User userPojo = daoUser.getUserById(user);
			UtilsBusiness.assertNotNull(userPojo, errorCode,errorMessage);
			
			
			List<Warehouse> warehouses=daoWarehouse.getWarehousesByDealerIdAndWhTypeCodeWithBranch(dealerId,warehouseType,withbranch);
			
			for (Warehouse warehouse : warehouses) {
				
				alarmIsActive = false;
				warehouseName="";
				
				List<WarehouseElementStock> list = getWarehouseElementStockByWareHouseId( warehouse.getId() );
				if(list != null  && !list.isEmpty()){
				
					StringBuffer mailContent = new StringBuffer("\n" ); 
					mailContent.append( ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue()+"\t"+ApplicationTextEnum.QUANTITY_MIN_STOCK.getApplicationTextValue()+"\t"+ApplicationTextEnum.QUANTITY_WAREHOUSE.getApplicationTextValue()+"\n" );
				    
				    WarehouseVO warehouseVO=UtilsBusiness.copyObject(WarehouseVO.class, warehouse);
					warehouseBusiness.genWareHouseName(warehouseVO);
					warehouseName = warehouseVO.getWarehouseName();
					
				    for (WarehouseElementStock stock : list) {
				    	
						  Double totalElementsInWareHouse = daoWareHouseElement.getCurrentQuantityInWarehouseByElementType(warehouse.getId(), stock.getElementType().getId(),null);
						  String line = comparisonElementStockQuantityVsWareHouseElementQuantity(stock, stock.getElementType(), totalElementsInWareHouse);
					      if(line!=null){
			                   alarmIsActive = true;
			                   if(mailContent.toString().indexOf(line)==-1){
			                        mailContent.append(line);
			                   }
						  }
				    }
						
				    if(alarmIsActive && warehouse.getResponsibleEmail()!=null){
						sendMailTocheckShortcomingInWarehouse(warehouse.getResponsibleEmail(),mailContent.toString(),userPojo);
				    }
				
					response.add(new ResponseWareHouseIndicatorAlarmDTO(warehouseName,alarmIsActive));
				}
			}
			
			  return response;
			  
	        } catch(Throwable ex){
	        	log.error("== Error checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser/WarehouseElementStockBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser/WarehouseElementStockBusinessBean ==");
	        }
		
	}

	
	/**
	 * Metodo: permite comparar la cantidad de elemenentos de una bodega contra la cantidad minima definida en los
	 * stocks de una bodega
	 * @return un String que al ser diferente de null indica si la cantidad de eltos en bodega es menor a la definida en el stock 
	 * @throws BusinessException En caso de error al tratar de ejecutar la operacion
	 * @author garciniegas
	 */
   private String comparisonElementStockQuantityVsWareHouseElementQuantity( List<WarehouseElementStock> list,ElementType element,Double totalElementsInWareHouse )
   {
	   StringBuffer lineContent = new StringBuffer();
	   
	     for(WarehouseElementStock stock:list){
	    	if(stock.getElementType().getId().longValue()==element.getId().longValue()){
	    	  if(totalElementsInWareHouse.doubleValue()< stock.getMinQuantity().doubleValue()){
	    		  lineContent.append("\n");
	    		  lineContent.append(element.getTypeElementName());
                  lineContent.append("\t\t");
                  lineContent.append(stock.getMinQuantity().doubleValue());
                  lineContent.append("\t\t\t");
                  lineContent.append(totalElementsInWareHouse);
                  lineContent.append("\n");
	    	      return lineContent.toString();
	    	  }else{
	    		  return null;
	    	  }
	    	}
	     }
	   
	   return null;
   }
   
   /**
	 * Metodo: permite comparar la cantidad de elemenentos de una bodega contra la cantidad minima definida en los
	 * stocks de una bodega
	 * @return un String que al ser diferente de null indica si la cantidad de eltos en bodega es menor a la definida en el stock 
	 * @throws BusinessException En caso de error al tratar de ejecutar la operacion
	 * @author cduarte
	 */
  private String comparisonElementStockQuantityVsWareHouseElementQuantity( WarehouseElementStock stock,ElementType element,Double totalElementsInWareHouse )
  {
	   StringBuffer lineContent = new StringBuffer();
	   
	   if( totalElementsInWareHouse.doubleValue()< stock.getMinQuantity().doubleValue() )
	    	  {
	    		  lineContent.append( "\n" );
	    		  lineContent.append( element.getTypeElementName() );
                 lineContent.append( "\t\t" );
                 lineContent.append( stock.getMinQuantity().doubleValue() );
                 lineContent.append( "\t\t\t" );
                 lineContent.append( totalElementsInWareHouse );
                 lineContent.append( "\n" );
                 
	    	    return lineContent.toString();
	    	  }

	   return null;
  }

    private void sendMailTocheckShortcomingInWarehouse( String recipient,String content,User user ) throws BusinessException
    {
        SendEmailDTO email = new SendEmailDTO();
        
    	email.setNewsType( EmailTypesEnum.ALARM_INSUFFICIENT_STOCK_ITEMS.getEmailTypecode() );
        email.setNewsDocument( user.getIdNumber() );
        email.setNewsObservation( content );
        email.setNewsSourceUser( user.getName() );
        List<String>list = new ArrayList<String>();
        list.add( recipient );
        email.setRecipient( list );
        
        businessEmailType.sendEmailByEmailTypeCodeAsic( email, user.getCountry().getId() );
    }

   public List<WarehouseElementStock> getWarehouseElementStockByWareHouseId( Long warehouseId )throws BusinessException
   {
	   log.debug("== Inicia getWarehouseElementStockByWareHouseId/WarehouseElementStockBusinessBean ==");
	  
	try {
	  
	   UtilsBusiness.assertNotNull(warehouseId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	   return daoWarehouseElementStock.getWarehouseElementStockByWareHouseId( warehouseId );
	   
     }catch(Throwable ex){
	        	log.error("== Error getWarehouseElementStockByWareHouseId/WarehouseElementStockBusinessBean ==");
	        	throw this.manageException(ex);
	     } finally {
	            log.debug("== Termina getWarehouseElementStockByWareHouseId/WarehouseElementStockBusinessBean ==");
	     }
   }
}
