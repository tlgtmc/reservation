package com.smarthost.reservation.service;

import com.smarthost.reservation.domain.dto.CustomerCheckInDto;

/**
 * <h1>ReservationService</h1>
 *
 * This interface specifies the functionalities of ReservationService
 *
 * @author tatmaca
 */

public interface ReservationService {

    String reserve(CustomerCheckInDto checkInDto);

}
