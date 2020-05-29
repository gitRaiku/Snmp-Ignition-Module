package allnetauto.ca;

public class GatewayScriptModule extends AbstractScriptModule {

    @Override
    protected String snmpGetImpl(String community, String addr, int port, String OID) {
        return snmp.snmpGet(addr, port, community, OID);
    }

}