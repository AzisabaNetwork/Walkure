package amata1219.walkure;

public class Channels {

    public static final String REQUEST;
    public static final String RESPONSE;
    public static final String CONNECT;

    static {
        String name = "Walkure";
        String separator = ":";
        String prefix = name + separator;

        REQUEST = prefix + "request";
        RESPONSE = prefix + "response";
        CONNECT = prefix + "connect";
    }

}
