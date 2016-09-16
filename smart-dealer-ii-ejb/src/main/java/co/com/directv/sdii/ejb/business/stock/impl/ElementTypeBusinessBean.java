package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.collection.ElementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ElementType
 * 
 * Fecha de Creación: Mar 8, 2010
 * 
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal
 */
@Stateless(name = "ElementTypeBusinessBeanLocal", mappedName = "ejb/ElementTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementTypeBusinessBean extends BusinessBase implements
		ElementTypeBusinessBeanLocal {

	@EJB(name = "ElementTypeDAOLocal", beanInterface = ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal daoElementType;
	
	@EJB(name = "ElementModelBusinessBeanLocal", beanInterface = ElementModelBusinessBeanLocal.class)
	private ElementModelBusinessBeanLocal elementModelBusiness;
	
	@EJB(name = "ElementBusinessBeanLocal", beanInterface = ElementBusinessBeanLocal.class)
	private ElementBusinessBeanLocal elementBusiness;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
    private WarehouseElementDAOLocal daoWarehouseElement;
	
	@EJB(name = "ElementDAOLocal", beanInterface = ElementDAOLocal.class)
	private ElementDAOLocal daoElement;
	
	private final static Logger log = UtilsBusiness.getLog4J(ElementTypeBusinessBean.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getAllElementTypes()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getAllElementTypes() throws BusinessException {
		log.debug("== Inicia getAllElementTypes/ElementTypeBusinessBean ==");
		try {
			
			List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType.getAllElementTypes(), ElementTypeVO.class);
			fillNameCodeToPrint(list );
			
			return list;

		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getAllElementTypes/ElementTypeBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllElementTypes/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementTypeVO getElementTypeByID(Long id) throws BusinessException {
		log.debug("== Inicia getElementTypeByID/ElementTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(id,
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			ElementType objPojo = daoElementType.getElementTypeByID(id);
			if (objPojo == null) {
				throw new BusinessException(
						ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),
						ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			}
			return UtilsBusiness.copyObject(ElementTypeVO.class, objPojo);
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypeByID/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypeByID/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * createElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createElementType(ElementTypeVO obj) throws BusinessException {
		log.debug("== Inicia createElementType/ElementTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(obj,
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {

			if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
				log.error("Error de validación de campos requeridos");
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}

			ElementType objPojo = daoElementType.getElementTypeByCode(obj
					.getTypeElementCode());
			if (objPojo != null) {
				log.debug("El objeto que intenta crear como nuevo ya existe");
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN347
						.getCode(), ErrorBusinessMessages.STOCK_IN347
						.getMessage());
			}
			ElementType objPojoTmp = daoElementType.getElementTypeByName(obj
					.getTypeElementName());
			if (objPojoTmp != null) {
				log.debug("El objeto que intenta crear como nuevo ya existe");
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN346
						.getCode(), ErrorBusinessMessages.STOCK_IN346
						.getMessage());
			}
			if (obj.getTypeRegEx() != null) {
				try {
					UtilsBusiness.validateRegEx(obj.getTypeRegEx());
				} catch (PatternSyntaxException e) {
					throw new BusinessException(
							ErrorBusinessMessages.STOCK_IN348.getCode(),
							ErrorBusinessMessages.STOCK_IN348.getMessage());
				}
			}
			
			//Valida que el tipo de elemento no tenga caracteres especiales.
			if (!obj.getTypeElementCode().matches("^[^&<>%$ #]*$")){
				log.debug("El codigo del objeto contine caracteres especiales, no permitidos");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN398.getCode(), ErrorBusinessMessages.STOCK_IN398.getMessage());
			}

			objPojo = UtilsBusiness.copyObject(ElementType.class, obj);
			daoElementType.createElementType(objPojo);
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación createElementType/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createElementType/ElementTypeBusinessBean ==");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * updateElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateElementType(ElementTypeVO obj) throws BusinessException {
		log.debug("== Inicia updateElementType/ElementTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(obj,
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			
			if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
				log.error("Error de validación de campos requeridos");
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}

			ElementType persistedElementType = daoElementType.getElementTypeByCode(obj
					.getTypeElementCode());
			if (persistedElementType != null
					&& persistedElementType.getId().longValue() != obj.getId().longValue()) {
				log.debug("El objeto que intenta crear como nuevo ya existe");
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN347
						.getCode(), ErrorBusinessMessages.STOCK_IN347
						.getMessage());
			}
			ElementType objPojoTmp = daoElementType.getElementTypeByName(obj
					.getTypeElementName());
			if (objPojoTmp != null
					&& objPojoTmp.getId().longValue() != obj.getId()
							.longValue()) {
				log.debug("El objeto que intenta crear como nuevo ya existe");
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN346
						.getCode(), ErrorBusinessMessages.STOCK_IN346
						.getMessage());
			}
			if (obj.getTypeRegEx() != null) {
				try {
					UtilsBusiness.validateRegEx(obj.getTypeRegEx());
				} catch (PatternSyntaxException e) {
					throw new BusinessException(
							ErrorBusinessMessages.STOCK_IN348.getCode(),
							ErrorBusinessMessages.STOCK_IN348.getMessage());
				}
			}
			
			// Validaciones de inactivación, que se ejecutan cuando se cambia de estado activo a inactivo
			if ( persistedElementType.getIsActive().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE.getCodeEntity())
					 && obj.getIsActive().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_INACTIVE.getCodeEntity()) ) {
				// 1) Validar que el modelo asociado no se encuentre activo
					elementModelBusiness.getElementModelByID(obj.getElementModel().getId());
				// 2) Validar que no existan elementos asociados al tipo
					elementBusiness.getElementsByElementType(obj, null);
				
				//issue:64173, Si hay elementos relacionados en alguna bodega, no se debe permitir modificación del tipo de elemento
				if( areThereElementsOfTypeInAnyWarehouse(obj.getId()) ) {					
					//
		        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN430.getCode(), ErrorBusinessMessages.STOCK_IN430.getMessage());
					
				}
			}

			// Valida que el tipo de elemento no tenga caracteres especiales.
			if (!obj.getTypeElementCode().matches("^[^&<>%$ #]*$")) {
				log.debug("El codigo del objeto contine caracteres especiales, no permitidos");
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN398
						.getCode(), ErrorBusinessMessages.STOCK_IN398
						.getMessage());
			}

			
			persistedElementType = UtilsBusiness.copyObject(ElementType.class, obj);
			daoElementType.updateElementType(persistedElementType);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación updateElementType/ElementTypeBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateElementType/ElementTypeBusinessBean ==");
		}
	}

	private boolean areThereElementsOfTypeInAnyWarehouse(Long elementTypeId) throws DAOServiceException, DAOSQLException {
		Long countElement = daoElement.countElementByElementType(elementTypeId);
		return countElement>0;
	}

	@SuppressWarnings("unused")
	private boolean isElementTypeOfClassDecoOrSmartcard(ElementTypeVO elementTypeVO) throws PropertiesException, DAOServiceException, DAOSQLException {
		boolean isElementTypeOfClassDecoOrSmartcard = false;
		
		ElementType et = daoElementType.getElementTypeByID(elementTypeVO.getId().longValue());
		
		if(et != null 
				 && et.getElementModel() != null 
				 && et.getElementModel().getElementClass() != null
				 && et.getElementModel().getElementClass().getElementClassCode() != null) {
		
			isElementTypeOfClassDecoOrSmartcard =
				et.getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())
				|| et.getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity());
			
		}
		
		return isElementTypeOfClassDecoOrSmartcard;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * deleteElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteElementType(ElementTypeVO obj) throws BusinessException {
		log.debug("== Inicia deleteElementType/ElementTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(obj,
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(obj.getId(),
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			ElementType objPojo = UtilsBusiness.copyObject(ElementType.class,
					obj);
			daoElementType.deleteElementType(objPojo);
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación deleteElementType/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deleteElementType/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypeByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ElementTypeVO getElementTypeByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getElementTypeByCode/ElementTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(code,
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
				ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			ElementType objPojo = daoElementType.getElementTypeByCode(code);
			if (objPojo == null) {
				return null;
			}
			ElementTypeVO objVo = UtilsBusiness.copyObject(ElementTypeVO.class,
					objPojo);
			return objVo;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypeByCode/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.debug("== Termina getElementTypeByCode/ElementTypeBusinessBean ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementsTypeByCode(List<String> listaElementTypeCode)
			throws BusinessException {
		log.debug("== Inicia getElementTypeByCode/ElementTypeBusinessBean ==");
		try {
			List<ElementType> objPojo = daoElementType.getElementsTypeByCode(listaElementTypeCode);
			if (objPojo == null) {
				return null;
			}
			
			List<ElementTypeVO> objVo = new ArrayList<ElementTypeVO>(); 
			for(ElementType eAux : objPojo){
				objVo.add( UtilsBusiness.copyObject(ElementTypeVO.class,eAux));
			}
			return objVo;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypeByCode/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.debug("== Termina getElementTypeByCode/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByActive()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementTypesByActive()
			throws BusinessException {
		log
				.info("== Inicia getElementTypesByActive/ElementTypeBusinessBean ==");
		try {

			List<ElementType> list = daoElementType
					.getElementTypesByIsActive(CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE
							.getCodeEntity());
			if (list == null) {
				return null;
			}
			List<ElementTypeVO> listTmp = UtilsBusiness.convertList(list,
					ElementTypeVO.class);
			fillNameCodeToPrint(listTmp);
			return listTmp;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypesByActive/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypesByActive/ElementTypeBusinessBean ==");
		}
	}

	private void fillNameCodeToPrint(List<ElementTypeVO> elementTypes) {
		if(elementTypes != null) {
			for (ElementTypeVO elementTypeVO : elementTypes) {
				StringBuffer sb = new StringBuffer();
				
				if(!StringUtils.isBlank(elementTypeVO.getTypeElementCode())) {
					sb.append(elementTypeVO.getTypeElementCode()).append("-");
				}
				if(!StringUtils.isBlank(elementTypeVO.getTypeElementName())) {
					sb.append(elementTypeVO.getTypeElementName());
				}
				elementTypeVO.setNameCodeToPrint(sb.toString());
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByActive()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementTypesByActiveAndIsSerialized(
			boolean isSerialized) throws BusinessException {
		log
				.info("== Inicia getElementTypesByActiveAndIsSerialized/ElementTypeBusinessBean ==");
		try {
			
			List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType
					.getElementTypesByIsActiveAndIsSerialized(
							CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE
									.getCodeEntity(), isSerialized),
					ElementTypeVO.class);
			fillNameCodeToPrint(list);
			
			return list;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypesByActiveAndIsSerialized/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypesByActiveAndIsSerialized/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByElementModel(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementTypesByElementModel(
			String codeElementModel) throws BusinessException {
			log.debug("== Inicia getElementTypesByElementModel/ElementTypeBusinessBean ==");
			try {
				
				List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType.getElementTypeByElementModel(codeElementModel),ElementTypeVO.class);
				fillNameCodeToPrint(list);
				
				return list;
			} catch (Throwable ex) {
				log.debug("== Error al tratar de ejecutar la operación getElementTypesByElementModel/ElementTypeBusinessBean ==",ex);
				throw this.manageException(ex);
			} finally {
				log.debug("== Termina getElementTypesByElementModel/ElementTypeBusinessBean ==");
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByElementModelAndActiveStatus(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementTypesByElementModelAndActiveStatus(
			String codeElementModel) throws BusinessException {
		log
				.info("== Inicia getElementTypesByElementModelAndActiveStatus/ElementTypeBusinessBean ==");
		try {
			List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType
					.getElementTypeByElementModelAndStatus(codeElementModel,
							CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE
									.getCodeEntity()), ElementTypeVO.class);
			for (ElementTypeVO elementType : list) {
				elementType.setElementModel(null);
				elementType.setMeasureUnit(null);
			}
			fillNameCodeToPrint(list);
			return list;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypesByElementModelAndActiveStatus/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypesByElementModelAndActiveStatus/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByElementModelAndInactiveStatus(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementTypesByElementModelAndInactiveStatus(
			String codeElementModel) throws BusinessException {
		log
				.info("== Inicia getElementTypesByElementModelAndInactiveStatus/ElementTypeBusinessBean ==");
		try {
			List<ElementTypeVO> list = UtilsBusiness
					.convertList(
							daoElementType
									.getElementTypeByElementModelAndStatus(
											codeElementModel,
											CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_INACTIVE
													.getCodeEntity()),
							ElementTypeVO.class);
			for (ElementTypeVO elementType : list) {
				elementType.setElementModel(null);
				elementType.setMeasureUnit(null);
			}
			fillNameCodeToPrint(list);
			return list;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypesByElementModelAndInactiveStatus/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypesByElementModelAndInactiveStatus/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByElementModelId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementTypesByElementModelId(
			Long idElementModel) throws BusinessException {
		log
				.info("== Inicia getElementTypesByElementModelId/ElementTypeBusinessBean ==");
		try {
			
			List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType
					.getElementTypeByElementModelId(idElementModel),
					ElementTypeVO.class);
			
			fillNameCodeToPrint(list);
			
			return list;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypesByElementModelId/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypesByElementModelId/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByElementModelIdAndActiveStatus(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementTypesByElementModelIdAndActiveStatus(
			Long idElementModel) throws BusinessException {
		log
				.info("== Inicia getElementTypesByElementModelIdAndActiveStatus/ElementTypeBusinessBean ==");
		try {
			List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType
					.getElementTypeByElementModelIdAndStatus(idElementModel,
							CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE
									.getCodeEntity()), ElementTypeVO.class);
			for (ElementTypeVO elementType : list) {
				elementType.setElementModel(null);
				elementType.setMeasureUnit(null);
			}
			
			fillNameCodeToPrint(list);
			
			return list;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypesByElementModelIdAndActiveStatus/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypesByElementModelIdAndActiveStatus/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByElementModelIdAndActiveStatus(java.lang.Long,
	 * co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ElementTypeResponse getElementTypesByElementModelIdAndActiveStatus(
			Long idElementModel, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log
				.info("== Inicia getElementTypesByElementModelIdAndActiveStatus/ElementTypeBusinessBean ==");
		List<ElementType> list;
		try {
			ElementTypeResponse response = daoElementType
					.getElementTypeByElementModelIdAndStatus(idElementModel,
							CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_ACTIVE
									.getCodeEntity(), requestCollInfo);
			list = response.getElementType();
			response.setElementTypeVO(UtilsBusiness.convertList(list,
					ElementTypeVO.class));
			response.setElementType(null);
			for (ElementTypeVO elementType : response.getElementTypeVO()) {
				elementType.setElementModel(null);
				elementType.setMeasureUnit(null);
			}
			
			fillNameCodeToPrint(response.getElementTypeVO());
			
			return response;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypesByElementModelIdAndActiveStatus/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypesByElementModelIdAndActiveStatus/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByElementModelIdAndInactiveStatus(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementTypesByElementModelIdAndInactiveStatus(
			Long idElementModel) throws BusinessException {
		log
				.info("== Inicia getElementTypesByElementModelIdAndInactiveStatus/ElementTypeBusinessBean ==");
		try {
			List<ElementTypeVO> list = UtilsBusiness
					.convertList(
							daoElementType
									.getElementTypeByElementModelIdAndStatus(
											idElementModel,
											CodesBusinessEntityEnum.ELEMENT_TYPE_STATUS_INACTIVE
													.getCodeEntity()),
							ElementTypeVO.class);
			for (ElementTypeVO elementType : list) {
				elementType.setElementModel(null);
				elementType.setMeasureUnit(null);
			}
			
			fillNameCodeToPrint(list);
			
			return list;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypesByElementModelIdAndInactiveStatus/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypesByElementModelIdAndInactiveStatus/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByElementModelIdAndAllStatusPage(java.lang.Long,
	 * java.lang.String,
	 * co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ElementTypeResponse getElementTypesByElementModelIdAndAllStatusPage(
			Long idElementModel, String code,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		log
				.info("== Inicia getElementTypesByElementModelIdAndAllStatusPage/ElementTypeBusinessBean ==");
		List<ElementType> list;
		try {
			ElementTypeResponse response = daoElementType
					.getElementTypesByElementModelIdAndAllStatusPage(
							idElementModel, code, requestCollInfo);
			list = response.getElementType();
			response.setElementTypeVO(UtilsBusiness.convertList(list,
					ElementTypeVO.class));
			response.setElementType(null);
			for (ElementTypeVO elementType : response.getElementTypeVO()) {
				if (elementType != null && elementType.getElementModel() != null && elementType.getElementModel().getElementClass() != null && elementType.getElementModel().getElementClass().getElementClassName() != null){					
					elementType.setElementClassName(elementType.getElementModel()
							.getElementClass().getElementClassName());
				}
				if (elementType != null && elementType.getElementModel() != null && elementType.getElementModel().getModelName() != null ){ 
					elementType.setElementModelName(elementType.getElementModel()
							.getModelName());
				}
				if (elementType != null && elementType.getMeasureUnit() != null && elementType.getMeasureUnit().getUnitName() != null ){
					elementType.setMeasureUnitName(elementType.getMeasureUnit()
							.getUnitName());
				}
				elementType.setElementModel(null);
				elementType.setMeasureUnit(null);
			}
			
			fillNameCodeToPrint(response.getElementTypeVO());
			
			return response;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getElementTypesByElementModelIdAndAllStatusPage/ElementTypeBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.info("== Termina getElementTypesByElementModelIdAndAllStatusPage/ElementTypeBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal#
	 * getElementTypesByPrepaidIdAndActiveStatusPage(java.lang.String,
	 * co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ElementTypeResponse getElementTypesByPrepaidIdAndActiveStatusPage(
			String prepaid, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getElementTypesByPrepaidIdAndActiveStatusPage/ElementTypeBusinessBean ==");
		List<ElementType> list;
		try {
			ElementTypeResponse response = daoElementType.getElementTypesByPrepaidIdAndActiveStatusPage(prepaid,requestCollInfo);
			list = response.getElementType();
			response.setElementTypeVO(UtilsBusiness.convertList(list,ElementTypeVO.class));
			response.setElementType(null);
			for (ElementTypeVO elementType : response.getElementTypeVO()) {
				elementType.setNameCodeToPrint(elementType.getTypeElementCode() + "-"
						+ elementType.getTypeElementName());
				elementType.setElementClassName(elementType.getElementModel().getElementClass().getElementClassName());
				elementType.setElementModelName(elementType.getElementModel().getModelName());
				elementType.setMeasureUnitName(elementType.getMeasureUnit().getUnitName());
				elementType.setElementModel(null);
				elementType.setMeasureUnit(null);
			}
			
			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getElementTypesByPrepaidIdAndActiveStatusPage/ElementTypeBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementTypesByPrepaidIdAndActiveStatusPage/ElementTypeBusinessBean ==");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementTypeVO> getElementTypeByElementModelAndIsSerialized(
			String elementModelCode, boolean isSerialized) throws BusinessException {
		log.debug("== Inicia getElementTypeByElementModelAndIsSerilized/ElementTypeBusinessBean ==");
		try {

			List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType.getElementTypeByElementModelAndIsSerialized(elementModelCode, isSerialized),ElementTypeVO.class);
			fillNameCodeToPrint(list);

			return list;
		} catch (Throwable ex) {
			log
			.debug(
					"== Error al tratar de ejecutar la operación getElementTypeByElementModelAndIsSerilized/ElementTypeBusinessBean ==",
					ex);
			throw this.manageException(ex);
		} finally {
			log
			.info("== Termina getElementTypeByElementModelAndIsSerilized/ElementTypeBusinessBean ==");
		}
	}
	
	public List<ElementTypeVO> getElementTypeByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId) throws BusinessException {
		log.debug("== Inicia getElementTypeByElementModelAndIsSerilized/ElementTypeBusinessBean ==");
		try {

			List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType.getElementTypeByWarehouseIdAndElementModelId(warehouseId,elementModelId),ElementTypeVO.class);
			fillNameCodeToPrint(list);

			return list;
		} catch (Throwable ex) {
			log
			.debug(
					"== Error al tratar de ejecutar la operación getElementTypeByElementModelAndIsSerilized/ElementTypeBusinessBean ==",
					ex);
			throw this.manageException(ex);
		} finally {
			log
			.info("== Termina getElementTypeByElementModelAndIsSerilized/ElementTypeBusinessBean ==");
		}
	}

	@Override
	public List<ElementTypeVO> getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(
			String isPrepaid, String isSerialized) throws BusinessException {
		log.debug("== Inicia getElementTypeBySerorNotSerAndPrepaidorNotPrepaid/ElementTypeBusinessBean ==");
		try {
			List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType.getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(isPrepaid, isSerialized),ElementTypeVO.class);
			fillNameCodeToPrint(list);
			return list;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getElementTypeBySerorNotSerAndPrepaidorNotPrepaid/ElementTypeBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementTypeBySerorNotSerAndPrepaidorNotPrepaid/ElementTypeBusinessBean ==");
		}
	}
	
	@Override
	public List<ElementTypeVO> getElementTypesByModelStatusAndIsSerialized(Boolean isSerialized, String elementModelCode,Boolean elementTypeStatus) throws BusinessException {
		log.debug("== Inicia getElementTypeBySerorNotSerAndPrepaidorNotPrepaid/ElementTypeBusinessBean ==");
		try {
			List<ElementTypeVO> list = UtilsBusiness.convertList(daoElementType.getElementTypesByModelStatusAndIsSerialized( isSerialized,  elementModelCode, elementTypeStatus), ElementTypeVO.class);
			fillNameCodeToPrint(list);
			return list;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getElementTypeBySerorNotSerAndPrepaidorNotPrepaid/ElementTypeBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementTypeBySerorNotSerAndPrepaidorNotPrepaid/ElementTypeBusinessBean ==");
		}
	}	
}
