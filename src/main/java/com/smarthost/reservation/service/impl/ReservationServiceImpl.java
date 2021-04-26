package com.smarthost.reservation.service.impl;

import com.smarthost.reservation.config.HotelConfiguration;
import com.smarthost.reservation.domain.RoomReservationResult;
import com.smarthost.reservation.domain.dto.CustomerCheckInDto;
import com.smarthost.reservation.enums.CategoryType;
import com.smarthost.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tatmaca
 */

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final RoomServiceImpl roomService;
    private final HotelConfiguration hotelConfiguration;

    @Override
    public String reserve(CustomerCheckInDto checkInDto) {
        Map<CategoryType, List<Double>> categorizedCustomers = categorizeCustomers(checkInDto);

        return reserveByCustomerCategory(categorizedCustomers);
    }

    private Map<CategoryType, List<Double>> categorizeCustomers(CustomerCheckInDto checkInDto) {
        return checkInDto
                .getPayments()
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.groupingBy(c -> (c >= 100 ? CategoryType.PREMIUM : CategoryType.ECONOMY)));
    }

    private String reserveByCustomerCategory(Map<CategoryType, List<Double>> categorizedCustomers) {

        var premiumReservations = checkIn(categorizedCustomers.get(CategoryType.PREMIUM), CategoryType.PREMIUM);

        var economyReservations = checkIn(categorizedCustomers.get(CategoryType.ECONOMY), CategoryType.ECONOMY);

        this.checkUpgradeIfNecessary(premiumReservations, economyReservations, categorizedCustomers.get(CategoryType.ECONOMY));

        var sb = new StringBuilder(premiumReservations.toString());
        sb.append("\n");
        sb.append(economyReservations);
        return sb.toString();
    }

    private RoomReservationResult checkIn(List<Double> payments, CategoryType type) {
        var reservationResult = new RoomReservationResult();
        reservationResult.setCategoryType(type);
        reservationResult.setCurrency(hotelConfiguration.getCurrency());

        checkAvailabilityAndReserveRoom(type, payments, reservationResult);

        return reservationResult;
    }

    private void checkAvailabilityAndReserveRoom(CategoryType type, List<Double> payments, RoomReservationResult reservationResult) {

        Iterator<Double> iterator = payments.iterator();

        while (roomService.checkAvailabilityFor(type) && iterator.hasNext()) {
            roomService.reserveRoom(type);
            reservationResult.addPayment(iterator.next());
            iterator.remove();
        }
    }

    private void checkUpgradeIfNecessary(RoomReservationResult premiumReservations, RoomReservationResult economyReservations, List<Double> waitingCustomers) {

        List<Double> tmpList = new ArrayList<>();

        Iterator<Double> iterator = economyReservations.getCustomerPayments().iterator();

        Iterator<Double> waitingCustIterator = waitingCustomers.iterator();

        while (iterator.hasNext() && waitingCustIterator.hasNext() && roomService.checkAvailabilityFor(CategoryType.PREMIUM)) {
            premiumReservations.addPayment(iterator.next());
            iterator.remove();

            roomService.reserveRoom(premiumReservations.getCategoryType());
            roomService.checkOutRoom(economyReservations.getCategoryType());

            tmpList.add(waitingCustIterator.next());
            waitingCustIterator.remove();
        }
        checkAvailabilityAndReserveRoom(CategoryType.ECONOMY, tmpList, economyReservations);
    }
}
