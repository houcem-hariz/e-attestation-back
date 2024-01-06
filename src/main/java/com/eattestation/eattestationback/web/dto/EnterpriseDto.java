package com.eattestation.eattestationback.web.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
public class EnterpriseDto {

    private Long enterpriseId;
    @NotNull(message = "Le nom de l'entreprise est obligatoire")
    @NotEmpty(message = "Le nom de l'entreprise ne doit pas être vide")
    @Length(max = 100, message = "Le nom de l'entreprise ne doit pas dépasser 100 caractères")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$",
            message = "Le nom de l'entreprise ne doit contenir que des caractères alphanumériques")
    private String enterpriseName;

    @NotNull(message = "Le SIRET de l'entreprise est obligatoire")
    @NotEmpty(message = "Le SIRET de l'entreprise ne doit pas être vide")
    @Pattern(regexp="\\d{14}", message = "Le SIRET de l'entreprise doit contenir 14 chiffres exactement")
    private String enterpriseSiret;

    @NotNull(message = "certifiedIso27001 est obligatoire")
    private boolean certifiedIso27001;

    @NotNull(message = "certifiedIso9001 est obligatoire")
    private boolean certifiedIso9001;

    @NotNull(message = "certifiedIso45001 est obligatoire")
    private boolean certifiedIso45001;
}
