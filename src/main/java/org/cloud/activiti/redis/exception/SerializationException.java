package org.cloud.activiti.redis.exception;

public class SerializationException extends Exception {
    private static final long serialVersionUID = 1L;
    public SerializationException(String msg) {
        super(msg);
    }
    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
