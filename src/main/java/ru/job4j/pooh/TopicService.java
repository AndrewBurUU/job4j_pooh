package ru.job4j.pooh;

import java.util.concurrent.*;

public class TopicService implements Service {
    private final ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>
            > topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        return null;
    }
}
