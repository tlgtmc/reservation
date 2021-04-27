package com.smarthost.reservation;

import com.smarthost.reservation.controller.ReservationController;
import com.smarthost.reservation.domain.dto.CustomerCheckInDto;
import com.smarthost.reservation.domain.dto.RoomCountUpdateRequestDto;
import com.smarthost.reservation.enums.CategoryType;
import com.smarthost.reservation.service.RoomService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tatmaca
 */

@SpringBootTest
public class ReservationControllerTest {

    private static final Path inputFilePathLocal = Paths.get("src","main","resources","test","input");

    private static List<Double> parameterList;

    @Autowired
    private ReservationController reservationController;

    @Autowired
    private RoomService roomService;

    @BeforeAll
    static void readDataFromFile() {
        String input = "";
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = Files.newInputStream(inputFilePathLocal)) {
            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    if (stringBuilder.toString().length() > 0)
                        stringBuilder.append("\n");

                    stringBuilder.append(scanner.nextLine());
                }
                input = stringBuilder.toString();
            }
        } catch (IOException e) {
        }
        parameterList = Arrays.stream(input.substring(1,input.length()-1).split(",")).map(Double::new).collect(Collectors.toList());

        System.out.println(input);
    }

    @BeforeEach
    public void checkOutRooms() {
        roomService.checkOutAllRooms();
    }

    @Test
    public void threeRoomsForEachCategoryTest() {
        updateRoomCount(3, 3);

        StringBuilder expectedResultSb = new StringBuilder("Usage Premium: 3 (EUR 738.00)");
        expectedResultSb.append("\n");
        expectedResultSb.append("Usage Economy: 3 (EUR 167.99)");

        var inputDTO = new CustomerCheckInDto();
        inputDTO.setPayments(parameterList);
        var result = reservationController.checkInList(inputDTO);
        assertEquals(expectedResultSb.toString(), result);
    }

    @Test
    public void sevenPremiumFiveEconomyRoomsTest() {
        updateRoomCount(7, 5);

        StringBuilder expectedResultSb = new StringBuilder("Usage Premium: 6 (EUR 1,054.00)");
        expectedResultSb.append("\n");
        expectedResultSb.append("Usage Economy: 4 (EUR 189.99)");

        var inputDTO = new CustomerCheckInDto();
        inputDTO.setPayments(parameterList);
        var result = reservationController.checkInList(inputDTO);
        assertEquals(expectedResultSb.toString(), result);
    }

    @Test
    public void twoPremiumSevenEconomyRoomsTest() {
        updateRoomCount(2, 7);

        StringBuilder expectedResultSb = new StringBuilder("Usage Premium: 2 (EUR 583.00)");
        expectedResultSb.append("\n");
        expectedResultSb.append("Usage Economy: 4 (EUR 189.99)");

        var inputDTO = new CustomerCheckInDto();
        inputDTO.setPayments(parameterList);
        var result = reservationController.checkInList(inputDTO);
        assertEquals(expectedResultSb.toString(), result);
    }

    @Test
    public void sevenPremiumOneEconomyRoomsTest() {
        updateRoomCount(7, 1);

        StringBuilder expectedResultSb = new StringBuilder("Usage Premium: 7 (EUR 1,153.99)");
        expectedResultSb.append("\n");
        expectedResultSb.append("Usage Economy: 1 (EUR 45.00)");

        var inputDTO = new CustomerCheckInDto();
        inputDTO.setPayments(parameterList);
        var result = reservationController.checkInList(inputDTO);
        assertEquals(expectedResultSb.toString(), result);
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
