package com.retailer.customerrewardservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class CustomerRewardException extends RuntimeException {
    private String errorCode;
    private String errorDescription;
    private HttpStatus httpStatusCode;

}
