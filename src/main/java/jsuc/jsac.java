package jsuc;

import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "jsac",
        description = "Subnet Calculator",
        subcommands = {Contains.class}
)
public class jsac implements Callable<Integer> {
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

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message") private boolean helpRequested;

    @Override
    public Integer call() throws Exception {
        Subnet subnet = new Subnet(new IpAddress(ipAddress), new IpAddress(subnetmask));
        System.out.println("Suffix: " + GREEN + subnet.getSuffix() + RESET);
        System.out.println(
                "Netaddress: " + GREEN + subnet.getNetAddress() + RESET + "\n"
                + "Broadcastaddress: " + GREEN + subnet.getBroadcastAddress() + RESET + "\n"
                + "Number of Hosts: " + GREEN + subnet.getNumberOfHosts() + RESET + "\n"
                + "First Ip in Network: " + GREEN + subnet.getFirstIp() + RESET + "\n"
                + "Last Ip in Network: " + GREEN + subnet.getLastIp() + RESET
        );
        return null;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new jsac()).execute(args);
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
       Subnet subnet = new Subnet(new IpAddress(jsac.ipAddress), new IpAddress(jsac.subnetmask));
       System.out.println(
               "Address " + GREEN + address.getAsString() + RESET
                       + " is in subnet " + GREEN + subnet + RESET + ": "
                       + subnet.isInNetwork(new IpAddress(containedIpAddress))
       );
   }
}