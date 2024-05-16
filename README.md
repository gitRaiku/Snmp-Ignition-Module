# Snmp-Ignition-Module
A module that allows the user to use snmp operations in scripts

# Newer projects
Killez has forked my project having added walk functionality among other things, so go show him some love
https://github.com/kiilez/Snmp-Ignition-Module

# Limitations
As of now the only operation available is snmp Get and can be called by using <code>system.snmp.get('address', port, ['OID1', 'OID2', ...], 'community')</code>, This will return a python list with the length equal to the number of OIDs provided

You can also specify other variables like the snmp version, timeout or retry count by adding 'var=value' as an extra parameter at the end.
For example <code> system.snmp.get('address', port, ['OID1', 'OID2'], 'community', 'version=1', 'timeout=2000', 'retry=2') </code>
This will return a python list with the length of 2 containing the values of OID1 and OID2, or an error message.
It will perform the get operation on version 1, with a timeout of 2000ms and a retry count of 2.

If these variables are not given by the user it will use the defaults, meaning: version=2c, timeout=3000, retry=1

Currently this module does not have support for snmp v3


