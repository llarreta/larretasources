package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.AdjustmentStatus;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.TransferReason;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TransferReasonDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.reports.dto.AdjustmentElementDTO;

/**
 * 
 * Clase encargada del Cargue masivo de elementos serializados Ajuste Salida 
 * 
 * Fecha de Creación: 22/08/2011
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="FileProcessorLoadMassiveSerializedElementsAdjustmentTransferLocal",mappedName="ejb/FileProcessorLoadMassiveSerializedElementsAdjustmentTransferLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorLoadMassiveSerializedElementsAdjustmentTransfer extends BasicFileProcessor implements FileProcessorLoadMassiveSerializedElementsAdjustmentTransferLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorLoadMassiveSerializedElementsAdjustmentOutput.class);

	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal warehouseElementDAO;
	
	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;
	
	@EJB(name="TransferReasonDAOLocal", beanInterface=TransferReasonDAOLocal.class)
	private TransferReasonDAOLocal daoTransferReason;
	
	@EJB(name="AdjustmentBusinessBeanLocal", beanInterface=AdjustmentBusinessBeanLocal.class)
	private AdjustmentBusinessBeanLocal businessAdjustment;
	
	@EJB(name="AdjustmentElementsBusinessBeanLocal", beanInterface=AdjustmentElementsBusinessBeanLocal.class)
	private AdjustmentElementsBusinessBeanLocal businessAdjustmentElements;
	
	@EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal daoWarehouse;
	
	@EJB(name="AdjustmentDAOLocal", beanInterface=AdjustmentDAOLocal.class)
    private AdjustmentDAOLocal daoAdjustment;
    
    @EJB(name="AdjustmentStatusDAOLocal", beanInterface=AdjustmentStatusDAOLocal.class)
    private AdjustmentStatusDAOLocal daoAdjustmentStatus;
    
    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal daoSerialized;
    
    @EJB(name = "WarehouseBusinessBeanLocal", beanInterface = WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal warehouseBusinessBean;
    
    @EJB(name = "MovementElementBusinessBeanLocal", beanInterface = MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
    
    @EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal dAODealers;
	
	private Adjustment adjustment = null;
	
	private List<Dealer> dealerValids = null;
	
	private Boolean userSuperPriv = null;
	
	private User user = null;
	
	private Warehouse warehouseAdjusTransit = null;
	
	private MovementElementDTO dtoGenerics=null;

	private static int POS_SERIAL_ELEMENT = 0;
	private static int POS_COD_UBICACION_ORIGEN = 1;
	private static int POS_COD_UBICACION_DESTINO = 2;
	private static int POS_SERIAL_LINK_ELEMENT = 3;
	

	/**
	 * 
	 * Constructor: Contructor de la clase con la inicialización de las columnas
	 * @author waguilera
	 */
	public FileProcessorLoadMassiveSerializedElementsAdjustmentTransfer(){
		String[] columnTitles = new String[] {
				ApplicationTextEnum.SERIAL.getApplicationTextValue(),
				ApplicationTextEnum.ORIGIN_LOCATION_CODE.getApplicationTextValue(),
				ApplicationTextEnum.DESTINATION_LOCATION_CODE.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue()
			};
		setColumnTitles(columnTitles);
	}
	


	

	
 
	@Override
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(
			List<FileRecordDTO> fileData) {
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		this.adjustment = null;
		this.user = null;	
		userSuperPriv = false;
		try{
			//1.Consulta parametro de ajuste seleecionado
			UploadFileParam param = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_TRANSFER_REASON_ID.getCodeEntity());
			if (param==null){
				throw new BusinessException("Parametro de causal no encontrado");
			}
			
			//Consultar el transfer Reason 
			TransferReason transferReason = daoTransferReason.getTransferReasonByID(new Long(param.getParamValue().toString()));
			if (transferReason==null){
				throw new BusinessException("Causal de ajuste no encontrado");
			}
			
			//Validar que el ajuste sea de traslado
			if(!transferReason.getAdjustmentType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.ADJUSTMENT_TYPE_TRANSFER.getCodeEntity())){
				throw new BusinessException("El causal de ajuste debe ser de traslado");
			}
			
			//1.Consulta parametro de commentario
			UploadFileParam paramComment = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_ADJUSTMENT_COMENT_ID.getCodeEntity());
			if (paramComment==null){
				throw new BusinessException("Parametro de comentario no encontrado");
			}
			
			this.user =  this.getUploadFile().getUser();
			if(this.user==null){
				throw new BusinessException("Parametro de usuario no encontrado");
			}
			
			
			//Capturar el pais donde se realiza el proceso
			Long idCountry = this.getUploadFile().getCountry().getId();
			if(idCountry==null){
				throw new BusinessException("Parametro de pais no encontrado");
			}
			
			warehouseAdjusTransit = warehouseBusinessBean.getWareHouseAdjusmentTransit(idCountry);

			if((user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_BACKOFFICE.getCodeEntity())) 
				|| (user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity())) 
				|| (user.getRol().getRoleType().getRoleTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity()))){
				userSuperPriv = true;
			}
			if(user.getDealer()!=null){
				dealerValids = dAODealers.getDealerBranchesByDealerId(user.getDealer().getId());
			}
			
			this.adjustment = new Adjustment();
			this.adjustment.setComent(paramComment.getParamValue());
			this.adjustment.setTransferReason(transferReason);
			this.adjustment.setCreationUser(user);
			//Setear de manera automatica el estado del ajuste a creado
			this.adjustment.setAdjustmentStatus(new AdjustmentStatus(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_CREATE.getCodeEntity()));
			this.adjustment.setCountry(this.getUploadFile().getCountry());
			this.adjustment.setIsSerialized(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity());
			
			
			
			//Validaciones basicas de campos requeridos
			for(Iterator<FileRecordDTO> iterator = fileData.iterator(); iterator.hasNext();){
				FileRecordDTO dto = iterator.next();
				try{
					if(dto.getRowData().length<3){
						throw new BusinessException("Registro no valido. Se requiere Código de tipo de elemento 1, Serial elemento 1, Código de tipo de elemento 2, Serial elemento 2, Código de la Bodega");
					}

					String serialElement = dto.getRowData()[POS_SERIAL_ELEMENT];
					String codeWarehouseSource = dto.getRowData()[POS_COD_UBICACION_ORIGEN];
					String codeWarehouseTarget = dto.getRowData()[POS_COD_UBICACION_DESTINO];
					

					//Validaciones
					if (StringUtils.isBlank(serialElement)) {
						throw new BusinessException("Debe digitar el serial del elemento");
					}
					if (StringUtils.isBlank(codeWarehouseSource)) {
						throw new BusinessException("Debe digitar el código de la ubicación donde se encuentra el elemento");
					}
					if (StringUtils.isBlank(codeWarehouseTarget)) {
						throw new BusinessException("Debe digitar el código de la ubicación destino");
					}
					
				}catch (Exception e) {
					errors.add(buildFileDetailProcess(dto.getRow(), e.getMessage()));
					iterator.remove();//eliminar el registro para que no sea procesado posteriormente
				}
			}

		}catch (Exception e) {
			log.error(e);
			errors.add(buildFileDetailProcess(0, e.getMessage()));			
			fileData.clear();//no se debe procesar ningún registro
		}
		return errors;
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO)
			throws BusinessException {
		try{
			String serialElement = fileRecordDTO.getRowData()[POS_SERIAL_ELEMENT];
			String codeWarehouseSource = fileRecordDTO.getRowData()[POS_COD_UBICACION_ORIGEN];
			String codeWarehouseTarget = fileRecordDTO.getRowData()[POS_COD_UBICACION_DESTINO];
			String serialElementLink = fileRecordDTO.getRowData()[POS_SERIAL_LINK_ELEMENT];
			
			WarehouseElement warehouseElement  = warehouseElementDAO.getWarehouseElementBySerialActive(serialElement,this.getUploadFile().getCountry().getId());
			if(warehouseElement==null){
				throw new BusinessException("Elemento con serial ["+serialElement+"] no se encuentra en el sistema");
			}
			
			
			//Validar si el elemento esta en la bodega que se define en el archivo
			if(!warehouseElement.getWarehouseId().getWhCode().equalsIgnoreCase(codeWarehouseSource)){
				
				throw new BusinessException("Elemento con serial ["+serialElement+"] no se encuentra en la bodega con código ["+codeWarehouseSource+"]");
			}
			
			//Validar si la ubicacion  esta en el pais correspondiente
			if(warehouseElement.getWarehouseId().getCountry().getId().longValue()!=adjustment.getCountry().getId().longValue()){
				throw new BusinessException("La ubicación con código ["+codeWarehouseSource+"] no se encuentra en "+adjustment.getCountry().getCountryName()); 
			}
			
			//Validar que la ubicación origen no sea virtual
			if(warehouseElement.getWarehouseId().getWarehouseType().getIsVirtual().equalsIgnoreCase(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VIRTUAL.getCodeEntity())){
				throw new BusinessException("El Elemento con serial ["+serialElement+"] no se puede ajustar porque se encuentra en una ubicación de transito ");
			}
			
			//Validar que elemento que esta como principal en el archivo no este siendo vinculado por otro elemento
			List<Serialized> linkedElementS = daoSerialized.getLinkedSerializedBySerializedId(warehouseElement.getSerialized().getElementId());
			if(linkedElementS!=null && linkedElementS.size()>0){
				throw new BusinessException("El elemento que definió como principal en el archivo con serial ["+serialElement+"]  esta siendo vinculado por el elemento ["+linkedElementS.get(0).getSerialCode()+"]");
			}
			
			if(serialElementLink!=null && !serialElementLink.isEmpty()){
				if(warehouseElement.getSerialized().getSerialized()!=null){
					if(!warehouseElement.getSerialized().getSerialized().getSerialCode().trim().toUpperCase()
							.equalsIgnoreCase(serialElementLink.trim().toUpperCase())){
						throw new BusinessException("El elemento con serial ["+serialElement+"] no se encuentra vinculado con el elemento con serial  ["+serialElementLink+"]");
					}
				}
			}
			
			//Se valida que exista la ubicación destino y que no sea virtual
			Warehouse warehouseTargetVO = daoWarehouse.getWarehouseByCodeAndByCountry(codeWarehouseTarget.trim().toUpperCase(), adjustment.getCountry().getId().longValue());
			if(warehouseTargetVO==null){
				throw new BusinessException("La ubicación destino con código ["+codeWarehouseTarget+" no existe en el sistema");
			}
			
			//Validar que la ubicación destino no sea virtual
			if(warehouseTargetVO.getWarehouseType().getIsVirtual().equalsIgnoreCase(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VIRTUAL.getCodeEntity())){
				throw new BusinessException("El Elemento con serial ["+serialElement+"] no se puede ajustar porque porque la ubicación destino es de transito ");
			}
			
			checkDealerValid(warehouseElement.getWarehouseId().getDealerId(),codeWarehouseSource);
			checkDealerValid(warehouseTargetVO.getDealerId(),codeWarehouseTarget);
			
			
			//Validar que no sea la misma ubicación
			if(warehouseElement.getWarehouseId().getId().longValue() == warehouseTargetVO.getId().longValue()){
				throw new BusinessException("La ubicación destino no puede ser la misma de la ubicación origen");
			}
			
			
			AdjustmentElementDTO dto = new AdjustmentElementDTO();
			dto.setElementId(warehouseElement.getSerialized().getElementId());
			dto.setSerialCode(warehouseElement.getSerialized().getSerialCode());
			dto.setIdWarehouse(warehouseElement.getWarehouseId().getId());
			dto.setIdWarehouseDestination(warehouseTargetVO.getId());		
			
			if(this.adjustment != null && (this.adjustment.getId()==null || this.adjustment.getId()<=0L)){
				Long idAdjustment =  businessAdjustment.createAdjustment(UtilsBusiness.copyObject(AdjustmentVO.class, this.adjustment), this.getUploadFile().getUser().getId());
				this.adjustment = businessAdjustment.getAdjustmentByID(idAdjustment);
				if(adjustment.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
	        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(adjustment.getTransferReason().getMovTypeIn().getMovTypeCode(),
	        				                                                              adjustment.getTransferReason().getMovTypeOut().getMovTypeCode());
	        	}else{
	        		dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_IN.getCodeEntity(),
	        																			  CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_OUT.getCodeEntity());
	        	}
			}
			businessAdjustmentElements.adjustmentTransferElementSerialized(UtilsBusiness.copyObject(AdjustmentVO.class, this.adjustment), dto, this.getUploadFile().getUser(),warehouseAdjusTransit,dtoGenerics);
			
			
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==");
		}	
		
	}
	
	/**
	 * Metodo: Permite validar si el dealer puede realizar el ajuste a una bodega
	 * @param dealer
	 * @param codeWarehouse
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private void checkDealerValid(Dealer dealer, String codeWarehouse) throws BusinessException{
		boolean checkDealerAut = false;
		if(!userSuperPriv && dealer!=null){
			for (Dealer dealerValid : dealerValids) {
				if(dealerValid.getId().equals(dealer.getId())){
					checkDealerAut=true;
					break;
				}
			}
			if(!checkDealerAut)
				throw new BusinessException(" El usuario no tiene permisos para realizar ajustes en la bodega con código ["+codeWarehouse+"]");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<FileDetailProcess> doPostProcess() {
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		try{
			if(this.adjustment != null && this.adjustment.getId()!=null || this.adjustment.getId()>0L){
				if(this.adjustment.getTransferReason().getTransferReasonAuthorized().equalsIgnoreCase(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
	        		AdjustmentStatus adjustmentStatusProcessCompleted = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PROCESS.getCodeEntity());
	        		this.adjustment.setAdjustmentStatus(adjustmentStatusProcessCompleted);
	        		daoAdjustment.updateAdjustment(UtilsBusiness.copyObject(Adjustment.class,  this.adjustment));
	        	}else{
	        		AdjustmentStatus adjustmentStatusProcessPending = daoAdjustmentStatus.getAdjustmentStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PENDING.getCodeEntity());
	        		this.adjustment.setAdjustmentStatus(adjustmentStatusProcessPending);
	        		daoAdjustment.updateAdjustment(UtilsBusiness.copyObject(Adjustment.class,  this.adjustment));
	        	}
			}
		}catch (Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, "No se ha creado el ajuste"));
		}
		return errors;
		
	}



	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}


}
