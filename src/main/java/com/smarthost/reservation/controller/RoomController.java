package com.smarthost.reservation.controller;

import com.smarthost.reservation.config.HotelConfiguration;
import com.smarthost.reservation.domain.ApiResponse;
import com.smarthost.reservation.domain.dto.RoomCountUpdateRequestDto;
import com.smarthost.reservation.service.impl.RoomServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>RoomController</h1>
 *
 * This controller uses "/room" mapping.
 *
 * @author tatmaca
 */

@AllArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomServiceImpl roomService;
    private final HotelConfiguration hotelConfiguration;

    /**
     * This method updates room count for given request DTO.
     * @param roomCount -> request DTO
     * @return ApiResponse -> Status and explanation result
     */
    @PostMapping("/count")
    public ApiResponse updateRoomCount(@RequestBody RoomCountUpdateRequestDto roomCount) {
        return roomService.updateRoomCount(roomCount);
    }

    /**
     * This method checks-out all the rooms.
     * @return ApiResponse -> Status and explanation result
     */
    @PutMapping("/checkOutAll")
    public ApiResponse checkOutAllRooms() {
        return roomService.checkOutAllRooms();
    }

    /**
     * This method returns room counts.
     * @return String -> explanation of room counts.
     */
    @GetMapping("/counts")
    public String getRoomCounts() {
        var sb = new StringBuilder("Premium Room Count: ");
        sb.append(hotelConfiguration.getPremiumRoomCount());
        sb.append(", ");
        sb.append("Economy Room Count: ");
        sb.append(hotelConfiguration.getEconomyRoomCount());

        return sb.toString();
    }
}
