package com.netcracker.smarthome.business.endpoints.transformators;

public interface ITransformator<T,U,P> {
    public T fromJsonEntity (U jsonEntity, P parameter);

    //public U toJsonEntity (T Entity);
}
