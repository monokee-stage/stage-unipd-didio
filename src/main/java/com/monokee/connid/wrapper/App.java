package com.monokee.connid.wrapper;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    				
    				MyConnector conn = new MyConnector();
    				
    				//mySQL example
    				conn.setBundleDirectory("C:\\Users\\pdidi\\.m2\\repository\\net\\tirasa\\connid\\bundles\\db\\net.tirasa.connid.bundles.db.table\\2.2.6");
    				conn.setJar("net.tirasa.connid.bundles.db.table-2.2.6.jar");
    				conn.setBundle("net.tirasa.connid.bundles.db.table");
    				conn.setVersion("2.2.6");
    				conn.setConnidType("net.tirasa.connid.bundles.db.table.DatabaseTableConnector");
    				
    				HashMap<String, Object> properties = new HashMap<String, Object>();
    				properties.put("host", "127.0.0.1");
    				properties.put("port", "3306");
    				properties.put("user", "root");
    				properties.put("password", new GuardedString("root".toCharArray()));
    				properties.put("database", "monokee");
    				properties.put("table", "accounts");
    				properties.put("keyColumn", "accountId");
    				properties.put("jdbcDriver", "com.mysql.cj.jdbc.Driver");
    				properties.put("jdbcUrlTemplate", "jdbc:mysql://%h:%p/%d");
    				properties.put("passwordCharset", "UTF-8");
    				
    				
    				/*
    				//LDAP example
    				conn.setBundleDirectory("C:\\Users\\pdidi\\.m2\\repository\\net\\tirasa\\connid\\bundles\\net.tirasa.connid.bundles.ldap\\1.5.4");
    				conn.setJar("net.tirasa.connid.bundles.ldap-1.5.4.jar");
    				conn.setBundle("net.tirasa.connid.bundles.ldap");
    				conn.setVersion("1.5.4");
    				conn.setConnidType("net.tirasa.connid.bundles.ldap.LdapConnector");
    				
    				HashMap<String, Object> properties = new HashMap<String, Object>();
    				properties.put("host", "localhost");
    				properties.put("port", 389);
    				properties.put("principal","cn=Manager,dc=my-domain,dc=com");
    				properties.put("credentials", new GuardedString("sudo".toCharArray()));
    				properties.put("baseContexts", new String[] {"dc=my-domain,dc=com"});
    				properties.put("readSchema", true);
    				//properties.put("accountObjectClasses", new String[] {"top","person","organizationalPerson","posixAccount","shadowAccount","inetOrgPerson"});
    				properties.put("accountObjectClasses", new String[] {"top","person","organizationalPerson","inetOrgPerson"});
    				*/
    				
    				//pass all the connection parameters in a Map<String,Object>
    				conn.setConnector(properties);
    				
    				//check if the connection is properly set
    				conn.validate();
    				
    				//create some queries (need to be parsed)
			    	String sql6 = "salary gt 2000.00";
			    	String sql10 = "department eq \"Product Development\" and age gt 36 or age eq 35";
			    	String ldap1 = "homeDirectory eq \"/home/hacker\"";
			    	String ldap2= "uidNumber gt \"100\"";
			    	String ldap3= "ou eq \"People\"";
			    	String ldap4= "uidNumber gt \"100\" and homeDirectory eq \"/home/hacker\" and uidNumber lt \"6666\"";
			    	String ldap5= "sn eq \"Zetts\""; //sn no result or givenName
			    	String ldap6= "employeeType eq \"Employee\""; //multiple results
			    	String ldap7= "cn eq \"Pen Radko\""; //Radko
			    	String ldap8= "cn eq \"Marco Boldo\"";// Boldo
			    	String ldap9= "sn eq \"Boldo\"";// Boldo
			    	String ldap10= "gidNumber eq \"10037\"";// Boldo
			    	
			    	//initializing a parser (with relative connector)
			    	FilterParser parser = new FilterParser(conn) {
			            @Override
			            protected String parseField(String fieldDescription) {
			            	System.out.println(fieldDescription);
			                return fieldDescription;
			            }
			        };
			        
			        // parsing the query
			        Filter query= parser.valueOf(sql10);
			        
			        
			        //result handler to pass to the search query 
			        final List<ConnectorObject> res = new ArrayList<ConnectorObject>();
			        ResultsHandler hand = new ResultsHandler() {
				        public boolean handle(ConnectorObject obj) {
				            res.add(obj);
				            return true;
				        } 
				    };
				    
				    //alternatively, use CustomHandler (LDAP only)
				    //CustomHandler myhand = new CustomHandler();
				    
				    //search
			         conn.getFacade().search(ObjectClass.ACCOUNT, query, hand, null);
			        
			         
			         //print result, if any  w/ ResultHandler
			        if(res.isEmpty()) {System.out.println("The query has no result");}
				    for(ConnectorObject obj : res ) {
				        System.out.println("Name: " + obj.getName() + "\tUID: " + obj.getUid());
				    } 
				    
			         
			         //print with CustomHandler (LDAP Only)
				    //myhand.printObjects();
				    
				    /*
				    //update
				    //works
			         Set<Attribute> toUpdate = new HashSet<Attribute>();
				     toUpdate.add(AttributeBuilder.build("gidNumber", "11111"));
				    conn.getFacade().update(ObjectClass.ACCOUNT, new Uid("43f79794-8ad1-4cd8-b864-223fe3040d09"), toUpdate, new OperationOptionsBuilder().build());
			         */
				   
				    //delete
				    //works
				    //conn.getFacade().delete(ObjectClass.ACCOUNT, new Uid("3801d6fd-401c-4241-b6ce-7f682e996b3f"), new OperationOptionsBuilder().build());
				    
		
    } catch (Exception e) {
		e.printStackTrace();
	}
		
    }
    
    
}
