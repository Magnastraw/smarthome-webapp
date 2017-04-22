package com.netcracker.smarthome.business.endpoints.transformators;

public interface ITransformator<T,U> {
    public T fromJsonEntity (U jsonEntity);

    public U toJsonEntity (T Entity);
}
