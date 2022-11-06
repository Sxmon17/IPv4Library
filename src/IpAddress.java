import java.util.Arrays;

public class IpAddress {
    int address;

    final String LOCALHOST = "127.0.0.1";
    final String MODEM = "10.0.0.138";

    public IpAddress() {
        this.address = IpUtility.to32BitIp("127.0.0.1");
    }

    public IpAddress(int address) {
        this.address = address;
    }

    public IpAddress(int o1, int o2, int o3, int o4) {
        this.address = IpUtility.to32BitIp(o1+"."+o2+"."+o3+"."+o4);
    }

    public IpAddress(int[] octetts) {
        this.address = IpUtility.to32BitIp(octetts[0]+"."+octetts[1]+"."+octetts[2]+"."+octetts[3]);
    }

    public IpAddress(String address) {
        this.address = IpUtility.to32BitIp(address);
    }

    public void set(int address) {
        this.address = address;
    }

    public void set(int o0, int o1, int o2, int o3) {
        this.address = IpUtility.to32BitIp(o0+"."+o1+"."+o2+"."+o3);
    }

    public void set(int[] octetts) {
        this.address = IpUtility.to32BitIp(octetts[0]+"."+octetts[1]+"."+octetts[2]+"."+octetts[3]);
    }

    public void set(String address) {
        this.address = IpUtility.to32BitIp(address);
    }

    public int getAsInt() {
        return this.address;
    }

    public int getOctett(int num) {
        int[] octetts = Arrays.stream(IpUtility.toString(this.address).split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
        return octetts[num];
    }

    public int[] getAsArray(){
        return Arrays.stream(IpUtility.toString(this.address).split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IpAddress address1 = (IpAddress) o;

        return address == address1.address;
    }

    @Override
    public int hashCode() {
        return address;
    }

    @Override
    public String toString() {
        return IpUtility.toString(address);
    }
}
