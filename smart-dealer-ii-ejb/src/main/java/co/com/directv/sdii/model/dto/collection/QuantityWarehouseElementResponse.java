package co.com.directv.sdii.model.dto.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.reports.dto.WareHouseElementsReportDTO;

/**
 * 
 * Transporta Warehouse entre la capa de DAO, Business y Services. 
 * 
 * Fecha de Creación: 4/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class QuantityWarehouseElementResponse extends CollectionBase implements Serializable {

	public QuantityWarehouseElementResponse(
			List<WareHouseElementsReportDTO> wareHouseElementsReportDTO) {
		super();
		this.wareHouseElementsReportDTO = wareHouseElementsReportDTO;
		warehouseVO = new ArrayList<WarehouseVO>();
		quantityWarehouseElementsDTO = new ArrayList<QuantityWarehouseElementsDTO>();
		for(WareHouseElementsReportDTO wherdto: wareHouseElementsReportDTO){
			Double value = 0D;
			if(wherdto.getActualQuantity()!=null){
				value = wherdto.getActualQuantity();
			}
			String age = "";
			if(wherdto.getAge()!=null){
				age = wherdto.getAge().toString();
			}
			quantityWarehouseElementsDTO.add(new QuantityWarehouseElementsDTO(wherdto.getDealerId(),
					wherdto.getCompany(), wherdto.getBranch(),
					wherdto.getBranchId(), wherdto.getCrewId(), wherdto.getCrewName(),
					wherdto.getWhId(), wherdto.getWhTypeId(), wherdto.getWhTypeCode(), wherdto.getWhTypeName(), 
					wherdto.getWarehouseCode(), wherdto.getModelId(), wherdto.getModelName(),
					wherdto.getModelCode(), wherdto.getElementTypeId(), wherdto.getTypeElementCode(),
					wherdto.getElementTypeName(), value, null,
					wherdto.getSerialCode(), wherdto.getRid(), wherdto.getSerialCodeLinked(),
					null, null, null,
					null, null,
					null, null, age));

		}
	}
	
	
	public QuantityWarehouseElementResponse(
			List<QuantityWarehouseElementsDTO> quantityWarehouseElementsDTO, int a) {
		super();
		this.quantityWarehouseElementsDTO = quantityWarehouseElementsDTO;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2655965608586718690L;

	private List<QuantityWarehouseElementsDTO> quantityWarehouseElementsDTO;
	
	private List<WareHouseElementsReportDTO> wareHouseElementsReportDTO;

	private List<WarehouseVO> warehouseVO;
	
	
	
	/**
	 * Constructor: vacio
	 * @param quantityWarehouseElementsDTO
	 * @author
	 */
	public QuantityWarehouseElementResponse() {
		super();
	}
	

	/**
	 * @return the quantityWarehouseElementsDTO
	 */
	public List<QuantityWarehouseElementsDTO> getQuantityWarehouseElementsDTO() {
		return quantityWarehouseElementsDTO;
	}

	/**
	 * @param quantityWarehouseElementsDTO the quantityWarehouseElementsDTO to set
	 */
	public void setQuantityWarehouseElementsDTO(
			List<QuantityWarehouseElementsDTO> quantityWarehouseElementsDTO) {
		this.quantityWarehouseElementsDTO = quantityWarehouseElementsDTO;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<WareHouseElementsReportDTO> getWareHouseElementsReportDTO() {
		return wareHouseElementsReportDTO;
	}

	public void setWareHouseElementsReportDTO(
			List<WareHouseElementsReportDTO> wareHouseElementsReportDTO) {
		this.wareHouseElementsReportDTO = wareHouseElementsReportDTO;
	}

	public List<WarehouseVO> getWarehouseVO() {
		return warehouseVO;
	}

	public void setWarehouseVO(List<WarehouseVO> warehouseVO) {
		this.warehouseVO = warehouseVO;
	}
	
	
	
}
