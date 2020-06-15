package com.monokee.connid.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.ResultsHandler;

public final class CustomHandler implements ResultsHandler {

    private final List<String> connectorObjects = new ArrayList<String>();
    private List<String> uids = new ArrayList<String>();
    
    
    public boolean handle(ConnectorObject object) {
    	System.out.println((String) object.getAttributeByName("uid").getValue().get(0) + "-->" + (String) object.getAttributeByName("cn").getValue().get(0));
    	uids.add(object.getUid().getUidValue());
    	connectorObjects.add((String) object.getAttributeByName("cn").getValue().get(0));
        return true;
    }

    public List<String> getObjects() {
        return connectorObjects;
    }
    
    public List<String> getUids() {
        return uids;
    }
    
    public void printObjects(){
   	 List<String> a= this.getObjects();
        int b= 0;
        for (int i =0; i < this.getObjects().size(); b++ ) {
        	System.out.println("Print oggetto " + (b+1) + ": "+a.remove(i));        	
        }
   
        a= this.getUids();
        for (int i =0; i < a.size(); i++ ) {
        	System.out.println("Print Uid " + (i+1) + ": "+a.get(i));        	
        }
   }
   
   public int size () {
   	return connectorObjects.size();
   }


}