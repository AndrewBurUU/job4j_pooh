package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Empty content!");
        }
        String[] contentLines = content.split(System.lineSeparator());
        if (contentLines.length < 2) {
            throw new IllegalArgumentException("Incorrect parameter numbers!");
        }
        String[] firstLine = contentLines[0].split(" ");
        String[] slashString = firstLine[1].split("/");
        if (slashString.length < 2) {
            throw new IllegalArgumentException("Not found parameters: poohMode, sourceName!");
        }
        String requestType = firstLine[0];
        String poohMode = slashString[1];
        String sourceName = slashString[2];
        String param = "";
        if ("POST".equals(requestType)) {
            param = contentLines[contentLines.length - 1];
        }
        if ("GET".equals(requestType)
                && "topic".equals(poohMode)
        ) {
            param = slashString[3];
        }
        return new Req(requestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
