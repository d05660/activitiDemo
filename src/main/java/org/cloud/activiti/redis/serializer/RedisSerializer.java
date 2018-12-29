package org.cloud.activiti.redis.serializer;

import org.cloud.activiti.redis.exception.SerializationException;

public interface RedisSerializer<T> {

    byte[] serialize(T t) throws SerializationException;

    T deserialize(byte[] bytes) throws SerializationException;
}
