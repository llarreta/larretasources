package ar.com.larreta.commons.persistence.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import ar.com.larreta.commons.AppConfig;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.persistence.IEAction;
import ar.com.larreta.commons.persistence.JDBCConnection;
import ar.com.larreta.commons.persistence.NamedParameterStatement;

/**
 * Wrapper de connection, para establecer una conexion JDBC pura
 */
@Component
@DependsOn("appConfig")
public class JDBCConnectionImpl extends AppObjectImpl implements JDBCConnection {
	
	private static final long serialVersionUID = 1L;

	private static final String SQL = ".sql";

	private static final String EXPORT = "export_";

	private static final String WHITE_SPACE = " ";

	private static final String DATABASE = "[DATABASE]";

	private static final String PASSWORD = "[PASSWORD]";

	private static final String USER = "[USER]";
	
	private static final String VAR_SCHEMMA = "[SCHEMMA]";
	
	public static final String NEW_LINE = "\n";
	
	@Autowired
	private AppConfig appConfig;
	
	public final static String DEFAULT_DELIMITER = ";";
	
	private Connection connection;
	
	private String delimiter = DEFAULT_DELIMITER;
	private boolean fullLineDelimiter = false;

	@PostConstruct
	public void init() throws ClassNotFoundException, SQLException, IOException{
		appConfig.initializeSchemma(this);
	}
	
	public Connection getConnection(){
		if (connection==null){
			try {
				//STEP 1: Register JDBC driver
				Class.forName(appConfig.getAppConfigData().getDatabaseDriver());
				//STEP 2: Open a connection
				connection = DriverManager.getConnection(appConfig.getAppConfigData().getDatabaseURL(),
															appConfig.getDatabaseAdminUsername(),
															appConfig.getDatabaseAdminPassword());			
			} catch (Exception e){
				getLog().error(AppException.getStackTrace(e));
			}
		}
		return connection;
	}
	
	public void dropSchema(){
		try {
			executeSimpleQuery("DROP SCHEMA IF EXISTS " + appConfig.getAppConfigData().getDatabaseURLSchemma());
		} catch (SQLException e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}
	
	public void createSchema(){
		try {
			executeSimpleQuery("CREATE SCHEMA IF NOT EXISTS " + appConfig.getAppConfigData().getDatabaseURLSchemma());
		} catch (SQLException e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}
	
	public void executeSimpleQuery(String query)
			throws SQLException {
		NamedParameterStatement preparedStatement = new NamedParameterStatement(getConnection(), query);
		preparedStatement.execute();
		preparedStatement.close();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return getConnection().unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return getConnection().isWrapperFor(iface);
	}

	public Statement createStatement() throws SQLException {
		return getConnection().createStatement();
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return getConnection().prepareStatement(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		return getConnection().prepareCall(sql);
	}

	public String nativeSQL(String sql) throws SQLException {
		return getConnection().nativeSQL(sql);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		getConnection().setAutoCommit(autoCommit);
	}

	public boolean getAutoCommit() throws SQLException {
		return getConnection().getAutoCommit();
	}

	public void commit() throws SQLException {
		getConnection().commit();
	}

	public void rollback() throws SQLException {
		getConnection().rollback();
	}

	public void close() throws SQLException {
		getConnection().close();
	}

	public boolean isClosed() throws SQLException {
		return getConnection().isClosed();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return getConnection().getMetaData();
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		getConnection().setReadOnly(readOnly);
	}

	public boolean isReadOnly() throws SQLException {
		return getConnection().isReadOnly();
	}

	public void setCatalog(String catalog) throws SQLException {
		getConnection().setCatalog(catalog);
	}

	public String getCatalog() throws SQLException {
		return getConnection().getCatalog();
	}

	public void setTransactionIsolation(int level) throws SQLException {
		getConnection().setTransactionIsolation(level);
	}

	public int getTransactionIsolation() throws SQLException {
		return getConnection().getTransactionIsolation();
	}

	public SQLWarning getWarnings() throws SQLException {
		return getConnection().getWarnings();
	}

	public void clearWarnings() throws SQLException {
		getConnection().clearWarnings();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return getConnection().createStatement(resultSetType, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return getConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return getConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return getConnection().getTypeMap();
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		getConnection().setTypeMap(map);
	}

	public void setHoldability(int holdability) throws SQLException {
		getConnection().setHoldability(holdability);
	}

	public int getHoldability() throws SQLException {
		return getConnection().getHoldability();
	}

	public Savepoint setSavepoint() throws SQLException {
		return getConnection().setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return getConnection().setSavepoint(name);
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		getConnection().rollback(savepoint);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		getConnection().releaseSavepoint(savepoint);
	}

	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return getConnection().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return getConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return getConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		return getConnection().prepareStatement(sql, autoGeneratedKeys);
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		return getConnection().prepareStatement(sql, columnIndexes);
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		return getConnection().prepareStatement(sql, columnNames);
	}

	public Clob createClob() throws SQLException {
		return getConnection().createClob();
	}

	public Blob createBlob() throws SQLException {
		return getConnection().createBlob();
	}

	public NClob createNClob() throws SQLException {
		return getConnection().createNClob();
	}

	public SQLXML createSQLXML() throws SQLException {
		return getConnection().createSQLXML();
	}

	public boolean isValid(int timeout) throws SQLException {
		return getConnection().isValid(timeout);
	}

	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		getConnection().setClientInfo(name, value);
	}

	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		getConnection().setClientInfo(properties);
	}

	public String getClientInfo(String name) throws SQLException {
		return getConnection().getClientInfo(name);
	}

	public Properties getClientInfo() throws SQLException {
		return getConnection().getClientInfo();
	}

	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		return getConnection().createArrayOf(typeName, elements);
	}

	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		return getConnection().createStruct(typeName, attributes);
	}
	
    /**
     * Runs an SQL script (read in using the Reader parameter) using the
     * connection passed in
     *
     * @param conn
     *            - the connection to use for the script
     * @param reader
     *            - the source of the script
     * @throws SQLException
     *             if any SQL errors occur
     * @throws IOException
     *             if there is an error reading from the Reader
     */
    public void runScript(Reader reader) throws IOException, SQLException {
            StringBuilder command = null;
            try {
                    LineNumberReader lineReader = new LineNumberReader(reader);
                    String line = null;
                    while ((line = lineReader.readLine()) != null) {
                            if (command == null) {
                                    command = new StringBuilder();
                            }
                            String trimmedLine = line.trim();
                            if (trimmedLine.startsWith("--")) {
                            	getLog().info(trimmedLine);
                            } else if (trimmedLine.length() < 1
                                            || trimmedLine.startsWith("//")) {
                                    // Do nothing
                            } else if (trimmedLine.length() < 1
                                            || trimmedLine.startsWith("--")) {
                                    // Do nothing
                            } else if (!fullLineDelimiter
                                            && trimmedLine.endsWith(getDelimiter())
                                            || fullLineDelimiter
                                            && trimmedLine.equals(getDelimiter())) {
                                    command.append(line.substring(0, line
                                                    .lastIndexOf(getDelimiter())));
                                    command.append(WHITE_SPACE);
                                    Statement statement = getConnection().createStatement();

                                    String finalQuery = command.toString();
                                    finalQuery = finalQuery.replace(VAR_SCHEMMA, appConfig.getAppConfigData().getDatabaseURLSchemma());
                                    getLog().info(finalQuery);

                                    boolean hasResults = false;
                                    if (appConfig.getStopOnError()) {
                                            hasResults = statement.execute(finalQuery);
                                    } else {
                                            try {
                                                    statement.execute(finalQuery);
                                            } catch (SQLException e) {
                                            	getLog().error("Error executing: " + finalQuery);
                                            	getLog().error(AppException.getStackTrace(e));
                                            }
                                    }

                                    if (getAutoCommit() && !getConnection().getAutoCommit()) {
                                            getConnection().commit();
                                    }

                                    ResultSet rs = statement.getResultSet();
                                    if (hasResults && rs != null) {
                                            ResultSetMetaData md = rs.getMetaData();
                                            int cols = md.getColumnCount();
                                            for (int i = 0; i < cols; i++) {
                                                    String name = md.getColumnLabel(i);
                                                    getLog().info(name + "\t");
                                            }
                                            getLog().info("");
                                            while (rs.next()) {
                                                    for (int i = 0; i < cols; i++) {
                                                            String value = rs.getString(i);
                                                            getLog().info(value + "\t");
                                                    }
                                                    getLog().info("");
                                            }
                                    }

                                    command = null;
                                    try {
                                            statement.close();
                                    } catch (Exception e) {
                                            // Ignore to workaround a bug in Jakarta DBCP
                                    }
                                    Thread.yield();
                            } else {
                                    command.append(line);
                                    command.append(WHITE_SPACE);
                            }
                    }
                    if (!getAutoCommit()) {
                            getConnection().commit();
                    }
            } catch (SQLException e) {
            	getLog().error("Error executing: " + command);
            	getLog().error(AppException.getStackTrace(e));
                throw e;
            } catch (IOException e) {
            	getLog().error("Error executing: " + command);
            	getLog().error(AppException.getStackTrace(e));
                throw e;
            } finally {
            	if (!getConnection().getAutoCommit()){
                    getConnection().rollback();
            	}
            }
    }

	private String getDelimiter() {
		return delimiter;
	}
	
	/**
	 * Genera el dump de la base
	 * @return
	 * @throws IOException
	 */
	public File getDump() throws IOException{
		File file = new File(appConfig.getAppConfigData().getDatabaseDumpDirectory() + File.separator + EXPORT + System.currentTimeMillis() + SQL );
		final FileWriter writer = new FileWriter(file);
		
		List<String> params = new ArrayList<String>();
		params.add(appConfig.getAppConfigData().getDatabaseHomeDirectory() + appConfig.getAppConfigData().getDatabaseDumpCommand());
		String paramsWithReplacedValues = appConfig.getAppConfigData().getDatabaseDumpCommandParam().replace(USER, appConfig.getDatabaseAdminUsername())
															.replace(PASSWORD, appConfig.getDatabaseAdminPassword())
														.		replace(DATABASE, appConfig.getAppConfigData().getDatabaseURLSchemma());
		params.addAll(Arrays.asList(paramsWithReplacedValues.split(WHITE_SPACE)));
		
		
		ProcessBuilder processBuilder = new ProcessBuilder(params);
		Process process = processBuilder.start();
		
		readInputStream(writer, process);
	    readErrorStream(process);
	    
	    return file;
	}
	
	protected void readErrorStream(Process process) throws IOException {
		readStream(process.getErrorStream(), new IEAction() {
			
			public void read(String line) {
				getLog().error(line + NEW_LINE);
			}
			
			public void finish() {
				// do nothing
			}
		});
	}
	
	protected void readInputStream(final FileWriter writer, Process process) throws IOException {
		readStream(process.getInputStream(), new IEAction() {
			
			public void read(String line) {
				try {
					writer.write(line + NEW_LINE);
				} catch (IOException e) {
					getLog().error(e);
				}
			}
			
			public void finish() {
				 try {
					writer.close();
				} catch (IOException e) {
					getLog().error(e);
				}
			}
		});
	}

	protected void readStream(InputStream inputStream, IEAction action)
			throws IOException {
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader buffer = new BufferedReader(reader);
		String line;
		while ((line = buffer.readLine()) != null) {
			action.read(line);
		}
		action.finish();
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		getLog().debug("setSchema:" + schema);
	}

	@Override
	public String getSchema() throws SQLException {
		return null;
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		getLog().debug("abort:" + executor);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds)
			throws SQLException {
		getLog().debug("setNetworkTimeout:" + executor + milliseconds);
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return 0;
	}

}
