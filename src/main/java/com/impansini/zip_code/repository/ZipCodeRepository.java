package com.impansini.zip_code.repository;

import com.impansini.zip_code.domain.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZipCodeRepository extends JpaRepository<ZipCode, Long> {
    Optional<ZipCode> findZipCodeByZipCode(String zipCode);
}
