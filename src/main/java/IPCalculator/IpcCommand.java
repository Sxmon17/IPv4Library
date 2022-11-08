import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(name = "ipc", description = "Ip Calculator")
public class IpcCommand implements Callable<Integer> {

    @Option(names = {"-s", "--suffix"})
    private String suffix;

    @Parameters(index = "0")
    private String addressString;

    @Parameters(index = "1")
    private String subnetString;

    @Override
    public Integer call() throws Exception {
        return null;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new IpcCommand()).execute(args);
        System.exit(exitCode);
    }
}
