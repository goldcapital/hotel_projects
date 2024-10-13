package com.example.hotel_projects.service;

import com.example.hotel_projects.entity.ProfileEntity;
import org.springframework.stereotype.Service;

@Service
public class VerificationEmailService {
    public static   String sendVerificationEmail(ProfileEntity entity, String jwt){

        String text = "<h1 style=\"=text-align: center\">Hello %s</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding:30px\"> To complete registration please link to the following link </p>\n" +
                "<a style=\"background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8081/auth/verification/email/%s\n" +
                "\">Click</a>\n" +
                "<br>\n";

        text = String.format(text, entity.getName(), jwt);
        return text;
    }
    public static String sendEmailVerification(String jwt,String message) {
        String text = "<h1 style=\"text-align: center\">Salom</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding:30px\">%s!</p>\n" +
                "<a style=\"background-color: #4CAF50; " +
                "color: white; padding: 14px 25px; text-align: center; text-decoration: none; display: inline-block;\" href=\"http://localhost:8081/auth/verification/email/permission/%s\">Tasdiqlash</a>\n" +
                "<br>\n" +
                "<a style=\"background-color: #f44336; color: white; padding: 14px 25px; text-align: center; text-decoration: none; display: inline-block;\" href=\"http://localhost:8081/auth/verification/email/rejection/%s\">Rad etish</a>\n" +
                "<br>\n";

        text = String.format(text, message, jwt, jwt);
        return text;
    }
}
