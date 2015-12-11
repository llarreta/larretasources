package ar.com.larreta.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * Utilidades para la operacion de fechas
 * 
 * Tener en cuenta que se considera el nulo como una fecha infinita
 * Con lo cual si se compara una fecha cualquiera con una fecha nula, la primer fecha siempre sera menor que la fecha nula o infinita
 */
public class DateUtils {
	
	public static final Long ONE_SECOND = new Long(1000);
	public static final Long ONE_MINUTE = 60 * ONE_SECOND;
	public static final Long ONE_HOUR = 60 * ONE_MINUTE;
	public static final Long ONE_DAY = 24 * ONE_HOUR;
	public static final Long ONE_MOUNTH = 30 * ONE_DAY;
	public static final Long ONE_YEAR = 12 * ONE_MOUNTH;
	
	public static Boolean isMajorOrEqual(Date compare, Date with){
		return isMajor(compare, with) || isEqual(compare, with);
	}

	public static Boolean isMinorOrEqual(Date compare, Date with){
		return isMinor(compare, with) || isEqual(compare, with);
	}
	
	/**
	 * Retorna verdadero si la fecha compare es menor que la fecha with
	 * o si la fecha compare no es nulo, y la fecha with si es nulo
	 * @param compare
	 * @param with
	 * @return
	 */
	public static Boolean isMinor(Date compare, Date with){
		return (compare!=null) && ((with==null)||(compare.before(with)));
	}

	/**
	 * Retorna verdadero si la fecha compare es mayor que la fecha with
	 * @param compare
	 * @param with
	 * @return
	 */
	public static Boolean isMajor(Date compare, Date with){
		return (compare==null) || (compare.after(with));
	}
	
	/**
	 * Retorna verdadero cuando ambas fechas son iguales
	 * @param compare
	 * @param with
	 * @return
	 */
	public static Boolean isEqual(Date compare, Date with){
		return ((compare==null)&&(with==null)) ||
				((compare!=null) && (compare.equals(with)));
	}
	
	/**
	 * A partir de la fecha pasada por parametro
	 * Retorna un texto que representa cuanto hace que ocurrio dicha fecha / horario
	 * @param date
	 * @return
	 */
	public static String getAgo(Date date){
		DateUtilData diff = getDateDifference(new Date(), date);
		
		StringBuilder agoText = new StringBuilder();
		agoText.append("... hace");
		
		//Contador para que especifique solo dos tipos de detalle
		Integer count = 0;
		
		// Formato para cuando paso mas de un año
		if ((diff.getYears()>0) && (count<2)){
			count++;
			agoText.append(" ");
			agoText.append(diff.getYears());
			agoText.append(" año");
			if (diff.getYears()>1){
				agoText.append("s");	
			}
		}
		// Formato para cuando paso mas de un mes
		if ((diff.getMonths()>0) && (count<2)){
			count++;
			agoText.append(" ");
			agoText.append(diff.getMonths());
			agoText.append(" mes");
			if (diff.getMonths()>1){
				agoText.append("es");	
			}
		}
		// Formato para cuando paso mas de un dia
		if ((diff.getDays()>0) && (count<2)){
			count++;
			agoText.append(" ");
			agoText.append(diff.getDays());
			agoText.append(" día");
			if (diff.getDays()>1){
				agoText.append("s");	
			}
		}
		// Formato para cuando paso mas de una hora
		if ((diff.getHours()>0) && (count<2)){
			count++;
			agoText.append(" ");
			agoText.append(diff.getHours());
			agoText.append(" hora");
			if (diff.getHours()>1){
				agoText.append("s");	
			}
		}		
		// Formato para cuando pasaron solo minutos
		if ((diff.getMinutes()>0) && (count<2)){
			count++;
			agoText.append(" ");
			agoText.append(diff.getMinutes());
			agoText.append(" minuto");
			if (diff.getMinutes()>1){
				agoText.append("s");	
			}
		}
		// Formato para cuando pasaron solo segundos
		if ((diff.getSeconds()>0) && (count<2)){
			count++;
			agoText.append(" ");
			agoText.append(diff.getSeconds());
			agoText.append(" segundo");
			if (diff.getSeconds()>1){
				agoText.append("s");	
			}
		}
		
		if (count==0){
			agoText.append(" muy poquito ... ");
		}
		
		return agoText.toString();
	}

	/**
	 * Retorna la diferencia entre una fecha y otra
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static DateUtilData getDateDifference(Date dateA, Date dateB) {
		Long milliseconds = dateA.getTime() - dateB.getTime();
		DateUtilData diff = new DateUtilData();
				
		if (milliseconds>ONE_YEAR){
			Long result = milliseconds / ONE_YEAR;
			milliseconds -= ONE_YEAR * result;
			diff.setYears(result);
		}

		if (milliseconds>ONE_MOUNTH){
			Long result = milliseconds / ONE_MOUNTH;
			milliseconds -= ONE_MOUNTH * result;
			diff.setMonths(result);
		}

		if (milliseconds>ONE_DAY){
			Long result = milliseconds / ONE_DAY;
			milliseconds -= ONE_DAY * result;
			diff.setDays(result);
		}
		
		if (milliseconds>ONE_HOUR){
			Long result = milliseconds / ONE_HOUR;
			milliseconds -= ONE_HOUR * result;
			diff.setHours(result);
		}

		if (milliseconds>ONE_MINUTE){
			Long result = milliseconds / ONE_MINUTE;
			milliseconds -= ONE_MINUTE * result;
			diff.setMinutes(result);
		}
		
		if (milliseconds>ONE_SECOND){
			Long result = milliseconds / ONE_SECOND;
			milliseconds -= ONE_SECOND * result;
			diff.setSeconds(result);
		}
		
		diff.setMilliseconds(milliseconds);
		
		return diff;
	}
	
	public static Date add(Date actual, Integer type, Integer count){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(actual);
		calendar.add(type, count);
		return calendar.getTime();
	}
	
	public static String format(Date date, String pattern){
		if (date==null){
			return  StringUtils.EMPTY;
		}
		if (pattern==null){
			return date.toString();
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	
	
}
