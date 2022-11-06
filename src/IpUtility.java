import java.util.Arrays;

public class IpUtility {
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

    public static String toString(int ip32Bit){
        String[] octetts;
        if(Integer.toHexString(ip32Bit).length() < 8) {
            octetts = ("0" + Integer.toHexString(ip32Bit)).split("(?<=\\G.{" + 2 + "})");
        } else {
            octetts = Integer.toHexString(ip32Bit).split("(?<=\\G.{" + 2 + "})");
        }
        StringBuilder result = new StringBuilder();
        for (String octett : octetts) {
            result.append(Integer.parseInt(octett, 16)).append(".");
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }

    public static int getSuffix(String network) {
        return Integer.parseInt(network.substring(network.indexOf("/")+1));
    }

}
