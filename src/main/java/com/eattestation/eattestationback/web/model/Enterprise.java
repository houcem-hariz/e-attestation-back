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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enterprise_id", unique = true, nullable = false)
    private Long enterpriseId;

    @Column(name = "enterprise_name", nullable = false)
    private String enterpriseName;

    @Column(name = "enterprise_siret", unique = true, nullable = false)
    private String enterpriseSiret;

    @Column(name = "certified_iso_27001", nullable = false)
    private boolean certifiedIso27001;

    @Column(name = "certified_iso_9001", nullable = false)
    private boolean certifiedIso9001;

    @Column(name = "certified_iso_45001", nullable = false)
    private boolean certifiedIso45001;

    @Column(name = "iso_27001_task_executed", nullable = false)
    private boolean iso27001TaskExecuted;

    @Column(name = "iso_9001_task_executed", nullable = false)
    private boolean iso9001TaskExecuted;

    @Column(name = "iso_45001_task_executed", nullable = false)
    private boolean iso45001TaskExecuted;
}
