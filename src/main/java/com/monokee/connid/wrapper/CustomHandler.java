package com.monokee.connid.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.ResultsHandler;

public final class CustomHandler implements ResultsHandler {

    private final List<String> connectorObjects = new ArrayList<String>();

    public boolean handle(ConnectorObject object) {
    	System.out.println((String) object.getAttributeByName("uid").getValue().get(0) + "-->" + (String) object.getAttributeByName("cn").getValue().get(0));
        connectorObjects.add((String) object.getAttributeByName("cn").getValue().get(0));
        return true;
    }

    public List<String> getObjects() {
        return connectorObjects;
    }

}