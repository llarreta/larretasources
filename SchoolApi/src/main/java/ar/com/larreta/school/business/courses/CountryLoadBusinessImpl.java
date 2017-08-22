package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.mystic.model.Country;
import ar.com.larreta.stepper.impl.LoadParametricEntityBusinessImpl;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(CountryLoadBusiness.BUSINESS_NAME)
@Transactional
public class CountryLoadBusinessImpl extends LoadParametricEntityBusinessImpl<ParametricData, Country> implements CountryLoadBusiness {
}
