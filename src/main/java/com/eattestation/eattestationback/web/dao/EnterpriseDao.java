package com.eattestation.eattestationback.web.dao;

import com.eattestation.eattestationback.web.model.Enterprise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public interface EnterpriseDao extends CrudRepository<Enterprise, Long> {
    @NonNull
    @SuppressWarnings(value = "unchecked")
    Enterprise save(@NonNull Enterprise enterprise);

    @NonNull
    List<Enterprise> findAll();

    boolean existsByEnterpriseSiret(String enterpriseSiret);
}
