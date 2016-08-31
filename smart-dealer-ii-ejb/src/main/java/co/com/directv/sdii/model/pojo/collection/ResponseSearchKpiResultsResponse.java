package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.assign.kpi.dto.ResponseSearchKpiResultsDTO;

/**
 * Clase utilizada para retornar los datos paginados de la consulta de kpiResult 
 * 
 * Fecha de CreaciÃ³n: 3/10/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ResponseSearchKpiResultsResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2655965608586718690L;

	/**
	 *  Lista de la consulta de kpiResult 
	 */
	private List<ResponseSearchKpiResultsDTO> response;

	public List<ResponseSearchKpiResultsDTO> getResponse() {
		return response;
	}

	public void setResponse(List<ResponseSearchKpiResultsDTO> response) {
		this.response = response;
	}
	
}
