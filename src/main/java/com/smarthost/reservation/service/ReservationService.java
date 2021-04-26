package com.smarthost.reservation.service;

import com.smarthost.reservation.config.HotelConfiguration;
import com.smarthost.reservation.domain.dto.CustomerCheckInDto;
import com.smarthost.reservation.enums.CategoryType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author tatmaca
 */

@AllArgsConstructor
@Service
public class ReservationService {

    private final RoomService roomService;

    public void checkInCustomerList(CustomerCheckInDto checkInDto) {

        Map<CategoryType, List<Double>> categorizedCustomers = checkInDto.getPayments().stream().sorted(Comparator.reverseOrder())
                .collect(Collectors.groupingBy(c -> (c >= 100 ? CategoryType.PREMIUM : CategoryType.ECONOMY)));

        var premiumPayments = checkInCustomers(categorizedCustomers.get(CategoryType.PREMIUM), CategoryType.PREMIUM);
        var economyPayments = checkInCustomers(categorizedCustomers.get(CategoryType.ECONOMY), CategoryType.ECONOMY);

        System.out.println("Premium payments " + premiumPayments);
        System.out.println("Economy payments " + economyPayments);
        System.out.println(categorizedCustomers);
    }


    private Double checkInCustomers(List<Double> payments, CategoryType type) {
        double totalPayment = 0.0;
        if (type.equals(CategoryType.ECONOMY)) {
            totalPayment += checkForUpgrade(payments, CategoryType.PREMIUM);
        }

        for (Double payment: payments) {
            if (roomService.checkAvailabilityFor(type)){
                totalPayment += payment;
                roomService.reserveRoom(type);
            }
        }
        return totalPayment;
    }

    private Double checkForUpgrade(List<Double> payments, CategoryType type) {
        double totalPayment = 0.0;

        Iterator<Double> iterator = payments.iterator();

        while (roomService.checkAvailabilityFor(type) && iterator.hasNext()) {
            totalPayment += iterator.next();
            roomService.reserveRoom(type);
            iterator.remove();
        }

        return totalPayment;
    }
}
