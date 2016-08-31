package co.com.directv.sdii.common.enumerations;

import java.util.HashMap;
import java.util.Map;

    /**
     *  Enumeración de los tipos de dialectos para las diferentes Bases de datos que soporta Hibernate
     *  
     * @author Alex Quitiaquez
     * */
public enum DialectoHibernate {
		DB2("DB2","org.hibernate.dialect.DB2Dialect"),
		DB2_AS_400("DB2 AS/400","org.hibernate.dialect.DB2400Dialect"),
		DB2_OS390("DB2 OS390","org.hibernate.dialect.DB2390Dialect"),
		POSTGRESQL("PostgreSQL","org.hibernate.dialect.PostgreSQLDialect"),
		MYSQL("MySQL","org.hibernate.dialect.MySQLDialect"),
		MYSQL_INNODB("MySQL with InnoDB","org.hibernate.dialect.MySQLInnoDBDialect"),
		MYSQL_MYISAM("MySQL with MyISAM","org.hibernate.dialect.MySQLMyISAMDialect"),
		ORACLE_ANY_VERSION("Oracle (any version)","org.hibernate.dialect.OracleDialect"),
		ORACLE_9I("Oracle 9","org.hibernate.dialect.Oracle9Dialect"),
		SYBASE("Sybase","org.hibernate.dialect.SybaseDialect"),
		SYBASE_ANYWHERE("Sybase Anywhere","org.hibernate.dialect.SybaseAnywhereDialect"),
		MICROSOFT_SQL_SERVER("Microsoft SQL Server","org.hibernate.dialect.SQLServerDialect"),
		SAP_DB("SAP DB","org.hibernate.dialect.SAPDBDialect"),
		INFORMIX ("Informix","org.hibernate.dialect.InformixDialect"),
		HYPERSONICSQL("HypersonicSQL","org.hibernate.dialect.HSQLDialect"),
		INGRES("Ingres","org.hibernate.dialect.IngresDialect"),
		PROGRESS("Progress","org.hibernate.dialect.ProgressDialect"),
		MCKOI_SQL("Mckoi SQL","org.hibernate.dialect.MckoiDialect"),
		INTERBASE("Interbase","org.hibernate.dialect.InterbaseDialect"),
		POINTBASE("Pointbase","org.hibernate.dialect.PointbaseDialect"),
		FRONTBASE("FrontBase","org.hibernate.dialect.FrontbaseDialect"),
		FIREBIRD("Firebird","org.hibernate.dialect.FirebirdDialect");
		
		DialectoHibernate(String dataBase,String dialecto1) {
			this.dataBase=dataBase;
			dialecto=dialecto1;
            
            if (DialectoHibernate.getDataBaseMap() == null) {
            	DialectoHibernate.setDataBaseMap(new HashMap<String,DialectoHibernate>());
            	}
            //Inserta en el mapa el tipo de documento con el codigo como clave en el map
            DialectoHibernate.getDataBaseMap().put(dataBase, this);
        }
        private String dataBase;
        private String dialecto;
        //Mapa de los Dialecto_BD_Hibernate
        private static Map<String,DialectoHibernate> dataBaseMap;
        
        public String getDataBase() {
        	return dataBase;
        }

        public String getDialecto() {
        	return dialecto;
        }
        
        public static Map<String, DialectoHibernate> getDataBaseMap() {
			return dataBaseMap;
		}

		public static void setDataBaseMap(Map<String, DialectoHibernate> dataBaseMap) {
			DialectoHibernate.dataBaseMap = dataBaseMap;
		}

		/**
         * Obtiene el Dialecto_BD_Hibernate basado en la base_datos pasada por parametro 
         * 
         * @param codigo
         * @return
         */
        public static DialectoHibernate valueOfCodigo(String base_datos) {
        	DialectoHibernate value = dataBaseMap.get(base_datos);
        	if (value == null) {
        		throw new IllegalArgumentException(base_datos + " no es una base de datos válida.");
        	}
        	return value;
        }
    }
