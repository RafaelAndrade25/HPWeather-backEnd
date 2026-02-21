package com.HelpTapProj.backEnd.service;

import com.HelpTapProj.backEnd.dto.IllnessCreateDTO;
import com.HelpTapProj.backEnd.dto.IllnessResponseDTO;
import com.HelpTapProj.backEnd.infra.security.EncryptionUtil;
import com.HelpTapProj.backEnd.model.Illness;
import com.HelpTapProj.backEnd.model.User;
import com.HelpTapProj.backEnd.model.UserRole;
import com.HelpTapProj.backEnd.repository.IllnessRepository;
import com.HelpTapProj.backEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IllnessService {

    private final IllnessRepository illnessRepository;
    private final UserRepository userRepository;
    private final EncryptionUtil encryptionUtil;

    @Transactional
    public IllnessResponseDTO createIllness(IllnessCreateDTO dto, Integer userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Illness illness = Illness.builder()
                .user(user)
                .isSensitive(dto.isSensitive())
                .build();

        // Configura encriptação antes de setar os valores
        illness.setEncryptionUtil(encryptionUtil);
        illness.setName(dto.name());
        illness.setNotes(dto.notes());

        Illness saved = illnessRepository.save(illness);
        return toResponseDTO(saved, true);
    }

    @Transactional(readOnly = true)
    public List<IllnessResponseDTO> getIllnessesByUser(Integer userId, Authentication authentication) {
        List<Illness> illnesses = illnessRepository.findByUserId(userId);

        // Verifica role do usuário autenticado
        UserRole userRole = extractRole(authentication);
        boolean canSeeSensitive = canAccessSensitiveData(userRole, userId, authentication);

        return illnesses.stream()
                .filter(illness -> {
                    // Se não for sensível, todos podem ver
                    if (!illness.getIsSensitive()) return true;
                    // Se for sensível, apenas quem tem permissão
                    return canSeeSensitive;
                })
                .map(illness -> {
                    illness.setEncryptionUtil(encryptionUtil);
                    try {
                        return toResponseDTO(illness, canSeeSensitive);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    private boolean canAccessSensitiveData(UserRole role, Integer targetUserId, Authentication authentication) {
        String email = authentication.getName();
        User authUser = userRepository.findByEmail(email).orElse(null);

        if (authUser == null) return false;

        switch (role) {
            case DOCTOR:
                return true;
            case PATIENT:
                return authUser.getId().equals(targetUserId);
            case ADMIN:
            case POLICE:
            case FIREFIGHTER:
            case RESCUER:
            default:
                return false;
        }
    }

    private UserRole extractRole(Authentication authentication) {
        String roleStr = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .orElse("");

        try {
            return UserRole.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private IllnessResponseDTO toResponseDTO(Illness illness, boolean decrypt) throws Exception {
        String name = decrypt && illness.getIsSensitive()
                ? illness.getDecryptedName()
                : illness.getIllnessName();

        String notes = decrypt && illness.getIsSensitive()
                ? illness.getDecryptedNotes()
                : illness.getNotes();

        // Se não pode ver dados sensíveis, oculta
        if (!decrypt && illness.getIsSensitive()) {
            name = "[DADOS SENSÍVEIS - SEM PERMISSÃO]";
            notes = "[DADOS SENSÍVEIS - SEM PERMISSÃO]";
        }

        return new IllnessResponseDTO(
                illness.getIlnessId(),
                name,
                illness.getIsSensitive(),
                notes
        );
    }
}
