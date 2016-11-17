package ar.com.larreta.screens;

public class CSSGrid extends Div {

	private static final String WHITE_SPACE = " ";
	private static final String CLASS_PREFIX_G = "ui-g-";
	private static final String CLASS_PREFIX_MD = "ui-md-";
	private static final String CLASS_PREFIX_LG = "ui-lg-";
	private static final String NO_PAD = "ui-g-nopad";
	private static final String NEW_LINE = "ui-g";
	
	@Override
	public String getPersistEntityName() {
		return Div.class.getName();
	}
	
	public CSSGrid(Boolean newLine){
		setStyleClass(NEW_LINE);
	}
	
	public CSSGrid(Integer indexG){
		this(indexG, null, null, Boolean.FALSE);
	}
	
	public CSSGrid(Integer indexG, Integer indexMD){
		this(indexG, indexMD, null, Boolean.FALSE);
	}
	
	public CSSGrid(Integer indexG, Boolean noPad){
		this(indexG, null, null, noPad);
	}
	
	public CSSGrid(Integer indexG, Integer indexMD, Boolean noPad){
		this(indexG, indexMD, null, noPad);
	}
	
	public CSSGrid(Integer indexG, Integer indexMD, Integer indexLG){
		this(indexG, indexMD, indexLG, Boolean.FALSE);
	}
	
	public CSSGrid(Integer indexG, Integer indexMD, Integer indexLG, Boolean noPad){
		super();
		StringBuffer buffer = new StringBuffer();
		addClass(buffer, CLASS_PREFIX_G, indexG);
		addClass(buffer, CLASS_PREFIX_MD, indexMD);
		addClass(buffer, CLASS_PREFIX_LG, indexLG);
		if(noPad!=null && noPad){
			buffer.append(NO_PAD);
		}
		setStyleClass(buffer.toString());
	}

	private void addClass(StringBuffer buffer, String classPrefix, Integer index) {
		if (index!=null) {
			buffer.append(classPrefix + index);
			buffer.append(WHITE_SPACE);
		}
	}
	
	public void addExtraClass(String extraClass){
		setStyleClass(getStyleClass() + " " + extraClass);
	}
	
}
