package co.com.directv.sdii.persistence.dao.config.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.MessageCoreAllocatorDTO;
import co.com.directv.sdii.model.dto.WoInfoEsbServiceDTO;
import co.com.directv.sdii.model.dto.WoInfoEsbServiceReportDTO;
import co.com.directv.sdii.model.pojo.WoInfoEsbNotificationType;
import co.com.directv.sdii.model.pojo.WoInfoEsbParentDate;
import co.com.directv.sdii.model.pojo.WoInfoEsbService;
import co.com.directv.sdii.model.pojo.WoInfoEsbServiceLog;
import co.com.directv.sdii.model.pojo.WoInfoEsbState;
import co.com.directv.sdii.model.pojo.WoInfoEsbType;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoInfoEsbServiceDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * Session Bean implementation class WoInfoEsbServiceDAO
 * 
 * @author aharker
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoInfoEsbServiceDAO extends BaseDao implements WoInfoEsbServiceDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(WoInfoEsbServiceDAO.class);
	
	@EJB(name="WoInfoEsbServiceDAOLocal", beanInterface=WoInfoEsbServiceDAOLocal.class)
	private WoInfoEsbServiceDAOLocal woInfoEsbServiceDAOLocal;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
    public WoInfoEsbServiceDAO() {
    }

	/**
	 * Metodo encargado de la creacion de un evento en la tabla de procesamiento en paralelo de core y asignador
	 * @param woInfoEsbService Informacion completa del evento de core o asignador
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public void createWoInfoEsbService(WoInfoEsbService woInfoEsbService)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createWoInfoEsbService/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            if(woInfoEsbService!=null){
            	session.persist(woInfoEsbService);
            }
        }  catch (Throwable ex) {
            log.error("== Error createWoInfoEsbService ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina createWoInfoEsbService/WoInfoEsbServiceDAO ==");
        }
	}

	/**
	 * Crea el log para un evento de procesamiento en paralelo de core y asignador
	 * @param woInfoEsbServiceId id del evento al que se le generara el log
	 * @param timeNow fecha y hora actual, con la cual se generara el log
	 * @param description mensaje que lleva el log
	 * @param codeTypeNotification tipo de notificacion en caso de ser necesaria la generacion del reporte de errores
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public void createWoInfoEsbServiceLog(Long woInfoEsbServiceId, Date timeNow, String description, String codeTypeNotification)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio createWoInfoEsbServiceLog/WoInfoEsbServiceDAO == id event "+woInfoEsbServiceId);
		Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            WoInfoEsbService woInfoEsbService= findWoInfoEsbServiceById(woInfoEsbServiceId);
            WoInfoEsbServiceLog woInfoEsbServiceLogNew = new WoInfoEsbServiceLog();
            woInfoEsbServiceLogNew.setDescription(description);
            woInfoEsbServiceLogNew.setLogDate(new Timestamp(timeNow.getTime()));
            woInfoEsbServiceLogNew.setNotify(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
            woInfoEsbServiceLogNew.setTryNumber(woInfoEsbService.getTryNumbers()+1L);
            if(codeTypeNotification!=null){
            	woInfoEsbServiceLogNew.setWoInfoEsbNotificationType(getWoInfoEsbNotificationTypeByCode(codeTypeNotification));
            }	
            woInfoEsbServiceLogNew.setWoInfoEsbService(woInfoEsbService);
            session.persist(woInfoEsbServiceLogNew);
        }  catch (Throwable ex) {
            log.error("== Error en createWoInfoEsbServiceLog ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina createWoInfoEsbServiceLog/WoInfoEsbServiceDAO == id event "+woInfoEsbServiceId);
        }
	}

	/**
	 * Extrae un tipo de notificacion de la base de datos dado su codigo
	 * @param codeTypeNotification codigo del tipo de notificacion
	 * @return el tipo de notificacion dado el codigo
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public WoInfoEsbNotificationType getWoInfoEsbNotificationTypeByCode(String codeTypeNotification) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoInfoEsbNotificationTypeByCode/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append(" select wient from "+WoInfoEsbNotificationType.class.getName()+" wient where wient.code = :code ");
            Query q = session.createQuery(strignQuery.toString());
            q.setString("code", codeTypeNotification);
            WoInfoEsbNotificationType returnValue = (WoInfoEsbNotificationType)q.uniqueResult();
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en la consulta de WoInfoEsbNotificationType por code ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWoInfoEsbNotificationTypeByCode/WoInfoEsbServiceDAO ==");
        }
	}
	
	/**
	 * Metodo encargado de encontrar un estado de eventos de procesamiento en paralelo de core y asignador dado un codigo de estado
	 * @param codeState codigo del estado que se desea buscar
	 * @return estado encontrado con el codigo suministrado
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public WoInfoEsbState getWoInfoEsbStateByCode(String codeState) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoInfoEsbStateByCode/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append(" select wies from "+WoInfoEsbState.class.getName()+" wies where wies.code = :codeState ");
            Query q = session.createQuery(strignQuery.toString());
            q.setString("codeState", codeState);
            WoInfoEsbState returnValue = (WoInfoEsbState)q.uniqueResult();
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en getWoInfoEsbStateByCode/WoInfoEsbServiceDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWoInfoEsbStateByCode/WoInfoEsbServiceDAO ==");
        }
	}
	
	/**
	 * Elimina un evento de procesamiento en paralelo de core y asignador
	 * @param id id del evento que se desea eliminar
	 * @return si se logro o no eliminar el evento
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public Boolean deleteWoInfoEsbService(Long id) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deleteWoInfoEsbService/WoInfoEsbServiceDAO ==");
		Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            WoInfoEsbService woInfoEsbService= findWoInfoEsbServiceById(id);
            session.delete(woInfoEsbService);
            return true;
        }  catch (Throwable ex) {
            log.error("== Error en deleteWoInfoEsbService ==");
            return false;
        }  finally {
            log.debug("== Termina deleteWoInfoEsbService/WoInfoEsbServiceDAO ==");
        }
	}

	/**
	 * Busca procesos bloqueados dentro de la tabla de eventos de procesamiento en paralelo de core y asignador
	 * @param countryId pais en el cual se desean buscar los eventos bloqueados
	 * @param dateNow fecha y hora actual para comparar el tiempo que llevan bloqueados los eventos
	 * @return lista de eventos bloqueados mas tiempo de lo que permiten los parametros del sistema
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @author Aharker
	 */	
	@SuppressWarnings("unchecked")
	public List<WoInfoEsbServiceDTO> searchBlockInfoEsbService(Long countryId, Date dateNow) throws DAOSQLException, DAOServiceException{
        log.debug("== Inicio searchBlockInfoEsbService/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            
            strignQuery.append(" select new "+WoInfoEsbServiceDTO.class.getName()+"(wies.woCode, wies.woInfoEsbType.code, wies.id, ");
            strignQuery.append(" wies.woInfoEsbParentDate.country.countryCode, wies.woInfoEsbParentDate.country.id) from "+WoInfoEsbService.class.getName()+" ");
            strignQuery.append(" wies where wies.woInfoEsbParentDate.country.id = :countryId and wies.woInfoEsbParentDate.dateInfo > :firstDate and ");
            strignQuery.append(" wies.lastDateProccess < :oldDate and wies.stateWoInfo.code = :stateCode order by wies.id desc ");
            
        	Calendar dateCompare=Calendar.getInstance();
        	dateCompare.setTimeInMillis(dateNow.getTime());
        	Integer timeWait = Integer.parseInt(CodesBusinessEntityEnum.SDII_CODE_MAX_TIME_TO_REPROCCESS_CORE_ALLOCATOR.getCodeEntity());
        	dateCompare.add(Calendar.MINUTE, (-1*timeWait));

            Query q = session.createQuery(strignQuery.toString());
            q.setParameter("oldDate", new Timestamp(dateCompare.getTimeInMillis()), Hibernate.TIMESTAMP);
            q.setParameter("stateCode", CodesBusinessEntityEnum.SDII_WO_INFO_ESB_STARTED.getCodeEntity(), Hibernate.STRING);
            
            Calendar firstDate=Calendar.getInstance();
            firstDate.setTimeInMillis(dateNow.getTime());
            Integer timeDays = Integer.parseInt(this.systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SDII_PAST_DAYS_FROM_PARALLEL_CORE_ALLOCATOR_PROCCES.getCodeEntity(), countryId).getValue());
            firstDate.add(Calendar.DAY_OF_MONTH, (-1*timeDays));
            
            q.setParameter("countryId", countryId, Hibernate.LONG);
            q.setParameter("firstDate", new Timestamp(firstDate.getTimeInMillis()), Hibernate.TIMESTAMP);
            
        	List<WoInfoEsbServiceDTO> returnValue = (List<WoInfoEsbServiceDTO>)q.list();
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en searchBlockInfoEsbService ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina searchBlockInfoEsbService/WoInfoEsbServiceDAO ==");
        }
	} 
	
	/**
	 * consulta las work orders que se deben enviar al procesamiento en paralelo tanto de core como de asignador y crea las DTO que se deben enviar a
	 * las respectivas colas de mensajes
	 * @param stateCodes codigos de estado en los cuales es valida la consulta de los eventos de core y asignador (ej pendiente, pendiente por reprocesar)
	 * @param limit limite maximo de eventos que se extraeran
	 * @param countryId pais del cual se desean consultar los eventos
	 * @param typeCode tipo de evento que se desea consultar (core, asignador)
	 * @param stateCodeNoQuery estados de work orders de los cuales debe ignorar para no cruzar los distintos prcesadores (ej iniciado)
	 * @param reProccesing si es una consulta de reprocesamiento o no
	 * @param dateNow fecha y hora actual para la comparacion de reprocesamiento
	 * @return lista de mensajes que se deben enviar a las respectivas colas de mensajes
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 * @throws PropertiesException 
	 */
	@Override
	public List<MessageCoreAllocatorDTO> findAllWoInfoEsbServiceMessageByParameters(List<String> stateCodes, Long limit, Long countryId, String typeCode
			, List<String> stateCodeNoQueryList, boolean reProccesing, Date dateNow, String... stateCodeNoQueryListUnic ) throws DAOServiceException, DAOSQLException, PropertiesException {
        /*log.debug("== Inicio findAllWoInfoEsbServiceByParameters/WoInfoEsbServiceDAO ==");
        if( typeCode.equalsIgnoreCase(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity()) ){
        	return findAllWoInfoEsbServiceMessageByParametersForCore(stateCodes, limit, countryId, typeCode, stateCodeNoQueryList, reProccesing, dateNow, stateCodeNoQueryListUnic);
        }else if(typeCode.equalsIgnoreCase(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity())){
        	return findAllWoInfoEsbServiceMessageByParametersForAllocator(stateCodes, limit, countryId, typeCode, stateCodeNoQueryList, reProccesing, dateNow);
        }
        else{
        	return null;
        }*/
		return null;
	}

	/**
	 * consulta las work orders que se deben enviar al procesamiento en paralelo para core y crea las DTO que se deben enviar a
	 * las respectivas colas de mensajes
	 * @param stateCodes codigos de estado en los cuales es valida la consulta de los eventos de core y asignador (ej pendiente, pendiente por reprocesar)
	 * @param limit limite maximo de eventos que se extraeran
	 * @param countryId pais del cual se desean consultar los eventos
	 * @param typeCode tipo de evento que se desea consultar (core, asignador)
	 * @param stateCodeNoQuery estados de work orders de los cuales debe ignorar para no cruzar los distintos prcesadores (ej iniciado)
	 * @param reProccesing si es una consulta de reprocesamiento o no
	 * @param dateNow fecha y hora actual para la comparacion de reprocesamiento
	 * @param delayTimeMs es el tiempo en milisegundos que se esperara para que un evento sea procesado en el orden correcto.
	 * @return lista de mensajes que se deben enviar a las respectivas colas de mensajes
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 * @throws PropertiesException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MessageCoreAllocatorDTO> findAllWoInfoEsbServiceMessageByParameters(List<Long> stateIds, Long limit, Long countryId
			, List<Long> stateIdNoQueryList, boolean reProccesing, Date dateNow, String typeCode, Long delayTimeMs) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio findAllWoInfoEsbServiceMessageByParametersForCore/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            
            String stateCode = "";
            int tamCodes=stateIds.size();
            for(int i=0; i<tamCodes; ++i){
            	stateCode+=stateIds.get(i);
            	if(i<(tamCodes-1)){
            		stateCode+=",";
            	}
            }
            
            String stateCodeNoQuery="";
            tamCodes=stateIdNoQueryList.size();
            for(int i=0; i<tamCodes; ++i){
            	stateCodeNoQuery+=stateIdNoQueryList.get(i);
            	if(i<(tamCodes-1)){
            		stateCodeNoQuery+=",";
            	}
            }
            
            strignQuery.append(" SELECT PPEF.ID id, PPEF.WO_CODE woCode, PPT.CODE processCode ");
            strignQuery.append(" , PARENT_DATES.COUNTRY_ID countryId, CO.COUNTRY_CODE countryCode "); 
            strignQuery.append(" FROM PARALLEL_PROCESS_EVENTS PPEF ");
            strignQuery.append(" INNER JOIN PARALLEL_PROCESS_PARENT_DATES PARENT_DATES ON PARENT_DATES.ID = PPEF.PARALLEL_PROCESS_PARENT_DAT_ID ");
            strignQuery.append(" INNER JOIN PARALLEL_PROCESS_STATUS PPS ON PPS.ID = PPEF.PARALLEL_PROCESS_STATUS_ID ");            
            strignQuery.append(" INNER JOIN ( ");
            //strignQuery.append(" SELECT MIN(PPE.ID) ID_PPE, PPE.CUSTOMER_CODE ");
            //strignQuery.append(" FROM PARALLEL_PROCESS_EVENTS PPE INNER JOIN PARALLEL_PROCESS_PARENT_DATES PARENT_DATES_INT ON PARENT_DATES_INT.ID = PPE.PARALLEL_PROCESS_PARENT_DAT_ID ");
            //strignQuery.append(" WHERE PPE.PARALLEL_PROCESS_STATUS_ID  IN("+stateCode+") and TRUNC(PARENT_DATES_INT.DATE_INFO) > (:dateFirst) ");
            strignQuery.append(" 	SELECT MIN(PPE.ID) ID_PPE, PPE_MIN.NUM_DATE, PPE_MIN.CUSTOMER_CODE ");
            strignQuery.append(" 	FROM( ");
            strignQuery.append(" 		SELECT MIN(PPE_INT.NUM_IBS_CREATION_DATE) NUM_DATE, PPE_INT.CUSTOMER_CODE ");
            strignQuery.append(" 		FROM PARALLEL_PROCESS_EVENTS PPE_INT ");
            strignQuery.append(" 		INNER JOIN PARALLEL_PROCESS_PARENT_DATES PARENT_DATES_INT ON (PARENT_DATES_INT.ID = PPE_INT.PARALLEL_PROCESS_PARENT_DAT_ID) ");
            strignQuery.append(" 		WHERE PPE_INT.PARALLEL_PROCESS_STATUS_ID IN ("+stateCode+") AND TRUNC(PARENT_DATES_INT.DATE_INFO) > (:dateFirst) ");
            strignQuery.append(" 		AND PPE_INT.IBS_CREATION_DATE < (:delayTimeMs) ");
            if(reProccesing){
            	strignQuery.append("	AND PPE_INT.LAST_DATE_PROCCESS < :dateCompare ");
            }
            strignQuery.append(" 		GROUP BY PPE_INT.CUSTOMER_CODE ) PPE_MIN ");
            strignQuery.append(" 		INNER JOIN PARALLEL_PROCESS_EVENTS PPE ON (PPE.CUSTOMER_CODE = PPE_MIN.CUSTOMER_CODE AND (PPE.NUM_IBS_CREATION_DATE = PPE_MIN.NUM_DATE) AND PPE.PARALLEL_PROCESS_STATUS_ID IN ("+stateCode+") ) ");
            strignQuery.append("GROUP BY PPE_MIN.CUSTOMER_CODE , PPE_MIN.NUM_DATE");
            strignQuery.append(" ) PPE_EXT ON PPE_EXT.ID_PPE = PPEF.ID ");
            //strignQuery.append(" GROUP BY PPE.CUSTOMER_CODE) T1 ON T1.ID_PPE = PPEF.ID ");
            strignQuery.append(" INNER JOIN PARALLEL_PROCESS_TYPES PPT ON (PPT.ID = PPEF.PARALLEL_PROCESS_TYPE_ID) ");
            strignQuery.append(" INNER JOIN COUNTRIES CO ON (CO.ID = PARENT_DATES.COUNTRY_ID ) ");
            strignQuery.append(" WHERE TRUNC(PARENT_DATES.DATE_INFO) > (:dateFirst) ");
            strignQuery.append(" AND PPEF.PARALLEL_PROCESS_STATUS_ID NOT IN ("+stateCodeNoQuery+") ");
            strignQuery.append(" AND PARENT_DATES.COUNTRY_ID = :countryId ");
            if(reProccesing){
            	strignQuery.append(" AND PPT.CODE = :typeCode ");
            }
            Calendar dateCompare=Calendar.getInstance();
        	dateCompare.setTimeInMillis(dateNow.getTime());

            if(reProccesing){
            	Integer timeWait = Integer.parseInt(this.systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SDII_CODE_TIME_TO_PROCCESS_CORE_ALLOCATOR.getCodeEntity()+typeCode, countryId).getValue());
            	dateCompare.add(Calendar.SECOND, (-1*timeWait));
            }
            
            Query q = session.createSQLQuery(strignQuery.toString())
            .addScalar("woCode")
            .addScalar("processCode")
            .addScalar("countryId", Hibernate.LONG)
            .addScalar("countryCode")
            .addScalar("id", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(MessageCoreAllocatorDTO.class))
            ;
            
            Calendar firstDate=Calendar.getInstance();
            firstDate.setTimeInMillis(dateNow.getTime());
            Integer timeDays = Integer.parseInt(this.systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SDII_PAST_DAYS_FROM_PARALLEL_CORE_ALLOCATOR_PROCCES.getCodeEntity(), countryId).getValue());
            firstDate.add(Calendar.DAY_OF_MONTH, (-1*timeDays));
            
            Calendar delayDate = Calendar.getInstance();
            delayDate.setTimeInMillis(dateNow.getTime());
            delayDate.add(Calendar.MILLISECOND, -1*delayTimeMs.intValue());
            
            q.setParameter("countryId", countryId, Hibernate.LONG);
            q.setParameter("dateFirst", new Timestamp(firstDate.getTimeInMillis()), Hibernate.TIMESTAMP);
            q.setParameter("delayTimeMs", new Timestamp(delayDate.getTimeInMillis()), Hibernate.TIMESTAMP);
            if(reProccesing){
            	q.setParameter("typeCode", typeCode, Hibernate.STRING);
            	q.setParameter("dateCompare", new Timestamp(dateCompare.getTimeInMillis()), Hibernate.TIMESTAMP);
            }
            
        	if( limit != null ){
				q.setFirstResult( 0 );
				q.setMaxResults( limit.intValue() );
        	}
        	List<MessageCoreAllocatorDTO> returnValue = (List<MessageCoreAllocatorDTO>)q.list();
        	log.info("== Se encontraron "+returnValue.size()+" eventos para procesar en el proceso paralelo de core");
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en findAllWoInfoEsbServiceMessageByParametersForCore ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina findAllWoInfoEsbServiceMessageByParametersForCore/WoInfoEsbServiceDAO ==");
        }
	}

		/**
	 * consulta las work orders que se deben enviar al procesamiento en paralelo para asignador y crea las DTO que se deben enviar a
	 * las respectivas colas de mensajes
	 * @param stateCodes codigos de estado en los cuales es valida la consulta de los eventos de core y asignador (ej pendiente, pendiente por reprocesar)
	 * @param limit limite maximo de eventos que se extraeran
	 * @param countryId pais del cual se desean consultar los eventos
	 * @param typeCode tipo de evento que se desea consultar (core, asignador)
	 * @param stateCodeNoQuery estados de work orders de los cuales debe ignorar para no cruzar los distintos prcesadores (ej iniciado)
	 * @param reProccesing si es una consulta de reprocesamiento o no
	 * @param dateNow fecha y hora actual para la comparacion de reprocesamiento
	 * @return lista de mensajes que se deben enviar a las respectivas colas de mensajes
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 * @throws PropertiesException 
	 */
	@SuppressWarnings({ "unchecked"})
	private List<MessageCoreAllocatorDTO> findAllWoInfoEsbServiceMessageByParametersForAllocator(List<String> stateCodes, Long limit, Long countryId, String typeCode
			, List<String> stateCodeNoQueryList, boolean reProccesing, Date dateNow, String ... stateNoQueryTotal) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio findAllWoInfoEsbServiceMessageByParametersForAllocator/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            
            String stateCode = "";
            int tamCodes=stateCodes.size();
            for(int i=0; i<tamCodes; ++i){
            	stateCode+="'"+stateCodes.get(i)+"'";
            	if(i<(tamCodes-1)){
            		stateCode+=",";
            	}
            }
            
            String stateCodeNoQuery="";
            tamCodes=stateCodeNoQueryList.size();
            for(int i=0; i<tamCodes; ++i){
            	stateCodeNoQuery+="'"+stateCodeNoQueryList.get(i)+"'";
            	if(i<(tamCodes-1)){
            		stateCodeNoQuery+=",";
            	}
            }
            
            strignQuery.append(" select new "+MessageCoreAllocatorDTO.class.getName()+"( ");
            strignQuery.append("        wies.woCode, ");
            strignQuery.append("        wies.woInfoEsbType.code, ");
            strignQuery.append("        wies.id, ");
            strignQuery.append("        wies.woInfoEsbParentDate.country.countryCode, ");
            strignQuery.append("        wies.woInfoEsbParentDate.country.id) ");
            strignQuery.append("   from "+WoInfoEsbService.class.getName() + " wies ");
            strignQuery.append("  where (wies.stateWoInfo.code in ("+stateCode+") ) ");
            strignQuery.append("        and (wies.woInfoEsbType.code = :typeCode ");
            strignQuery.append("             or :typeCode is null) ");
            strignQuery.append("        and (wies.woInfoEsbParentDate.country.id = :countryId ");
            strignQuery.append("             or :countryId is null) ");
            strignQuery.append("        and ( NOT EXISTS ( select 1 ");
            strignQuery.append("                             from "+WoInfoEsbService.class.getName()+" wies2 ");
            strignQuery.append("                            where wies2.stateWoInfo.code in ( "+stateCodeNoQuery+" ) ");
            strignQuery.append("                                  and wies2.customerCode=wies.customerCode and wies2.id<wies.id ) ) ");
            strignQuery.append("        and ( wies.id = ( select min (wies2.id) ");
            strignQuery.append("                            from "+WoInfoEsbService.class.getName()+" wies2 ");
            strignQuery.append(" 	                       where (wies2.customerCode=wies.customerCode) ");
            strignQuery.append("                                 and (wies2.stateWoInfo.code in ("+stateCode+") ) ");
            strignQuery.append("                                 and (wies2.woInfoEsbType.code = :typeCode ");
            strignQuery.append("                                      or :typeCode is null) ");
            strignQuery.append("                                 and (wies2.woInfoEsbParentDate.country.id = :countryId ");
            strignQuery.append("                                      or :countryId is null) ");
            strignQuery.append("        						and ( NOT EXISTS ( select 1 ");
            strignQuery.append("                             from "+WoInfoEsbService.class.getName()+" wies3 ");
            strignQuery.append("                            where wies3.stateWoInfo.code in ( "+stateCodeNoQuery+" ) ");
            strignQuery.append("                                  and wies3.customerCode=wies.customerCode and wies3.id<wies.id ) ) ) ) ");

            Calendar dateCompare=Calendar.getInstance();
        	dateCompare.setTimeInMillis(dateNow.getTime());
        	Integer timeWait = Integer.parseInt(this.systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SDII_CODE_TIME_TO_PROCCESS_CORE_ALLOCATOR.getCodeEntity()+typeCode, countryId).getValue());
        	dateCompare.add(Calendar.SECOND, (-1*timeWait));
            if(reProccesing){
            	strignQuery.append(" and ( ( select max (wiesLog.logDate) ");
            	strignQuery.append("           from "+WoInfoEsbServiceLog.class.getName()+" wiesLog ");
            	strignQuery.append("          where wiesLog.woInfoEsbService.id = wies.id ) < :timeWait ) ");
            }
            
            strignQuery.append(" and ( TRUNC(wies.woInfoEsbParentDate.dateInfo) >=  :firstDate  ");
            strignQuery.append("       AND TRUNC(wies.woInfoEsbParentDate.dateInfo) <=  :secondDate  ) ");
           
            strignQuery.append(" order by wies.id desc ");

            Query q = session.createQuery(strignQuery.toString());
            
            Calendar firstDate=Calendar.getInstance();
            firstDate.setTimeInMillis(dateNow.getTime());
            Integer timeDays = Integer.parseInt(this.systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SDII_PAST_DAYS_FROM_PARALLEL_CORE_ALLOCATOR_PROCCES.getCodeEntity(), countryId).getValue());
            firstDate.add(Calendar.DAY_OF_MONTH, (-1*timeDays));
            Calendar secondDate=Calendar.getInstance();
            secondDate.setTimeInMillis(dateNow.getTime());
            secondDate.add(Calendar.DAY_OF_MONTH, 1);
            
            q.setParameter("typeCode", typeCode, Hibernate.STRING);
            q.setParameter("countryId", countryId, Hibernate.LONG);
            q.setParameter("firstDate", new Timestamp(firstDate.getTimeInMillis()), Hibernate.TIMESTAMP);
            q.setParameter("secondDate", new Timestamp(secondDate.getTimeInMillis()), Hibernate.TIMESTAMP);
            if(reProccesing){
            	q.setParameter("timeWait", new Timestamp(dateCompare.getTimeInMillis()), Hibernate.TIMESTAMP);
            }
        	if( limit != null ){	  		
				q.setFirstResult( 1 );
				q.setMaxResults( limit.intValue() );
        	}
        	List<MessageCoreAllocatorDTO> returnValue = (List<MessageCoreAllocatorDTO>)q.list();
        	log.info("== Se encontraron "+returnValue.size()+" eventos para procesar en el proceso paralelo de asignador");
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en findAllWoInfoEsbServiceMessageByParametersForAllocator ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina findAllWoInfoEsbServiceMessageByParametersForAllocator/WoInfoEsbServiceDAO ==");
        }
	}
	
	/**
	 * Busca un evento en base de datos del procesamiento en paralelo de core y asignador
	 * @param id id del evento
	 * @return evento del procesamiento en paralelo
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public WoInfoEsbService findWoInfoEsbServiceById(Long id)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio findWoInfoEsbServiceById/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append(" select wies from "+WoInfoEsbService.class.getName()+" wies ");
            strignQuery.append(" where wies.id = :id ");
            strignQuery.append(" order by wies.id desc ");
            Query q = session.createQuery(strignQuery.toString());
            q.setParameter("id", id, Hibernate.LONG);
            WoInfoEsbService returnValue = (WoInfoEsbService)q.uniqueResult();
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en findWoInfoEsbServiceById ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina findWoInfoEsbServiceById/WoInfoEsbServiceDAO ==");
        }
	}

	
	/**
	 * Actualiza un evento de procesamiento en paralelo con la informacion traida en woInfoEsbService
	 * @param woInfoEsbService informacion que quedara en base de datos.
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public void updateWoInfoEsbService(WoInfoEsbService woInfoEsbService)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio updateWoInfoEsbService/WoInfoEsbServiceDAO ==");
		Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(woInfoEsbService);
        }  catch (Throwable ex) {
            log.error("== Error en updateWoInfoEsbService ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateWoInfoEsbService/WoInfoEsbServiceDAO ==");
        }		
	}

	/**
	 * Actualiza un evento de procesamiento en paralelo de core y asignador
	 * @param woInfoEsbServiceId id del evento que se desea actualizar
	 * @param stateCode nuevo estado que tendra el evento
	 * @param tryNum numero de intento que se realizo
	 * @param dateNow fecha y hora actual con la que quedara la actualizacion
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public void updateWoInfoEsbServiceSatate(Long woInfoEsbServiceId, String stateCode, boolean tryNum, Date dateNow)throws DAOServiceException, DAOSQLException{
		try{
			log.debug("== Inicia updateWoInfoEsbServiceSatate/WoInfoEsbServiceDAO == woInfoEsbServiceId="+woInfoEsbServiceId);
			WoInfoEsbService woInfoEsbService = findWoInfoEsbServiceById(woInfoEsbServiceId);
			if(stateCode!=null && !stateCode.trim().equalsIgnoreCase("")){
				woInfoEsbService.setStateWoInfo(woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(stateCode));
				woInfoEsbService.setLastDateProccess(new Timestamp(dateNow.getTime()));
			}
			if(tryNum){
				woInfoEsbService.setTryNumbers(woInfoEsbService.getTryNumbers()+1L);
				if(stateCode.equalsIgnoreCase(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity()) && 
						woInfoEsbService.getTryNumbers() < Long.parseLong(systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.
						SDII_CODE_MAX_TRY_PROCCESS_CORE_ALLOCATOR.getCodeEntity()+woInfoEsbService.getWoInfoEsbType().getCode(), woInfoEsbService.
						getWoInfoEsbParentDate().getCountry().getId()).getValue())){
					woInfoEsbService.setStateWoInfo(woInfoEsbServiceDAOLocal.getWoInfoEsbStateByCode(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_PENDING_REPROCESSING.getCodeEntity()));
				}
			}
			updateWoInfoEsbService(woInfoEsbService);
        } catch (Throwable ex) {
            log.error("== Error en updateWoInfoEsbServiceSatate/WoInfoEsbServiceDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoInfoEsbServiceSatate/WoInfoEsbServiceDAO == woInfoEsbServiceId = "+woInfoEsbServiceId);
        }

	}
	
	
	public void updateWoInfoEsbServiceStateInBlock(List<Long> idsRecord, Long idState)throws DAOServiceException, DAOSQLException{
		try{
			Session session = ConnectionFactory.getSession();
			if(idsRecord.isEmpty()){
				return;
			}
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            
            String whereids="";
            int idsRecordSize=idsRecord.size();
            
            for(int i=0; i<idsRecordSize; ++i){
            	if(i!=0){
            		whereids+=" or ";
            	}
            	whereids+=" ID = "+idsRecord.get(i);
            }
            
            stringQuery.append(" UPDATE PARALLEL_PROCESS_EVENTS SET PARALLEL_PROCESS_STATUS_ID = "+idState+" WHERE "+whereids);
            Query query = session.createSQLQuery(stringQuery.toString());
            query.executeUpdate();
        } catch (Throwable ex) {
            log.error("== Error en updateWoInfoEsbServiceStateInBlock/WoInfoEsbServiceDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoInfoEsbServiceStateInBlock/WoInfoEsbServiceDAO == ");
        }

	}
	
	/**
	 * Metodo encargado de buscar la fecha padre dado un pais y una fecha y hora actual
	 * @param actualDate fecha y hora actual
	 * @param countryId id del pais del que se desea la consulta
	 * @return fecha padre dado un pais y una fecha
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public WoInfoEsbParentDate getDateForTheCountry(Date actualDate, Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDateForTheCountry/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append(" select wiepd from "+WoInfoEsbParentDate.class.getName()+" wiepd ");
            strignQuery.append(" where wiepd.country.id = :countryId AND TRUNC(:actualDate) = TRUNC(wiepd.dateInfo) ORDER BY wiepd.id desc ");
            Query q = session.createQuery(strignQuery.toString());
            q.setParameter("countryId", countryId, Hibernate.LONG);
            q.setParameter("actualDate", new Timestamp(actualDate.getTime()), Hibernate.TIMESTAMP);
            WoInfoEsbParentDate returnValue = (WoInfoEsbParentDate)q.uniqueResult();
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en getDateForTheCountry/WoInfoEsbServiceDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDateForTheCountry/WoInfoEsbServiceDAO ==");
        }
	}

	/**
	 * Metodo encargado de buscar un tipo de evento de procesamiento en paralelo de core y asignador dado un codigo de tipo de evento
	 * @param code codigo de tipo de evento
	 * @return tipo de evento encontrado con el codigo suministrado
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public WoInfoEsbType getWoInfoEsbTypeByCode(String code)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoInfoEsbTypeByCode/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append(" select wiet from "+WoInfoEsbType.class.getName()+" wiet ");
            strignQuery.append(" where wiet.code = :code ");
            Query q = session.createQuery(strignQuery.toString());
            q.setParameter("code", code, Hibernate.STRING);
            WoInfoEsbType returnValue = (WoInfoEsbType)q.uniqueResult();
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en getWoInfoEsbTypeByCode/WoInfoEsbServiceDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWoInfoEsbTypeByCode/WoInfoEsbServiceDAO ==");
        }
	}
	
	/**
	 * Metodo encargado de la creacion de un evento en la tabla de procesamiento en paralelo de core y asignador
	 * @param woInfoEsbService Informacion completa del evento de core o asignador
	 * @param responseXML XML que se desea guardar en Base de Datos
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWoInfoEsbService(WoInfoEsbService woInfoEsbService,
			byte[] responseXML) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createWoInfoEsbService/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            woInfoEsbService.setResponseXml(Hibernate.createBlob(responseXML, session));
            if(woInfoEsbService!=null){
            	if(woInfoEsbService!=null && woInfoEsbService.getWoInfoEsbParentDate()!=null && woInfoEsbService.getWoInfoEsbParentDate().getId()==null){
            		session.persist(woInfoEsbService.getWoInfoEsbParentDate());
            	}
            	session.persist(woInfoEsbService);
            }
        }  catch (Throwable ex) {
            log.error("== Error createWoInfoEsbService ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina createWoInfoEsbService/WoInfoEsbServiceDAO ==");
        }		
	}
	
	/**
	 * busca los log de eventos que necesiten generar reporte
	 * @param countryId id del pais del cual se desea generar el reporte
	 * @param typeReport tipo de reporte, si es de core o asignador
	 * @return lista de intentos y errores del proceso de core o asignador segun corresponda
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @author Aharker
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoInfoEsbServiceReportDTO> searchInfoReport(Long countryId,
			String typeReport) throws DAOSQLException, DAOServiceException {
        log.debug("== Inicio searchInfoReport/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append(" select new "+WoInfoEsbServiceReportDTO.class.getName()+" ( w.woInfoEsbService.woCode, ");
            strignQuery.append(" w.woInfoEsbService.customerCode, w.description, w.logDate, w.tryNumber, w.id ");
            strignQuery.append(" ) from "+WoInfoEsbServiceLog.class.getName()+" w where w.woInfoEsbNotificationType.code = :typeReport ");
            strignQuery.append(" and w.notify = :notify and w.woInfoEsbService.woInfoEsbParentDate.country.id = :countryId order by w.id desc");
            Query q = session.createQuery(strignQuery.toString());
            q.setParameter("typeReport", typeReport, Hibernate.STRING);
            q.setParameter("notify", CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity(), Hibernate.STRING);
            q.setParameter("countryId", countryId, Hibernate.LONG);
            List<WoInfoEsbServiceReportDTO> returnValue = (List<WoInfoEsbServiceReportDTO>)q.list();
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en searchInfoReport/WoInfoEsbServiceDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina searchInfoReport/WoInfoEsbServiceDAO ==");
        }
	}

	/**
	 * Actualiza los logs suministrados para que no se envien mas reportes con ellos
	 * @param woInfoEsbServiceReportDTOs logs que deben actualizarse
	 * @param notify el valor que se debe colocar en el campo de notificacion de los logs
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public void updateStateProcesbyReport(List<WoInfoEsbServiceReportDTO> woInfoEsbServiceReportDTOs, String notify) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio updateStateProcesbyReport/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append(" UPDATE PARALLEL_PROCESS_EVENT_LOGS SET notify = '"+notify+"' WHERE 1<>1 ");
            
            for(WoInfoEsbServiceReportDTO woInfoEsbServiceReportDTO: woInfoEsbServiceReportDTOs ){
            	strignQuery.append(" or ID = "+woInfoEsbServiceReportDTO.getId()+" ");
            }
            
            Query q = session.createSQLQuery(strignQuery.toString());
            q.executeUpdate();
        } catch (Throwable ex) {
            log.error("== Error en updateStateProcesbyReport/WoInfoEsbServiceDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateStateProcesbyReport/WoInfoEsbServiceDAO ==");
        }
	}
	
	/**
	 * Metodo enfocado a consultar que codigo de work orders de un conjunto especifico esta pendiente por el proceso en paralelo para un tipo de proceso dado 
	 * @param workOrders consjunto de work orders que se desea validar
	 * @param status estados de las work order que se necesitan validar
	 * @param process oproceso para el cual se validara
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPendingProccessForCore(List<WorkOrderVO> workOrders, List<String> status, String process) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getPendingProccessForCore/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            if(workOrders==null || workOrders.isEmpty() || status==null || status.isEmpty()){
            	return new ArrayList<String>();
            }
            String StatusIn=" '' ";
            for(String s:status){
            	StatusIn+=" , '"+s+"' ";
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append(" select distinct ppe.woCode ");
            strignQuery.append("   from "+WoInfoEsbService.class.getName()+" ppe ");
            strignQuery.append("  where ( ppe.woInfoEsbType.code = '"+process+"' ) ");
            strignQuery.append("          and ppe.stateWoInfo.code in ( "+StatusIn+" ) ");
            String workOrdersQuery="";
            String strTempOr="";
            for(WorkOrderVO wovo: workOrders){
            	workOrdersQuery+= strTempOr + " ppe.woCode = '"+wovo.getWoCode()+"' ";
            	strTempOr = " or ";
            }
            if(!workOrdersQuery.trim().equals("")){
            	workOrdersQuery= " ( " + workOrdersQuery + " ) ";
        	}
            strignQuery.append(" and " + workOrdersQuery);
            Query q = session.createQuery(strignQuery.toString());
            List<String> returnValue = (List<String>)q.list();
            return returnValue;
        } catch (Throwable ex) {
            log.error("== Error en getPendingProccessForCore/WoInfoEsbServiceDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPendingProccessForCore/WoInfoEsbServiceDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoInfoEsbServiceDAOLocal#createWoInfoEsbParentDate(co.com.directv.sdii.model.pojo.WoInfoEsbParentDate)
	 */
	@Override
	public void createWoInfoEsbParentDate(WoInfoEsbParentDate woInfoEsbParentDate) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio createWoInfoEsbParentDate/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
           
            session.persist(woInfoEsbParentDate);
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.error("== Error createWoInfoEsbParentDate ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina createWoInfoEsbParentDate/WoInfoEsbServiceDAO ==");
        }
	}
	
	@Override
	public Date getLastWOEventDate(String woCode) throws DAOServiceException, DAOSQLException{
		
		log.debug("== Inicio getLastWOEventDate/WoInfoEsbServiceDAO ==");
        Session session = ConnectionFactory.getSession();
        
        try{
            
        	if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append(" select max (ibs_creation_date) ");
            stringQuery.append(" from parallel_process_events ");
            stringQuery.append(" where wo_code =:code ");
            
            Query q = session.createSQLQuery(stringQuery.toString());
            q.setParameter("code", woCode, Hibernate.STRING);
            
            return (Date) q.uniqueResult();
            
        }catch(Throwable ex) {
            log.error("== Error en getLastWOEventDate/WoInfoEsbServiceDAO ==");
            throw this.manageException(ex);
        }finally{
            log.debug("== Termina getLastWOEventDate/WoInfoEsbServiceDAO ==");
        }
		
	}
	
}

