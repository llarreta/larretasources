package co.com.directv.sdii.assign.schedule.dto;

import java.io.Serializable;

/**
 * Encapsula la informaci�n de la carga de trabajo que puede soportar un dealer en
 * una fecha, jornada y super categor�a de servicio, as� como la carga de trabajo
 * actual.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DealerWorkLoad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1394285237615406498L;
	/**
	 * cantidad de trabajos que tiene asignada la compa��a instaladora
	 */
	private int usedCapacity;
	private DealerWorkCapacity dealerWorkCapacity;

	public DealerWorkLoad(){

	}
	

	public DealerWorkLoad(int usedCapacity, DealerWorkCapacity workCapacity) {
		super();
		this.usedCapacity = usedCapacity;
		this.dealerWorkCapacity = workCapacity;
	}



	public int getUsedCapacity() {
		return usedCapacity;
	}



	public void setUsedCapacity(int usedCapacity) {
		this.usedCapacity = usedCapacity;
	}



	public DealerWorkCapacity getDealerWorkCapacity() {
		return dealerWorkCapacity;
	}



	public void setDealerWorkCapacity(DealerWorkCapacity workCapacity) {
		this.dealerWorkCapacity = workCapacity;
	}



	public void finalize() throws Throwable {

	}
}