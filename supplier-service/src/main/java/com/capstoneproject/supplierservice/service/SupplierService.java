package com.capstoneproject.supplierservice.service;

import com.capstoneproject.supplierservice.dto.BankResponse;
import com.capstoneproject.supplierservice.dto.SupplierRequest;
import com.capstoneproject.supplierservice.dto.SupplierResponse;
import com.capstoneproject.supplierservice.model.BankDetails;
import com.capstoneproject.supplierservice.model.Supplier;
import com.capstoneproject.supplierservice.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public void createSupplier(SupplierRequest supplierRequest) {
        BankDetails bank = null;
        if (supplierRequest.getBankDetails() != null) {
            bank = BankDetails.builder()
                    .accountNumber(supplierRequest.getBankDetails().getAccountNumber())
                    .bankName(supplierRequest.getBankDetails().getBankName())
                    .beneficiaryName(supplierRequest.getBankDetails().getBeneficiaryName())
                    .bsbNumber(supplierRequest.getBankDetails().getBsbNumber())
                    .build();
            bank.setCreatedBy("ram"); //need to implement login and name should come from userid
            bank.setCreatedAt(LocalDateTime.now());
        }
        Supplier supplier = Supplier.builder()
                .companyName(supplierRequest.getCompanyName())
                .primaryContact(supplierRequest.getPrimaryContact())
                .email(supplierRequest.getEmail())
                .mobileNumber(supplierRequest.getMobileNumber())
                .address(supplierRequest.getAddress())
                .bankDetails(bank)
                .build();
        supplier.setCreatedBy("ram"); //need to implement login and name should come from userid
        supplier.setCreatedAt(LocalDateTime.now());
        supplierRepository.save(supplier);
        log.info("Supplier {} is saved", supplier.getId());
    }

    public List<SupplierResponse> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();

        return suppliers.stream().map(this::mapToSupplierResponse).toList();
    }

    public SupplierResponse getSupplierById(Long id) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(id);
        return supplierOpt.isPresent() ? supplierOpt.map(this::mapToSupplierResponse).get() : null;
    }

    public void updateSupplier(Long id, SupplierRequest supplierRequest) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(id);

        supplierOpt.ifPresent(supplier -> {
            BankDetails bank = supplierOpt.map(Supplier::getBankDetails).orElse(null);
            BankDetails bankDetails;

            if (bank != null || supplierRequest.getBankDetails() != null) {
                bankDetails = new BankDetails();
                bankDetails.setBankName(supplierRequest.getBankDetails() != null ? supplierRequest.getBankDetails().getBankName() : bank.getBankName());
                bankDetails.setAccountNumber(supplierRequest.getBankDetails() != null ? supplierRequest.getBankDetails().getAccountNumber() : bank.getAccountNumber());
                bankDetails.setBsbNumber(supplierRequest.getBankDetails() != null ? supplierRequest.getBankDetails().getBsbNumber() : bank.getBsbNumber());
                bankDetails.setBeneficiaryName(supplierRequest.getBankDetails() != null ? supplierRequest.getBankDetails().getBeneficiaryName() : bank.getBeneficiaryName());
                bankDetails.setCreatedAt(bank == null ? LocalDateTime.now() : null);
                bankDetails.setCreatedBy(bank == null ? "ram" : null);
                bankDetails.setUpdatedAt(bank != null ? LocalDateTime.now() : null);
                bankDetails.setUpdatedBy(bank != null ? "Shyam" : null);
            } else {
                bankDetails = null;
            }

            Supplier updatedSupplier = Supplier.builder()
                    .id(supplier.getId())
                    .companyName(Optional.ofNullable(supplierRequest.getCompanyName()).orElse(supplier.getCompanyName()))
                    .primaryContact(Optional.ofNullable(supplierRequest.getPrimaryContact()).orElse(supplier.getPrimaryContact()))
                    .email(Optional.ofNullable(supplierRequest.getEmail()).orElse(supplier.getEmail()))
                    .mobileNumber(Optional.ofNullable(supplierRequest.getMobileNumber()).orElse(supplier.getMobileNumber()))
                    .address(Optional.ofNullable(supplierRequest.getAddress()).orElse(supplier.getAddress()))
                    .bankDetails(bankDetails)
                    .createdBy(supplier.getCreatedBy())
                    .updatedBy("shyam") // Change the name with the login credential
                    .createdAt(supplier.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();

            supplierRepository.saveAndFlush(updatedSupplier);
            log.info("Supplier {} is updated", updatedSupplier.getId());
        });
    }

    private SupplierResponse mapToSupplierResponse(Supplier supplier) {
        SupplierResponse.SupplierResponseBuilder responseBuilder = SupplierResponse.builder()
                .id(supplier.getId())
                .companyName(supplier.getCompanyName())
                .primaryContact(supplier.getPrimaryContact())
                .email(supplier.getEmail())
                .mobileNumber(supplier.getMobileNumber())
                .address(supplier.getAddress());

        if (supplier.getBankDetails() != null) {
            responseBuilder.bankDetails(mapToBankResponse(supplier.getBankDetails()));
        }

        return responseBuilder.build();
    }

    private BankResponse mapToBankResponse(BankDetails bank) {
        return BankResponse.builder()
                .id(bank.getId())
                .accountNumber(bank.getAccountNumber())
                .bankName(bank.getBankName())
                .beneficiaryName(bank.getBeneficiaryName())
                .bsbNumber(bank.getBsbNumber())
                .build();
    }

}

