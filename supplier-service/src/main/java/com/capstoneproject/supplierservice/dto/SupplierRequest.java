package com.capstoneproject.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequest {
    private String companyName;
    private String primaryContact;
    private String email;
    private String mobileNumber;
    private String address;
    private BankRequest bankDetails;
}
