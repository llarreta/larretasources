package ar.com.larreta.validators;

public interface FormatValidatorProcessor {
	public Boolean process(String text);
	public void setPattern(String pattern);
}
