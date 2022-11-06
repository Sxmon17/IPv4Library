public class Subnet {
    int address;
    int mask;
    public Subnet(String address) {
        this.address = IpUtility.to32BitIp(address.substring(0, address.indexOf("/")));
        if(address.substring(address.indexOf("/")+1).length() <= 2) {
            this.mask = IpUtility.to32BitIp(
                    String.valueOf(0xffffffff << 32 - Integer.parseInt(
                            address.substring(address.indexOf("/")+1)
                    ))
            );
        } else {
            this.mask = IpUtility.to32BitIp(address.substring(address.indexOf("/")+1));
        }
    }

    public Subnet(IpAddress address, int suffix) {
        this.address = address.getAsInt();
        this.mask = IpUtility.to32BitIp(
                String.valueOf(0xffffffff << 32 - suffix)
        );
    }

    public Subnet(IpAddress address, IpAddress mask) {
        this.address = address.getAsInt();
        this.mask = mask.getAsInt();
    }

    public Subnet(String address, String mask) {
        this.address = IpUtility.to32BitIp(address);
        this.mask = IpUtility.to32BitIp(mask);
    }

    public String getNetMask() {
        return IpUtility.toString(this.mask);
    }

    public String getNetAddress() {
        return IpUtility.toString(this.address);
    }

    public int getNumberOfHosts() {
        return (int) Math.pow(2, 32 - Integer.toBinaryString(this.mask).chars().filter(ch -> ch == '1').count()) - 2;
    }

    public boolean isInNetwork(IpAddress address) {
        return ((address.getAsInt() - this.address)+1 & this.mask) == 0;
    }

    public IpAddress getBroadcastAddress() {
        return new IpAddress((this.address | ~this.mask));
    }

    public IpAddress getFirstIp() {
        return new IpAddress((this.address & this.mask) + 1);
    }

    public IpAddress getLastIp() {
        return new IpAddress((this.address | ~this.mask) - 1);
    }

    public IpAddress[] getAllIpsInNetwork() {
        IpAddress[] addresses = new IpAddress[1 + getLastIp().getAsInt() - getFirstIp().getAsInt()];
        for (int i = 0; i <= getLastIp().getAsInt()-getFirstIp().getAsInt(); i++) {
            addresses[i] = new IpAddress(getFirstIp().getAsInt() + i);
        }
        return addresses;
    }

    public Subnet getNextSubnet() {
        return new Subnet(new IpAddress(getBroadcastAddress().getAsInt() + 1), new IpAddress(this.mask));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subnet subnet = (Subnet) o;

        if (address != subnet.address) return false;
        return mask == subnet.mask;
    }

    @Override
    public int hashCode() {
        int result = address;
        result = 31 * result + mask;
        return result;
    }

    @Override
    public String toString() {
        return IpUtility.toString(address) + "/" + IpUtility.toString(mask);
    }
}
