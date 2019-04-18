package constants;

public final class Queues {

    private Queues() {
        throw new UnsupportedOperationException("No instance allowed");
    }

    public static final String REQUEST_QUEUE = "queue/requestQueue";
    public static final String REPLY_QUEUE = "queue/replyQueue";
}
