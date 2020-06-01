package ca.allnetauto.client;

import ca.allnetauto.common.AbstractScriptModule;
import ca.allnetauto.common.FunctionInterface;
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
    protected String[] getImpl(String addr, int port, String[] OIDS, String... params){
        return rpc.get(addr, port, OIDS, params);
    }

}
