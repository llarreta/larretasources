package ar.com.larreta.stepper.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.mystic.model.DocumentType;
import ar.com.larreta.stepper.DocumentTypeLoadBusiness;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(DocumentTypeLoadBusiness.BUSINESS_NAME)
@Transactional
public class DocumentTypeLoadBusinessImpl extends LoadParametricEntityBusinessImpl<ParametricData, DocumentType>
		implements DocumentTypeLoadBusiness {
	
}


