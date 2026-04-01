package com.help.tap.service;

import com.help.tap.dto.authentication.LoginRequestDTO;
import com.help.tap.dto.authentication.LoginResponseDTO;
import com.help.tap.infra.security.JwtUtil;
import com.help.tap.model.User;
import com.help.tap.model.UserRole;
import com.help.tap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private static final List<UserRole> MOBILE_ROLES = Arrays.asList(UserRole.ADMIN, UserRole.PATIENT);

    private static final List<UserRole> WEB_ROLES = Arrays.asList(UserRole.ADMIN, UserRole.PATIENT,
            UserRole.DOCTOR, UserRole.POLICE, UserRole.FIREFIGHTER, UserRole.RESCUER);

    @Transactional(readOnly = true)
    public LoginResponseDTO loginMobile(LoginRequestDTO loginRequestDTO){
        User user  = validateCredentials(loginRequestDTO);

        if (!MOBILE_ROLES.contains(user.getRole())) {
            throw new BadCredentialsException("This type of user cannot be accessed by a mobile application");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().toString(),
                user.getId()
        );

        return new LoginResponseDTO(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                jwtExpiration,
                "MOBILE"
        );
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO loginWeb(LoginRequestDTO loginRequestDTO){
        User user = validateCredentials(loginRequestDTO);

        if (!WEB_ROLES.contains(user.getRole())) {
            throw new BadCredentialsException("This type of user cannot be accessed by a web application");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().toString(),
                user.getId()
        );

        return new LoginResponseDTO(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                jwtExpiration,
                "WEB"
        );
    }

    private User validateCredentials(LoginRequestDTO loginRequestDTO){
        User user = userRepository.findByEmail(loginRequestDTO.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return user;
    }

    public Integer validateTokenAndGetUserId(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new BadCredentialsException("Token inválido ou expirado");
        }
        return jwtUtil.extractUserId(token);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
