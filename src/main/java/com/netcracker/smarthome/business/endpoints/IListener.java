package com.netcracker.smarthome.business.endpoints;

public interface IListener {
    public void onSaveOrUpdate(Long smartHouseId, Object object);
}