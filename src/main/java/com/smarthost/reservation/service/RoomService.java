package com.smarthost.reservation.service;

import com.smarthost.reservation.domain.ApiResponse;
import com.smarthost.reservation.domain.dto.RoomCountUpdateRequestDto;
import com.smarthost.reservation.enums.CategoryType;

/**
 * @author tatmaca
 */

public interface RoomService {

    ApiResponse updateRoomCount(RoomCountUpdateRequestDto requestDto);
    void reserveRoom(CategoryType categoryType);
    void checkOutRoom(CategoryType categoryType);
    boolean checkAvailabilityFor(CategoryType categoryType);
    ApiResponse checkOutAllRooms();
}
