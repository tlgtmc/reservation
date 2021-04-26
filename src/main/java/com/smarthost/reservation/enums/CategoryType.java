package com.smarthost.reservation.enums;

/**
 * <h1>CategoryType</h1>
 *
 * This enum class defines the customer categories.
 *
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
