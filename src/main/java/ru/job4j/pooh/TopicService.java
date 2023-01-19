package ru.job4j.pooh;

import java.util.concurrent.*;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String requestType = req.httpRequestType();
        String name = req.getSourceName();
        String param = req.getParam();
        if ("POST".equals(requestType)) {
            topics.get(name).forEach((s, strings) -> strings.add(param));
            return new Resp(param, "200");
        }
        if ("GET".equals(requestType)) {
            topics.putIfAbsent(name, new ConcurrentHashMap<>());
            topics.get(name).putIfAbsent(param, new ConcurrentLinkedQueue<>());
            var text = topics.get(name).get(param).poll();
            return new Resp(text, "200");
        }
        return null;
    }
}
