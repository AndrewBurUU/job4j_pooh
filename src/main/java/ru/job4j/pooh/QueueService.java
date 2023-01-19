package ru.job4j.pooh;

import java.util.*;
import java.util.concurrent.*;

public class QueueService implements Service {
    private final Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String name = req.getSourceName();
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
            queue.get(name).add(req.getParam());
            return new Resp(req.getParam(), "200");
        }
        if ("GET".equals(req.httpRequestType())) {
            var text = queue.getOrDefault(name, new ConcurrentLinkedQueue<>()).poll();
            return new Resp(text, "200");
        }
        return null;
    }
}
