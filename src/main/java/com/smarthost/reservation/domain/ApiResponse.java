package com.smarthost.reservation.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * <h1>ApiResponse</h1>
 *
 * Response object of the application
 * consists the following fields:
 *
 * number
 * httpStatus
 * httpStatusCode
 *
 * @author tatmaca
 */

@NoArgsConstructor
@Data
public class ApiResponse {

    private String value;
    private HttpStatus httpStatus;
    private int httpStatusCode;

    public ApiResponse(String value, HttpStatus httpStatus, int httpStatusCode) {
        this.value = value;
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
    }
}
