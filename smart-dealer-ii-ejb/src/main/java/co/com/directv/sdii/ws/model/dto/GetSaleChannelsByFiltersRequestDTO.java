/**
 * Creado 13/07/2011 15:26:40
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

/**
 * Encapsula las propiedades de la petición para el servicio web que consulta
 * los canales de venta dados los criterios de búsqueda
 * 
 * Fecha de Creación: 13/07/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class GetSaleChannelsByFiltersRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6077453696969121751L;

	private Long countryId;
	
	private String saleChannelCode;
	
	private String saleChannelName;
	
	private Long sellerDealerCode;
	
	private Long installerDealerCode;

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getSaleChannelCode() {
		return saleChannelCode;
	}

	public void setSaleChannelCode(String saleChannelCode) {
		this.saleChannelCode = saleChannelCode;
	}

	public String getSaleChannelName() {
		return saleChannelName;
	}

	public void setSaleChannelName(String saleChannelName) {
		this.saleChannelName = saleChannelName;
	}

	public Long getSellerDealerCode() {
		return sellerDealerCode;
	}

	public void setSellerDealerCode(Long sellerDealerCode) {
		this.sellerDealerCode = sellerDealerCode;
	}

	public Long getInstallerDealerCode() {
		return installerDealerCode;
	}

	public void setInstallerDealerCode(Long installerDealerCode) {
		this.installerDealerCode = installerDealerCode;
	}
	
	
}
