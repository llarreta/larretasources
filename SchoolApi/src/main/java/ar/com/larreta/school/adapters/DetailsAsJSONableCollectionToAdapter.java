package ar.com.larreta.school.adapters;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ar.com.larreta.school.messages.DetailData;
import ar.com.larreta.school.messages.ObligationData;
import ar.com.larreta.school.persistence.Detail;
import ar.com.larreta.school.persistence.Obligation;
import ar.com.larreta.school.persistence.Price;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.tools.Adapter;
import ar.com.larreta.tools.BeanUtils;
import ar.com.larreta.tools.StandardAdapter;

@Component("DetailsAsJSONableCollectionToAdapter")
public class DetailsAsJSONableCollectionToAdapter extends StandardAdapter {

	@Autowired
	private BeanUtils beanUtils;
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	//FIXME: corregir
	@Override
	public Object process(Object toAdapt, Class type, Class[] generics)  {
		ObligationData obligationData = (ObligationData) toAdapt;
		//Obligation obligation = (Obligation) target;
		
		JSONableCollection<DetailData> details = obligationData.getDetails();
		if (details!=null){
			Iterator<DetailData> it = details.iterator();
			Set<Detail> newsDetails = new HashSet<>();
			while (it.hasNext()) {
				DetailData detailData = (DetailData) it.next();
				Detail detail = applicationContext.getBean(Detail.class);
				newsDetails.add(detail);
				beanUtils.copy(detailData, detail);
			}
			Price price = applicationContext.getBean(Price.class);
			price.setValidityStartDate(new Date());
			price.setDetails(newsDetails); 
			Set<Price> prices = new HashSet<>();
			prices.add(price);
			//obligation.setPrices(prices);
			
		}
		
		return null;		
		
	}

}
