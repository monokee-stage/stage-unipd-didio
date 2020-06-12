package it.athesys.connid.wrapper;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

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
import org.identityconnectors.framework.api.operations.CreateApiOp;
import org.identityconnectors.framework.api.operations.SearchApiOp;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.AttributeInfo;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.ObjectClassInfo;
import org.identityconnectors.framework.common.objects.OperationOptions;
import org.identityconnectors.framework.common.objects.OperationOptionsBuilder;
import org.identityconnectors.framework.common.objects.ResultsHandler;
import org.identityconnectors.framework.common.objects.Schema;
import org.identityconnectors.framework.common.objects.Uid;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.identityconnectors.framework.common.objects.filter.FilterBuilder;

import com.sun.jndi.ldap.ctl.VirtualListViewControl;

import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.search.LdapFilter;
import net.tirasa.connid.bundles.ldap.search.LdapSearch;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	try {
    				/*
					ConnectorInfoManagerFactory fact = ConnectorInfoManagerFactory.getInstance();
					File bundleDirectory = new File("C:\\Users\\pdidi\\.m2\\repository\\net\\tirasa\\connid\\bundles\\db\\net.tirasa.connid.bundles.db.table\\2.2.6");       
					URL url = IOUtil.makeURL(bundleDirectory, "net.tirasa.connid.bundles.db.table-2.2.6.jar");       

					ConnectorInfoManager manager = fact.getLocalManager(url);
					ConnectorKey key = new ConnectorKey("net.tirasa.connid.bundles.db.table", "2.2.6", "net.tirasa.connid.bundles.db.table.DatabaseTableConnector");


					ConnectorInfo info = manager.findConnectorInfo(key);


					// From the ConnectorInfo object, create the default APIConfiguration.
					APIConfiguration apiConfig = info.createDefaultAPIConfiguration();

					// From the default APIConfiguration, retrieve the ConfigurationProperties.
					ConfigurationProperties properties = apiConfig.getConfigurationProperties();

					// Print out what the properties are (not necessary)
					List<String> propertyNames = properties.getPropertyNames();
					for(String propName : propertyNames) {
						ConfigurationProperty prop = properties.getProperty(propName);
						//System.out.println("Property Name: " + prop.getName() + "\tProperty Type: " + prop.getType());
					}

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
					ConnectorFacade conn = ConnectorFacadeFactory.getInstance().newInstance(apiConfig);

					// Make sure we have set up the Configuration properly
					conn.validate(); 
					*/
    		
    				MyConnector conn = MyConnector.getInstance();
    				
    				conn.setFile("C:\\Users\\pdidi\\.m2\\repository\\net\\tirasa\\connid\\bundles\\db\\net.tirasa.connid.bundles.db.table\\2.2.6");
    				conn.setJar("net.tirasa.connid.bundles.db.table-2.2.6.jar");
    				conn.setBundle("net.tirasa.connid.bundles.db.table");
    				conn.setVersion("2.2.6");
    				conn.setConnidType("net.tirasa.connid.bundles.db.table.DatabaseTableConnector");
    				conn.setHost("127.0.0.1");
    				conn.setPort("3306");
    				conn.setUser("root");
    				conn.setPassword("root");
    				conn.setDatabase("monokee");
    				conn.setTable("accounts");
    				conn.setKeyColumn("accountId");
    				conn.setDriver("com.mysql.cj.jdbc.Driver");
    				conn.setUrlTemplate("jdbc:mysql://%h:%p/%d");
    				conn.setPasswordCharset("UTF-8");
    				
    				conn.setConnector();
    				conn.validate();
    				
			    	String a6 = "salary gt 2000.00";
			    	String a10 = "department eq \"Product Development\" and age gt 36 or age eq 35";
			    	
			    	FilterParser parser = new FilterParser() {
			            @Override
			            protected String parseField(String fieldDescription) {
			            	System.out.println(fieldDescription);
			                return fieldDescription;
			            }
			        };
			        
			        //Filter a7 = parser.valueOf(a6);
			        Filter a11= parser.valueOf(a10);
			        
			        final List<ConnectorObject> res = new ArrayList<ConnectorObject>();
			        ResultsHandler hand = new ResultsHandler() {
				        public boolean handle(ConnectorObject obj) {
				            res.add(obj);
				            return true;
				        } 
				    };
				    
				    System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
			         conn.getFacade().search(ObjectClass.ACCOUNT, a11, hand, null);
				    for(ConnectorObject obj : res ) {
				        System.out.println("Name: " + obj.getName() + "\tUID: " + obj.getUid());
				    } 
				    System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
		
    } catch (Exception e) {
		e.printStackTrace();
	}
		
    }
    
    
}
