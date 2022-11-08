package jsuc;

public class TestSubnet {
    public static void main(String[] args) {
        Subnet stringSubnet = new Subnet("192.168.0.1/16");
        System.out.println("String jsuc.Subnet: " + stringSubnet
                + " getNetmask: " + stringSubnet.getNetMask()
                + " getNetAddress: " + stringSubnet.getNetAddress()
                + " getNumberOfHosts: " + stringSubnet.getNumberOfHosts()
                + " getBroadcastAddress: " + stringSubnet.getBroadcastAddress()
        );

        Subnet addressAndSuffixSubnet = new Subnet(new IpAddress("192.168.0.1"), 20);
        System.out.println("Address and Suffix jsuc.Subnet: " + addressAndSuffixSubnet
                + " getNetmask: " + addressAndSuffixSubnet.getNetMask()
                + " getNetAddress: " + addressAndSuffixSubnet.getNetAddress()
                + " getNumberOfHosts: " + addressAndSuffixSubnet.getNumberOfHosts()
                + " getBroadcastAddress: " + addressAndSuffixSubnet.getBroadcastAddress()
        );

        Subnet addressSubnet = new Subnet(new IpAddress("192.168.0.1"), new IpAddress("255.255.252.0"));
        System.out.println("Address jsuc.Subnet: " + addressSubnet
                + " getNetmask: " + addressSubnet.getNetMask()
                + " getNetAddress: " + addressSubnet.getNetAddress()
                + " getNumberOfHosts: " + addressSubnet.getNumberOfHosts()
                + " getBroadcastAddress: " + addressSubnet.getBroadcastAddress()
        );

        Subnet stringsSubnet = new Subnet("192.168.0.1", "255.255.192.0");
        System.out.println("Strings jsuc.Subnet: " + stringsSubnet
                + " getNetmask: " + stringsSubnet.getNetMask()
                + " getNetAddress: " + stringsSubnet.getNetAddress()
                + " getNumberOfHosts: " + stringsSubnet.getNumberOfHosts()
                + " getBroadcastAddress: " + stringsSubnet.getBroadcastAddress()
                + "\n"
        );

        System.out.println("Get 1. Ip from Strings jsuc.Subnet Array: " + stringsSubnet.getAllIpsInNetwork()[0]);
        System.out.println("Get 10. Ip from Strings jsuc.Subnet Array: " + stringsSubnet.getAllIpsInNetwork()[10]);
        System.out.println("Get Last Ip from Strings jsuc.Subnet Array: " + stringsSubnet.getAllIpsInNetwork()[stringsSubnet.getAllIpsInNetwork().length-1] + "\n");

        System.out.println("Get next jsuc.Subnet of Strings jsuc.Subnet Array: " + stringsSubnet.getNextSubnet() + "\n");

        IpAddress address1 = new IpAddress("192.168.30.200");
        System.out.println("Is " + address1 +
                " in range " + stringsSubnet.getFirstIp() + " to " + stringsSubnet.getLastIp() + " from network " + stringsSubnet + ": " + stringsSubnet.isInNetwork(address1));

        IpAddress address2 = new IpAddress("192.168.70.120");
        System.out.println("Is " + address2 +
                " in range " + stringsSubnet.getFirstIp() + " to " + stringsSubnet.getLastIp() + " from network " + stringsSubnet + ": " + stringsSubnet.isInNetwork(address2));
    }
}
