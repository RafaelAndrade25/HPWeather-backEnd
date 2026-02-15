package com.HelpTapProj.backEnd.dto;

import com.HelpTapProj.backEnd.model.UserRole;

public record LoginResponseDTO(
        String token,
        String type,
        Integer userId,
        String email,
        String fullName,
        UserRole role,
        Long expiresIn,
        String platform){}
