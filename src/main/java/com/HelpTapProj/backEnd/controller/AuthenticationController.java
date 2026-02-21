package com.HelpTapProj.backEnd.controller;

import com.HelpTapProj.backEnd.dto.LoginRequestDTO;
import com.HelpTapProj.backEnd.dto.LoginResponseDTO;
import com.HelpTapProj.backEnd.service.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthorizationService authorizationService;

    @PostMapping("mobile/login")
    public ResponseEntity<LoginResponseDTO> loginMobile(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = authorizationService.loginMobile(loginRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("web/login")
    public ResponseEntity<LoginResponseDTO> loginWeb(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = authorizationService.loginWeb(loginRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("validate")
    public ResponseEntity<Integer> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Integer userId = authorizationService.validateTokenAndGetUserId(token);
        return ResponseEntity.ok(userId);
    }
}
