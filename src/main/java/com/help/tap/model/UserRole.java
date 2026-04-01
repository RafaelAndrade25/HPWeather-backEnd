package com.help.tap.model;

public enum UserRole {
    ADMIN("ADMIN"),
    PATIENT("PATIENT"),
    DOCTOR("DOCTOR"),
    POLICE("POLICE"),
    FIREFIGHTER("FIREFIGHTER"),
    RESCUER("RESCUER");
        //no banco isso deve ser salvo como ROLE_NOMEDAROLE - aqui deve ser letra maiuscula
    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
