package com.smarthost.reservation.domain.dto;

import com.smarthost.reservation.enums.CategoryType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <h1>RoomCountUpdateRequestDto</h1>
 *
 * This class is request DTO for room count update.
 *
 * @author tatmaca
 */

@Data
public class RoomCountUpdateRequestDto {

    @NotNull
    private int count;

    @NotNull
    private CategoryType type;
}
