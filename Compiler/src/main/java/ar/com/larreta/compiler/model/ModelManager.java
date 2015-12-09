package ar.com.larreta.compiler.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import ar.com.larreta.compiler.exceptions.CompilerException;

public class ModelManager {

	private static Integer MAX_LEVEL = 2;
	
	private static CompilerPath compilerPath = new CompilerPath(Boolean.TRUE);
	
	private List<JavaProgramUnit> tokens;
	private JavaProgramUnit lastUnit;
	private ListIterator<JavaProgramUnit> it;
	
	private Integer level = 0;
	
	public ModelManager(List<JavaProgramUnit> tokens){
		updateTokens(tokens);
	}

	private void updateTokens(List<JavaProgramUnit> tokens) {
		this.tokens = tokens;
		it = this.tokens.listIterator();
	}
	
	public static CompilerPath getCompilerPath(CompilerPath path, JavaClass type){
		return ((CompilerPath)path.get(type));
	}
	
	public JavaFile getJava() throws CompilerException{
		try{
			List<JavaProgramUnit> units = programUnits();
			if (units.size()==1){
				return (JavaFile) units.iterator().next();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		throw new CompilerException();
	}
	
	public List<JavaProgramUnit> programUnits(){
		Boolean anyChanges = Boolean.FALSE;

		List<JavaProgramUnit> units = new ArrayList<JavaProgramUnit>();
		if (tokens!=null){
			while (it.hasNext()) {
				List<JavaProgramUnit> inProgress = new ArrayList<JavaProgramUnit>();
				Class unitClass = (Class) followThePath(compilerPath, inProgress);
			
				if (unitClass!=null){
					JavaProgramUnit unit = null;
					try {
						unit = (JavaProgramUnit) unitClass.newInstance();
						unit.process(inProgress);
					} catch (Exception e) {
						e.printStackTrace();
					}
					anyChanges = Boolean.TRUE;
					units.add(unit);
				} else {
					if (inProgress.size()>1){
						Integer index = inProgress.size();
						while (index>0){
							lastUnit = it.previous();
							index--;
						}
						it.next(); //Avance para reacomodar la posicion del siguiente
					}
					units.add(lastUnit);
				}
			}
		}
		
		if ((!anyChanges) && (level<MAX_LEVEL)){
			level++;
			initializeForAdvanceLevel();
			anyChanges = Boolean.TRUE;
		}
		
		if (anyChanges){
			updateTokens(units);
			units = programUnits();
		}
		
		return units;
	}

	private void initializeForAdvanceLevel() {
		switch (level) {
		case 1:
			compilerPath.initializeLevel1();
			break;
		case 2:
			compilerPath.initializeLevel2();
			break;
		default:
			// do nothing
			break;
		}
	}

	private Object followThePath(CompilerPath compilerPath, Collection<JavaProgramUnit> inProgress) {
		if (it.hasNext()) {
			lastUnit = (JavaProgramUnit) it.next();

			return evaluateCompilerPath(compilerPath, inProgress);
		} else {
			//FIXME: Throw Exception for uncompilation error
			return null;
		}
	}

	private Object evaluateCompilerPath(CompilerPath compilerPath, Collection<JavaProgramUnit> inProgress) {
		Object path = compilerPath.get(lastUnit.getClass());
		
		inProgress.add(lastUnit);
		if (path instanceof CompilerPath) {
			path = followThePath((CompilerPath) path, inProgress);
		}
		
		return path;
	}

	
}
