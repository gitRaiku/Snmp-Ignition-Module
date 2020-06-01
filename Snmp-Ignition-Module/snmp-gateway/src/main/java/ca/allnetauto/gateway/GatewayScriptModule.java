package ca.allnetauto.gateway;

import ca.allnetauto.common.AbstractScriptModule;
import ca.allnetauto.common.snmp;

public class GatewayScriptModule extends AbstractScriptModule {

    @Override
    protected String[] getImpl(String addr, int port, String[] OIDS, String... params) {
        return snmp.snmpGet(addr, port, OIDS, params);
    }

}