package com.netcracker.smarthome.model.enums;

import org.apache.commons.lang3.text.WordUtils;

public enum PolicyStatus {
    CREATED,
    ACTIVE,
    INACTIVE,
    ERROR;

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(name());
    }
}
