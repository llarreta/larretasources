package ar.com.larreta.rest.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.persistence.model.DocumentType;
import ar.com.larreta.rest.business.DocumentTypeLoadBusiness;
import ar.com.larreta.rest.messages.ParametricData;

@Service(DocumentTypeLoadBusiness.BUSINESS_NAME)
@Transactional
public class DocumentTypeLoadBusinessImpl extends LoadParametricEntityBusinessImpl<ParametricData, DocumentType>
		implements DocumentTypeLoadBusiness {
	
}


