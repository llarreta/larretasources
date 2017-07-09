package ar.com.larreta.persistence.dao.impl;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public class QueryElement {

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
		if (StringUtils.isEmpty(symbolicName) && !StringUtils.isEmpty(name)) {
			symbolicName = getSymbolicName(name);
		}
		return symbolicName;
	}
	
	public String getSymbolicName(String name) {
		return getSymbolicName(name, Boolean.TRUE);
	}
	
	//FIXME: revisar, entra en loop, codigo muy feo
	public String getSymbolicName(String name, Boolean updateMainReference) {
		String methodSymbolicName = StringUtils.EMPTY;
		String field = StringUtils.EMPTY;
		String accumulated = StringUtils.EMPTY;
		Integer index = name.lastIndexOf(StandardDAOImpl.DOT);
		Integer lastIndex = index;
		if (index >= 0) {
			field = name.substring(index + 1);
			String packagefield = name.substring(0, index);
			do {
				lastIndex = index;
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
			} while ((index>=0) && (mainReference) && (index!=lastIndex));
		}
		if (mainReference) {
			methodSymbolicName = name;
		} else {
			methodSymbolicName += StandardDAOImpl.DOT + accumulated + field;
		}

		return methodSymbolicName;
	}

	/**
	 * Genera un symbolo para una propiedad dada
	 * Este symbolo luego es utilizado en la query HQL en construccion
	 * @param args
	 * @param property
	 * @return
	 */
	public static String generateSymbol(LoadArguments args, String property) {
		if (args!=null){
			if (args.containPropertySymbol(property)){
				return args.getSymbol(property);
			}
			args.addSymbol(property, generateSymbol(property));
		}
		return generateSymbol(property);
	}

	private static String generateSymbol(String property) {
		return StringUtils.upperCase(StringUtils.replace(ar.com.larreta.tools.StringUtils.vocalRemove(property), StandardDAOImpl.DOT, UNDERFLOW));
	}
}
