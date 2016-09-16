package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import co.com.directv.sdii.ejb.business.file.data.LoadMassiveSerializedAdjusmentData;
import co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.AdjustmentElementsStatus;
import co.com.directv.sdii.model.pojo.AdjustmentStatus;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.TransferReason;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsStatusDAOLocal;
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
    
    @EJB(name = "AdjustmentElementsStatusDAOLocal", beanInterface = AdjustmentElementsStatusDAOLocal.class)
	private AdjustmentElementsStatusDAOLocal daoAdjustmentElementsStatus;
	
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
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> processRecordMassive(List<FileRecordDTO> fileData) throws BusinessException {
		List<LoadMassiveSerializedAdjusmentData> listaElementos = new ArrayList<LoadMassiveSerializedAdjusmentData>();
		Set<String> setSerialElement = new HashSet<String>();
		Map<String, Warehouse> mapaWareHouses = new HashMap<String, Warehouse>();

		// Validaciones.
		try {
			// 1.-Montamos la clase Auxiliar.
			for (FileRecordDTO fileDataAux : fileData) {
				LoadMassiveSerializedAdjusmentData dataAux = new LoadMassiveSerializedAdjusmentData(fileDataAux, POS_SERIAL_ELEMENT, POS_COD_UBICACION_ORIGEN, POS_COD_UBICACION_DESTINO, POS_SERIAL_LINK_ELEMENT);
				listaElementos.add(dataAux);
			}

			// 2.- Creamos aquellos mapas de datos necesarios.
			for (LoadMassiveSerializedAdjusmentData data : listaElementos) {
				setSerialElement.add(data.getSerialElement());
			}

			// 2.-Buscamos los elementos a partir de los seriales.
			Map<String, WarehouseElement> lWarehouseElement = this.fillMapElements(setSerialElement);

			// 3.- Asignamos a cada Objeto su elemento de BBDD Y REALIZAMOS
			// VALIDACIONES.
			for (LoadMassiveSerializedAdjusmentData sAux : listaElementos) {
				WarehouseElement wAux = lWarehouseElement.get(sAux.getSerialElement());
				if (wAux == null) {
					sAux.setError(true);
					sAux.setsError("Elemento con serial [" + sAux.getSerialElement() + "] no se encuentra en el sistema");
				} else {
					sAux.setWarehouseElement(wAux);
					// Validar si el elemento esta en la bodega que se define en el archivo
					if (!wAux.getWarehouseId().getWhCode().equalsIgnoreCase(sAux.getCodeWarehouseSource())) {
						sAux.setError(true);
						sAux.setsError("Elemento con serial [" + sAux.getSerialElement() + "] no se encuentra en la bodega con código [" + sAux.getCodeWarehouseSource() + "]");
					}
					// Validar si la ubicacion esta en el pais correspondiente
					if (!sAux.isError() && wAux.getWarehouseId().getCountry().getId().longValue() != adjustment.getCountry().getId().longValue()) {
						sAux.setError(true);
						sAux.setsError("La ubicación con código [" + sAux.getCodeWarehouseSource() + "] no se encuentra en " + adjustment.getCountry().getCountryName());
					}

					// Validar que la ubicación origen no sea virtual
					if (!sAux.isError() && wAux.getWarehouseId().getWarehouseType().getIsVirtual().equalsIgnoreCase(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VIRTUAL.getCodeEntity())) {
						sAux.setError(true);
						sAux.setsError("El Elemento con serial [" + sAux.getSerialElement() + "] no se puede ajustar porque se encuentra en una ubicación de transito ");
					}

					// Validar que elemento que esta como principal en el archivo no este siendo vinculado por otro elemento
					List<Serialized> linkedElementS = daoSerialized.getLinkedSerializedBySerializedId(wAux.getSerialized().getElementId());
					if (!sAux.isError() && linkedElementS != null && linkedElementS.size() > 0) {
						sAux.setError(true);
						sAux.setsError("El elemento que definió como principal en el archivo con serial [" + sAux.getSerialElement() + "]  esta siendo vinculado por el elemento [" + linkedElementS.get(0).getSerialCode() + "]");
					}

					if (!sAux.isError() && sAux.getSerialElementLink() != null && !sAux.getSerialElementLink().isEmpty()) {
						if (wAux.getSerialized().getSerialized() != null) {
							if (!wAux.getSerialized().getSerialized().getSerialCode().trim().toUpperCase().equalsIgnoreCase(sAux.getSerialElementLink().trim().toUpperCase())) {
								sAux.setError(true);
								sAux.setsError("El elemento con serial [" + sAux.getSerialElement() + "] no se encuentra vinculado con el elemento con serial  [" + sAux.getSerialElementLink() + "]");
							}
						}
					}

					// Se valida que exista la ubicación destino y que no sea virtual
					String sWareHouseTargetCode = sAux.getCodeWarehouseTarget().trim().toUpperCase();
					 // Si no existe la WareHouse en el mapa se busca.
					if (!sAux.isError() && !mapaWareHouses.containsValue(sWareHouseTargetCode)) {
						Warehouse warehouseTargetVO = this.getWarehouseByCodeAndByCountry(sWareHouseTargetCode, adjustment.getCountry().getId().longValue());
						mapaWareHouses.put(sWareHouseTargetCode, warehouseTargetVO);
					}
					Warehouse warehouseTargetVO = mapaWareHouses.get(sWareHouseTargetCode);
					if (!sAux.isError() && warehouseTargetVO == null) {
						sAux.setError(true);
						sAux.setsError("La ubicación destino con código [" + sWareHouseTargetCode + " no existe en el sistema");
					} else if (!sAux.isError()) {
						if (warehouseTargetVO.getWarehouseType().getIsVirtual().equalsIgnoreCase(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VIRTUAL.getCodeEntity())) {
							sAux.setError(true);
							sAux.setsError("El Elemento con serial [" + sAux.getSerialElement() + "] no se puede ajustar porque porque la ubicación destino es de transito ");
						}
						try {
							if (!sAux.isError()) {
								checkDealerValid(sAux.getWarehouseElement().getWarehouseId().getDealerId(), sAux.getCodeWarehouseSource());
								checkDealerValid(warehouseTargetVO.getDealerId(), sAux.getCodeWarehouseTarget());
							}
						} catch (BusinessException bex) {
							sAux.setError(true);
							sAux.setsError(bex.getMessage());
						}

						// Validar que no sea la misma ubicación
						if (!sAux.isError() && (sAux.getWarehouseElement().getWarehouseId().getId().longValue() == warehouseTargetVO.getId().longValue())) {
							sAux.setError(true);
							sAux.setsError("La ubicación destino no puede ser la misma de la ubicación origen");
						}
						
						if(!sAux.isError()){
							sAux.setWarehouseTargetVO(warehouseTargetVO);
						}
					}
				}
			}

		} catch (Throwable t) {
			log.debug("== Error al tratar de ejecutar la operación validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==", t);
			log.debug("== Termina validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==");
			throw this.manageException(t);
		}

		//Buscamos en la BBDD los datos comunes.
		MovementType movementTypeE = new MovementType();
		MovementType movementTypeS = new MovementType();
		RecordStatus recordStatusU = new RecordStatus();
		RecordStatus recordStatusH = new RecordStatus();
		AdjustmentElementsStatus adjustmentElementsStatus = new AdjustmentElementsStatus();
		boolean allElementsFailed = false;
		int nElemFallidos = 0;
		
		for (LoadMassiveSerializedAdjusmentData dataAux : listaElementos) {
			if(dataAux.isError()){
				nElemFallidos++;
			}
		}
		
		if(nElemFallidos == listaElementos.size()){
			allElementsFailed = true;
		}
		
		if(!allElementsFailed){
			try {
				
				if (this.adjustment != null && (this.adjustment.getId() == null || this.adjustment.getId() <= 0L)) {
					Long idAdjustment = this.createAdjustment(UtilsBusiness.copyObject(AdjustmentVO.class, this.adjustment), this.getUploadFile().getUser().getId());
					this.adjustment = this.getAdjustmentByID(idAdjustment);
					if (adjustment.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())) {
						businessMovementElement.fillData(adjustment.getTransferReason().getMovTypeIn().getMovTypeCode(), adjustment.getTransferReason().getMovTypeOut().getMovTypeCode(), movementTypeE, movementTypeS, recordStatusU, recordStatusH);
					} else {
						businessMovementElement.fillData(CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_IN.getCodeEntity(), CodesBusinessEntityEnum.MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_OUT.getCodeEntity(), movementTypeE, movementTypeS, recordStatusU, recordStatusH);
					}
					dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatusMassive(movementTypeE, movementTypeS, recordStatusU, recordStatusH);
				}
				
				//Buscamos los estados.
				if(adjustment.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_YES.getCodeEntity())){
	        		adjustmentElementsStatus = this.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
	        	}else{
	        		adjustmentElementsStatus = this.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PROCESS.getCodeEntity());
	        	}
			} catch (BusinessException bex) {
				//Si esto falla se ponen todas como erroneas.
				for (LoadMassiveSerializedAdjusmentData dataAux : listaElementos) {
					if(!dataAux.isError()){
						dataAux.setError(true);
						dataAux.setsError(bex.getMessage());
					}
				}
			} catch (PropertiesException pex) {
				log.debug("== Error al tratar de ejecutar la operación validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==", pex);
				log.debug("== Termina validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==");
				throw this.manageException(pex);
			} catch (DAOSQLException dex){
				log.debug("== Error al tratar de ejecutar la operación validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==", dex);
				log.debug("== Termina validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==");
				throw this.manageException(dex);
			}catch(DAOServiceException dex){
				log.debug("== Error al tratar de ejecutar la operación validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==", dex);
				log.debug("== Termina validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==");
				throw this.manageException(dex);
			}
	
			//Pasadas las validaciones, procedemos a procesar aquellas filas que no tienen errores.
			try {
				for (LoadMassiveSerializedAdjusmentData dataAux : listaElementos) {
					try{
						if(!dataAux.isError()){
							processAdjustmentMassive(dataAux,movementTypeE,movementTypeS,recordStatusU,recordStatusH,adjustmentElementsStatus);
						}
					}catch(BusinessException bex){
						dataAux.setError(true);
						dataAux.setsError(bex.getMessage());
					}catch(PropertiesException pex){
						dataAux.setError(true);
						dataAux.setsError(pex.getMessage());
					}
				}
			} catch (Throwable t) {
				log.debug("== Error al tratar de ejecutar la operación validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==", t);
				throw this.manageException(t);
			} finally {
				log.debug("== Termina validateFile/FileProcessorLoadMassiveSerializedElementsAdjustmentOutput ==");
			}
		}
		
		//Mapeamos la salida.
		List<Object> listaSalida = new ArrayList<Object>();
		listaSalida.addAll(listaElementos);
		
		return listaSalida;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public AdjustmentElementsStatus getAdjustmentElementsStatusByCode(String sCode) throws DAOServiceException, DAOSQLException {
		return daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCodeRequired(sCode);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Adjustment getAdjustmentByID(Long idAdjustment) throws BusinessException {
		return businessAdjustment.getAdjustmentByIDNew(idAdjustment);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long createAdjustment(AdjustmentVO copyObject, Long id) throws BusinessException {
		return businessAdjustment.createAdjustment(copyObject, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Warehouse getWarehouseByCodeAndByCountry(String sWareHouseTargetCode, long longValue) throws DAOServiceException, DAOSQLException {
		return daoWarehouse.getWarehouseByCodeAndByCountry(sWareHouseTargetCode, adjustment.getCountry().getId().longValue());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Map<String, WarehouseElement> fillMapElements(Set<String> setSerialElement) throws DAOServiceException, DAOSQLException{
		return warehouseElementDAO.getWarehouseElementBySerialActiveMassive(setSerialElement, this.getUploadFile().getCountry().getId());
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processAdjustmentMassive(LoadMassiveSerializedAdjusmentData dataAux, MovementType movementTypeE, MovementType movementTypeS, 
			RecordStatus recordStatusU, RecordStatus recordStatusH, AdjustmentElementsStatus adjustmentElementsStatus) throws BusinessException, PropertiesException{
		AdjustmentElementDTO dto = new AdjustmentElementDTO();
		dto.setElementId(dataAux.getWarehouseElement().getSerialized().getElementId());
		dto.setSerialCode(dataAux.getWarehouseElement().getSerialized().getSerialCode());
		dto.setIdWarehouse(dataAux.getWarehouseElement().getWarehouseId().getId());
		dto.setIdWarehouseDestination(dataAux.getWarehouseTargetVO().getId());

		businessAdjustmentElements.adjustmentTransferElementSerializedMassive(UtilsBusiness.copyObject(AdjustmentVO.class, this.adjustment), dto,
				this.getUploadFile().getUser(), warehouseAdjusTransit, dtoGenerics,dataAux,adjustmentElementsStatus);
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
