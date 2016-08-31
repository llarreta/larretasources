package co.com.directv.sdii.assign.schedule.dto;

import java.io.Serializable;

/**
 * encapsula la capacidad de trabajo que tiene una compa��a
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DealerWorkCapacity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6883879931380896960L;
	/**
	 * n�mero m�ximo de trabajos que puede realizar la compa��a
	 */
	private int maxCapacity;
	private DealerWorkCapacityCriteria capacityCriteria;

	public DealerWorkCapacity(){

	}

	public DealerWorkCapacity(int maxCapacity,
			DealerWorkCapacityCriteria capacityCriteria) {
		super();
		this.maxCapacity = maxCapacity;
		this.capacityCriteria = capacityCriteria;
	}



	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}


	public DealerWorkCapacityCriteria getCapacityCriteria() {
		return capacityCriteria;
	}



	public void setCapacityCriteria(DealerWorkCapacityCriteria capacityCriteria) {
		this.capacityCriteria = capacityCriteria;
	}


	public void finalize() throws Throwable {

	}
}