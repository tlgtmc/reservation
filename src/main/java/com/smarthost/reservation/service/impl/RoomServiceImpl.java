package com.smarthost.reservation.service.impl;

import com.smarthost.reservation.config.HotelConfiguration;
import com.smarthost.reservation.domain.ApiResponse;
import com.smarthost.reservation.domain.dto.RoomCountUpdateRequestDto;
import com.smarthost.reservation.enums.CategoryType;
import com.smarthost.reservation.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author tatmaca
 */

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final HotelConfiguration hotelConfiguration;

    private int reservedEconomyRoomCount = 0;
    private int reservedPremiumRoomCount = 0;

    @Override
    public ApiResponse updateRoomCount(RoomCountUpdateRequestDto requestDto) {
        switch (requestDto.getType()) {
            case ECONOMY:
                hotelConfiguration.setEconomyRoomCount(requestDto.getCount());
                break;
            case PREMIUM:
                hotelConfiguration.setPremiumRoomCount(requestDto.getCount());
                break;
        }
        return new ApiResponse(String.format("%s room count successfully updated to %d.", requestDto.getType().getFriendlyName(), hotelConfiguration.getPremiumRoomCount()), HttpStatus.OK, HttpStatus.OK.value());
    }

    @Override
    public void reserveRoom(CategoryType categoryType) {
        switch (categoryType) {
            case ECONOMY:
                this.reservedEconomyRoomCount++;
                break;
            case PREMIUM:
                this.reservedPremiumRoomCount++;
                break;
        }
    }

    @Override
    public void checkOutRoom(CategoryType categoryType) {
        switch (categoryType) {
            case ECONOMY:
                this.reservedEconomyRoomCount--;
                break;
            case PREMIUM:
                this.reservedPremiumRoomCount--;
                break;
        }
    }

    @Override
    public boolean checkAvailabilityFor(CategoryType categoryType) {
        switch (categoryType) {
            case ECONOMY:
                return this.reservedEconomyRoomCount < hotelConfiguration.getEconomyRoomCount();
            case PREMIUM:
                return this.reservedPremiumRoomCount < hotelConfiguration.getPremiumRoomCount();
        }
        return false;
    }

    @Override
    public ApiResponse checkOutAllRooms() {
        this.reservedEconomyRoomCount = 0;
        this.reservedPremiumRoomCount = 0;
        return new ApiResponse("All guests are checked out!.", HttpStatus.OK, HttpStatus.OK.value());
    }
}
