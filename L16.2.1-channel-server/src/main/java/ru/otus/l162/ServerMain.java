package ru.otus.l162;

import ru.otus.l162.runner.ProcessRunnerImpl;
import ru.otus.l162.server.BlockingEchoSocketMsgServer;
import ru.otus.l162.server.NonBlockingEchoSocketMsgServer;
import ru.otus.l162.server.NonBlockingLogSocketMsgServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    private static final String CLIENT_START_COMMAND = "java -jar ../L16.2.2-channel-client/target/client.jar";
    private static final int CLIENT_START_DELAY_SEC = 1;
    private static final int CLIENTS_COUNT = 1;

    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        startClients(CLIENTS_COUNT, executorService);

//        startBlockingServer();
        startLogServer();
        //startEchoServer();

        executorService.shutdown();
    }

    private void startLogServer() throws Exception {
        new NonBlockingLogSocketMsgServer().start();
    }

    private void startBlockingServer() throws Exception {
        new BlockingEchoSocketMsgServer().start();
    }

    private void startEchoServer() throws Exception {
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        final ObjectName name = new ObjectName("ru.otus:type=Server");
        final NonBlockingEchoSocketMsgServer server = new NonBlockingEchoSocketMsgServer();
        mbs.registerMBean(server, name);

        server.start();
    }

    private void startClients(int count, ScheduledExecutorService executorService) {
        for (int i = 0; i < count; i++) {
            executorService.schedule(() -> {
                try {
                    new ProcessRunnerImpl().start(CLIENT_START_COMMAND);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }, CLIENT_START_DELAY_SEC + i, TimeUnit.SECONDS);
        }
    }

}
