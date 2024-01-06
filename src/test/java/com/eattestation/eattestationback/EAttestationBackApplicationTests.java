package com.eattestation.eattestationback;

import com.eattestation.eattestationback.web.config.AppConfig;
import com.eattestation.eattestationback.web.dao.EnterpriseDao;
import com.eattestation.eattestationback.web.dto.EnterpriseDto;
import com.eattestation.eattestationback.web.exception.EnterpriseException;
import com.eattestation.eattestationback.web.model.Enterprise;
import com.eattestation.eattestationback.web.service.impl.EnterpriseServiceImpl;
import com.eattestation.eattestationback.web.validation.ObjectValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
class EAttestationBackApplicationTests {
    static ObjectValidation<EnterpriseDto> validator;

    @InjectMocks
    EnterpriseServiceImpl enterpriseService;

    @Mock
    EnterpriseDao enterpriseDao;

    @Mock
    MessageSource messageSource;

    @BeforeAll
    static void init(){
        validator = new ObjectValidation<>();
    }

    @Test
    void getAllEnterprises() {
        List<Enterprise> list = new ArrayList<>(List.of(
                new Enterprise(
                        1L,
                        "Enterprise Name",
                        "12345678912345",
                        true,
                        true,
                        false,
                        false,
                        false,
                        false
                ),
                new Enterprise(
                        2L,
                        "Enterprise Name",
                        "12345678912345",
                        true,
                        true,
                        false,
                        false,
                        false,
                        false
                )
        ));

        when(enterpriseDao.findAll()).thenReturn(list);
        List<Enterprise> enterpriseList = enterpriseService.getAllEnterprises();
        assertEquals(2, enterpriseList.size());
        verify(enterpriseDao, times(1)).findAll();
    }
    @Test
    void nominalCase(){
        EnterpriseDto enterpriseDto = new EnterpriseDto(
                1L,
                "Enterprise Name",
                "12345678912345",
                true,
                true,
                false
        );
        Set<String> violations = validator.validate(enterpriseDto);
        Assertions.assertTrue(violations.isEmpty());
    }
    @Test
    void checkEnterpriseName() {
        EnterpriseDto enterpriseDto1 =  new EnterpriseDto(
                1L,
                null,
                "12345678912345",
                true,
                true,
                false
        );
        assertThat(validator.validate(enterpriseDto1).size()).isEqualTo(2);
    }

    @Test
    void cannotAddEnterpriseWithSameSiret(){
        when(enterpriseDao.existsByEnterpriseSiret("12345678912345")).thenReturn(true);
        EnterpriseDto existingEnterprise = new EnterpriseDto(
                99L,
                "Enterprise Name",
                "12345678912345",
                true,
                true,
                false
        );

        try {
            enterpriseService.insertEnterprise(existingEnterprise);
        } catch (EnterpriseException e) {
            System.out.println("Enterprise already exists");
        }

        verify(enterpriseDao, never()).save(any());
    }

}
