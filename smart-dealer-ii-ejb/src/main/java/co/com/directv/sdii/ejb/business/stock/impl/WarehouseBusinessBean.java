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
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.model.dto.collection.WarehouseElementStockDTO;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WarehouseElementStockVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD Warehouse
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal
 */
@Stateless(name="WarehouseBusinessBeanLocal",mappedName="ejb/WarehouseBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WarehouseBusinessBean extends BusinessBase implements WarehouseBusinessBeanLocal {
	
    @EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
    private WarehouseDAOLocal daoWarehouse;
    
    @EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
    private WarehouseElementDAOLocal daoWarehouseElement;
    
    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal daoSerialized;
    
    @EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
    private ReferenceBusinessBeanLocal refBusinessBeanLocal;
    
    @EJB(name="WarehouseTypeBusinessBeanLocal", beanInterface=WarehouseTypeBusinessBeanLocal.class)
    private WarehouseTypeBusinessBeanLocal whTypeBusinessBeanLocal;
    
    @EJB(name="EmployeesCrewDAOLocal", beanInterface=EmployeesCrewDAOLocal.class)
    private EmployeesCrewDAOLocal employCrewDAO;
    
    @EJB(name="WarehouseTypeDAOLocal", beanInterface=WarehouseTypeDAOLocal.class)
    private WarehouseTypeDAOLocal daoWarehouseType;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    @EJB(name="CustomerDAOLocal", beanInterface=CustomerDAOLocal.class)
    private CustomerDAOLocal customerDAO;
    
    @EJB(name="DealersDAOLocal",beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal dealersDao;
    
    @EJB(name="CrewsDAOLocal",beanInterface=CrewsDAOLocal.class)
    private CrewsDAOLocal daoCrew;
    
    @EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal daoDealer;
    
    @EJB (name="WarehouseTypeBusinessBeanLocal", beanInterface=WarehouseTypeBusinessBeanLocal.class)
	private WarehouseTypeBusinessBeanLocal businessWarehouseType;
    
    @EJB (name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal businessWarehouse;
    
    @EJB (name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(WarehouseBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getAllWarehouses()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WarehouseVO> getAllWarehouses() throws BusinessException {
        log.debug("== Inicia getAllWarehouses/WarehouseBusinessBean ==");
        try {
        	List<WarehouseVO> result = UtilsBusiness.convertList(daoWarehouse.getAllWarehouses(), WarehouseVO.class);
        	generateWarehouseNames(result);
        	return result;
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllWarehouses/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllWarehouses/WarehouseBusinessBean ==");
        }
    }
    
 
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getAllWarehousesByCountryId(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     *
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WarehouseInfoResponseDetailDTO getAllWarehousesByCountryId(
    		Long countryId, 
    		String code,
    		RequestCollectionInfo requestCollInfo
    		) 
    throws BusinessException {
    	log.debug("== Inicia getAllWarehousesByCountryId/WarehouseBusinessBean ==");
		List<Warehouse> whList ;
        try {
        	WarehouseInfoResponseDetailDTO response = daoWarehouse.getAllWarehousesByCountryId(countryId,code,requestCollInfo);
//        	whList = response.getWareHouse();
//        	response.setWareHouseVO(UtilsBusiness.convertList(whList, WarehouseVO.class));
//        	response.setWareHouse(null);
        	//Se itera para generar el nombre de la ubicación
//        	generateWarehouseNames(response.getWareHouseVO());
        	return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllWarehousesByCountryId/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllWarehousesByCountryId/WarehouseBusinessBean ==");
        }
    } 
    
    /* 
  	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros.
     *
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getAllWarehousesByCountryId(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     *
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WarehouseInfoResponseDetailDTO getWhByWhTypeDealerBranchCrewIds(  Long countryId, 
																				 String  warehouseTypeId, 
																				 String  dealerId, 
																				 String  branchId,
																				 String  crewId ,
																				 RequestCollectionInfo requestCollInfo
    		) 
    throws BusinessException {
    	log.debug("== Inicia getWhByWhTypeDealerBranchCrewIds/WarehouseBusinessBean ==");
		List<Warehouse> whList ;
        try {

        	 Long lcountryId =  countryId;
        	 
        	 Long lwarehouseTypeId = null;
			 if ((warehouseTypeId!=null)&&(!warehouseTypeId.isEmpty()))
				 lwarehouseTypeId = Long.parseLong(warehouseTypeId);
			 
			 Long ldealerId = null;
			 if ((dealerId!=null)&&(!dealerId.isEmpty()))
				 ldealerId = Long.parseLong(dealerId);
				 
			 Long lbranchId = null;
			 if ((branchId!=null)&&(!branchId.isEmpty()))
				 lbranchId = Long.parseLong(branchId);
			 

			 Long lcrewId = null;
			 if ((crewId!=null)&&(!crewId.isEmpty()))
				 lcrewId = Long.parseLong(crewId);
			 
        	WarehouseInfoResponseDetailDTO response = daoWarehouse.getWhByWhTypeDealerBranchCrewIds( lcountryId, 
																										 lwarehouseTypeId, 
																										 ldealerId, 
																										 lbranchId,
																										 lcrewId ,
																										 requestCollInfo);        	
        	return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWhByWhTypeDealerBranchCrewIds/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWhByWhTypeDealerBranchCrewIds/WarehouseBusinessBean ==");
        }
    } 
    
    
    /**
     * Metodo: Operación para generar los nombres de las bodegas
     * @param whList2GenerateName lista con las bodegas a se llenadas
     * @throws DAOServiceException en caso de error
     * @throws DAOSQLException en caso de error
     * @author jjimenezh
     * @throws DAOSQLException 
     * @throws DAOServiceException 
     * @throws PropertiesException 
     */
    private void generateWarehouseNames(List<WarehouseVO> whList2GenerateName)  throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
    	if(whList2GenerateName == null)
    		return;
    	
    	for(WarehouseVO tmp: whList2GenerateName)
    		generateWarehouseName(tmp);
    	
    }
    
     
    
    private void generateWarehouseName(WarehouseVO warehouse2GenerateName) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
    	if(warehouse2GenerateName == null){
    		return;
    	}
    	String tmpDealerDepotPlusName1="";
    	String tmpDealerDepotPlusName2="";
    	DealerVO dealerVO = null;    	
    	StringBuffer buffer = new StringBuffer();
    	
    	if (warehouse2GenerateName.getCrewId() != null){
			buffer.append(warehouse2GenerateName.getCrewId().getDealer().getDepotCode() + " - ");
    		buffer.append(warehouse2GenerateName.getCrewId().getDealer().getDealerName() + " - ");
    		List<EmployeeCrew> listTmp= employCrewDAO.getEmployeesCrewByCrewID(warehouse2GenerateName.getCrewId().getId());
    		if(listTmp.size()>0){
    			Employee employee = null;
    			for (EmployeeCrew employeeCrew : listTmp) {
					if(employeeCrew.getIsResponsible().equals(CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity())){
						employee=employeeCrew.getEmployee();
						break;
					}	
				}
    			if(employee != null){
	    			buffer.append( employee.getFirstName()+" " +employee.getLastName() + " - ");
	        		warehouse2GenerateName.setCrewResponsable(employee.getFirstName()+" " +employee.getLastName());
    			}
    		}
		}else if (warehouse2GenerateName.getDealerId()!=null){
			buffer.append(warehouse2GenerateName.getDealerId().getDepotCode() + " - ");
    		buffer.append(warehouse2GenerateName.getDealerId().getDealerName() + " - ");
		}else if (warehouse2GenerateName.getCustomerId()!=null){
			buffer.append(warehouse2GenerateName.getCustomerId().getCustomerCode() + " - ");
    		buffer.append(warehouse2GenerateName.getCustomerId().getFirstName() + " - ");
    		buffer.append(warehouse2GenerateName.getCustomerId().getLastName() + " - ");
		}
    	
		if(warehouse2GenerateName.getDealerId()!=null){
			
			dealerVO =  UtilsBusiness.copyObject(DealerVO.class, warehouse2GenerateName.getDealerId());
			dealerVO.generateDepotPlusName();
			tmpDealerDepotPlusName1=dealerVO.getDepotPlusName();
		
			if(warehouse2GenerateName.getDealerId().getDealer()!=null){
				dealerVO =  UtilsBusiness.copyObject(DealerVO.class, warehouse2GenerateName.getDealerId().getDealer());
				dealerVO.generateDepotPlusName();
				tmpDealerDepotPlusName2=dealerVO.getDepotPlusName();
				
				warehouse2GenerateName.setDealerDepotPlusName(tmpDealerDepotPlusName2);
				warehouse2GenerateName.setDealerBranchDepotPlusName(tmpDealerDepotPlusName1);
			}else{
				warehouse2GenerateName.setDealerDepotPlusName(tmpDealerDepotPlusName1);
				warehouse2GenerateName.setDealerBranchDepotPlusName(tmpDealerDepotPlusName2);
			}
		}
		
		buffer.append(warehouse2GenerateName.getWarehouseType().getWhTypeName());
		warehouse2GenerateName.setWarehouseName(buffer.toString());
    }
    
    
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByCountryId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseResponse getWarehousesByCountryId(Long countryId, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getWarehousesByCountryId/WarehouseBusinessBean ==");
        try {
        	WareHouseResponse response = daoWarehouse.getWarehousesByCountryId(countryId, requestCollInfo);
        	List<Warehouse> whPojos = response.getWareHouse();
        	response.setWareHouseVO( UtilsBusiness.convertList(whPojos, WarehouseVO.class) );
        	response.setWareHouse( null );
        	generateWarehouseNames(response.getWareHouseVO());
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehousesByCountryId/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehousesByCountryId/WarehouseBusinessBean ==");
        }
	}
	
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByCountryIdAndDealerType(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseInfoResponseDTO getWarehousesByCountryIdAndDealerCondition(Long countryId, RequestCollectionInfo requestCollInfo,
				String isSeller, String isInstaller)
			throws BusinessException {
		log.debug("== Inicia getWarehousesByCountryIdAndDealerCondition/WarehouseBusinessBean ==");
        try {
        	WarehouseInfoResponseDTO response = daoWarehouse.getWarehousesByCountryIdAndDealerCondition(countryId, requestCollInfo, isSeller, isInstaller);
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehousesByCountryIdAndDealerCondition/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehousesByCountryIdAndDealerCondition/WarehouseBusinessBean ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByCountryIdAndActive(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseVO> getWarehousesByCountryIdAndActive(Long countryId,Long dealerId)
			throws BusinessException {
		log.debug("== Inicia getWarehousesByCountryIdAndActive/WarehouseBusinessBean ==");
        try {
        	List<Warehouse> whPojos = daoWarehouse.getWarehousesByCountryIdAndStatus(countryId, dealerId,CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
        	if(whPojos==null){
        		return null;
        	}
        	List<WarehouseVO> whVO = UtilsBusiness.convertList(whPojos, WarehouseVO.class);
        	
        	generateWarehouseNames(whVO);
        	
        	return whVO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehousesByCountryIdAndActive/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehousesByCountryIdAndActive/WarehouseBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByCountryIdAndActive(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseResponse getWarehousesByCountryIdAndActive(Long countryId,RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getWarehousesByCountryIdAndActive/WarehouseBusinessBean ==");
		List<Warehouse> whList ;
        try {
        	WareHouseResponse response = daoWarehouse.getWarehousesByCountryIdAndStatus(countryId, CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity(),requestCollInfo);
        	whList = response.getWareHouse();
        	response.setWareHouseVO(UtilsBusiness.convertList(whList, WarehouseVO.class));
        	response.setWareHouse(null);
        	//Se itera para generar el nombre de la ubicación
        	generateWarehouseNames(response.getWareHouseVO());
        	return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehousesByCountryIdAndActive/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehousesByCountryIdAndActive/WarehouseBusinessBean ==");
        }
	}

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WarehouseVO getWarehouseByID(Long id) throws BusinessException {
        log.debug("== Inicia getWarehouseByID/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Warehouse objPojo = daoWarehouse.getWarehouseByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            WarehouseVO tmp = UtilsBusiness.copyObject(WarehouseVO.class, objPojo);
            generateWarehouseName(tmp);
            return tmp;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseByID/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseByID/WarehouseBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#createWarehouse(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseVO createWarehouse(WarehouseVO obj) throws BusinessException {
		log.debug("== Inicia createWarehouse/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getWarehouseType().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getCountry().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getResponsibleEmail(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getWhResponsible(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getDealerId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        if((obj.getDealerId()==null ||obj.getDealerId().getId()==null) && (obj.getDealerId().getDealer()==null ||obj.getDealerId().getDealer().getId()==null)
         		&&(obj.getCrewId()==null ||obj.getCrewId().getId()==null)){
    		 log.error("Error de validaciÃ³n de campos requeridos");
     		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
         }
        try {
        	String whCode=buildWhCode(obj);
            obj.setWhCode(whCode);
            Warehouse objPojo =  this.daoWarehouse.getWarehouseByCodeAndByCountry(obj.getWhCode(),obj.getCountry().getId());
            if (objPojo != null) {
            	//gfandino-01/06/2011 Se adiciona por incluir estado de Warehouse INV104 v2.2
            	throw new BusinessException(ErrorBusinessMessages.STOCK_IN335.getCode(),ErrorBusinessMessages.STOCK_IN335.getMessage());
            	
            }
            
            //gfandino-01/06/2011 Se adiciona por incluir estado de Warehouse INV104 v2.2
            //Se valida que para la  no exista una bodega ya creada del mismo tipo
            //Consultar el tipo de bodega por el ID. .net no esta enviando el codigo, por tal razon hay que consultar el codigo.
            
            
            //Validar si la nueva bodega es para una cuadrilla
            WarehouseType wht = daoWarehouseType.getWarehouseTypeByID(obj.getWarehouseType().getId());
            if(obj.getCrewId()!=null &&obj.getCrewId().getId()!=null &&obj.getCrewId().getId()>0){
            	List<Warehouse> delaerWhs = daoWarehouse.getWhByCrewAndWhTypeCode(obj.getCrewId().getId(), wht.getWhTypeCode());
            	if(delaerWhs.size()>0){
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN336.getCode(),ErrorBusinessMessages.STOCK_IN336.getMessage());
            	}
            }else if(obj.getDealerId().getDealer() != null && obj.getDealerId().getDealer().getId() != null && obj.getDealerId().getDealer().getId().longValue() > 0){
            	//Si entra en esta opción indica que es una sucursal
            	Warehouse delaerWhs = daoWarehouse.getWarehousesByDealerIdAndWhTypeCode(obj.getDealerId().getId(),wht.getWhTypeCode());
            	//Si la lista tiene uno o más registros indica que ya existe una bodega del tipo para la compañía
            	boolean existWarehouseDealer= false;
            	if(delaerWhs != null){
            		existWarehouseDealer=true;
            	}
            	if(existWarehouseDealer){
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN336.getCode(),ErrorBusinessMessages.STOCK_IN336.getMessage());
            	}
            }else if(obj.getDealerId() != null && obj.getDealerId().getId() != null && obj.getDealerId().getId().longValue() > 0){
            	//Si entra en esta opción indica que es una compañia principal
            	Warehouse delaerWhs = daoWarehouse.getWarehousesByDealerIdAndWhTypeCode(obj.getDealerId().getId(),wht.getWhTypeCode());
            	//Si la lista tiene uno o más registros indica que ya existe una bodega del tipo para la sucursal
            	boolean existWarehouseDealer= false;
            	if(delaerWhs != null){
            		existWarehouseDealer=true;
            	}
            	if(existWarehouseDealer){
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN337.getCode(),ErrorBusinessMessages.STOCK_IN337.getMessage());
            	}
            }
            
            
            /*
             * gfandino-01/06/2011 Se elimina esta validación dado que el caso de uso INV104 v2.2 no lo incluye
             */
            /*if(obj.getCrewId() != null && obj.getCrewId().getId() != null && obj.getCrewId().getId().longValue() > 0){
            	List<Warehouse> crewWhs = daoWarehouse.getWarehousesByCrewId(obj.getCrewId().getId());
            	for (Warehouse crewWh : crewWhs) {
					if(crewWh.getWarehouseType().getId().longValue() == obj.getWarehouseType().getId().longValue()){
						//throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(),ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
						//gfandino-01/06/2011 Se adiciona por incluir estado de Warehouse INV104 v2.2
		            	throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ALREADY_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_ALREADY_EXIST.getMessage());
					}
				}
            }*/
            
            
            
     		List<Warehouse> whList = daoWarehouse.getWarehouseByWhCode(obj.getWhCode(), obj.getCountry().getId());
     		Boolean updateCrew = false;
     		
     		if(whList!=null && !whList.isEmpty() && whList.size()>0){
     			
     			if(whList.get(0).getCrewId() != null){

         			//si esta ubicacion posee la cuadrilla asociada en estado activa, emitir error informando que la ubicacion ya existe (el usuario deberia ir por moodificacion y activar la ubicacion)    			
         			List<EmployeeCrew> empCrews = employCrewDAO.getEmployeesCrewByCrewID(whList.get(0).getCrewId().getId());
         			Employee employeeResp = new Employee();
         			for(EmployeeCrew emp : empCrews) {
         				if(CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity().equals(emp.getIsResponsible())){
         					if(CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity().equals(emp.getCrew().getCrewStatus().getStatusCode())) {
         						throw new BusinessException(ErrorBusinessMessages.STOCK_IN335.getCode(),ErrorBusinessMessages.STOCK_IN335.getMessage());
         					}
         					employeeResp = emp.getEmployee();
         				}
         			}
         			List<EmployeeCrew> emplCrews = employCrewDAO.getEmployeesCrewByEmpIdAndCrewStatus(employeeResp.getId(),CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());    			
         			//si esta ubicacion posee la cuadrilla asociada en estado inactiva, buscar si existe una cuadrilla activa con el mismo responsable y dealer y proceder a activar la ubicacion asociandola a la nueva cuadrilla 
         			objPojo = UtilsBusiness.copyObject(Warehouse.class, whList.get(0));
         			///obj.setCrewId(emplCrews.get(0).getCrew());
         			objPojo.setCrewId(emplCrews.get(0).getCrew());
         			objPojo.setResponsibleEmail(obj.getResponsibleEmail());
         			objPojo.setIsActive(CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
         			objPojo.setWhResponsible(obj.getWhResponsible());
         			obj = UtilsBusiness.copyObject(WarehouseVO.class,objPojo);
         			updateCrew = true;
         			
     			}else{
     			   	
     				throw new BusinessException(ErrorBusinessMessages.STOCK_IN335.getCode(),ErrorBusinessMessages.STOCK_IN335.getMessage());
     				
     			}
     			
     		}   		

     		if(updateCrew){
     			updateWarehouseTransit(obj,CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());               
         		objPojo = daoWarehouse.updateWarehouse(objPojo);
     		}else{ 
     			objPojo =  UtilsBusiness.copyObject(Warehouse.class, obj);
     			objPojo = daoWarehouse.createWarehouse(objPojo);
     		}
            
            return UtilsBusiness.copyObject(WarehouseVO.class, objPojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n createWarehouse/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWarehouse/WarehouseBusinessBean ==");
        }
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String buildWhCode(Warehouse wh) throws DAOServiceException, DAOSQLException, BusinessException{
		String returnValue="";
		boolean haveDealer=false;
		boolean haveCrew=false;
		boolean haveCustomer=false;
		
		if(		wh.getDealerId()!=null 
			&& 	wh.getDealerId().getId()!=null 
			&& (wh.getCrewId()==null  			// no tiene cuadrilla
			||  wh.getCrewId().getId()==null)	// no tiene cuadrilla
		){//tiene unicamente Dealer
			haveDealer=true;
			
		}else if(	   wh.getDealerId()!=null 
					&& wh.getDealerId().getId()!=null 
					&& wh.getCrewId()!=null 
					&& wh.getCrewId().getId()!=null
		){//Tiene dealer y cuadrilla
			haveCrew=true;
			
		}else if(		wh.getCustomerId()!=null 
					&& 	wh.getCustomerId().getId()!=null 
					&& (wh.getDealerId()==null 
					||  wh.getDealerId().getId()==null)
		){//Tiene solo cliente
			haveCustomer=true;
		}
		
		wh.setWarehouseType(daoWarehouseType.getWarehouseTypeByID(wh.getWarehouseType().getId()));
		
		if(haveDealer){
			wh.setDealerId(dealersDao.getDealerByID(wh.getDealerId().getId()));
			returnValue = wh.getWarehouseType().getWhTypeCode()+"_"+wh.getDealerId().getDepotCode();
		
		}else if(haveCrew){
			wh.setDealerId(dealersDao.getDealerByID(wh.getDealerId().getId()));
			wh.setCrewId(daoCrew.getCrewById(wh.getCrewId().getId()));
			Employee resposible = employCrewDAO.getEmployeeResponsibleByCrewId(wh.getCrewId().getId());
			returnValue = wh.getWarehouseType().getWhTypeCode() + "_" + wh.getDealerId().getDepotCode()+"_"+resposible.getFirstName().trim().replace(" ", "_")+"_"+resposible.getLastName().trim().replace(" ", "_");
		
		}else if(haveCustomer){
			wh.setCustomerId(this.customerDAO.getCustomerByID(wh.getCustomerId().getId()));
			returnValue = wh.getWarehouseType().getWhTypeCode() + "_" + wh.getCustomerId().getCustomerCode();
		}else{
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN502.getCode(), ErrorBusinessMessages.STOCK_IN502.getMessage());
		}
		if(returnValue.length()>=100){
			returnValue=returnValue.substring(0, 100);
		}
		returnValue=returnValue.toUpperCase();
		return returnValue;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#createWarehouseTransit(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseVO createWarehouseTransit(WarehouseVO obj) throws BusinessException{
		log.debug("== Inicia createWarehouse/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getDealerId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getDealerId().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	obj.setId(null);
        	WarehouseType whType = this.whTypeBusinessBeanLocal.getWarehouseTypeByCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_WHTRO722.getCodeEntity());
        	obj.setWarehouseType(whType);
        	obj = this.createWarehouse(obj);
        	return obj;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n createWarehouse/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWarehouse/WarehouseBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#createWarehouseTransit(co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.WarehouseTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseVO createWarehouseTransit(WarehouseVO obj,WarehouseTypeVO warehouseType) throws BusinessException{
		log.debug("== Inicia createWarehouse/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getDealerId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getDealerId().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	obj.setId(null);
        	obj.setWarehouseType(UtilsBusiness.copyObject(WarehouseType.class, warehouseType));
        	obj.setWhCode(null);
        	obj = this.createWarehouse(obj);
        	return obj;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n createWarehouse/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWarehouse/WarehouseBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#updateWarehouse(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateWarehouse(WarehouseVO obj) throws BusinessException {
        log.debug("== Inicia updateWarehouse/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	Warehouse objPojo =  this.daoWarehouse.getWarehouseByCodeAndByCountry(obj.getWhCode(),obj.getCountry().getId());
            if (objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            
            
          //gfandino-01/06/2011 Se adiciona por incluir estado de Warehouse INV104 v2.2
          /*
           * Se valida:  V-02 - Una ubicación que se inactive no debe tener mercancía cargada o mercancía pendiente por confirmar. 
           * Sus saldos deben estar en Cero
           */
            if(obj.getIsActive().equals(CodesBusinessEntityEnum.WAREHOUSE_STATUS_INACTIVE.getCodeEntity())){
            	//Se valida que los saldos se encuentren en cero            
                List<WarehouseElement> listElement= daoWarehouseElement.getWHElementByWarehouseAndLastRecord(obj.getId());
                if(listElement.size()>0){
                	throw new BusinessException(ErrorBusinessMessages.STOCK_IN338.getCode(),ErrorBusinessMessages.STOCK_IN338.getMessage());
                }
                //Se valida que la bodega no sea destino de alguna remisión 
                WarehouseVO whTmp = new WarehouseVO();
                whTmp.setId(obj.getId());
                List<ReferenceVO> ref = refBusinessBeanLocal.getReferencesByTargetWareHouseAndByCountry(whTmp,obj.getCountry().getId());
                for(ReferenceVO tmp : ref){
                	//Si existe una remisión que tenga como destino la bodega a inactivar y que no esté recibida
                	if(!tmp.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity())
                			&&!tmp.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_DELETED.getCodeEntity())){
                		throw new BusinessException(ErrorBusinessMessages.STOCK_IN339.getCode(),ErrorBusinessMessages.STOCK_IN339.getMessage());
                	}
                }
                //validateCrew(obj,CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
                updateWarehouseTransit(obj,CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());               
            }
            else {
                validateCrew(obj,CodesBusinessEntityEnum.CREW_STATUS_INACTIVE.getCodeEntity());
            	updateWarehouseTransit(obj,CodesBusinessEntityEnum.WAREHOUSE_STATUS_INACTIVE.getCodeEntity());                                     	
            }

            objPojo =  UtilsBusiness.copyObject(Warehouse.class, obj);
            daoWarehouse.updateWarehouse(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateWarehouse/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWarehouse/WarehouseBusinessBean ==");
        }
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateWarehouseTransit(WarehouseVO warehouse, String isActive) throws BusinessException {
		try {
			if(warehouse.getCrewId()!=null){
				List<Warehouse> whs = daoWarehouse.getWarehousesByDealerIdAndCrewId(warehouse.getDealerId().getId(), warehouse.getCrewId().getId());
				WarehouseType whTypeTransSource = this.businessWarehouseType.getWarehouseTypeByCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_WHTRO722.getCodeEntity());
				WarehouseType whTypeTransTarget = this.businessWarehouseType.getWarehouseTypeByCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_WHTRD723.getCodeEntity());
				for(Warehouse wh : whs ){
					if(wh.getIsActive().equals(isActive) &&
					  (wh.getWarehouseType().getId().equals(whTypeTransSource.getId()) ||
					   wh.getWarehouseType().getId().equals(whTypeTransTarget.getId()) ) ) {
					   wh.setIsActive(warehouse.getIsActive());
					   wh.setResponsibleEmail(warehouse.getResponsibleEmail());
					   wh.setCrewId(wh.getCrewId());
					   wh.setWhCode(wh.getWhCode());
					   daoWarehouse.updateWarehouse(wh);        			
					}
				}	
			}
		} catch (Throwable ex) {
			throw this.manageException(ex);
		} finally {
			log.info("== Termina updateWarehouseTransit/WarehouseBusinessBean ==");
		}
	}	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateCrew(WarehouseVO obj, String crewStatus) throws BusinessException{
		try {
			if(obj.getCrewId()!=null){	
				Crew crew = null;
				crew = daoCrew.getCrewById(obj.getCrewId().getId());
				Object params[]=new Object[1];
				params[0] = crew.getVehicle().getPlate();
				if(obj.getCrewId().getCrewStatus().getStatusCode().equals(crewStatus))
					throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_CREW_STATUS_INVALID.getCode(),ErrorBusinessMessages.WAREHOUSE_CREW_STATUS_INVALID.getMessage(params)); 
			}
		}catch(Throwable ex){
			throw this.manageException(ex);
		} finally{
			log.info("== Termina validateCrew/WarehouseBusinessBean ==");
		}	
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#deleteWarehouse(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteWarehouse(WarehouseVO obj) throws BusinessException {
        log.debug("== Inicia deleteWarehouse/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Warehouse objPojo =  UtilsBusiness.copyObject(Warehouse.class, obj);
            daoWarehouse.deleteWarehouse(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteWarehouse/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWarehouse/WarehouseBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehouseByCodeAndByCountry(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseVO getWarehouseByCodeAndByCountry(String code,Long country) throws BusinessException {
		log.debug("== Inicia getWarehouseByCodeAndByCountry/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Warehouse objPojo = daoWarehouse.getWarehouseByCodeAndByCountry(code,country);
            WarehouseVO result = UtilsBusiness.copyObject(WarehouseVO.class, objPojo);
            generateWarehouseName(result);
            return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseByCodeAndByCountry/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseByCodeAndByCountry/WarehouseBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWhByCrewAndWhType(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseVO> getWhByCrewAndWhType(Long crewId,
			Long whTypeId) throws BusinessException {
		log.debug("== Inicia getWhByCrewAndWhType/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(crewId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(whTypeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            List<Warehouse> objPojo = daoWarehouse.getWhByCrewAndWhType(crewId,whTypeId);
            List<WarehouseVO> result = UtilsBusiness.convertList(objPojo, WarehouseVO.class);
            generateWarehouseNames(result);
            return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWhByCrewAndWhType/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWhByCrewAndWhType/WarehouseBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#validateSerializedInventory(java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SerializedVO> validateSerializedInventory(Long whID, List<SerializedVO> serializadosFisicos, Long countryId) throws BusinessException {
		log.debug("== Inicia validateSerializedInventory/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(whID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serializadosFisicos, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	List<SerializedVO> comparativo = new ArrayList<SerializedVO>();
        	//Recorrer la lista de elementos físicos serializados
        	for(SerializedVO elementoFisico : serializadosFisicos){
        		SerializedVO serializadoBodegaTmp = new SerializedVO();
        		//Consultar si para whID, el elemento serializado tiene fecha de salida nula. Si no es porque no está en esa bodega
        		UtilsBusiness.assertNotNull(elementoFisico, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
        		UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		UtilsBusiness.assertNotNull(elementoFisico.getSerialCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		UtilsBusiness.assertNotNull(elementoFisico.getElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
        		Warehouse wareHouse = daoWarehouse.getWarehouseByID(whID);
        		
        		//Se cargan los parametros que son necesarios para realizar la validacion de que el 
        		//elemento exista en la Bodega recibida.
        		String whCode = wareHouse.getWhCode();
        		String serialCode = elementoFisico.getSerialCode();        		       		
        		String elementCode = elementoFisico.getElement().getElementType().getTypeElementCode();
        		String elementStatusCode = elementoFisico.getElement().getElementStatus().getElementStatusCode();
        		String isSerialized = elementoFisico.getElement().getIsSerialized();
        		String modelCode = elementoFisico.getElement().getElementType().getElementModel().getModelCode();         		
        		//Opcionales
        		//Si el elemento recibido viene con un elmento vinculado.
        		String linkedSerialCode = null;
        		if( elementoFisico.getSerialized() != null ){
        			linkedSerialCode = elementoFisico.getSerialized().getSerialCode();
        		}
        		String lote = null;
        		if( elementoFisico.getElement().getLote() != null ) 
        			lote = elementoFisico.getElement().getLote();
        		String measureUnit = null;
        		/*if( elementoFisico.getElement().getMeasureUnit() != null )
        			if( elementoFisico.getElement().getMeasureUnit().getUnitCode() != null )
        			measureUnit = elementoFisico.getElement().getMeasureUnit().getUnitCode();*/
        		if( elementoFisico.getElement().getElementType().getMeasureUnit() != null )
        			if( elementoFisico.getElement().getElementType().getMeasureUnit().getUnitCode() != null )
        			measureUnit = elementoFisico.getElement().getElementType().getMeasureUnit().getUnitCode();
        		String ird = null; 
        		if( elementoFisico.getIrd() != null )
        			ird = elementoFisico.getIrd(); 
        		Long quantity = null; 
        		
        		WarehouseElement elementoBodega = daoWarehouseElement.getWHouseElementByActualElementsCodes(whCode, countryId, serialCode, elementCode, elementStatusCode, isSerialized, ird, linkedSerialCode, modelCode, lote, measureUnit, quantity);
        		//Si el elemento fisico se encuentra en la Bodega.
        		if(elementoBodega != null){
        			
        			serializadoBodegaTmp = UtilsBusiness.copyObject(SerializedVO.class, elementoBodega.getSerialized()) ;
        			
        			//Se valida si el elemento es un vinculado, se verifica por medio del serial del elemento.
        			List<Serialized> serializeds = daoSerialized.getLinkedSerializedBySerialCode( serialCode,countryId );
        			if( !serializeds.isEmpty() ){
        				serializadoBodegaTmp = elementoFisico;
            			serializadoBodegaTmp.setElement( elementoBodega.getSerialized().getElement() );
            			serializadoBodegaTmp.setWareHouse( true );
            			serializadoBodegaTmp.setValidSerialLinked( true );
            			serializadoBodegaTmp.setRegistrationDate( elementoBodega.getSerialized().getRegistrationDate() );
        			}else{
        				serializadoBodegaTmp = elementoFisico;
            			serializadoBodegaTmp.setElement( elementoBodega.getSerialized().getElement() );
            			serializadoBodegaTmp.setWareHouse( true );
            			serializadoBodegaTmp.setValidSerialLinked( false );
            			serializadoBodegaTmp.setRegistrationDate( elementoBodega.getSerialized().getRegistrationDate() );
        			}
        			
        			//25-01-2011 jalopez, se reemplaza toda la validacion hecha en codigo por consultas.
        			/*
        			//Comparando los seriales de los elementos vinculados, si es que tienen, si no tienen vinculados el elemento es válido        			
        			Serialized physicalLinkedSerialized = elementoFisico.getSerialized();      	        			
        			if(physicalLinkedSerialized != null && elementoBodega.getSerialized() != null){        				
        				
        				if( ( physicalLinkedSerialized.getSerialCode() == null || physicalLinkedSerialized.getSerialCode().equals("") ) && ( physicalLinkedSerialized.getIrd() == null || physicalLinkedSerialized.getIrd().equals("") ) ){
                			throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
                		}
        				
        				serializadoBodegaTmp = UtilsBusiness.copyObject(SerializedVO.class, elementoBodega.getSerialized()) ;
        				serializadoBodegaTmp.setWareHouse(true);
        				
        				//Se comparan los seriales de los elementos vinculados
        				if( serializadoBodegaTmp.getSerialized().getSerialCode() != null ){
        					if( physicalLinkedSerialized.getSerialCode() != null && !physicalLinkedSerialized.getSerialCode().equals("") ){
        						if(serializadoBodegaTmp.getSerialized().getSerialCode().equals(physicalLinkedSerialized.getSerialCode())){
                					serializadoBodegaTmp.setValidSerialLinked(true);
                				}
        					} else{
        						serializadoBodegaTmp.setValidSerialLinked(false);
        					}
        				} else if( serializadoBodegaTmp.getSerialized().getIrd() != null ){
        					if(serializadoBodegaTmp.getSerialized().getIrd().equals(physicalLinkedSerialized.getIrd())){
            					serializadoBodegaTmp.setValidSerialLinked(true);
            				}else{
        						serializadoBodegaTmp.setValidSerialLinked(false);
        					}
        				} else{
        						serializadoBodegaTmp.setValidSerialLinked(false);
        				}
        			//Si ninguno de los dos (el físico y el registrado en el sistema tienen vinculados, entonces el elemento es válido
        			}else if(physicalLinkedSerialized == null && elementoBodega.getSerialized() == null) {
        				serializadoBodegaTmp = elementoFisico;
        				serializadoBodegaTmp.setElement(elementoBodega.getSerialized().getElement());
            			serializadoBodegaTmp.setWareHouse(true);
            			serializadoBodegaTmp.setValidSerialLinked(true);
            			serializadoBodegaTmp.setRegistrationDate( elementoBodega.getSerialized().getRegistrationDate() );
        			}else{
            			serializadoBodegaTmp = elementoFisico;
            			serializadoBodegaTmp.setElement(elementoBodega.getSerialized().getElement());
            			serializadoBodegaTmp.setWareHouse(true);
            			serializadoBodegaTmp.setValidSerialLinked(false);
            			serializadoBodegaTmp.setRegistrationDate( elementoBodega.getSerialized().getRegistrationDate() );
            		}
            		*/
        		//Si no se encuentra el elemento fisico en la Bodega
        		}else{
        			serializadoBodegaTmp = elementoFisico;
        			serializadoBodegaTmp.setWareHouse(false);
        			serializadoBodegaTmp.setValidSerialLinked(false);
        		}
        		comparativo.add(serializadoBodegaTmp);
        		
        	}
        	return comparativo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación validateSerializedInventory/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina validateSerializedInventory/WarehouseBusinessBean ==");
        }
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NotSerializedVO> validateNotSerializedInventory(Long whID,List<NotSerializedVO> noSerializadosFisicos, Long countryId)
			throws BusinessException {
		log.debug("== Inicia validateNotSerializedInventory/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(whID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(noSerializadosFisicos, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	boolean phisicalElementIsFilled = false;
        	List<NotSerializedVO> comparativo = new ArrayList<NotSerializedVO>();
        	//Recorrer la lista de elementos físicos no serializados
        	for(NotSerializedVO elementoFisico : noSerializadosFisicos){
        		phisicalElementIsFilled = false;
        		Element elementTmp = elementoFisico.getElement();
        		if(elementTmp == null){
        			//2011-01-14 jalopez
	        		//CC Comparacion de Inventarios, en caso de que un elemento no exista no se retorna exception.
	        		//Se ingresa el elemento como null para ser tratado en capa de presentacion.
        			
	        		//Consulta el elemento no serializado
	        		//elementTmp = daoElement.getElementsByElementTypeCode(elementoFisico.getCodeElement());
	        		//phisicalElementIsFilled = true;		        		
	        		if(elementTmp == null){
	        			log.error(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage()+",Elemento: "+elementoFisico.getCodeElement());
	        			//throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
	        		}
	        		elementoFisico.setElement(elementTmp);
        		}
        		
        		NotSerializedVO noSerializadoBodegaTmp = new NotSerializedVO();
        		//Consultar si para whID, el elemento no serializado tiene fecha de salida nula. Si no es porque no está en esa bodega
        		UtilsBusiness.assertNotNull(elementoFisico, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		UtilsBusiness.assertNotNull(elementoFisico.getElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
        		//25-01-2011 jalopez, se ajusta la consulta para que valide por todos los campos procesados en el xls.
        		String elementCode=elementoFisico.getElement().getElementType().getTypeElementCode();        		
        		String isSerialized=elementoFisico.getElement().getIsSerialized();
        		String elementStatusCode=elementoFisico.getElement().getElementStatus().getElementStatusCode();        		
        		//String measureUnitCode=elementoFisico.getElement().getMeasureUnit().getUnitCode();
        		String measureUnitCode=elementoFisico.getElement().getElementType().getMeasureUnit().getUnitCode();
        		Double actualQuantity=elementoFisico.getPhysicalQuantity();
        		//opcionales        		
        		String lote = null; 
        		if( elementoFisico.getElement().getLote() != null )
        			lote = elementoFisico.getElement().getLote(); 
        		String modelCode=null;
        		if( elementoFisico.getElement().getElementType().getElementModel().getModelCode() != null )
        			modelCode=elementoFisico.getElement().getElementType().getElementModel().getModelCode();
        		
        		//WarehouseElement elementoBodega = daoWarehouseElement.getWareHouseElementByActualWhAndWhAndElement(whID, elementoFisico.getElement().getId());
        		List<WarehouseElement> elementosBodega = daoWarehouseElement.getWHElementByActualElementNotSerialized(whID, countryId, lote, measureUnitCode, elementCode, isSerialized, elementStatusCode, modelCode, actualQuantity);
        		Double whQuantity = daoWarehouseElement.countWHElementByActualElementNotSerialized(whID, countryId, lote, measureUnitCode, elementCode, isSerialized, elementStatusCode, modelCode);
        		if( !elementosBodega.isEmpty() ){
        			WarehouseElement elementoBodega = elementosBodega.get(0);        			
        			noSerializadoBodegaTmp = UtilsBusiness.copyObject(NotSerializedVO.class, elementoBodega.getNotSerialized()) ;
        			noSerializadoBodegaTmp.setActualQuantity(whQuantity);
        			noSerializadoBodegaTmp.setPhysicalQuantity(elementoFisico.getPhysicalQuantity());
        			noSerializadoBodegaTmp.setDifferenceQuantity(elementoFisico.getPhysicalQuantity()-whQuantity);
        		}
        		
        		/*
        		//Si se encuentra en la bodega
        		boolean validateNulls = elementoBodega!=null 
										&&  elementoFisico.getElement().getMeasureUnit() != null
										&&  elementoFisico.getElement().getMeasureUnit().getId() != null &&  elementoFisico.getElement().getMeasureUnit().getId() > 0L
										&& elementoBodega.getNotSerialized() != null
										&& elementoBodega.getNotSerialized().getElement() != null
										&& elementoBodega.getNotSerialized().getElement().getMeasureUnit() != null
										&& elementoBodega.getNotSerialized().getElement().getMeasureUnit().getId() != null && elementoBodega.getNotSerialized().getElement().getMeasureUnit().getId() > 0L;
				//2011-01-14 jalopez
        		//CC Comparacion de Inventarios, en caso de que un elemento no exista no se retorna exception.
        		//Se ingresa el elemento como null para ser tratado en capa de presentacion.
        		if(validateNulls && !elementoBodega.getNotSerialized().getElement().getMeasureUnit().getId().equals( elementoFisico.getElement().getMeasureUnit().getId() )){
        			log.error( ErrorBusinessMessages.FILE_ELEMENTS_DONT_MATCH.getMessage() );
        			//throw new BusinessException(ErrorBusinessMessages.FILE_ELEMENTS_DONT_MATCH.getCode(), ErrorBusinessMessages.FILE_ELEMENTS_DONT_MATCH.getMessage());
        		} else if(validateNulls && elementoBodega.getNotSerialized().getElement().getMeasureUnit().getId().equals( elementoBodega.getNotSerialized().getElement().getMeasureUnit().getId() )){
        			UtilsBusiness.assertNotNull(elementoFisico.getElement().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			noSerializadoBodegaTmp = UtilsBusiness.copyObject(NotSerializedVO.class, elementoBodega.getNotSerialized()) ;
        			noSerializadoBodegaTmp.setActualQuantity(elementoBodega.getActualQuantity());
        			noSerializadoBodegaTmp.setPhysicalQuantity(elementoFisico.getPhysicalQuantity());
        			noSerializadoBodegaTmp.setDifferenceQuantity(elementoFisico.getPhysicalQuantity()-elementoBodega.getActualQuantity());
        			
        		//Si no se encuentra en la bodega
        		}
        		//2011-01-14 jalopez
        		//CC Comparacion de Inventarios, en caso de que un elemento no exista no se retorna exception.
        		//Se ingresa el elemento como null para ser tratado en capa de presentacion.
        		/*else{
        			if(! phisicalElementIsFilled){
        				elementTmp = daoElement.getElementByID(elementTmp.getId());
        			}
        			noSerializadoBodegaTmp.setElement(elementTmp);
        			noSerializadoBodegaTmp.setActualQuantity(Double.valueOf(0));
        			noSerializadoBodegaTmp.setPhysicalQuantity(elementoFisico.getPhysicalQuantity());
        			noSerializadoBodegaTmp.setDifferenceQuantity(elementoFisico.getPhysicalQuantity());
        		}*/
        		
        		comparativo.add(noSerializadoBodegaTmp);
        		
        	}
        	return comparativo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación validateNotSerializedInventory/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina validateNotSerializedInventory/WarehouseBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseVO> getWarehousesByDealerId(Long dealerId) throws BusinessException {
		 log.debug("== Inicia getWarehousesByDealerId/WarehouseBusinessBean ==");
	        try {
	        	UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        	List<Warehouse> wareHouses = daoWarehouse.getWarehousesByDealerId(dealerId);
	        	
	        	List<WarehouseVO> result = UtilsBusiness.convertList(wareHouses, WarehouseVO.class);
	        	generateWarehouseNames(result);
	        	return result;
	        } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación getAllWarehouses/WarehouseBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getWarehousesByDealerId/WarehouseBusinessBean ==");
	        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByDealerIdOwnAndRelated(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseVO> getWarehousesByDealerIdOwnAndRelated(
			Long dealerId, Long warehouseTypeId) throws BusinessException {
		log.debug("== Inicia getWarehousesByDealerIdOwnAndRelated/WarehouseBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	List<Warehouse> wareHouses = daoWarehouse.getWarehousesByDealerIdOwnAndRelated(dealerId, warehouseTypeId);
        	List<WarehouseVO> result = UtilsBusiness.convertList(wareHouses, WarehouseVO.class);
        	generateWarehouseNames(result);
        	return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehousesByDealerIdOwnAndRelated/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehousesByDealerIdOwnAndRelated/WarehouseBusinessBean ==");
        }
	}
	
	
	
	
	/**
	 * Metodo: Obtiene las bodegas filtradas por codigo de bodega,codigo de compa�ia,codigo
	 * de sucursal, codigo de cuadrilla y codigo para tipo de bodega
	 * @param wareHouseId identificador de la bodega
	 * @param companyId identificador de la compa�ia
	 * @param branchId identificador de la sucursal
	 * @param crewId identificador de la cuadrilla
	 * @param wareHouseTypeId identificador del tipo de bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseResponse, Lista de bodegas producto filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author garciniegas
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseResponse getWarehousesByComplexFilter( WarehouseVO wareHouseId,DealerVO companyId,DealerVO branchId,CrewVO crewId,
		   WarehouseTypeVO wareHouseTypeId, RequestCollectionInfo requestCollInfo)throws BusinessException{
		
		log.debug("== Inicia getWarehousesByComplexFilter/WarehouseBusinessBean ==");
        
		try {
			
        	Warehouse wareHouseIdPojo         = UtilsBusiness.copyObject(Warehouse.class, wareHouseId);
        	Dealer companyIdPojo              = UtilsBusiness.copyObject(Dealer.class, companyId); 
        	Dealer branchIdPojo               = UtilsBusiness.copyObject(Dealer.class, branchId); 
        	Crew crewIdPojo                   = UtilsBusiness.copyObject(Crew.class, crewId); 
        	WarehouseType wareHouseTypeIdPojo = UtilsBusiness.copyObject(WarehouseType.class, wareHouseTypeId); 
        	
        	WareHouseResponse response = daoWarehouse.getWarehousesByComplexFilter(wareHouseIdPojo, companyIdPojo, branchIdPojo, crewIdPojo, wareHouseTypeIdPojo, requestCollInfo);
        	response.setWareHouseVO(UtilsBusiness.convertList( response.getWareHouse(), WarehouseVO.class ));
        	response.setWareHouse(null);
        	generateWarehouseNames(response.getWareHouseVO());
		    return response;		         
			}catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la tarea getWarehousesByComplexFilter/WarehouseBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getWarehousesByComplexFilter/WarehouseBusinessBean ==");
	        }
	 }
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByDealerIdAndCrewId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseVO> getWarehousesByDealerIdAndCrewId(Long dealerId, Long crewId) throws BusinessException {
		 log.debug("== Inicia getWarehousesByDealerIdAndCrewId/WarehouseBusinessBean ==");
	        try {
	        	UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        	UtilsBusiness.assertNotNull(crewId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        	List<Warehouse> wareHouses = daoWarehouse.getWarehousesByDealerIdAndCrewId(dealerId,crewId);
	        	List<WarehouseVO> result = UtilsBusiness.convertList(wareHouses, WarehouseVO.class);
	        	generateWarehouseNames(result);
	        	return result;
	        } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación getWarehousesByDealerIdAndCrewId/WarehouseBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getWarehousesByDealerIdAndCrewId/WarehouseBusinessBean ==");
	        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByAdjustNotSerElemVriteria(java.lang.Long, java.lang.Long,  java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseResponse getWarehousesByAdjustNotSerElemCriteria(
			Long wareHouseId, Long dealerId, Long branchId, Long crewId,
			Long wareHouseTypeId, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getWarehousesByAdjustNotSerElemCriteria/WarehouseBusinessBean ==");
        try {
        	if(wareHouseTypeId == null || wareHouseTypeId.equals(CodesBusinessEntityEnum.WAREHOUSE_TYPE_AJUSTE.getIdEntity(WarehouseType.class.getName()))){
        		throw new BusinessException(ErrorBusinessMessages.INVALID_WH_TYPE.getCode(),ErrorBusinessMessages.INVALID_WH_TYPE.getMessage());
        	}
        	WareHouseResponse response = this.daoWarehouse.getWarehousesByAdjustNotSerElemCriteria(wareHouseId, dealerId, branchId, crewId, wareHouseTypeId, requestCollInfo);
        	List<Warehouse> wareHouses = response.getWareHouse();
        	response.setWareHouseVO( UtilsBusiness.convertList(wareHouses, WarehouseVO.class) );  
        	response.setWareHouse( null );
        	generateWarehouseNames(response.getWareHouseVO());
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehousesByAdjustNotSerElemCriteria/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehousesByAdjustNotSerElemCriteria/WarehouseBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByCustomerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseVO> getWarehousesByCustomerId(Long customerId) throws BusinessException {
		 log.debug("== Inicia getWarehouseByCustomerId/WarehouseBusinessBean ==");
	        try {
	        	UtilsBusiness.assertNotNull(customerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        	List<Warehouse> wareHouses = daoWarehouse.getWarehousesByCustomerId(customerId);
	        	List<WarehouseVO> result = UtilsBusiness.convertList(wareHouses, WarehouseVO.class);
	        	generateWarehouseNames(result);
	        	return result;
	        } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operacion getWarehouseByCustomerId/WarehouseBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getWarehouseByCustomerId/WarehouseBusinessBean ==");
	        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByCustomerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public SerializedVO getSerializedByElementId( ElementVO elementId )throws BusinessException
	{
		log.debug("== Inicia getSerializedByElementId/WarehouseBusinessBean ==");
		 try {
			    String errorCode    = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			    String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			 
	        	UtilsBusiness.assertNotNull( elementId , errorCode, errorMessage);
	        	UtilsBusiness.assertNotNull( elementId.getId() , errorCode, errorMessage);
	            
	        	return UtilsBusiness.copyObject( SerializedVO.class, daoSerialized.getSerializedByID( elementId.getId())  );
	        
		  } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operacion getSerializedByElementId/WarehouseBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getSerializedByElementId/WarehouseBusinessBean ==");
	        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWhTransitDealerOrCrew(co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseVO getWhTransitDealerOrCrew(WarehouseVO whSource)
			throws BusinessException {
		log.debug("== Inicia getWhTransitDealerOrCrew/WarehouseBusinessBean ==");
		try {
			Warehouse whTransit =  this.daoWarehouse.getWhTransitDealerOrCrew(whSource);
			WarehouseVO result =  UtilsBusiness.copyObject(WarehouseVO.class, whTransit);
			generateWarehouseName(result);
			return result;
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operacion getWhTransitDealerOrCrew/WarehouseBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhTransitDealerOrCrew/WarehouseBusinessBean ==");
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWhByCrewAndDealerAndWhType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseVO> getWhByCrewAndDealerAndWhType(Long dealerId, Long crewId, Long whTypeId) throws BusinessException {
		log.debug("== Inicia getWhByCrewAndDealerAndWhType/WarehouseBusinessBean ==");
        try {
            List<Warehouse> objPojo = daoWarehouse.getWhByCrewAndDealerAndWhType(dealerId,crewId,whTypeId);
            List<WarehouseVO> result = UtilsBusiness.convertList(objPojo, WarehouseVO.class);
            generateWarehouseNames(result);
            return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWhByCrewAndDealerAndWhType/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWhByCrewAndDealerAndWhType/WarehouseBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWhByCrewAndDealerAndWhType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseVO> getWhByCrewAndDealerAndWhTypeNotVirtual(Long dealerId, Long crewId, Long whTypeId) throws BusinessException {
		log.debug("== Inicia getWhByCrewAndDealerAndWhType/WarehouseBusinessBean ==");
        try {
            List<Warehouse> objPojo = daoWarehouse.getWhByCrewAndDealerAndWhTypeNotVirtual(dealerId,crewId,whTypeId);
            List<WarehouseVO> result = UtilsBusiness.convertList(objPojo, WarehouseVO.class);
            generateWarehouseNames(result);
            return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWhByCrewAndDealerAndWhType/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWhByCrewAndDealerAndWhType/WarehouseBusinessBean ==");
        }
	}



	public WarehouseVO getWarehouseBySerialCode(String warehouseCode) throws BusinessException{
		log.debug("== Inicia getWarehouseBySerialCode/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(warehouseCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Warehouse objPojo = daoWarehouse.getWarehouseBySerialCode(warehouseCode);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            WarehouseVO tmp = UtilsBusiness.copyObject(WarehouseVO.class, objPojo);
            generateWarehouseName(tmp);
            return tmp;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseByID/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseBySerialCode/WarehouseBusinessBean ==");
        }
	}


	@Override
	public WarehouseVO getWarehouseTypeByDealerId(Long dealerId,
			String warehouseType) throws BusinessException {

		log.debug("== Inicia getWarehouseTypeByDealerId/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(warehouseType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<Warehouse> lista = daoWarehouse.getWhByCrewAndDealerAndWhType(dealerId, null, warehouseType);
        	Warehouse objPojo = null;
        	if ( lista.size() > 0 ){
        		objPojo = lista.get(0);
        	}
            if (objPojo == null) {
            	WarehouseType eht = daoWarehouseType.getWarehouseTypeByCode(warehouseType);
            	if (eht == null){
            		Object[] params = new Object[1];			
    				params[0] = warehouseType;
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN427.getCode(),ErrorBusinessMessages.STOCK_IN427.getMessage(params),UtilsBusiness.getParametersWS(params));
            	}
            	Object[] params = new Object[1];			
				params[0] = eht.getWhTypeName();
            	throw new BusinessException(ErrorBusinessMessages.STOCK_IN423.getCode(),ErrorBusinessMessages.STOCK_IN423.getMessage(params),UtilsBusiness.getParametersWS(params));
            }
            WarehouseVO tmp = UtilsBusiness.copyObject(WarehouseVO.class, objPojo);
            generateWarehouseName(tmp);
            return tmp;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseTypeByDealerId/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseTypeByDealerId/WarehouseBusinessBean ==");
        }
	}


	@Override
	public WarehouseVO getWarehouseByDealerIDIBSCodeDocumentCustomer(
			Long warehouseId, String codeIBS, String documentCustomer)
			throws BusinessException {
		log.debug("== Inicia getWarehouseByDealerIDIBSCodeDocumentCustomer/WarehouseBusinessBean ==");
		Boolean existeParametro = Boolean.FALSE;
		if(warehouseId!=null){
			existeParametro = Boolean.TRUE;
		}
		if(codeIBS!=null&&!codeIBS.equals("")){
			existeParametro = Boolean.TRUE;
		}
		if(documentCustomer!=null&&!documentCustomer.equals("")){
			existeParametro = Boolean.TRUE;
		}
		if(!existeParametro){
			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
		
        try {
            Warehouse objPojo = daoWarehouse.getWarehouseByDealerIDIBSCodeDocumentCustomer(warehouseId, codeIBS, documentCustomer);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            WarehouseVO tmp = UtilsBusiness.copyObject(WarehouseVO.class, objPojo);
            generateWarehouseName(tmp);
            return tmp;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseByDealerIDIBSCodeDocumentCustomer/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseByDealerIDIBSCodeDocumentCustomer/WarehouseBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehouseByCountryIdbyDealerOrNotDealerID(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public List<WarehouseVO> getWarehouseByCountryIdAndOptionalDealerId(
			Long dealerID, Long countryID, Long userId, boolean searchSourceWh)
			throws BusinessException {
		log.debug("== Inicia getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseBusinessBean ==");
		UtilsBusiness.assertNotNull(countryID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		boolean searchSingleDealer = true, includeCrews = false, includeBranches = false;
		boolean includeCrewsForASingleDealer=false;
		
        try {
        	
        	User user = daoUser.getUserById(userId);
            if(dealerID == null || !(dealerID.longValue()>0L))
            	if(user.getDealer()!=null)
            		dealerID=user.getDealer().getId();
            
        	String roleTypeCode = user.getRol().getRoleType().getRoleTypeCode();
        	
        	if(roleTypeCode.equals(CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity())) {
        		searchSingleDealer = false;
        		includeCrews = true;
        		includeBranches = true;
        	}else if (searchSourceWh) {//consulta de ubicaciones origen
        	
        		if (roleTypeCode.equals(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_OPERATOR.getCodeEntity())) {//operador logistico
	            	searchSingleDealer = true;
	        		includeCrews = false;
	            } else if (roleTypeCode.equals(CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity())) {//analista de logistica dealer
	            	searchSingleDealer = true;
	        		includeCrews = true;
	            } else if (roleTypeCode.equals(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity())) {//analista de logistica DTV
	        		searchSingleDealer = false;
	        		includeCrews = false;
	        		includeBranches = true;
	            }
            
        	} else {//consulta de ubicaciones destino
        		
        		if (roleTypeCode.equals(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_OPERATOR.getCodeEntity())) {//operador logistico
 	            	searchSingleDealer = false;
 	            	includeBranches = true;
 	        		includeCrews = false;
 	            } else if (roleTypeCode.equals(CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity())||roleTypeCode.equals(CodesBusinessEntityEnum.ROLE_TYPE_CONTROL_TOWER.getCodeEntity())) {//analista de logistica dealer
	            	searchSingleDealer = false;
	        		includeCrews = true;
	        		includeBranches = true;
	        		includeCrewsForASingleDealer=true;
	            } else if (roleTypeCode.equals(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity())) {//analista de logistica DTV
	            	searchSingleDealer = false;
	        		includeCrews = false;
	        		includeBranches = true;
	            }
        		
        	}
        	
        	List<Long> warehouseIds = daoWarehouse.getWarehousesByCountryIdAndOptionalDealerId(dealerID, countryID, searchSingleDealer, includeCrews, includeBranches, includeCrewsForASingleDealer);

        	List<WarehouseVO> listWarehouse = null;
        	if(warehouseIds.size() > 0){
        		listWarehouse = this.daoWarehouse.getWarehouseByIds(warehouseIds, countryID);
        	}        	
        	
            return listWarehouse;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseBusinessBean ==");
        }
	}

	

	@Override
	public void genWareHouseName(WarehouseVO warehouse)
			throws BusinessException {
		log.debug("== Inicia getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseBusinessBean ==");
		try {
			this.generateWarehouseName(warehouse);
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseByCountryIdbyDealerOrNotDealerID/WarehouseBusinessBean ==");
		}
		
	}


	
	@Override
	public WarehouseElementStockDTO setNameWarehouseElementStockList( WarehouseElementStockDTO dto ) throws BusinessException {
		log.debug("== Inicia setNameWarehouseElementStockList/WarehouseBusinessBean ==");
        try {
			List<WarehouseElementStockVO> listaElementos = dto.getWhElementsStock();
			
			//Se recorre la lista de WarehouseElementStockVO obteniendo las bodedas y agregandolas a un lista
			List<Warehouse> listaBodegas = new ArrayList<Warehouse>();
			for (WarehouseElementStockVO warehouseElementStockVO : listaElementos){
				listaBodegas.add( warehouseElementStockVO.getWarehouse() );
			}
			
			//Se convierte y obtienen nombres
			List<WarehouseVO> listaBodegasVO = UtilsBusiness.convertList(listaBodegas, WarehouseVO.class);
	    	generateWarehouseNames(listaBodegasVO);
	    	
	    	//Se recorre la lista de WarehouseElementStockVO asignandole el nombre de la bodega
	    	int i = 0;
	    	for (WarehouseElementStockVO warehouseElementStockVO : listaElementos){
	    		WarehouseVO warehouseVO = (WarehouseVO) listaBodegasVO.get(i);
	    		warehouseElementStockVO.setWareHouseName( warehouseVO.getWarehouseName() );
	    		warehouseElementStockVO.setDealerBranchDepotPlusName( warehouseVO.getDealerBranchDepotPlusName() );
	    		warehouseElementStockVO.setDealerDepotPlusName(warehouseVO.getDealerDepotPlusName() );
	    		listaElementos.set(i, warehouseElementStockVO);
	    		i++;
	    	}
	    	
	    	dto.setWhElementsStock(listaElementos);
	    	
	    	return dto;
	    	
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación setNameWarehouseElementStockList/WarehouseBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina setNameWarehouseElementStockList/WarehouseBusinessBean ==");
	    }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWarehousesByDealerIdAndWhTypeCode(java.lang.Long, java.lang.String)
	 */
	public List<WarehouseVO> getWarehousesByDealerIdAndWhTypeCodeWithBranch(Long dealerId,
			String whTypeCode,
			boolean withBranch)  throws BusinessException {
		log.debug("== Inicia getWarehousesByDealerIdAndWhTypeCode/WarehouseBusinessBean ==");
        try {
			
        	List<WarehouseVO> result = UtilsBusiness.convertList(daoWarehouse.getWarehousesByDealerIdAndWhTypeCodeWithBranch(dealerId,whTypeCode,withBranch), WarehouseVO.class);
        	generateWarehouseNames(result);
        	return result;
	    	
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación getWarehousesByDealerIdAndWhTypeCode/WarehouseBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina getWarehousesByDealerIdAndWhTypeCode/WarehouseBusinessBean ==");
	    }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#createCustomerWarehouse(java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createCustomerWarehouse(String customerCode,String countryCode) throws BusinessException {
		log.debug("== Inicia createCustomerWarehouse/WarehouseBusinessBean ==");
		try {
			//Consulta la bodega del cliente, en caso que la encuentre, no la crea
			Warehouse customerWh = this.daoWarehouse.getCustomerWarehouseByCountry(customerCode, countryCode);
			if( customerWh != null && customerWh.getId().longValue() > 0 )
				return;
			Customer customer = this.customerDAO.getCustomerByCode(customerCode);
			//Se valida que el cliente exista en HSP
			if( customer == null || customer.getId().longValue() <= 0 ){
				Object[] params = {customerCode};
				List<String> paramsList = new ArrayList<String>();
				paramsList.add(customerCode);
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN475.getCode(),ErrorBusinessMessages.STOCK_IN475.getMessage(params),paramsList);
			}
			customerWh = new Warehouse();
			customerWh.setCustomerId( customer );
			customerWh.setCountry( customer.getCountry() );
			customerWh.setIsActive( CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity() );
			WarehouseType whTypeCustomer = this.daoWarehouseType.getWarehouseTypeByCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());
			//Valida que el tipo de bodega cliente exista
			if( whTypeCustomer == null || whTypeCustomer.getId().longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN476.getCode(),ErrorBusinessMessages.STOCK_IN476.getMessage());
			}
			customerWh.setWarehouseType(whTypeCustomer);
			customerWh.setWhResponsible(ApplicationTextEnum.EMPTY.getApplicationTextValue());
			customerWh.setResponsibleEmail(ApplicationTextEnum.EMPTY.getApplicationTextValue());
			StringBuilder sb = new StringBuilder();
			sb.append(buildWhCode(customerWh));
			customerWh.setWhCode( sb.toString());
			this.daoWarehouse.createWarehouse(customerWh);
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación createCustomerWarehouse/WarehouseBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina createCustomerWarehouse/WarehouseBusinessBean ==");
	    }
	}


	@Override
	public Warehouse getCustomerWarehouseByCountry(Long customerId,
			Long countryId) throws BusinessException {
		log.debug("== Inicia getCustomerWarehouseByCountry/WarehouseBusinessBean ==");
        UtilsBusiness.assertNotNull(customerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Warehouse objPojo = daoWarehouse.getCustomerWarehouseByCountry(customerId, countryId);
            return objPojo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getCustomerWarehouseByCountry/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerWarehouseByCountry/WarehouseBusinessBean ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void changeTheWhCodeForWareHousesByCrewCode(Long crewId)throws DAOServiceException, DAOSQLException, BusinessException{
		List<Warehouse> wareHouses = daoWarehouse.getWarehousesByCrewId(crewId);
		
		if(wareHouses!=null && !wareHouses.isEmpty()){
			
			for(Warehouse wh: wareHouses){
				String code = buildWhCode(wh);
				if(!daoWarehouse.existWhCode(code)){
					wh.setWhCode(code);
					daoWarehouse.updateWarehouse(wh);
				}else{
					Object[] errorParams=new Object[1];
					errorParams[0]=code;
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN503.getCode(), ErrorBusinessMessages.STOCK_IN503.getMessage(errorParams));
				}
			}
			
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Warehouse> getNoVirtualWarehousesByCrewId(Long crewId)throws DAOServiceException, DAOSQLException, BusinessException{
		return daoWarehouse.getNoVirtualWarehousesByCrewId(crewId);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal#getWareHouseAdjusmentTransit(java.lang.Long)
	 */
	public Warehouse getWareHouseAdjusmentTransit(Long countryId) throws BusinessException{
		
		log.debug("== Inicia getWareHouseAdjusmentTransit/WarehouseBusinessBean ==");
		try {
			SystemParameter systemParameter = null;
			systemParameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_DEALER_ADJUSMENT_TRANSIT.getCodeEntity(),countryId);
			if(systemParameter == null){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN506.getCode(),ErrorBusinessMessages.STOCK_IN506.getMessage());
			}
			
			Long dealerCode = Long.parseLong(systemParameter.getValue());
			Dealer dealer = daoDealer.getDealerByDealerCode(dealerCode);
			
			if(dealer == null){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN507.getCode(),ErrorBusinessMessages.STOCK_IN507.getMessage());
			}
			
			String whTypeCode = CodesBusinessEntityEnum.WAREHOUSE_TYPE_ADJUSTMENT_TRANSIT.getCodeEntity();
			if(whTypeCode == null || whTypeCode.equals("")){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN508.getCode(),ErrorBusinessMessages.STOCK_IN508.getMessage());
			}
			
			Warehouse warehouse = getWareHouseOrCreateIfNotExists(dealer.getId(),whTypeCode);
			
			return warehouse;
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación getWareHouseAdjusmentTransit/WarehouseBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina getWareHouseAdjusmentTransit/WarehouseBusinessBean ==");
	    }
		
	}
	
	/**
	 * Metodo trae el identificador de la Bodega, si no existe la ubicaciÃ³n de transito la crea de nuevo
	 * @param dealerId
	 * @param whTypeCode
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 * @author cduarte
	 */
	private Warehouse getWareHouseOrCreateIfNotExists(Long dealerId, String whTypeCode) throws DAOSQLException, DAOServiceException, BusinessException, PropertiesException{
		log.debug("== Inicia getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
		try{
			Warehouse warehouseFind =  daoWarehouse.getWarehousesByDealerIdAndWhTypeCode(dealerId, whTypeCode);
			if (warehouseFind == null){
				Dealer actualDealer = daoDealer.getDealerByID(dealerId);
				Warehouse warehouseCreate = new Warehouse();
				Warehouse warehouseTemp = new Warehouse();
				warehouseTemp.setDealerId(new Dealer());
				warehouseTemp.getDealerId().setId(dealerId);
				warehouseTemp.setWhResponsible(ApplicationTextEnum.EMPTY.getApplicationTextValue());
				warehouseTemp.setResponsibleEmail(ApplicationTextEnum.EMPTY.getApplicationTextValue());
				warehouseTemp.setIsActive(CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
				warehouseTemp.setCountry(actualDealer.getPostalCode().getCity().getState().getCountry());
				WarehouseType whTypeTrans = businessWarehouseType.getWarehouseTypeByCode(whTypeCode); 
				warehouseCreate = businessWarehouse.createWarehouseTransit( UtilsBusiness.copyObject(WarehouseVO.class, warehouseTemp), UtilsBusiness.copyObject(WarehouseTypeVO.class, whTypeTrans));
				return warehouseCreate;
			}else{
				return warehouseFind;
			}
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
	    }
		
	}
}
