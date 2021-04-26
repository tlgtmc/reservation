package com.smarthost.reservation.service;

import com.smarthost.reservation.domain.dto.CustomerCheckInDto;

/**
 * @author tatmaca
 */

public interface ReservationService {

    String reserve(CustomerCheckInDto checkInDto);

}
