package com.help.tap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IllnessCreateDTO(

        @NotBlank(message = "Nome da doença é obrigatório")
        String name,

        @NotNull(message = "É necessário informar se é sensível")
        Boolean isSensitive,

        String notes
) {}

