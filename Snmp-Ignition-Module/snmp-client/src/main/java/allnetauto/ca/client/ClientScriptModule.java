package allnetauto.ca.client;

import allnetauto.ca.AbstractScriptModule;
import allnetauto.ca.FunctionInterface;
import com.inductiveautomation.ignition.client.gateway_interface.ModuleRPCFactory;

public class ClientScriptModule extends AbstractScriptModule {

    private final FunctionInterface rpc;

    public ClientScriptModule() {
        rpc = ModuleRPCFactory.create(
                "allnetauto.ca.snmp-1.0.0",
                FunctionInterface.class
        );
    }

    @Override
    protected String snmpGetImpl(String community, String addr, int port, String OID){
        return rpc.snmpGet(community, addr, port, OID);
    }

}
