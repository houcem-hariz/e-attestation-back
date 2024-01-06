package com.eattestation.eattestationback.web.controller;

import com.eattestation.eattestationback.web.dto.EnterpriseDto;
import com.eattestation.eattestationback.web.exception.EnterpriseException;
import com.eattestation.eattestationback.web.model.Enterprise;
import com.eattestation.eattestationback.web.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enterprise")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class EnterpriseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnterpriseController.class);

    private final EnterpriseService enterpriseService;

    @PostMapping(value = "/insert")
    public Enterprise insertEnterprise(
            @RequestBody final EnterpriseDto enterpriseDto) throws EnterpriseException {

        LOGGER.info("Begin insert of Controller Class");

        return enterpriseService.insertEnterprise(enterpriseDto);
    }

    @GetMapping
    public List<Enterprise> getAll() {
        return enterpriseService.getAllEnterprises();
    }
}
