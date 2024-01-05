package com.eattestation.eattestationback.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "enterprise", schema = "public")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Enterprise implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** The enterprise id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enterprise_id", unique = true, nullable = false)
    private Long enterpriseId;

    /** The enterprise name. */
    @Column(name = "enterprise_name", nullable = false)
    private String enterpriseName;

    /** The enterprise SIRET. */
    @Column(name = "enterprise_siret", unique = true, nullable = false)
    private String enterpriseSiret;

    /** The email. */
    @Column(name = "has_iso_27001", nullable = false)
    private boolean hasIso27001;

    /** The email. */
    @Column(name = "has_iso_9001", nullable = false)
    private boolean hasIso9001;

    /** The email. */
    @Column(name = "has_iso_45001", nullable = false)
    private boolean hasIso45001;
}
