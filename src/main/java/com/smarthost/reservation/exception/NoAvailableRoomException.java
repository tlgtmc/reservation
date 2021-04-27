package com.smarthost.reservation.exception;

/**
 * @author tatmaca
 */

public class NoAvailableRoomException extends Exception {
    public NoAvailableRoomException(String message, Exception e) {
        super(message, e);
    }

    public NoAvailableRoomException(String message) {
        super(message);
    }
}
