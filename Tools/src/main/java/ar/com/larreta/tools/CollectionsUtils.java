package ar.com.larreta.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;

public class CollectionsUtils {

	private static @Log Logger LOG;
	
	public static List removeEmtpyElements(List elements) {
		List newElements = null;
		try {
			if ((elements==null) || (elements.size()==0)){
				return elements;
			}
			
			newElements = new ArrayList();
			
			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Object object = (Object) it.next();
				if (((object instanceof String) && !(StringUtils.isEmpty((CharSequence) object))) ||
					(!(object instanceof String) && (object!=null))) {
					newElements.add(object);
				}
			}
		} catch (Exception e){
			LOG.error("Ocurrio un error removiendo elementos vacios.", e);
		}
		return newElements;
	}
	
}
