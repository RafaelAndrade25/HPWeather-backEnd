package com.help.tap.service;

import com.help.tap.dto.authentication.UserCreateDTO;
import com.help.tap.dto.authentication.UserResponseDTO;
import com.help.tap.dto.UserUpdateDTO;
import com.help.tap.model.User;
import com.help.tap.model.UserRole;
import com.help.tap.repository.UserRepository;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByEmail(userCreateDTO.email())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (userRepository.existsByNationalRegistration(userCreateDTO.cpf())) {
            throw new IllegalArgumentException("CPF already in use");
        }
        if (userRepository.existsByIdentifier(userCreateDTO.identifier())) {
            throw new IllegalArgumentException("Identifier already in use");
        }

        User user = User.builder().fullName(userCreateDTO.fullName())
                .nationalRegistration(userCreateDTO.cpf())
                .birthDate(userCreateDTO.dateBirth())
                .sex(userCreateDTO.sex())
                .email(userCreateDTO.email())
                .password(passwordEncoder.encode(userCreateDTO.password()))
                .fatherName(userCreateDTO.nameOfFather())
                .motherName(userCreateDTO.nameOfMother())
                .identifier(userCreateDTO.identifier())
                .role(userCreateDTO.role())
                .build();
        User savedUser = userRepository.save(user);
        return toResponseDTO(savedUser);
    }

    @Transactional
    public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return toResponseDTO(user);

    }

    @Transactional
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        return toResponseDTO(user);
    }

    @Transactional
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<UserResponseDTO> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDTO updateUser(Integer id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));

        if (dto.email() != null && !dto.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.email())) {
                throw new IllegalArgumentException("Email já cadastrado");
            }
            user.setEmail(dto.email());
        }
        if (dto.identifier() != null && !dto.identifier().equals(user.getIdentifier())) {
            if (userRepository.existsByIdentifier(dto.identifier())) {
                throw new IllegalArgumentException("Identificador já cadastrado");
            }
            user.setIdentifier(dto.identifier());
        }
            if (dto.fullName() != null) user.setFullName(dto.fullName());
            if (dto.dateBirth() != null) user.setBirthDate(dto.dateBirth());
            if (dto.sex() != null) user.setSex(dto.sex());
            if (dto.password() != null) user.setPassword(passwordEncoder.encode(dto.password()));
            if (dto.nameOfFather() != null) user.setFatherName(dto.nameOfFather());
            if (dto.nameOfMother() != null) user.setMotherName(dto.nameOfMother());
            if (dto.role() != null) user.setRole(dto.role());

            User updatedUser = userRepository.save(user);
            return toResponseDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
        }
        userRepository.deleteById(id);
    }
    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getNationalRegistration(),
                user.getBirthDate(),
                user.getSex(),
                user.getEmail(),
                user.getFatherName(),
                user.getMotherName(),
                user.getIdentifier(),
                user.getRole()
        );
    }
}
