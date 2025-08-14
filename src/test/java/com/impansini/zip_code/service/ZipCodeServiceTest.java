package com.impansini.zip_code.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impansini.zip_code.domain.ZipCode;
import com.impansini.zip_code.domain.ZipCodeRequestLog;
import com.impansini.zip_code.repository.ZipCodeRepository;
import com.impansini.zip_code.repository.ZipCodeRequestLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ZipCodeServiceTest {

    @Mock
    private ZipCodeRepository zipCodeRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ZipCodeRequestLogRepository zipCodeRequestLogRepository;

    @InjectMocks
    private ZipCodeService zipCodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldFillMissingFieldsAndPersist() throws Exception {
        ZipCode input = new ZipCode();
        input.setZipCode("12345678");

        ZipCode apiZipCode = new ZipCode();
        apiZipCode.setStreet("Street of Fools, number 0");
        apiZipCode.setCity("Where Judas lost his boots");
        apiZipCode.setState("Liquid State");
        apiZipCode.setCountry("BraSil");

        String apiResponse = "{\"street\":\"Street of Fools, number 0\",\"city\":\"Where Judas lost his boots\",\"state\":\"Liquid State\",\"country\":\"BraSil\"}";
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(apiResponse));
        when(objectMapper.readValue(apiResponse, ZipCode.class)).thenReturn(apiZipCode);
        when(zipCodeRepository.save(any(ZipCode.class))).thenAnswer(i -> i.getArgument(0));

        ZipCode result = zipCodeService.save(input);

        assertEquals("Street of Fools, number 0", result.getStreet());
        assertEquals("Where Judas lost his boots", result.getCity());
        assertEquals("Liquid State", result.getState());
        assertEquals("BraSil", result.getCountry());
        assertNotNull(result.getCreatedAt());
        verify(zipCodeRepository).save(result);
        verify(zipCodeRequestLogRepository).save(any(ZipCodeRequestLog.class));
    }

    @Test
    void findAll_shouldReturnList() {
        List<ZipCode> zipCodes = Arrays.asList(new ZipCode(), new ZipCode());
        when(zipCodeRepository.findAll()).thenReturn(zipCodes);

        List<ZipCode> result = zipCodeService.findAll();

        assertEquals(2, result.size());
        verify(zipCodeRepository).findAll();
    }

    @Test
    void findOne_shouldReturnOptional() {
        ZipCode zipCode = new ZipCode();
        zipCode.setId(1L);
        when(zipCodeRepository.findById(1L)).thenReturn(Optional.of(zipCode));

        Optional<ZipCode> result = zipCodeService.findOne(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(zipCodeRepository).findById(1L);
    }

    @Test
    void delete_shouldRemoveZipCode() {
        ZipCode zipCode = new ZipCode();
        zipCode.setId(1L);
        when(zipCodeRepository.findById(1L)).thenReturn(Optional.of(zipCode));

        zipCodeService.delete(1L);

        verify(zipCodeRepository).delete(zipCode);
    }
}