package ru.job4j.pooh;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

public class PoohServer {

    private final static Map<DispatcherMethod, Service> SERVICE_DISPATCHER = Map.of(
            DispatcherMethod.queue, new QueueService(),
            DispatcherMethod.topic, new TopicService()
    );

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     InputStream input = socket.getInputStream()) {
                    byte[] buff = new byte[1_000_000];
                    var total = input.read(buff);
                    var text = new String(Arrays.copyOfRange(buff, 0, total), StandardCharsets.UTF_8);
                    var request = Req.of(text);
                    var service = SERVICE_DISPATCHER.get(request.getPoohMode());
                    var response = service.process(request);
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    out.write(response.text().getBytes());
                }
            }
        }
    }
}