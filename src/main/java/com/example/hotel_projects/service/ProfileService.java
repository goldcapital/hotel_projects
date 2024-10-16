package com.example.hotel_projects.service;

import com.example.hotel_projects.confg.CustomUserDetails;
import com.example.hotel_projects.dto.HotelDTO;
import com.example.hotel_projects.entity.ProfileEntity;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.exp.AppBadException;
import com.example.hotel_projects.repository.ProfileRepository;
import com.example.hotel_projects.utl.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.hotel_projects.service.VerificationEmailService.sendEmailVerification;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ResourceBundleService resourceBundleService;
    private final MailSenderService mailSenderService;
    private final HotelService hotelService;

    public Boolean getByIdAndIsLoggedIn(String email, AppLanguage appLanguage) {
        return profileRepository.findByEmailAndIsLoggedIn(email, true).map(profileEntity -> {
                    return true;
                })
                .orElseThrow(() -> new AppBadException(resourceBundleService.getMessage("you.are.locked.out.please.login.again", appLanguage)));
    }

    public void setVerificationProfile(Long hotelId, String roomNumber, CustomUserDetails currentUser) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(currentUser.getEmail());
        ProfileEntity profile = optional.get();

        HotelDTO hotelDTO = hotelService.getByIdHotel(hotelId);

        String jwt = JWTUtil.encode(currentUser.getEmail(), profile.getRole(), null);

        String txt = sendEmailVerification(jwt, "shu email orqali biznig %s holilimzdan ushbu raqamli %s xnonani band qilishdi ." +
                "BU sizmisiz siz bulsangiz tastiqlang bulmasam rad eting");

        txt = String.format(txt, hotelDTO.getHotelName(), roomNumber);
        mailSenderService.sendEmail(profile.getEmail(), "Check login", txt);
    }
}
