package ar.com.larreta.stepper.validators;

public interface FormatValidatorProcessor {
	public Boolean process(String text);
	public void setPattern(String pattern);
}
