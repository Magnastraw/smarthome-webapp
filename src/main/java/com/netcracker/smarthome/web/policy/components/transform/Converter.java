package com.netcracker.smarthome.web.policy.components.transform;

public interface Converter<S, T> {
    T convert(S sourceObject);
}
