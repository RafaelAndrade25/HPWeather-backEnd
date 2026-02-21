package com.HelpTapProj.backEnd.dto;

public record IllnessResponseDTO(
        Integer id,
        String name,
        Boolean isSensitive,
        String notes
) {}
