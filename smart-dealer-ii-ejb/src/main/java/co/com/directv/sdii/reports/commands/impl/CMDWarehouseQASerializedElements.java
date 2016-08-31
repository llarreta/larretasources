package co.com.directv.sdii.reports.commands.impl;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWarehouseQASerializedElementsLocal;
import co.com.directv.sdii.reports.commands.ICommand;

@Stateless(mappedName = "ejb/CMDWarehouseQASerializedElements")
public class CMDWarehouseQASerializedElements  extends BaseCommand  implements ICommand,CMDWarehouseQASerializedElementsLocal {
	
	@EJB
	private WarehouseElementFacadeBeanLocal warehouseElementFacadeBeanLocal;
	
	@EJB
	private DealersDAOLocal dealerLocal;

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			HashMap<String,String> map = getParams(args);

			//Carga de argumentos
			Long dealerId = map.get("dealerId")!= null ? Long.parseLong(map.get("dealerId").toString()) : null ;
			String elementType = map.get("elementType")!= null ? map.get("elementType").toString() : null ;
			String serialCode = map.get("serialCode")!= null ? map.get("serialCode").toString() : null ;
			//Carga de dealer
			Dealer dealer = dealerLocal.getDealerByID(dealerId);
			
			WareHouseElementResponse serviceResponse = warehouseElementFacadeBeanLocal.getWarehouseQASerializedElements(dealer, elementType, serialCode, null);
			List<WarehouseElementVO> lista = serviceResponse.getWareHouseElementsVO();
			return (List<T>) lista;
		} catch(Throwable e){
			throw this.manageException(e);
		}
	}

	@Override
	public List<String> getFieldList() {
		return null;
	}

	@Override
	public void setFieldList(List<String> fieldList) {
		
	}

}
