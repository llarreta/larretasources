package ar.com.larreta.school.business.students;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.Scholarship;
import ar.com.larreta.stepper.impl.LoadParametricEntityBusinessImpl;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(ScholarshipLoadBusiness.BUSINESS_NAME)
@Transactional
public class ScholarshipsLoadBusinessImpl extends LoadParametricEntityBusinessImpl<ParametricData, Scholarship>
		implements ScholarshipLoadBusiness {

}
