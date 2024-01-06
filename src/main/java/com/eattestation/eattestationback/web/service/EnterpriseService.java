package com.eattestation.eattestationback.web.service;

import com.eattestation.eattestationback.web.dto.EnterpriseDto;
import com.eattestation.eattestationback.web.exception.EnterpriseException;
import com.eattestation.eattestationback.web.model.Enterprise;

import java.util.List;

public interface EnterpriseService {
    Enterprise insertEnterprise(EnterpriseDto enterpriseDto) throws EnterpriseException;
    List<Enterprise> getAll();
    void askForDocuments();
}
