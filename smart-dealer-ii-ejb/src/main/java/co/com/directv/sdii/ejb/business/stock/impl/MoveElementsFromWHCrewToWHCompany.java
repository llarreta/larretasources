package co.com.directv.sdii.ejb.business.stock.impl;

import java.sql.Timestamp;
import java.util.Date;
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
import co.com.directv.sdii.ejb.business.stock.MoveElementsFromWHCrewToWHCompanyLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.MassiveMovement;
import co.com.directv.sdii.model.pojo.MassiveMovementDealer;
import co.com.directv.sdii.model.pojo.ProcessStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

/**
 * 
 * Clase encargada de realizar en un hilo, 
 * el traslado masivo de elementos de cuadrillas a bodegas
 * disponibles. 
 * 
 * Fecha de Creación: 19/08/2011
 * @author hcorredor <a href="mailto:hcorredor@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="MoveElementsFromWHCrewToWHCompanyLocal",mappedName="ejb/MoveElementsFromWHCrewToWHCompanyLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MoveElementsFromWHCrewToWHCompany extends BusinessBase implements MoveElementsFromWHCrewToWHCompanyLocal{

	
	private final static Logger log = UtilsBusiness.getLog4J(MoveElementsFromWHCrewToWHCompany.class);
	
	@EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal daoWarehouse;
	
	@EJB(name="CrewsDAOLocal",beanInterface=CrewsDAOLocal.class)
    private CrewsDAOLocal daoCrew;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
    private WarehouseElementBusinessBeanLocal businessWhElement;
	
	@EJB(name="MassiveMovementDAOLocal", beanInterface=MassiveMovementDAOLocal.class)
    private MassiveMovementDAOLocal daoMassiveMovement;
	
	@EJB(name="ProcessStatusDAOLocal", beanInterface=ProcessStatusDAOLocal.class)
	private ProcessStatusDAOLocal processStatusDao;
	
	@EJB(name="MassiveMovementDealerDAOLocal", beanInterface=MassiveMovementDealerDAOLocal.class)
    private MassiveMovementDealerDAOLocal daoMassiveMovementDealer;
	
	@EJB(name="MovementElementBusinessBeanLocal", beanInterface=MovementElementBusinessBeanLocal.class)
    private MovementElementBusinessBeanLocal businessMovementElement;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
	
	/**
	 * 
	 * Metodo: Realiza el movimiento de elementos de las cuadrillas de las companias a 
	 * la bodega de las compañias, segun el tipo de Bodega.
	 * @param companies
	 * @param usr
	 * @param WHTypeCode, tipo de Bodega.
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void moveElementsFromWHCrewToWHCompany(List<DealerVO> companies, User usr, String WHTypeCode, MassiveMovement process) throws BusinessException {
		
		log.debug("== Inicia moveElementsFromWHCrewToWHCompany/MoveElementsFromWHCrewToWHCompany ==");
		UtilsBusiness.assertNotNull(companies, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	
		try{
			
			//  Consulta las bodegas de los crew -cuadrillas- y pasarla a la bodega de 
			//  disponibles de la compania S01
			//  01. Consultar las cuadrillas de las compania.
			for (DealerVO company: companies){
				
				//Ubicar la bodega destino
				List<Warehouse> warehouseTargets =   daoWarehouse.getWhByCrewAndDealerAndWhType(company.getId(),null, WHTypeCode);
				if (warehouseTargets == null || warehouseTargets.size() < 1 ){
					storeMassiveMovementDealersMessages(company, process, ApplicationTextEnum.COMPANY_IDENTIFIER.getApplicationTextValue()+": ["+company.getId()+"] "+ApplicationTextEnum.NO_WAREHOUSE_TYPE.getApplicationTextValue()+WHTypeCode);
					continue;
				}
				
				for (Warehouse warehouseTarget: warehouseTargets){
					List<Crew> crewsList = daoCrew.getCrewsByDealerId(company.getId());
					if (crewsList == null || crewsList.size() < 1 ){
						storeMassiveMovementDealersMessages(company, process, ApplicationTextEnum.COMPANY_IDENTIFIER.getApplicationTextValue()+": ["+company.getId()+"] "+ApplicationTextEnum.NO_ASSOCIATED_CREWS.getApplicationTextValue());
						continue;
					}
					for (Crew crew: crewsList){
						//Ubicar la bodega Origen, de donde salen los elementos de la cuadrilla.
						List<Warehouse> warehouseSources = daoWarehouse.getWhByCrewAndWhTypeCode(crew.getId(), WHTypeCode);
						if (warehouseSources == null || warehouseSources.size() < 1){
							storeMassiveMovementDealersMessages(company, process, ApplicationTextEnum.CREW_IDENTIFIER.getApplicationTextValue()+": ["+company.getId()+"] "+ApplicationTextEnum.NO_WAREHOUSE_TYPE.getApplicationTextValue()+" " + WHTypeCode);
							continue;
						}
						//02. Toma los elementos de la Bodega S01-Stock
						for (Warehouse warehouseSource: warehouseSources){
							List<WarehouseElement> warehouseElements = daoWarehouseElement.getWarehouseElementsByWHIdSourceAndLastRecordStatus(warehouseSource.getId(),true);
						
							if (warehouseElements == null || warehouseElements.size() < 1 ){
								storeMassiveMovementDealersMessages(company, process,ApplicationTextEnum.CREW_IDENTIFIER.getApplicationTextValue()+": ["+company.getId()+"] "+ApplicationTextEnum.NO_ELEMENTS_WAREHOUSE_ONE.getApplicationTextValue());
								continue;
							}
							//03. Trasladar los elementos de la bodega de las cuadrillas a la Bodega destino de la Compania.
							//Colocar esto en una nueva transacción, el contenedor genera error por timeout,
							//Por tal razon hay que realizar commit por cada movimiento de bodegas entre cuadrillas a bodega de la
							//compania.
							moveElementsFromCrewToCompany(warehouseElements, warehouseTarget, warehouseSource, usr);
						}
					}
				}
			}
			
			//Cambia el estado del proceso de mover los elementos
			ProcessStatus processStatusF = processStatusDao.getProcessStatusByCode(CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_CORRECT_FINISHED.getCodeEntity());
			process.setProcessStatus(processStatusF);
			process.setFinishProcessDate(new Timestamp(new Date().getTime()));
			daoMassiveMovement.updateMassiveMovement(process);
			
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación moveElementsFromWHCrewToWHCompany/MoveElementsFromWHCrewToWHCompany ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveElementsFromWHCrewToWHCompany/MoveElementsFromWHCrewToWHCompany ==");
		}
		
		
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void moveElementsFromCrewToCompany(List<WarehouseElement> warehouseElements,Warehouse warehouseTarget, Warehouse warehouseSource, User user) throws BusinessException, PropertiesException{
		
		MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_ENTRY.getCodeEntity(), 
				                                                                                 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_EXIT.getCodeEntity());
		for (WarehouseElement wHE: warehouseElements ){
			if (wHE.getSerialized() != null){
				
				/*ElementMovementDTO dto =new ElementMovementDTO(warehouseSource.getId(), 
				 *                                               warehouseTarget.getId(), 
				 *                                               wHE.getSerialized().getElementId(), 
				 *                                               CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_ENTRY.getCodeEntity(), 
				 *                                               CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_EXIT.getCodeEntity(), 
				 *                                               null, 
				 *                                               null, 
				 *                                               wHE.getSerialized().getSerialCode(), 
				 *                                               null, 
				 *                                               false, 
				 *                                               null,
				 *                                               null); 
				businessWhElement.moveElementToWareHouse(dto);*/
				MovementElementDTO dtoMovement = new MovementElementDTO(user, 
						UtilsBusiness.copyObject(WarehouseVO.class, warehouseSource), 
						UtilsBusiness.copyObject(WarehouseVO.class, warehouseTarget),
						wHE.getSerialized(),
						null,
						null,
						dtoGenerics.getMovTypeCodeE(),
						dtoGenerics.getMovTypeCodeS(),
						dtoGenerics.getRecordStatusU(), 
						dtoGenerics.getRecordStatusH(), 
						true, 
						CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
			}else{
				/*ElementMovementDTO dto = new ElementMovementDTO(warehouseSource.getId(), 
						                                        warehouseTarget.getId(), 
						                                        wHE.getNotSerialized().getElement().getElementType().getId(), 
						                                        wHE.getActualQuantity(), 
						                                        CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_ENTRY.getCodeEntity(), 
						                                        CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_BETWEEN_WH_EXIT.getCodeEntity(), 
						                                        false, 
						                                        null, 
						                                        null);
				businessWhElement.moveNotSerializedElementBetweenWareHouses(dto);*/
				MovementElementDTO dtoMovement = new MovementElementDTO(user,
						UtilsBusiness.copyObject(WarehouseVO.class,  warehouseSource), 
						UtilsBusiness.copyObject(WarehouseVO.class,  warehouseTarget), 
						null, 
						wHE.getNotSerialized().getElement().getElementType().getId(), 
						wHE.getNotSerialized().getElement().getElementType().getTypeElementCode(),
						null, 
						null, 
						dtoGenerics.getMovTypeCodeE(), 
						dtoGenerics.getMovTypeCodeS(), 
						dtoGenerics.getRecordStatusU(),
						dtoGenerics.getRecordStatusH(),
						wHE.getActualQuantity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMovement);
				
			}
		}
		
	}
	
	/**
	 * 
	 * Metodo: Realiza el almacenamiento de los mensajes por dealer de 
	 * los movimientos de las cuadrillas a la Bodega de la compania.
	 * @author hcorredor
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void storeMassiveMovementDealersMessages(Dealer dealer,MassiveMovement massiveMovement, String movementMessage ) throws DAOServiceException, DAOSQLException{
		MassiveMovementDealer objPojo =  new MassiveMovementDealer();
		objPojo.setDealer(dealer);
		objPojo.setMassiveMovement(massiveMovement);
		objPojo.setMovementMessage(movementMessage);
        daoMassiveMovementDealer.createMassiveMovementDealer(objPojo);
	}
	
}
