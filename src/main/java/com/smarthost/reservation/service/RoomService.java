package com.smarthost.reservation.service;

import com.smarthost.reservation.config.HotelConfiguration;
import com.smarthost.reservation.domain.ApiResponse;
import com.smarthost.reservation.domain.dto.RoomCountUpdateRequestDto;
import com.smarthost.reservation.enums.CategoryType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author tatmaca
 */

@Service
@AllArgsConstructor
public class RoomService {

    private final HotelConfiguration hotelConfiguration;

    public ApiResponse updateRoomCount(RoomCountUpdateRequestDto requestDto) {
        switch (requestDto.getType()) {
            case ECONOMY:
                hotelConfiguration.setEconomyRoomCount(requestDto.getCount());
            case PREMIUM:
                hotelConfiguration.setPremiumRoomCount(requestDto.getCount());
        }
        return new ApiResponse(String.format("%s room count successfully updated to %d.", requestDto.getType().toString(), requestDto.getCount()), HttpStatus.OK, HttpStatus.OK.value());
    }

    public void reserveRoom(CategoryType categoryType) {
        switch (categoryType) {
            case ECONOMY:
                hotelConfiguration.setEconomyRoomCount(hotelConfiguration.getEconomyRoomCount() - 1);
            case PREMIUM:
                hotelConfiguration.setPremiumRoomCount(hotelConfiguration.getPremiumRoomCount() - 1);
        }
    }

    public boolean checkAvailabilityFor(CategoryType categoryType) {
        switch (categoryType) {
            case ECONOMY:
                return hotelConfiguration.getEconomyRoomCount() > 0 ? true : false;
            case PREMIUM:
                return hotelConfiguration.getPremiumRoomCount() > 0 ? true : false;
        }
        return false;
    }
}
