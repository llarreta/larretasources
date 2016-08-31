/**
 * Creado 2/11/2010 14:23:12
 */
package co.com.directv.sdii.ejb.business.core.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import weblogic.wsee.util.bytecode.AttributeInfo.Code;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.Agreement;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.BusinessInteractionType;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.Category;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.ContactMedium;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.Customer;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.CustomerHistoryEvent;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.EmailContact;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.FaxNumber;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.IndividualIdentification;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.NationalIdentityCardIdentification;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.PhysicalResource;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.PhysicalResourceCollection;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.Product;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.ProductCategory;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.ProductCharacteristicValueCollection;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.ProductCollection;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent.DataArea;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent.Header;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.RequestMetadataType;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.ServiceProvider;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.ShippingOrder;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.TelephoneNumber;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.UrbanPropertyAddress;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.WorkOrder;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.WorkOrderItem;
import co.com.directv.sdii.dto.esb.event.publishworkorderevent.WorkOrderItemCollection;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.config.SystemParameterBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOImporterLocal;
import co.com.directv.sdii.ejb.business.core.OrderHandlingServiceBusinessLocal;
import co.com.directv.sdii.ejb.business.core.WoInfoEsbServiceBusinessLocal;
import co.com.directv.sdii.ejb.business.core.WoLoadBusinessLocal;
import co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CustomerInquiriesByCriteriaIBSDTO;
import co.com.directv.sdii.model.dto.OrderHandlingServiceRequestDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.ElementClass;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.WoLoad;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.WoStatusHistoryVO;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;

import com.directvla.schema.businessdomain.product.orderhandling.v1_0.ProductCharacteristicValue;



/**
 * Abstrae la complejidad relacionada con la creación de Work orders orquestando
 * la invocación de las operaciones de CoreBusiness que manejan transacciones atómicas.
 * esto permite la tolerancia a fallos necesaria para crear las work orders subsiguientes a
 * una work order fallida.
 * 
 * También genera la información estadística del cargue de work orders
 * 
 * Fecha de Creación: 2/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal
 * @see 
 */
@Stateless(name="CoreWOImporterLocal",mappedName="ejb/CoreWOImporterLocal")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CoreWOImporter extends BusinessBase implements CoreWOImporterLocal {

	@EJB(name="CoreWOBusinessLocal",beanInterface=CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal business;
	
	@EJB(name="CountriesDAOLocal",beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDAO;
	
	@EJB(name="WoLoadBusinessLocal",beanInterface=WoLoadBusinessLocal.class)
	private WoLoadBusinessLocal woLoadBusiness;
	
	@EJB(name="WoInfoEsbServiceBusinessBean", beanInterface=WoInfoEsbServiceBusinessLocal.class)
	private WoInfoEsbServiceBusinessLocal woInfoEsbServiceBusinessLocal;
	
	@EJB(name="WorkOrderMarkBusinessBeanLocal",beanInterface=WorkOrderMarkBusinessBeanLocal.class)
	private WorkOrderMarkBusinessBeanLocal workOrderMarkBusiness;
	
	@EJB(name="OrderHandlingServiceBusinessLocal", beanInterface=OrderHandlingServiceBusinessLocal.class)
	private OrderHandlingServiceBusinessLocal orderHandlingServiceBusiness;

	@EJB(name="ElementClassBusinessBeanLocal", beanInterface=ElementClassBusinessBeanLocal.class)
	private ElementClassBusinessBeanLocal elementClassBusiness;
	
	@EJB(name="SystemParameterBusinessBeanLocal", beanInterface=SystemParameterBusinessBeanLocal.class)
	private SystemParameterBusinessBeanLocal systemParameterBusiness;
	
	private final static Logger log = UtilsBusiness.getLog4J(CoreWOBusiness.class);
	
	/**
	 * Se encarga de copiar del objeto de ibs en la DTO interna de HSP, reflejo de dicho objeto
	 * @param ibsWo objeto work order de ESB
	 * @return DTO de work order de HSP+ para la informacion proveniente de ESB
	 * @author Aharker
	 */
	@Deprecated
	private co.com.directv.sdii.dto.esb.WorkOrder copyWorkOrderObject(com.directvla.schema.crm.common.v1_1.WorkOrder ibsWo){
		co.com.directv.sdii.dto.esb.WorkOrder localWo = new co.com.directv.sdii.dto.esb.WorkOrder();
		//1. Action
		localWo.setAction(ibsWo.getAction());
		//1. Code
		localWo.setCode(ibsWo.getCode());
		//1. DealerId
		localWo.setDealerId(ibsWo.getDealerId());
		//1. DealerISalesd
		localWo.setDealerISalesd(ibsWo.getDealerISalesd());
		//1. Description
		localWo.setDescription(ibsWo.getDescription());
		//1. FacilityCode
		localWo.setFacilityCode(ibsWo.getFacilityCode());
		//1. HistoryCodeEventId
		localWo.setHistoryCodeEventId(ibsWo.getHistoryCodeEventId());
		//1. ID
		localWo.setID(ibsWo.getID());
		//1. InitiatedBy
		localWo.setInitiatedBy(ibsWo.getInitiatedBy());
		//1. InteractionDate
		localWo.setInteractionDate(ibsWo.getInteractionDate());
		//1. InteractionDateComplete
		localWo.setInteractionDateComplete(ibsWo.getInteractionDateComplete());
		//1. InteractionStatus
		localWo.setInteractionStatus(ibsWo.getInteractionStatus());
		//1. IsAppointment
		localWo.setIsAppointment(ibsWo.getIsAppointment());
		//1. PossibleDeliveryDate
		localWo.setPossibleDeliveryDate(ibsWo.getPossibleDeliveryDate());
		//1. RequestedDeliveryDate
		localWo.setRequestedDeliveryDate(ibsWo.getRequestedDeliveryDate());
		//1. WorkingTime
		localWo.setWorkingTime(ibsWo.getWorkingTime());
		//1. WorkorderStatusByActualStatusId
		localWo.setWorkorderStatusByActualStatusId(ibsWo.getWorkorderStatusByActualStatusId());
		//1. WorkorderStatusByPreviusStatusId
		localWo.setWorkorderStatusByPreviusStatusId(ibsWo.getWorkorderStatusByPreviusStatusId());
		
		/* 1. Customer */
		if(ibsWo!=null && ibsWo.getCustomer()!=null){
			co.com.directv.sdii.dto.esb.Customer customerLocal = new co.com.directv.sdii.dto.esb.Customer();
			//2. CustomerRank
			customerLocal.setCustomerRank(ibsWo.getCustomer().getCustomerRank());
			//2. CustomerStatus
			customerLocal.setCustomerStatus(ibsWo.getCustomer().getCustomerStatus());
			//2. ID
			customerLocal.setID(ibsWo.getCustomer().getID());
			//2. Name
			customerLocal.setName(ibsWo.getCustomer().getName());
			//2. PartyRoleId
			customerLocal.setPartyRoleId(ibsWo.getCustomer().getPartyRoleId());
			//2. status
			customerLocal.setStatus(ibsWo.getCustomer().getStatus());
			
			/* 2. AddressList */
			
			co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection urbanPropertyAddressCollectionLocal = new 
				co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection();
			//3. UrbanPropertyAddress
			urbanPropertyAddressCollectionLocal.getUrbanPropertyAddress();
			/* 3. UrbanPropertyAddress */
			
			if (ibsWo.getCustomer().getAddressList().getUrbanPropertyAddress()!=null){
				
				for(com.directvla.schema.crm.common.v1_1.UrbanPropertyAddress upa: ibsWo.getCustomer().getAddressList().getUrbanPropertyAddress()){
					co.com.directv.sdii.dto.esb.UrbanPropertyAddress urbanPropertyAddressLocal = new co.com.directv.sdii.dto.esb.UrbanPropertyAddress();
					// 4. AdditionalAttribute1
					urbanPropertyAddressLocal.setAdditionalAttribute1(upa.getAdditionalAttribute1());
					// 4. AdditionalAttribute2
					urbanPropertyAddressLocal.setAdditionalAttribute2(upa.getAdditionalAttribute2());
					// 4. AddressCode
					urbanPropertyAddressLocal.setAddressCode(upa.getAddressCode());
					// 4. AddressType
					urbanPropertyAddressLocal.setAddressType(upa.getAddressType());
					// 4. City
					urbanPropertyAddressLocal.setCity(upa.getCity());
					// 4. Country
					urbanPropertyAddressLocal.setCountry(upa.getCountry());
					// 4. Locality
					urbanPropertyAddressLocal.setLocality(upa.getLocality());
					// 4. PostCode
					urbanPropertyAddressLocal.setPostCode(upa.getPostCode());
					// 4. StateOrProvince
					urbanPropertyAddressLocal.setStateOrProvince(upa.getStateOrProvince());
					// 4. StreetName
					urbanPropertyAddressLocal.setStreetName(upa.getStreetName());
					// 4. StreetNrFirst
					urbanPropertyAddressLocal.setStreetNrFirst(upa.getStreetNrFirst());
					// 4. StreetNrFirstSuffix
					urbanPropertyAddressLocal.setStreetNrFirstSuffix(upa.getStreetNrFirst());
					// 4. StreetNrLast
					urbanPropertyAddressLocal.setStreetNrLast(upa.getStreetNrLast());
					// 4. StreetNrLastSuffix
					urbanPropertyAddressLocal.setStreetNrLastSuffix(upa.getStreetNrLastSuffix());
					// 4. StreetSuffix
					urbanPropertyAddressLocal.setStreetSuffix(upa.getStreetSuffix());
					// 4. StreetType
					urbanPropertyAddressLocal.setStreetType(upa.getStreetType());
					
					/* 4. ValidFor */
					
					if(upa.getValidFor()!=null){
						
						co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
						// 5. EndDateTime
						timePeriodLocal.setEndDateTime(upa.getValidFor().getEndDateTime());
						// 5. StartDateTime
						timePeriodLocal.setStartDateTime(upa.getValidFor().getStartDateTime());
						
						urbanPropertyAddressLocal.setValidFor(timePeriodLocal);
							
						
					}
					
					/* 4. ValidFor */
					urbanPropertyAddressCollectionLocal.getUrbanPropertyAddress().add(urbanPropertyAddressLocal);
				}
				
			}
			
			/* 3. UrbanPropertyAddress */
			
			customerLocal.setAddressList(urbanPropertyAddressCollectionLocal);
			
			/* 2. AddressList */
			
			/* 2. ContactableVia */
			
			if(ibsWo!=null && ibsWo.getCustomer()!=null && ibsWo.getCustomer().getContactableVia()!=null 
					&& ibsWo.getCustomer().getContactableVia().getContactMedium()!=null){
				co.com.directv.sdii.dto.esb.ContactMediumCollection contactMediumCollectionLocal = new co.com.directv.sdii.dto.esb.ContactMediumCollection();
				
				contactMediumCollectionLocal.getContactMedium();
				
				for( com.directvla.schema.crm.common.v1_1.ContactMedium cm: ibsWo.getCustomer().getContactableVia().getContactMedium()){
					co.com.directv.sdii.dto.esb.ContactMedium contactMediumLocal = null; 
					if(cm instanceof com.directvla.schema.crm.common.v1_1.TelephoneNumber){
						com.directvla.schema.crm.common.v1_1.TelephoneNumber telephoneNumberRemote = (com.directvla.schema.crm.common.v1_1.TelephoneNumber)cm;
						co.com.directv.sdii.dto.esb.TelephoneNumber telephoneNumberLocal = new co.com.directv.sdii.dto.esb.TelephoneNumber();
						telephoneNumberLocal.setNumber(telephoneNumberRemote.getNumber());
						telephoneNumberLocal.setType(telephoneNumberRemote.getType());
						contactMediumLocal = telephoneNumberLocal;
					}
					if(cm instanceof com.directvla.schema.crm.common.v1_1.EmailContact){
						com.directvla.schema.crm.common.v1_1.EmailContact emailContactRemote = (com.directvla.schema.crm.common.v1_1.EmailContact)cm;
						co.com.directv.sdii.dto.esb.EmailContact emailContactLocal = new co.com.directv.sdii.dto.esb.EmailContact();
						emailContactLocal.setEMailAddress(emailContactRemote.getEMailAddress());
						emailContactLocal.setEMailProtocol(emailContactRemote.getEMailProtocol());
						contactMediumLocal = emailContactLocal;
					}
					if(cm instanceof com.directvla.schema.crm.common.v1_1.FaxNumber){
						com.directvla.schema.crm.common.v1_1.FaxNumber telephoneNumberRemote = (com.directvla.schema.crm.common.v1_1.FaxNumber)cm;
						co.com.directv.sdii.dto.esb.FaxNumber telephoneNumberLocal = new co.com.directv.sdii.dto.esb.FaxNumber();
						telephoneNumberLocal.setNumber(telephoneNumberRemote.getNumber());
						telephoneNumberLocal.setType(telephoneNumberRemote.getType());
						contactMediumLocal = telephoneNumberLocal;
					}
					if(contactMediumLocal != null){
						if(cm.getValidFor()!=null){
							co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
							timePeriodLocal.setEndDateTime(cm.getValidFor().getEndDateTime());
							timePeriodLocal.setStartDateTime(cm.getValidFor().getStartDateTime());
							contactMediumLocal.setValidFor(timePeriodLocal);
						}
						contactMediumCollectionLocal.getContactMedium().add(contactMediumLocal);
					}
					
				}
				customerLocal.setContactableVia(contactMediumCollectionLocal);
			}			
			
			/* 2. ContactableVia */
			
			/* 2. IndividualRole */
			
			if(ibsWo.getCustomer().getIndividualRole()!=null){
				co.com.directv.sdii.dto.esb.Individual individualLocal = new co.com.directv.sdii.dto.esb.Individual();
				individualLocal.setDisabilities(ibsWo.getCustomer().getIndividualRole().getDisabilities());
				individualLocal.setGender(ibsWo.getCustomer().getIndividualRole().getGender());
				individualLocal.setMaritalStatus(ibsWo.getCustomer().getIndividualRole().getMaritalStatus());
				individualLocal.setNationality(ibsWo.getCustomer().getIndividualRole().getNationality());
				individualLocal.setPartyId(ibsWo.getCustomer().getIndividualRole().getPartyId());
				individualLocal.setPlaceOfBirth(ibsWo.getCustomer().getIndividualRole().getPlaceOfBirth());
				individualLocal.setSkills(ibsWo.getCustomer().getIndividualRole().getSkills());
				individualLocal.getIndividualName();
				
				if(ibsWo.getCustomer().getIndividualRole().getAliveDuring()!=null){
					co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
					timePeriodLocal.setEndDateTime(ibsWo.getCustomer().getIndividualRole().getAliveDuring().getEndDateTime());
					timePeriodLocal.setStartDateTime(ibsWo.getCustomer().getIndividualRole().getAliveDuring().getStartDateTime());
					individualLocal.setAliveDuring(timePeriodLocal);
				}
				
				if(ibsWo.getCustomer().getIndividualRole().getValidFor()!=null){
					co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
					timePeriodLocal.setEndDateTime(ibsWo.getCustomer().getIndividualRole().getValidFor().getEndDateTime());
					timePeriodLocal.setStartDateTime(ibsWo.getCustomer().getIndividualRole().getValidFor().getStartDateTime());
					individualLocal.setValidFor(timePeriodLocal);
				}

				for(com.directvla.schema.crm.common.v1_1.IndividualName in: ibsWo.getCustomer().getIndividualRole().getIndividualName()){
					co.com.directv.sdii.dto.esb.IndividualName individualNameLocal = new co.com.directv.sdii.dto.esb.IndividualName();
					individualNameLocal.setFormattedName(in.getFormattedName());
					individualNameLocal.setLegalName(in.getLegalName());
					individualNameLocal.setAristocraticTitle(in.getAristocraticTitle());
					individualNameLocal.setFormOfAddress(in.getFormOfAddress());
					individualNameLocal.setGeneration(in.getGeneration());
					individualNameLocal.setGivenNames(in.getGivenNames());
					individualNameLocal.setPreferredGivenName(in.getPreferredGivenName());
					individualNameLocal.setMiddleNames(in.getMiddleNames());
					individualNameLocal.setFamilyNamePrefix(in.getFamilyNamePrefix());
					individualNameLocal.setFamilyNames(in.getFamilyNames());
					individualNameLocal.setFamilyGeneration(in.getFamilyGeneration());
					individualNameLocal.setQualifications(in.getQualifications());
					
					if(in.getValidFor()!=null){
						co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
						timePeriodLocal.setEndDateTime(in.getValidFor().getEndDateTime());
						timePeriodLocal.setStartDateTime(in.getValidFor().getStartDateTime());
						individualNameLocal.setValidFor(timePeriodLocal);
					}
					
					individualLocal.getIndividualName().add(individualNameLocal);
				}
				
				for(com.directvla.schema.crm.common.v1_1.IndividualIdentification id: ibsWo.getCustomer().getIndividualRole().getIndividualIdentification()){
					if(id instanceof com.directvla.schema.crm.common.v1_1.NationalIdentityCardIdentification){
						com.directvla.schema.crm.common.v1_1.NationalIdentityCardIdentification nici=(com.directvla.schema.crm.common.v1_1.NationalIdentityCardIdentification)id;
						co.com.directv.sdii.dto.esb.NationalIdentityCardIdentification nationalIdentityCardIdentificationLocal = new co.com.directv.sdii.dto.esb.NationalIdentityCardIdentification();
						nationalIdentityCardIdentificationLocal.setScan(nici.getScan());
						nationalIdentityCardIdentificationLocal.setCardNr(nici.getCardNr());
						individualLocal.getIndividualIdentification().add(nationalIdentityCardIdentificationLocal);
					}
				}
				customerLocal.setIndividualRole(individualLocal);
			}
			
			
			/* 2. IndividualRole */
			
			/* 2. validFor */
			
			if(ibsWo.getCustomer().getValidFor()!=null){
				co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
				timePeriodLocal.setEndDateTime(ibsWo.getCustomer().getValidFor().getEndDateTime());
				timePeriodLocal.setStartDateTime(ibsWo.getCustomer().getValidFor().getStartDateTime());
				customerLocal.setValidFor(timePeriodLocal);
			}
			
			/* 2. validFor */		
			
			localWo.setCustomer(customerLocal);	
		}
		
		/* 1. Customer */
		
		/* 1. GeographicArea */
		
		if(ibsWo.getGeographicArea()!=null){
			co.com.directv.sdii.dto.esb.GeographicArea geographicAreaLocal = new co.com.directv.sdii.dto.esb.GeographicArea();
			
			geographicAreaLocal.setName(ibsWo.getGeographicArea().getName());
			geographicAreaLocal.setCode(ibsWo.getGeographicArea().getCode());
			geographicAreaLocal.setType(ibsWo.getGeographicArea().getType());
			
			if(ibsWo.getGeographicArea().getState()!=null){
				geographicAreaLocal.setState(new co.com.directv.sdii.dto.esb.State());
				geographicAreaLocal.getState().setCode(ibsWo.getGeographicArea().getState().getCode());
				geographicAreaLocal.getState().setName(ibsWo.getGeographicArea().getState().getName());
				geographicAreaLocal.getState().setType(ibsWo.getGeographicArea().getState().getType());
				if(ibsWo.getGeographicArea().getState().getValidFor()!=null){
					co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
					timePeriodLocal.setEndDateTime(ibsWo.getGeographicArea().getState().getValidFor().getEndDateTime());
					timePeriodLocal.setStartDateTime(ibsWo.getGeographicArea().getState().getValidFor().getStartDateTime());
					geographicAreaLocal.getState().setValidFor(timePeriodLocal);
				}
			}

			if(ibsWo.getGeographicArea().getCountry()!=null){
				co.com.directv.sdii.dto.esb.Country countryLocal = new co.com.directv.sdii.dto.esb.Country();
				countryLocal.setName(ibsWo.getGeographicArea().getCountry().getName());
				countryLocal.setIso2Code(ibsWo.getGeographicArea().getCountry().getIso2Code());
				countryLocal.setIso3Code(ibsWo.getGeographicArea().getCountry().getIso3Code());
				countryLocal.setType(ibsWo.getGeographicArea().getCountry().getType());
				geographicAreaLocal.setCountry(countryLocal);
			}

			if(ibsWo.getGeographicArea().getPostCode()!=null){
				geographicAreaLocal.setPostCode(new co.com.directv.sdii.dto.esb.GeographicPostcode());
				geographicAreaLocal.getPostCode().setCode(ibsWo.getGeographicArea().getPostCode().getCode());
			}
			
			if(ibsWo.getGeographicArea().getValidFor()!=null){
				co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
				timePeriodLocal.setEndDateTime(ibsWo.getGeographicArea().getValidFor().getEndDateTime());
				timePeriodLocal.setStartDateTime(ibsWo.getGeographicArea().getValidFor().getStartDateTime());
				geographicAreaLocal.setValidFor(timePeriodLocal);
			}
			localWo.setGeographicArea(geographicAreaLocal);			
		}

		
		/* 1. GeographicArea */
		
		/* 1. ServiceList */
		
		if(ibsWo.getServiceList()!=null){
			co.com.directv.sdii.dto.esb.ServiceCollection serviceCollectionLocal=new co.com.directv.sdii.dto.esb.ServiceCollection();
			serviceCollectionLocal.getService();
			List<com.directvla.schema.crm.common.v1_1.Service> services=ibsWo.getServiceList().getService();
			for(com.directvla.schema.crm.common.v1_1.Service s: services ){
				co.com.directv.sdii.dto.esb.Service serviceLocal = new co.com.directv.sdii.dto.esb.Service();
				serviceLocal.setServiceKey(s.getServiceKey());
				serviceLocal.setName(s.getName());
				serviceLocal.setCode(s.getCode());
				serviceLocal.setDescription(s.getDescription());
				serviceLocal.setProductSerialNumber(s.getProductSerialNumber());
				serviceLocal.setIsShippingOrderRequired(s.getIsShippingOrderRequired());
				serviceLocal.setWorkOrderKey(s.getWorkOrderKey());
				if(s.getServiceCategory()!=null){
					serviceLocal.setServiceCategory(new co.com.directv.sdii.dto.esb.ServiceCategory());
					serviceLocal.getServiceCategory().setServiceCategoryCode(s.getServiceCategory().getServiceCategoryCode());
					serviceLocal.getServiceCategory().setServiceCategoryName(s.getServiceCategory().getServiceCategoryName());
				}
				if(s.getServiceType()!=null){
					serviceLocal.setServiceType(new co.com.directv.sdii.dto.esb.ServiceType());
					serviceLocal.getServiceType().setServiceTypeCode(s.getServiceType().getServiceTypeCode());
					serviceLocal.getServiceType().setServiceTypeName(s.getServiceType().getServiceTypeName());
				}
				if(s.getValidFor()!=null){
					co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
					timePeriodLocal.setEndDateTime(s.getValidFor().getEndDateTime());
					timePeriodLocal.setStartDateTime(s.getValidFor().getStartDateTime());
					serviceLocal.setValidFor(timePeriodLocal);
				}
				
				serviceCollectionLocal.getService().add(serviceLocal);
				
			}
			localWo.setServiceList(serviceCollectionLocal);
		}
		
		/* 1. ServiceList */
		
		/* 1. ShippingOrder */
		
		if(ibsWo.getShippingOrder()!=null){
			
			co.com.directv.sdii.dto.esb.ShippingOrder shippingOrderLocal = new co.com.directv.sdii.dto.esb.ShippingOrder();
			
			shippingOrderLocal.setShippingOrderKey(ibsWo.getShippingOrder().getShippingOrderKey());
			shippingOrderLocal.setName(ibsWo.getShippingOrder().getName());
			shippingOrderLocal.setCode(ibsWo.getShippingOrder().getCode());
			shippingOrderLocal.setInstallationPrice(ibsWo.getShippingOrder().getInstallationPrice());
			shippingOrderLocal.setActivationPrice(ibsWo.getShippingOrder().getActivationPrice());
			shippingOrderLocal.setSoldBy(ibsWo.getShippingOrder().getSoldBy());
			shippingOrderLocal.setShippingMethodKey(ibsWo.getShippingOrder().getShippingMethodKey());
			shippingOrderLocal.setContractNumber(ibsWo.getShippingOrder().getContractNumber());
			shippingOrderLocal.setContractCode(ibsWo.getShippingOrder().getContractCode());

			shippingOrderLocal.setID(ibsWo.getShippingOrder().getID());
			shippingOrderLocal.setDescription(ibsWo.getShippingOrder().getDescription());
			
			shippingOrderLocal.setInteractionDate(ibsWo.getShippingOrder().getInteractionDate());
			shippingOrderLocal.setInteractionDateComplete(ibsWo.getShippingOrder().getInteractionDateComplete());
			
			shippingOrderLocal.setInteractionStatus(ibsWo.getShippingOrder().getInteractionStatus());
			
			if(ibsWo.getShippingOrder().getProductList()!=null){
				co.com.directv.sdii.dto.esb.ProductCollection productCollectionLocal=new co.com.directv.sdii.dto.esb.ProductCollection();
				productCollectionLocal.getProduct();
				for(com.directvla.schema.crm.common.v1_1.Product p: ibsWo.getShippingOrder().getProductList().getProduct()){
					co.com.directv.sdii.dto.esb.Product productLocal=new co.com.directv.sdii.dto.esb.Product();
					productLocal.setProductKey(p.getProductKey());
					productLocal.setName(p.getName());
					productLocal.setCode(p.getCode());
					productLocal.setDescription(p.getDescription());
					productLocal.setProductStatus(p.getProductStatus());
					productLocal.setProductSerialNumber(p.getProductSerialNumber());
					productLocal.setProductPrice(p.getProductPrice());
					productLocal.setModelKey(p.getModelKey());
					productLocal.setType(p.getType());
					if(p.getValidFor()!=null){
						co.com.directv.sdii.dto.esb.TimePeriod timePeriodLocal = new co.com.directv.sdii.dto.esb.TimePeriod();
						timePeriodLocal.setEndDateTime(p.getValidFor().getEndDateTime());
						timePeriodLocal.setStartDateTime(p.getValidFor().getStartDateTime());
						productLocal.setValidFor(timePeriodLocal);
					}
					productCollectionLocal.getProduct().add(productLocal);
				}
				shippingOrderLocal.setProductList(productCollectionLocal);
			}
			localWo.setShippingOrder(shippingOrderLocal);
		}
		
		/* 1. ShippingOrder */
		
		/* 1. WoTypeByPreviusWoTypeId */
		
		if(ibsWo.getWoTypeByPreviusWoTypeId()!=null){
			localWo.setWoTypeByPreviusWoTypeId(new co.com.directv.sdii.dto.esb.WoType());
			localWo.getWoTypeByPreviusWoTypeId().setId(ibsWo.getWoTypeByPreviusWoTypeId().getId());
			localWo.getWoTypeByPreviusWoTypeId().setWoTypeCode(ibsWo.getWoTypeByPreviusWoTypeId().getWoTypeCode());
			localWo.getWoTypeByPreviusWoTypeId().setWoTypeName(ibsWo.getWoTypeByPreviusWoTypeId().getWoTypeName());
		}
		
		/* 1. WoTypeByPreviusWoTypeId */
		
		/* 1. WoTypeByWoTypeId */
		
		if(ibsWo.getWoTypeByWoTypeId()!=null){
			localWo.setWoTypeByWoTypeId(new co.com.directv.sdii.dto.esb.WoType());
			localWo.getWoTypeByWoTypeId().setId(ibsWo.getWoTypeByWoTypeId().getId());
			localWo.getWoTypeByWoTypeId().setWoTypeCode(ibsWo.getWoTypeByWoTypeId().getWoTypeCode());
			localWo.getWoTypeByWoTypeId().setWoTypeName(ibsWo.getWoTypeByWoTypeId().getWoTypeName());
		}
		
		/* 1. WoTypeByWoTypeId */
		
		return localWo;
	}
	
	/**
	 * Se encarga de copiar del objeto de ESB en la DTO interna de HSP, reflejo de dicho objeto.
	 * @param publishWorkOrderEvent objeto de ESB que contiene toda la información respecto a un evento de una WO.
	 * @param countryCode se recibe el countryCode al que pertenece el evento.
	 * @return DTO de work order de HSP+ para la informacion proveniente de ESB.
	 * @author ssanabri
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 * 
 	 * ialessan abril 2014
	 * IN400990 - Descarga de Eventos
	 * Se modificó   *2 ProductList* para que admita una lista "product.getProductHasPhysicalResources().getPhysicalResources()" de un solo elemento 
	 * y cargue el atributo de uso posterior "ProvisionedDevices"
	 */
	private co.com.directv.sdii.dto.esb.WorkOrder copyWorkOrderObjectFromWoEvent(PublishWorkOrderEvent esbWoEvent, String countryCode) throws PropertiesException, BusinessException{
		co.com.directv.sdii.dto.esb.WorkOrder localWo = new co.com.directv.sdii.dto.esb.WorkOrder();
		
		if(esbWoEvent == null || esbWoEvent.getDataArea() == null){
			//throw exception
			log.error("No se creó el evento por falta de información");
		}
		
		/*1. WorkOrder */
		if(esbWoEvent.getDataArea().getWorkOrder() != null){
			//1. ID
			localWo.setID(esbWoEvent.getDataArea().getWorkOrder().getID());
			//1. Action
			localWo.setAction(esbWoEvent.getDataArea().getWorkOrder().getActionTaken());
			//1. InteractionDate
			localWo.setInteractionDate(esbWoEvent.getDataArea().getWorkOrder().getInteractionDate());
			//1. InteractionDateComplete
			localWo.setInteractionDateComplete(esbWoEvent.getDataArea().getWorkOrder().getInteractionDateComplete());
			//1. RequestedDeliveryDate
			localWo.setRequestedDeliveryDate(esbWoEvent.getDataArea().getWorkOrder().getRequestedDeliveryDate());
			//1. WorkingTime
			localWo.setWorkingTime(esbWoEvent.getDataArea().getWorkOrder().getWorkingTime());
			//1. Description.
			localWo.setDescription(esbWoEvent.getDataArea().getWorkOrder().getDescription());
			//1. CreateDateTime (mapeado al CreateDate)
			if(esbWoEvent.getDataArea().getHistoryEvent() != null){
				localWo.setCreateDate(esbWoEvent.getDataArea().getHistoryEvent().getCreateDatetime());				
			}
			
			localWo.setAddressId(esbWoEvent.getDataArea().getWorkOrder().getAddressId()); 

			//1. Ibs Technician
			if(esbWoEvent.getDataArea().getWorkOrder().getTechnician()!=null){
				localWo.setIbsTechnical(esbWoEvent.getDataArea().getWorkOrder().getTechnician().getID());
			}
			
			//1. iso2code del Country.
			co.com.directv.sdii.dto.esb.GeographicArea localGeoArea = new co.com.directv.sdii.dto.esb.GeographicArea();
			co.com.directv.sdii.dto.esb.Country localCountry = new co.com.directv.sdii.dto.esb.Country();
			if(countryCode != null){				
				localCountry.setIso2Code(countryCode);
				localGeoArea.setCountry(localCountry);
				localWo.setGeographicArea(localGeoArea);
			}
			
			if(esbWoEvent.getDataArea().getWorkOrder().getServiceProvider() != null && esbWoEvent.getDataArea().getWorkOrder().getServiceProvider().getID() != null){
				//1. DealerId
				localWo.setDealerId(Long.parseLong(esbWoEvent.getDataArea().getWorkOrder().getServiceProvider().getID()));				
			}
			if(esbWoEvent.getDataArea().getWorkOrder().getInteractionStatus() != null){				
				//1. InteractionStatus
				localWo.setInteractionStatus(esbWoEvent.getDataArea().getWorkOrder().getInteractionStatus().getId());
				//1. WorkorderStatusByActualStatusId
				localWo.setWorkorderStatusByActualStatusId(esbWoEvent.getDataArea().getWorkOrder().getInteractionStatus().getId());
			}
			
			/*2. ServiceList */
			if(esbWoEvent.getDataArea().getWorkOrder().getWorkOrderItemList() != null 
					&& esbWoEvent.getDataArea().getWorkOrder().getWorkOrderItemList().getWorkOrderItem() != null){
				
				List<WorkOrderItem> services = esbWoEvent.getDataArea().getWorkOrder().getWorkOrderItemList().getWorkOrderItem();
				co.com.directv.sdii.dto.esb.ServiceCollection localServiceCollection = new co.com.directv.sdii.dto.esb.ServiceCollection();
				localServiceCollection.getService();
				
				for(WorkOrderItem service : services){
					co.com.directv.sdii.dto.esb.Service localService = new co.com.directv.sdii.dto.esb.Service();
					if(service.getID() != null){
						localService.setServiceKey(service.getID().toString());		
					}
					if(service.getBusinessInteractionItemInvolvesService() != null){
						localService.setCode(service.getBusinessInteractionItemInvolvesService().getCode());						
					}						
					localService.setProductSerialNumber(service.getBusinessInteractionItemInvolvesResource());
					
					
					localServiceCollection.getService().add(localService);
					
				}
				localWo.setServiceList(localServiceCollection);
			}
			/* 2. ServiceList */
		}
		/*1. WorkOrder */
		
		/*1. HistoryEvent */
		if (esbWoEvent.getDataArea().getHistoryEvent() != null){			
			//1. InitiatedBy
			localWo.setInitiatedBy(esbWoEvent.getDataArea().getHistoryEvent().getUserKey());
			//1. CreateDate (mal mapeado)
			//localWo.setCreateDate(esbWoEvent.getDataArea().getHistoryEvent().getCreateDatetime());
			//1. ID
			if(esbWoEvent.getDataArea().getHistoryEvent().getID() != null){				
				localWo.setHistoryCodeEventId(esbWoEvent.getDataArea().getHistoryEvent().getID().longValue());
			}
			
			//Spira 22159 - REPORTES FTP Campos inconsistentes
			if (esbWoEvent.getDataArea().getHistoryEvent().getReason() != null){
				localWo.setReason(esbWoEvent.getDataArea().getHistoryEvent().getReason());
			}
		}
		/*1. HistoryEvent */
		
		/* 1. ShippingOrder */
		if(esbWoEvent.getDataArea().getShippingOrder() != null){
			
			co.com.directv.sdii.dto.esb.ShippingOrder localShippingOrder = new co.com.directv.sdii.dto.esb.ShippingOrder();

			if(esbWoEvent.getDataArea().getShippingOrder().getType() != null){				
				localShippingOrder.setShippingOrderKey(esbWoEvent.getDataArea().getShippingOrder().getType().getId());
			}
			localShippingOrder.setSoldBy(esbWoEvent.getDataArea().getShippingOrder().getInitiatedBy());
			localShippingOrder.setShippingMethodKey(esbWoEvent.getDataArea().getShippingOrder().getShippingMethodId());
			localShippingOrder.setContractNumber(esbWoEvent.getDataArea().getShippingOrder().getContractNumber());
			localShippingOrder.setContractCode(esbWoEvent.getDataArea().getShippingOrder().getContractCode());
			localShippingOrder.setID(esbWoEvent.getDataArea().getShippingOrder().getID());
			localShippingOrder.setDescription(esbWoEvent.getDataArea().getShippingOrder().getDescription());
			localShippingOrder.setInteractionDate(esbWoEvent.getDataArea().getShippingOrder().getInteractionDate());
			localShippingOrder.setInteractionDateComplete(esbWoEvent.getDataArea().getShippingOrder().getInteractionDateComplete());
			if(esbWoEvent.getDataArea().getShippingOrder().getInteractionStatus() != null){
				localShippingOrder.setInteractionStatus(esbWoEvent.getDataArea().getShippingOrder().getInteractionStatus().getId());				
			}
			
			localWo.setShippingOrder(localShippingOrder);
		}
		/* 1. ShippingOrder */
		
        /* 1. Products de Shipping */
        if(esbWoEvent.getDataArea().getShippingOrder() != null
                      && esbWoEvent.getDataArea().getShippingOrder().getProductList() != null
                      && esbWoEvent.getDataArea().getShippingOrder().getProductList().getProduct() != null){
               
               co.com.directv.sdii.dto.esb.ProductCollection localProductCollection = new co.com.directv.sdii.dto.esb.ProductCollection();
               localProductCollection.getProduct();
               
               List<Product> productList = esbWoEvent.getDataArea().getShippingOrder().getProductList().getProduct();
               for(Product product : productList){
                      co.com.directv.sdii.dto.esb.Product localProduct = new co.com.directv.sdii.dto.esb.Product();
                      if(product.getProductCategory() != null && product.getProductCategory().getId() != null){
                    	  localProduct.setModelKey(product.getProductCategory().getId().toString());
                      }
                      if(product != null && product.getProductHasPhysicalResources() != null 
                    		  && product.getProductHasPhysicalResources().getPhysicalResources() != null 
                    		  && product.getProductHasPhysicalResources().getPhysicalResources().get(0) != null){                    	  
                    	  localProduct.setProductSerialNumber(product.getProductHasPhysicalResources().getPhysicalResources().get(0).getSerialNumber());
                      }
                      
                      if(product.getProductCharacteristicValueCollection() != null && product.getProductCharacteristicValueCollection().getProductCharValue() != null){               	  
	                      for(co.com.directv.sdii.dto.esb.event.publishworkorderevent.ProductCharacteristicValue prodCharValue : product.getProductCharacteristicValueCollection().getProductCharValue()){
	                             if(prodCharValue.getProductSpecCharacteristic() != null && 
	                            		 CodesBusinessEntityEnum.UPDATE_WORK_ORDER_TYPE_PRODUCT_TECHNOLOGY.getCodeEntity().equals(prodCharValue.getProductSpecCharacteristic().getID())){                                        
	                                   localProduct.setType(prodCharValue.getValue());
	                             }
	                      }
	                      localProductCollection.getProduct().add(localProduct);
                      }
               }
               
               if(localWo.getShippingOrder() != null){
                      localWo.getShippingOrder().setProductList(localProductCollection);
               }else{
                      co.com.directv.sdii.dto.esb.ShippingOrder localShippingOrder = new co.com.directv.sdii.dto.esb.ShippingOrder();
                      localShippingOrder.setProductList(localProductCollection);
                      localWo.setShippingOrder(localShippingOrder);
               }
        }
        /* 1. Products de Shipping */

		
		/* 1. Customer */
		if(esbWoEvent.getDataArea().getCustomer() != null){
			
			co.com.directv.sdii.dto.esb.Customer customerLocal = new co.com.directv.sdii.dto.esb.Customer();
			
			//2. ID
			customerLocal.setID(esbWoEvent.getDataArea().getCustomer().getID());
			
			//2. CustomerRank
			if(esbWoEvent.getDataArea().getCustomer().getCustomerRank() != null){
				customerLocal.setCustomerRank(esbWoEvent.getDataArea().getCustomer().getCustomerRank().toString());
			}
			
			//2. CustomerRank
			if(esbWoEvent.getDataArea().getCustomer().getCategorizedBy() != null){
				customerLocal.setCategorizedById(esbWoEvent.getDataArea().getCustomer().getCategorizedBy().getId());
			}
			//2. Agreement
			if(esbWoEvent.getDataArea().getCustomer().getAgreement() != null && esbWoEvent.getDataArea().getCustomer().getAgreement().getReferencesId() != null 
				&& !esbWoEvent.getDataArea().getCustomer().getAgreement().getReferencesId().isEmpty() ){				
				localWo.setDealerISalesd(Long.parseLong(esbWoEvent.getDataArea().getCustomer().getAgreement().getReferencesId()));
			}
			
			/*2. UrbanPropertyAddress */
			if(esbWoEvent.getDataArea().getCustomer().getAddressList() != null
					&& esbWoEvent.getDataArea().getCustomer().getAddressList().getUrbanPropertyAddress() != null){
				
				List<UrbanPropertyAddress> upaList = esbWoEvent.getDataArea().getCustomer().getAddressList().getUrbanPropertyAddress();
				co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection localUpaCollection = new co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection();
				localUpaCollection.getUrbanPropertyAddress();
				
				for(UrbanPropertyAddress upa : upaList){
					co.com.directv.sdii.dto.esb.UrbanPropertyAddress localUpa = new co.com.directv.sdii.dto.esb.UrbanPropertyAddress();
					
					localUpa.setAdditionalAttribute2(upa.getAdditionalAttribute2());
					localUpa.setId(upa.getID());
					localUpa.setStreetName(upa.getStreetName());
					localUpa.setStreetNrFirst(upa.getStreetNrFirst());
					localUpa.setStreetNrLast(upa.getStreetNrLast());
					localUpa.setStreetSuffix(upa.getStreetSuffix());
					localUpa.setPostCode(upa.getPostCode());
					localUpa.setAdditionalAttribute1(upa.getAdditionalAttribute1());
					if(upa.getCategorizedBy() != null){						
						localUpa.setCategorizedByName(upa.getCategorizedBy().getName());
					}
					localUpa.setLocality(upa.getLocality());						
					localUpa.setAddressCode(upa.getAddressCode());
					
					localUpaCollection.getUrbanPropertyAddress().add(localUpa);
				}
				
				customerLocal.setAddressList(localUpaCollection);
			}
			/*2. UrbanPropertyAddress */
			
			/*2. ContactMedium */
			if(esbWoEvent.getDataArea().getCustomer().getContactableVia() != null 
					&& esbWoEvent.getDataArea().getCustomer().getContactableVia().getContactMedium() != null){
				
				List<ContactMedium> contactMediumList = esbWoEvent.getDataArea().getCustomer().getContactableVia().getContactMedium();
				co.com.directv.sdii.dto.esb.ContactMediumCollection localContactMediumCollection = new co.com.directv.sdii.dto.esb.ContactMediumCollection();
				localContactMediumCollection.getContactMedium();
				
				for(ContactMedium contactMedium : contactMediumList){
					if(contactMedium instanceof TelephoneNumber){
						co.com.directv.sdii.dto.esb.TelephoneNumber localTelephoneNumber = new co.com.directv.sdii.dto.esb.TelephoneNumber();
						localTelephoneNumber.setNumber(((TelephoneNumber) contactMedium).getNumber());
						localTelephoneNumber.setType(((TelephoneNumber) contactMedium).getType());
						localContactMediumCollection.getContactMedium().add(localTelephoneNumber);
					}
					if(contactMedium instanceof FaxNumber){
						co.com.directv.sdii.dto.esb.FaxNumber localFaxNumber = new co.com.directv.sdii.dto.esb.FaxNumber();
						localFaxNumber.setNumber(((FaxNumber) contactMedium).getNumber());
						localFaxNumber.setType(((FaxNumber) contactMedium).getType());
						localContactMediumCollection.getContactMedium().add(localFaxNumber);
					}
					if(contactMedium instanceof EmailContact){
						co.com.directv.sdii.dto.esb.EmailContact localEmailContact = new co.com.directv.sdii.dto.esb.EmailContact();
						localEmailContact.setEMailAddress(((EmailContact) contactMedium).getEMailAddress());
						localEmailContact.setEMailProtocol(((EmailContact) contactMedium).getEMailProtocol());
						localContactMediumCollection.getContactMedium().add(localEmailContact);
					}
				}
				
				customerLocal.setContactableVia(localContactMediumCollection);
			}
			/*2. ContactMedium */
			
			/*2. IndividualRole */
			if(esbWoEvent.getDataArea().getCustomer().getIndividualRole() != null){
				co.com.directv.sdii.dto.esb.Individual localIndividual = new co.com.directv.sdii.dto.esb.Individual();
				
				/*3. NameUsing */
				if(esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing() != null
						&& esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames() != null
						&& !esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames().isEmpty()){
					
					localIndividual.getIndividualName();
					
					co.com.directv.sdii.dto.esb.IndividualName localIndividualName = new co.com.directv.sdii.dto.esb.IndividualName();
					if(esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getQualifications() != null){
						localIndividualName.setQualifications(esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getQualifications().getName());						
					}
					if(esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getAristocraticTitle() != null &&
					!esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getAristocraticTitle().isEmpty()){
						localIndividualName.setAristocraticTitle(esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getAristocraticTitle());						
					}
					localIndividualName.setGivenNames(esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getGivenNames());
					localIndividualName.setFamilyNames(esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getFamilyNames());
					localIndividualName.setLegalName(esbWoEvent.getDataArea().getCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getLegalName());
				
					localIndividual.getIndividualName().add(localIndividualName);
				}
				/*3. NameUsing */
				
				/*3. IndividualIdentifications */
				if(esbWoEvent.getDataArea().getCustomer().getIndividualRole().getIdentifiedBy() != null
						&& esbWoEvent.getDataArea().getCustomer().getIndividualRole().getIdentifiedBy().getIndividualIdentifications() != null){
					
					List<IndividualIdentification> individualIdentificationList = esbWoEvent.getDataArea().getCustomer().getIndividualRole().getIdentifiedBy().getIndividualIdentifications();
					localIndividual.getIndividualIdentification();
					
					for(IndividualIdentification individualIdentification : individualIdentificationList){
						if(individualIdentification instanceof NationalIdentityCardIdentification){
							co.com.directv.sdii.dto.esb.NationalIdentityCardIdentification localNationalIdentityCardId = new co.com.directv.sdii.dto.esb.NationalIdentityCardIdentification();
							localNationalIdentityCardId.setCardNr(((NationalIdentityCardIdentification) individualIdentification).getCardNr());
							localNationalIdentityCardId.setScan(individualIdentification.getScan());
							
							localIndividual.getIndividualIdentification().add(localNationalIdentityCardId);
						}
					}
				}
				/*3. IndividualIdentifications */
				
				customerLocal.setIndividualRole(localIndividual);
			}
			/*2. IndividualRole */
			
			/*2 ProductList*/							
				if(    esbWoEvent.getDataArea().getActivePhysicalProductList() != null
					&& esbWoEvent.getDataArea().getActivePhysicalProductList().getProduct() != null){
					
					co.com.directv.sdii.dto.esb.ProductCollection localProductCollection = new co.com.directv.sdii.dto.esb.ProductCollection();
					
					localProductCollection.getProduct();
					
					List<Product> productList = esbWoEvent.getDataArea().getActivePhysicalProductList().getProduct();
					
					for(Product product : productList){
						
						co.com.directv.sdii.dto.esb.Product localProduct = new co.com.directv.sdii.dto.esb.Product();
						
						localProduct.setName(product.getName());
						
						if(    product.getProductHasPhysicalResources() != null 
							&& product.getProductHasPhysicalResources().getPhysicalResources() != null){							
								 
							List<PhysicalResource> physicalResourcesOrdered = 
									this.orderPhysicalResources(product.getProductHasPhysicalResources().getPhysicalResources(), countryCode);
							
							if(        product.getProductHasPhysicalResources().getPhysicalResources().size() >= 1 //si la lista de productos es de un elemento o más
									&& physicalResourcesOrdered.get(0) != null){
								
									if(physicalResourcesOrdered.get(0).getStatus() != null){
								
										localProduct.setProductStatusName(physicalResourcesOrdered.get(0).getStatus().getName());
										localProduct.setProductStatus(physicalResourcesOrdered.get(0).getStatus().getId());
									}
								
									localProduct.setProvisionedDevices(physicalResourcesOrdered.get(0).getSerialNumber());
							}
							
							if(		   product.getProductHasPhysicalResources().getPhysicalResources().size() > 1 //si la lista de productos es más de un elemento 
									&& physicalResourcesOrdered.get(1) != null){
								
									localProduct.setProvisionedDevicesRelated(physicalResourcesOrdered.get(1).getSerialNumber());
							}
						}			
						
						localProductCollection.getProduct().add(localProduct);
					}
					
					customerLocal.setProductList(localProductCollection);
				}
				
				
			/*2 ProductList*/
			
			/*Paquete de programacion del cliente*/
			if(esbWoEvent.getDataArea().getProductCategory()!=null && esbWoEvent.getDataArea().getProductCategory().getDescription()!=null){
				customerLocal.setProduct(esbWoEvent.getDataArea().getProductCategory().getDescription());
			}
					
			localWo.setCustomer(customerLocal);
			
		}
		/* 1. Customer */

		/*1. CustomerAgreementList */
		if(esbWoEvent.getDataArea().getCustomerAgreementList()  != null
				&& esbWoEvent.getDataArea().getCustomerAgreementList()  != null
                && esbWoEvent.getDataArea().getCustomerAgreementList().getCustomerAgreement() != null){			
			
			co.com.directv.sdii.dto.esb.event.publishworkorderevent.CustomerAgreementCollection localCustomerAgreementCollection = 
					new co.com.directv.sdii.dto.esb.event.publishworkorderevent.CustomerAgreementCollection();
			
			//localCustomerAgreementCollection.getCustomerAgreement();
			//por definicion funcional un cliente siempre tiene al menos un agreement
			List<Agreement> agreementList = esbWoEvent.getDataArea().getCustomerAgreementList().getCustomerAgreement();
			for(Agreement agreement : agreementList){
				co.com.directv.sdii.dto.esb.event.publishworkorderevent.Agreement localAgreement = new co.com.directv.sdii.dto.esb.event.publishworkorderevent.Agreement();
				//es necesario cargar todos los demas atributos de agreement?
				localAgreement.setType(agreement.getType());
				localCustomerAgreementCollection.getCustomerAgreement().add(localAgreement);
			}
			
			if (localWo.getCustomer() != null) {
				localWo.getCustomer().setCustomerAgreement(localCustomerAgreementCollection);				
			}
		}
			
		/*1. CustomerAgreementList */
		
		/* 1. MDUCustomer (Building) */
		if(esbWoEvent.getDataArea().getMDUCustomer() != null){
			
			co.com.directv.sdii.dto.esb.Customer buildingLocal = new co.com.directv.sdii.dto.esb.Customer();
			
			//2. ID
			buildingLocal.setID(esbWoEvent.getDataArea().getMDUCustomer().getID());
			
			/*2. UrbanPropertyAddress (Building) */
			if(esbWoEvent.getDataArea().getMDUCustomer().getAddressList() != null
					&& esbWoEvent.getDataArea().getMDUCustomer().getAddressList().getUrbanPropertyAddress() != null){
				
				List<UrbanPropertyAddress> upaList = esbWoEvent.getDataArea().getMDUCustomer().getAddressList().getUrbanPropertyAddress();
				co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection localUpaCollection = new co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection();
				localUpaCollection.getUrbanPropertyAddress();
				
				for(UrbanPropertyAddress upa : upaList){
					co.com.directv.sdii.dto.esb.UrbanPropertyAddress localUpa = new co.com.directv.sdii.dto.esb.UrbanPropertyAddress();
					
					localUpa.setAdditionalAttribute2(upa.getAdditionalAttribute2());
					localUpa.setStreetName(upa.getStreetName());
					localUpa.setStreetNrFirst(upa.getStreetNrFirst());
					localUpa.setStreetNrLast(upa.getStreetNrLast());
					localUpa.setStreetSuffix(upa.getStreetSuffix());
					localUpa.setPostCode(upa.getPostCode());
					localUpa.setAdditionalAttribute1(upa.getAdditionalAttribute1());
					if(upa.getCategorizedBy() != null){
						localUpa.setCategorizedByName(upa.getCategorizedBy().getName());
					}
					localUpa.setLocality(upa.getLocality());						
					localUpa.setAddressCode(upa.getAddressCode());
					localUpa.setFlatApartment(upa.getFlatOrApartment());
					
					
					localUpaCollection.getUrbanPropertyAddress().add(localUpa);
				}
				
				buildingLocal.setAddressList(localUpaCollection);
			}
			/*2. UrbanPropertyAddress (Building) */
			
			/*2. IndividualRole (Building) */
			if(esbWoEvent.getDataArea().getMDUCustomer().getIndividualRole() != null){
				co.com.directv.sdii.dto.esb.Individual localIndividual = new co.com.directv.sdii.dto.esb.Individual();
				
				/*3. NameUsing */
				if(esbWoEvent.getDataArea().getMDUCustomer().getIndividualRole().getNameUsing() != null
						&& esbWoEvent.getDataArea().getMDUCustomer().getIndividualRole().getNameUsing().getIndividualNames() != null
						&& !esbWoEvent.getDataArea().getMDUCustomer().getIndividualRole().getNameUsing().getIndividualNames().isEmpty()){
					
					localIndividual.getIndividualName();
					
					co.com.directv.sdii.dto.esb.IndividualName localIndividualName = new co.com.directv.sdii.dto.esb.IndividualName();
					localIndividualName.setGivenNames(esbWoEvent.getDataArea().getMDUCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getGivenNames());
					localIndividualName.setMiddleNames(esbWoEvent.getDataArea().getMDUCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getMiddleNames());
					localIndividualName.setFamilyNames(esbWoEvent.getDataArea().getMDUCustomer().getIndividualRole().getNameUsing().getIndividualNames().get(0).getFamilyNames());
					
					localIndividual.getIndividualName().add(localIndividualName);
				}
				/*3. NameUsing */
				
				buildingLocal.setIndividualRole(localIndividual);
			}
			/*2. IndividualRole (Building) */
			
			localWo.setMDUCustomer(buildingLocal);
			
		}
		/* 1. MDUCustomer (Building) */
		
		return localWo;
	}
	
	
	/**
	 * Metodo: Se encarga de ordenar los productos fisicos que tiene instalados el cliente.
	 * Para identificar cual es el IRD y cual es la SC se utiliza el serialNumber del primer producto de la lista.
	 * Se busca en HSP la clase de elemento al que pertenece dicho producto y de esta manera se determina si es el IRD o la SC.<br/>
	 * Flujo Alternativo 1: Si se encuentra desactivado el modulo de inventarios, no se hará ningún cambio en el orden de los productos. <br/>
	 * Flujo Alternativo 2: Si se obtiene una cantidad de elementos distinta a 2, no se hara ningún cambio en el orden. <br/>
	 * Flujo Alternativo 3: Si no se encuentra el elemento en HSP, no se hará ningún cambio en el orden de los productos. <br/>
	 * @param physicalResources - la lista de productos del cliente.
	 * @param countryCode - El iso2code del pais.
	 * @return Se devuelve una lista de PhysicalResources ordenando en primer lugar a IRD y en segundo lugar la SC, 
	 * a menos que se origine un caso particular de los mencionados.
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 */
	private List<PhysicalResource> orderPhysicalResources(List<PhysicalResource> physicalResources, String countryCode) throws BusinessException, PropertiesException {
		log.debug("== Inicia orderPhysicalResources/CoreWOImporter ==");
		
		//Si esta desactivado el modulo de inventarios de HSP.
		SystemParameter invokeInternalInventory = systemParameterBusiness.getSysParamByCodeAndCountryCode(CodesBusinessEntityEnum.SYSTEM_PARAM_INVOKE_HSP_INVENTORY_SERVICE.getCodeEntity(), countryCode);
		if(invokeInternalInventory != null 
				&& invokeInternalInventory.getValue().equals(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity())){
			log.debug("Se encuentra desactivado el Modulo de Inventarios de HSP.");
			return physicalResources;
		}
		
		
		if(physicalResources.size() != 2){
			log.warn("orderPhysicalResources no tiene 2 elementos. Tiene: " + physicalResources.size());
			return physicalResources;
		}
		
		ElementClass elementClass = elementClassBusiness.getElementClassByElementSerialNumber(physicalResources.get(0).getSerialNumber());
		if(elementClass == null){
			log.warn("orderPhysicalResources no se encontró el elemento con serial: " + physicalResources.get(0).getSerialNumber());
			return physicalResources;
		}
		
		List<PhysicalResource> physicalResourceOrdered = new java.util.ArrayList<PhysicalResource>();
		//Si el primero es una SC tengo que darlos vuelta.
		if(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity().equals(elementClass.getElementClassCode())){			
			log.debug("Se cambia el orden de los productos. ");

			physicalResourceOrdered.add(physicalResources.get(1));
			physicalResourceOrdered.add(physicalResources.get(0));
		}else{
			log.debug("Se mantiene el orden de los productos. ");

			physicalResourceOrdered = physicalResources;
		}
		
		if(log.isDebugEnabled()) log.debug("IRD:["+ physicalResourceOrdered.get(0).getSerialNumber()+"] | SC:["+ physicalResourceOrdered.get(1).getSerialNumber() +"]");
		
		log.debug("== Termina orderPhysicalResources/CoreWOImporter ==");
		return physicalResourceOrdered;
	}

	/**
	 * Metodo: Crea las wo desde ibs envolviendo cada WO en una transacción independiente, modificado para procesamiento en paralelo de core
	 * @param countryCode código del país en donde se realizará la importación de wo
	 * @throws BusinessException En caso de error al realizar la importación
	 * @author jjimenezh, aharker
	 */
	@Deprecated
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void addWorkOrder(String countryCode) throws BusinessException {
		
		log.debug("addWorkOrder/CoreWOImporter");
		WoLoad load = woLoadBusiness.getLastWoLoad(countryCode);
		if ( load != null ) {
			if ( load.getEndDate() == null ) {
				log.warn("Se encuentra un proceso de importacion de WorkOrders Activo");
				return;
			}
		}
		
		List<com.directvla.schema.crm.common.v1_1.WorkOrder> ibsWos = business.getWorkordersFromIBS(countryCode);
		
		if (ibsWos.isEmpty()) {
			log.warn("No se encontraron work orders para importar desde IBS");			
			return;
		}
		
		Integer i = 0, size = ibsWos.size();
		log.info("Se encontraron " + size + " work orders para importar desde IBS");

		for (com.directvla.schema.crm.common.v1_1.WorkOrder ibsWorkorder : ibsWos) {
			
			try{
				
				co.com.directv.sdii.dto.esb.WorkOrder woHSP = copyWorkOrderObject(ibsWorkorder);
				
				//woInfoEsbServiceBusinessLocal.createWoInfoEsbServiceForCore(woHSP, countryDao.getCountriesByCode(countryCode).getId());
				
			} catch (Throwable e) {
				log.fatal("Al tratar de crear la WO " + i + " de " + size + "con código " + ibsWorkorder.getID());
				BusinessException be = super.manageException(e);
				continue;
			}
		}
	
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOImporterLocal#addEvent(co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent)
	 */
	public void addEvent(PublishWorkOrderEvent publishWoEvent, String iso3Code) throws BusinessException{
		log.debug("Inicia addEvent/CoreWOImporter");
		
		try{
			Country country = countryDao.getCountriesByIso3Code(iso3Code);
			if(country == null){
				throw new BusinessException("El código ISO no fue encontrado", "El código ISO no fue encontrado");
			}
			
			woInfoEsbServiceBusinessLocal.createWoInfoEsbServiceForCore(publishWoEvent, country.getId());
		
		} catch (Throwable e) {
			log.fatal("Al tratar de crear el WOEvent con código : " + publishWoEvent.getDataArea().getWorkOrder().getID());
			BusinessException be = super.manageException(e);
		} finally {			
			log.debug("Finaliza addEvent/CoreWOImporter");
		}
	}
	
	/**
	 * Metodo que se encarga de realizar la descarga de una work order para el procesamiento en paralelo de core
	 * @param ibsWorkorder objeto proveniente del servicio de descarga
	 * @param workOrderDto DTO a llenar con todos los datos de la Work Order
	 * @param countryCode codigo del pais del proceso de core que se corre
	 * @param idProcess id de la tabla de eventos de procesamiento en paralelo de core y asignador
	 * @param countryId id del pais del proceso de core que se corre
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public void addWorkOrder(PublishWorkOrderEvent publishWoEvent, WorkOrderDTO workOrderDto, String countryCode, Long idProcess, Long countryId) throws 
		BusinessException{
		boolean importWo = true;
		co.com.directv.sdii.dto.esb.WorkOrder ibsWorkorder;
		try{
			ibsWorkorder = copyWorkOrderObjectFromWoEvent(publishWoEvent, countryCode);
		} catch (Throwable e) {
			log.fatal("Al tratar de copiar PublishWorkOrderEvent a WorkOrder con código : " + publishWoEvent.getDataArea().getWorkOrder().getID());
			super.manageException(e);
			return;
		}
		
		try {
			
			log.info("Importando la WO con codigo: " + ibsWorkorder.getID() + " con estado: " + ibsWorkorder.getWorkorderStatusByActualStatusId());
			
			//valda si se importa la workOrder o solo sele actualiza el estado
			importWo = checkWoStatusIsValidByUpdate(ibsWorkorder,workOrderDto, idProcess, countryId);
			if ( !importWo) {
				log.debug("== al ejecutar la operación addWorkOrder/ParallelProcessingCoreConsumer, no importa completa la WO, solo ac==");
				String codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED.getCodeEntity();
				String messageError = " Unicamente actualiza el estado de la work order ";
				woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish, "Proceso terminado exitosamente - "+messageError, null,
						CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, true);
			}else{					
				//Spira 22159 - REPORTES FTP:Campos inconsistentes
				workOrderDto = business.convertIbsWoIntoSdiiWo(ibsWorkorder, countryCode);
				if(workOrderDto != null)
				{
					if(!validateWorkOrderHasShippingOrder(workOrderDto))
					{
						log.debug("== Uno de los servicios incluidos en la Work order requiere Shipping Order ==");
						String codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED.getCodeEntity();
						String messageError = " Uno de los servicios incluidos en la Work order requiere Shipping Order ";
						woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish,messageError, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE_WARNING.getCodeEntity(),
								CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, false);
					}
				}
			}
		} catch (Throwable ex) {
			try{
				log.fatal("No se creará en HSP la WO con código " + ibsWorkorder.getID() + " Debido a errores previos.");
				BusinessException be = super.manageException(ex);
				String codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity();
				String messageError = be.getMessage();
				woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish,messageError, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE.getCodeEntity(),
						CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, true);
			}catch (Throwable e) {
			}
			return;
		}
		
		try {
			if ( importWo ) {
				
				// Si la orden viene cancelada desde IBS se invoca el servicio de cancelacion de WO del EJB
//				WorkorderStatus statusCanceled = this.workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
//				UtilsBusiness.assertNotNull(statusCanceled, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//				if(workOrderDto.getWorkOrder().getWorkorderStatusByActualStatusId().getWoStateCode().equals(statusCanceled.getWoStateCode()))
//				{
//					business.cancelWorkOrderFromIBS(workOrderDto, countryCode);
//				}
//				else
//				{
					persistWorkOrder(countryCode, workOrderDto);
//				}
				
				if(workOrderDto!=null
						&& workOrderDto.getWorkOrder()!=null
						&& workOrderDto.getWorkOrder().getCustomer()!=null){
					if(workOrderDto.getWorkOrder().getCustomer().getCustType()!=null){
						if(workOrderDto.getWorkOrder().getCustomer().getCustType().getCustomerClass()!=null
								&& workOrderDto.getWorkOrder().getCustomer().getCustType().getCustomerClass().getCustomerClassCode()!=null
								&& workOrderDto.getWorkOrder().getCustomer().getCustType().getCustomerClass().getCustomerClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.CUSTOMER_CODE_CLASS_DEFAULT.getCodeEntity())
								&& workOrderDto.getWorkOrder().getCustomer().getCustType().getCustomerType()!=null
								&& workOrderDto.getWorkOrder().getCustomer().getCustType().getCustomerType().getCustomerTypeCode()!=null
								&& workOrderDto.getWorkOrder().getCustomer().getCustType().getCustomerType().getCustomerTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.CUSTOMER_CODE_TYPE_DEFAULT.getCodeEntity())){
							
							String codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED.getCodeEntity();
							String messageError = ErrorBusinessMessages.CUSTOMER_CLASS_OR_TYPE_DOES_NOT_EXIST.getMessage()+" < codigo de clase de cliente = "+workOrderDto.getCustomerCodeClass()+" , codigo de tipo de cliente = "+workOrderDto.getCustomerCodeType()+" > ";
							woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish,messageError, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE_WARNING.getCodeEntity(),
									CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, false);
							
						}
					}
					else{
						String codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED.getCodeEntity();
						String messageError = ErrorBusinessMessages.CUSTOMER_CLASS_OR_TYPE_DOES_NOT_EXIST.getMessage()+" < codigo de clase de cliente = "+workOrderDto.getCustomerCodeClass()+" , codigo de tipo de cliente = "+workOrderDto.getCustomerCodeType()+" > ";
						woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish,messageError, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE_WARNING.getCodeEntity(),
								CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, false);
					}
				}
				
				log.info("Se creó satisfactoriamente la WO  con código " + ibsWorkorder.getID() + " en estado: " + workOrderDto.getWorkOrder().getWorkorderStatusByActualStatusId().getWoStateName());
				
				if( !workOrderDto.isError() && !workOrderDto.isWarning()){
					
					try {
						String codeFinish;

						codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED.getCodeEntity();

						String messageError = "Proceso terminado exitosamente";
						woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish,messageError, null,
								CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, true);
					} catch (PropertiesException e1) {
					}
					
				} else if(workOrderDto.isWarning()){
					BusinessException be = new BusinessException(workOrderDto.getErrorCode(), workOrderDto.getErrorMessage());
					try {
						String codeFinish;

						codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED.getCodeEntity();

						String messageError = be.getMessage();
						woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish,messageError, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE_WARNING.getCodeEntity(),
								CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, true);
					} catch (PropertiesException e1) {
					}
				}else if(workOrderDto.isError()){
					BusinessException be = new BusinessException(workOrderDto.getErrorCode(), workOrderDto.getErrorMessage());
					try {
						String codeFinish;

						codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity();

						String messageError = be.getMessage();
						woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish,messageError, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE.getCodeEntity(),
								CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, true);
					} catch (PropertiesException e1) {
					}
				}
				
			}
		} catch (Throwable e) {
			try {
				log.fatal("Al tratar de crear la WO con código " + ibsWorkorder.getID());
				BusinessException be = super.manageException(e);
				String codeFinish;

				codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity();

				String messageError = be.getMessage();
				woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish,messageError, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE.getCodeEntity(),
						CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, true);
			} catch (PropertiesException e1) {
			}
			return;
		}
	}
	
	/**
	 * Metodo: Se llaman ciertos metodos que pueden exceptionar, para ello se colocan los metodos como REQUIRES_NEW
	 * @param countryCode
	 * @param workOrderDto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	private void persistWorkOrder(String countryCode,WorkOrderDTO workOrderDto) throws BusinessException{
		
		populateWorkOrderInformationSystemExternal(countryCode,workOrderDto);
		
		business.persistWorkOrder(countryCode, workOrderDto);
		
	}
	
	/**
	 * Metodo: Permite poblar cierta informacion de la work order suceptible a fallos por la comunicacion a ESB u otros sistemas externos
	 * @param countryCode
	 * @param workOrderDto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	private void populateWorkOrderInformationSystemExternal(String countryCode,WorkOrderDTO workOrderDto) throws BusinessException{
		
		workOrderMarkBusiness.populateRequiredContract(workOrderDto);
		
		if(workOrderDto.isCallEsbChangeStateSchedule()){
			business.callEsbByChangeStateScheduleBeforePersistentWorkOrder(countryCode, workOrderDto);
		}
		
		business.assignDealerSaleInformation(workOrderDto);
		
	}

	/**
	 * Metodo: permite validar si se importa la workOrder o solo sele actualiza el estado, modificado por aharker para procesamiento en paralelo
	 * @param ibsWorkorder workorder de ibs
	 * @param workOrderDto objecto a importar 
	 * @return
	 * @throws PropertiesException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte, aharker
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private boolean checkWoStatusIsValidByUpdate(co.com.directv.sdii.dto.esb.WorkOrder ibsWorkorder, WorkOrderDTO workOrderDto, Long idProcess, Long countryId) throws 
		PropertiesException, DAOServiceException, DAOSQLException, BusinessException{
		boolean isValidWoStatus = false;
		boolean result = true;
		//Se obtiene el estado de la workOrder
		WorkorderStatus workorderStatus = this.workorderStatusDAO.getWorkorderStatusByIBS6StatusCode(ibsWorkorder.getWorkorderStatusByActualStatusId());
		//Si la workorder esta en estado Realizada, finalizada o cancelada
		if(workorderStatus!=null){
			String woStateCode =  workorderStatus.getWoStateCode();
			if(woStateCode.equals(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity())
			   || woStateCode.equals(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity())
			   || woStateCode.equals(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity())){
				isValidWoStatus = true;
			}
		}
		List<WoStatusHistoryVO> woStatusHistory = null;
		//Si trae historyCodeEventId 
		if ( ibsWorkorder.getHistoryCodeEventId() != null && ibsWorkorder.getHistoryCodeEventId().longValue() > 0 ) {
			woStatusHistory = business.getWoStatusHistoryByWoCodeAndHistoryKey(ibsWorkorder.getID(), ibsWorkorder.getHistoryCodeEventId().toString());
			log.info("History Event: " + ibsWorkorder.getHistoryCodeEventId() + " recuperado al importar la WorkOrder con codigo: " + ibsWorkorder.getID());
		}
		//Si existe woStatusHistory y no es igual a los estados Realizada, finalizada o cancelada se importa sino no
		if(woStatusHistory != null && !woStatusHistory.isEmpty() && !isValidWoStatus){
			this.business.updateIBSStatusForWorkOrder(ibsWorkorder.getID(), ibsWorkorder.getWorkorderStatusByActualStatusId());
			String codeFinish = CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED.getCodeEntity();
			String messageError = ErrorBusinessMessages.CORE_CR062.getMessage();
			woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(idProcess, codeFinish,messageError, null, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity(), countryId, false);
			result = false;
		}
		return result;
	}
	
	/**
	 * Valida si una work order requiere tener asociada una shipping order
	 * @param workOrderDto Work order Dto
	 * @return True si la wo tiene shipping order
	 */
	private boolean validateWorkOrderHasShippingOrder(WorkOrderDTO workOrderDto)	{
		// Si alguno de los servicios asociados a la WO requiere una shipping order asociada y la WO no la tiene,
		// se agrega la WO a la lista de WO's con warning para enviar el correo
		co.com.directv.sdii.model.pojo.WorkOrder wo = workOrderDto.getWorkOrder();
		if(wo != null){
			Set<WorkOrderService> woservices = wo.getWorkOrderServices();
			if(woservices != null){
				for(WorkOrderService service : woservices){
					if(service.isShippingOrderRequired() && workOrderDto.getShippingOrder() == null){
						return false;
					}
				}
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOImporterLocal#updateInfoWorkOrderIBSToHSP(java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void updateInfoWorkOrderIBSToHSP(String countryCode,String woCode) throws BusinessException{
		log.debug("== Inicia updateInfoWorkOrderIBSToHSP/CoreWOImporter ==");
		try {
			//Se valida la informacion requerida u obligatoria
			validParamUpdateInfoWorkOrderIBSToHSP(countryCode,woCode);
			
			//Se consulta la workOrder en IBS; el metodo de consulta si no existe la workOrder genera una exception
			com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder ibsWorkOrder = business.getCustomerWorkOrdersById(countryCode,woCode);
			
			OrderHandlingServiceRequestDTO orderHandlingServiceRequestDTO = null;
			com.directvla.schema.businessdomain.common.orderhandling.v1_0.ShippingOrder shippingOrder = null;
			//Si el servicio getCustomerWorkOrderId retorna informacion de la shippingOrder
			if(ibsWorkOrder.getShippingOrder()!=null 
			   && !ibsWorkOrder.getShippingOrder().getShippingOrderKey().equals("")
			   && !ibsWorkOrder.getShippingOrder().getShippingOrderKey().equals("0")){
				orderHandlingServiceRequestDTO = new OrderHandlingServiceRequestDTO();
				orderHandlingServiceRequestDTO.setCountryCode(countryCode);
				orderHandlingServiceRequestDTO.setShippingOrderCode(ibsWorkOrder.getShippingOrder().getShippingOrderKey());
				shippingOrder = orderHandlingServiceBusiness.getShippingOrder(orderHandlingServiceRequestDTO);
			}
			
			PublishWorkOrderEvent publishWOEvent = populatePublishWorkOrderWithInfoManageworkforceAndOrderhandling(countryCode,ibsWorkOrder,shippingOrder);
			//co.com.directv.sdii.dto.esb.WorkOrder woHSP = populateWorkOrderWithInfoManageworkforceAndOrderhandling(countryCode,ibsWorkOrder,shippingOrder);
			//Crea el evento para que sea procesado por procesamiento en paralelo
			woInfoEsbServiceBusinessLocal.createWoInfoEsbServiceForCore(publishWOEvent, countryDao.getCountriesByCode(countryCode).getId());
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOImporter/updateInfoWorkOrderIBSToHSP");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina updateInfoWorkOrderIBSToHSP/CoreWOImporter ==");
		}
	}
	
	/**
	 * Metodo: pobla un objecto de co.com.directv.sdii.dto.esb.WorkOrder con la informacion consultada por el servicios de getCustomerWorkOrderId and getShippingOrder
	 * @param countryCode
	 * @param ibsWo
	 * @param shippingOrder
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws PropertiesException 
	 */
	private co.com.directv.sdii.dto.esb.WorkOrder populateWorkOrderWithInfoManageworkforceAndOrderhandling(String countryCode,
			                                                          com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder ibsWo,
			                                                          com.directvla.schema.businessdomain.common.orderhandling.v1_0.ShippingOrder shippingOrder) throws PropertiesException{
		
		
		co.com.directv.sdii.dto.esb.WorkOrder localWo = new co.com.directv.sdii.dto.esb.WorkOrder();
		
		//1. Action
		localWo.setAction(ibsWo.getActionTaken());
		
		//1. DealerId
		Long dealerId = 0l;
		if(!ibsWo.getServiceProvider().getID().equals("")){
			dealerId = Long.parseLong(ibsWo.getServiceProvider().getID());
		}
		localWo.setDealerId(dealerId);

		//1. DealerISalesd
		Long dealerISalesd = 0l; 
		if(!ibsWo.getStockhandler().getPartyRoleId().equals("")){
			dealerISalesd = Long.parseLong(ibsWo.getStockhandler().getPartyRoleId());
		}
		localWo.setDealerISalesd(dealerISalesd);
		
		//1. Description
		localWo.setDescription(ibsWo.getDescription());
		
		//1. HistoryCodeEventId se coloca -1l para poder marcar el tipo de cambio de la work order
		localWo.setHistoryCodeEventId(-1l);
		
		//1. ID
		localWo.setID(ibsWo.getID());
		
		//1. InitiatedBy
		localWo.setInitiatedBy(ibsWo.getInitiatedBy());
		//1. InteractionDate
		localWo.setInteractionDate(ibsWo.getInteractionDate());
		//1. InteractionDateComplete
		localWo.setInteractionDateComplete(ibsWo.getInteractionDateComplete());
		//1. InteractionStatus
		localWo.setInteractionStatus(ibsWo.getInteractionStatus().getId());
		//1. IsAppointment
		localWo.setIsAppointment(ibsWo.getIsAppointment());
		//1. PossibleDeliveryDate
		localWo.setPossibleDeliveryDate(ibsWo.getPossibleDeliveryDate());
		//1. RequestedDeliveryDate
		localWo.setRequestedDeliveryDate(ibsWo.getRequestedDeliveryDate());
		//1. WorkingTime
		localWo.setWorkingTime(ibsWo.getWorkingTime());
		//1. WorkorderStatusByActualStatusId
		localWo.setWorkorderStatusByActualStatusId(ibsWo.getInteractionStatus().getId());
		
		/* 1. Customer */
		if(ibsWo!=null && ibsWo.getCustomerKey()!=null){
			co.com.directv.sdii.dto.esb.Customer customerLocal = new co.com.directv.sdii.dto.esb.Customer();
			customerLocal.setID(ibsWo.getCustomerKey());

			/* 2. AddressList */
			co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection urbanPropertyAddressCollectionLocal = new 
				co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection();
			//3. UrbanPropertyAddress
			urbanPropertyAddressCollectionLocal.getUrbanPropertyAddress();
			/* 3. UrbanPropertyAddress */
			
			if (ibsWo.getAddressId()!=null){

					co.com.directv.sdii.dto.esb.UrbanPropertyAddress urbanPropertyAddressLocal = new co.com.directv.sdii.dto.esb.UrbanPropertyAddress();
					// 4. AddressCode
					urbanPropertyAddressLocal.setAddressCode(ibsWo.getAddressId());
					urbanPropertyAddressCollectionLocal.getUrbanPropertyAddress().add(urbanPropertyAddressLocal);
			}
			
			/* 3. UrbanPropertyAddress */
			customerLocal.setAddressList(urbanPropertyAddressCollectionLocal);
			localWo.setCustomer(customerLocal);	
		}
		
		/* 1. Customer */
		
		/* 1. GeographicArea */
	    co.com.directv.sdii.dto.esb.GeographicArea geographicAreaLocal = new co.com.directv.sdii.dto.esb.GeographicArea();
			

		co.com.directv.sdii.dto.esb.Country countryLocal = new co.com.directv.sdii.dto.esb.Country();
		countryLocal.setIso2Code(countryCode);
		geographicAreaLocal.setCountry(countryLocal);
		localWo.setGeographicArea(geographicAreaLocal);			
		/* 1. GeographicArea */
		
		/* 1. ServiceList */
		if(ibsWo.getWorkOrderItemList()!=null){
			co.com.directv.sdii.dto.esb.ServiceCollection serviceCollectionLocal=new co.com.directv.sdii.dto.esb.ServiceCollection();
			serviceCollectionLocal.getService();
			List<com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrderItem> workOrderItems=ibsWo.getWorkOrderItemList().getWorkOrderItem();
			for(com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrderItem woi: workOrderItems ){
				co.com.directv.sdii.dto.esb.Service serviceLocal = new co.com.directv.sdii.dto.esb.Service();
				
				serviceLocal.setServiceKey(woi.getID().toString());
				serviceLocal.setName(woi.getBusinessInteractionItemInvolvesService().getName());
				serviceLocal.setCode(woi.getBusinessInteractionItemInvolvesService().getCode());
				serviceLocal.setDescription(woi.getProblemDescription());
				serviceLocal.setProductSerialNumber(woi.getBusinessInteractionItemInvolvesResource());
				serviceLocal.setWorkOrderKey(woi.getWorkOrderID().toString());
				serviceCollectionLocal.getService().add(serviceLocal);
				
			}
			localWo.setServiceList(serviceCollectionLocal);
		}
		
		/* 1. ServiceList */
		
		/* 1. ShippingOrder */
		
		if(shippingOrder!=null){
			
			co.com.directv.sdii.dto.esb.ShippingOrder shippingOrderLocal = new co.com.directv.sdii.dto.esb.ShippingOrder();
			
			shippingOrderLocal.setShippingOrderKey(shippingOrder.getType().getId());
			shippingOrderLocal.setName(shippingOrder.getName());
			shippingOrderLocal.setCode(shippingOrder.getCode());
			shippingOrderLocal.setInstallationPrice(shippingOrder.getInstallationPrice());
			shippingOrderLocal.setActivationPrice(shippingOrder.getActivationPrice());
			shippingOrderLocal.setSoldBy(shippingOrder.getSoldBy());
			shippingOrderLocal.setShippingMethodKey(shippingOrder.getShippingMethodId());
			shippingOrderLocal.setContractNumber(shippingOrder.getContractNumber());
			shippingOrderLocal.setContractCode(shippingOrder.getContractCode());

			shippingOrderLocal.setID(shippingOrder.getID());
			shippingOrderLocal.setDescription(shippingOrder.getDescription());
			
			shippingOrderLocal.setInteractionDate(shippingOrder.getInteractionDate());
			shippingOrderLocal.setInteractionDateComplete(shippingOrder.getInteractionDateComplete());
			
			shippingOrderLocal.setInteractionStatus(shippingOrder.getInteractionStatus().getId());
			
			if(shippingOrder.getProductList()!=null){
				co.com.directv.sdii.dto.esb.ProductCollection productCollectionLocal=new co.com.directv.sdii.dto.esb.ProductCollection();
				productCollectionLocal.getProduct();
				for(com.directvla.schema.businessdomain.product.orderhandling.v1_0.Product p: shippingOrder.getProductList().getProduct()){
					co.com.directv.sdii.dto.esb.Product productLocal=new co.com.directv.sdii.dto.esb.Product();
					productLocal.setProductKey(p.getProductKey());
					productLocal.setName(p.getName());
					productLocal.setCode(p.getCode());
					productLocal.setDescription(p.getDescription());
					productLocal.setModelKey(p.getProductCategory().getId().toString());
					//productLocal.setType(p.getCode());
					if(p.getProductCharacteristicValueCollection()!=null){
						if(p.getProductCharacteristicValueCollection().getProductCharValue()!=null){
							productLocal.setType(getTypeTechnologyUpdateworkOrder(p.getProductCharacteristicValueCollection().getProductCharValue()));
						}
					}
					
					
					productCollectionLocal.getProduct().add(productLocal);
				}
				shippingOrderLocal.setProductList(productCollectionLocal);
			}
			localWo.setShippingOrder(shippingOrderLocal);
		}
		
		/* 1. WoTypeByPreviusWoTypeId */
		
		/* 1. WoTypeByWoTypeId */
		
		if(ibsWo.getType()!=null){
			localWo.setWoTypeByWoTypeId(new co.com.directv.sdii.dto.esb.WoType());
			Long idWoTypeByWoTypeId =0l;
			if(ibsWo.getType().getId()!=null){
				idWoTypeByWoTypeId = Long.parseLong(ibsWo.getType().getId());
			}
			localWo.getWoTypeByWoTypeId().setId(idWoTypeByWoTypeId);
			localWo.getWoTypeByWoTypeId().setWoTypeCode(ibsWo.getType().getCode());
			localWo.getWoTypeByWoTypeId().setWoTypeName(ibsWo.getType().getName());
		}
		
		/* 1. WoTypeByWoTypeId */
		return localWo;
	}
	
	/**
	 * Metodo: pobla un objecto de co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent con la informacion consultada por el servicios de getCustomerWorkOrderId and getShippingOrder
	 * @param countryCode
	 * @param ibsWo
	 * @param shippingOrder
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws PropertiesException 
	 */
	private co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent populatePublishWorkOrderWithInfoManageworkforceAndOrderhandling(String countryCode,
			                                                          com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder ibsWo,
			                                                          com.directvla.schema.businessdomain.common.orderhandling.v1_0.ShippingOrder shippingOrder) throws PropertiesException{
		
		
		PublishWorkOrderEvent localPublishWorkOrderEvent = new PublishWorkOrderEvent();
		
		Header localHeader = new Header();
		localPublishWorkOrderEvent.setHeader(localHeader);
		
		RequestMetadataType localRequestMetadataType = new RequestMetadataType();
		localHeader.setRequestMetadataType(localRequestMetadataType);
		
		DataArea localDataArea = new DataArea();
		localPublishWorkOrderEvent.setDataArea(localDataArea);

		WorkOrder localWorkOrder = new WorkOrder();
		localDataArea.setWorkOrder(localWorkOrder);
		
		//SystemId
		localRequestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		
		//
		//co.com.directv.sdii.dto.esb.WorkOrder localWo = new co.com.directv.sdii.dto.esb.WorkOrder();
		
		
		//1. Action
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setActionTaken(ibsWo.getActionTaken());
		//localWo.setAction(ibsWo.getActionTaken());
		
		//1. DealerId
		Long dealerId = 0l;
		if(!ibsWo.getServiceProvider().getID().equals("")){
			dealerId = Long.parseLong(ibsWo.getServiceProvider().getID());
		}
		ServiceProvider localServiceProvider = new ServiceProvider();
		localServiceProvider.setID(dealerId.toString());
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setServiceProvider(localServiceProvider);
		//localWo.setDealerId(dealerId);

		//1. DealerISalesd
		Long dealerISalesd = 0l;
		if(!ibsWo.getStockhandler().getPartyRoleId().equals("")){
			dealerISalesd = Long.parseLong(ibsWo.getStockhandler().getPartyRoleId());
		}
		//localWo.setDealerISalesd(dealerISalesd);
		
		//1. Description
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setDescription(ibsWo.getDescription());
		//localWo.setDescription(ibsWo.getDescription());
		
		//1. HistoryCodeEventId se coloca -1l para poder marcar el tipo de cambio de la work order
		CustomerHistoryEvent localCustomerHistoryEvent = new CustomerHistoryEvent();
		localPublishWorkOrderEvent.getDataArea().setHistoryEvent(localCustomerHistoryEvent);
		localPublishWorkOrderEvent.getDataArea().getHistoryEvent().setID(-1);
		//localWo.setHistoryCodeEventId(-1l);
		
		//1. ID
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setID(ibsWo.getID());
		//localWo.setID(ibsWo.getID());
		
		//1. InitiatedBy
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setInitiatedBy(ibsWo.getInitiatedBy());
		//localWo.setInitiatedBy(ibsWo.getInitiatedBy());
		//1. InteractionDate
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setInteractionDate(ibsWo.getInteractionDate());
		//localWo.setInteractionDate(ibsWo.getInteractionDate());
		//1. InteractionDateComplete
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setInteractionDateComplete(ibsWo.getInteractionDateComplete());
		//localWo.setInteractionDateComplete(ibsWo.getInteractionDateComplete());
		//1. InteractionStatus
		Category interactionStatus = new Category();
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setInteractionStatus(interactionStatus);
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().getInteractionStatus().setId(ibsWo.getInteractionStatus().getId());
		//localWo.setInteractionStatus(ibsWo.getInteractionStatus().getId());
		//1. IsAppointment
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setIsAppointment(ibsWo.getIsAppointment());
		//localWo.setIsAppointment(ibsWo.getIsAppointment());
		//1. PossibleDeliveryDate
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setPossibleDeliveryDate(ibsWo.getPossibleDeliveryDate());
		//localWo.setPossibleDeliveryDate(ibsWo.getPossibleDeliveryDate());
		//1. RequestedDeliveryDate
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setRequestedDeliveryDate(ibsWo.getRequestedDeliveryDate());
		//localWo.setRequestedDeliveryDate(ibsWo.getRequestedDeliveryDate());
		//1. WorkingTime
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setWorkingTime(ibsWo.getWorkingTime());
		//localWo.setWorkingTime(ibsWo.getWorkingTime());
		//1. WorkorderStatusByActualStatusId
		//Ya está hecho en una linea anterior.
		//localWo.setWorkorderStatusByActualStatusId(ibsWo.getInteractionStatus().getId());
		
		//1. AddressId (NUEVO)
		localPublishWorkOrderEvent.getDataArea().getWorkOrder().setAddressId(ibsWo.getAddressId());
		
		/* 1. Customer */
		if(ibsWo!=null && ibsWo.getCustomerKey()!=null){
			//co.com.directv.sdii.dto.esb.Customer customerLocal = new co.com.directv.sdii.dto.esb.Customer();
			Customer localCustomer = new Customer();
			localCustomer.setID(ibsWo.getCustomerKey());
			//customerLocal.setID(ibsWo.getCustomerKey());

			/* 2. AddressList */
			//co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection urbanPropertyAddressCollectionLocal = new co.com.directv.sdii.dto.esb.UrbanPropertyAddressCollection();
			//UrbanPropertyAddressCollection localUrbanPropertyAddressCollection = new UrbanPropertyAddressCollection();
			
			// Los Addresses, los MediaContacts y los ActivePhysicalProducts se obtendran de dos WS nuevos.
			//3. UrbanPropertyAddress
			//localUrbanPropertyAddressCollection.getUrbanPropertyAddress();
			//urbanPropertyAddressCollectionLocal.getUrbanPropertyAddress();
			/* 3. UrbanPropertyAddress */
			
			//if (ibsWo.getAddressId()!=null){
					//UrbanPropertyAddress localUrbanPropertyAddress = new UrbanPropertyAddress();
					//co.com.directv.sdii.dto.esb.UrbanPropertyAddress urbanPropertyAddressLocal = new co.com.directv.sdii.dto.esb.UrbanPropertyAddress();
					// 4. AddressCode
					//La linea de abajo es la original pero genera un NullPointer en CoreWokrOrderImporteServiceTOA#populateCustomerInformation por no tener ID.
					//localUrbanPropertyAddress.setAddressCode(ibsWo.getAddressId());
					//urbanPropertyAddressLocal.setAddressCode(ibsWo.getAddressId());
					//localUrbanPropertyAddressCollection.getUrbanPropertyAddress().add(localUrbanPropertyAddress);
					//urbanPropertyAddressCollectionLocal.getUrbanPropertyAddress().add(urbanPropertyAddressLocal);
			//}
			
			/* 3. UrbanPropertyAddress */
			//localCustomer.setAddressList(localUrbanPropertyAddressCollection);
			//customerLocal.setAddressList(urbanPropertyAddressCollectionLocal);
			localPublishWorkOrderEvent.getDataArea().setCustomer(localCustomer);
			//localWo.setCustomer(customerLocal);	
		}
		
		/* 1. Customer */
		
		/* 1. GeographicArea */
		
		localPublishWorkOrderEvent.getHeader().getRequestMetadataType().setSourceID(countryCode);
		
		//co.com.directv.sdii.dto.esb.GeographicArea geographicAreaLocal = new co.com.directv.sdii.dto.esb.GeographicArea();
			
	    //co.com.directv.sdii.dto.esb.Country countryLocal = new co.com.directv.sdii.dto.esb.Country();
		//countryLocal.setIso2Code(countryCode);
		//geographicAreaLocal.setCountry(countryLocal);
		//localWo.setGeographicArea(geographicAreaLocal);
		
		/* 1. GeographicArea */
		
		/* 1. ServiceList */
		if(ibsWo.getWorkOrderItemList()!=null){
			WorkOrderItemCollection localWorkOrderItemCollection = new WorkOrderItemCollection();
			//co.com.directv.sdii.dto.esb.ServiceCollection serviceCollectionLocal=new co.com.directv.sdii.dto.esb.ServiceCollection();
			localWorkOrderItemCollection.getWorkOrderItem();
			//serviceCollectionLocal.getService();
			
			List<com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrderItem> workOrderItems=ibsWo.getWorkOrderItemList().getWorkOrderItem();
			for(com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrderItem woi: workOrderItems ){
				WorkOrderItem localWorkOrderItem = new WorkOrderItem();
				//co.com.directv.sdii.dto.esb.Service serviceLocal = new co.com.directv.sdii.dto.esb.Service();
				
				localWorkOrderItem.setID(woi.getID());
				//serviceLocal.setServiceKey(woi.getID().toString());
				Category category = new Category();
				localWorkOrderItem.setBusinessInteractionItemInvolvesService(category);
				localWorkOrderItem.getBusinessInteractionItemInvolvesService().setName(woi.getBusinessInteractionItemInvolvesService().getName());
				//serviceLocal.setName(woi.getBusinessInteractionItemInvolvesService().getName());
				localWorkOrderItem.getBusinessInteractionItemInvolvesService().setCode(woi.getBusinessInteractionItemInvolvesService().getCode());
				//serviceLocal.setCode(woi.getBusinessInteractionItemInvolvesService().getCode());
				localWorkOrderItem.setProblemDescription(woi.getProblemDescription());
				//serviceLocal.setDescription(woi.getProblemDescription());
				localWorkOrderItem.setBusinessInteractionItemInvolvesResource(woi.getBusinessInteractionItemInvolvesResource());
				//serviceLocal.setProductSerialNumber(woi.getBusinessInteractionItemInvolvesResource());
				localWorkOrderItem.setWorkOrderID(woi.getWorkOrderID());
				//serviceLocal.setWorkOrderKey(woi.getWorkOrderID().toString());
				
				localWorkOrderItemCollection.getWorkOrderItem().add(localWorkOrderItem);
				//serviceCollectionLocal.getService().add(serviceLocal);
				
			}
			localPublishWorkOrderEvent.getDataArea().getWorkOrder().setWorkOrderItemList(localWorkOrderItemCollection);
			//localWo.setServiceList(serviceCollectionLocal);
		}
		
		/* 1. ServiceList */
		
		/* 1. ShippingOrder */
		
		if(shippingOrder!=null){
			ShippingOrder localShippingOrder = new ShippingOrder();
			//co.com.directv.sdii.dto.esb.ShippingOrder shippingOrderLocal = new co.com.directv.sdii.dto.esb.ShippingOrder();
			
			BusinessInteractionType localBusinessInteractionType = new BusinessInteractionType();
			localShippingOrder.setType(localBusinessInteractionType);
			localShippingOrder.getType().setId(shippingOrder.getType().getId());
			//shippingOrderLocal.setShippingOrderKey(shippingOrder.getType().getId());
			localShippingOrder.setName(shippingOrder.getName());
			//shippingOrderLocal.setName(shippingOrder.getName());
			localShippingOrder.setCode(shippingOrder.getCode());
			//shippingOrderLocal.setCode(shippingOrder.getCode());
			//shippingOrderLocal.setInstallationPrice(shippingOrder.getInstallationPrice());
			//shippingOrderLocal.setActivationPrice(shippingOrder.getActivationPrice());
			localShippingOrder.setInitiatedBy(shippingOrder.getSoldBy());
			//shippingOrderLocal.setSoldBy(shippingOrder.getSoldBy());
			localShippingOrder.setShippingMethodId(shippingOrder.getShippingMethodId());
			//shippingOrderLocal.setShippingMethodKey(shippingOrder.getShippingMethodId());
			localShippingOrder.setContractNumber(shippingOrder.getContractNumber());
			//shippingOrderLocal.setContractNumber(shippingOrder.getContractNumber());
			localShippingOrder.setContractCode(shippingOrder.getContractCode());
			//shippingOrderLocal.setContractCode(shippingOrder.getContractCode());

			localShippingOrder.setID(shippingOrder.getID());
			//shippingOrderLocal.setID(shippingOrder.getID());
			localShippingOrder.setDescription(shippingOrder.getDescription());
			//shippingOrderLocal.setDescription(shippingOrder.getDescription());
			
			localShippingOrder.setInteractionDate(shippingOrder.getInteractionDate());
			//shippingOrderLocal.setInteractionDate(shippingOrder.getInteractionDate());
			localShippingOrder.setInteractionDateComplete(shippingOrder.getInteractionDateComplete());
			//shippingOrderLocal.setInteractionDateComplete(shippingOrder.getInteractionDateComplete());
			
			Category category = new Category();
			localShippingOrder.setInteractionStatus(category);
			localShippingOrder.getInteractionStatus().setId(shippingOrder.getInteractionStatus().getId());
			//shippingOrderLocal.setInteractionStatus(shippingOrder.getInteractionStatus().getId());
			
			if(shippingOrder.getProductList()!=null){
				ProductCollection localProductCollection = new ProductCollection();
				//co.com.directv.sdii.dto.esb.ProductCollection productCollectionLocal=new co.com.directv.sdii.dto.esb.ProductCollection();
				localProductCollection.getProduct();
				//productCollectionLocal.getProduct();
				
				for(com.directvla.schema.businessdomain.product.orderhandling.v1_0.Product p: shippingOrder.getProductList().getProduct()){
					Product localProduct = new Product();
					//co.com.directv.sdii.dto.esb.Product productLocal=new co.com.directv.sdii.dto.esb.Product();
					PhysicalResourceCollection localPhysicalResourceCollection = new PhysicalResourceCollection();
					localPhysicalResourceCollection.getPhysicalResources();
					localProduct.setProductHasPhysicalResources(localPhysicalResourceCollection);
					PhysicalResource localPhysicalResource = new PhysicalResource();
					localProduct.getProductHasPhysicalResources().getPhysicalResources().add(localPhysicalResource);
					localPhysicalResource.setSerialNumber(p.getProductKey());
					//productLocal.setProductKey(p.getProductKey());
					//productLocal.setName(p.getName());
					//productLocal.setCode(p.getCode());
					//productLocal.setDescription(p.getDescription());
					ProductCategory localProductCategory = new ProductCategory();
					localProduct.setProductCategory(localProductCategory);
					localProduct.getProductCategory().setId(p.getProductCategory().getId());
					//productLocal.setModelKey(p.getProductCategory().getId().toString());
					////productLocal.setType(p.getCode());
					if(p.getProductCharacteristicValueCollection()!=null){
						if(p.getProductCharacteristicValueCollection().getProductCharValue()!=null){
							ProductCharacteristicValueCollection localProductCharacteristicValueCollection = new ProductCharacteristicValueCollection();
							localProduct.setProductCharacteristicValueCollection(localProductCharacteristicValueCollection);
							co.com.directv.sdii.dto.esb.event.publishworkorderevent.ProductCharacteristicValue localProductCharacteristicValue = new co.com.directv.sdii.dto.esb.event.publishworkorderevent.ProductCharacteristicValue();
							localProductCharacteristicValue.setValue(getTypeTechnologyUpdateworkOrder(p.getProductCharacteristicValueCollection().getProductCharValue()));
							localProduct.getProductCharacteristicValueCollection().getProductCharValue().add(localProductCharacteristicValue);
							//productLocal.setType(getTypeTechnologyUpdateworkOrder(p.getProductCharacteristicValueCollection().getProductCharValue()));
						}
					}
					
					localProductCollection.getProduct().add(localProduct);
					//productCollectionLocal.getProduct().add(productLocal);
				}
				localShippingOrder.setProductList(localProductCollection);
				//shippingOrderLocal.setProductList(productCollectionLocal);
			}
			localPublishWorkOrderEvent.getDataArea().setShippingOrder(localShippingOrder);
			//localWo.setShippingOrder(shippingOrderLocal);
		}
		
		/* 1. WoTypeByPreviusWoTypeId */
		
		/* 1. WoTypeByWoTypeId */
		
		/*if(ibsWo.getType()!=null){
			localWo.setWoTypeByWoTypeId(new co.com.directv.sdii.dto.esb.WoType());
			Long idWoTypeByWoTypeId =0l;
			if(ibsWo.getType().getId()!=null){
				idWoTypeByWoTypeId = Long.parseLong(ibsWo.getType().getId());
			}
			localWo.getWoTypeByWoTypeId().setId(idWoTypeByWoTypeId);
			localWo.getWoTypeByWoTypeId().setWoTypeCode(ibsWo.getType().getCode());
			localWo.getWoTypeByWoTypeId().setWoTypeName(ibsWo.getType().getName());
		}*/
		
		/* 1. WoTypeByWoTypeId */
		
		//return localWo;
		return localPublishWorkOrderEvent;
	}
	
	/**
	 * Metodo: valida la informacion requerida u obligatoria.
	 * @param countryCode
	 * @param woCode
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private void validParamUpdateInfoWorkOrderIBSToHSP(String countryCode,String woCode) throws BusinessException{
		
    	if(countryCode==null || countryCode.trim().equals("")){
			throw new BusinessException(ErrorBusinessMessages.COUNTRY_REQUIRED.getCode(), ErrorBusinessMessages.COUNTRY_REQUIRED.getMessage());
		}
			
    	if(woCode==null || woCode.trim().equals("")){
			throw new BusinessException(ErrorBusinessMessages.WORK_ORDER_REQUIRED.getCode(), ErrorBusinessMessages.WORK_ORDER_REQUIRED.getMessage());
		}
		
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOImporterLocal#downloadWorkOrderContact(java.lang.Long, java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void downloadWorkOrderContact(Long countryId, Date processDate) throws BusinessException{
		log.debug("== Inicia downloadCustomerContact/CoreWOImporter ==");
		try {
			
			CustomerInquiriesByCriteriaIBSDTO request = business.populateCustomerInquiriesByCriteriaIBSDTO(countryId, processDate);
			business.downloadWorkOrderContact(request);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOImporter/downloadCustomerContact");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina downloadCustomerContact/CoreWOImporter ==");
		}
	}
	
	private String getTypeTechnologyUpdateworkOrder(List<ProductCharacteristicValue> productCharacteristicValues) throws PropertiesException{

		for (ProductCharacteristicValue productCharacteristicValue : productCharacteristicValues) {
			
			if(productCharacteristicValue.getProductSpecCharacteristic() !=null){
				if(productCharacteristicValue.getProductSpecCharacteristic().getID() !=null){
					if(productCharacteristicValue.getProductSpecCharacteristic().getID().equals(CodesBusinessEntityEnum.UPDATE_WORK_ORDER_TYPE_PRODUCT_TECHNOLOGY.getCodeEntity())){
						return productCharacteristicValue.getValue();
					}
				}
			}
		}
		
		return "";
	}

	
}
