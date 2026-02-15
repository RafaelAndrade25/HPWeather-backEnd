package com.HelpTapProj.backEnd.dto;

import com.HelpTapProj.backEnd.model.UserRole;

import java.time.LocalDate;

public record UserResponseDTO(Integer id,
                              String fullName,
                              String cpf,
                              LocalDate dateBirth,
                              String sex,
                              String email,
                              String nameOfFather,
                              String nameOfMother,
                              String identifier,
                              UserRole role
) {}
