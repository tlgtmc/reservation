package com.smarthost.reservation.controller;

import com.smarthost.reservation.domain.dto.CustomerCheckInDto;
import com.smarthost.reservation.service.impl.ReservationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>ReservationController</h1>
 *
 * This controller uses "/reservation" mapping.
 *
 * @author tatmaca
 */

@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    /**
     * This method is used to check in given customer list.
     * @param customerCheckInDto -> contains list for customers
     * @return String result of occupations.
     */
    @PostMapping("checkin")
    public String checkInList(@RequestBody CustomerCheckInDto customerCheckInDto) {
        return reservationService.reserve(customerCheckInDto);
    }
}
