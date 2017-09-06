package ar.com.larreta.school.business.students;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.ResponsibleType;
import ar.com.larreta.stepper.impl.LoadParametricEntityBusinessImpl;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(ResponsibleTypeLoadBusiness.BUSINESS_NAME)
@Transactional
public class ResponsibleTypeLoadBusinessImpl extends LoadParametricEntityBusinessImpl<ParametricData, ResponsibleType>
		implements ResponsibleTypeLoadBusiness {

}
