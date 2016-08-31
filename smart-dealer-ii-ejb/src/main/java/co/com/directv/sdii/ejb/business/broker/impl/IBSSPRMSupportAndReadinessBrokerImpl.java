/**
 * Creado 2012/4/10 9:07
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.IBSSPRMSupportAndReadinessBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.toa.IBSSPRMSupportAndReadinessTOA;
import co.com.directv.sdii.ejb.business.dealers.MediaContactConfiguratorHelper;
import co.com.directv.sdii.exceptions.BusinessDetailException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.ws.model.dto.DealersWSDTO;

import com.directvla.contract.businessdomain.sprmsupportandreadiness.v1_0.GetDealerCodesException;
import com.directvla.contract.businessdomain.sprmsupportandreadiness.v1_0.GetDealerInfoException;
import com.directvla.contract.businessdomain.sprmsupportandreadiness.v1_0.MoveResourceToStockHandlerException;
import com.directvla.contract.businessdomain.sprmsupportandreadiness.v1_0.ReceiveReturnedPhysicalResourceException;
import com.directvla.contract.businessdomain.sprmsupportandreadiness.v1_0.SPRMSupportAndReadinessPt;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.Category;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.DealerCode;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.Individual;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.IndividualIdentification;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.IndividualIdentificationCollection;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.NationalIdentityCardIdentification;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.UrbanPropertyAddress;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.UrbanPropertyAddressCollection;
import com.directvla.schema.businessdomain.customer.sprmsupportandreadiness.v1_0.Customer;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.GetDealerCodes;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.GetDealerCodesRequest;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.GetDealerInfo;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.GetDealerInfoRequest;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.GetDealerInfoResponse;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.GetDealerInfoResult;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.MoveResourceToStockHandlerRequest;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.MoveResourceToStockHandlerResponse;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.ReceiveReturnedPhysicalResourceRequest;
import com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.ReceiveReturnedPhysicalResourceResponse;
import com.directvla.schema.util.commondatatypes.sprmsupportandreadiness.v1_0.RequestMetadataType;
import com.directvla.schema.util.commondatatypes.sprmsupportandreadiness.v1_0.ResponseMetadataType;

/**
 * Implementación de Broker de servicios asociados con el módulo de inventarios SPRMSupportAndReadinessBrokerLocal
 * 
 * Fecha de Creación: 2012/4/10
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.IBSSPRMSupportAndReadinessBrokerLocal
 */
@Stateless(name="IBSSPRMSupportAndReadinessBrokerLocal",mappedName="ejb/IBSSPRMSupportAndReadinessBrokerLocal")
public class IBSSPRMSupportAndReadinessBrokerImpl extends IBSWSBase implements IBSSPRMSupportAndReadinessBrokerLocal, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(InventoryServiceBrokerImpl.class);

	@EJB(name="SystemParameterDAOLocal",beanInterface=SystemParameterDAOLocal.class)
    private SystemParameterDAOLocal systemParameterDao;


	@Override
	public List<DealerVO> getDealerCodes(Country country) throws BusinessException {
		log.debug("=== Iniciando llamado a servicio web getDealerCodes ===");        
		 
        try {
        	List<DealerVO> listVO = new ArrayList<DealerVO>();
        	//PtCustomer customer = ServiceLocator.getInstance().getDealerService();
        	SPRMSupportAndReadinessPt sprmSupportAndReadinessPt = ServiceLocator.getInstance().getSPRMSupportAndReadinessInventoryService();
    		
    		GetDealerCodesRequest dealerCodesReq = new GetDealerCodesRequest();
    		Random r = new Random();
    		RequestMetadataType metadata = new RequestMetadataType();
			int requestId =  r.nextInt(1000000);
			metadata.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP
					.getCodeEntity());
    		metadata.setRequestID(requestId+"");
    		metadata.setSourceID(country.getCountryCode());
    		dealerCodesReq.setRequestMetadata(metadata);
    		
    		GetDealerCodes dealerCodes = new GetDealerCodes();		
    		dealerCodesReq.setGetDealerCodes(dealerCodes);
    		
    		List<DealerCode> dealerCodesList;    		
			dealerCodesList = sprmSupportAndReadinessPt.getDealerCodes(dealerCodesReq).getGetDealerCodesResult().getDealerCodesList().getDealerCodeItem();
			    		
    		 if ( dealerCodesList == null || dealerCodesList.isEmpty() ) {
    			 throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
             }
    		 
    		for (DealerCode dealerCode : dealerCodesList) {					
			
    			DealerVO dealerVo = new DealerVO();	    			
    			dealerVo.setDealerCode( Long.parseLong(dealerCode.getDealerCode()) );
    			dealerVo.setDepotCode( dealerCode.getDealerDepotCode() );
    			dealerVo.setDealerName( dealerCode.getDealerName() );
    			dealerVo.setCountryId(country.getId());
    			dealerVo.setCountryName(country.getCountryName());
    			listVO.add(dealerVo);
    		}

            log.debug("numero de companias leidas: " + dealerCodesList.size());

            return listVO;
        
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerCodes/IBSSPRMSupportAndReadinessBrokerImpl ==");
        	throw manageServiceException(ex);
        } finally {
            log.debug("== Termina getDealerCodes/IBSSPRMSupportAndReadinessBrokerImpl ==");
        }
	}
	

	@Override
	public DealersWSDTO getDealerInfo(Long dealerCode, String depotCode, Country country) throws BusinessException {
		DealersWSDTO result = null;
		try{
			MediaContactConfiguratorHelper.getInstance().loadMediaContactCodes((IBSWSBase)this, country.getId(), systemParameterDao);
			
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			//PtCustomer customer = servicelocator.getDealerService();	
			SPRMSupportAndReadinessPt sprmSupportAndReadinessPt = ServiceLocator.getInstance().getSPRMSupportAndReadinessInventoryService();
			GetDealerInfoRequest gdInfoRequest = new GetDealerInfoRequest();
			RequestMetadataType metadata = new RequestMetadataType();
			GetDealerInfo gdInfo = new GetDealerInfo();
			Random r = new Random();
			int requestId =  r.nextInt(1000000);
			metadata.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP
					.getCodeEntity());
			metadata.setRequestID(requestId+"");
    		metadata.setSourceID(country.getCountryCode());
    		gdInfoRequest.setRequestMetadata(metadata);
    		
			gdInfo.setDealerCode("" + dealerCode);
			if(dealerCode != null){
				gdInfo.setDealerKey("" + dealerCode);
			}			
			else{
				log.debug("== Parametros Requeridos sin datos [dealerCode,depotCode] ==");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			gdInfoRequest.setGetDealerInfo(gdInfo);	
			
			GetDealerInfoResponse gdInfoResponse = sprmSupportAndReadinessPt.getDealerInfo(gdInfoRequest);
			GetDealerInfoResult gdInfoResult = gdInfoResponse.getGetDealerInfoResult();
			result = buildDealersInfoFromResult(gdInfoResult, country);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getDealerInfo/DealersServiceBrokerImpl ==");
			if(ex instanceof BusinessDetailException){
				throw new BusinessDetailException(((BusinessDetailException) ex).getMessageCode(),ex.getMessage(),((BusinessDetailException) ex).getParameters());
			}
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getDealerInfo/DealersServiceBrokerImpl ==");
		}
		return result;
	}
	

	/**
     * 
     * Metodo: Valida la informacion requerida que proviene
     * de IBS.
     * @param GetDealerInfoResult gdInfoResult     
     * @author Joan Lopez - reformulado por RESB por Andres Harker
     * 28/06/2010
     * @throws BusinessDetailException 
     */
    private void validarInformacionRequeridaIBS(GetDealerInfoResult pInfoResult) throws BusinessDetailException, BusinessException{
    	try {
			int indexAddress=findIndexForTheConcreteAddress(pInfoResult.getCust().getAddressList().getUrbanPropertyAddress());

    	String parameterName="";
    	
    	if(pInfoResult==null){
    		log.error("== Parametro Requerido nulo desde IBS [DealerInfoResult] ==");
    		List<String> params = getParametersWS( getErrorParameters("DealerInfoResult", "getDealerInfo") );
			throw new BusinessDetailException(ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(),ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters("DealerInfoResult", "getDealerInfo")),params);
    	}
    	if(pInfoResult.getCust().getAddressList() == null || pInfoResult.getCust().getAddressList().getUrbanPropertyAddress() == null 
				|| pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty()){
    		log.error("== Parametro Requerido nulo desde IBS [DealerInfoResult.Address] ==");
    		List<String> params = getParametersWS( getErrorParameters("Address", "getDealerInfo") );
			throw new BusinessDetailException(ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(),ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters("Address", "getDealerInfo")),params);
    	}
    	if(pInfoResult.getCust()==null){
    		log.warn("== Parametro Opcional nulo desde IBS [DealerInfoResult.Cust] ==");			
    	}
    	if(pInfoResult.getCust().getCustomerRank()==null){
    		log.warn("== Parametro Opcional nulo desde IBS [DealerInfoResult.Cust.CustomerRank] ==");			
    	}
    	//parameterName="pInfoResult.getAddress().getCity()";   	
    	parameterName="Ciudad";
    	//Cust.AddressList.UrbanPropertyAddress.city
    	if(pInfoResult!=null && pInfoResult.getCust()!=null && pInfoResult.getCust().getAddressList()!=null && pInfoResult.getCust().getAddressList().getUrbanPropertyAddress()!=null && !pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty() && indexAddress>=0){
    		validateResult(parameterName, pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getCity(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	
    	//parameterName="pInfoResult.getAddress().getCountry()";
    	parameterName="País";
    	if(pInfoResult!=null && pInfoResult.getCust()!=null && pInfoResult.getCust().getAddressList()!=null && pInfoResult.getCust().getAddressList().getUrbanPropertyAddress()!=null && !pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty() && indexAddress>=0){
    		validateResult(parameterName, pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getCountry(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	//parameterName="pInfoResult.getAddressText()";
    	parameterName="Direccion";
    	//Cust.AddressList.UrbanPropertyAddress.streetName
    	if(pInfoResult!=null && pInfoResult.getCust()!=null && pInfoResult.getCust().getAddressList()!=null && pInfoResult.getCust().getAddressList().getUrbanPropertyAddress()!=null && !pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty() && indexAddress>=0){
    		validateResult(parameterName, pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getStreetName(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName,"getDealerInfo")),"getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	//parameterName="pInfoResult.getChannelTypeId()";
    	parameterName="Tipo de Canal";
    	//Cust.CategorizedBy.id
    	if(pInfoResult!=null && pInfoResult.getCust()!=null && pInfoResult.getCust().getCategorizedBy()!=null){
    		validateResult(parameterName, pInfoResult.getCust().getCategorizedBy().getId(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName,"getDealerInfo")),"getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	//parameterName="pInfoResult.getChannelTypeName()";
    	parameterName="Nombre del Tipo de Canal";
    	if(pInfoResult!=null && pInfoResult.getCust()!=null && pInfoResult.getCust().getCategorizedBy()!=null){
    		validateResult(parameterName, pInfoResult.getCust().getCategorizedBy().getName(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName,"getDealerInfo")),"getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	//parameterName="pInfoResult.getDealerCode()";
    	parameterName="Codigo del Dealer";
    	if(pInfoResult!=null){
    		validateResult(parameterName, pInfoResult.getDealerCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	//parameterName="pInfoResult.getName()";
    	parameterName="Nombre del Dealer";
    	if(pInfoResult!=null && pInfoResult.getCust()!=null){
    		validateResult(parameterName, pInfoResult.getCust().getName(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	
    	//parameterName="pInfoResult.getTypeKey()";
    	parameterName="Tipo de Dealer";
    	validateResult(parameterName, pInfoResult.getCust().getCategorizedBy().getId(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	
    	//parameterName="pInfoResult.getDepotCode()";
    	parameterName="Depot Code";
    	if(pInfoResult!=null){
    		validateResult(parameterName, pInfoResult.getDepotCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	
    	//jjimenezh 2010-09-28 Se cambia la validación del reference number (NIT) no es obligatorio y se guardará vacio, esto se acordó en reunión del comité de pruebas del 2010-09-27
    	//for(int i=0; i<)

	    	if(pInfoResult==null || pInfoResult.getCust()==null
	    			|| pInfoResult.getCust().getIndividualRole()==null
	    			|| pInfoResult.getCust().getIndividualRole().getIdentifiedBy()==null
	    			|| pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications()==null
	    			|| pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().isEmpty()
	    			/*|| ((NationalIdentityCardIdentification)pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(0)).getCardNr() == null 
	    			|| ((NationalIdentityCardIdentification)pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(0)).getCardNr().trim().length() == 0*/){
	    		if(pInfoResult.getCust()==null){
	    			pInfoResult.setCust(new Customer());
	    		}
	    		if(pInfoResult.getCust().getIndividualRole()==null){
	    			pInfoResult.getCust().setIndividualRole(new Individual());
	    		}
	    		if(pInfoResult.getCust().getIndividualRole().getIdentifiedBy()==null){
	    			pInfoResult.getCust().getIndividualRole().setIdentifiedBy(new IndividualIdentificationCollection());
	    		}
	    		pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().add(new NationalIdentityCardIdentification());
	    		((NationalIdentityCardIdentification)pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().size()-1)).setCardNr("vacio");
	    	}
	    	else{
	    		boolean findObject = false;
	    		for(int i=0; i<pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().size() && !findObject; ++i){
	    			if(pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(i) instanceof NationalIdentityCardIdentification){
	    				findObject=true;
	    				if(((NationalIdentityCardIdentification)pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(i)).getCardNr() == null 
	    		    			|| ((NationalIdentityCardIdentification)pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(i)).getCardNr().trim().length() == 0){
	    					((NationalIdentityCardIdentification)pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(i)).setCardNr("vacio");
	    				}
	    			}
	    		}
	    		if(!findObject){
		    		pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().add(new NationalIdentityCardIdentification());
		    		((NationalIdentityCardIdentification)pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().size()-1)).setCardNr("vacio");
	    		}
	    	}
	    	//Cust.AddressList.additionalAttribute1
    	if(pInfoResult.getCust() == null 
    			|| pInfoResult.getCust().getAddressList()==null
    			|| pInfoResult.getCust().getAddressList().getUrbanPropertyAddress() == null
    			|| pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty()
    			|| indexAddress==-1
    			|| (indexAddress>=0 && pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getAdditionalAttribute1()==null)
    			|| (indexAddress>=0 && pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getAdditionalAttribute1().trim().length() == 0)){
    		if(pInfoResult.getCust()==null){
    			pInfoResult.setCust(new Customer());
    		}
    		if(pInfoResult.getCust().getAddressList()==null){
    			pInfoResult.getCust().setAddressList(new UrbanPropertyAddressCollection());
    		}
    		if(pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty()){
    			pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().add(new UrbanPropertyAddress());
    		}
    		
    		pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().size()-1).setAdditionalAttribute1("REP_LEGAL_NULO");
    		pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().size()-1).setCategorizedBy(new Category());
    		pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().size()-1).getCategorizedBy().setId(CodesBusinessEntityEnum.SDII_CORRECT_ID_ADDRESS_FROM_RESB.getCodeEntity());
    	}
    	indexAddress=findIndexForTheConcreteAddress(pInfoResult.getCust().getAddressList().getUrbanPropertyAddress());
    	//parameterName="pInfoResult.getCareOfName()";
    	parameterName="Representante Legal";
    	if(indexAddress>=0){
    		validateResult(parameterName, pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getAdditionalAttribute1(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	
    	//parameterName="pInfoResult.getReferenceNumber()";
    	parameterName="Nit";
		for(int i=0; i<pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().size(); ++i){
			if(pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(i) instanceof NationalIdentityCardIdentification){
				validateResult(parameterName, ((NationalIdentityCardIdentification)pInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(i)).getCardNr(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
			}
		}
    	
    	//parameterName="pInfoResult.getAddress().getPostcode()";
    	parameterName="Postal Code";
    	if(pInfoResult.getCust()!=null && pInfoResult.getCust().getAddressList()!=null 
    			&& pInfoResult.getCust().getAddressList().getUrbanPropertyAddress()!=null 
    			&& !pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty() && indexAddress>=0){
    		validateResult(parameterName, pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getPostCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	//parameterName="pInfoResult.getIsPrincipal()";
    	parameterName="Compañia Principal";
    	validateResult(parameterName, pInfoResult.isIsPrincipal(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	
    	//parameterName="pInfoResult.getParentDealerCode()";
    	parameterName="Codigo del Dealer de la Compañia Principal";
    	validateResult(parameterName, pInfoResult.getParentDealerCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	
    	//parameterName="pInfoResult.getParentDepotCode()";
    	parameterName="Depot Code de la Compañia Principal";
    	validateResult(parameterName, pInfoResult.getParentDepotCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");    	
    	
    	//parameterName="pInfoResult.getAddress().getStateOrProvinceCode()";
    	parameterName="Estado o Provincia";
    	//Cust.AddressList.UrbanPropertyAddress.stateOrProvince
    	if(pInfoResult.getCust() != null 
    			&& pInfoResult.getCust().getAddressList()!=null
    			&& pInfoResult.getCust().getAddressList().getUrbanPropertyAddress() != null
    			&& !pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty()
    			&& indexAddress>=0
    			&& (indexAddress>=0 && pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getStateOrProvince()!=null)){
    		validateResult(parameterName, pInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getStateOrProvince().getId(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	
    	
    	//parameterName="pInfoResult.getStatusKey()";
    	parameterName="Estado del Dealer";
    	//Cust.status.id
    	if(pInfoResult.getCust()!=null && pInfoResult.getCust().getStatus()!=null){
    		validateResult(parameterName, pInfoResult.getCust().getStatus().getId(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
    	else{
    		validateResult(parameterName, null, ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_SERVICE_PARAM_NULL.getMessage(getErrorParameters(parameterName, "getDealerInfo")), "getDealerInfo");
    	}
		} catch (Throwable e) {
			throw manageServiceException(e);
		} 	
    }
	
    /**
     * Este metodo esta enfocado a extraer dado un parametro de propiedades el indice de un arreglo de direcciones, la que realmente es la direccion oficial
     * @param urbanPropertyAddress la lista de direcciones en la cual se buscara la direccion oficial
     * @return el indice donde se encuentra la direccion oficial, en caso de no encontrarla
     * @throws PropertiesException
     * @author Aharker
     */
    private int findIndexForTheConcreteAddress(List<UrbanPropertyAddress> urbanPropertyAddress) throws PropertiesException{
    	int returnValue=-1;
    	if(urbanPropertyAddress!=null && !urbanPropertyAddress.isEmpty()){
    		int it=0;
    		for(UrbanPropertyAddress upa: urbanPropertyAddress){
    			if(upa.getCategorizedBy().getId().equalsIgnoreCase(CodesBusinessEntityEnum.SDII_CORRECT_ID_ADDRESS_FROM_RESB.getCodeEntity())){
    				returnValue = it;
    				break;
    			}
    			++it;
    		}
    	}
    	return returnValue;
    }
    
	 /**
     * Construye un objeto del dominio del sistema a partir de un objeto del dominio de IBS
     * @param gdInfoResult Objeto del dominio de IBS que contiene la información de un dealer
     * @return Objeto con la información del dealer
	 * @throws BusinessException
     */
	private DealersWSDTO buildDealersInfoFromResult(GetDealerInfoResult gdInfoResult, Country country) throws BusinessDetailException, BusinessException {
		
		DealersWSDTO result = new DealersWSDTO();
		
		validarInformacionRequeridaIBS(gdInfoResult);
		
		try{
			
			int indexAddress=findIndexForTheConcreteAddress(gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress());
			if(indexAddress>=0){
				result.setAddress(gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getStreetName());
			}
			
			
			result.setChannelTypeCode(gdInfoResult.getCust().getCategorizedBy().getId());
			result.setChannelTypeName(gdInfoResult.getCust().getCategorizedBy().getName());
			result.setCity(gdInfoResult.getCust().getAddressList() == null || gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress() == null 
					|| gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty() ? "" : 
						indexAddress>=0 ? gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getCity(): "");
			//jjimenezh 2010-10-08 Se asigna el mismo país al dealer por control de cambios solicitado por DTV
			result.setCountry(country.getCountryCode());
			result.setDealerCode(gdInfoResult.getDealerCode());
			result.setDealerName(gdInfoResult.getCust().getName());
			result.setDealerTypeCode(gdInfoResult.getCust().getCategorizedBy().getId());
			result.setDepotCode(gdInfoResult.getDepotCode());
			
			String [] cellPhones = super.getArrayDealerCellPhones( gdInfoResult.getCust().getContactableVia(), 
					CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity());
			result.setCelPhones(cellPhones);
			String [] emails = super.getArrayDealerEmails(gdInfoResult.getCust().getContactableVia(), EMAIL_OPTION);
			result.setEmails(emails);
			
			String [] faxes = super.getArrayDealerFaxes(gdInfoResult.getCust().getContactableVia(), 
					CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getCodeEntity());
			result.setFaxes(faxes);
			
			String [] homePhones = super.getArrayDealerTelephones(gdInfoResult.getCust().getContactableVia(), 
					CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity());
			result.setHomePhones(homePhones);
			
			String [] workPhones = super.getArrayDealerTelephones(gdInfoResult.getCust().getContactableVia(), 
					CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getCodeEntity());
			result.setWorkPhones(workPhones);
			//Cust.AddressList.additionalAttribute1
			if(indexAddress>=0){
				result.setLegalRepresentative(gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getAdditionalAttribute1());
			}
			
			//Cust.IndividualRole.IdentifiedBy.IndividualIdentifications.cardNr
			for(IndividualIdentification ii: gdInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications()){
				if(ii instanceof NationalIdentityCardIdentification){
					result.setNit(((NationalIdentityCardIdentification)ii).getCardNr());
					break;
				}
			}

			//result.setNit(gdInfoResult.getCust().getIndividualRole().getIdentifiedBy().getIndividualIdentifications().get(0));
						
			result.setPostalCode(gdInfoResult.getCust().getAddressList() == null || gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress() == null 
					|| gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty() ? "" : indexAddress>=0 ? gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getPostCode(): "");
			result.setPrincipal(gdInfoResult.isIsPrincipal());
			result.setPrincipalDealerCode(gdInfoResult.getParentDealerCode());
			String depotCode = gdInfoResult.getParentDepotCode();
			
			if(depotCode != null && !depotCode.trim().equalsIgnoreCase("")){
				result.setPrincipalDepotCode(depotCode);
			}
			
			if(gdInfoResult.getCust() != null ){
				if(gdInfoResult.getCust().getCustomerRank()!=null)
					result.setScore(gdInfoResult.getCust() == null ? 0D : gdInfoResult.getCust().getCustomerRank());
			}
			result.setState(gdInfoResult.getCust().getAddressList() == null || gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress() == null 
					|| gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress().isEmpty() ? "" : indexAddress>=0 ? gdInfoResult.getCust().getAddressList().getUrbanPropertyAddress().get(indexAddress).getStateOrProvince().getId():"");
			result.setStatusCode(gdInfoResult.getCust().getStatus().getId());		
			
		}catch(Throwable ex){			
			log.error("== Error al tratar de ejecutar la operación buildDealersInfoFromResult/DealersBusinessBean ==");	
			if(ex instanceof BusinessDetailException){
				throw new BusinessDetailException(((BusinessDetailException) ex).getMessageCode(),ex.getMessage(),((BusinessDetailException) ex).getParameters());
			}
        	throw manageServiceException(ex);
        } 
		
		
		return result;
	}
	 
	/**
	 * Este metodo esta enfocado a el consumo de la operacion moveResourceToStockHandler de ESB, la cual mueve un elemento serializado hacia un Dealer
	 * @param record regla de movimiento que se va a realizar, correspondiente a un registro de la tabla MOV_CMD_QUEUE
	 * @return Es un retorno logico (verdadero o falso), que indica si se pudo realizar la operacion en ESB
	 * @throws Excepcion de negocio que encapsula las excepciones que genere la invocacion del metodo de ESB o el procesamiento de HSP+
	 * @author Aharker
	 */
	@Override
	public boolean moveResourceToStockHandler(MovCmdQueueVO record) throws BusinessException {
		log.debug("== Inicia la operación moveResourceToStockHandler/IBSSPRMSupportAndReadinessBrokerImpl ==");
		boolean success = true;
		try {
			//log.debug("Se invocará la operación moveResourceToStockHandler con los siguientes parámetros: " + serElMovDto);
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			SPRMSupportAndReadinessPt service = servicelocator.getSPRMSupportAndReadinessInventoryService();
			MoveResourceToStockHandlerRequest moveSerElementRequest = IBSSPRMSupportAndReadinessTOA.createMoveResourceToStockHandlerRequest(record);
			
			//Si el request viene nulo, no se notifica el movimiento
			if(moveSerElementRequest == null){
				return success;
			}
			IBSSPRMSupportAndReadinessTOA.generateMoveResourceToStockHandlerLog(moveSerElementRequest);
			MoveResourceToStockHandlerResponse moveSerElementResponse = service.moveResourceToStockHandler(moveSerElementRequest);
			ResponseMetadataType responseMetaData = moveSerElementResponse.getResponseMetadata();
			if(responseMetaData.getBoxUsage() == null || responseMetaData.getRequestID() == null || responseMetaData.getTimeStamp() == null){
				success = false;
			}			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación moveResourceToStockHandler/IBSSPRMSupportAndReadinessBrokerImpl ==");
			throw this.manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación moveResourceToStockHandler/IBSSPRMSupportAndReadinessBrokerImpl ==");
		}
		return success;
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.IBSSPRMSupportAndReadinessBrokerLocal#receiveReturnedPhysicalResource(co.com.directv.sdii.model.vo.MovCmdQueueVO, boolean)
	 */
	@Override
	public boolean receiveReturnedPhysicalResource(MovCmdQueueVO record,boolean linkSerial) throws BusinessException {
		log.debug("== Inicia la operación moveResourceToStockHandler/IBSSPRMSupportAndReadinessBrokerImpl ==");
		boolean success = true;
		try {
			ServiceLocator servicelocator = ServiceLocator.getInstance();
			SPRMSupportAndReadinessPt service = servicelocator.getSPRMSupportAndReadinessInventoryService();
			ReceiveReturnedPhysicalResourceRequest receiveReturnedPhysicalResourceRequest = IBSSPRMSupportAndReadinessTOA.createReceiveReturnedPhysicalResourceRequest(record,linkSerial);
			
			//Si el request viene nulo, no se notifica el movimiento
			if(receiveReturnedPhysicalResourceRequest != null){
				IBSSPRMSupportAndReadinessTOA.generatereceiveReturnedPhysicalResourceLog(receiveReturnedPhysicalResourceRequest,false);
				ReceiveReturnedPhysicalResourceResponse receiveReturnedPhysicalResourceResponse = service.receiveReturnedPhysicalResource(receiveReturnedPhysicalResourceRequest);
				ResponseMetadataType responseMetaData = receiveReturnedPhysicalResourceResponse.getResponseMetadata();
				
				if(responseMetaData.getBoxUsage() == null || responseMetaData.getRequestID() == null || responseMetaData.getTimeStamp() == null){
					return false;
				}
				
			}
						
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación moveResourceToStockHandler/IBSSPRMSupportAndReadinessBrokerImpl ==");
			throw this.manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación moveResourceToStockHandler/IBSSPRMSupportAndReadinessBrokerImpl ==");
		}
		return success;
		
	}
	

	
	/**
	 * Metodo encargado de tomar una excepcion generica, clasificarla y encapsularla en una excepcion de negocio de HSP+
	 * @param e Excepcion que se desea encapsular en una excepcion de negocio de HSP+
	 * @return Excepcion de negocio de HSP+ que encapsula la excepcion generica y su correspondiente mensaje
	 * @author Aharker
	 */
	@Override
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		if(e instanceof MoveResourceToStockHandlerException){
			
			errorMessage = this.getMoveResourceToStockHandlerExceptionMessage(((MoveResourceToStockHandlerException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
			
		}else if(e instanceof  ReceiveReturnedPhysicalResourceException){
			
			errorMessage = this.getReceiveReturnedPhysicalResourceExceptionMessage(((ReceiveReturnedPhysicalResourceException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
			
		} else if(e instanceof GetDealerInfoException){
			errorMessage = this.getGetDealerInfoExceptionMessage(((GetDealerInfoException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if(e instanceof GetDealerCodesException){
			errorMessage = this.getGetDealerCodesExceptionMessage(((GetDealerCodesException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else{
			be = this.manageException(e);
		}
		return be;
	}

	/**
	 * Metodo enfocado a extraer el mensaje de error de una excepcion generada por el metodo de ESB GetDealerCodes
	 * @param faultInfo informacion del error, esta informacion encapsula toras las posibles excepciones que puede generar el servicio
	 * @param defaultMessage mensaje por defecto que tomara el error en caso de no lograr clasificar la excepcion
	 * @return mensaje de error que se dara al encapsulado en la excepcion de HSP+
	 * @author Aharker
	 */
	private String getGetDealerCodesExceptionMessage(
			com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.GetDealerCodesException faultInfo,
			String errorMessage) {
		if(faultInfo.getEntityAlreadyExistsException()!=null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityNotFoundException()!=null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getEntityInUseException()!=null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getInternalErrorException()!=null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getMissingParameterException()!=null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException()!=null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException()!=null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException()!=null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException()!=null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException()!=null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getAccessDeniedException()!=null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException()!=null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException()!=null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		return errorMessage;
	}

	/**
	 * Metodo enfocado a extraer el mensaje de error de una excepcion generada por el metodo de ESB GetDealerInfo
	 * @param faultInfo informacion del error, esta informacion encapsula toras las posibles excepciones que puede generar el servicio
	 * @param defaultMessage mensaje por defecto que tomara el error en caso de no lograr clasificar la excepcion
	 * @return mensaje de error que se dara al encapsulado en la excepcion de HSP+
	 * @author Aharker
	 */
	private String getGetDealerInfoExceptionMessage(
			com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.GetDealerInfoException faultInfo,
			String errorMessage) {
		if(faultInfo.getEntityAlreadyExistsException()!=null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityNotFoundException()!=null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getEntityInUseException()!=null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getInternalErrorException()!=null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getMissingParameterException()!=null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException()!=null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException()!=null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException()!=null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException()!=null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException()!=null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getAccessDeniedException()!=null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException()!=null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException()!=null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		return errorMessage;
	}
	

	/**
	 * Metodo enfocado a extraer el mensaje de error de una excepcion generada por el metodo de ESB MoveResourceToStockHandler
	 * @param faultInfo informacion del error, esta informacion encapsula toras las posibles excepciones que puede generar el servicio
	 * @param defaultMessage mensaje por defecto que tomara el error en caso de no lograr clasificar la excepcion
	 * @return mensaje de error que se dara al encapsulado en la excepcion de HSP+
	 * @author Aharker
	 */
	private String getMoveResourceToStockHandlerExceptionMessage(com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.MoveResourceToStockHandlerException faultInfo, String defaultMessage){
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException() != null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}
		
		return defaultMessage;
	}
	
	private String getReceiveReturnedPhysicalResourceExceptionMessage(com.directvla.schema.businessdomain.sprmsupportandreadiness.v1_0.ReceiveReturnedPhysicalResourceException faultInfo, String defaultMessage){
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException() != null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}
		
		return defaultMessage;
	}
	
	
}
