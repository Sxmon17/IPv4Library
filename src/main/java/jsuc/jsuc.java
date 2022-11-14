 package jsuc;

import de.vandermeer.asciitable.AT_Row;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.asciitable.CWC_LongestWordMax;
import de.vandermeer.asciithemes.TA_GridThemes;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Help.Ansi;

import javax.security.auth.login.AccountNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.lang.*;
import java.io.*;

@Command(
        name = "jsac",
        description = "Subnet Calculator",
        subcommands = {
                Info.class,
                Save.class,
                History.class,
                Reset.class,
                AddHeader.class,
                Bitmap.class,
                Contains.class
        }
)
public class jsuc implements Callable<Integer> {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message") private boolean helpRequested;

    public static String createOutput(Subnet subnet, boolean colored) {
        String format = "%-30s%s%n";
        String output =
                String.format(format, "Suffix: ", subnet.getSuffix())
                        + String.format(format, "Netaddress: ", subnet.getNetAddress())
                        + String.format(format, "Broadcastaddress: ", subnet.getBroadcastAddress())
                        + String.format(format, "Number of Hosts: ", subnet.getNumberOfHosts())
                        + String.format(format, "First Ip in Network: ", subnet.getFirstIp())
                        + String.format(format, "Last Ip in Network: ", subnet.getLastIp())
                        + String.format(format, "Next Subnet: ", subnet.getNextSubnet());
        if(colored) {
            return colorOutput(output);
        }
        return output;
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


    @Override
    public Integer call() throws Exception {
        System.out.println("Use a subcommand");
        return null;
    }
    public static void main(String... args) {
        int exitCode = new CommandLine(new jsuc()).execute(args);
        System.exit(exitCode);
    }
}

@Command(
        name = "info",
        description = "%n@|bold,underline Description|@:%n%n"
)
class Info implements Runnable {
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
    public void run() {
        Subnet subnet = new Subnet(new IpAddress(ipAddress), new IpAddress(subnetmask));
        System.out.println(jsuc.createOutput(subnet, true));
    }
}

@Command(
        name = "save",
        description = "save network to history"
)
class Save implements Runnable {
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
    public void run() {
        Subnet subnet = new Subnet(new IpAddress(ipAddress), new IpAddress(subnetmask));
        History.write(createSubnetNameHeader(subnet) + jsuc.createOutput(subnet, false) + "\n", true);
        System.out.println("Successfully saved");
    }
    public static String createSubnetNameHeader(Subnet subnet) {
        return StringUtils.center(subnet.toString(), 62, '-') + "\n";
    }
}

@Command(
        name = "addHeader",
        description = "adds a custom header to the history"
)
class AddHeader implements Runnable {
    @Parameters(
            index = "0",
            paramLabel = "header",
            description = "custom header as string"

    ) private String header;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message") private boolean helpRequested;

    @Override
    public void run() {
        History.write(
                StringUtils.center("", 62, '#') + "\n"
                     + StringUtils.center(header, 62, '-') + "\n"
                     + StringUtils.center("", 62, '#') + "\n" + "\n", true
        );
    }
}

@Command(
        name = "history",
        description = "displays saved history"
)
class History implements Runnable {
    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message") private boolean helpRequested;

    @Override
    public void run() {
        Path filePath = Path.of("/home/simon/IdeaProjects/jsuc/src/main/resources/output.txt");
        try {
            System.out.println(Files.readString(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(String input, boolean append) {
        try{
            File file = new File("/home/simon/IdeaProjects/jsuc/src/main/resources/output.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(input);
            bw.close();
        } catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
    }
}

@Command(
        name = "reset",
        description = "resets the saved history"
)
class Reset implements Runnable {
    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message") private boolean helpRequested;

    @Override
    public void run() {
       History.write("", false);
    }
}

@Command(
        name = "contains",
        description = "Checks if IpAddress is contained in NetworkAddress / Subnetmask"
)
class Contains implements Runnable {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";

    @Parameters(
            index = "0",
            paramLabel = "IP Address",
            description = "networkaddress in dotted decimal notation"
    ) private String ipAddress;

    @Parameters(
            index = "1",
            paramLabel = "Subnetmask",
            description = "subnet mask in dotted decimal notation"
    ) private String subnetmask;

    @Parameters(
            index = "2",
            paramLabel = "IP Address",
            description = "check if an address is contained in the given subnet"
    ) private String containedIpAddress;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message") private boolean helpRequested;

   public void run() {
       IpAddress address = new IpAddress(containedIpAddress);
       Subnet subnet = new Subnet(new IpAddress(ipAddress), new IpAddress(subnetmask));
       System.out.println(
               "Address " + GREEN + address.getAsString() + RESET
                       + " is in subnet " + GREEN + subnet + RESET + ": "
                       + subnet.isInNetwork(new IpAddress(containedIpAddress))
       );
   }
}

@Command(name = "bitmap")
class Bitmap implements Runnable {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

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

    @Override
    public void run() {
        System.out.println(generateTable(new IpAddress(ipAddress), new IpAddress(subnetmask)));
    }

    public String colorBits(String uncoloredString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < uncoloredString.length(); i++) {
            switch (uncoloredString.charAt(i)) {
                case '0' -> sb.append(RED + "0");
                case '1' -> sb.append(GREEN + "1");
                case '.' -> sb.append(RESET + ".");
            }
        }
        sb.append(RESET);
        return sb.toString();
    }

    public String generateTable(IpAddress address, IpAddress mask) {
        Subnet subnet = new Subnet(address, mask);
        String format = "%-27s%s%n";
        System.out.println(StringUtils.center("", 62, '='));
        System.out.printf(format, "ipAddress", colorBits(IpAddress.getAsBinaryString(address.toString())));
        System.out.printf(format, "subnetmask", colorBits(IpAddress.getAsBinaryString(mask.toString())));
        System.out.println(StringUtils.center("", 62, '='));
        System.out.printf(format, "networkaddress (AND)", colorBits(IpAddress.getAsBinaryString(subnet.getNetAddress())));
        System.out.println(StringUtils.center("", 62, '='));
        return "";
    }
}