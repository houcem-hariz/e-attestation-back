package com.eattestation.eattestationback.web.service.impl;

import com.eattestation.eattestationback.web.dao.EnterpriseDao;
import com.eattestation.eattestationback.web.dto.EnterpriseDto;
import com.eattestation.eattestationback.web.exception.EnterpriseException;
import com.eattestation.eattestationback.web.model.Enterprise;
import com.eattestation.eattestationback.web.service.EnterpriseService;

import com.eattestation.eattestationback.web.validation.ObjectValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    private final EnterpriseDao enterpriseDao;
    private final MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

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
            enterprise.setCertifiedIso27001(enterpriseDto.isCertifiedIso27001());
            enterprise.setCertifiedIso9001(enterpriseDto.isCertifiedIso9001());
            enterprise.setCertifiedIso45001(enterpriseDto.isCertifiedIso45001());
            return enterpriseDao.save(enterprise);
        }
    }

    @Override
    public List<Enterprise> getAllEnterprises() {
        return enterpriseDao.findAll();
    }

    @Override
    public void askForDocuments() {
        if (logger.isInfoEnabled()) {
            logger.info("Called askForDocuments enterprise service");
        }

        getAllEnterprises().stream()
                .filter(
                        (Predicate.not(Enterprise::isIso27001TaskExecuted)
                                .and(Enterprise::isIso27001TaskExecuted)
                        )
                        .or(Predicate.not(Enterprise::isIso9001TaskExecuted)
                                .and(Enterprise::isCertifiedIso9001)
                        )
                        .or(Predicate.not(Enterprise::isIso45001TaskExecuted))
                                .and(Enterprise::isCertifiedIso45001)
                )
                .forEach(enterprise -> {
                    String s = "%s (%s) : Déclenchement de la demande de document".formatted(
                            enterprise.getEnterpriseName(),
                            enterprise.getEnterpriseSiret());

                    if(enterprise.isCertifiedIso27001()){
                        System.out.println(String.join(" ",s, "ISO27001"));
                        enterprise.setIso27001TaskExecuted(true);
                    }

                    if(enterprise.isCertifiedIso9001()){
                        System.out.println(String.join(" ", s, "ISO9001"));
                        enterprise.setIso9001TaskExecuted(true);
                    }

                    if(enterprise.isCertifiedIso45001()){
                        System.out.println(String.join(" ",s, "ISO45001"));
                        enterprise.setIso45001TaskExecuted(true);
                    }

                    enterpriseDao.save(enterprise);
                });
    }
}
