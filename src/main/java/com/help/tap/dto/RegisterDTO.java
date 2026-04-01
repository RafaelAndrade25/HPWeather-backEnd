package com.help.tap.dto;

import com.help.tap.model.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {

}
