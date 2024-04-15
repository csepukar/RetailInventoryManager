package com.capstoneproject.supplierservice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankRequest {
    private String beneficiaryName;
    private String bankName;
    private Long accountNumber;
    private Long bsbNumber;
}
