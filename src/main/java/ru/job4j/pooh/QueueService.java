package ru.job4j.pooh;

import java.util.*;
import java.util.concurrent.*;

public class QueueService implements Service {
    private static Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        }
        return null;
    }
}
