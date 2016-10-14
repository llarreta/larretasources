package co.com.directv.sdii.common.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * 
 * Clase que implementa la interfaz Comparator, para
 * realizar la comparacion de los valores de dos 
 * metodos por medio de introspeccion. 
 * 
 * Fecha de Creación: 11/05/2010
 * @author Joan Lopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see Comparator
 */
@SuppressWarnings("unchecked")
public class ComparatorUtil implements Comparator, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3879024241804420903L;
	
	private String nombreMetodo;
    private boolean ascendente;

    public ComparatorUtil(String pMetodo, boolean pAscendente) {
    	
	   this.setNombreMetodo ( pMetodo);
       this.setAscendente(pAscendente);
   }

   public boolean isAscendente() {
       return ascendente;
   }

   public void setAscendente(boolean ascendente) {
       this.ascendente = ascendente;
   }

   public String getNombreMetodo() {
       return nombreMetodo;
   }
   
   public void setNombreMetodo(String nombreMetodo) {
       this.nombreMetodo = nombreMetodo;

   }
  /**
   * Metodo: Implementacion de la firma compare
   * de la interfaz Comparator, para realizar
   * la validacion del orden del metodo de ordenacion.
   * @param o1
   * @param o2
   * @return
   */
   public int compare(Object o1, Object o2) {
       Method org;
       try {
    	   
           org = o1.getClass().getMethod(this.getNombreMetodo());
           Object invoke = org.invoke(o1);
           Object invoke2 = org.invoke(o2);
           
           String st = (String) invoke;
           String st2 = (String) invoke2;
           
           if (this.isAscendente()){
               return st.compareTo(st2);
           }else{
               return st2.compareTo(st);
           }

       } catch (Throwable ex) {
            return 0;
       }
    
   }

   @Override
   public boolean equals(Object o) {
       return this == o;
   }
   
   @Override
	public int hashCode() {
		return super.hashCode();
	}
   
}
