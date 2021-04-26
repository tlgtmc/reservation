package com.smarthost.reservation.controller;

import com.smarthost.reservation.domain.dto.CustomerCheckInDto;
import com.smarthost.reservation.enums.CategoryType;
import com.smarthost.reservation.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tatmaca
 */

@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("checkin")
    public void checkInList(@RequestBody CustomerCheckInDto customerCheckInDto) {
        reservationService.checkInCustomerList(customerCheckInDto);
    }
}
