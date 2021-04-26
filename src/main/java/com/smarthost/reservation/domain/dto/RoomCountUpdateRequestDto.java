package com.smarthost.reservation.domain.dto;

import com.smarthost.reservation.enums.CategoryType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tatmaca
 */

@Data
public class RoomCountUpdateRequestDto {

    @NotNull
    private int count;

    @NotNull
    private CategoryType type;
}
