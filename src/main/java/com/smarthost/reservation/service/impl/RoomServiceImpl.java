package com.smarthost.reservation.service.impl;

import com.smarthost.reservation.config.HotelConfiguration;
import com.smarthost.reservation.domain.ApiResponse;
import com.smarthost.reservation.domain.dto.RoomCountUpdateRequestDto;
import com.smarthost.reservation.enums.CategoryType;
import com.smarthost.reservation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * <h1>RoomServiceImpl</h1>
 *
 * This class is the implementation class of RoomService interface
 *
 * @author tatmaca
 */

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final HotelConfiguration hotelConfiguration;

    private int reservedEconomyRoomCount = 0;
    private int reservedPremiumRoomCount = 0;

    /**
     * This method updates the room count for specified room type.
     *
     * @param requestDto
     * @return ApiResponse -> Status and explanation result
     */
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

    /**
     * This method reserves a room for given specific category type.
     *
     * @param categoryType
     */
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

    /**
     * This method checks-out for given category type.
     * @param categoryType
     */
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

    /**
     * This method checks availability for given category type.
     *
     * @param categoryType
     * @return Boolean
     */
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


    /**
     * This method checks-out all the guests.
     *
     * @return ApiResponse -> Status and explanation result
     */
    @Override
    public ApiResponse checkOutAllRooms() {
        this.reservedEconomyRoomCount = 0;
        this.reservedPremiumRoomCount = 0;
        return new ApiResponse("All guests are checked out!.", HttpStatus.OK, HttpStatus.OK.value());
    }
}
