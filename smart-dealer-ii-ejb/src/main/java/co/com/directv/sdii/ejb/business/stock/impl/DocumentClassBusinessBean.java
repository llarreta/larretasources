package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.DocumentClassBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DocumentClassVO;
import co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ElementType 
 * 
 * Fecha de Creación: 16/11/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.DocumentClassBusinessBeanLocal     
 */
@Stateless(name = "DocumentClassBusinessBeanLocal", mappedName = "ejb/DocumentClassBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentClassBusinessBean extends BusinessBase implements DocumentClassBusinessBeanLocal {

	@EJB(name = "DocumentClassDAOLocal", beanInterface = DocumentClassDAOLocal.class)
	private DocumentClassDAOLocal daoDocumentClass;
	
	private final static Logger log = UtilsBusiness.getLog4J(DocumentClassBusinessBean.class);

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.DocumentClassBusinessBeanLocal#getAllDocumentClass()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DocumentClassVO> getAllDocumentClass() throws BusinessException {
		log.debug("== Inicia getAllDocumentClass/DocumentClassBusinessBean ==");
		try {
			
			List<DocumentClassVO> list = UtilsBusiness.convertList(daoDocumentClass.getAllDocumentClass(), DocumentClassVO.class);
			return list;

		} catch (Throwable ex) {
				log.debug("== Error al tratar de ejecutar la operación getAllDocumentClass/DocumentClassBusinessBean ==",ex);
				throw this.manageException(ex);
		} finally {
				log.debug("== Termina getAllDocumentClass/DocumentClassBusinessBean ==");
		}
	}
	
}
