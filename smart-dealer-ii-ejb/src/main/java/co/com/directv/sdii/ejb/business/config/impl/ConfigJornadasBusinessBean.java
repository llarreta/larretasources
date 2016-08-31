package co.com.directv.sdii.ejb.business.config.impl;

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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ConfigJornadasBusinessLocal;
import co.com.directv.sdii.ejb.business.dealers.impl.VehiclesCRUDBean;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ServiceHourDTO;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.ServiceHourStatus;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Jornadas.
 *
 * Caso de Uso CFG - 01 - Gestionar Jornadas de Servicio
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see ConfigJornadasBusinessLocal
 */
@Stateless(name="ConfigJornadasBusinessLocal",mappedName="ejb/ConfigJornadasBusinessLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigJornadasBusinessBean extends BusinessBase implements ConfigJornadasBusinessLocal {

    private final static Logger log = UtilsBusiness.getLog4J(VehiclesCRUDBean.class);
    @EJB(name="ServiceHourDAOLocal",beanInterface=ServiceHourDAOLocal.class)
    private ServiceHourDAOLocal serviceHourDAO;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceHourVO getServiceHoursByID(Long id) throws BusinessException {
        log.debug("== Inicia getServiceHoursByID/ConfigJornadasBusinessBean ==");
        try {
            if (id == null) {
                log.debug("== Error Parametro id con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            ServiceHour sh = serviceHourDAO.getServiceHourByID(id);
            if (sh == null) {
                return null;
            }

            ServiceHourVO shVO = UtilsBusiness.copyObject(ServiceHourVO.class, sh);
            return shVO;

        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }finally {
            log.debug("== Termina getServiceHoursByID/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceHourVO> getServiceHoursByName(String name) throws BusinessException {
        log.debug("== Inicia getServiceHoursByID/ConfigJornadasBusinessBean ==");
        try {
            if (name == null) {
                log.debug("== Error Parametro name con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            List<ServiceHour> shList = serviceHourDAO.getServiceHourByName(name);
            if (shList == null) {
                return null;
            }

            List<ServiceHourVO> shVOList = UtilsBusiness.convertList(shList, ServiceHourVO.class);
            return shVOList;

        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceHoursByID/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceHourVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/ConfigJornadasBusinessBean ==");
        try {
            List<ServiceHour> shList = serviceHourDAO.getAll();
            if (shList == null) {
                return null;
            }
            List<ServiceHourVO> shVOList = UtilsBusiness.convertList(shList, ServiceHourVO.class);
            return shVOList;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceHourVO> getServiceHoursByDate(Date init, Date end) throws BusinessException {
        log.debug("== Inicia getAll/ConfigJornadasBusinessBean ==");
        try {
            List<ServiceHour> shList = serviceHourDAO.getServiceHoursByDate(init, end);
            if (shList == null) {
                return null;
            }
            List<ServiceHourVO> shVOList = UtilsBusiness.convertList(shList, ServiceHourVO.class);
            return shVOList;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ConfigJornadasBusinessBean ==");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceHours(ServiceHourVO obj, Long userId) throws BusinessException {

        log.debug("== Inicia createServiceHours/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
            }

            validateServiceHourRange(obj);

            ServiceHourStatus hourStatus = new ServiceHourStatus();
            hourStatus.setId(CodesBusinessEntityEnum.CODIGO_SERVICE_HOUR_STATUS_INACTIVE.getIdEntity(ServiceHourStatus.class.getName()));
            hourStatus.setServiceHoursStatusCode(CodesBusinessEntityEnum.CODIGO_SERVICE_HOUR_STATUS_INACTIVE.getCodeEntity());
            
            obj.setInitTime(UtilsBusiness.convert2FirstYearMonthAndDay(obj.getInitTime()));
            obj.setEndTime(UtilsBusiness.convert2FirstYearMonthAndDay(obj.getEndTime()));
            obj.setInitTime(UtilsBusiness.setInitSecondsToDate(obj.getInitTime()));
            obj.setEndTime(UtilsBusiness.setFinalSecondsToDate(obj.getEndTime()));
            obj.setServiceHourStatus(hourStatus);
            
            //En caso que se este activo valida que no se sobrelape
            validateServieHour(obj);
            
            serviceHourDAO.createServiceHour(UtilsBusiness.copyObject(ServiceHour.class, obj));

        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createServiceHours/ConfigJornadasBusinessBean ==");
        }
    }
    
    /**
     * 
     * Metodo: Valida que el service hour que se va a crear o se va a activar no se sobrelape con un service hour activo 
     * @param serviceHour
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    private void validateServieHour(ServiceHourVO serviceHour) throws BusinessException{
    	log.debug("== Inicia validateServieHour/ConfigJornadasBusinessBean ==");
        try {
        	List<ServiceHour> allActiveSH = this.serviceHourDAO.getAllServiceHoursByCountryIdAndStatusCode(serviceHour.getCountry().getId(),CodesBusinessEntityEnum.CODIGO_SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity(),serviceHour.getId());
        	if( allActiveSH != null && !allActiveSH.isEmpty() ){
        		Date aRealInitHour = UtilsBusiness.convert2FirstYearMonthAndDay(serviceHour.getInitTime());
            	Date aRealEndHour = UtilsBusiness.convert2FirstYearMonthAndDay(serviceHour.getEndTime());
        		for( ServiceHour pohoSh : allActiveSH ){
        			Date pojoRealInitHour = UtilsBusiness.convert2FirstYearMonthAndDay(pohoSh.getInitTime());
                	Date pojoRealEndHour = UtilsBusiness.convert2FirstYearMonthAndDay(pohoSh.getEndTime());
        			boolean initEquals = aRealInitHour.equals( pojoRealInitHour );
        			boolean endNewInitPojoEquals = aRealEndHour.equals( pojoRealInitHour );
            		boolean endEquals = aRealEndHour.equals( pojoRealEndHour );
            		boolean initNewEndPojoEquals = aRealInitHour.equals( pojoRealEndHour );
            		if( initEquals || endEquals || endNewInitPojoEquals || initNewEndPojoEquals ){
            			throw new BusinessException(ErrorBusinessMessages.CONFIG_CF3.getCode(), ErrorBusinessMessages.CONFIG_CF3.getMessage());
            		}
            		boolean before = aRealInitHour.before( pojoRealInitHour ) 
									&& aRealEndHour.after( pojoRealInitHour ) 
									&& aRealEndHour.before( pojoRealEndHour );
	
					boolean in = aRealInitHour.after( pojoRealInitHour ) 
								 && aRealEndHour.before( pojoRealEndHour );
	
					boolean after = aRealEndHour.after( pojoRealEndHour ) 
									&& aRealInitHour.after( pojoRealInitHour ) 
									&& aRealInitHour.before( pojoRealEndHour );
					if(before || in || after){
						throw new BusinessException(ErrorBusinessMessages.CONFIG_CF3.getCode(), ErrorBusinessMessages.CONFIG_CF3.getMessage());
					}
        		}
        	}
        	
        	
        	
        	/*
        	Long serviceHourStatusActiveCode = CodesBusinessEntityEnum.CODIGO_SERVICE_HOUR_STATUS_ACTIVE.getIdEntity( ServiceHourStatus.class.getName() );
        	//Si el objeto que se va a validar tiene estado y es activo se revisa
        	if( serviceHour.getServiceHourStatus() != null 
        			&& serviceHour.getServiceHourStatus().getId() != null
        			&& serviceHour.getServiceHourStatus().getId().equals( serviceHourStatusActiveCode )
        			&& serviceHour.getCountry() != null && serviceHour.getCountry().getId() != null 
        			&& serviceHour.getCountry().getId().longValue() > 0){
        		List<ServiceHour> serviceHours = this.serviceHourDAO.getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(serviceHour.getCountry().getId(),serviceHourStatusActiveCode,serviceHour.getInitTime(),serviceHour.getEndTime());
        		if( serviceHours != null && !serviceHours.isEmpty() )
        			throw new BusinessException(ErrorBusinessMessages.CONFIG_CF3.getCode(), ErrorBusinessMessages.CONFIG_CF3.getMessage());
        	}*/
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación validateServieHour/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina validateServieHour/ConfigJornadasBusinessBean ==");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceHours(ServiceHourVO obj, Long userId) throws BusinessException {
        log.debug("== Inicia updateServiceHours/ConfigJornadasBusinessBean ==");

        try {
            if (obj == null) {
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }

           validateServiceHourRange(obj);

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
            }

            obj.setInitTime(UtilsBusiness.convert2FirstYearMonthAndDay(obj.getInitTime()));
            obj.setEndTime(UtilsBusiness.convert2FirstYearMonthAndDay(obj.getEndTime()));
            obj.setInitTime(UtilsBusiness.setInitSecondsToDate(obj.getInitTime()));
            obj.setEndTime(UtilsBusiness.setFinalSecondsToDate(obj.getEndTime()));

            //En caso que se este activo valida que no se sobrelape
            validateServieHour(obj);
            serviceHourDAO.updateServiceHour(UtilsBusiness.copyObject(ServiceHour.class, obj));

        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }finally {
            log.debug("== Termina updateServiceHours/   ConfigJornadasBusinessBean ==");
        }
    }
    
    private void validateServiceHourRange(ServiceHourVO serviceHour) throws BusinessException{
    	Calendar initTime = Calendar.getInstance();
    	initTime.setTime(serviceHour.getInitTime());
    	Calendar endTime = Calendar.getInstance();
    	endTime.setTime(serviceHour.getEndTime());
    	
    	if(endTime.get(Calendar.HOUR_OF_DAY) == 0){
    		endTime.roll(Calendar.DAY_OF_YEAR, true);
    		serviceHour.setEndTime(endTime.getTime());
    	}
    	
    	if (serviceHour.getInitTime().after(serviceHour.getEndTime())) {
             log.debug("== El rango de horas es incorrecto hora de inicio: "+serviceHour.getInitTime()+" hora de fin: "+serviceHour.getEndTime()+" == ");
             throw new BusinessException(ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getCode(), ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getMessage());
         }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceHours(ServiceHourVO obj) throws BusinessException {
        log.debug("== Inicia deleteServiceHours/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                log.debug("== Error: El parametro obj viene establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            serviceHourDAO.deleteServiceHour(UtilsBusiness.copyObject(ServiceHour.class, obj));

        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteServiceHours/ConfigJornadasBusinessBean ==");
        }
    }


    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigJornadasBusinessLocal#getAllServiceHoursByCountryId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ServiceHourVO> getAllServiceHoursByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllServiceHoursByCountryId/ConfigJornadasBusinessBean ==");
        try {
            List<ServiceHour> shList = serviceHourDAO.getAllServiceHoursByCountryId(countryId);
            if (shList == null) {
                return null;
            }
            List<ServiceHourVO> shVOList = UtilsBusiness.convertList(shList, ServiceHourVO.class);
            return shVOList;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllServiceHoursByCountryId/ConfigJornadasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllServiceHoursByCountryId/ConfigJornadasBusinessBean ==");
        }
	}
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigJornadasBusinessLocal#getAllActiveServiceHoursByCountryId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ServiceHourVO> getAllActiveServiceHoursByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllActiveServiceHoursByCountryId/ConfigJornadasBusinessBean ==");
        try {
            List<ServiceHour> shList = serviceHourDAO.getAllServiceHoursByCountryIdAndStatusCode(countryId,CodesBusinessEntityEnum.SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity());
            if (shList == null) {
                return null;
            }
            List<ServiceHourVO> shVOList = UtilsBusiness.convertList(shList, ServiceHourVO.class);
            return shVOList;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllActiveServiceHoursByCountryId/ConfigJornadasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllActiveServiceHoursByCountryId/ConfigJornadasBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigJornadasBusinessLocal#getServiceHoursDtoByCountryId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ServiceHourDTO> getServiceHoursDtoByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getServiceHoursDtoByCountryId/ConfigJornadasBusinessBean ==");
        try {
            List<ServiceHour> shList = serviceHourDAO.getAllServiceHoursByCountryId(countryId);
            if (shList == null) {
                return null;
            }
            List<ServiceHourVO> shVOList = UtilsBusiness.convertList(shList, ServiceHourVO.class);
            List<ServiceHourDTO> result = new ArrayList<ServiceHourDTO>();
            ServiceHourDTO shDto = null;
            
            for (ServiceHourVO serviceHourVo : shVOList) {
            	shDto = buildShDtoFromVo(serviceHourVo);
            	result.add(shDto);
			}
            
            return result;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getServiceHoursDtoByCountryId/ConfigJornadasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceHoursDtoByCountryId/ConfigJornadasBusinessBean ==");
        }
	}

	/**
	 * Metodo: Construye la información de una jornada a partir de 
	 * los datos persistidos
	 * @param serviceHourVo
	 * @return <tipo> <descripcion>
	 * @author jjimenezh
	 */
	private ServiceHourDTO buildShDtoFromVo(ServiceHourVO serviceHourVo) {
		ServiceHourDTO shDto = new ServiceHourDTO(serviceHourVo);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(serviceHourVo.getInitTime());
		int initHour = cal.get(Calendar.HOUR_OF_DAY);
		int initMinute = cal.get(Calendar.MINUTE);
		
		cal.setTime(serviceHourVo.getEndTime());
		int endHour = cal.get(Calendar.HOUR_OF_DAY);
		int endMinute = cal.get(Calendar.MINUTE);
		
		shDto.setInitHour(initHour);
		shDto.setInitMinute(initMinute);
		
		shDto.setEndHour(endHour);
		shDto.setEndMinute(endMinute);
		
		return shDto;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigJornadasBusinessLocal#getServiceHourDtoByID(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ServiceHourDTO getServiceHourDtoByID(Long id)
			throws BusinessException {
		log.debug("== Inicia getServiceHourDtoByID/ConfigJornadasBusinessBean ==");
        try {
            ServiceHour sh = serviceHourDAO.getServiceHourByID(id);
            if(sh == null){
            	return null;
            }
            ServiceHourVO shVO = UtilsBusiness.copyObject(ServiceHourVO.class, sh);
            ServiceHourDTO result =  buildShDtoFromVo(shVO);
            return result;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getServiceHourDtoByID/ConfigJornadasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceHourDtoByID/ConfigJornadasBusinessBean ==");
        }
	}

	@Override
	public ServiceHourVO getServiceHoursByNameAndCountryId(
			String serviceHourName, Long countryId) throws BusinessException {
		log.debug("== Inicia getServiceHoursByNameAndCountryId/ConfigJornadasBusinessBean ==");
        try {
            ServiceHour sh = serviceHourDAO.getServiceHoursByNameAndCountryId(serviceHourName, countryId);
            if(sh == null){
            	return null;
            }
            ServiceHourVO shVO = UtilsBusiness.copyObject(ServiceHourVO.class, sh);
            return shVO;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getServiceHoursByNameAndCountryId/ConfigJornadasBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceHoursByNameAndCountryId/ConfigJornadasBusinessBean ==");
        }
	}
}
