package com.smarthost.reservation.enums;

/**
 * @author tatmaca
 */

public enum CategoryType {
    ECONOMY("Economy"),
    PREMIUM("Premium");

    String friendlyName;

    CategoryType(String name) {
        this.friendlyName = name;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
}
