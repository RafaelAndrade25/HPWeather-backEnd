package com.HelpTapProj.backEnd.dto;

import com.HelpTapProj.backEnd.model.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {

}
