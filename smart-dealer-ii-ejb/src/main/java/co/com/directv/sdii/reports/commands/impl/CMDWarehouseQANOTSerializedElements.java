package co.com.directv.sdii.reports.commands.impl;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWarehouseQANOTSerializedElementsLocal;
import co.com.directv.sdii.reports.commands.ICommand;

@Stateless(mappedName = "ejb/CMDWarehouseQANOTSerializedElements")
public class CMDWarehouseQANOTSerializedElements  extends BaseCommand  implements ICommand,CMDWarehouseQANOTSerializedElementsLocal {
	
	@EJB
	private WarehouseElementFacadeBeanLocal warehouseElementFacadeBeanLocal;

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			HashMap<String,String> map = getParams(args);
			Long dealerId = map.get("dealerId")!= null ? Long.parseLong(map.get("dealerId").toString()) : null ;
			WareHouseElementResponse serviceResponse = warehouseElementFacadeBeanLocal.getWarehouseQANOTSerializedElements(dealerId, null);
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
