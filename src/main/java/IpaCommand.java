import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


@CommandLine.Command(
        name = "hello",
        description = "Says hello"
)
public class IpaCommand implements Runnable{
    public static void main(String[] args) {
        CommandLine.run(new IpaCommand(), args);
    }

    @CommandLine.Option(
            names = {"-i", "--ipaddress"}
    )
    private String ipaddress;

    @CommandLine.Parameters
    private List<String> addresses;

    @Override
    public void run() {
        addresses.forEach(String -> System.out.println("Adding " + Arrays.toString(new IpAddress(String).getAsArray()) + " to the staging area"));
        //IpAddress address = new IpAddress(ipaddress);
        //System.out.println(IpUtility.to32BitIp(String.valueOf(address)));
    }
}
