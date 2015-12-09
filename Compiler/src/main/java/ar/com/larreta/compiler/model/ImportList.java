package ar.com.larreta.compiler.model;

import java.util.List;

@Deprecated
public class ImportList extends JavaProgramUnit {

	private List<JavaProgramUnit> imports;
	
	public List<JavaProgramUnit> getImports() {
		return imports;
	}

	public void setImports(List<JavaProgramUnit> imports) {
		this.imports = imports;
	}

	@Override
	public void process(List<JavaProgramUnit> inProgress) {
		imports = inProgress;
	}

}
