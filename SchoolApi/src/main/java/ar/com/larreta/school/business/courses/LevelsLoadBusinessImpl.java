package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.Level;
import ar.com.larreta.stepper.impl.LoadParametricEntityBusinessImpl;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(LevelsLoadBusiness.BUSINESS_NAME)
@Transactional
public class LevelsLoadBusinessImpl extends LoadParametricEntityBusinessImpl<ParametricData, Level> implements LevelsLoadBusiness {

}
