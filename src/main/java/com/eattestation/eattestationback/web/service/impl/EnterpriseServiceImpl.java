package com.eattestation.eattestationback.web.service.impl;

import com.eattestation.eattestationback.web.dao.EnterpriseDao;
import com.eattestation.eattestationback.web.dto.EnterpriseDto;
import com.eattestation.eattestationback.web.exception.EnterpriseException;
import com.eattestation.eattestationback.web.model.Enterprise;
import com.eattestation.eattestationback.web.service.EnterpriseService;

import com.eattestation.eattestationback.web.validation.ObjectValidation;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    private final EnterpriseDao enterpriseDao;
    private final MessageSource messageSource;
    private final ObjectValidation<EnterpriseDto> objectValidation = new ObjectValidation<>();

    public EnterpriseServiceImpl(EnterpriseDao enterpriseDao,
                                 @NonNull MessageSource messageSource) {
        this.enterpriseDao = enterpriseDao;
        this.messageSource = messageSource;
    }

    @Override
    public Enterprise insertEnterprise(EnterpriseDto enterpriseDto) throws EnterpriseException {

        Enterprise enterprise;
        var violations = objectValidation.validate(enterpriseDto);

        if (!violations.isEmpty()) {
            throw new EnterpriseException(
                    INTERNAL_SERVER_ERROR.toString(),
                    String.join("\n", violations));
        }

        if (enterpriseDao.existsByEnterpriseSiret(enterpriseDto.getEnterpriseSiret())){
            throw new EnterpriseException(INTERNAL_SERVER_ERROR.toString(),
                    messageSource.getMessage(
                            "Duplicate.form.enterpriseSiret",
                            null,
                            LocaleContextHolder.getLocale()));
        }
        else {
            enterprise = new Enterprise();
            enterprise.setEnterpriseName(enterpriseDto.getEnterpriseName());
            enterprise.setEnterpriseSiret(enterpriseDto.getEnterpriseSiret());
            enterprise.setHasIso27001(enterpriseDto.getHasIso27001());
            enterprise.setHasIso9001(enterpriseDto.getHasIso9001());
            enterprise.setHasIso45001(enterpriseDto.getHasIso45001());
            return enterpriseDao.save(enterprise);
        }

    }

    @Override
    public List<Enterprise> getAll() {
        return enterpriseDao.findAll();
    }
}
