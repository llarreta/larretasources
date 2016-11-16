/**
 * Req 0067 Confirmacion masiva de remisiones en HSP
 */
package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;

import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.ReferenceStatus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.BasicProcessorAuxData;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceElementItemBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.persistence.dao.file.FileDetailProcessDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * @author ialessan
 *
 */
@Stateless(name="FileProcessorConfirmItemsLoadMassiveReference",mappedName="ejb/FileProcessorConfirmItemsLoadMassiveReference")
@TransactionAttribute(TransactionAttributeType.REQUIRED) 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorConfirmItemsLoadMassiveReference extends BasicFileProcessor implements FileProcessorConfirmItemsLoadMassiveReferenceLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorConfirmItemsLoadMassiveReference.class);
	
	@EJB(name="ReferenceElementItemBusinessBeanLocal", beanInterface=ReferenceElementItemBusinessBeanLocal.class)
	private ReferenceElementItemBusinessBeanLocal businessReferenceElementItem;

	@EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
	private ReferenceBusinessBeanLocal businessReference;
	
	@EJB(name="ElementTypeBusinessBeanLocal", beanInterface=ElementTypeBusinessBeanLocal.class)
	private ElementTypeBusinessBeanLocal businessElementType;
	
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
	
	 @EJB(name="FileDetailProcessDAOLocal", beanInterface=FileDetailProcessDAOLocal.class)
	 private FileDetailProcessDAOLocal fileDetailProcessDAO ;
    
	private User user;

	private static int POS_NUM_RN = 0;
	private static int POS_ELEMENT_TYPE_CODE = 1;
	private static int POS_SERIAL = 2;
	private static int POS_LINKED_SERIAL = 3;
	private static int POS_QUANTITY = 4;
	
	public FileProcessorConfirmItemsLoadMassiveReference(){
		String[] columnTitles = new String[] {
				ApplicationTextEnum.CODE_REFERRAL.getApplicationTextValue(),
				ApplicationTextEnum.ELEMENT_TYPE_CODE.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue(),
				ApplicationTextEnum.QUANTITY.getApplicationTextValue()
		};
		setColumnTitles(columnTitles);
	}
	
	/** 
	 *@see co.com.directv.sdii.ejb.business.file.IBasicFileProcessor#processRecord(co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO)
	 * Procesa un registro del archivo. Cada fila puede contener elementos serializados como no serializados.<br/>
	 * Los Serializados deben contener: RN_NUMBER, ELEMENT_TYPE_CODE, SERIAL, SERIAL_LINKED.<br/>
	 * Los No Serializados deben contener: RN_NUMBER, ELEMENT_TYPE_CODE, QUANTITY.<br/>
	 */
	/*@Override
	public void processRecord(FileRecordDTO fileRecordDTO)
			throws BusinessException {
		log.debug("== Inicio processRecord/FileProcessorConfirmItemsLoadMassiveReference ==");
		
		int filaProcesada = 0;
		
		try{			
			String rnNumber = fileRecordDTO.getRowData()[POS_NUM_RN];
			String elementTypeCode = fileRecordDTO.getRowData()[POS_ELEMENT_TYPE_CODE];
			String serialCode = fileRecordDTO.getRowData()[POS_SERIAL];
			String serialCodeLinked = fileRecordDTO.getRowData()[POS_LINKED_SERIAL];
			String quantityStr = fileRecordDTO.getRowData()[POS_QUANTITY];
			
			if(rnNumber == null || rnNumber.trim().isEmpty()){
				throw new BusinessException("El Número RN es requerido");
			}
			if(elementTypeCode == null || elementTypeCode.trim().isEmpty()){
				throw new BusinessException("El tipo de elemento es requerido");
			}

			ElementTypeVO elementTypeVO = businessElementType.getElementTypeByCode(elementTypeCode);
			if(elementTypeVO==null){
				throw new BusinessException("El tipo de elemento informado es inválido");
			}
			
			//lista de remisiones segun RN, menos las que se encuentren eliminadas
			List<ReferenceVO> referenceVOs = businessReference.getReferenceByRNNumber(rnNumber,CodesBusinessEntityEnum.REFERENCE_STATUS_DELETED.getCodeEntity());
			if (referenceVOs.size()>1)
				throw new BusinessException("El número de RN ya se encuentra asociado a otra remisión");
			ReferenceVO referenceVO = referenceVOs.get(0);
			
			if (!referenceVO.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity()) && 
				!referenceVO.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity())	) 
				throw new BusinessException("El estado de la remisión debe ser EN CREACION o CREADA");
				
			if(elementTypeVO.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity()) && serialCode != null && !serialCode.trim().isEmpty() ){
				//serializados.
				businessReferenceElementItem.addElementSerialized(serialCode, serialCodeLinked, referenceVO.getId(), user);
			}else if(elementTypeVO.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity()) && quantityStr != null && !quantityStr.trim().isEmpty()){
				//No Serializados.
				Double quantity = 0D;
				if(!NumberUtils.isNumber(quantityStr)){
					throw new BusinessException("La cantidad debe ser numerica");
				}else{
					quantity = Double.parseDouble(quantityStr);
				}
				
				boolean isPrepaid = referenceVO.getIsPrepaidRef().equals(CodesBusinessEntityEnum.REFERENCE_PREPAID.getCodeEntity());
				ReferenceElementItemsResponse referenceElementItemsResponse = businessReferenceElementItem.getElementNotSerializedFromWarehouse(null,referenceVO.getId(),isPrepaid, elementTypeVO.getId(), null);
				List<ReferenceElementItemVO> refElementItemVOList = referenceElementItemsResponse.getReferenceElementItemsVO();
				if(!refElementItemVOList.isEmpty()){
					refElementItemVOList.get(0).setRefQuantity(quantity);
					Element elem = new Element();
					elem.setElementType(elementTypeVO);
					refElementItemVOList.get(0).setElement(elem);
					businessReferenceElementItem.addElementNotSerialized(referenceElementItemsResponse.getReferenceElementItemsVO(), referenceVO.getId(), user.getId());
				}
				
			} else{
				throw new BusinessException("No se puede procesar como Serializado ni como No Serializado");
			}
			
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación processRecord/FileProcessorConfirmItemsLoadMassiveReference ==", t);
			BusinessException businessException = new BusinessException( ""+filaProcesada ,t.getMessage());
			throw businessException;
		} finally {
		   log.debug("== Termina processRecord/FileProcessorConfirmItemsLoadMassiveReference ==");
		}		 
	}*/
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> processRecordMassive(List<FileRecordDTO> fileData)
			throws BusinessException {
		log.debug("== Inicio processRecord/FileProcessorConfirmItemsLoadMassiveReference ==");
		int filaProcesada = 0;
		
		List<BasicProcessorAuxData> listaDatos = new ArrayList<BasicProcessorAuxData>();
		
		try{
			for(FileRecordDTO datos : fileData){
				listaDatos.add(new BasicProcessorAuxData(datos));
			}
			
			for(BasicProcessorAuxData sAux : listaDatos){
				sAux.rellenaDatos(POS_NUM_RN, POS_ELEMENT_TYPE_CODE, POS_SERIAL, POS_LINKED_SERIAL, POS_QUANTITY);
			}
			
			//Comprobamos que todas tengan numero de RN y tipo de elemento.
			for(BasicProcessorAuxData sAux : listaDatos){
				if(!this.validaNumber(sAux.getQuantityStr())){
					sAux.setBeException(new BusinessException("La cantidad no es numerica."));
					continue;
				}
				if(sAux.getRnNumber() == null || sAux.getRnNumber().trim().isEmpty()){
					sAux.setBeException(new BusinessException("El Número RN es requerido"));
					continue;
				}
				if(sAux.getElementTypeCode() == null || sAux.getElementTypeCode().trim().isEmpty()){
					sAux.setBeException(new BusinessException("El tipo de elemento es requerido"));
					continue;
				}
			}
			
			//Obtenemos los diferentes tipos de elementos y las remisiones y los agrupamos. Solo para los que hallan pasado la validacion de arriba.
			HashSet<String> listaElementTypeCode = new HashSet<String>(); //USAMOS 1º un SET para eliminar los duplicados.
			HashSet<String> listaRemisiones = new HashSet<String>(); //USAMOS 1º un SET para eliminar los duplicados.
			for(BasicProcessorAuxData sAux : listaDatos){
				if(sAux.getBeException() == null){
					listaElementTypeCode.add(sAux.getElementTypeCode());
					listaRemisiones.add(sAux.getRnNumber());
				}
			}
			
			int numElementos = 0;
			List<ElementTypeVO> listelementTypeVO = new ArrayList<ElementTypeVO>();
			List<String> listaElementos = new ArrayList<String>();
			for(String sAux : listaElementTypeCode){
				listaElementos.add(sAux);
				numElementos++;
				if(numElementos == 999){
					numElementos = 0;
					listelementTypeVO.addAll(businessElementType.getElementsTypeByCode(listaElementos));
					listaElementos.clear();
				}
			}
			//Esto es necesario para procesar los que queden pendientes.
			if(!listaElementos.isEmpty()){
				listelementTypeVO.addAll(businessElementType.getElementsTypeByCode(listaElementos));
			}
			
			//De la lista de elementTypeVO nos quedamos solo con los ID de los existentes.
			HashMap<String,ElementTypeVO> hAux = new HashMap<String,ElementTypeVO>();
			for(ElementTypeVO elemAux : listelementTypeVO){
				hAux.put(elemAux.getTypeElementCode().toUpperCase(),elemAux);
			}
			
			
			//Ahora comparamos los datos obtenidos con la lista del fichero. Siempre que no tengan ya error.
			for(BasicProcessorAuxData sAux : listaDatos){
				if(sAux.getBeException() == null){
					if(hAux.get(sAux.getElementTypeCode().toUpperCase()) != null){
						sAux.setElementTypeVO(hAux.get(sAux.getElementTypeCode().toUpperCase()));
					}else{
						sAux.setBeException(new BusinessException("El tipo de elemento informado es inválido"));
					}
				}
			}
			
			//Ahora verificamos las remisiones.
			HashMap<String,List<ReferenceVO>> mapRemisiones = new HashMap<String, List<ReferenceVO>>();
			for(String sRem : listaRemisiones){
				try{
					mapRemisiones.put(sRem, businessReference.getReferenceByRNNumber(sRem,CodesBusinessEntityEnum.REFERENCE_STATUS_DELETED.getCodeEntity()));
				}catch(BusinessException be){
					mapRemisiones.put(sRem,null);
				}catch(Throwable t){
					mapRemisiones.put(sRem,null);
				}
			}
			
			//AUXILIARES PARA LAS REMISIONES SERIALIZADAS.
			HashMap<String,Reference> mapaReferencias = new HashMap<String, Reference>();
			ReferenceStatus[] refStatus = new ReferenceStatus[1];
			ItemStatus[] itemStatus = new ItemStatus[1];
			//AUXILIARES PARA LAS REMISIONES NO SERIALIZADAS.
			ItemStatus[] itemStatusNotSerialized = new ItemStatus[1];
			ReferenceStatus[] refStatusNotSerialized = new ReferenceStatus[1];
			RecordStatus[] recordStatusU = new RecordStatus[1];
			RecordStatus[] recordStatusH = new RecordStatus[1];
			
			for(BasicProcessorAuxData sAux : listaDatos){
				if(sAux.getBeException() == null){
					try{
						String rnNumber = sAux.getRnNumber();
						//lista de remisiones segun RN, menos las que se encuentren eliminadas
						List<ReferenceVO> referenceVOs = mapRemisiones.get(rnNumber);
						if (referenceVOs.size()>1){
							throw new BusinessException("El número de RN ya se encuentra asociado a otra remisión");
						}
						if(referenceVOs == null || referenceVOs.isEmpty()){
							throw new BusinessException("La remision " + rnNumber + " indicada no existe.");
						}
						ReferenceVO referenceVO = referenceVOs.get(0);
						
						if (!referenceVO.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED.getCodeEntity()) && 
							!referenceVO.getReferenceStatus().getRefStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.REFERENCE_STATUS_CREATED_PROCESS.getCodeEntity())	) 
							throw new BusinessException("El estado de la remisión debe ser EN CREACION o CREADA");
						
						String serialCode = sAux.getSerialCode();
						String serialCodeLinked = sAux.getSerialCodeLinked();
						ElementTypeVO elementTypeVO = sAux.getElementTypeVO();
						String quantityStr = sAux.getQuantityStr();
						if(elementTypeVO.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity()) && serialCode != null && !serialCode.trim().isEmpty() ){
							//serializados.
							businessReferenceElementItem.addElementSerializedMassive(serialCode, serialCodeLinked, referenceVO.getId(), user,mapaReferencias,refStatus,itemStatus);
						}else if(elementTypeVO.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity()) && quantityStr != null && !quantityStr.trim().isEmpty()){
							//No Serializados.
							Double quantity = 0D;
							if(!NumberUtils.isNumber(quantityStr)){
								throw new BusinessException("La cantidad debe ser numerica");
							}else{
								quantity = Double.parseDouble(quantityStr);
							}
							
							boolean isPrepaid = referenceVO.getIsPrepaidRef().equals(CodesBusinessEntityEnum.REFERENCE_PREPAID.getCodeEntity());
							ReferenceElementItemsResponse referenceElementItemsResponse = businessReferenceElementItem.getElementNotSerializedFromWarehouse(null,referenceVO.getId(),isPrepaid, elementTypeVO.getId(), null);
							List<ReferenceElementItemVO> refElementItemVOList = referenceElementItemsResponse.getReferenceElementItemsVO();
							if(!refElementItemVOList.isEmpty()){
								refElementItemVOList.get(0).setRefQuantity(quantity);
								Element elem = new Element();
								elem.setElementType(elementTypeVO);
								refElementItemVOList.get(0).setElement(elem);
								businessReferenceElementItem.addElementNotSerializedMassive(referenceElementItemsResponse.getReferenceElementItemsVO(), referenceVO.getId(), user,itemStatusNotSerialized,refStatusNotSerialized,recordStatusU,recordStatusH);
							}
							
						} else{
							throw new BusinessException("No se puede procesar como Serializado ni como No Serializado");
						}
					}catch(BusinessException be){
						sAux.setBeException(be);
					}catch (Throwable t){
						BusinessException businessException = new BusinessException( ""+filaProcesada ,t.getMessage());
						sAux.setBeException(businessException);
					}	
				}
			}
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación processRecord/FileProcessorConfirmItemsLoadMassiveReference ==", t);
			BusinessException businessException = new BusinessException( ""+filaProcesada ,t.getMessage());
			throw businessException;
		} finally {
		   log.debug("== Termina processRecord/FileProcessorConfirmItemsLoadMassiveReference ==");
		}
		List<Object> listaSalida = new ArrayList<Object>();
		listaSalida.addAll(listaDatos);
		
		return listaSalida;
	}

	private boolean validaNumber(String quantityStr) {
		boolean isNumber = false;
		
		if(quantityStr != null && !quantityStr.isEmpty()){
			isNumber = StringUtils.isNumeric(quantityStr);
		}
		return isNumber;
	}

	@Override
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(
			List<FileRecordDTO> fileData) {
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		try{
			user = daoUser.getUserById(this.getUploadFile().getUser().getId());
		} catch (Throwable t){
			log.debug("== Error al tratar de ejecutar la operación doGlobalValidationsAndDropNotValidRecords/FileProcessorConfirmItemsLoadMassiveReference ==", t);
			errors.add( buildFileDetailProcess(0, t.getMessage()) );
		} finally {
		   log.debug("== Termina doGlobalValidationsAndDropNotValidRecords/FileProcessorConfirmItemsLoadMassiveReference ==");
		}
		
		return errors;
	}

	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<FileDetailProcess> doPostProcess() {
		//Este fichero no tiene post procesamiento
		return new ArrayList<FileDetailProcess>();
	}
	
}
