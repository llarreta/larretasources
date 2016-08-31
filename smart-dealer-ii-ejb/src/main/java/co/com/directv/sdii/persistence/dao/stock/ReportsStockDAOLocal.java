package co.com.directv.sdii.persistence.dao.stock;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.WareHouseElementsReportDTO;

/**
 * CC053 - HSP Reportes de inventarios.
 * @author ssanabri
 *
 */
@Local
public interface ReportsStockDAOLocal {

	/**
	 * Metodo: permite obtener los datos del reporte de WAREHOUSE_ELEMENTS
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author
	 */
	public List<WareHouseElementsReportDTO> getWarehouseElements(Long countryId, RequestCollectionInfo requestInfo) throws DAOSQLException, DAOServiceException;
	

	/**
	 * Metodo: ACM-F-05_HSP_ReportesSC_CC053
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author
	 */
	public List<QuantityWarehouseElementsDTO> getWarehouseMovements(Long countryId, Date nowDate, RequestCollectionInfo requestInfo, QuantityWarehouseElementsDTO quantityWarehouseElementsDTO) throws DAOSQLException, DAOServiceException;
	
}
