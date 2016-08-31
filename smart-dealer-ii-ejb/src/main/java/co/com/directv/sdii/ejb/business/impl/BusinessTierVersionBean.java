package co.com.directv.sdii.ejb.business.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.Constantes;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.BusinessTierVersionBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.WoInfoEsbParentDate;
import co.com.directv.sdii.model.pojo.WoInfoEsbService;
import co.com.directv.sdii.model.vo.BusinessTierVersionVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoInfoEsbServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * 
 * Implementa las operaciones de negocio que retorna 
 * 
 * Fecha de Creación: 23/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name = "BusinessTierVersionLocal", mappedName = "ejb/BusinessTierVersionLocal")
public class BusinessTierVersionBean extends BusinessBase implements BusinessTierVersionBeanLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(BusinessTierVersionBean.class);
	
	@EJB(name="WoInfoEsbServiceDAOLocal", beanInterface=WoInfoEsbServiceDAOLocal.class)
	private WoInfoEsbServiceDAOLocal woInfoEsbServiceDAOLocal;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
	@Override
	public BusinessTierVersionVO getBusinessVersion() throws BusinessException {
		 log.debug("== Inicia getBusinessVersion/BusinessTierVersionBean ==");
		try{
			BusinessTierVersionVO tierVersionVO = new BusinessTierVersionVO();
			StringBuffer str = new StringBuffer(PropertiesReader.getInstance().getAppKey(Constantes.VERSION_BUSINESS_TIER));
			Properties properties = new Properties();
			properties.load( getClass().getResourceAsStream( str.toString()) );
			tierVersionVO.setVersionBusiness( properties.get(Constantes.LABEL_VERSION).toString() );
			tierVersionVO.setVersionDate( properties.get(Constantes.LABEL_VERSION_DATE).toString() );
			
			return tierVersionVO;
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getBusinessVersion/BusinessTierVersionBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getBusinessVersion/BusinessTierVersionBean ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void test6008(String woCode) throws BusinessException {
		
		try{
			
			WoInfoEsbService wies = new WoInfoEsbService();
			
			long countryId = 1;
			Date dateNow=UtilsBusiness.getDateLastChangeOfUser(new Long(countryId), userDao, systemParameterDAO);
			Date dateNowWithoutTime = UtilsBusiness.getFirstMomentOfDay(dateNow);
			
			WoInfoEsbParentDate woInfoEsbParentDate = woInfoEsbServiceDAOLocal.getDateForTheCountry(dateNowWithoutTime, countryId);
			if(woInfoEsbParentDate == null){
				woInfoEsbParentDate = new WoInfoEsbParentDate(null,new Country(countryId),new Timestamp(dateNowWithoutTime.getTime()));
				try{
					woInfoEsbServiceDAOLocal.createWoInfoEsbParentDate(woInfoEsbParentDate);
				}catch(DAOSQLException daose ){
					woInfoEsbParentDate = woInfoEsbServiceDAOLocal.getDateForTheCountry(dateNowWithoutTime, countryId);
				}
			}
			wies.setWoInfoEsbParentDate(woInfoEsbParentDate);
			
			wies.setWoInfoEsbType(woInfoEsbServiceDAOLocal.getWoInfoEsbTypeByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity()));
			
			Timestamp ibsCreationDate = new Timestamp(System.currentTimeMillis()); //hora de ahora
			wies.setIbsCreationDate(ibsCreationDate);
			wies.setCreationDate(ibsCreationDate);
			wies.setTryNumbers(0L);
			wies.setWoCode(woCode);
			
			//Inicio PR6008
			Date lastWOEventDate = woInfoEsbServiceDAOLocal.getLastWOEventDate(woCode);
			if(lastWOEventDate == null || wies.getIbsCreationDate().after(lastWOEventDate) ){
				wies.setStateWoInfo(woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_PENDING.getCodeEntity()));
			}else{
				wies.setStateWoInfo(woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity()));
			}
			//Fin PR6008
			
			woInfoEsbServiceDAOLocal.createWoInfoEsbService(wies, null);
			if(wies.getStateWoInfo().getCode().equals(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity())){
				String description = "La fecha del nuevo evento recibido es anterior a la fecha del ultimo evento recibido.";
	        	woInfoEsbServiceDAOLocal.createWoInfoEsbServiceLog(wies.getId(), ibsCreationDate, description, null);
			}
			
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación createWoInfoEsbServiceForCore/WoInfoEsbServiceBusinessBean ==");
	    	throw this.manageException(ex);
		}finally {
			log.debug("== Termina createWoInfoEsbServiceForCore/StateMachineWOBusiness ==");
		}
		
	}

}
