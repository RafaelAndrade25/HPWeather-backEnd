package com.help.tap.dto;

public record IllnessResponseDTO(
        Integer id,
        String name,
        Boolean isSensitive,
        String notes
) {}
