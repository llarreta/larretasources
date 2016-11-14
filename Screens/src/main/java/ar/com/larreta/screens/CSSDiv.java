package ar.com.larreta.screens;

public class CSSDiv extends Div {

	private static final String WHITE_SPACE = " ";
	private static final String CLASS_PREFIX_G = "ui-g-";
	private static final String CLASS_PREFIX_MD = "ui-md-";
	private static final String CLASS_PREFIX_LG = "ui-lg-";
	private static final String NO_PAD = "ui-g-nopad";
	
	@Override
	public String getPersistEntityName() {
		return Div.class.getName();
	}
	
	public CSSDiv(Integer indexG){
		this(indexG, null, null, Boolean.FALSE);
	}
	
	public CSSDiv(Integer indexG, Integer indexMD){
		this(indexG, indexMD, null, Boolean.FALSE);
	}
	
	public CSSDiv(Integer indexG, Integer indexMD, Integer indexLG, Boolean noPad){
		super();
		StringBuffer buffer = new StringBuffer();
		addClass(buffer, CLASS_PREFIX_G, indexG);
		addClass(buffer, CLASS_PREFIX_MD, indexMD);
		addClass(buffer, CLASS_PREFIX_LG, indexLG);
		if(noPad!=null && noPad){
			buffer.append(noPad);
		}
		setStyleClass(buffer.toString());
	}

	private void addClass(StringBuffer buffer, String classPrefix, Integer index) {
		if (index!=null) {
			buffer.append(classPrefix + index);
			buffer.append(WHITE_SPACE);
		}
	}
	
}
