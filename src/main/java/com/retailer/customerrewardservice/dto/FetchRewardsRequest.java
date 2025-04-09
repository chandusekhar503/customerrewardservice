package com.retailer.customerrewardservice.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FetchRewardsRequest {

  @NotBlank(message = "customerId should not be blank.")
  private String customerId;

  @NotNull(message = "fromDate should not be empty.")
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate fromDate;

  @NotNull(message = "toDate should not be empty.")
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate toDate;

}
