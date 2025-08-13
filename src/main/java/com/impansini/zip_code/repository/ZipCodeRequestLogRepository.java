package com.impansini.zip_code.repository;

import com.impansini.zip_code.domain.ZipCodeRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipCodeRequestLogRepository extends JpaRepository<ZipCodeRequestLog, Long> {
    List<ZipCodeRequestLog> findAllByInputZipCode(String inputZipCode);
}
