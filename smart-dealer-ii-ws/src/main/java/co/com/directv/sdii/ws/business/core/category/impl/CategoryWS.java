package co.com.directv.sdii.ws.business.core.category.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CategoryFacadeBeanLocal;
import co.com.directv.sdii.model.vo.CategoryVO;
import co.com.directv.sdii.ws.business.core.category.ICategoryWS;

/**
 * Servicio web que expone las operaciones relacionadas con Category
 * 
 * Fecha de Creación:  Oct 7, 2014
 * @author ssanabri
 * @version 1.0
 * 
 * @see facade.core.CategoryFacadeBeanLocal
 */
@MTOM(threshold=3072)
@WebService(serviceName="CategoryService",
		endpointInterface="co.com.directv.sdii.ws.business.core.category.ICategoryWS",
		targetNamespace="http://directvla.com.contract/ws/sdii/Category",
		portName="CategoryPort")
@Stateless()
public class CategoryWS implements ICategoryWS {

	@EJB
    private CategoryFacadeBeanLocal ejbRef;
	
	@Override
	public List<CategoryVO> getParentCategories() throws BusinessException{
		return ejbRef.getParentCategories();
	}

	@Override
	public List<CategoryVO> getChildrenCategories(Long parentId) throws BusinessException{
		return ejbRef.getChildrenCategories(parentId);
	}
	
}
