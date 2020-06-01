/*

Code taken from:

https://sites.google.com/site/mullais/logic/java/how-to-run-a-simple-snmp-get-program-using-java-with-eclipse?tmpl=%2Fsystem%2Fapp%2Ftemplates%2Fprint%2F&showPrintDialog=1

 */

package ca.allnetauto.common;

import java.io.IOException;
import java.util.ArrayList;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class snmp {

    public static final int DEFAULT_VERSION = SnmpConstants.version2c;
    public static final String DEFAULT_PROTOCOL = "udp";
    public static final long DEFAULT_TIMEOUT = 3000L;
    public static final int DEFAULT_RETRY = 1;

    public static CommunityTarget createDefault(String ip, String community, int port, String[] params) {
        Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip + "/" + port);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(address);

        target.setVersion(DEFAULT_VERSION);
        target.setTimeout(DEFAULT_TIMEOUT);
        target.setRetries(DEFAULT_RETRY);

        for(String param : params) {
            String[] value = param.split("=");
            if(value[0].equalsIgnoreCase("version")){
                target.setVersion(getVersion(value[1]));
            } else if(value[0].equalsIgnoreCase("timeout")){
                target.setTimeout(Integer.parseInt(value[1]));
            } else if(value[0].equalsIgnoreCase("retry")){
                target.setRetries(Integer.parseInt(value[1]));
            }
        }
        return target;
    }

    private static int getVersion(String s) {
        if(s.equalsIgnoreCase("1")){
            return SnmpConstants.version1;
        } else if (s.equalsIgnoreCase("3")){
            return SnmpConstants.version3;
        } else {
            return SnmpConstants.version2c;
        }

    }

    public static String[] snmpGet(String ip, int port, String[] oids, String[] params) { /// if anyone knows how to get rid of the first item from an array please let me know
        String community = params[0];
        CommunityTarget target = createDefault(ip, community, port, params);
        PDU pdu = new PDU();
        pdu.addAll(getBindings(oids));
        return get(pdu, target);
    }

    private static VariableBinding[] getBindings(String[] oids) {
        ArrayList<VariableBinding> vars = new ArrayList<>();
        for (String oid : oids) {
            vars.add(new VariableBinding(new OID(oid)));
        }

        return vars.toArray(new VariableBinding[0]);
    }

    private static String[] get(PDU pdu, CommunityTarget target) {

        Snmp snmp = null;
        ArrayList<String> res = new ArrayList<>();
        try {
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            pdu.setType(PDU.GET);
            ResponseEvent respEvent = snmp.send(pdu, target);
            PDU response = respEvent.getResponse();

            if (response == null) {
                res.add("Error: no Response");
            } else {
                for (int i = 0; i < response.size(); i++) {
                    VariableBinding vb = response.get(i);
                    res.add(String.valueOf(vb.getVariable()));
                }
            }
            return res.toArray(new String[0]);
        } catch (Exception e) {
            res.add("Error: " + e.getMessage());
            return res.toArray(new String[0]);
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
