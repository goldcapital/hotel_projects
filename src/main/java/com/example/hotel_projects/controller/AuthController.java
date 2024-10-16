package com.example.hotel_projects.controller;


import com.example.hotel_projects.dto.ProfileDTO;
import com.example.hotel_projects.dto.request.AuthRegistrationRequest;
import com.example.hotel_projects.dto.request.ProfileLoginRequestDTO;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    @GetMapping("/admin-token")
    public ResponseEntity<ProfileDTO>tokenAdmin(  @RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage appLanguage){
        return ResponseEntity.ok(authService.getAdminToken(appLanguage));
    }

    @PostMapping("/login")
    @Operation(summary = "Api for login", description = "this api used for authorization")
    public ResponseEntity<ProfileDTO> loge(@RequestBody ProfileLoginRequestDTO dto,
                                           @RequestHeader(value = "Accept-Language",
                                                   defaultValue = "uz") AppLanguage appLanguage) {
        log.info("loge", dto.getEmail());
        return ResponseEntity.ok(authService.loge(dto, appLanguage));
    }

    @Operation(summary = "Api for email  registration", description = "this api used for registration email")
    @PostMapping("/registration/email")
    public ResponseEntity<String> registrationEmail(@RequestBody AuthRegistrationRequest dto,
                                                    @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage appLanguage) {
        log.info("registration Email", dto.getEmail());
        return ResponseEntity.ok(authService.registrationEmail(dto, appLanguage));
    }

    @Operation(summary = "Api for email code Verification", description = "this api used for verification email")
    @GetMapping("/verification/email/{id}")
    public ResponseEntity<Boolean> emailVerification(@PathVariable("id") String token) {
        return ResponseEntity.ok(authService.emailVerification(token));
    }

    @Operation(summary = "API for granting email verification permission",
            description = "This API is used for confirming email verification permission by user Token.")
    @GetMapping("/verification/email/permission/{id}")
    public ResponseEntity<Boolean> emailVerificationPermission(@PathVariable("id") String token) {
        return ResponseEntity.ok(authService.emailVerificationPermission(token));
    }

    @Operation(summary = "API for rejecting email verification",
            description = "This API is used for rejecting email verification by user Token.")
    @GetMapping("/verification/email/rejection/{id}")
    public ResponseEntity<Boolean> emailVerificationRejection(@PathVariable("id") String token) {
        return ResponseEntity.ok(authService.emailVerificationRejection(token));
    }
}
