package com.smarthost.reservation.controller;

import com.smarthost.reservation.config.HotelConfiguration;
import com.smarthost.reservation.domain.ApiResponse;
import com.smarthost.reservation.domain.dto.RoomCountUpdateRequestDto;
import com.smarthost.reservation.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author tatmaca
 */

@AllArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    private final HotelConfiguration hotelConfiguration;

    @PostMapping("/count")
    public ApiResponse updateRoomCount(@RequestBody RoomCountUpdateRequestDto roomCount) {
        return roomService.updateRoomCount(roomCount);
    }

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
