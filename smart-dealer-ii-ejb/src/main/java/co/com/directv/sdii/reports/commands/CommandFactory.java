/**
 * 
 */
package co.com.directv.sdii.reports.commands;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;


/**
 * @author jvelezmu
 *
 */
@Stateless(name="ICommandFactory", mappedName="ejb/CommandFactory")
public class CommandFactory implements ICommandFactory {

	private ICommand cmd;
	
	@EJB
	private  CMDWarehElemStockByElemTypeCodeOrDealerCodeOrWhCodeLocal cmdWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode;
	@EJB
	private  CMDActiveTransportCompaniesByCountryIdLocal cmdActiveTransportCompaniesByCountryId;
	@EJB
	private  CMDAllElementModelsLocal cmdAllElementModels;
	@EJB
	private  CMDWarehousesByCountryIdLocal cmdWarehousesByCountryId;
	@EJB
	private  CMDAllSuppliersLocal cmdAllSuppliers;
	@EJB
	private  CMDElementsByElementTypeLocal cmdElementsByElementType;
	@EJB
	private  CMDWarehousesByComplexFilterLocal cmdWarehousesByComplexFilter;
	@EJB
	private  CMDWhElementsByCriteriaLocal cmdWhElementsByCriteria;
	@EJB
	private  CMDWarehousesByAdjustNotSerElemCriteriaLocal cmdWarehousesByAdjustNotSerElemCriteria;
	@EJB
	private  CMDWhElementsAndNotSerPartRetByWarehouseIdLocal cmdWhElementsAndNotSerPartRetByWarehouseId;
	@EJB
	private  CMDWarehouseElementsBySearchFilterLocal cmdWarehouseElementsBySearchFilter;
	@EJB
	private  CMDImportLogItemsByImportLogIdLocal cmdImportLogItemsByImportLogId;
	@EJB
	private  CMDWareHouseElementHistoricalForSerializedElementLocal cmdWareHouseElementHistoricalForSerializedElement;
	@EJB
	private  CMDWarehouseElementsByFiltersLocal cmdWarehouseElementsByFilters;
	@EJB
	private  CMDElementsInImportLogLocal cmdElementsInImportLog;
	@EJB
	private  CMDImportLogsByIdAndCreationDateAndByCountryLocal cmdImportLogsByIdAndCreationDateAndByCountry;
	@EJB
	private  CMDAllImportLogsByStatusConfirmationLocal cmdAllImportLogsByStatusConfirmation;
	@EJB
	private  CMDReferenceElementItemsByReferenceIDLocal cmdReferenceElementItemsByReferenceID;
	@EJB
	private  CMDNotSerializedElementInReferenceLocal cmdNotSerializedElementInReference;
	@EJB
	private  CMDSerializedElementInReferenceLocal cmdSerializedElementInReference;
	@EJB
	private  CMDSerializedWhElementsByCriteriaLocal cmdSerializedWhElementsByCriteria;
	@EJB
	private  CMDWorkOrdersActiveAndSuspendByCountryIdLocal cmdWorkOrdersActiveAndSuspendByCountryId;
	@EJB
	private  CMDWorkOrdersByDealerDateCrewQBELocal cmdWorkOrdersByDealerDateCrewQBE;
	@EJB
	private  CMDWorkOrdersByCustomerQBELocal cmdWorkOrdersByCustomerQBE;
	@EJB
	private  CMDWorkOrdersByDealerWorkOrderQBELocal cmdWorkOrdersByDealerWorkOrderQBE;
	@EJB
	private  CMDWorkOrderByDealerLocal cmdWorkOrderByDealer;
	@EJB
	private  CMDImpLogByCriteriaLocal cmdImpLogByCriteria;
	@EJB
	private  CMDWareHouseElementsByFiltersAndIsSerializedLocal cmdWareHouseElementsByFiltersAndIsSerialized;
	@EJB
	private  CMDGenerateCrewWorkOrdersPDFLocal cmdGenerateCrewWorkOrdersPDF;
	@EJB
	private  CMDEmployeeByChriteriaLocal cmdEmployeeByChriteria;
	@EJB
	private  CMDDealerCoveragesLocal cmdDealerCoverages;
	@EJB
	private  CMDDealerConfCoveragesLocal cmdDealerConfCoverages;
	@EJB
	private  CMDDealerDetailsLocal cmdDealerDetails;
	@EJB
	private  CMDDealerServiceSubCatWthPcLocal cmdDealerServiceSubCatWthPc;
	@EJB
	private  CMDDealerBuildsLocal cmdDealerBuilds;
	@EJB
	private  CMDAllWarehouseTypesLocal cmdAllWarehouseTypes;
	@EJB
	private  CMDAllWarehousesByCountryIdLocal cmdAllWarehousesByCountryId;
	@EJB
	private  CMDAllMovementTypesLocal cmdAllMovementTypes;
	@EJB
	private  CMDAllElementClassesLocal cmdAllElementClasses;
	@EJB
	private  CMDAllElementBrandsLocal cmdAllElementBrands;
	@EJB
	private  CMDAllMeasureUnitsLocal cmdAllMeasureUnits;
	@EJB
	private  CMDAllElementTypesLocal cmdAllElementTypes;
	@EJB
	private  CMDWarehouseQANOTSerializedElementsLocal cmdWarehouseQANOTSerializedElements;
	@EJB
	private  CMDWarehouseQASerializedElementsLocal cmdWarehouseQASerializedElements;
	@EJB
	private  CMDSpecialCommentsByReferenceIdLocal cmdSpecialCommentsByReferenceId;
	@EJB
	private  CMDImportLogInconsistencesByCriteriaLocal cmdImportLogInconsistencesByCriteria;
	@EJB
	private  CMDPreloadRefByWhSourceLocal cmdPreloadRefByWhSource;
	@EJB
	private  CMDSerializedElementbyDealerSerialCodeTypeElementLocal cmdSerializedElementbyDealerSerialCodeTypeElement;
	@EJB
	private  CMDPreLoadReferenceToPrintByIDLocal cmdPreLoadReferenceToPrintByID;
	@EJB
	private  CMDPreLoadImportLogToPrintByIDLocal cmdPreLoadImportLogToPrintByID;
	@EJB
	private  CMDGetRefQtyCtrlItemsByReferenceLocal cmdGetRefQtyCtrlItemsByReference;
	@EJB
	private  CMDPreLoadMovedElementSerializedByLinkedOrSerialCodeLocal cmdPreLoadMovedElementSerializedByLinkedOrSerialCode;
	@EJB
	private  CMDPreLoadHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumberLocal cmdPreLoadHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber;
	@EJB
	private  CMDQuantityWarehouseElementsSummariesByFiltersLocal cmdQuantityWarehouseElementsSummariesByFilters;
	@EJB
	private  CMDQuantityWarehouseElementsDetailsByFiltersLocal cmdQuantityWarehouseElementsDetailsByFilters;
	@EJB
	private  CMDImportLogItemsByImportLogIdAndIsSerializedLocal cmdImportLogItemsByImportLogIdAndIsSerialized;
	@EJB
	private  CMDImportLogInconsistencesByImportLogIdLocal cmdImportLogInconsistencesByImportLogId;
	@EJB
	private  CMDGetReferenceModificationsByReferenceIDLocal cmdGetReferenceModificationsByReferenceID;
	@EJB
	private  CMDGetReferenceInconsistenciesOpenedByReferenceIdLocal cmdGetReferenceInconsistenciesOpenedByReferenceId;
	@EJB
	private  CMDGetReferenceInconsistenciesClosedByReferenceIdLocal cmdGetReferenceInconsistenciesClosedByReferenceId;
	@EJB
	private  CMDGetReferenceElementItemsByReferenceIDAndSerLocal cmdGetReferenceElementItemsByReferenceIDAndSer;
	@EJB
	private  CMDGetReferenceElementItemsByReferenceIDAndNotSerLocal cmdGetReferenceElementItemsByReferenceIDAndNotSer;
	@EJB
	private  CMDGetElementNotSerializedFromWarehouseLocal cmdGetElementNotSerializedFromWarehouse;
	@EJB
	private  CMDGetReferenceElementItemsByReferenceIDAndNotSerWHLocal cmdGetReferenceElementItemsByReferenceIDAndNotSerWH;
	@EJB
	private  CMDGetWarehouseElementsByWarehouseLocal cmdGetWarehouseElementsByWarehouse;
	@EJB
	private  CMDGetNotSerializedElementsLastByDealerOrCrewOrWhOrWhTypeLocal cmdGetNotSerializedElementsLastByDealerOrCrewOrWhOrWhType;
	@EJB
	private  CMDGetSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCodeLocal cmdGetSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCode;
	@EJB
	private  CMDGetWarehouseElementsSummariesByFilterLocal cmdGetWarehouseElementsSummariesByFilter;
	
	

	
	public CommandFactory(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.reports.commands.ICommandFactory#getCommand(java.lang.String)
	 */
	@Override
	public ICommand getCommand(String cmdName) throws BusinessException {
        try {
        	
        	if (cmdName.equals("getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode")){		
        		cmd = (ICommand) cmdWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode;
        	} else if (cmdName.equals("getActiveTransportCompaniesByCountryId")){	
        		cmd = (ICommand) cmdActiveTransportCompaniesByCountryId;
        	} else if (cmdName.equals("getAllElementModels")){	
        		cmd = (ICommand) cmdAllElementModels;
        	} else if (cmdName.equals("getWarehousesByCountryId")){	
        		cmd = (ICommand) cmdWarehousesByCountryId;
        	} else if (cmdName.equals("getAllSuppliers")){	
        		cmd = (ICommand) cmdAllSuppliers;
        	} else if (cmdName.equals("getElementsByElementType")){	
        		cmd = (ICommand) cmdElementsByElementType;
        	} else if (cmdName.equals("getWarehousesByComplexFilter")){	
        		cmd = (ICommand) cmdWarehousesByComplexFilter;
        	} else if (cmdName.equals("getWhElementsByCriteria")){	
        		cmd = (ICommand) cmdWhElementsByCriteria;
        	} else if (cmdName.equals("getWarehousesByAdjustNotSerElemCriteria")){	
        		cmd = (ICommand) cmdWarehousesByAdjustNotSerElemCriteria;
        	} else if (cmdName.equals("getWhElementsAndNotSerPartRetByWarehouseId")){	
        		cmd = (ICommand) cmdWhElementsAndNotSerPartRetByWarehouseId;
        	} else if (cmdName.equals("getWarehouseElementsBySearchFilter")){	
        		cmd = (ICommand) cmdWarehouseElementsBySearchFilter;
        	} else if (cmdName.equals("getImportLogItemsByImportLogId")){	
        		cmd = (ICommand) cmdImportLogItemsByImportLogId;
        	} else if (cmdName.equals("getWareHouseElementHistoricalForSerializedElement")){	
        		cmd = (ICommand) cmdWareHouseElementHistoricalForSerializedElement;
        	} else if (cmdName.equals("getWarehouseElementsByFilters")){	
        		cmd = (ICommand) cmdWarehouseElementsByFilters;
        	} else if (cmdName.equals("getElementsInImportLogAndSerialize")){	
        		cmd = (ICommand) cmdElementsInImportLog;
        	} else if (cmdName.equals("getElementsInImportLogAndNotSerialize")){	
        		cmd = (ICommand) cmdElementsInImportLog;
        	} else if (cmdName.equals("getImportLogsByIdAndCreationDateAndByCountry")){	
        		cmd = (ICommand) cmdImportLogsByIdAndCreationDateAndByCountry;
        	} else if (cmdName.equals("getAllImportLogsByStatusConfirmation")){	
        		cmd = (ICommand) cmdAllImportLogsByStatusConfirmation;
        	} else if (cmdName.equals("getReferenceElementItemsByReferenceID")){	
        		cmd = (ICommand) cmdReferenceElementItemsByReferenceID;
        	} else if (cmdName.equals("getNotSerializedElementInReference")){	
        		cmd = (ICommand) cmdNotSerializedElementInReference;
        	} else if (cmdName.equals("getSerializedElementInReference")){	
        		cmd = (ICommand) cmdSerializedElementInReference;
        	} else if (cmdName.equals("getSerializedWhElementsByCriteria")){	
        		cmd = (ICommand) cmdSerializedWhElementsByCriteria;
        	} else if (cmdName.equals("getWorkOrdersActiveAndSuspendByCountryId")){	
        		cmd = (ICommand) cmdWorkOrdersActiveAndSuspendByCountryId;
        	} else if (cmdName.equals("getWorkOrdersByDealerDateCrewQBE")){	
        		cmd = (ICommand) cmdWorkOrdersByDealerDateCrewQBE;
        	} else if (cmdName.equals("getWorkOrdersByCustomerQBE")){	
        		cmd = (ICommand) cmdWorkOrdersByCustomerQBE;
        	} else if (cmdName.equals("getWorkOrdersByDealerWorkOrderQBE")){	
        		cmd = (ICommand) cmdWorkOrdersByDealerWorkOrderQBE;
        	} else if (cmdName.equals("getWorkOrderByDealer")){	
        		cmd = (ICommand) cmdWorkOrderByDealer;
        	} else if (cmdName.equals("getImportLogByCriteria")){	
        		cmd = (ICommand) cmdImpLogByCriteria;
        	} else if (cmdName.equals("getWarehouseElementsByFiltersAndIsSerialized")){	
        		cmd = (ICommand) cmdWareHouseElementsByFiltersAndIsSerialized;
        	} else if (cmdName.equals("getWarehouseElementsByFiltersAndIsNotSerialized")){	
        		cmd = (ICommand) cmdWareHouseElementsByFiltersAndIsSerialized;
        	}  else if (cmdName.equals("getNotSerializedWhElementsByCriteria")){	
        		cmd = (ICommand) cmdWhElementsByCriteria;
        	}  else if (cmdName.equals("generateCrewWorkOrdersPDF")){	
        		cmd = (ICommand) cmdGenerateCrewWorkOrdersPDF;
        	}  else if (cmdName.equals("getEmployeeByChriteria")){	
        		cmd = (ICommand) cmdEmployeeByChriteria;
        	} else if (cmdName.equals("getDealerCoverageByCountry")){	
        		cmd = (ICommand) cmdDealerCoverages;
        	} else if (cmdName.equals("getDealerConfCoverageByCountry")){	
        		cmd = (ICommand) cmdDealerConfCoverages;
        	}else if (cmdName.equals("getDealerDetailByCountry")){	
        		cmd = (ICommand) cmdDealerDetails;
        	}else if (cmdName.equals("getDealerServiceSubCatWhtPc")){	
        		cmd = (ICommand) cmdDealerServiceSubCatWthPc;
        	}else if (cmdName.equals("getDealerBuilds")){	
        		cmd = (ICommand) cmdDealerBuilds;
        	}else if (cmdName.equals("getAllWarehouseTypes")){	
        		cmd = (ICommand) cmdAllWarehouseTypes;
        	}else if (cmdName.equals("getAllWarehousesByCountryId")){	
        		cmd = (ICommand) cmdAllWarehousesByCountryId;
        	}else if (cmdName.equals("getAllMovementTypes")){	
        		cmd = (ICommand) cmdAllMovementTypes;
        	}else if (cmdName.equals("getAllElementClass")){	
        		cmd = (ICommand) cmdAllElementClasses;
        	}else if (cmdName.equals("getAllElementBrand")){	
        		cmd = (ICommand) cmdAllElementBrands;
        	}else if (cmdName.equals("getAllMeasureUnit")){	
        		cmd = (ICommand) cmdAllMeasureUnits;
        	}else if (cmdName.equals("getAllElementType")){	
        		cmd = (ICommand) cmdAllElementTypes;
        	}else if (cmdName.equals("getWarehouseQANOTSerializedElements")){	
        		cmd = (ICommand) cmdWarehouseQANOTSerializedElements;
        	}else if (cmdName.equals("getWarehouseQASerializedElements")){	
        		cmd = (ICommand) cmdWarehouseQASerializedElements;
        	}else if (cmdName.equals("getSpecialCommentsByReferenceId")){	
        		cmd = (ICommand) cmdSpecialCommentsByReferenceId;
        	}else if (cmdName.equals("getImportLogInconsistencesByCriteria")){	
        		cmd = (ICommand) cmdImportLogInconsistencesByCriteria;
        	}else if (cmdName.equals("getPreloadReferenceByWhSource")){	
        		cmd = (ICommand) cmdPreloadRefByWhSource;
        	}else if (cmdName.equals(CodesBusinessEntityEnum.CMD_REPORT_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT.getCodeEntity())){	
        		cmd = (ICommand) cmdSerializedElementbyDealerSerialCodeTypeElement;
        	}else if (cmdName.equals("getInfoPrintReferenceById")){	
        		cmd = (ICommand) cmdPreLoadReferenceToPrintByID;
        	}else if (cmdName.equals("getInfoPrintImportLogById")){	
        		cmd = (ICommand) cmdPreLoadImportLogToPrintByID;
        	}else if (cmdName.equals("getRefQtyCtrlItemsByReference")){	
        		cmd = (ICommand) cmdGetRefQtyCtrlItemsByReference;
        	}else if (cmdName.equals("getMovedWareHouseElementSerializedByLinkedOrSerialCode")){	
        		cmd = (ICommand) cmdPreLoadMovedElementSerializedByLinkedOrSerialCode;
        	}else if (cmdName.equals("getWarehouseElementsByCustomerIdSerialAndDatesRange")){	
        		cmd = (ICommand) cmdPreLoadHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber;
        	}else if (cmdName.equals("getQuantityWarehouseElementsSummariesByFilters")){	
        		cmd = (ICommand) cmdQuantityWarehouseElementsSummariesByFilters;
        	}else if (cmdName.equals("getQuantityWarehouseElementsDetailsByFilters")){	
        		cmd = (ICommand) cmdQuantityWarehouseElementsDetailsByFilters;
        	}else if (cmdName.equals("getItemsSerializedElements")){	
        		cmd = (ICommand) cmdImportLogItemsByImportLogIdAndIsSerialized;
        	}else if (cmdName.equals("getItemsNotSerializedElements")){	
        		cmd = (ICommand) cmdImportLogItemsByImportLogIdAndIsSerialized;
        	}else if (cmdName.equals("getImportLogInconsistencesByImportLogId")){	
        		cmd = (ICommand) cmdImportLogInconsistencesByImportLogId;
        	}else if (cmdName.equals("getReferenceModificationsByReferenceID")){	
        		cmd = (ICommand) cmdGetReferenceModificationsByReferenceID;
        	}else if (cmdName.equals("getReferenceInconsistenciesOpenedByReferenceId")){	
        		cmd = (ICommand) cmdGetReferenceInconsistenciesOpenedByReferenceId;
        	}else if (cmdName.equals("getReferenceInconsistenciesClosedByReferenceId")){	
        		cmd = (ICommand) cmdGetReferenceInconsistenciesClosedByReferenceId;
        	}else if (cmdName.equals("getReferenceElementItemsByReferenceIDAndSer")){	
        		cmd = (ICommand) cmdGetReferenceElementItemsByReferenceIDAndSer;
        	}else if (cmdName.equals("getReferenceElementItemsByReferenceIDAndNotSer")){	
        		cmd = (ICommand) cmdGetReferenceElementItemsByReferenceIDAndNotSer;
        	}else if (cmdName.equals("getReferenceElementItemsByReferenceIDAndNotSerModify")){	
        		cmd = (ICommand) cmdGetReferenceElementItemsByReferenceIDAndNotSer;
        	}else if (cmdName.equals("getElementNotSerializedFromWarehouse")){	
        		cmd = (ICommand) cmdGetElementNotSerializedFromWarehouse;
        	}else if (cmdName.equals("getReferenceElementItemsByReferenceIDAndNotSerWH")){	
        		cmd = (ICommand) cmdGetReferenceElementItemsByReferenceIDAndNotSerWH;
        	}else if (cmdName.equals("getWarehouseElementsByWarehouse")){	
        		cmd = (ICommand) cmdGetWarehouseElementsByWarehouse;
        	}else if (cmdName.equals("getNotSerializedElementsLastByDealerOrCrewOrWhOrWhType")){	
        		cmd = (ICommand) cmdGetNotSerializedElementsLastByDealerOrCrewOrWhOrWhType;
        	}else if (cmdName.equals("getSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCode")){	
        		cmd = (ICommand) cmdGetSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCode;
        	}else if (cmdName.equals("cmdGetWarehouseElementsSummariesByFilter")){	
        		cmd = (ICommand) cmdGetWarehouseElementsSummariesByFilter;
        		
        		
        	}else{      		
        		throw new BusinessException("No existe la operacion para el comando que se trata de ejecutar [" + cmdName + "]");
        	}
        } catch (PropertiesException e) {
			throw new BusinessException("Error leyendo el archivo de propiedades al momento de buscar el comando [" + cmdName + "]");
		}
		
		return cmd;
	}
}
