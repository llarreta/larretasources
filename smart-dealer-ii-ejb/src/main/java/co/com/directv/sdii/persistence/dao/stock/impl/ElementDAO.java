package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkFilterVO;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkVO;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.collection.BuildingElementHistoryResponse;
import co.com.directv.sdii.model.pojo.collection.ElementResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Element
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Element
 * @see co.com.directv.sdii.model.hbm.Element.hbm.xml
 */
@Stateless(name="ElementDAOLocal",mappedName="ejb/ElementDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementDAO extends BaseDao implements ElementDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ElementDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementsDAOLocal#createElement(co.com.directv.sdii.model.pojo.Element)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createElement(Element obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createElement/ElementDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el Element ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElement/ElementDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementsDAOLocal#updateElement(co.com.directv.sdii.model.pojo.Element)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateElement(Element obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateElement/ElementDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Element ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElement/ElementDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementsDAOLocal#deleteElement(co.com.directv.sdii.model.pojo.Element)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteElement(Element obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteElement/ElementDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Element entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el Element ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElement/ElementDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementsDAOLocal#getElementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Element getElementByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getElementByID/ElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Element.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Element) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementByID/ElementDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementsDAOLocal#getAllElements()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Element> getAllElements(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllElements/ElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" from " + Element.class.getName() + " e ");
        	stringQuery.append(" where e.country.id = :aCountryId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllElements/ElementDAO ==");
        }
    }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal#getElementsByElementType(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementResponse getElementsByElementType(Long idElementType, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementsByElementType/ElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	//Paginacion
        	stringCount.append("select count(*) ");
        	//Cuerpo de la consulta
        	stringQuery.append("from ");
        	stringQuery.append(Element.class.getName());
        	stringQuery.append(" entity where entity.elementType.id = :anIdElementType");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anIdElementType", idElementType);

          //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setLong("anIdElementType", idElementType);
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ElementResponse response = new ElementResponse();
        	List<Element> elements = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elements.size(), recordQty.intValue() );
        	response.setElements( elements );
        	
            return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementsByElementType/ElementDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal#getElementsByElementTypeCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Element getElementsByElementTypeCodeAndIsSerialized(String codeElement,boolean isSerialized,Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementsByElementTypeCode/ElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" from " + Element.class.getName() + " entity ");
        	stringQuery.append(" where entity.elementType.typeElementCode = :aElementTypeCode");
        	stringQuery.append("       and entity.isSerialized = :aSerializedCode");
        	stringQuery.append("       and entity.country.id = :aCountryId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aElementTypeCode", codeElement);
            query.setLong("aCountryId", countryId);
            
            if(isSerialized)
            	query.setString("aSerializedCode", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
            else
            	query.setString("aSerializedCode", CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity());

            List<Element> elements = query.list();
            
            if(elements.isEmpty()){
            	return null;
            }
            return elements.get(0);

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementsByElementTypeCode/ElementDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal#getElementsHistoryOnBuildingCode(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public BuildingElementHistoryResponse getElementsHistoryOnBuildingCode(String ibsCode,Long countryId, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException{
		
		log.debug("== Inicio getElementsHistoryOnBuildingCode/ElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringSelect = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringOrder = new StringBuffer();
        	
        	
        	//Paginacion
        	stringCount.append("select count(*) ");
        	
        	stringSelect.append(" select whe ");
        	stringQuery.append("   from " + WarehouseElement.class.getName() + " whe ");
        	stringQuery.append("  where exists(select 1  ");
        	stringQuery.append("                 from " + WorkOrder.class.getName() + " w ");
        	stringQuery.append(" 				where w.buildingCode = :ibsCode ");
        	stringQuery.append(" 				      and w.customer.id = whe.warehouseId.customerId.id ");
        	stringQuery.append(" 				      and w.country.id = :aCountryId ) ");
        	stringQuery.append("         and whe.recordStatus.recordStatusCode = :recordStatus ");
        	
        	stringOrder.append(" order by whe.id desc ");
        	//Paginacion
        	Query countQuery = session.createQuery( stringCount.toString() + stringQuery.toString() );
        	Query query = session.createQuery(stringSelect.toString() + stringQuery.toString() + stringOrder.toString());
        	
            query.setString("ibsCode", ibsCode);
            countQuery.setString("ibsCode", ibsCode);
            
            query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC
        			.getCodeEntity());
            countQuery.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC
        			.getCodeEntity());
            
            query.setLong("aCountryId", countryId);
            countQuery.setLong("aCountryId", countryId);
            
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}        	
        	
        	BuildingElementHistoryResponse response = new BuildingElementHistoryResponse();
        	List<Object[]> objects = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), objects.size(), recordQty.intValue() );
        	response.setBuildingElements( objects );
        	
            return response;

        } catch (Throwable ex){
			log.error("== Error getElementsHistoryOnBuildingCode/ElementDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementsHistoryOnBuildingCode/ElementDAO ==");
        }
	}	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal#getSerializedElementsByElementTypeCodeAndSerial(java.lang.String,java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Serialized> getSerializedElementsByElementTypeCodeAndSerial(String codeElement , String serialCode , Long warehouseId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSerializedElementsByElementTypeCodeAndSerial/ElementDAO ==");
        Session session = super.getSession();

        try {
        	boolean isCodeElement = (codeElement != null && !codeElement.equals(""))? true:false;  
        	boolean isSerialCode = (serialCode != null && !serialCode.equals(""))? true:false;
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select we.serialized from ");
        	stringQuery.append(WarehouseElement.class.getName());
        	stringQuery.append(" we where ");
        	stringQuery.append("we.warehouseId.id = :warehouseId ");
        	stringQuery.append("and we.serialized.element.isSerialized = :elementSerialize ");
        	stringQuery.append("and we.recordStatus.recordStatusCode = :aWheStCode ");
        	if(isCodeElement)
        		stringQuery.append("and we.serialized.element.elementType.typeElementCode = :codeElement ");
        	else
        		stringQuery.append("and we.serialized.element.elementType.elementModel.elementClass.elementClassCode in (:classDeco,:classTarj) ");
        	if(isSerialCode)
        		stringQuery.append("and we.serialized.serialCode = :serialCode ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	if(isCodeElement)
        		query.setString("codeElement", codeElement);
        	else{
        		query.setString("classDeco", CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity());
        		query.setString("classTarj", CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity());
        	}
            if(isSerialCode)
            	query.setString("serialCode", serialCode);
            query.setString("elementSerialize", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity());
            query.setLong("warehouseId", warehouseId);
            query.setString("aWheStCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

            return query.list();

        } catch (Throwable ex){
			log.error("== Error getSerializedElementsByElementTypeCodeAndSerial/ElementDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedElementsByElementTypeCodeAndSerial/ElementDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal#getElementBySerialAndTypeAndRidAndLinkedSerial(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public Element getElementBySerialAndTypeAndRidAndLinkedSerial(
			String serial, String typeCode, String Rid, String LinkedSerial,Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSerializedBySerialAndElementType/SerializedDAO ==");
        Session session = super.getSession();
        
        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select entity.element from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" entity where " );
        	stringQuery.append(" entity.serialCode = :serialCode ");
        	stringQuery.append(" and entity.element.elementType.typeElementCode = :elementType ");
        	stringQuery.append(" and entity.element.country.id = :aCountryId ");
        	if( Rid != null && !Rid.equals("") ){
        		stringQuery.append(" and entity.ird = :Rid ");
        	}
        	if( LinkedSerial != null && !LinkedSerial.equals("")){
        		stringQuery.append(" and entity.serialized.serialCode = :LinkedSerial ");
        	}
        	Query query = session.createQuery(stringQuery.toString());
    		query.setString( "serialCode" , serial);
    		query.setString( "elementType" , typeCode );
    		query.setLong("aCountryId" , countryId );
    		if( Rid != null && !Rid.equals("") ){
    			query.setString( "Rid" , Rid );
        	}
        	if( LinkedSerial != null && !LinkedSerial.equals("")){
        		query.setString( "LinkedSerial" , LinkedSerial );
        	}
            
        	return (Element) query.uniqueResult();
            
        } catch (Throwable ex){
			log.error("== Error en getSerializedBySerialAndElementType/SerializedDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedBySerialAndElementType/SerializedDAO ==");
        }	
	}


	@Override
	public ElementSerializedLinkUnLinkVO getElementSerializedToLinkUnLink(
			ElementSerializedLinkUnLinkFilterVO elementSerializedLinkUnLinkFilterVO)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementSerializedToLinkUnLink/ElementDAO ==");
        boolean filterWarehouse = false;       
		try	 {
			//Obtiene la session
			Session session = super.getSession();
			StringBuilder stringQuery = new StringBuilder();
			stringQuery.append("select case when dealer2 = null then dealer1.dealerCode else dealer2.dealerCode end, ");
			stringQuery.append("case when dealer2 = null then dealer1.dealerName else dealer2.dealerName end, ");
			stringQuery.append("case when dealer2 <> null then  dealer1.dealerCode end, ");
			stringQuery.append("case when dealer2 <> null then dealer1.dealerName end, ");
			stringQuery.append("elementType.typeElementCode, elementType.typeElementName, elementType.elementModel.modelName, ");
			stringQuery.append("ser.serialCode, ser.elementId, ser.ird, link.serialCode,  linkElementModel.modelName, linkElementType.typeElementName, warehouse.id, ");
			stringQuery.append("case when dealer2 = null then dealer1.id else dealer2.id end, ");
			stringQuery.append("case when dealer2 <> null then  dealer1.id end, linkElement.id ");
			stringQuery.append("from WarehouseElement we inner join we.warehouseId warehouse ");
			stringQuery.append("inner join we.serialized ser left outer join ser.serialized link left outer join link.element linkElement ");
			stringQuery.append("left outer join linkElement.elementType linkElementType left outer join linkElementType.elementModel linkElementModel ");
			stringQuery.append("inner join warehouse.dealerId dealer1 left outer join dealer1.dealer dealer2 ");
			stringQuery.append("inner join ser.element element inner join element.elementType elementType ");
			stringQuery.append("where we.recordStatus.recordStatusCode = :recordStatus ");
			stringQuery.append("and elementType.typeElementCode = :elementType ");
			stringQuery.append("and UPPER(ser.serialCode) = UPPER(:serialCode) ");			
			stringQuery.append("and warehouse.country.id = :aCountryId ");
			if(elementSerializedLinkUnLinkFilterVO.getIdWarehouse()!=null && elementSerializedLinkUnLinkFilterVO.getIdWarehouse().longValue() !=0){
				stringQuery.append("and warehouse.id = :idBodega ");
				filterWarehouse = true;
			}
			Query query = session.createQuery(stringQuery.toString());
			query.setString("serialCode", elementSerializedLinkUnLinkFilterVO.getSerialCodeElement());
			query.setString("elementType", elementSerializedLinkUnLinkFilterVO.getCodeTypeElement() );
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setLong("aCountryId", elementSerializedLinkUnLinkFilterVO.getIdCountry());
			if(filterWarehouse){
				query.setLong("idBodega", elementSerializedLinkUnLinkFilterVO.getIdWarehouse());
			}
			Object[] obj =  (Object[]) query.uniqueResult();
			ElementSerializedLinkUnLinkVO elementSerializedLinkUnLinkVO = null;
			
			if(obj!=null){
				elementSerializedLinkUnLinkVO = new ElementSerializedLinkUnLinkVO();
				if(obj[0]!=null)
					elementSerializedLinkUnLinkVO.setCodeCompany(obj[0].toString());
				if(obj[1]!=null)
					elementSerializedLinkUnLinkVO.setNameCompany(obj[0].toString() +" "+ obj[1].toString());
				if(obj[2]!=null)
					elementSerializedLinkUnLinkVO.setCodeBranch(obj[2].toString());
				if(obj[3]!=null)
					elementSerializedLinkUnLinkVO.setNameBranch(obj[3].toString());
				if(obj[4]!=null)
					elementSerializedLinkUnLinkVO.setCodeTypeElement(obj[4].toString());
				if(obj[5]!=null)
					elementSerializedLinkUnLinkVO.setNameTypeElement(obj[5].toString());
				if(obj[6]!=null)
					elementSerializedLinkUnLinkVO.setModelElement(obj[6].toString());
				if(obj[7]!=null)
					elementSerializedLinkUnLinkVO.setSerialCodeElement(obj[7].toString());
				if(obj[8]!=null)
					elementSerializedLinkUnLinkVO.setElementId(new Long(obj[8].toString()));
				if(obj[9]!=null)
					elementSerializedLinkUnLinkVO.setIrdElement(obj[9].toString());
				if(obj[10]!=null)
					elementSerializedLinkUnLinkVO.setSerialCodeLink(obj[10].toString());
				if(obj[11]!=null)
					elementSerializedLinkUnLinkVO.setModelElementLink(obj[11].toString());
				if(obj[12]!=null)
					elementSerializedLinkUnLinkVO.setTypeElementLinkName(obj[12].toString());
				if(obj[13]!=null)
					elementSerializedLinkUnLinkVO.setWarehouseId(new Long(obj[13].toString()));
				if(obj[16]!=null)
					elementSerializedLinkUnLinkVO.setElementIdLink(new Long(obj[16].toString()));
				
				//Validar la compañia enviada
				if(elementSerializedLinkUnLinkFilterVO.getIdCompany()!=null&&elementSerializedLinkUnLinkFilterVO.getIdCompany().longValue()!=0&&elementSerializedLinkUnLinkFilterVO.getIdCompany()!=-1){
					Long idCompanyFound = null;
					if(obj[14]!=null){
						idCompanyFound = new Long(obj[14].toString());
						if(idCompanyFound.longValue()!=elementSerializedLinkUnLinkFilterVO.getIdCompany().longValue()){
							return null;
						}
					}else{
						return null;
					}
				}
				
				//Validar la sucursal
				if(elementSerializedLinkUnLinkFilterVO.getIdBranch()!=null&&elementSerializedLinkUnLinkFilterVO.getIdBranch().longValue()!=0&&elementSerializedLinkUnLinkFilterVO.getIdBranch()!=-1){
					Long idBranchFound = null;
					if(obj[15]!=null){
						idBranchFound = new Long(obj[15].toString());
						if(idBranchFound.longValue()!=elementSerializedLinkUnLinkFilterVO.getIdBranch().longValue()){
							return null;
						}
					}else{
						Long idCompanyFound = null;
						if(obj[14]!=null){
							idCompanyFound = new Long(obj[14].toString());
							if(idCompanyFound.longValue()!=elementSerializedLinkUnLinkFilterVO.getIdBranch().longValue()){
								return null;
							}
						}else{
							return null;
						}
						
					}
				}
			}
			
			
			return elementSerializedLinkUnLinkVO; 

		} catch (Throwable ex){
			log.error("== Error en getElementSerializedToLinkUnLink/ElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementSerializedToLinkUnLink/ElementDAO ==");
		}	
	}



	@Override
	public Long countElementByElementType(Long elementTypeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio countElementByElementType/ElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select count(*) from ");
        	stringQuery.append(WarehouseElement.class.getName());
        	stringQuery.append(" entity where entity.recordStatus.recordStatusCode = :recordStatusCode and entity.elementType.id = :elementTypeId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementTypeId", elementTypeId);
            query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

            return  (Long) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina countElementByElementType/ElementDAO ==");
        }
	}



	@Override
	public Long countElementByElementModel(Long elementModelId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio countElementByElementType/ElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select count(*) from ");
        	stringQuery.append(Element.class.getName());
        	stringQuery.append(" entity where entity.elementType.elementModel.id = :elementModelId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("elementModelId", elementModelId);

            return  (Long) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina countElementByElementType/ElementDAO ==");
        }
	}

}
