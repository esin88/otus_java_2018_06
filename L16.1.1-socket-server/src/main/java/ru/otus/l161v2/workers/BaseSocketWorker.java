package ru.otus.l161v2.workers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l161v2.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BaseSocketWorker implements SocketWorker {
    private static final Logger LOG = LoggerFactory.getLogger(BaseSocketWorker.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final int QUEUE_CAPACITY = 10;
    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

    private final Thread sender = new Thread(this::sendLoop);
    private final Thread receiver = new Thread(this::receiveLoop);

    private final Socket socket;

    public BaseSocketWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void start() {
        sender.setName("Sender");
        sender.start();
        receiver.setName("Receiver");
        receiver.start();
    }

    @Override
    public final boolean offer(Message message) {
        return output.offer(message);
    }

    @Override
    public final void put(Message message) throws InterruptedException {
        output.put(message);
    }

    @Override
    public final Message poll() {
        return input.poll();
    }

    @Blocks
    @Override
    public final Message take() throws InterruptedException {
        return input.take();
    }

    public void close() {
        sender.interrupt();
        receiver.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Blocks
    private void sendLoop() {
        try (final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                final Message message = output.take(); //blocks
                final String json = MAPPER.writeValueAsString(message);
                writer.println(json);
//                LOG.info("Sending message: " + json);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
    }

    @Blocks
    private void receiveLoop() {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            while (socket.isConnected() && (inputLine = reader.readLine()) != null) { //blocks
//                LOG.info("Receiving message: " + inputLine);
                final Message message = MAPPER.readValue(inputLine, Message.class);
                input.add(message);
            }
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        } finally {
            close();
        }
    }
}
