package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CoreWOFacadeLocal;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDGenerateCrewWorkOrdersPDFLocal;
import co.com.directv.sdii.reports.commands.ICommand;

@Stateless(mappedName = "ejb/CMDGenerateCrewWorkOrdersPDF")
public class CMDGenerateCrewWorkOrdersPDF extends BaseCommand  implements ICommand,CMDGenerateCrewWorkOrdersPDFLocal {
	
	@EJB
	private CoreWOFacadeLocal coreWOFacade;

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			HashMap<String,String> map = getParams(args);
			String woIds = map.get("woIds")!= null ? map.get("woIds").toString() : null;
			String crewIds = map.get("crewIds")!= null ? map.get("crewIds").toString() : null;
			String stringWoIds[] = woIds != null ? woIds.split(",") : null;
			List<Long> workOrderIds = null;
			if( stringWoIds != null && stringWoIds.length > 0 ){
				workOrderIds = new ArrayList<Long>();
				for( String woId : stringWoIds ){
					workOrderIds.add( Long.valueOf( woId ) );
				}
			}
			List<Long> crewsIds = null;
			String stringCrewsIds[] = crewIds != null ? crewIds.split(",") : null; 
			if( stringCrewsIds != null && stringCrewsIds.length > 0 ){
				crewsIds = new ArrayList<Long>();
				for( String cId : stringCrewsIds ){
					crewsIds.add( Long.valueOf( cId ) );
				}
			}
			String filesNames = coreWOFacade.generateCrewWorkOrdersPDF(workOrderIds, crewsIds);
			String splitFileNames[] = filesNames.split(",");
			List<String> arrayFileNames = new ArrayList<String>();
			if( splitFileNames != null && splitFileNames.length > 0 ){				
				for( String name : splitFileNames ){
					arrayFileNames.add( name );
				}
			}
			return (List<T>) arrayFileNames;
		}catch(Throwable e){
			throw this.manageException(e);
		}
		
	}

	@Override
	public List<String> getFieldList() {
		return null;
	}

	@Override
	public void setFieldList(List<String> fieldList) {
		
	}

}
