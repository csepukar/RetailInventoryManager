package com.capstoneproject.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {
    private Long id;
    private String beneficiaryName;
    private String bankName;
    private Long accountNumber;
    private Long bsbNumber;
}
