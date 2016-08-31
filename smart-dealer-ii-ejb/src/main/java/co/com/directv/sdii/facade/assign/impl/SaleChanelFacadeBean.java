package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.SaleChanelBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.SaleChanelFacadeBeanLocal;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.collection.SaleChannelResponse;
import co.com.directv.sdii.model.vo.SaleChanelVO;
import co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD SaleChanel
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.SaleChanelFacadeLocal
 */
@Stateless(name="SaleChanelFacadeLocal",mappedName="ejb/SaleChanelFacadeLocal")
public class SaleChanelFacadeBean implements SaleChanelFacadeBeanLocal {

		
    @EJB(name="SaleChanelBusinessBeanLocal", beanInterface=SaleChanelBusinessBeanLocal.class)
    private SaleChanelBusinessBeanLocal businessSaleChanel;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SaleChanelFacadeLocal#getAllSaleChanels()
     */
    public List<SaleChanelVO> getAllSaleChanels() throws BusinessException {
    	return businessSaleChanel.getAllSaleChanels();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SaleChanelFacadeLocal#getSaleChanelsByID(java.lang.Long)
     */
    public SaleChanelVO getSaleChanelByID(Long id) throws BusinessException {
    	return businessSaleChanel.getSaleChanelByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SaleChanelFacadeLocal#createSaleChanel(co.com.directv.sdii.model.vo.SaleChanelVO)
	 */
	public void createSaleChanel(SaleChanelVO obj) throws BusinessException {
		businessSaleChanel.createSaleChanel(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SaleChanelFacadeLocal#updateSaleChanel(co.com.directv.sdii.model.vo.SaleChanelVO)
	 */
	public void updateSaleChanel(SaleChanelVO obj) throws BusinessException {
		businessSaleChanel.updateSaleChanel(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SaleChanelFacadeLocal#deleteSaleChanel(co.com.directv.sdii.model.vo.SaleChanelVO)
	 */
	public void deleteSaleChanel(SaleChanelVO obj) throws BusinessException {
		businessSaleChanel.deleteSaleChanel(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.SaleChanelFacadeBeanLocal#deleteSaleChanelById(java.lang.Long)
	 */
	@Override
	public void deleteSaleChanelById(Long saleChannelId)
			throws BusinessException {
		businessSaleChanel.deleteSaleChanelById(saleChannelId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.SaleChanelFacadeBeanLocal#getSaleChannelsByFilters(co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	@Override
	public SaleChannelResponse getSaleChannelsByFilters(
			GetSaleChannelsByFiltersRequestDTO request,
			RequestCollectionInfoDTO requestCollectionInfo)
			throws BusinessException {
		return businessSaleChanel.getSaleChannelsByFilters(request, requestCollectionInfo);
	}
}
