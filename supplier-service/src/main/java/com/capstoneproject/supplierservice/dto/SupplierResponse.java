package com.capstoneproject.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponse {
    private Long id;
    private String companyName;
    private String primaryContact;
    private String email;
    private String mobileNumber;
    private String address;
    private BankResponse bankDetails;
}
