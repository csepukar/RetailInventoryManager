package com.capstoneproject.brandservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {
    private Long id;
    private String name;
    private String description;
    private String logo;
    private String website;
    private String address;
    private String email;
    private Long phoneNumber;
    private Boolean activeStatus;
}
