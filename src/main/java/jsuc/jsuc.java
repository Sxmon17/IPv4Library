package jsuc;

import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.Callable;
import java.lang.*;
import java.io.*;

@Command(
        name = "jsac",
        description = "Subnet Calculator",
        subcommands = {Contains.class}
)
public class jsuc implements Callable<Integer> {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";

    @Parameters(
            index = "0",
            paramLabel = "ipAddress",
            description = "Ip address in dotted decimal notation"
    ) public static String ipAddress;

    @Parameters(
            index="1",
            paramLabel = "subnetmask",
            description = "subnetmask in dotted decimal notation"
    ) public static String subnetmask;

    @Option(names = { "-s", "--save"}) private boolean save;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message") private boolean helpRequested;

    @Override
    public Integer call() throws Exception {
        Subnet subnet = new Subnet(new IpAddress(ipAddress), new IpAddress(subnetmask));
        String format = "%-30s%s%n";
        String output =
                  String.format(format, "Suffix: ", subnet.getSuffix())
                + String.format(format, "Netaddress: ", subnet.getNetAddress())
                + String.format(format, "Broadcastaddress: ", subnet.getBroadcastAddress())
                + String.format(format, "Number of Hosts: ", subnet.getNumberOfHosts())
                + String.format(format, "First Ip in Network: ", subnet.getFirstIp())
                + String.format(format, "Last Ip in Network: ", subnet.getLastIp())
                + String.format(format, "Next Subnet: ", subnet.getNextSubnet());

        System.out.println(colorOutput(output));
        if(save) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(
                        "/home/simon/IdeaProjects/jsuc/src/main/resources/content.txt"
                ));
                writer.write(createSubnetNameHeader(subnet) + output);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String colorOutput(String output) {
        StringBuilder sb = new StringBuilder(output);

        int index1 = sb.indexOf(":");
        while(index1 >= 0) {
            sb.insert(index1+1, GREEN);
            index1 = sb.indexOf(":", index1+1+GREEN.length());
        }

        int index2  = sb.indexOf("\n");
        while(index2 >= 0) {
            sb.insert(index2, RESET);
            index2 = sb.indexOf("\n", index2+1+RESET.length());
        }

        return sb.toString();
    }

    public static String createSubnetNameHeader(Subnet subnet) {
        int length = String.format("%-30s%s%n", "Next Subnet: ", subnet.getNextSubnet()).length();
        return StringUtils.center(subnet.toString(), length, '-') + "\n";
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new jsuc()).execute(args);
        System.exit(exitCode);
    }
}

@Command(name = "contains")
class Contains implements Runnable {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";

    @Parameters(
            index = "0",
            paramLabel = "IP Address",
            description = "check if an address is contained in the given subnet"
    ) private String containedIpAddress;

   public void run() {
       IpAddress address = new IpAddress(containedIpAddress);
       Subnet subnet = new Subnet(new IpAddress(jsuc.ipAddress), new IpAddress(jsuc.subnetmask));
       System.out.println(
               "Address " + GREEN + address.getAsString() + RESET
                       + " is in subnet " + GREEN + subnet + RESET + ": "
                       + subnet.isInNetwork(new IpAddress(containedIpAddress))
       );
   }
}

