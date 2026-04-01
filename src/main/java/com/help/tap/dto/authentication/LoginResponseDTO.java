package com.help.tap.dto.authentication;

import com.help.tap.model.UserRole;

public record LoginResponseDTO(
        String token,
        String type,
        Integer userId,
        String email,
        String fullName,
        UserRole role,
        Long expiresIn,
        String platform){}
