/**
 * Creado 3/06/2010 16:19:21
 */
package co.com.directv.sdii.ejb.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessDetailException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.IbsMediaContactType;
import co.com.directv.sdii.model.pojo.MediaContactType;
import co.com.directv.sdii.persistence.dao.dealers.MediaContactTypesDAOLocal;

import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.ContactMedium;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.ContactMediumCollection;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.EmailContact;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.FaxNumber;
import com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.TelephoneNumber;


/**
 * Encapsula operaciones comunes que se pueden realizar con los objetos de respuesta de un servicio web de ibs
 * 
 * Fecha de Creación: 3/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
public class IBSWSBase extends BusinessBase {

	private final static Logger log = UtilsBusiness.getLog4J(IBSWSBase.class);
	
	protected static final int EMAIL_OPTION = 1;
	protected static final int FAX_OPTION = 2;
	protected static final int TELEPHONE_HOME_OPTION = 3;
	protected static final int MOBILE_OPTION = 4;
	protected static final int TELEPHONE_WORK_OPTION = 5;
	
	private String homeCode = "notAssigned";
	
	private String workCode = "notAssigned";
	
	private String mobileCode = "notAssigned";
	
	@EJB(name="MediaContactTypesDAOLocal", beanInterface=MediaContactTypesDAOLocal.class)
	private MediaContactTypesDAOLocal mediaContactDAO;
	
	
	/**
	 * Obtiene un arreglo de String con los medios de contacto del dealer dependiendo
	 * de las llaves especificadas por las constantes definidas.
	 * 	
	 * @param contactMediumList lista de medios de contacto en el Wrapper de IBS
	 * @return Arreglo con los valores obtenidos de la lista de medios de contacto
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 * @author jalopez
	 */
	protected String[] getArrayDealerCellPhones(ContactMediumCollection contactMediumList, String mediaContactCode) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.ContactMedium> contactList = contactMediumList.getContactMedium();
		
		if(contactList == null){
			return new String[0];
		}
		
		for (com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.ContactMedium contactMedium : contactList) {
			
			if( contactMedium instanceof com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.FaxNumber ){
				
				com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.FaxNumber telephoneNumber = ((com.directvla.schema.businessdomain.common.sprmsupportandreadiness.v1_0.FaxNumber)contactMedium);
					
					IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, telephoneNumber.getType() );	
					
					if( ibsMediaContactType != null && telephoneNumber.getNumber()!=null && !telephoneNumber.getNumber().trim().equals("")){
						result.add( telephoneNumber.getNumber() );
					}else{
						tiposIbs.append(telephoneNumber.getType());
						tiposIbs.append(" ");					
					}
					
			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray( resultStr );
	}
	
	/**
	 * Obtiene un arreglo de String con los medios de contacto del dealer dependiendo
	 * de las llaves especificadas por las constantes definidas.
	 * 	
	 * @param contactMediumList lista de medios de contacto en el Wrapper de IBS
	 * @param arrayResultType Código de tipo de medio de contacto requerido:
	 * EMAIL_OPTION
	 * @return Arreglo con los valores obtenidos de la lista de medios de contacto
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 * @author jalopez
	 */
	protected String[] getArrayDealerEmails(ContactMediumCollection contactMediumList,  int arrayResultType) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		
		List<String> result = new ArrayList<String>();
		List<ContactMedium> contactList = contactMediumList.getContactMedium();
		if(contactList == null){
			return new String[0];
		}
		
		for (ContactMedium contactMedium : contactList) {
			if(contactMedium instanceof EmailContact && arrayResultType == EMAIL_OPTION){				
				result.add(((EmailContact)contactMedium).getEMailAddress().equalsIgnoreCase("") ? " ":((EmailContact)contactMedium).getEMailAddress()); 
			}
		}
		String[] resultStr = new String[result.size()];
		return result.toArray(resultStr);
	}
	
	/**
	 * Obtiene un arreglo de String con los medios de contacto del dealer dependiendo
	 * de las llaves especificadas por las constantes definidas.
	 * 	
	 * @param contactMediumList lista de medios de contacto en el Wrapper de IBS
	 * @return Arreglo con los valores obtenidos de la lista de medios de contacto
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 * @author jalopez
	 */
	protected String[] getArrayDealerTelephones(ContactMediumCollection contactMediumList, String mediaContactCode) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		
		List<String> result = new ArrayList<String>();
		StringBuilder tiposIbs = new StringBuilder();
		List<ContactMedium> contactList = contactMediumList.getContactMedium();
		if(contactList == null){
			return new String[0];
		}

		for (ContactMedium contactMedium : contactList) {
			
			 if(contactMedium instanceof TelephoneNumber){
				
				TelephoneNumber tel = (TelephoneNumber)contactMedium;

					IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, tel.getType() );	
					
					if( ibsMediaContactType != null && tel.getNumber()!=null && !tel.getNumber().trim().equalsIgnoreCase("")){
						result.add( tel.getNumber() );
					}else{
						tiposIbs.append(tel.getType());
						tiposIbs.append(" ");					
					}
			}
		}
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray(resultStr);
	}
	
	/**
	 * Obtiene un arreglo de String con los medios de contacto del dealer dependiendo
	 * de las llaves especificadas por las constantes definidas.
	 * 	
	 * @param contactMediumList lista de medios de contacto en el Wrapper de IBS
	 * @return Arreglo con los valores obtenidos de la lista de medios de contacto
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 */
	protected String[] getArrayDealerFaxes(ContactMediumCollection contactMediumList, String mediaContactCode) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<ContactMedium> contactList = contactMediumList.getContactMedium();
		if(contactList == null){
			return new String[0];
		}

		for (ContactMedium contactMedium : contactList) {
			if( contactMedium instanceof FaxNumber ){
				FaxNumber fax = ((FaxNumber)contactMedium);
				
					IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, fax.getType() );	
					
					if( ibsMediaContactType != null && fax.getNumber()!=null && !fax.getNumber().trim().equalsIgnoreCase("")){
						
						result.add( fax.getNumber() );
					}else{
						tiposIbs.append(fax.getType());
						tiposIbs.append(" ");					
					}
				
			}
		}
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray(resultStr);
	}
	
	/**
	 * Obtiene un arreglo de String con los medios de contacto del dealer dependiendo
	 * de las llaves especificadas por las constantes definidas.
	 * 	
	 * @param contactMediumList lista de medios de contacto en el Wrapper de IBS
	 * @param arrayResultType Código de tipo de medio de contacto requerido:
	 * EMAIL_OPTION
	 * FAX_OPTION
	 * TELEPHONE_OPTION
	 * MOBILE_OPTION
	 * @return Arreglo con los valores obtenidos de la lista de medios de contacto
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 */
	protected String[] getArrayFromContactMediumListV11(com.directvla.schema.businessdomain.common.v1_0.ContactMediumCollection contactMediumList, int arrayResultType, Long countryId) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		
		List<String> result = new ArrayList<String>();
		List<com.directvla.schema.businessdomain.common.v1_0.ContactMedium> contactList = contactMediumList.getContactMedium();
		if(contactList == null){
			return new String[0];
		}
		for (com.directvla.schema.businessdomain.common.v1_0.ContactMedium contactMedium : contactList) {
			if(contactMedium instanceof com.directvla.schema.businessdomain.common.v1_0.EmailContact && arrayResultType == EMAIL_OPTION){
				
				result.add(((com.directvla.schema.businessdomain.common.v1_0.EmailContact)contactMedium).getEMailAddress()); 
			}else if(contactMedium instanceof com.directvla.schema.businessdomain.common.v1_0.FaxNumber && arrayResultType == FAX_OPTION){
				
				result.add(((com.directvla.schema.businessdomain.common.v1_0.FaxNumber)contactMedium).getNumber());
			}
		}
		String[] resultStr = new String[result.size()];
		return result.toArray(resultStr);
	}
	
	/**
	 * Obtiene un arreglo de String con los medios de contacto del dealer dependiendo
	 * de las llaves especificadas por las constantes definidas.
	 * 	
	 * @param contactMediumList lista de medios de contacto en el Wrapper de IBS
	 * @param String mediaContactCode, codigo del medio de Contacto en HSP+
	 * @return Arreglo con los valores obtenidos de la lista de medios de contacto
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 * @author jalopez
	 */
	protected String[] getArrayTelephoneNumberFromContactMediumListV11(com.directvla.schema.businessdomain.common.v1_0.ContactMediumCollection contactMediumList, String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<com.directvla.schema.businessdomain.common.v1_0.ContactMedium> contactList = contactMediumList.getContactMedium();
		if(contactList == null){
			return new String[0];
		}
		
		for (com.directvla.schema.businessdomain.common.v1_0.ContactMedium contactMedium : contactList) {
			
			 if(contactMedium instanceof com.directvla.schema.businessdomain.common.v1_0.TelephoneNumber){	
				 
				com.directvla.schema.businessdomain.common.v1_0.TelephoneNumber tel = (com.directvla.schema.businessdomain.common.v1_0.TelephoneNumber)contactMedium;
				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, tel.getType() );	
				
				if( ibsMediaContactType != null ){
					result.add( tel.getNumber() );
				}else{
					tiposIbs.append(tel.getType());
					tiposIbs.append(" ");					
				}				
			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray(resultStr);
	}
	
	/**
	 * Metodo que arma un arreglo de String con los valores de los medios de contacto traidos de ESB que segun la parametrizacion en HSP+ coincidan con un codigo de medio de contacto
	 * @param contactMediumList medios de contacto consultados de vista360 en esb
	 * @param mediaContactCode codigo del medio de contacto en HSP+ con el que se desea comparar los traidos de ESB
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 */
	protected String[] getArrayTelephoneNumberFromContactMediumListV1_1(com.directvla.schema.crm.common.vista360.v1.ContactMediumCollection contactMediumList, String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<com.directvla.schema.crm.common.vista360.v1.ContactMedium> contactList = contactMediumList.getContactMediumItem();
		if(contactList == null){
			return new String[0];
		}
		
		for (com.directvla.schema.crm.common.vista360.v1.ContactMedium contactMedium : contactList) {
			
			 if(contactMedium instanceof com.directvla.schema.crm.common.vista360.v1.TelephoneNumber){	
				 
				com.directvla.schema.crm.common.vista360.v1.TelephoneNumber tel = (com.directvla.schema.crm.common.vista360.v1.TelephoneNumber)contactMedium;
				
					IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, tel.getType() );	
					
					if( ibsMediaContactType != null ){
						result.add( tel.getNumber() );
					}else{
						tiposIbs.append(tel.getType());
						tiposIbs.append(" ");					
					}

			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray(resultStr);
	}
	
	/**
	 * Obtiene un arreglo de String con los medios de contacto del dealer dependiendo
	 * de las llaves especificadas por las constantes definidas.
	 * 	
	 * @param contactMediumList lista de medios de contacto en el Wrapper de IBS
	 * @param String mediaContactCode, codigo del medio de Contacto en HSP+
	 * @return Arreglo con los valores obtenidos de la lista de medios de contacto
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 * @author jalopez
	 */
	protected String[] getArrayFaxesFromContactMediumListV11(com.directvla.schema.businessdomain.common.v1_0.ContactMediumCollection contactMediumList,  String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<com.directvla.schema.businessdomain.common.v1_0.ContactMedium> contactList = contactMediumList.getContactMedium();
		
		if(contactList == null){
			return new String[0];
		}
		
		for (com.directvla.schema.businessdomain.common.v1_0.ContactMedium contactMedium : contactList) {
			
			if( contactMedium instanceof com.directvla.schema.businessdomain.common.v1_0.FaxNumber ){
				com.directvla.schema.businessdomain.common.v1_0.FaxNumber fax = ((com.directvla.schema.businessdomain.common.v1_0.FaxNumber)contactMedium);
				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, fax.getType() );	
				
				if( ibsMediaContactType != null ){
					result.add( fax.getNumber() );
				}else{
					tiposIbs.append(fax.getType());
					tiposIbs.append(" ");					
				}
			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray( resultStr );
	}
	
	/**
	 * Obtiene un arreglo de String con los medios de contacto del dealer dependiendo
	 * de las llaves especificadas por las constantes definidas.
	 * 	
	 * @param contactMediumList lista de medios de contacto en el Wrapper de IBS
	 * @param String mediaContactCode, codigo del medio de Contacto en HSP+
	 * @return Arreglo con los valores obtenidos de la lista de medios de contacto
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 * @author jalopez
	 */
	protected String[] getArrayMobileFromContactMediumListV11(com.directvla.schema.businessdomain.common.v1_0.ContactMediumCollection contactMediumList,  String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<com.directvla.schema.businessdomain.common.v1_0.ContactMedium> contactList = contactMediumList.getContactMedium();
		
		if(contactList == null){
			return new String[0];
		}
		
		for (com.directvla.schema.businessdomain.common.v1_0.ContactMedium contactMedium : contactList) {
			
			if( contactMedium instanceof com.directvla.schema.businessdomain.common.v1_0.FaxNumber ){
				com.directvla.schema.businessdomain.common.v1_0.FaxNumber fax = ((com.directvla.schema.businessdomain.common.v1_0.FaxNumber)contactMedium);
				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, fax.getType() );	
				
				if( ibsMediaContactType != null ){
					result.add( fax.getNumber() );
				}else{
					tiposIbs.append(fax.getType());
					tiposIbs.append(" ");					
				}
			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray( resultStr );
	}

	/**
	 * Metodo que arma un arreglo de String con los valores de los medios de contacto traidos de ESB que segun la parametrizacion en HSP+ coincidan con un codigo de medio de contacto
	 * @param contactMediumList medios de contacto consultados de vista360 en esb
	 * @param mediaContactCode codigo del medio de contacto en HSP+ con el que se desea comparar los traidos de ESB
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 */
	protected String[] getArrayMobileFromContactMediumListV1_1(com.directvla.schema.crm.common.vista360.v1.ContactMediumCollection contactMediumList,  String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<com.directvla.schema.crm.common.vista360.v1.ContactMedium> contactList = contactMediumList.getContactMediumItem();
		
	
		for (com.directvla.schema.crm.common.vista360.v1.ContactMedium contactMedium : contactList) {
			
			if( contactMedium instanceof com.directvla.schema.crm.common.vista360.v1.TelephoneNumber ){
				
				com.directvla.schema.crm.common.vista360.v1.TelephoneNumber telephoneNumber = ((com.directvla.schema.crm.common.vista360.v1.TelephoneNumber)contactMedium);

					
					IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, telephoneNumber.getType() );	
					
					if( ibsMediaContactType != null ){
						result.add( telephoneNumber.getNumber() );
					}else{
						tiposIbs.append(telephoneNumber.getType());
						tiposIbs.append(" ");					
					}
					
			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray( resultStr );
	}
	
	protected MediaContactType buidMediaContactType(Long mediaContactTypeId, String mediaContactTypeName, String mediaContactTypeCode){
    	MediaContactType mediaContactType = new MediaContactType();
        mediaContactType.setId(mediaContactTypeId);
        mediaContactType.setMediaName(mediaContactTypeName);
        mediaContactType.setMediaCode(mediaContactTypeCode);
        return mediaContactType;
    }
	
	public String getHomeCode() {
		return homeCode;
	}

	public void setHomeCode(String homeCode) {
		this.homeCode = homeCode;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	
//	public String[] getArrayMobileFromContactMediumListV1_1(com.directvla.schema.crm.common.v1.ContactMediumCollection contactMediumList,
//                                                            String mediaContactCode) throws DAOServiceException,DAOSQLException {
//		
//		StringBuilder tiposIbs = new StringBuilder();
//		List<String> result = new ArrayList<String>();
//		List<com.directvla.schema.crm.common.v1.ContactMedium> contactList = contactMediumList.getContactMediumItem();
//
//		if (contactList == null) {
//			return new String[0];
//		}
//
//		for (com.directvla.schema.crm.common.v1.ContactMedium contactMedium : contactList) {
//
//			if (contactMedium instanceof com.directvla.schema.crm.common.v1.FaxNumber) {
//				com.directvla.schema.crm.common.v1.FaxNumber fax = ((com.directvla.schema.crm.common.v1.FaxNumber) contactMedium);
//				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, fax.getType());
//
//				if (ibsMediaContactType != null) {
//					result.add(fax.getNumber());
//				} else {
//					tiposIbs.append(fax.getType());
//					tiposIbs.append(" ");
//				}
//			}
//		}
//
//		if (result.isEmpty())
//			log.warn("No se encontro en la base de datos la relacion del media contact type: " + mediaContactCode + " con tipo de contacto de ibs: " + tiposIbs.toString());
//
//		String[] resultStr = new String[result.size()];
//		return result.toArray(resultStr);
//	}

	public String[] getArrayFromContactMediumListV1_1(com.directvla.schema.crm.common.vista360.v1.ContactMediumCollection contactMediumList, int arrayResultType) {
		List<String> result = new ArrayList<String>();
		List<com.directvla.schema.crm.common.vista360.v1.ContactMedium> contactList = contactMediumList.getContactMediumItem();
		if(contactList == null){
			return new String[0];
		}
		for (com.directvla.schema.crm.common.vista360.v1.ContactMedium contactMedium : contactList) {
			if(contactMedium instanceof com.directvla.schema.crm.common.vista360.v1.EmailContact && arrayResultType == EMAIL_OPTION){
				
				result.add(((com.directvla.schema.crm.common.vista360.v1.EmailContact)contactMedium).getEMailAddress()); 
			}else if(contactMedium instanceof com.directvla.schema.crm.common.vista360.v1.FaxNumber && arrayResultType == FAX_OPTION){
				
				result.add(((com.directvla.schema.crm.common.vista360.v1.FaxNumber)contactMedium).getNumber());
			}
		}
		String[] resultStr = new String[result.size()];
		return result.toArray(resultStr);
	}
	
	/**
	 * Metodo que arma un arreglo de String con los valores de los medios de contacto traidos de ESB que segun la parametrizacion en HSP+ coincidan con un codigo de medio de contacto
	 * @param contactMediumList medios de contacto consultados de vista360 en esb
	 * @param mediaContactCode codigo del medio de contacto en HSP+ con el que se desea comparar los traidos de ESB
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 */
	protected String[] getArrayFaxesFromContactMediumListV1_1(com.directvla.schema.crm.common.vista360.v1.ContactMediumCollection contactMediumList,  String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		
		StringBuilder tiposIbs = new StringBuilder();
		List<String> result = new ArrayList<String>();
		List<com.directvla.schema.crm.common.vista360.v1.ContactMedium> contactList = contactMediumList.getContactMediumItem();
		
		for (com.directvla.schema.crm.common.vista360.v1.ContactMedium contactMedium : contactList) {
			
			if( contactMedium instanceof com.directvla.schema.crm.common.vista360.v1.FaxNumber ){
				com.directvla.schema.crm.common.vista360.v1.FaxNumber fax = ((com.directvla.schema.crm.common.vista360.v1.FaxNumber)contactMedium);
				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, fax.getType() );	
				
				if( ibsMediaContactType != null ){
					result.add( fax.getNumber() );
				}else{
					tiposIbs.append(fax.getType());
					tiposIbs.append(" ");					
				}
			}
			
			if( contactMedium instanceof com.directvla.schema.crm.common.vista360.v1.TelephoneNumber ){
				com.directvla.schema.crm.common.vista360.v1.TelephoneNumber fax = ((com.directvla.schema.crm.common.vista360.v1.TelephoneNumber)contactMedium);

					IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, fax.getType() );	
					
					if( ibsMediaContactType != null ){
						result.add( fax.getNumber() );
					}else{
						tiposIbs.append(fax.getType());
						tiposIbs.append(" ");					
					}

			}
		}
		
		if( result.isEmpty() )
			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
		
		String[] resultStr = new String[result.size()];
		return result.toArray( resultStr );
	}

//	public String[] getArrayTelephoneNumberFromContactMediumListV1_1(com.directvla.schema.crm.common.v1.ContactMediumCollection contactMediumList, String mediaContactCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
//		
//		StringBuilder tiposIbs = new StringBuilder();
//		List<String> result = new ArrayList<String>();
//		List<com.directvla.schema.crm.common.v1.ContactMedium> contactList = contactMediumList.getContactMediumItem();
//		if(contactList == null){
//			return new String[0];
//		}
//		
//		for (com.directvla.schema.crm.common.v1.ContactMedium contactMedium : contactList) {
//			
//			 if(contactMedium instanceof com.directvla.schema.crm.common.v1.TelephoneNumber){	
//				 
//				com.directvla.schema.crm.common.v1.TelephoneNumber tel = (com.directvla.schema.crm.common.v1.TelephoneNumber)contactMedium;
//				IbsMediaContactType ibsMediaContactType = mediaContactDAO.getMediaContactTypeByIbsCode(mediaContactCode, tel.getType() );	
//				
//				if( ibsMediaContactType != null ){
//					result.add( tel.getNumber() );
//				}else{
//					tiposIbs.append(tel.getType());
//					tiposIbs.append(" ");					
//				}				
//			}
//		}
//		
//		if( result.isEmpty() )
//			log.warn("No se encontro en la base de datos la relacion del media contact type: "+mediaContactCode+" con tipo de contacto de ibs: "+tiposIbs.toString());
//		
//		String[] resultStr = new String[result.size()];
//		return result.toArray(resultStr);
//	}


/**
	 * Metodo: Valida un objeto determinando si es nulo, en caso que sea nulo lanza
	 * una excepción y escribe en el log el atributo que fué validado
	 * @param parameterName Nombre del atributo a ser validado o mensaje para
	 * ser escrito en el log en caso que el objeto sea nulo
	 * @param value2Validate objeto a ser validado
	 * @param errorCode código de error a ser lanzado
	 * @param errorMessage mensaje de error a ser lanzado
	 * @author jjimenezh
     * @throws BusinessDetailException 
	 */
	protected void validateResult(String parameterName, Object value2Validate, String errorCode, String errorMessage, String service) throws BusinessDetailException{	
		List<String> params = getParametersWS( getErrorParameters(parameterName, service) );
		try {	
			UtilsBusiness.assertNotNull(value2Validate, errorCode, errorMessage);
		} catch (BusinessException e) {
			log.error("== Error de validación de parámetros de respuesta de un WS de ibs: el parámetro: \""+parameterName+"\" llegó nulo ==");
			throw new BusinessDetailException(e.getMessageCode(),e.getMessage(),params);			
		}
	}
	
	/**
	 * Retorna los parametros que se formatean en el mensaje
	 * de error.
	 * @param parameterName
	 * @return Object[]
	 */
	protected Object[] getErrorParameters(String parameterName, String service){
		Object[] errorParameters = new Object[2];
		errorParameters[0]= parameterName;
		errorParameters[1]= service;
		return errorParameters;
	}
	
	/**
	 * Retorna los parametros que se formatean en el mensaje
	 * de error para el servicio web.
	 * @param errorParameters
	 * @return List<String>
	 */
	protected List<String> getParametersWS(Object[] errorParameters){
		List<String> params = new ArrayList<String>();
		for (int i = 0; i < errorParameters.length; i++) {
			params.add((String) errorParameters[i]);
		}
		return params;
	}

}
