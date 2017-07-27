package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.Level;
import ar.com.larreta.stepper.impl.CreateBusinessImpl;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(LevelsCreateBusiness.BUSINESS_NAME)
@Transactional
public class LevelsCreateBusinessImpl extends CreateBusinessImpl<ParametricData, Level> implements LevelsCreateBusiness {


}
