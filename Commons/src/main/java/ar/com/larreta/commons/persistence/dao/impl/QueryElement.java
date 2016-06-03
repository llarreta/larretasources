package ar.com.larreta.commons.persistence.dao.impl;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public class QueryElement extends AppObjectImpl {

	public static final String SUB = "SUB";
	public static final String UNDERFLOW = "_";

	protected LoadArguments args;
	protected String name;
	protected Boolean mainReference = Boolean.TRUE;
	protected String symbolicName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LoadArguments getArgs() {
		return args;
	}

	public void setArgs(LoadArguments args) {
		this.args = args;
	}

	public Boolean getMainReference() {
		return mainReference;
	}

	public String getSymbolicName() {
		if (symbolicName == null) {
			symbolicName = getSymbolicName(name);
		}
		return symbolicName;
	}
	
	public String getSymbolicName(String name) {
		return getSymbolicName(name, Boolean.TRUE);
	}
	
	public String getSymbolicName(String name, Boolean updateMainReference) {
		String methodSymbolicName = StringUtils.EMPTY;
		String field = StringUtils.EMPTY;
		String accumulated = StringUtils.EMPTY;
		Integer index = name.lastIndexOf(StandardDAOImpl.DOT);
		if (index >= 0) {
			field = name.substring(index + 1);
			String packagefield = name.substring(0, index);
			do {
				methodSymbolicName = args.getSymbol(packagefield);
				if (methodSymbolicName==null){
					index = packagefield.lastIndexOf(StandardDAOImpl.DOT);
					if (index >= 0) {
						accumulated = packagefield.substring(index + 1) + StandardDAOImpl.DOT + accumulated;
						packagefield = packagefield.substring(0, index);
					}
				}
				if (updateMainReference){
					mainReference=(methodSymbolicName==null);
				}
			} while ((index>=0) && (mainReference));
		}
		if (mainReference) {
			methodSymbolicName = name;
		} else {
			methodSymbolicName += StandardDAOImpl.DOT + accumulated + field;
		}

		return methodSymbolicName;
	}

	
	public static String generateSymbol(LoadArguments args, String property) {
		String symbol = StringUtils.upperCase(StringUtils.replace(ar.com.larreta.commons.utils.StringUtils.vocalRemove(property), StandardDAOImpl.DOT, UNDERFLOW));
		if (args!=null){
			while (args.containSymbol(symbol)){
				symbol += UNDERFLOW + SUB;
			}
			args.addSymbol(property, symbol);
		}
		return symbol;
	}
}
