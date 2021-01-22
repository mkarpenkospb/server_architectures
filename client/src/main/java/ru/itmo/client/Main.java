package ru.itmo.client;


import org.apache.commons.cli.*;

public class Main {
    private static final Options posixOptions = new Options();

    static {
        Option host = new Option("h", "host", true, "Server host");
        host.setArgs(1);
        posixOptions.addOption(host);

        Option port = new Option("p", "port", true, "Server port");
        port.setArgs(1);
        posixOptions.addOption(port);

        Option delta = new Option("d", "delta", true, "Delta between requests");
        delta.setArgs(1);
        posixOptions.addOption(delta);

        Option x = new Option("x", true, "Number of requests");
        x.setArgs(1);
        posixOptions.addOption(x);

        Option n = new Option("n", true, "Size of array");
        n.setArgs(1);
        posixOptions.addOption(n);
    }


    public static void main(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(posixOptions, args);

            int port = Integer.parseInt(cmd.getOptionValue("port"));
            String host = cmd.getOptionValue("host");
            int d = Integer.parseInt(cmd.getOptionValue("delta"));
            int x = Integer.parseInt(cmd.getOptionValue("x"));
            int n = Integer.parseInt(cmd.getOptionValue("n"));

            Client client = new Client(host, port, x, n, d);

            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
