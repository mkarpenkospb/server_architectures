package ru.itmo.asynch;

import org.apache.commons.cli.*;

public class ServerMain {
    private static final Options posixOptions = new Options();

    static {
        Option port = new Option("p", "port", true, "Server port");
        port.setArgs(1);
        posixOptions.addOption(port);

        Option threads = new Option("t", "threads", true, "Num of threads in threadpool");
        threads.setArgs(1);
        posixOptions.addOption(threads);

    }
    public static void main(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(posixOptions, args);

            int port = Integer.parseInt(cmd.getOptionValue("port"));
            int threads = Integer.parseInt(cmd.getOptionValue("threads"));
            ServerAsynch server = new ServerAsynch(port, threads);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
