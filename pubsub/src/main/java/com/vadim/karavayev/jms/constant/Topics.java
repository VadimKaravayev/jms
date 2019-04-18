package com.vadim.karavayev.jms.constant;

public final class Topics {
    private Topics() {
        throw new UnsupportedOperationException("No instance is allowed");
    }

    public static final String EMP_TOPIC = "topic/empTopic";
    public static final String CREDIT_CARD = "topic/creditCard";
}
