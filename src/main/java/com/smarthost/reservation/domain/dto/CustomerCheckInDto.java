package com.smarthost.reservation.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <h1>CustomerCheckInDto</h1>
 *
 * This class is request DTO for customer check-in.
 *
 * @author tatmaca
 */

@Data
public class CustomerCheckInDto {

    @NotNull
    private List<Double> payments;
}
