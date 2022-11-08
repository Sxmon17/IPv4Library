package jsuc;

import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(name = "ipc", description = "Ip Calculator")
public class IpcCommand implements Callable<Integer> {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";

    @Parameters(
            index = "0"
    ) private String addressString;

    @Option(
            names={"-m", "--mask"}
    ) private String mask;

    @Option(
            names={"-c", "--contains"}
    ) private String containedAddress;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message") private boolean helpRequested;

    @Override
    public Integer call() throws Exception {
        Subnet subnet;
        if(mask == null){
            subnet = new Subnet(addressString);
            if(containedAddress != null) {
                containedAddress(subnet);
                return null;
            }
            System.out.println("Subnetmask: " + GREEN + subnet.getNetMask() + RESET);
        } else {
            subnet = new Subnet(new IpAddress(addressString), new IpAddress(mask));
            if(containedAddress != null) {
                containedAddress(subnet);
                return null;
            }
            System.out.println("Suffix: " + GREEN + subnet.getSuffix() + RESET);
        }

        System.out.println(
                "Netaddress: " + GREEN + subnet.getNetAddress() + RESET + "\n"
                + "Broadcastaddress: " + GREEN + subnet.getBroadcastAddress() + RESET + "\n"
                + "Number of Hosts: " + GREEN + subnet.getNumberOfHosts() + RESET + "\n"
                + "First Ip in Network: " + GREEN + subnet.getFirstIp() + RESET + "\n"
                + "Last Ip in Network: " + GREEN + subnet.getLastIp() + RESET + "\n"
        );

        return null;
    }

    public void containedAddress(Subnet subnet) {
        IpAddress address = new IpAddress(containedAddress);
        System.out.println(
                "Address " + GREEN + address.getAsString() + RESET
                        + " is in subnet " + GREEN + subnet + RESET + ": "
                        + subnet.isInNetwork(new IpAddress(containedAddress))
        );
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new IpcCommand()).execute(args);
        System.exit(exitCode);
    }
}
