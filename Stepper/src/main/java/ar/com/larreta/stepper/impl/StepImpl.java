package ar.com.larreta.stepper.impl;

import javax.transaction.Transactional;

import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.StepElement;

@Transactional
public abstract class StepImpl extends StepElement implements Step {

}
