package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.DocumentClassBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.DocumentClassFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DocumentClassVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DocumentClass 
 * 
 * Fecha de Creación: 16/11/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.DocumentClassFacadeLocal  
 */
@Stateless(name="DocumentClassFacadeBeanLocal",mappedName="ejb/DocumentClassFacadeBeanLocal")
public class DocumentClassFacadeBean implements DocumentClassFacadeBeanLocal {

		
    @EJB(name="DocumentClassBusinessBeanLocal", beanInterface=DocumentClassBusinessBeanLocal.class)
    private DocumentClassBusinessBeanLocal businessDocumentClass;
    
   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.stock.DocumentClassFacadeBeanLocal#getAllDocumentClass()
     */
    public List<DocumentClassVO> getAllDocumentClass() throws BusinessException {
    	return businessDocumentClass.getAllDocumentClass();
    }
    
}
