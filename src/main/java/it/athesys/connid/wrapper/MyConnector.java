package it.athesys.connid.wrapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.identityconnectors.common.IOUtil;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.api.APIConfiguration;
import org.identityconnectors.framework.api.ConfigurationProperties;
import org.identityconnectors.framework.api.ConfigurationProperty;
import org.identityconnectors.framework.api.ConnectorFacade;
import org.identityconnectors.framework.api.ConnectorFacadeFactory;
import org.identityconnectors.framework.api.ConnectorInfo;
import org.identityconnectors.framework.api.ConnectorInfoManager;
import org.identityconnectors.framework.api.ConnectorInfoManagerFactory;
import org.identityconnectors.framework.api.ConnectorKey;

public class MyConnector {

	 //Singleton 
	private static  ConnectorFacade conn;
	private static MyConnector myconnector;
	
	
	private String file;
	private String jar;
	private String bundle;
	private String version;
	private String connidType;
	private String host;
	private String port;
	private String user;
	private String password;
	private String database;
	private String table;
	private String keyColumn;
	private String driver;
	private String urltemplate;
	private String passwordCharset;
	
	
	
	/*private MyConnector() {
	
	ConnectorInfoManagerFactory fact = ConnectorInfoManagerFactory.getInstance();
	//File bundleDirectory = new File("C:\\Users\\pdidi\\.m2\\repository\\net\\tirasa\\connid\\bundles\\db\\net.tirasa.connid.bundles.db.table\\2.2.6");       
	File bundleDirectory = new File(file);       
	URL url = null;
	try {
		//url = IOUtil.makeURL(bundleDirectory, "net.tirasa.connid.bundles.db.table-2.2.6.jar");
		url = IOUtil.makeURL(bundleDirectory, jar);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}     */
	
	private MyConnector() {
	}
/*
	//System.out.println(url);
	ConnectorInfoManager manager = fact.getLocalManager(url);
	//ConnectorKey key = new ConnectorKey("net.tirasa.connid.bundles.db.table", "2.2.6", "net.tirasa.connid.bundles.db.table.DatabaseTableConnector");
	ConnectorKey key = new ConnectorKey(bundle,version,connidType);

	//System.out.println(key);
	//System.out.println(manager);

	ConnectorInfo info = manager.findConnectorInfo(key);

	//System.out.println(info);

	// From the ConnectorInfo object, create the default APIConfiguration.
	APIConfiguration apiConfig = info.createDefaultAPIConfiguration();

	// From the default APIConfiguration, retrieve the ConfigurationProperties.
	ConfigurationProperties properties = apiConfig.getConfigurationProperties();

	// Print out what the properties are (not necessary)
	//List<String> propertyNames = properties.getPropertyNames();
	
	
	//works
	String host = "127.0.0.1";
	properties.setPropertyValue("host", host);
	String port = "3306";
	properties.setPropertyValue("port", port);
	String user = "root";
	properties.setPropertyValue("user", user);
	GuardedString password = new GuardedString("root".toCharArray());
	properties.setPropertyValue("password", password);
	String database = "monokee";
	properties.setPropertyValue("database", database);
	String table = "accounts";
	properties.setPropertyValue("table", table);
	String keyColumn = "accountId";
	properties.setPropertyValue("keyColumn", keyColumn);
	String driver = "com.mysql.cj.jdbc.Driver";
	properties.setPropertyValue("jdbcDriver", driver);
	String urltemplate = "jdbc:mysql://%h:%p/%d";
	properties.setPropertyValue("jdbcUrlTemplate", urltemplate);
	String passwordCharset = "UTF-8";
	properties.setPropertyValue("passwordCharset", passwordCharset);
	
	//properties.setPropertyValue(name, value);
	// Use the ConnectorFacadeFactory's newInstance() method to get a new connector.
	ConnectorFacade connector = ConnectorFacadeFactory.getInstance().newInstance(apiConfig);

	// Make sure we have set up the Configuration properly
	connector.validate(); 
	
	conn = connector;
	}
	*/
	
	public void setConnector() {
		ConnectorInfoManagerFactory fact = ConnectorInfoManagerFactory.getInstance();
		//File bundleDirectory = new File("C:\\Users\\pdidi\\.m2\\repository\\net\\tirasa\\connid\\bundles\\db\\net.tirasa.connid.bundles.db.table\\2.2.6");       
		File bundleDirectory = new File(file);       
		URL url = null;
		try {
			//url = IOUtil.makeURL(bundleDirectory, "net.tirasa.connid.bundles.db.table-2.2.6.jar");
			url = IOUtil.makeURL(bundleDirectory, jar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       

		//System.out.println(url);
		ConnectorInfoManager manager = fact.getLocalManager(url);
		//ConnectorKey key = new ConnectorKey("net.tirasa.connid.bundles.db.table", "2.2.6", "net.tirasa.connid.bundles.db.table.DatabaseTableConnector");
		ConnectorKey key = new ConnectorKey(bundle,version,connidType);

		//System.out.println(key);
		//System.out.println(manager);

		ConnectorInfo info = manager.findConnectorInfo(key);

		//System.out.println(info);

		// From the ConnectorInfo object, create the default APIConfiguration.
		APIConfiguration apiConfig = info.createDefaultAPIConfiguration();

		// From the default APIConfiguration, retrieve the ConfigurationProperties.
		ConfigurationProperties properties = apiConfig.getConfigurationProperties();

		// Print out what the properties are (not necessary)
		//List<String> propertyNames = properties.getPropertyNames();
		
		
		//works
		//String host = "127.0.0.1";
		properties.setPropertyValue("host", host);
		//String port = "3306";
		properties.setPropertyValue("port", port);
		//String user = "root";
		properties.setPropertyValue("user", user);
		//GuardedString password = new GuardedString("root".toCharArray());
		properties.setPropertyValue("password", new GuardedString(password.toCharArray()));
		//String database = "monokee";
		properties.setPropertyValue("database", database);
		//String table = "accounts";
		properties.setPropertyValue("table", table);
		//String keyColumn = "accountId";
		properties.setPropertyValue("keyColumn", keyColumn);
		//String driver = "com.mysql.cj.jdbc.Driver";
		properties.setPropertyValue("jdbcDriver", driver);
		//String urltemplate = "jdbc:mysql://%h:%p/%d";
		properties.setPropertyValue("jdbcUrlTemplate", urltemplate);
		//String passwordCharset = "UTF-8";
		properties.setPropertyValue("passwordCharset", passwordCharset);
		
		//properties.setPropertyValue(name, value);
		// Use the ConnectorFacadeFactory's newInstance() method to get a new connector.
		ConnectorFacade connector = ConnectorFacadeFactory.getInstance().newInstance(apiConfig);

		// Make sure we have set up the Configuration properly
		//connector.validate(); 
		
		conn = connector;
	}
	
	public static MyConnector getInstance() 
	    { 
	        if (myconnector == null) 
	            myconnector = new MyConnector(); 
	  
	        return myconnector; 
	    } 
	 
	public void setFile(String f) {
		file = f;
	}
	
	public void setJar(String j) {
		jar = j;
	}
	
	public void setBundle(String b) {
		bundle = b;
	}
	
	public void setVersion(String ver) {
		version = ver;
	}
	
	public void setConnidType(String connid) {
		connidType = connid;
	}
	
	public void setHost(String h) {
		host = h;
	}
	
	public void setPort(String p) {
		port = p;
	}
	
	public void setUser(String u) {
		user = u;
	}
	
	public void setPassword(String pwd) {
		password = pwd;
	}
	
	public void setDatabase(String db) {
		database = db;
	}
	
	public void setTable(String t) {
		table = t;
	}
	
	public void setKeyColumn(String keycolumn) {
		keyColumn = keycolumn;
	}
	
	public void setDriver(String d) {
		driver = d;
	}
	
	public void setUrlTemplate(String url) {
		urltemplate = url;
	}
	
	public void setPasswordCharset(String charset) {
		passwordCharset = charset;
	}
	
	public void validate() {
		conn.validate();
	}
	
	public ConnectorFacade getFacade() {
		return conn;
	}
}
