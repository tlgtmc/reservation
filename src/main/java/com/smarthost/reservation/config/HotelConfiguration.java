package com.smarthost.reservation.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>HotelConfiguration</h1>
 *
 * This class reads the configuration
 * parameters from property file.
 *
 * Fields are
 * - premiumRoomCount
 * - economyRoomCount
 * - currency
 *
 * @author tatmaca
 */

@Data
@Configuration
public class HotelConfiguration {

    @Value("${hotel.room.premium-count:10}")
    private Integer premiumRoomCount;

    @Value("${hotel.room.economy-count:10}")
    private Integer economyRoomCount;

    @Value("${hotel.payment.currency:EUR}")
    private String currency;

}
