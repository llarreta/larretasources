package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.DealersServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.IBSSPRMSupportAndReadinessBrokerLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessDetailException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.ChannelType;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerStatus;
import co.com.directv.sdii.model.pojo.DealerType;
import co.com.directv.sdii.model.pojo.MediaContactType;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.ChannelTypesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealerStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.PostalCodesDAOLocal;
import co.com.directv.sdii.ws.model.dto.DealersWSDTO;

/**
 * Interfaz EJB que Implementa las operaciones de negocio para la Entidad de
 * Dealers(Operaciones diferentes del CRUD)
 * @author Joan Lopez
 * @author Leonardo Cardozo Cadavid
 * @version 1.0
 * @see
 */
@Stateless(name="DealersBusinessBeanLocal",mappedName="ejb/DealersBusinessBeanLocal")
public class DealersBusinessBean extends IBSWSBase implements DealersBusinessBeanLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealersBusinessBean.class);
	
    @EJB(name="BuildingDAOLocal",beanInterface=BuildingDAOLocal.class)
    private BuildingDAOLocal buildingDAOLocal;
    
    @EJB(name="ChannelTypesDAOLocal",beanInterface=ChannelTypesDAOLocal.class)
    private ChannelTypesDAOLocal crudChannel;
    
    @EJB(name="DealerBuldingDAOLocal",beanInterface=DealerBuldingDAOLocal.class)
    private DealerBuldingDAOLocal dealerBuildingDAOLocal;
    
    @EJB(name="DealerStatusDAOLocal",beanInterface=DealerStatusDAOLocal.class)
    private DealerStatusDAOLocal crudDealerStatus;
    
    @EJB(name="DealerTypesDAOLocal",beanInterface=DealerTypesDAOLocal.class)
    private DealerTypesDAOLocal crudDealerType;
    
    @EJB(name="PostalCodesDAOLocal",beanInterface=PostalCodesDAOLocal.class)
    private PostalCodesDAOLocal crudPostalCode;
   	
    @EJB(name="CountriesDAOLocal",beanInterface=CountriesDAOLocal.class)
    private CountriesDAOLocal countriesDao;
    
    @EJB(name="SystemParameterDAOLocal",beanInterface=SystemParameterDAOLocal.class)
    private SystemParameterDAOLocal systemParameterDao;
	
    @EJB(name="DealersDAOLocal",beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal dealersDao;
    
    @EJB(name="ServiceDAOLocal",beanInterface=ServiceDAOLocal.class)
    private ServiceDAOLocal serviceDAO;

    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
    
    @EJB(name="DealerCoverageDAOLocal", beanInterface=DealerCoverageDAOLocal.class)
	private DealerCoverageDAOLocal dealerCoverageDAO;
    
    @EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
    private WorkOrderDAOLocal workOrderDAO;

    @EJB(name="IBSSPRMSupportAndReadinessBrokerLocal", beanInterface=IBSSPRMSupportAndReadinessBrokerLocal.class)
    private IBSSPRMSupportAndReadinessBrokerLocal ibsSPRMSupportAndReadinessBrokerLocal;
    
    @EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal dealersDAOLocal;
    
    
    /**
     *
     * Obtiene la información de un dealer desde el servicio IBS
     * @param dealerCode código del dealer en IBS
     * @param depotCode  código de depot en IBS
     * @return DealerVO Objeto del dominio del sistema con la información obtenida
     * @throws BusinessException en caso de error al tratar de obtener la inforamción del dealer
     * @author jalopez
     */
    @Override
    public DealerVO getDealer(Long dealerCode, String depotCode, Long countryId) throws BusinessException {

        try {
            log.debug("Iniciando llamado a servicio web");
            Country country = getCountry(countryId);
            
            DealersWSDTO dealerWS = ibsSPRMSupportAndReadinessBrokerLocal.getDealerInfo(dealerCode,depotCode, country);
            
            if (dealerWS == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return this.convertObjectToVO(dealerWS);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealer/DealersBusinessBean ==");
        	if(ex instanceof BusinessDetailException){
				throw new BusinessDetailException(((BusinessDetailException) ex).getMessageCode(),ex.getMessage(),((BusinessDetailException) ex).getParameters());
			}
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealer/DealersBusinessBean ==");
        }
    }
    
    /**
	 * Metodo: Si el edificio con codigo 'ibsBuildingCode' esta en la tabla BUILDINGS regresa los dealers que atienden ese edificio. 
	 *         Si no hay dealers activos que atiendan este edificio, regresa los dealers activos cuya covertura esta asociada al 'postalCode'.  
	 *         Si el edificio con codigo 'ibsBuildingCode' NO ESTA en la tabla BUILDINGS, regresa los dealers activos cuya covertura esta asociada al 'postalCode'.   
     * @param ibsBuildingCode Identificador del edificio.
     * @param postalCode Zona postal del edificio donde se va a prestar el servicio.
     * @return Regresa los dealers que atienden ese edificio. 
     * @throws BusinessException
     * @throws BusinessDetailException <tipo> <descripcion>
     * @author rdelarosa
     */   
   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public List<DealerVO> getDealersWhoAttendsABuildingOrAttendsBuildingsIntoPostalCode( String ibsBuildingCode, 
		   																				String postalCode, 
		   																				String countryCode
   ) throws BusinessException {

      List<DealerVO> listDealers = null ;
      List<Dealer> listDealersPOJOs = null ;
      Long longIbsBuildingCode = null ;
      Building building = null ;
      Dealer dealer = null ;
      // DealerVO dealerVO = null ;
      int i=0;
      boolean buscarDealersQueAtiendanEdificios = false ;
      PostalCode objPostalCode = null ;
      
	  try {
		   	log.debug("=== Iniciando getDealersWhoAttendsABuildingOrAttendsBuildings//DealersBusinessBean ===");
		   	listDealers = new ArrayList<DealerVO>() ;
			if ( ibsBuildingCode==null || ibsBuildingCode.trim().length() == 0) {
		    	return listDealers ;
		    }
		    longIbsBuildingCode = Long.parseLong(ibsBuildingCode);
		    
		    building = buildingDAOLocal.getBuildingByIbsBuldingCodeAndCountryCode(longIbsBuildingCode, countryCode);
		    
		    if ( building != null ) {
		       /*
		       Consultar si alguno de los dealers recibidos como entrada tiene configurado ese edificio en la 
		       tabla dealers_buildings
		       */	
		       listDealersPOJOs = dealerBuildingDAOLocal.getDealersWhoAttendsABuilding(longIbsBuildingCode, countryCode);
		       
		       if ( ( listDealersPOJOs!=null ) && (listDealersPOJOs.size() > 0 ) ) {
		    	   
		    	   for ( i=0 ; i<listDealersPOJOs.size() ; i++ ){
		    		   dealer = listDealersPOJOs.get(i);
		    		   listDealers.add( UtilsBusiness.copyObject(DealerVO.class, dealer) );
		    	   }
		    	   // return listDealers ;	   
		       }else {
		    	  // Paso 4
		    	  buscarDealersQueAtiendanEdificios = true ;
		       }
		    }else {
		    	// Paso 4
		    	buscarDealersQueAtiendanEdificios = true ;
		    }
		    
		    if (buscarDealersQueAtiendanEdificios) {
		    	/* buscar dealers Que Atiendan Edificios en un 'postalCode' de un 'countryCode' */
		    	objPostalCode = crudPostalCode.getPostalCodesByCodeByCountryCode(postalCode, countryCode);
		    	listDealersPOJOs = this.dealersDao.getAllActiveWhoAttendsAPostalZoneAndIsAllocAndAttendsBuildings(objPostalCode);
		    	
		    	if ( ( listDealersPOJOs!=null ) && (listDealersPOJOs.size() > 0 ) ) {
			    	   
			    	   for ( i=0 ; i<listDealersPOJOs.size() ; i++ ){
			    		   dealer = listDealersPOJOs.get(i);
			    		   listDealers.add( UtilsBusiness.copyObject(DealerVO.class, dealer) );
			    	   }
			    }
		    }
		    return listDealers ;
	  }catch (Throwable ex) {
		  throw this.manageException(ex);
	  }finally {
		  log.debug("=== Finalizando getDealersWhoAttendsABuildingOrAttendsBuildings//DealersBusinessBean ===");
	  }
	  // return listDealers ;
   }
   
   
    /**
     * Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
	 * Metodo: Si el edificio con id de edificio esta en la tabla BUILDINGS regresa el dealer que atiende ese edificio. 
	 *         Si no hay dealer que atienda este edificio, retorna una lista vacia.  
	 *         Si el edificio con id 'buildingId' NO ESTA en la tabla BUILDINGS, retorna una lista vacia.   
     * @param buildingId Identificador del edificio.
     * @return Regresa el dealer que atienden ese edificio. 
     * @throws BusinessException
     * @throws BusinessDetailException <tipo> <descripcion>
     * @author ialessan
     */   
   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public List<DealerVO> getDealersWhoAttendsABuilding(	Long  buildingId) throws BusinessException {

      List<DealerVO> listDealers = null ;      
      Building building = null ;
	  try {
		   	log.debug("=== Iniciando getDealersWhoAttendsABuilding/DealersBusinessBean ===");
		   	
		   	listDealers = new ArrayList<DealerVO>() ;
		   	
			if ( buildingId==null ) {
		    	return listDealers ;
		    }
			
		    //Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
		    building = buildingDAOLocal.getBuildingByID(buildingId);
		    
		    if ( building != null && building.getDealerId() != null ) {
		    		    		
		    		Dealer dealerAtiendeEdificio = dealersDAOLocal.getDealerByID(building.getDealerId());
		    		//se mantiene la lista por el tipo de retorno pero siempre tendrá un solo elemento para este caso
		    		//un edificio solo tiene un dealer asignado		    		
		    		listDealers.add(UtilsBusiness.copyObject(DealerVO.class, dealerAtiendeEdificio));
		    }
		    //si el edificio no tiene asociado un dealer se retorna la lista vacia
		    return listDealers ;
		    
	  }catch (Throwable ex) {
		  throw this.manageException(ex);
	  }finally {
		  log.debug("=== Finalizando getDealersWhoAttendsABuilding/DealersBusinessBean ===");
	  }
	  // return listDealers ;
   }
   
    /**
     * Metodo: <Descripcion>
     * @param countryId
     * @return
     * @throws BusinessException
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    private Country getCountry(Long countryId) throws BusinessException, DAOServiceException, DAOSQLException {
    	if(countryId==null || countryId.longValue() <= 0){
			log.debug("== Parametro Requerido nulo [countryId] ==");
			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
		Country country = countriesDao.getCountriesByID(countryId);
		return country;
	}
	
	/**
     *
     * Metodo: <Descripcion>
     * @return ArrayList<DealersCodesDTO>
     * @throws BusinessException
     * @author jalopez
     */
    @Override
    public List<DealerVO> getDealerCodes(Long countryId) throws BusinessException {
    	log.debug("=== Iniciando llamado a servicio web getDealerCodes ===");        
 
        try {
        	if(countryId==null || countryId.longValue() <= 0){
    			log.debug("== Parametro Requerido nulo [countryId] ==");
    			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		}
        	List<DealerVO> response = new ArrayList<DealerVO>();
        	Map dealerMap = new HashMap<String,DealerVO>();
        	Country country = getCountry(countryId);
        	if(country==null){
    			log.debug("== Parametro Country no encontrado [countryId = "+countryId+"] == ");
    			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		}
        	List<DealerVO> listVO = ibsSPRMSupportAndReadinessBrokerLocal.getDealerCodes(country);
        	
        	List<Long> listLongs = new ArrayList<Long>();
        	for(DealerVO dealerVO: listVO){
        		listLongs.add(dealerVO.getDealerCode());
        	}
        	
        	StringBuilder sb = new StringBuilder();
        	sb.append( UtilsBusiness.longListToOrInQuery(listLongs, "D.DEALER_CODE"));
        	for( DealerVO dealerVO : listVO ){
        		dealerMap.put(dealerVO.getDealerCode(), dealerVO);
        	}
        	List<Long> listCreatedInSD = dealersDao.getDealersCodesByDealerCode(sb.toString());
        	for( Long code : listCreatedInSD ){
        		if( dealerMap.containsKey(code) )
        			dealerMap.remove(code);
        	}
        	Collection collection = dealerMap.values();
        	Iterator itKeys = collection.iterator();
        	while (itKeys.hasNext()){
        		response.add( (DealerVO) itKeys.next() );
        	}
            return response;
        
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerCodes/DealersBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerCodes/DealersBusinessBean ==");
        }
    }

    /**
     * 
     * @param dealerWS
     * @return
     * @throws BusinessException
     * @throws HelperException 
     * @throws PropertiesException 
     * @throws DAOSQLException 
     * @throws DAOServiceException 
     */
    private DealerVO convertObjectToVO(DealersWSDTO dealerWS) throws BusinessException, PropertiesException, HelperException, DAOServiceException, DAOSQLException {

    	log.error("== Iniciando Llamado a  convertObjectToVO/DealersBusinessBean ==");
    	
        DealerVO dealerVO = new DealerVO();
        dealerVO.setDealerName(dealerWS.getDealerName());
        dealerVO.setLegalRepresentative(dealerWS.getLegalRepresentative());
        dealerVO.setNit(dealerWS.getNit());
        dealerVO.setAddress(dealerWS.getAddress());
        dealerVO.setScore(dealerWS.getScore());
        dealerVO.setDepotCode(dealerWS.getDepotCode());
        dealerVO.setDealerCode( Long.parseLong( dealerWS.getDealerCode()) );

        /*PostalCode Configuration*/
        PostalCode pc = crudPostalCode.getPostalCodesByCodeByCountryCode(dealerWS.getPostalCode(),dealerWS.getCountry());
        if(pc != null){
            dealerVO.setPostalCode(pc);
        }else{
        	String message = "No se obtuvo informacion en SDII para el Codigo Postal : "+dealerWS.getPostalCode()+" Pais : "+dealerWS.getCountry(); 
        	log.error(message);
        	throw new BusinessException(ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode(),message);
        }
        
        if(dealerWS.getPrincipalDealerCode() != null && dealerWS.getPrincipalDepotCode() != null && dealerWS.getPrincipalDealerCode().trim().length() > 0 && dealerWS.getPrincipalDepotCode().trim().length() > 0){
        	String countryCode = dealerWS.getCountry();
        	Long parentDealerCode = Long.parseLong( dealerWS.getPrincipalDealerCode() );
        	if(parentDealerCode.longValue() > 0){
	        	Country country = countriesDao.getCountriesByCode(countryCode);
	        	UtilsBusiness.assertNotNull(country, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se encontró el país con el código especificado: " + countryCode);
	        	Dealer parentDealer = dealersDao.getDealerByDealerCodeAndCountryId( parentDealerCode, country.getId());
	        	if(parentDealer == null){
	        		String error = "No se ha encontrado el dealer principal para esta sucursal, el código de dealer principal es: " + dealerWS.getPrincipalDealerCode() + " del país: " + countryCode + " Se debe crear primero el dealer principal";
	        		throw new BusinessException(ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage() + error);
	        	}
	        	dealerVO.setDealer(parentDealer);
        	}
        }
        /*Status Dealer Configuration*/      
        DealerStatus dealerStatus = crudDealerStatus.getDealerStatusByCode(dealerWS.getStatusCode());
        if(dealerStatus != null){        
            dealerVO.setDealerStatus(dealerStatus);
        }else{
        	log.error("== No se obtuvo informacion en SDII para el Estado del Dealer con el codigo: "+dealerWS.getStatusCode()+"==");
        	throw new BusinessException(ErrorBusinessMessages.DEALERS_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.DEALERS_STATUS_NOT_EXIST.getMessage());
        }
        
        /*Channel Type Configuration*/       
        ChannelType channelType = crudChannel.getChannelTypesByCode(dealerWS.getChannelTypeCode());
        //jjimenezh 2010-10-07 Isabel y Milton Jiménez piden que si no se encuentra en SDII el channelType con el código de IBS se asigne el ChannelType de instalador con código I
        if(channelType == null){
        	dealerWS.setChannelTypeCode(CodesBusinessEntityEnum.CHANNEL_TYPE_INSTALLER.getCodeEntity());
        	channelType = crudChannel.getChannelTypesByCode(CodesBusinessEntityEnum.CHANNEL_TYPE_INSTALLER.getCodeEntity());
        }
        
        if(channelType != null){
            dealerVO.setChannelType(channelType);
        }else{
        	log.error("== No se obtuvo informacion en SDII para el Canal del Dealer con el codigo: "+dealerWS.getChannelTypeCode()+"==");
        	throw new BusinessException(ErrorBusinessMessages.DEALERS_CHANNEL_NOT_EXIST.getCode(),ErrorBusinessMessages.DEALERS_CHANNEL_NOT_EXIST.getMessage());
        }

        /*Dealer Type Configuration*/          
    	DealerType dealerType = crudDealerType.getDealerTypesByCode(dealerWS.getDealerTypeCode());   
    	if( dealerType != null ){
    		dealerVO.setDealerType(dealerType); 
    	}else{
    		log.error("== No se obtuvo informacion en SDII para el Tipo del Dealer con el codigo: "+dealerWS.getDealerTypeCode()+"==");
        	throw new BusinessException(ErrorBusinessMessages.DEALERS_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.DEALERS_TYPE_NOT_EXIST.getMessage());
    	}      

        this.fillMediaContacts(dealerWS, dealerVO);

        log.debug("== Fin Llamado a  convertObjectToVO/DealersBusinessBean ==");
        
        return dealerVO;
    }

    /**
     * 
     * Metodo: <Descripcion>
     * @param dealerWS
     * @param dealerVO <tipo> <descripcion>
     * @author jjimenezh
     * @throws PropertiesException 
     * @throws HelperException 
     */
    private void fillMediaContacts(DealersWSDTO dealerWS, DealerVO dealerVO) throws PropertiesException, HelperException {
        String [] mobilePhones = dealerWS.getCelPhones();
        String [] emails = dealerWS.getEmails();
        String [] homePhones = dealerWS.getHomePhones();
        String [] workPhones = dealerWS.getWorkPhones();
        String [] faxes = dealerWS.getFaxes();

        fillMediaContactTypes(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getIdEntity(MediaContactType.class.getName()), "Telefono", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity(), homePhones, dealerVO.getMediaContacts());
        fillMediaContactTypes(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getIdEntity(MediaContactType.class.getName()), "Telefono", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity(), workPhones, dealerVO.getMediaContacts());
        fillMediaContactTypes(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getIdEntity(MediaContactType.class.getName()), "Celular", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity(), mobilePhones, dealerVO.getMediaContacts());
        fillMediaContactTypes(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getIdEntity(MediaContactType.class.getName()), "Email", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getCodeEntity(), emails, dealerVO.getMediaContacts());
        fillMediaContactTypes(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getIdEntity(MediaContactType.class.getName()), "Fax", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity(), faxes, dealerVO.getMediaContacts());
    }

    /**
     * 
     * Metodo: <Descripcion>
     * @param mediaContactTypeId
     * @param mediaContactTypeName
     * @param mediaContactTypeCode
     * @param mediaContactValues
     * @param targetMediaContacts <tipo> <descripcion>
     * @author jjimenezh
     */
    private void fillMediaContactTypes(Long mediaContactTypeId, String mediaContactTypeName, String mediaContactTypeCode, String [] mediaContactValues, List<DealerMediaContactVO> targetMediaContacts){
        MediaContactType mediaContactType = buidMediaContactType(mediaContactTypeId, mediaContactTypeName, mediaContactTypeCode);

        DealerMediaContactVO dealerMediaContact = null;

        if(mediaContactValues == null || mediaContactValues.length == 0){
            return;
        }

        for(String value : mediaContactValues){
            dealerMediaContact = new DealerMediaContactVO();
            dealerMediaContact.setMediaContactType(mediaContactType);
            dealerMediaContact.setMediaContactValue(value);
            targetMediaContacts.add(dealerMediaContact);
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.DealersBusinessBeanLocal#getDealersForSaleAndInstallSameDealerSkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersForSaleAndInstallSameDealerSkill(SkillEvaluationDTO parameters) throws BusinessException {
		log.debug("== Inicia getDealersForSaleAndInstallSameDealerSkill/DealersBusinessBean ==");
		final String REGEX=",";
		try{
//			String paramsNullCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
//			String paramsNullMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			if( parameters == null ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL040.getCode(),ErrorBusinessMessages.ALLOCATOR_AL040.getMessage());
			} else if( parameters.getServices() == null || parameters.getServices().isEmpty() ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL045.getCode(),ErrorBusinessMessages.ALLOCATOR_AL045.getMessage());
			} else if( parameters.getCountryId() == null ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL032.getCode(),ErrorBusinessMessages.ALLOCATOR_AL032.getMessage());
			}
			List<DealerVO> response = new ArrayList<DealerVO>();
			//1. Se valida que por lo menos un servicio sea de super categoria de instalacion
			Long countServicesOfInstallation = serviceDAO.calculateWoServicesInSuperCategory(parameters.getServices(), CodesBusinessEntityEnum.SUPER_CATEGORY_INSTALLATION.getCodeEntity());
			
			if(countServicesOfInstallation == null || countServicesOfInstallation.longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL009.getCode(), ErrorBusinessMessages.ALLOCATOR_AL009.getMessage());
			}
			
			//2. Se consulta el parametro CODE_SYS_PARAM_ENABLED_SALE_INSTALL_UPGRADE_DOWNGRADE para determinar 
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
			
			if( parameters.getIbsSaleDealerCode() == null || parameters.getIbsSaleDealerCode().trim().equals("") ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL010.getCode(), ErrorBusinessMessages.ALLOCATOR_AL010.getMessage());
			}
				//3. Se valida que la lista de dealers venga vacia: si viene vacia se consulta el dealer vendedor que viene 
				// por parametro y se retorna en el resultado
			if( parameters.getDealerList() == null || parameters.getDealerList().isEmpty() ){
				Dealer saleDealer = dealersDao.getDealerByDealerCodeAndCountryIdAndStatusCode( new Long(parameters.getIbsSaleDealerCode()), parameters.getCountryId(),CodesBusinessEntityEnum.DEALER_STATUS_NORMAL.getCodeEntity());
				if( saleDealer == null ){
					throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL011.getCode(), ErrorBusinessMessages.ALLOCATOR_AL011.getMessage());
				}
				response.add( UtilsBusiness.copyObject(DealerVO.class, saleDealer) );
				return response;
			}
			
			for( DealerVO dealer : parameters.getDealerList() ){
				//4. Si la lista que viene por parametro contiene el dealer que realiza la venta, se retorna ese dealer
				if( dealer.getDealerCode()!= null && dealer.getDealerCode().toString().equals( parameters.getIbsSaleDealerCode() ) ){
					response.add( UtilsBusiness.copyObject(DealerVO.class, dealer) );
				}
			}
			
			return response;
		}catch (Throwable e) {
			log.debug("== Error getDealersForSaleAndInstallSameDealerSkill/DealersBusinessBean ==",e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getDealersForSaleAndInstallSameDealerSkill/DealersBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersBusinessBeanLocal#getDealersForEvenOrOddCustomerCodeSkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersForEvenOrOddCustomerCodeSkill(SkillEvaluationDTO parameters) throws BusinessException {log.debug("== Inicia getDealersForSaleAndInstallSameDealerSkill/DealersBusinessBean ==");
		log.debug("== Inicia getDealersForEvenOrOddCustomerCodeSkill/DealersBusinessBean ==");
		try{
			if( parameters == null ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL040.getCode(),ErrorBusinessMessages.ALLOCATOR_AL040.getMessage());
			} else if( parameters.getIbsCustomerCode() == null || parameters.getIbsCustomerCode().equals("") ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL039.getCode(),ErrorBusinessMessages.ALLOCATOR_AL039.getMessage());
			} else if( parameters.getCountryId() == null || parameters.getCountryId().longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL032.getCode(),ErrorBusinessMessages.ALLOCATOR_AL032.getMessage());
			}
			List<DealerVO> response = new ArrayList<DealerVO>();
			//Se obtiene el penultimo numero del IBSCustomerCode
			String strIbsCustomerCode = parameters.getIbsCustomerCode();
			Long customerCode = new Long( strIbsCustomerCode.substring(strIbsCustomerCode.length()-2, strIbsCustomerCode.length()-1) );
			String codeEvenOrOdd = "";
			//Se verifica si el codigo del cliente es par y se obtiene el codigo del dealer parametrizado para los clientes par
			if( ( customerCode.intValue() % 2 ) == 0 ){
				codeEvenOrOdd = CodesBusinessEntityEnum.DEALER_DETAIL_ATTEND_TYPE_EVEN.getCodeEntity();
			} else{
				codeEvenOrOdd = CodesBusinessEntityEnum.DEALER_DETAIL_ATTEND_TYPE_ODD.getCodeEntity();
			}
			response =  UtilsBusiness.convertList(this.dealersDao.getDealerEvenOrOddByCountryId(parameters.getCountryId(), codeEvenOrOdd), DealerVO.class) ;
			
			return response;
		}catch (Throwable e) {
			log.debug("== Error getDealersForEvenOrOddCustomerCodeSkill/DealersBusinessBean ==",e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getDealersForEvenOrOddCustomerCodeSkill/DealersBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersBusinessBeanLocal#getAllActiveByPostalCode(co.com.directv.sdii.model.pojo.PostalCode)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getAllActiveByPostalCode(PostalCode postalCode)throws BusinessException {
		log.debug("== Inicia getAllActiveByPostalCode/DealersBusinessBean ==");
		UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<DealerVO> dealersVO = null;
		try {
			List<Dealer> dealers = dealersDao.getAllActiveByPostalCode(postalCode);
			dealersVO = UtilsBusiness.convertList(dealers, DealerVO.class);
		} catch (Throwable e) {
			log.error("== Error getAllActiveByPostalCode/DealersBusinessBean == " + e.getMessage());
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getAllActiveByPostalCode/DealersBusinessBean ==");
		}
		return dealersVO;
	}
    
    
}
