package ca.allnetauto.common;

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
    public String[] get(
            @ScriptArg("address") String addr,
            @ScriptArg("port") int port,
            @ScriptArg("OID") String[] OIDS,
            @ScriptArg("Others") String... params)
    {
        return snmp.snmpGet(addr, port, OIDS, params);
    }

    protected abstract String[] getImpl(String addr, int port, String[] OIDS, String... params);
}
