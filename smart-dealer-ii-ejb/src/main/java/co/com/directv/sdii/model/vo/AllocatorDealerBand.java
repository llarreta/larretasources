package co.com.directv.sdii.model.vo;

public class AllocatorDealerBand {

	private DealerVO dealer;
	
	private double lowerLimit;
	
	private double upperLimit;

	public DealerVO getDealer() {
		return dealer;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public AllocatorDealerBand(DealerVO dealer, double lowerLimit,
			double upperLimit) {
		super();
		this.dealer = dealer;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}
	
	
	
}
