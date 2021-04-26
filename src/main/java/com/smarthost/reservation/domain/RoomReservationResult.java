package com.smarthost.reservation.domain;

import com.smarthost.reservation.enums.CategoryType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>RoomReservationResult</h1>
 *
 * This class is reservation result for the given category.
 * It specifies the currency with configuration.
 *
 * @author tatmaca
 */

@Data
public class RoomReservationResult {

    private List<Double> customerPayments = new ArrayList<>();
    private CategoryType categoryType;
    private String currency;

    /**
     * This method returns the total reserved room count for the specified category.
     * @return int -> Reserved room count
     */
    public int getReservedRoomCount() {
        return this.getCustomerPayments().size();
    }

    /**
     * This method adds new payment to the payment list.
     * @param payment -> double payment value to be added.
     */
    public void addPayment(double payment) {
        this.getCustomerPayments().add(payment);
    }

    /**
     * This method returns the total amount of payments.
     * @return Double
     */
    private Double getTotalPayment() {
        return this.getCustomerPayments().stream().mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public String toString() {
        return String.format("Usage %s: %d (%s %,.2f)", getCategoryType().getFriendlyName(), getReservedRoomCount(), getCurrency(), getTotalPayment());
    }
}
