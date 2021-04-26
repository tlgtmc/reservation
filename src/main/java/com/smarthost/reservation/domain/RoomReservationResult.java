package com.smarthost.reservation.domain;

import com.smarthost.reservation.enums.CategoryType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tatmaca
 */

@Data
public class RoomReservationResult {

    private List<Double> customerPayments = new ArrayList<>();
    private CategoryType categoryType;
    private String currency;

    public int getReservedRoomCount() {
        return this.getCustomerPayments().size();
    }

    public void addPayment(double payment) {
        this.getCustomerPayments().add(payment);
    }

    private Double getTotalPayment() {
        return this.getCustomerPayments().stream().mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public String toString() {
        return String.format("Usage %s: %d (%s %,.2f)", getCategoryType().getFriendlyName(), getReservedRoomCount(), getCurrency(), getTotalPayment());
    }
}
