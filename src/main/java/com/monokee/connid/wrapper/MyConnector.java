package com.monokee.connid.wrapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

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

	
	private ConnectorFacade conn;

	//same parameters for each type of connection
	private String bundleDirectory;
	private String jar;
	private String bundle;
	private String version;
	private String connidType;
	

	public void setConnector(Map<String,Object> settings) {
		ConnectorInfoManagerFactory fact = ConnectorInfoManagerFactory.getInstance();
		//example
		// File bundleDirectory = new
		// File("C:\\Users\\pdidi\\.m2\\repository\\net\\tirasa\\connid\\bundles\\db\\net.tirasa.connid.bundles.db.table\\2.2.6");
		File bundleDir = new File(bundleDirectory);
		URL url = null;
		try {
			//example
			// url = IOUtil.makeURL(bundleDirectory,
			// "net.tirasa.connid.bundles.db.table-2.2.6.jar");
			url = IOUtil.makeURL(bundleDir, jar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ConnectorInfoManager manager = fact.getLocalManager(url);
		//example
		// ConnectorKey key = new ConnectorKey("net.tirasa.connid.bundles.db.table",
		// "2.2.6", "net.tirasa.connid.bundles.db.table.DatabaseTableConnector");
		ConnectorKey key = new ConnectorKey(bundle, version, connidType);

		ConnectorInfo info = manager.findConnectorInfo(key);


		// From the ConnectorInfo object, create the default APIConfiguration.
		APIConfiguration apiConfig = info.createDefaultAPIConfiguration();

		// From the default APIConfiguration, retrieve the ConfigurationProperties.
		ConfigurationProperties properties = apiConfig.getConfigurationProperties();

		
		for (Map.Entry<String, Object> entry: settings.entrySet()) {
			properties.setPropertyValue(entry.getKey(), entry.getValue());
		}
		
		// Use the ConnectorFacadeFactory's newInstance() method to get a new connector.
		ConnectorFacade connector = ConnectorFacadeFactory.getInstance().newInstance(apiConfig);
		
		/*
		//It's just a print of all the properies
		// From the default APIConfiguration, retrieve the ConfigurationProperties.
		// Print out what the properties are (not necessary)
				List<String> propertyNames = properties.getPropertyNames();
				for(String propName : propertyNames) {
					ConfigurationProperty prop = properties.getProperty(propName);
					System.out.println("Property Name: " + prop.getName() + "\tProperty Type: " + prop.getType());
				}
		*/

		conn = connector;
	}

	

	public void setBundleDirectory(String bundleDir) {
		bundleDirectory = bundleDir;
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

	public void validate() {
		
		conn.validate();
	}

	public ConnectorFacade getFacade() {
		return conn;
	}
}
