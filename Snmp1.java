package com.example.lib;

import com.example.activitudemo.Middle;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

public class Snmp1 {




    org.snmp4j.Snmp snmp = null;

    String address = null;

    /**

     * Constructor

     * @param add

     */

    public Snmp1(String add) {

        address = add;


    }
    public static void Run() throws IOException {



        Snmp1 client = new Snmp1("udp:XX.XXX.XXX.XX/161");

        client.start();


        String s11 = client.getAsString(new OID("1.3.6.1.4.1.26381.1.0"));
        String s22 = client.getAsString(new OID("1.3.6.1.4.1.26381.2.0"));

       temp1 = (Float.parseFloat(s11));
        hum1 = Float.parseFloat(s22);

        temp11 =temp1/10;
        hum11 = hum1/10;


        Middle.x = temp11;
        Middle.y =hum11;



        //System.out.println("Server's Temperature is = " + temp11);
        //System.out.println("Server's Humidity is = " +hum11);




    }



    public static void Runner() throws IOException {



        Snmp2 client = new Snmp2("udp:XX.XXX.XXX.XX/162");

        client.start();



        String s1 = client.getAsString(new OID("1.3.6.1.2.1.33.1.2.7"));
        String s2 = client.getAsString(new OID("1.3.6.1.2.1.33.1.2.4"));


        UPStemp = (Float.parseFloat(s1));
        UPSBattery = Float.parseFloat(s2);



        Middle.w = UPSBattery;
        Middle.z =UPStemp;


        System.out.println("UPS's Temperature is = " + UPStemp);
       System.out.println("UPS's Battery is = " +UPSBattery);



    }


    public static String s1;
    public static String s2;

    public static float temp1;
    public static float hum1;

    private static float hum11 =10;
    private static float temp11=20;

    public static float UPStemp;
    public static float UPSBattery;


    public static float getHum11() {
        return hum11;
    }

    public static float getUPStemp() {
        return UPStemp;
    }

    public static float getUPSBattery() {
        return UPSBattery;
    }




    public static float getTemp11() {
        return temp11;
    }

    public static void main(String[] args) throws IOException {



/**

 * Port 161 is used for Read and Other operations


 */
/**78.108.164.82, port 161*/

        Snmp1 client = new Snmp1("udp:XX.XXX.XXX.XX/161");

        client.start();




         s1 = client.getAsString(new OID("1.3.6.1.4.1.26381.1.0"));
         s2 = client.getAsString(new OID("1.3.6.1.4.1.26381.2.0"));

         temp1 = (Float.parseFloat(s1));
         hum1 = Float.parseFloat(s2);

         temp11 =temp1/10;
         hum11 = hum1/10;

        System.out.println("Server's Temperature is = " + temp11);
        System.out.println("Server's Humidity is = " +hum11);

        //Run();
      Runner();



        Middle.x = temp11;
        Middle.y =hum11;




    }




    public void start() throws IOException {

        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new org.snmp4j.Snmp(transport);
        transport.listen();

    }


    public String getAsString(OID oid) throws IOException {

        ResponseEvent event = get(new OID[] { oid });

        return event.getResponse().get(0).getVariable().toString();

    }




    public ResponseEvent get(OID oids[]) throws IOException {

        PDU pdu = new PDU();

        for (OID oid : oids) {

            pdu.add(new VariableBinding(oid));

        }

        pdu.setType(PDU.GET);

        ResponseEvent event = snmp.send(pdu, getTarget(), null);

        if(event != null) {

            return event;

        }

        throw new RuntimeException("GET timed out");

    }




    private Target getTarget() {

        Address targetAddress = GenericAddress.parse(address);

        CommunityTarget target = new CommunityTarget();

        target.setCommunity(new OctetString("public"));

        target.setAddress(targetAddress);

        target.setRetries(5);

        target.setTimeout(1500);

        //target.setVersion(SnmpConstants.version2c);

        return target;

    }


}


