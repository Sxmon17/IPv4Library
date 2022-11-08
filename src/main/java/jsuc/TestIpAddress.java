package IPCalculator;

import java.util.Arrays;

public class TestIpAddress {
    public static void main(String[] args) {
        IpAddress defaultAddress = new IpAddress();
        System.out.println("Default Address: " + defaultAddress);

        IpAddress decAddress = new IpAddress(IpAddress.to32BitIp("10.0.0.1"));
        System.out.println("Decimal Address: " + decAddress);

        IpAddress octAddress = new IpAddress(192, 168, 2, 1);
        System.out.println("Octett Address: " + octAddress);

        IpAddress arrayAddress = new IpAddress(new int[]{192, 168, 2, 1});
        System.out.println("Array Address: " + arrayAddress);

        IpAddress stringAddress = new IpAddress("192.168.2.1");
        System.out.println("String Address: " + stringAddress + "\n");

        IpAddress address = new IpAddress();

        address.set(IpAddress.to32BitIp("192.168.0.1"));
        System.out.println("Set Decimal Address: " + address);
        System.out.println("Get as Int: " + address.getAsInt());
        address.set(IpAddress.to32BitIp("10.0.0.1"));
        System.out.println("Set Decimal Address: " + address);
        System.out.println("Get as Int: " + address.getAsInt() + "\n");

        address.set(192, 168, 0, 1);
        System.out.println("Set Octett Address: " + address);
        System.out.println("Get Octett[0]:" + address.getOctett(0));
        address.set(10, 0, 0, 1);
        System.out.println("Set Octett Address: " + address);
        System.out.println("Get Octett[1]:" + address.getOctett(1) + "\n");

        address.set(new int[]{192, 168, 0, 1});
        System.out.println("Set Array Address: " + address);
        System.out.println("Get as Array: " + Arrays.toString(address.getAsArray()));
        address.set(new int[]{10, 0, 0, 1});
        System.out.println("Set Array Address: " + address);
        System.out.println("Get as Array: " + Arrays.toString(address.getAsArray()) + "\n");

        address.set("192.168.0.1");
        System.out.println("Set String Address: " + address);
        address.set("10.0.0.1");
        System.out.println("Set String Address: " + address + "\n");

        IpAddress address1 = new IpAddress("10.0.0.1");
        IpAddress address2 = new IpAddress(10, 0, 0, 1);
        System.out.println(address1 + " equals " + address2 + ": " + address1.equals(address2));
        address1.set("192.168.0.1");
        System.out.println(address1 + " equals " + address2 + ": " + address1.equals(address2));
    }
}
