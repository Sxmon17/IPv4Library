package jsuc;

import java.util.Arrays;

public class IpAddress {
    int address;

    final String LOCALHOST = "127.0.0.1";
    final String MODEM = "10.0.0.138";

    public IpAddress() {
        this.address = to32BitIp("127.0.0.1");
    }

    public IpAddress(int address) {
        this.address = address;
    }

    public IpAddress(int o1, int o2, int o3, int o4) {
        this.address = to32BitIp(o1+"."+o2+"."+o3+"."+o4);
    }

    public IpAddress(int[] octetts) {
        this.address = to32BitIp(octetts[0]+"."+octetts[1]+"."+octetts[2]+"."+octetts[3]);
    }

    public IpAddress(String address) {
        this.address = to32BitIp(address);
    }

    public void set(int address) {
        this.address = address;
    }

    public void set(int o0, int o1, int o2, int o3) {
        this.address = to32BitIp(o0+"."+o1+"."+o2+"."+o3);
    }

    public void set(int[] octetts) {
        this.address = to32BitIp(octetts[0]+"."+octetts[1]+"."+octetts[2]+"."+octetts[3]);
    }

    public void set(String address) {
        this.address = to32BitIp(address);
    }

    public int getAsInt() {
        return this.address;
    }

    public String getAsString() {
        return toDecimalString(this.address);
    }

    public int getOctett(int num) {
        return getAsArray()[num];
    }

    public int[] getAsArray(){
        return Arrays.stream(getAsString().split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public String getAsHexString() {
        return Integer.toHexString(this.address);
    }

    public static int to32BitIp(String ip) {
        int[] octetts = Arrays.stream(ip.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
        StringBuilder hexIp = new StringBuilder();
        for (int octett : octetts) {
            if(octett < 0xF) {
                hexIp.append("0");
            }
            hexIp.append(Integer.toHexString(octett));
        }
        return (int)Long.parseLong(hexIp.toString(), 16);
    }

    public static String toDecimalString(int address) {
        String[] octetts;
        if(Integer.toHexString(address).length() < 8) {
            octetts = ("0" + Integer.toHexString(address)).split("(?<=\\G.{" + 2 + "})");
        } else {
            octetts = Integer.toHexString(address).split("(?<=\\G.{" + 2 + "})");
        }
        StringBuilder result = new StringBuilder();
        for (String octett : octetts) {
            result.append(Integer.parseInt(octett, 16)).append(".");
        }
        return result.deleteCharAt(result.length() - 1).toString();
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
        return getAsString();
    }
}
