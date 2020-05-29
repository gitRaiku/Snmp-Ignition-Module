package allnetauto.ca;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.script.hints.ScriptArg;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;

public abstract class AbstractScriptModule implements FunctionInterface {

    static {
        BundleUtil.get().addBundle(
                AbstractScriptModule.class.getSimpleName(),
                AbstractScriptModule.class.getClassLoader(),
                AbstractScriptModule.class.getName().replace('.', '/')
        );
    }

    @Override
    @ScriptFunction
    public String snmpGet(
            @ScriptArg("community") String community,
            @ScriptArg("address") String addr,
            @ScriptArg("port") int port,
            @ScriptArg("OID") String OID)
    {
        return snmp.snmpGet(addr, port, community, OID);
    }

    protected abstract String snmpGetImpl(String community, String addr, int port, String OID);
}
