package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.HashMap;
import java.util.Iterator;
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
import co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.RefQuantityControlItem;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.collection.RefQuantityControlItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.RefQuantityControlItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RefQuantityControlItemDAOLocal;

@Stateless(name="RefQuantityControlItemBusinessBeanLocal",mappedName="ejb/RefQuantityControlItemBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefQuantityControlItemBusinessBean extends BusinessBase  implements
		RefQuantityControlItemBusinessBeanLocal {
	
	
	 @EJB(name="RefQuantityControlItemDAOLocal", beanInterface=RefQuantityControlItemDAOLocal.class)
	 private RefQuantityControlItemDAOLocal daoRefQtyCtrlItem;
	 
	 
	 @EJB(name="EmployeesCrewDAOLocal", beanInterface=EmployeesCrewDAOLocal.class)
	 private EmployeesCrewDAOLocal employCrewDAO;
	 
	 @EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
	    private ReferenceBusinessBeanLocal businessReference;
	 
	 private final static Logger log = UtilsBusiness.getLog4J(RefQuantityControlItemBusinessBean.class);
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createRefQuantityControlItem(RefQuantityControlItemVO obj)
			throws BusinessException {
		  log.debug("== Inicia createRefQuantityControlItem/RefQuantityControlItemBusinessBean ==");
	        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        try {
	        	RefQuantityControlItem objPojo =  UtilsBusiness.copyObject(RefQuantityControlItem.class, obj);
	            daoRefQtyCtrlItem.createRefQuantityControlItem(objPojo);
	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación createRefQuantityControlItem/RefQuantityControlItemBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina createRefQuantityControlItem/RefQuantityControlItemBusinessBean ==");
	        }

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal#deleteRefQuantityControlItem(co.com.directv.sdii.model.vo.RefQuantityControlItemVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteRefQuantityControlItem(RefQuantityControlItemVO obj)
			throws BusinessException {
		 log.debug("== Inicia deleteRefQuantityControlItem/RefQuantityControlItemBusinessBean ==");
	        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        try {
	        	RefQuantityControlItem objPojo =  UtilsBusiness.copyObject(RefQuantityControlItemVO.class, obj);
	            daoRefQtyCtrlItem.deleteRefQuantityControlItem(objPojo);
	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación deleteRefQuantityControlItem/RefQuantityControlItemBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina deleteRefQuantityControlItem/RefQuantityControlItemBusinessBean ==");
	        }

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal#getAllRefQuantityControlItems()
	 */
	@Override
	 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<RefQuantityControlItemVO> getAllRefQuantityControlItems()
			throws BusinessException {
		 log.debug("== Inicia getAllRefQuantityControlItems/RefQuantityControlItemBusinessBean ==");
	        try {
	            return UtilsBusiness.convertList(daoRefQtyCtrlItem.getAllRefQuantityControlItems(), RefQuantityControlItemVO.class);

	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación getAllRefQuantityControlItems/RefQuantityControlItemBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getAllRefQuantityControlItems/RefQuantityControlItemBusinessBean ==");
	        }
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public RefQuantityControlItemsResponse getRefQtyCtrlItemsByReference(
			RefQuantityControlItemVO refId,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getRefQtyCtrlItemsByReference/RefQuantityControlItemBusinessBean ==");
		List<RefQuantityControlItem> tmpList;
        try {
        	if( refId == null || refId.getIdReference() == null || refId.getIdReference().longValue() <= 0 ){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	RefQuantityControlItemsResponse response = daoRefQtyCtrlItem.getRefQtyCtrlItemsByReference(refId.getIdReference(),requestCollInfo);
        	tmpList = response.getRefQuantityControlItems();
        	response.setRefQuantityControlItemsVO (UtilsBusiness.convertList(tmpList, RefQuantityControlItemVO.class));
        	response.setRefQuantityControlItems(null);
        	for(RefQuantityControlItemVO tmp : response.getRefQuantityControlItemsVO() ){
        		tmp.setReference(null);
        	}
            return response;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRefQtyCtrlItemsByReference/RefQuantityControlItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefQtyCtrlItemsByReference/RefQuantityControlItemBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal#getRefQuantityControlItemByID(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public RefQuantityControlItemVO getRefQuantityControlItemByID(Long id)
			throws BusinessException {
		log.debug("== Inicia getRefQuantityControlItemByID/RefQuantityControlItemBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	RefQuantityControlItem objPojo = daoRefQtyCtrlItem.getRefQuantityControlItemByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(RefQuantityControlItemVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRefQuantityControlItemByID/RefQuantityControlItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefQuantityControlItemByID/RefQuantityControlItemBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal#updateRefQuantityControlItem(co.com.directv.sdii.model.vo.RefQuantityControlItemVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateRefQuantityControlItem(RefQuantityControlItemVO obj)
			throws BusinessException {
		log.debug("== Inicia updateRefQuantityControlItem/RefQuantityControlItemBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	RefQuantityControlItem objPojo =  UtilsBusiness.copyObject(RefQuantityControlItem.class, obj);
            daoRefQtyCtrlItem.updateRefQuantityControlItem(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateRefQuantityControlItem/RefQuantityControlItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefQuantityControlItem/RefQuantityControlItemBusinessBean ==");
        }

	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal#saveQtyRefCtrlItems(co.com.directv.sdii.model.vo.ReferenceVO, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveQtyRefCtrlItems(ReferenceVO reference,
			List<RefQuantityControlItemVO> qtyCtrlItems)
			throws BusinessException {
		log.debug("== Inicia saveQtyRefCtrlItems/RefQuantityControlItemBusinessBean ==");
        UtilsBusiness.assertNotNull(reference, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(reference.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(reference.getIsQuantityControl(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(qtyCtrlItems, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	
        	//asegurar que si se marcó que la remisión no tiene control de cantidad, no hayan ítems
        	if(reference.getIsQuantityControl().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())) {
	        	//1. Validar que en la lista no se encuentren repetidos tipos de elemento
	        	this.validateElementTypes(qtyCtrlItems);
	        	//2. Validar que la remisión no tenga ya control de cantidades guardado
	        	RefQuantityControlItemVO refQtyTmp = new RefQuantityControlItemVO();
	        	refQtyTmp.setReference(reference);
	        	refQtyTmp.setIdReference(reference.getId());
	        	RefQuantityControlItemsResponse response = this.getRefQtyCtrlItemsByReference(refQtyTmp, null);
	        	//Indica que la remisión ya cuenta co control de cantidades
	        	if(response.getRefQuantityControlItemsVO() != null && !response.getRefQuantityControlItemsVO().isEmpty() ){
	        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN363.getCode(),ErrorBusinessMessages.STOCK_IN363.getMessage());
	        	}
	        	//3. Guardar los elementos
	        	for(RefQuantityControlItemVO tmp : qtyCtrlItems){
	        		tmp.setReference(reference);
	        		this.createRefQuantityControlItem(tmp);
	        	}
        	}
        	
        	//actualiza la remisión, indicando si tiene o no control de cantidad
        	businessReference.updateReference(reference);
        	
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación saveQtyRefCtrlItems/RefQuantityControlItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina saveQtyRefCtrlItems/RefQuantityControlItemBusinessBean ==");
        }
		
	}
	
	
	
	private void validateElementTypes(List<RefQuantityControlItemVO> qtyCtrlItems)throws BusinessException{
		HashMap<Long, String> qtyCtrlItemsTmp =  new HashMap<Long, String>();
		//Se itera: se valida si el item se encuentra en el map; si no se agraga;
		//En el momento que exista se lanza excepción
		HashMap<String,String> listTiposRepetidos = new HashMap<String,String>();
		for(RefQuantityControlItemVO tmp : qtyCtrlItems){
			if(qtyCtrlItemsTmp.get(tmp.getElementType().getId())==null){
				qtyCtrlItemsTmp.put(tmp.getElementType().getId(), tmp.getElementType().getId().toString());
			}else{
				listTiposRepetidos.put(tmp.getElementType().getTypeElementName(), tmp.getElementType().getTypeElementName());
			}
		}
		//Se lanza excepción indicando los repetidos
		if(listTiposRepetidos.size()>0){
			int sizeTmp = listTiposRepetidos.keySet().size();
			Iterator<String> iterador = listTiposRepetidos.keySet().iterator();
			StringBuffer sb = new StringBuffer();
			int i = 0;
			//Genera la cadena que se enviará por parámetrp
			while(iterador.hasNext()){
				if(i!=0 && i!=sizeTmp){
					sb.append(",");
				}
				sb.append(iterador.next());
			}
			String[] param = new String[1];
			param[0] = sb.toString();
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN362.getCode(),ErrorBusinessMessages.STOCK_IN362.getMessage(param));
		}
		
		
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal#getReferencesPreloadByCreationProcess(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public RefQuantityControlItemsResponse getReferencesPreloadByCreationProcess(
			Long idWhSource, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getReferencesPreloadByCreationProcess/WarehouseBusinessBean ==");
    	UtilsBusiness.assertNotNull(idWhSource, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	RefQuantityControlItemsResponse response = this.daoRefQtyCtrlItem.getReferencesPreloadByCreationProcess(idWhSource,requestCollInfo);
        	List<RefQuantityControlItem> references = response.getRefQuantityControlItems();
        	response.setRefQuantityControlItemsVO( UtilsBusiness.convertList(references, RefQuantityControlItemVO.class) );  
        	response.setRefQuantityControlItems( null );
        	for(RefQuantityControlItemVO tmp : response.getRefQuantityControlItemsVO()){
        		tmp.setIdReference(tmp.getReference().getId());
        		StringBuffer bufferWhSource = new StringBuffer();
        		bufferWhSource.append(tmp.getReference().getWarehouseBySourceWh().getDealerId().getDepotCode() + " + ");
        		bufferWhSource.append(tmp.getReference().getWarehouseBySourceWh().getDealerId().getDealerName() + " + ");
        		if(tmp.getReference().getWarehouseBySourceWh().getCrewId()!=null){
        			List<EmployeeCrew> listTmp=employCrewDAO.getEmployeesCrewByCrewID(tmp.getReference().getWarehouseBySourceWh().getCrewId().getId());
            		if(listTmp.size()>0){
            			Employee employee = listTmp.get(0).getEmployee();
            			bufferWhSource.append(employee.getFirstName()+" " +employee.getLastName()+" + ");
            		}
        		}
        		/*if(tmp.getReference().getWarehouseBySourceWh().getWhResponsible()!=null){
        			bufferWhSource.append(tmp.getReference().getWarehouseBySourceWh().getWhResponsible()+ " + ");	
        		}*/
        		bufferWhSource.append(tmp.getReference().getWarehouseBySourceWh().getWarehouseType().getWhTypeName());
        		tmp.setWhSourceName(bufferWhSource.toString());
        		StringBuffer bufferWhTarget = new StringBuffer();
        		bufferWhTarget.append(tmp.getReference().getWarehouseByTargetWh().getDealerId().getDepotCode() + " + ");
        		bufferWhTarget.append(tmp.getReference().getWarehouseByTargetWh().getDealerId().getDealerName() + " + ");
        		/*if(tmp.getReference().getWarehouseByTargetWh().getWhResponsible()!=null){
        			bufferWhTarget.append(tmp.getReference().getWarehouseByTargetWh().getWhResponsible()+ " + ");	
        		}*/
        		if(tmp.getReference().getWarehouseByTargetWh().getCrewId()!=null){
        			List<EmployeeCrew> listTmp1=employCrewDAO.getEmployeesCrewByCrewID(tmp.getReference().getWarehouseByTargetWh().getCrewId().getId());
            		if(listTmp1.size()>0){
            			Employee employee = listTmp1.get(0).getEmployee();
            			bufferWhTarget.append(employee.getFirstName()+" " +employee.getLastName()+" + ");
            		}	
        		}
        		bufferWhTarget.append(tmp.getReference().getWarehouseByTargetWh().getWarehouseType().getWhTypeName());
        		tmp.setWhTargetName(bufferWhTarget.toString());
        		tmp.setIsPrepaidRef(tmp.getReference().getIsPrepaidRef());
        		
        		tmp.setReference(null);
        	}
            return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferencesPreloadByCreationProcess/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferencesPreloadByCreationProcess/WarehouseBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal#getRefQuantityControlItemByElmtTypeAndRef(java.lang.Long, java.lang.Long)
	 */
	@Override
	 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public RefQuantityControlItemVO getRefQuantityControlItemByElmtTypeAndRef(
			Long elementType, Long reference) throws BusinessException {
		log.debug("== Inicia getRefQuantityControlItemByElmtTypeAndRef/WarehouseBusinessBean ==");
    	UtilsBusiness.assertNotNull(elementType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	UtilsBusiness.assertNotNull(reference, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
            return UtilsBusiness.copyObject(RefQuantityControlItemVO.class, this.daoRefQtyCtrlItem.getRefQuantityControlItemByElmtTypeAndRef(elementType,reference));
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRefQuantityControlItemByElmtTypeAndRef/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefQuantityControlItemByElmtTypeAndRef/WarehouseBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal#generateRefQuantiyControls(co.com.directv.sdii.model.vo.ElementVO, co.com.directv.sdii.model.vo.ReferenceVO, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void generateRefQuantiyControls(ElementVO element , ReferenceVO reference,Double quantity) throws BusinessException {
		log.debug("== Inicia generateRefQuantiyControls/WarehouseElementBusinessBean ==");
        try{
        	RefQuantityControlItem refQuantityControl = this.daoRefQtyCtrlItem.getRefQuantityControlItemByElmtTypeAndRef(element.getElementType().getId(), reference.getId());
        	//Si el control de cantidad no existe, se crea
			if( refQuantityControl == null ){
				RefQuantityControlItem newRefQuantityControlItem = new RefQuantityControlItem();
				newRefQuantityControlItem.setElementType( element.getElementType() );
				newRefQuantityControlItem.setIncludedQty(quantity);
				newRefQuantityControlItem.setRequiredQty(0D);
				Reference refPojo = new Reference();
				refPojo.setId( reference.getId() );
				newRefQuantityControlItem.setReference( refPojo );
				this.daoRefQtyCtrlItem.createRefQuantityControlItem(newRefQuantityControlItem);
			//En el caso que exista se acutaliza la cantidad incluida
			}else {

				Double newIncludedQuantity = refQuantityControl.getIncludedQty() + quantity;
				//Caso en que la resta con el nuevo elmento da 0 y no fue un control de cantidad seleccionado
				if( newIncludedQuantity.doubleValue() == 0 && refQuantityControl.getRequiredQty().doubleValue() == 0){
					this.daoRefQtyCtrlItem.deleteRefQuantityControlItem(refQuantityControl);
				} else {
					refQuantityControl.setIncludedQty(newIncludedQuantity);
					this.daoRefQtyCtrlItem.updateRefQuantityControlItem(refQuantityControl);
				}

			}
        } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación generateRefQuantiyControls/WarehouseElementBusinessBean ==");
	    	throw this.manageException(ex);
	   } finally {
	        log.debug("== Termina generateRefQuantiyControls/WarehouseElementBusinessBean ==");
	   }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefQuantityControlItemBusinessBeanLocal#validateExistenceOfReferenceQuantityDiferences(java.lang.Long)
	 */
	@Override
	public boolean validateExistenceOfReferenceQuantityDifferences(Long referenceId) throws BusinessException {
		log.debug("== Inicia validateExistenceOfReferenceQuantityDiferences/WarehouseBusinessBean ==");
    	UtilsBusiness.assertNotNull(referenceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            return this.daoRefQtyCtrlItem.validateExistenceOfReferenceQuantityDifferences(referenceId);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRefQuantityControlItemByElmtTypeAndRef/WarehouseBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina validateExistenceOfReferenceQuantityDiferences/WarehouseBusinessBean ==");
        }
	}

}
