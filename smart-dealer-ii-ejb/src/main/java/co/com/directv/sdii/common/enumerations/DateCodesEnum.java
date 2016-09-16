package co.com.directv.sdii.common.enumerations;


/**
 * 
 * Clase de tipo Enum que define las constantes para la lista de fechas
 * para consulta de WO del Caso de Uso ADS - 18 - Visualizar Bandeja de Work Orders de la Compa��a Instaladora.
 *
 * WO sin Fecha de Agendamiento
 * WO del d�a de hoy
 * WO un d�a adelante
 * WO dos d�a adelante
 * WO tres d�a adelante
 * WO cuatro d�a adelante
 * WO cinco d�a adelante
 * WO seis d�a adelante
 * WO siete d�a adelante
 * WO hace m�s de dos d�a 
 * WO hace m�s de siete d�a 
 * WO hace m�s de quince d�a
 * WO hace m�s de treinta d�a 
 * Todas las WO
 * 
 * Fecha de Creaci�n: 05/05/2010
 * @author Jimmy V�lez Mu�oz
 * @version 1.0
 * 
 */
public enum DateCodesEnum {

    WO_SIN_FECHA_AGENDAMIENTO("01",1L),
    WO_DE_HOY("02",2L),
    WO_DE_UN_DIA_ADELANTE("03",3L),
    WO_DE_DOS_DIAS_ADELANTE("04",4L),
    WO_DE_TRES_DIAS_ADELANTE("05",5L),
    WO_DE_CUATRO_DIAS_ADELANTE("06",6L),
    WO_DE_CINCO_DIAS_ADELANTE("07",7L),
    WO_DE_SEIS_DIAS_ADELANTE("08",8L),
    WO_DE_SIETE_DIAS_ADELANTE("09",9L),
    WO_HACE_MAS_DE_DOS_DIAS("10",10L),
    WO_HACE_MAS_DE_SIETE_DIAS("11",11L),
    WO_HACE_MAS_DE_QUINCE_DIAS("12",12L),
    WO_HACE_MAS_DE_TREINTA_DIAS("13",13L),
    WO_TODAS("14",14L);
    
    private String code;
    private Long codeId;

   
    DateCodesEnum(String pCode, Long pCodeId) {
        this.code = pCode;
        this.codeId = pCodeId;
    }
    
     /**
     *
     * Metodo: Retorna el codigo de la enumeraci�n
     * 
     * @return String code
     * @author Jimmy V�lez Mu�oz
     */
    public String getCode(){
        return this.code;
    }
    
    public Long getCodeId(){
        return this.codeId;
    }
}
