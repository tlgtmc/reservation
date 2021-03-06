package com.smarthost.reservation.service;

import com.smarthost.reservation.domain.ApiResponse;
import com.smarthost.reservation.domain.dto.RoomCountUpdateRequestDto;
import com.smarthost.reservation.enums.CategoryType;

/**
 * <h1>RoomService</h1>
 *
 * This interface specifies the functionalities of RoomService
 *
 * @author tatmaca
 */

public interface RoomService {

    ApiResponse updateRoomCount(RoomCountUpdateRequestDto requestDto);
    void reserveRoom(CategoryType categoryType);
    void checkOutRoom(CategoryType categoryType);
    boolean checkAvailabilityFor(CategoryType categoryType);
    ApiResponse checkOutAllRooms();
}
