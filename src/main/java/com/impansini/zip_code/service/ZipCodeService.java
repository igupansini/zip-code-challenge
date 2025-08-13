package com.impansini.zip_code.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impansini.zip_code.domain.ZipCode;
import com.impansini.zip_code.domain.ZipCodeRequestLog;
import com.impansini.zip_code.repository.ZipCodeRepository;
import com.impansini.zip_code.repository.ZipCodeRequestLogRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ZipCodeService {
    private final Logger log = LoggerFactory.getLogger(ZipCodeService.class);
    private final ZipCodeRepository zipCodeRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ZipCodeRequestLogRepository zipCodeRequestLogRepository;

    public ZipCodeService(
            ZipCodeRepository zipCodeRepository,
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            ZipCodeRequestLogRepository zipCodeRequestLogRepository
    ) {
        this.zipCodeRepository = zipCodeRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.zipCodeRequestLogRepository = zipCodeRequestLogRepository;
    }

    @Transactional
    public ZipCode save(ZipCode zipCode) {
        log.debug("Request to save a zipCode: {}", zipCode);

        ZipCode zipCodeFromApi = fetchZipCodeFromApi(zipCode.getZipCode());

        if (zipCode.getCreatedAt() == null) {
            zipCode.setCreatedAt(LocalDateTime.now());
        }
        zipCode.setStreet(zipCodeFromApi.getStreet());
        zipCode.setCity(zipCodeFromApi.getCity());
        zipCode.setState(zipCodeFromApi.getState());
        zipCode.setCountry(zipCodeFromApi.getCountry());

        return zipCodeRepository.save(zipCode);
    }

    private ZipCode fetchZipCodeFromApi(String zipCodeValue) {
        String url = "http://wiremock:8080/api/zipcode-wiremock/" + zipCodeValue;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            log.info("API WireMock response: {}", response.getBody());

            ZipCodeRequestLog logEntry = new ZipCodeRequestLog();
            logEntry.setInputZipCode(zipCodeValue);
            logEntry.setApiResponse(response.getBody());
            logEntry.setRequestTime(LocalDateTime.now());
            zipCodeRequestLogRepository.save(logEntry);

            return objectMapper.readValue(response.getBody(), ZipCode.class);
        } catch (Exception e) {
            log.error("Error processing API response: {}", e.getMessage());
            throw new RuntimeException("Failed to process API response", e);
        }
    }

    public List<ZipCode> findAll() {
        log.debug("Request to get all zipCodes");
        return zipCodeRepository.findAll();
    }

    public Optional<ZipCode> findOne(Long id) {
        log.debug("Request to get a zipCode: {}", id);
        return zipCodeRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        zipCodeRepository
                .findById(id)
                .ifPresent(zipCode -> {
                    zipCodeRepository.delete(zipCode);
                    log.debug("Deleted zipCode: {}", zipCode);
                });
    }
}
