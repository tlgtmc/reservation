package com.smarthost.reservation.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tatmaca
 */

@Data
public class CustomerCheckInDto {

    @NotNull
    private List<Double> payments;
}
