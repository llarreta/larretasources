package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.DealersBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.exceptions.BusinessDetailException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.DealerInfoRequestDTO;
import co.com.directv.sdii.model.dto.DealerInfoResponseDTO;
import co.com.directv.sdii.model.dto.DealersCodesDTO;
import co.com.directv.sdii.model.dto.InfoDealerCountryWarehouseDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerMediaContact;
import co.com.directv.sdii.model.pojo.MediaContactType;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersMediaContactDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.MediaContactTypesDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Dealers
 *
 * @author Jimmy Vélez Muñoz
 */
@Stateless(name="DealersCRUDLocal",mappedName="ejb/DealersCRUDLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealersCRUDBean extends BusinessBase implements DealersCRUDLocal {

    @EJB(name="DealersDAOLocal",beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal dAODealerBean;

    @EJB(name="DealersMediaContactDAOLocal",beanInterface=DealersMediaContactDAOLocal.class)
    private DealersMediaContactDAOLocal dealersMediaContactDAO;

    @EJB(name="MediaContactTypesDAOLocal",beanInterface=MediaContactTypesDAOLocal.class)
    private MediaContactTypesDAOLocal mediaContactTypesDAO;

    @EJB(name="DealersBusinessBeanLocal",beanInterface=DealersBusinessBeanLocal.class)
    private DealersBusinessBeanLocal dealersBusinessBean;
    
    @EJB(name="UserDAOLocal",beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDao;
    
    private final static Logger log = UtilsBusiness.getLog4J(DealersCRUDBean.class);
    
    /**
     * Crea un dealer en el sistema
     * @param pVo - DealersVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealer(DealerVO pVo) throws BusinessException {
        log.debug("== Inicio createDealer/DealersCRUDBean ==");
        try {
            if (pVo == null) {
                throw new BusinessException("Parametro pVo nulo. No se puede crear Dealer");
            }

            if(!BusinessRuleValidationManager.getInstance().isValid(pVo)){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            Dealer dealerPojo = UtilsBusiness.copyObject(Dealer.class, pVo);
            dAODealerBean.createDealer(dealerPojo);
            pVo.setId(dealerPojo.getId());
            saveMediaContacts(pVo);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealer/DealersCRUDBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealer(List<DealersCodesDTO> dealerCodes) throws BusinessException {
    	this.createDealersFromIbsCodesAndReturn(dealerCodes);
    }
    
    /**
     * Crea un dealer en el sistema
     * @param pVo - DealersVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<DealerVO> createDealersFromIbsCodesAndReturn(List<DealersCodesDTO> dealerCodes) throws BusinessException,BusinessDetailException {
        log.debug("== Inicio createDealer/DealersCRUDBean ==");
        try {
        	List<Dealer> resultDealers = new ArrayList<Dealer>();
            for (DealersCodesDTO dealerCode : dealerCodes) {
            	
            	log.debug("== createDealer/DealersCRUDBean dealerCode,depotCode["+dealerCode.getDealerCode()+","+dealerCode.getDepotCode()+"] ==");
            	
                DealerVO newDTO = dealersBusinessBean.getDealer(dealerCode.getDealerCode(), dealerCode.getDepotCode(),dealerCode.getCountryId());
                
                if(!BusinessRuleValidationManager.getInstance().isValid(newDTO)){
                    log.error("== Error en la Capa de Negocio debido a una Validacion de negocio ==");
                    throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode() , "No se puede crear el Dealer, porque no se ha asignado uno o mas parametros obligatorios");
                }

                //Validando que no exista previamente un dealer con el mismo depotCode
                Dealer dealer = dAODealerBean.getDealerByDepotID(newDTO.getDepotCode());

                if(dealer != null){
                    log.error("== Error en la Capa de Negocio debido a una Validacion de negocio: ya existe un dealer con el mismo depotCode: "+newDTO.getDepotCode()+" ==");
                    throw new BusinessException(ErrorBusinessMessages.ALREADY_EXISTS_DEALER.getCode() , "No se puede crear el Dealer, porque ya existe un dealer con el mismo codigo depot: " + newDTO.getDepotCode());
                }

                dealer = dAODealerBean.getDealerByDealerCode(newDTO.getDealerCode());

                if(dealer != null){
                    log.error("== Error en la Capa de Negocio debido a una Validacion de negocio: ya existe un dealer con el mismo dealerCode: "+newDTO.getDealerCode()+" ==");
                    throw new BusinessException(ErrorBusinessMessages.ALREADY_EXISTS_DEALER.getCode() , "No se puede crear el Dealer, porque ya existe un dealer con el mismo codigo de dealer: " + newDTO.getDealerCode());
                }

                dealer = UtilsBusiness.copyObject(Dealer.class, newDTO);
                if( dealerCode.isBranch() )
                	dealer.setDealer(new Dealer(dealerCode.getParentDealerID()));
                dAODealerBean.createDealer(dealer);
                newDTO.setId(dealer.getId());
                this.saveMediaContacts(newDTO);
                resultDealers.add(dealer);
            }

            return UtilsBusiness.convertList(resultDealers, DealerVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	if(ex instanceof BusinessDetailException){
				throw new BusinessDetailException(((BusinessDetailException) ex).getMessageCode(),ex.getMessage(),((BusinessDetailException) ex).getParameters());
			}
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina createDealer/DealersCRUDBean ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerVO createSaleDealerForWoImporter(DealersCodesDTO code, WorkOrderDTO workOrderDto) throws BusinessException {
        log.debug("== Inicio createSaleDealerForWoImporter/DealersCRUDBean ==");

        try {
            	if(log.isDebugEnabled())
            		log.debug("== createDealer/DealersCRUDBean dealerCode,depotCode["+code.getDealerCode()+","+code.getDepotCode()+"] ==");
            	
                DealerVO newDTO = dealersBusinessBean.getDealer(code.getDealerCode(), code.getDepotCode(),code.getCountryId());
                
                if(!BusinessRuleValidationManager.getInstance().isValid(newDTO)){
                    log.error("== Error en la Capa de Negocio debido a una Validacion de negocio ==");
                    throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode() , "No se puede crear el Dealer, porque no se ha asignado uno o mas parametros obligatorios");
                }

                //Validando que no exista previamente un dealer con el mismo depotCode
                Dealer dealer = dAODealerBean.getDealerByDepotID(newDTO.getDepotCode());

                if(dealer != null){
                    log.error("== Error en la Capa de Negocio debido a una Validacion de negocio: ya existe un dealer con el mismo depotCode: "+newDTO.getDepotCode()+" ==");
                    throw new BusinessException(ErrorBusinessMessages.ALREADY_EXISTS_DEALER.getCode() , "No se puede crear el Dealer, porque ya existe un dealer con el mismo codigo depot: " + newDTO.getDepotCode());
                }

                dealer = dAODealerBean.getDealerByDealerCode(newDTO.getDealerCode());

                if(dealer != null){
                    log.error("== Error en la Capa de Negocio debido a una Validacion de negocio: ya existe un dealer con el mismo dealerCode: "+newDTO.getDealerCode()+" ==");
                    throw new BusinessException(ErrorBusinessMessages.ALREADY_EXISTS_DEALER.getCode() , "No se puede crear el Dealer, porque ya existe un dealer con el mismo codigo de dealer: " + newDTO.getDealerCode());
                }

                dealer = UtilsBusiness.copyObject(Dealer.class, newDTO);
                if( code.isBranch() )
                	dealer.setDealer(new Dealer(code.getParentDealerID()));
                dAODealerBean.createDealer(dealer);
                newDTO.setId(dealer.getId());
                this.saveMediaContacts(newDTO);
            
            return UtilsBusiness.copyObject( DealerVO.class  , dealer);
        } catch(Throwable ex){

        	log.error("== Error al tratar de ejecutar la operación getDealerByName/CrewStatusCRUDBean ==");
        	String errorCode = ErrorBusinessMessages.ERROR_DEALER_SALE_CREATE.getCode();
			Object[] params = {code.getDealerCode()};
			String message = ErrorBusinessMessages.ERROR_DEALER_SALE_CREATE.getMessage(params);
        	workOrderDto.setWarning(true);
			workOrderDto.setErrorCode(errorCode);
			workOrderDto.setErrorMessage(message);
			throw this.manageException(ex);			
        }  finally {
            log.debug("== Termina createSaleDealerForWoImporter/DealersCRUDBean ==");
        }

    }

    /**
     * Obtiene los dealers que poseen el nombre especificado
     * @param name - String
     * @return - List<DealersVO>
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<DealerVO> getDealerByName(String name) throws BusinessException {
        log.debug("== Inicia getDealerByName/DealersCRUDBean ==");
        try {
            List<DealerVO> listVo = UtilsBusiness.convertList(dAODealerBean.getDealerByName(name), DealerVO.class);
            for(DealerVO dealer : listVo){
                fillRelationshipNames(dealer);
            }
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByName/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerByName/DealersCRUDBean ==");
        }
    }

    /**
     * Actualiza un dealer
     * @param pVo - DealersVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealer(DealerVO pVo) throws BusinessException {
        log.debug("== Inicia updateDealer/DealersCRUDBean ==");
        try {
            if (pVo == null) {
                throw new BusinessException("Parametro pVo nulo. No se puede actualizar Dealer");
            }

            if(!BusinessRuleValidationManager.getInstance().isValid(pVo)){
                log.error("== Error en la Capa de Negocio debido a una Validaci�n de negocio ==");
                throw new BusinessException("No se puede actualizar Dealer, porque no se ha asignado uno o mas par�metros obligatorios");
            }

            Dealer dealerPojo = UtilsBusiness.copyObject(Dealer.class, pVo);
            dAODealerBean.updateDealer(dealerPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealer/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealer/DealersCRUDBean ==");
        }
    }

    /**
     * Elimina un dealer del sistema
     * @param pVo - DealersVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealer(DealerVO pVo) throws BusinessException {
        log.debug("== Inicia deleteDealer/DealersCRUDBean ==");
        try {
            if (pVo == null) {
                throw new BusinessException("Parametro pVo nulo. No se puede eliminar Dealer");
            }
            Dealer dealerPojo = UtilsBusiness.copyObject(Dealer.class, pVo);
            dAODealerBean.deleteDealer(dealerPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealer/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealer/DealersCRUDBean ==");
        }
    }

    /**
     *  Obtiene un listado de todos los Dealers del sistema
     * @return - List<DealersVO>
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<DealerVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/DealersCRUDBean ==");
        try {
            List<DealerVO> listVo = UtilsBusiness.convertList(dAODealerBean.getAll(), DealerVO.class);
            for(DealerVO dealer : listVo){
                fillRelationshipNames(dealer);
            }
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAll/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DealersCRUDBean ==");
        }
    }

    /**
     * Obtiene un Dealer dependiendo del Depot Id especificado
     * @param id
     * @return DealersVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerVO getDealerByDepotID(final String id) throws BusinessException {
        log.debug("== Inicia getDealerByDepotID/DealersCRUDBean ==");
        try {
            if (id == null || id.equals("")) {
                throw new BusinessException("Parametro id no especificado", new IllegalArgumentException("Param Id"));
            }

            Dealer dealerId = dAODealerBean.getDealerByDepotID(id);
            if(dealerId == null)
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());

            DealerVO dealerVO = UtilsBusiness.copyObject(DealerVO.class, dealerId);
            fillRelationshipNames(dealerVO);
            return dealerVO;
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByDepotID/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerByDepotID/DealersCRUDBean ==");
        }
    }
    
    /**
     * Obtiene un Dealer dependiendo del code Id especificado
     * @param id
     * @return DealersVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Long getDealerIdByDealerCode(Long dealerCode) throws BusinessException {
        log.debug("== Inicia getDealerIdByDealerCode/DealersCRUDBean ==");
        try {
            if (dealerCode == null || dealerCode.equals("")) {
                throw new BusinessException("Parametro id no especificado", new IllegalArgumentException("Param Id"));
            }

            Long dealerId = dAODealerBean.getDealerIdByDealerCode(dealerCode);

            return dealerId;
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerIdByDealerCode/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerIdByDealerCode/DealersCRUDBean ==");
        }
    }

    /**
     * Obtiene un dealer dependiendo del id especificado
     * @param id
     * @return - DealersVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerVO getDealerByID(final Long id) throws BusinessException {
        log.debug("== Inicia getDealerByID/DealersCRUDBean ==");
        try {

            if (id == null) {
                throw new BusinessException("Parametro id no especificado", new IllegalArgumentException("Param Id"));
            }
            Dealer dealerId = dAODealerBean.getDealerByID(id);
            if (dealerId == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            
            DealerVO dealerVo = UtilsBusiness.copyObject(DealerVO.class, dealerId);
            fillRelationshipNames(dealerVo);
            populateMediaContacts(dealerVo);
            populateDealerBranches(dealerVo);
            return dealerVo;
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByID/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerByID/DealersCRUDBean ==");
        }
    }
    
    /**
     * Obtiene un dealer dependiendo del code especificado
     * @param id
     * @return - DealersVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerVO getDealerByCode(Long dealerCode) throws BusinessException {
        log.debug("== Inicia getDealerByCode/DealersCRUDBean ==");
        DealerVO dealerVo = null;
        try {

            Dealer dealerId = dAODealerBean.getDealerByDealerCode(dealerCode);
            if (dealerId != null) {
            	dealerVo = UtilsBusiness.copyObject(DealerVO.class, dealerId);
                fillRelationshipNames(dealerVo);
                populateMediaContacts(dealerVo);
                populateDealerBranches(dealerVo);
            }
            
            return dealerVo;
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByCode/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerByCode/DealersCRUDBean ==");
        }
    }
      

    /**
     * Obtiene un dealer dependiendo del string code especificado
     * @param id
     * @return - DealersVO
     * @throws BusinessException
     * 
	 * ialessan
	 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
	 * marzo 2014
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerVO getDealerByCode(String dealerCode) throws BusinessException {
        log.debug("== Inicia getDealerByCode/DealersCRUDBean ==");
        
        if (dealerCode == null || dealerCode.equals("") || dealerCode.isEmpty()) {
            throw new BusinessException("Parametro Dealer Code no especificado", new IllegalArgumentException("Param dealerCode"));
        }
        
        Long dealerCodeLong = null;
        Object[] params = new Object[2];
        params = new Object[1];	
        params[0] = "dealerCode";
        
        try {
        	  dealerCodeLong = Double.valueOf(dealerCode).longValue();
        }catch (Exception e){
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_INVALID_NUMBER.getCode(), ErrorBusinessMessages.PARAMS_INVALID_NUMBER.getMessage(params).concat(" Dealer Code "));        	
        }
                
        DealerVO dealerVo = null;
        try {

            Dealer dealerId = dAODealerBean.getDealerByDealerCode(dealerCodeLong);
            if (dealerId != null) {
            	dealerVo = UtilsBusiness.copyObject(DealerVO.class, dealerId);
                fillRelationshipNames(dealerVo);
                populateMediaContacts(dealerVo);
                populateDealerBranches(dealerVo);
            }
            
            return dealerVo;
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByCode/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerByCode/DealersCRUDBean ==");
        }
    }    
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerVO getDealerByDepotCodeOrDealerCode(String depotCode, Long dealerCode) throws BusinessException {
        log.debug("== Inicia getDealerByDepotCodeOrDealerCode/DealersCRUDBean ==");
        try {
            if ((depotCode == null || depotCode.trim().equals("")) && (dealerCode == null || dealerCode == 0L)) {
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), new IllegalArgumentException("Param depotCode or dealerCode"));
            }

            Dealer dealer = dAODealerBean.getDealerByDepotCodeOrDealerCode(depotCode, dealerCode);
            if(dealer == null)
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());

            DealerVO dealerVO = UtilsBusiness.copyObject(DealerVO.class, dealer);
            fillRelationshipNames(dealerVO);
            populateMediaContacts(dealerVO);
            populateDealerBranches(dealerVO);
            return dealerVO;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByDepotCodeOrDealerCode/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerByDepotCodeOrDealerCode/DealersCRUDBean ==");
        }
    }

    /**
     * Completa la informaci�n de un Dealer obteniendo los nombres del tipo de canal,
     * el estado, el tipo y el c�digo postal asociado.
     * @param dealer Entidad dealer que fu� construida a partir de la obtenida de
     * la persistencia
     * @throws DAOServiceException en caso de error al tratar de obtener los nombres
     * de las relaciones
     * @throws DAOSQLException en caso de error en la capa de persistencia
     */
    private void fillRelationshipNames(DealerVO dealer) throws DAOServiceException, DAOSQLException{
        dealer.populateBean();
    }

    private void populateDealerBranches(DealerVO dealer) throws DAOServiceException, DAOSQLException, BusinessException{
        List<Dealer> branchesPojo = dAODealerBean.getActiveDealerBranchesByDealerId(dealer.getId(), null, null);
        List<DealerVO> branches = UtilsBusiness.convertList(branchesPojo, DealerVO.class);
        dealer.setDealerBranches(branches);
    }

    private void populateMediaContacts(DealerVO dealer) throws DAOSQLException, DAOServiceException, BusinessException{
        if(dealer == null)
            return;

        Long dealerId = dealer.getId();
        List<DealerMediaContact> mediaContacts = this.dealersMediaContactDAO.getDealersMediaContactByDealerId(dealerId);

        List<DealerMediaContactVO> result = UtilsBusiness.convertList(mediaContacts, DealerMediaContactVO.class);

        List<MediaContactType> allContactTypes = mediaContactTypesDAO.getAllMediaContactTypes();

        Map<Long, MediaContactType> mapMediaContact = new HashMap<Long, MediaContactType>();

        for(MediaContactType mediaContact : allContactTypes){
            mapMediaContact.put(mediaContact.getId(), mediaContact);
        }

        MediaContactType mct;

        for(DealerMediaContactVO dealerMediaContact : result){
             mct = mapMediaContact.get(dealerMediaContact.getMediaContactTypeId());
            dealerMediaContact.setMediaContactTypeName(mct == null ? null : mct.getMediaName());
        }
        
        dealer.setMediaContacts(result);
    }

    /**
     * 
     * @param dealer
     * @throws BusinessException
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    private void saveMediaContacts(DealerVO dealer) throws BusinessException, DAOServiceException, DAOSQLException{
        List<DealerMediaContactVO> mediaContactsVo = dealer.getMediaContacts();

        Dealer dealerPojo = UtilsBusiness.copyObject(Dealer.class, dealer);

        dealersMediaContactDAO.deleteDealersMediaContactByDealerId(dealer.getId());

        for(DealerMediaContactVO mediaContact : mediaContactsVo){
            DealerMediaContact dealerMediaContact = UtilsBusiness.copyObject(DealerMediaContact.class, mediaContact);
            dealerMediaContact.setDealer(dealerPojo);
            dealersMediaContactDAO.createDealerMediaContact(dealerMediaContact);
        }
    }

    /**
     * 
     */
    public void validateBusinessRules(){
        
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getAllDealersOnlyBasicInfo()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerVO> getAllDealersOnlyBasicInfo() throws BusinessException {
		log.debug("== Inicia getAllDealersOnlyBasicInfo/DealersCRUDBean ==");
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getAllDealersOnlyBasicInfo();
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealersOnlyBasicInfo/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealersOnlyBasicInfo/DealersCRUDBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getAllDealersByCountryId(java.lang.Long)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getAllDealersByCountryId(Long countryId, String isSeller, String isInstaller)
			throws BusinessException {
		log.debug("== Inicia getAllDealersByCountryId/DealersCRUDBean ==");
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getAllDealersByCountryId(countryId, isSeller, isInstaller);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            fillDepodAndName(listVo);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealersByCountryId/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealersByCountryId/DealersCRUDBean ==");
        }
	}
    
    
    /*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getAllDealersByCountryId(java.lang.Long)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getMajorDealersByCountryId(Long countryId, String isSeller, String isInstaller)
			throws BusinessException {
		log.debug("== Inicia getAllDealersByCountryId/DealersCRUDBean ==");
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getMajorDealersByCountryId(countryId, isSeller, isInstaller);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            fillDepodAndName(listVo);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealersByCountryId/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealersByCountryId/DealersCRUDBean ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getActiveMajorDealersAndBranchesByCountryId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getActiveMajorDealersAndBranchesByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getActiveMajorDealersAndBranchesByCountryId/DealersCRUDBean ==");
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getMajorDealersByCountryId(countryId, null, null);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            
            List<Dealer> branches = null;
            List<DealerVO> branchesVo = null;
            for (DealerVO dealerVO : listVo) {
            	branches = dAODealerBean.getActiveDealerBranchesByDealerId(dealerVO.getId(), null, null);
            	branchesVo = UtilsBusiness.convertList(branches, DealerVO.class);
            	dealerVO.setDealerBranches(branchesVo);
			}
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveMajorDealersAndBranchesByCountryId/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveMajorDealersAndBranchesByCountryId/DealersCRUDBean ==");
        }
	}
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getActiveMajorDealersAndBranchesByUserId(Long userId, String isSeller, String isInstaller)
			throws BusinessException {
		log.debug("== Inicia getActiveMajorDealersAndBranchesByUserId/DealersCRUDBean ==");
        try {
        	User user = daoUser.getUserById(userId);
        	List<Dealer> dealerPojos = null;
        	List<Dealer> branches = null;
            List<DealerVO> branchesVo = null;
            List<DealerVO> listVo = null;
            
        	//SI SON TODOS
        	if(user.getDealer()==null){
        		dealerPojos = dAODealerBean.getMajorDealersByCountryId(user.getCountry().getId(), isSeller, isInstaller);
        		listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
        		
        		for (DealerVO dealerVO : listVo) {
	            	branches = dAODealerBean.getActiveDealerBranchesByDealerId(dealerVO.getId(), isSeller, isInstaller);
	            	branchesVo = UtilsBusiness.convertList(branches, DealerVO.class);
	            	dealerVO.generateDepotPlusName();
	            	dealerVO.setDealerBranches(branchesVo);
				}
        	//SI ES UNA SUCURSAL
        	}else if(user.getDealer().getDealer()!=null){
        		dealerPojos = new ArrayList<Dealer>();
        		dealerPojos.add(user.getDealer().getDealer());
        		
        		listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);

        		if (listVo.get(0)!=null) {
	        		branches = new ArrayList<Dealer>();
	        		branches.add(user.getDealer());
	        		branchesVo = UtilsBusiness.convertList(branches, DealerVO.class);
	            	listVo.get(0).setDealerBranches(branchesVo);
        		}
            	
        	//SI ES UNA PRINCIPAL
        	}else{
        		dealerPojos = new ArrayList<Dealer>();
        		dealerPojos.add(user.getDealer());
        		
        		listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
        		
        		if (listVo.get(0)!=null) {
        			DealerVO dealerVO =listVo.get(0);
	            	branches = dAODealerBean.getActiveDealerBranchesByDealerId(dealerVO.getId(), isSeller, isInstaller);
	            	branchesVo = UtilsBusiness.convertList(branches, DealerVO.class);
	            	dealerVO.setDealerBranches(branchesVo);
				}
        	}
        	
        	//Generar DepotPlusName de la compañia principal y la sucursal
        	for (DealerVO dealerVO : listVo) {
				for (DealerVO dealerBranchesVO : dealerVO.getDealerBranches()) {
					dealerBranchesVO.generateDepotPlusName();
				}
				dealerVO.generateDepotPlusName();
			}
        		
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveMajorDealersAndBranchesByUserId/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveMajorDealersAndBranchesByUserId/DealersCRUDBean ==");
        }
	}
    

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getDealerBranchesByDealerIdAndFilter(java.lang.Long, java.lang.String, java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getDealerBranchesByDealerIdAndFilter(Long dealerId, String isSeller, String isInstaller)
			throws BusinessException {
		log.debug("== Inicia getDealerBranchesByDealerId/DealersCRUDBean ==");
        try {
        	//List<Dealer> dealerPojos = dAODealerBean.getDealerBranchesByDealerId(dealerId);
        	List<Dealer> dealerPojos = dAODealerBean.getDealerBranchesByDealerIdAndFilter(dealerId,isSeller,isInstaller);
        	List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            fillDepodAndName(listVo);
            return listVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerBranchesByDealerId/CrewStatusCRUDBean ==", ex);
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerBranchesByDealerId/DealersCRUDBean ==");
        }
	}
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getDealerBranchesByDealerId(java.lang.Long)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getDealerBranchesByDealerId(Long dealerId)
			throws BusinessException {
		log.debug("== Inicia getDealerBranchesByDealerId/DealersCRUDBean ==");
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getDealerBranchesByDealerId(dealerId);
        	List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            fillDepodAndName(listVo);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerBranchesByDealerId/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerBranchesByDealerId/DealersCRUDBean ==");
        }
	}
    
    

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getDealerByCodeAndType(java.lang.Long, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerVO getDealerByCodeAndType(Long id, String typeId) throws BusinessException {
        log.debug("== Inicia getDealerByCodeAndType/DealersCRUDBean ==");
        DealerVO dealerVo = null;
        try {
            Dealer dealer = dAODealerBean.getDealerByCodeAndType(id,typeId);
            if (dealer == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            dealerVo = UtilsBusiness.copyObject(DealerVO.class, dealer);
            fillRelationshipNames(dealerVo);
            populateMediaContacts(dealerVo);
            populateDealerBranches(dealerVo);
            return dealerVo;
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion getDealerByCodeAndType/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerByCodeAndType/DealersCRUDBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getDealersByDealerTypeCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getDealersByDealerTypeCodeAndCountryId(String dealerTypeCode, Long countryId) throws BusinessException {
		if(log.isDebugEnabled()){
			log.debug("== Inicia getDealersByDealerTypeCodeAndCountryId/DealersCRUDBean ==");
			log.debug("== dealerTypeCode: == [" + dealerTypeCode + "]" );
			log.debug("== countryId: == [" + countryId + "]" );
		}
		try {
        	List<Dealer> dealerPojos = dAODealerBean.getDealersByDealerTypeCodeAndCountryId(dealerTypeCode, countryId);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            fillDepodAndName(listVo);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersByDealerTypeCodeAndCountryId/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersByDealerTypeCodeAndCountryId/DealersCRUDBean ==");
        }
	}
	
	private void fillDepodAndName(List<DealerVO> listVo){
		
		if (listVo != null && !listVo.isEmpty()){
			for (DealerVO dealerVO : listVo) {
				dealerVO.generateDepotPlusName();
			}
		}
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getPrincipalsDealersByAndCountryId(Long countryId, String isSeller, String isInstaller) throws BusinessException{
		log.debug("== Inicia getPrincipalsDealersByAndCountryId/DealersCRUDBean ==");
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getPrincipalsDealersByAndCountryId(countryId, isSeller, isInstaller);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            fillDepodAndName(listVo);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getPrincipalsDealersByAndCountryId/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPrincipalsDealersByAndCountryId/DealersCRUDBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#updateDealerFromIbs(java.lang.Long)
	 */
	@Override
    public void updateDealerFromIbs(Long id) throws BusinessException{
		log.debug("== Inicia updateDealerFromIbs/DealersCRUDBean ==");
        try {
        	if (id == null) {
                throw new BusinessException("Parametro id no especificado", new IllegalArgumentException("Param Id"));
            }
        	//Consulto el dealer que se encuentra en la base de datos de SDII
            Dealer dealerPojo = dAODealerBean.getDealerByID(id);
            if (dealerPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            DealerVO newDTO = dealersBusinessBean.getDealer(dealerPojo.getDealerCode(), dealerPojo.getDepotCode(),dealerPojo.getPostalCode().getCity().getState().getCountry().getId());
            
            if(!BusinessRuleValidationManager.getInstance().isValid(newDTO)){
                log.error("== Error en la Capa de Negocio debido a una Validacion de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode() , "No se puede actualizar el Dealer, porque no se ha asignado uno o mas parametros obligatorios");
            }

            newDTO.setDealerCode( dealerPojo.getDealerCode() );
            newDTO.setDepotCode( dealerPojo.getDepotCode() );
            newDTO.setDealer( dealerPojo.getDealer() );
            newDTO.setId( dealerPojo.getId() );
            newDTO.setIsSeller(dealerPojo.getIsSeller());
            newDTO.setIsInstaller(dealerPojo.getIsInstaller());
            
            dealerPojo = UtilsBusiness.copyObject(Dealer.class, newDTO);
            
            dAODealerBean.updateDealer(dealerPojo);
            newDTO.setId(dealerPojo.getId());
            dealersMediaContactDAO.deleteDealersMediaContactByDealerId(dealerPojo.getId());
            this.saveMediaContacts(newDTO);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerFromIbs/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerFromIbs/DealersCRUDBean ==");
        }
	}
	
	/*
	 * modificacion ialessan
	 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
	 * marzo 2014
     *
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getDealerByDepotCodeAndDealerCodeAndCountryId(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerVO getDealerByDepotCodeAndDealerCodeAndCountryId(Long dealerCode, String depotCode, Long countryId) throws BusinessException {
        log.debug("== Inicia getDealerByDepotCodeAndDealerCodeAndCountryId/DealersCRUDBean ==");
        try {
        	
        	String paramsRequired = new String("");
        	
            if (  dealerCode == null 
                ||depotCode == null 
            	||depotCode.isEmpty()
            	||countryId == null 
            	||countryId <=0L) 
            {
                
                if (dealerCode == null)                	
                	paramsRequired = paramsRequired.concat( " Dealer Code ");                
                else if (depotCode == null)
                	paramsRequired = paramsRequired.concat( " Depot Code ");
                else if (depotCode.isEmpty())
                	paramsRequired = paramsRequired.concat( " Depot Code ");
                else if (countryId == null)
                	paramsRequired = paramsRequired.concat( " Country "); //validar antes
                else if (countryId <=0L)
                	paramsRequired = paramsRequired.concat( " Country "); //validar antes
                
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage().concat(paramsRequired));
            }
            
            Dealer dealerId = dAODealerBean.getDealerByDepotCodeOrDealerCode( dealerCode, depotCode, countryId);
            if (dealerId == null) {
            	Object[] params = new Object[2];
            	params[0] = dealerCode.toString();
            	params[1] = depotCode;
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage(params), UtilsBusiness.getParametersWS(params));
            }
            
            DealerVO dealerVo = UtilsBusiness.copyObject(DealerVO.class, dealerId);
            dealerVo.setDealerType(null);
            dealerVo.setChannelType(null);
            dealerVo.setDealerStatus(null);
            dealerVo.setDealer(null);
            /*fillRelationshipNames(dealerVo);
            populateMediaContacts(dealerVo);
            populateDealerBranches(dealerVo);*/
            return dealerVo;
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByDepotCodeAndDealerCodeAndCountryId/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerByDepotCodeAndDealerCodeAndCountryId/DealersCRUDBean ==");
        }
    }

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getDealersByCountryIdAndTypeCode(java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getDealersByCountryIdAndTypeCode(
			Long countryId, String dealerTypeCode) throws BusinessException {
		log.debug("== Inicia getDealersByCountryCodeAndTypeCode/DealersCRUDBean ==");
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getDealersByCountryIdAndTypeCode(countryId, dealerTypeCode);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersByCountryCodeAndTypeCode/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersByCountryCodeAndTypeCode/DealersCRUDBean ==");
        }
	}
	

	@Override
	public List<DealerVO> getDealersForGenerateKpis(Long countryId) throws BusinessException {
		log.debug("== Inicia getDealersForGenerateKpis/DealersCRUDBean ==");
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getDealersForGenerateKpis(countryId);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersForGenerateKpis/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersForGenerateKpis/DealersCRUDBean ==");
        }
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersBusinessBeanLocal#getAllActiveByCountryIdAndIsAlloc(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getAllActiveByCountryIdAndIsAlloc(Long countryId, String isSeller, String isInstaller)
			throws BusinessException {
		log.debug("== Inicia getAllActiveByCountryIdAndIsAlloc/DealersBusinessBean ==");
		UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<DealerVO> dealersVO = null;
		try {
			List<Dealer> dealers = dAODealerBean.getAllActiveByCountryIdAndIsAlloc(countryId, isSeller, isInstaller);
			dealersVO = UtilsBusiness.convertList(dealers, DealerVO.class);
		} catch (Throwable e) {
			log.error("== Error getAllActiveByCountryIdAndIsAlloc/DealersBusinessBean == " + e.getMessage());
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getAllActiveByCountryIdAndIsAlloc/DealersBusinessBean ==");
		}
		return dealersVO;
        
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getDealerBranchesByDealerId(java.util.List)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getDealerBranchesByDealerId(List<Long> dealerIds)throws BusinessException {
		log.debug("== Inicia getDealerBranchesByDealerId/DealersCRUDBean ==");
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getDealerBranchesByDealerId(dealerIds);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerBranchesByDealerId/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerBranchesByDealerId/DealersCRUDBean ==");
        }
	}

	@Override
	public List<DealerVO> getDealersByWarehouseTypeCode(String warehouseType, Long countryId)
			throws BusinessException {
		log.debug("== Inicia getDealersByWarehouseTypeCode/DealersCRUDBean ==");
		UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<Dealer> dealerPojos = dAODealerBean.getDealersByWarehouseTypeCode(warehouseType, countryId);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            fillDepodAndName(listVo);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersByWarehouseTypeCode/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersByWarehouseTypeCode/DealersCRUDBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal#getAllActiveDealerByUserIdAndCodeLogisticsOperatorAndWarehouseTypeCode(java.lang.Long, boolean)
	 */
	public List<DealerVO> getAllActiveDealerByUserIdAndCodeLogisticsOperatorAndWarehouseTypeCode(InfoDealerCountryWarehouseDTO infoDealerCountryWarehouseDTO) throws BusinessException {
		log.debug("== Inicia getAllActiveDealerByUserIdAndCodeLogisticsOperator/DealersCRUDBean ==");
		String dealerTypeCode="";
		Long userId;
		Long countryId;
		String warehouseTypeCode="";
		try {
        	if(infoDealerCountryWarehouseDTO.isCodeLogisticsOperator())
        		dealerTypeCode=CodesBusinessEntityEnum.CODE_DEALER_TYPE_LOGISTIC_OPERATOR.getCodeEntity();
        	userId=infoDealerCountryWarehouseDTO.getUserId();
    		countryId=infoDealerCountryWarehouseDTO.getCountryId();
    		warehouseTypeCode=infoDealerCountryWarehouseDTO.getWarehouseTypeCode();
        	List<Dealer> dealerPojos = dAODealerBean.getAllActiveDealerByUserIdAndDealerTypeCode(userId,countryId,dealerTypeCode,warehouseTypeCode);
            List<DealerVO> listVo = UtilsBusiness.convertList(dealerPojos, DealerVO.class);
            fillDepodAndName(listVo);
            return listVo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllActiveDealerByUserIdAndCodeLogisticsOperator/CrewStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllActiveDealerByUserIdAndCodeLogisticsOperator/DealersCRUDBean ==");
        }
	}

	/**
	 * Metodo encargado de consultar los dealers existentes en HSP+ ordenados por nombre de dealer de forma ascendente
	 * @param getHSPDealers parametro de consulta, paginacion, codigo de pais y modo de respuesta (en archivo o en objetos)
	 * @return
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public DealerInfoResponseDTO getHSPDealers(
			DealerInfoRequestDTO getHSPDealers) throws BusinessException {
		try{
			UtilsBusiness.assertNotNull(getHSPDealers, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(getHSPDealers.getCountryCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			if(getHSPDealers.getIsCsvOrSoapResponse()==null || !getHSPDealers.getIsCsvOrSoapResponse()){
				UtilsBusiness.assertNotNull(getHSPDealers.getPageIndex()>0?1L:null, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				UtilsBusiness.assertNotNull(getHSPDealers.getPageSize()>0?1L:null, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			log.debug("== Inicia getHSPDealers/DealersCRUDBean ==");
			DealerInfoResponseDTO returnValue=new DealerInfoResponseDTO();
			
			if(getHSPDealers.getIsCsvOrSoapResponse() == null || !getHSPDealers.getIsCsvOrSoapResponse()){
				RequestCollectionInfo ri = new RequestCollectionInfo();
				ri.setPageIndex(getHSPDealers.getPageIndex());
				ri.setPageSize(getHSPDealers.getPageSize());
				returnValue=dAODealerBean.getHSPDealers(getHSPDealers, ri);
			}
			else{
				boolean needOtherCall = true;
				int page = 0;
				int pageSize = Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT.getCodeEntity());
				String nameFile=null;
				List<String> columnNames = new ArrayList<String>();
				columnNames.add(ApplicationTextEnum.IBS_DEALER_CODE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.DEPOT_CODE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.DEALER_STATUS_CODE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.DEALER_STATUS.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.DEALER_PRINCIPAL_IBS_CODE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.SCORE.getApplicationTextValue());
				columnNames.add(ApplicationTextEnum.IS_PRINCIPAL.getApplicationTextValue());
				columnNames.add("Código tipo canal");
				List<String> fieldNames = new ArrayList<String>();
				fieldNames.add("ibsCode");
				fieldNames.add("depotCode");
				fieldNames.add("dealerStateCode");
				fieldNames.add("dealerState");
				fieldNames.add("ibsCodePrincipalDealer");
				fieldNames.add("score");
				fieldNames.add("isPrincipal");
				fieldNames.add("channelCode");
				while(needOtherCall){
					RequestCollectionInfo ri = new RequestCollectionInfo();
					ri.setPageIndex(page+1);
					ri.setPageSize(pageSize);
					DealerInfoResponseDTO dataList = dAODealerBean.getHSPDealers(getHSPDealers, ri);
					if(dataList== null || dataList.getDealerInfoDTOList()==null || dataList.getDealerInfoDTOList().isEmpty() || dataList.getDealerInfoDTOList().size()<pageSize){
						needOtherCall = false;
					}
					nameFile=UtilsBusiness.generateCsv(dataList.getDealerInfoDTOList(),fieldNames,columnNames,page, nameFile);
					++page;
				}
				String nameFileResponse = "report"+nameFile;
				String fileType = "";
				nameFileResponse+=".csv";
				fileType = "text/plain";
				returnValue.setFile( UtilsBusiness.generateResponseFromNameFileTempCsv(nameFileResponse, "dealers_report.csv", fileType) );
			}
			return returnValue;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación getHSPDealers/CrewStatusCRUDBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getHSPDealers/DealersCRUDBean ==");
		}
	}
	
}
