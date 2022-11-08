package IPCalculator;

import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(name = "ipc", description = "Ip Calculator")
public class IpcCommand implements Callable<Integer> {

    @Option(names={"-m", "--mask"}) private String mask;

    @Parameters(index = "0") private String addressString;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message") private boolean helpRequested;

    @Override
    public Integer call() throws Exception {
        if(mask == null){
            Subnet suffixSubnet = new Subnet(addressString);
            System.out.println("Subnet: " + suffixSubnet + "\n"
                    + "Netaddress: " + suffixSubnet.getNetAddress() + "\n"
                    + "Broadcastaddress: " + suffixSubnet.getBroadcastAddress() + "\n"
                    + "Number of Hosts: " + suffixSubnet.getNumberOfHosts() + "\n"
                    + "First Ip in Network: " + suffixSubnet.getFirstIp() + "\n"
                    + "Last Ip in Network: " + suffixSubnet.getLastIp() + "\n"
            );
        } else {
            Subnet addressSubnet = new Subnet(new IpAddress(addressString), new IpAddress(mask));
            System.out.println("Subnet: " + addressSubnet + "\n"
                    + "Network Address: " + addressSubnet.getNetAddress() + "\n"
                    + "Broadcast Address: " + addressSubnet.getBroadcastAddress() + "\n"
                    + "Number of Hosts: " + addressSubnet.getNumberOfHosts() + "\n"
                    + "First Ip in Network: " + addressSubnet.getFirstIp() + "\n"
                    + "Last Ip in Network: " + addressSubnet.getLastIp() + "\n"
            );
        }
        return null;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new IpcCommand()).execute(args);
        System.exit(exitCode);
    }
}
