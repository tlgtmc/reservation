package com.smarthost.reservation.controller;

import com.smarthost.reservation.domain.dto.CustomerCheckInDto;
import com.smarthost.reservation.service.impl.ReservationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author tatmaca
 */

@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    @PostMapping("checkin")
    public String checkInList(@RequestBody CustomerCheckInDto customerCheckInDto) {
        return reservationService.reserve(customerCheckInDto);
    }
}
