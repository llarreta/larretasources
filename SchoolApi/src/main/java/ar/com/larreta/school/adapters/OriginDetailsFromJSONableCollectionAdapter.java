package ar.com.larreta.school.adapters;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.school.messages.DetailData;
import ar.com.larreta.school.persistence.Detail;
import ar.com.larreta.school.persistence.Price;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.tools.BeanUtils;
import ar.com.larreta.tools.StandardAdapter;

@Component("OriginDetailsFromJSONableCollectionAdapter")
public class OriginDetailsFromJSONableCollectionAdapter extends StandardAdapter {


	@Autowired
	private BeanUtils beanUtils;
	
	@Autowired
	protected ApplicationContext applicationContext;

	@Override
	public String getPropertyTarget(String propertyName) {
		return "prices";
	}
	
	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws PersistenceException  {
		JSONableCollection<DetailData> details = (JSONableCollection<DetailData>) toAdapt;
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
			return prices;
		}
		
		return null;		
		
	}

}
