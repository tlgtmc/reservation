package com.smarthost.reservation;

import com.smarthost.reservation.config.HotelConfiguration;
import com.smarthost.reservation.domain.dto.RoomCountUpdateRequestDto;
import com.smarthost.reservation.enums.CategoryType;
import com.smarthost.reservation.service.RoomService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tatmaca
 */

@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;


    @Autowired
    private HotelConfiguration hotelConfiguration;

    @Test
    @Order(1)
    public void updateRoomCountTest(){

        updateRoomCount(3,5);

        assertEquals(3, hotelConfiguration.getPremiumRoomCount());

        assertEquals(5, hotelConfiguration.getEconomyRoomCount());
    }

    @Test
    @Order(2)
    public void reserveRoomTest() {
        updateRoomCount(1,1);

        assertEquals(1, hotelConfiguration.getPremiumRoomCount());
        assertEquals(1, hotelConfiguration.getEconomyRoomCount());

        roomService.reserveRoom(CategoryType.PREMIUM);
        roomService.reserveRoom(CategoryType.ECONOMY);

        assertFalse(roomService.checkAvailabilityFor(CategoryType.PREMIUM));
        assertFalse(roomService.checkAvailabilityFor(CategoryType.ECONOMY));
    }

    @Test
    @Order(3)
    public void checkOutRoomTest() {

        roomService.checkOutAllRooms();

        updateRoomCount(1,1);

        assertEquals(1, hotelConfiguration.getPremiumRoomCount());
        assertEquals(1, hotelConfiguration.getEconomyRoomCount());

        roomService.reserveRoom(CategoryType.PREMIUM);
        roomService.reserveRoom(CategoryType.ECONOMY);

        assertFalse(roomService.checkAvailabilityFor(CategoryType.PREMIUM));
        assertFalse(roomService.checkAvailabilityFor(CategoryType.ECONOMY));

        roomService.checkOutRoom(CategoryType.PREMIUM);
        assertTrue(roomService.checkAvailabilityFor(CategoryType.PREMIUM));

        roomService.checkOutRoom(CategoryType.ECONOMY);
        assertTrue(roomService.checkAvailabilityFor(CategoryType.ECONOMY));
    }

    @Test
    @Order(4)
    public void checkAvailabilityTest(){
        roomService.checkOutAllRooms();

        updateRoomCount(1,1);

        assertEquals(1, hotelConfiguration.getPremiumRoomCount());
        assertEquals(1, hotelConfiguration.getEconomyRoomCount());

        roomService.reserveRoom(CategoryType.PREMIUM);
        roomService.reserveRoom(CategoryType.ECONOMY);

        assertFalse(roomService.checkAvailabilityFor(CategoryType.PREMIUM));
        assertFalse(roomService.checkAvailabilityFor(CategoryType.ECONOMY));

        roomService.checkOutAllRooms();

        assertTrue(roomService.checkAvailabilityFor(CategoryType.PREMIUM));
        assertTrue(roomService.checkAvailabilityFor(CategoryType.ECONOMY));
    }

    @Test
    @Order(5)
    public void checkOutAllRoomsTest() {

        updateRoomCount(1,1);

        assertEquals(1, hotelConfiguration.getPremiumRoomCount());
        assertEquals(1, hotelConfiguration.getEconomyRoomCount());

        roomService.reserveRoom(CategoryType.PREMIUM);
        roomService.reserveRoom(CategoryType.ECONOMY);
        assertFalse(roomService.checkAvailabilityFor(CategoryType.PREMIUM));
        assertFalse(roomService.checkAvailabilityFor(CategoryType.ECONOMY));

        roomService.checkOutAllRooms();

        assertTrue(roomService.checkAvailabilityFor(CategoryType.PREMIUM));
        assertTrue(roomService.checkAvailabilityFor(CategoryType.ECONOMY));
    }

    private void updateRoomCount(int premiumRoomCount, int economyRoomCount) {

        var roomCountUpdateDto = new RoomCountUpdateRequestDto();
        roomCountUpdateDto.setCount(premiumRoomCount);
        roomCountUpdateDto.setType(CategoryType.PREMIUM);
        roomService.updateRoomCount(roomCountUpdateDto);

        roomCountUpdateDto.setCount(economyRoomCount);
        roomCountUpdateDto.setType(CategoryType.ECONOMY);
        roomService.updateRoomCount(roomCountUpdateDto);
    }
}
