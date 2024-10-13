package com.example.hotel_projects.service;


import com.example.hotel_projects.dto.JwtDTO;
import com.example.hotel_projects.dto.ProfileDTO;
import com.example.hotel_projects.dto.request.AuthRegistrationRequest;
import com.example.hotel_projects.dto.request.ProfileLoginRequestDTO;
import com.example.hotel_projects.entity.person.EmailHistoryEntity;
import com.example.hotel_projects.entity.ProfileEntity;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.enums.ProfileRole;
import com.example.hotel_projects.enums.ProfileStatus;
import com.example.hotel_projects.exp.AppBadException;
import com.example.hotel_projects.repository.EmailHistoryRepository;
import com.example.hotel_projects.repository.ProfileRepository;
import com.example.hotel_projects.utl.JWTUtil;
import com.example.hotel_projects.utl.MDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.hotel_projects.service.VerificationEmailService.sendEmailVerification;
import static com.example.hotel_projects.service.VerificationEmailService.sendVerificationEmail;
import static com.example.hotel_projects.utl.JWTUtil.decode;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final ProfileRepository profileRepository;
    private final MailSenderService mailSenderService;
    private final ResourceBundleService resourceBundleService;
    private final EmailHistoryRepository emailHistoryRepository;

    public ProfileDTO loge(ProfileLoginRequestDTO dto, AppLanguage appLanguage) {
        ProfileEntity entity =getByEmail(dto.getEmail(),appLanguage);

        if (entity.getStatus().equals(ProfileStatus.ACTIVE)
                && entity.getPassword().
                equals(MDUtil.encode(dto.getPassword()))) {
            String jwt=JWTUtil.encode(entity.getEmail(),entity.getRole(),appLanguage);

          String txt=  sendEmailVerification(jwt,"Siz bizning Hotel saytimizga ushbu email orqali kirishga harakat qildingiz." +
                  " Agar bu siz bo'lsangiz, iltimos tasdiqlang yoki rad eting");
            mailSenderService.sendEmail(entity.getEmail(), "Check login", txt);
            profileRepository.updateByLoggedInAndEmail(true,entity.getEmail());

            return toDto(entity,jwt);
        }
        throw new AppBadException(resourceBundleService.getMessage("email.password.wrong", appLanguage));

    }
    public String registrationEmail(AuthRegistrationRequest dto, AppLanguage appLanguage) {
        if(!validateUserRegistrationAttempt(dto,appLanguage)){

        }
        ProfileEntity entity=entityDto(dto);

        String jwt= JWTUtil.encode(entity.getEmail(),entity.getRole(),appLanguage);

        String text=sendVerificationEmail(entity,jwt);
        mailSenderService.sendEmail(entity.getEmail(), "Complete registration", text);

        var emailHistoryEntity=new EmailHistoryEntity();
        emailHistoryEntity.setEmail(dto.getEmail());
        emailHistoryEntity.setMessage(text);
        emailHistoryRepository.save(emailHistoryEntity);

       String message=resourceBundleService.getMessage("please.confirm.email",appLanguage);
        return message;

    }

    private boolean validateUserRegistrationAttempt(AuthRegistrationRequest dto, AppLanguage language) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.deleteByEmail(optional.get().getEmail());

                LocalDateTime from = LocalDateTime.now().minusMinutes(1);
                LocalDateTime to = LocalDateTime.now();

                if (emailHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 3) {
                    throw new AppBadException(resourceBundleService.getMessage("To.many.attempt.Please.try.after.one.minute", language));
                }
                return true;
            }
            throw new AppBadException(resourceBundleService.getMessage("This.email.has.been.registered", language));
        }
        return true;
    }

public Boolean emailVerification(String token) {
       JwtDTO jwtDTO=decode(token);

    ProfileEntity profileEntity=getByEmail(jwtDTO.getEmail(), jwtDTO.getAppLanguage());

    if (!profileEntity.getStatus().equals(ProfileStatus.ACTIVE)) {
        profileRepository.updateByEmail(ProfileStatus.ACTIVE, jwtDTO.getEmail());
        return true;
    }
    throw new AppBadException(resourceBundleService.getMessage("This.email.has.been.registered",jwtDTO.getAppLanguage()));
}


    private ProfileEntity getByEmail(String email,AppLanguage appLanguage){
    Optional<ProfileEntity> optional = Optional.ofNullable(profileRepository.findByEmail(email)
            .orElseThrow(() -> new AppBadException(resourceBundleService.getMessage("item.not.found", appLanguage))));
    return optional.get();

}
    public Boolean emailVerificationPermission(String token) {

        ProfileEntity profileEntity=getProfileToken(token);
        if(!profileEntity.isLoggedIn()){
          profileRepository.updateByLoggedInAndEmail(true,profileEntity.getEmail());
          return true;
        }
        return null;
    }
    public Boolean emailVerificationRejection(String token) {
        ProfileEntity profileEntity=getProfileToken(token);
        if(profileEntity.isLoggedIn()){
            profileRepository.updateByLoggedInAndEmail(false,profileEntity.getEmail());
            return true;
        }
        return null;
    }

    private  ProfileEntity entityDto(AuthRegistrationRequest dto){
        ProfileEntity entity = new ProfileEntity();
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setName(dto.getUsername());

        profileRepository.save(entity);
        return entity;
    }
    private  ProfileEntity getProfileToken(String token){
        JwtDTO jwtDTO=decode(token);
        ProfileEntity profileEntity=getByEmail(jwtDTO.getEmail(), jwtDTO.getAppLanguage());
        return profileEntity;
    }

    private ProfileDTO toDto(ProfileEntity entity, String jwt){
        ProfileDTO dto=new ProfileDTO();

        dto.setEmail(entity.getEmail());
        dto.setJwtToken(jwt);

        return dto;
    }
}