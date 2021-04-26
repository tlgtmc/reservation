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
 * <h1>ReservationServiceImpl</h1>
 *
 * This class is the implementation class of ReservationService interface
 *
 * @author tatmaca
 */

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final RoomServiceImpl roomService;
    private final HotelConfiguration hotelConfiguration;

    /**
     * This method starts reservation process for the given DTO.
     *
     * Firstly, categorizes the customers and then starts reservation progress.
     *
     * @param checkInDto -> CustomerCheckInDto input parameter
     * @return -> String result explanation
     */
    @Override
    public String reserve(CustomerCheckInDto checkInDto) {
        Map<CategoryType, List<Double>> categorizedCustomers = categorizeCustomers(checkInDto);

        return reserveByCustomerCategory(categorizedCustomers);
    }

    /**
     * This method categorizes customers depending on payments as PREMIUM or ECONOMY customer.
     *
     * @param checkInDto -> CustomerCheckInDto input parameter
     * @return -> Map with key CategoryType and payments as its value.
     */
    private Map<CategoryType, List<Double>> categorizeCustomers(CustomerCheckInDto checkInDto) {
        return checkInDto
                .getPayments()
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.groupingBy(c -> (c >= 100 ? CategoryType.PREMIUM : CategoryType.ECONOMY)));
    }

    /**
     * This method checks in premium and economy customers.
     * Then checks if upgrade is necessary or not.
     *
     * @param categorizedCustomers -> Customers to be checked-in
     * @return -> String explanation of result
     */
    private String reserveByCustomerCategory(Map<CategoryType, List<Double>> categorizedCustomers) {

        var premiumReservations = checkIn(categorizedCustomers.get(CategoryType.PREMIUM), CategoryType.PREMIUM);

        var economyReservations = checkIn(categorizedCustomers.get(CategoryType.ECONOMY), CategoryType.ECONOMY);

        this.checkUpgradeIfNecessary(premiumReservations, economyReservations, categorizedCustomers.get(CategoryType.ECONOMY));

        var sb = new StringBuilder(premiumReservations.toString());
        sb.append("\n");
        sb.append(economyReservations);
        return sb.toString();
    }

    /**
     * This method checksIn with given customer payments and type.
     * It creates a new object and fills the necessary fields.
     *
     * @param payments -> Payments to be processed
     * @param type -> Type of customers
     * @return -> RoomReservationResult as result of the check-in process
     */
    private RoomReservationResult checkIn(List<Double> payments, CategoryType type) {
        var reservationResult = new RoomReservationResult();
        reservationResult.setCategoryType(type);
        reservationResult.setCurrency(hotelConfiguration.getCurrency());

        checkAvailabilityAndReserveRoom(type, payments, reservationResult);

        return reservationResult;
    }

    /**
     * This method checks availability of a room for given type and reserves if available.
     *
     * @param type -> CategoryType
     * @param payments -> Payments to be processed
     * @param reservationResult -> RoomReservationResult object of the reservation
     */
    private void checkAvailabilityAndReserveRoom(CategoryType type, List<Double> payments, RoomReservationResult reservationResult) {

        Iterator<Double> iterator = payments.iterator();

        while (roomService.checkAvailabilityFor(type) && iterator.hasNext()) {
            roomService.reserveRoom(type);
            reservationResult.addPayment(iterator.next());
            iterator.remove();
        }
    }

    /**
     * This method upgrades economy customers to premium if there are any waiting customers.
     *
     * First it reserves a premium room, then checks-out from economy room and adds the customer that will be checked-in to a temp list.
     * Later, temp list is processed by checkAvailabilityAndReserveRoom() method.
     *
     * @param premiumReservations
     * @param economyReservations
     * @param waitingCustomers
     */
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
