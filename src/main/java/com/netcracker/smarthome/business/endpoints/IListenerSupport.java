package com.netcracker.smarthome.business.endpoints;

public interface IListenerSupport<T> {
    public void addListener(T listener);

    public void removeListener(T listener);
}
